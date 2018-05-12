package com.em.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.em.constant.Constant;
import com.em.tools.Util;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

@SuppressWarnings("rawtypes")
public class BaseModel<M extends BaseModel> extends Model<M> {

    /**
     * 
     */
    private static final long serialVersionUID = -5776595751865905849L;
    
    protected SysUsers getUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession(false) == null || !currentUser.isAuthenticated())
            return new SysUsers();//return null;
        SysUsers users = (SysUsers) currentUser.getSession(false).getAttribute(Constant.SYS_SESSION_ADMIN);
        return users;
    }
    
    protected List<Object> conver2Object(List<M> list, String column) {
        List<Object> newlist = new ArrayList<Object>();
        for (M m : list) {
            newlist.add(m.get(column));
        }
        return newlist;
    }
    
    protected String getRecursionSql(String columns, String id, String pid) {
        Table tb = this.getTable();
        Stack<String> ailsColumns = new Stack<String>();
        for (String s : columns.split(",")) {
            ailsColumns.push("t." + s.trim());
        }
        String ails = StringUtils.join(ailsColumns.toArray(), ",");
        StringBuilder sql_recursion = new StringBuilder()
                .append("WITH children(" + columns + ") ")
                .append("AS (")
                .append("SELECT "+ columns +" FROM "+ tb.getName() +" WHERE ISNULL("+ pid +", '') = ? ")
                .append("UNION ALL ")
                .append("SELECT "+ ails +" FROM " + tb.getName() +" AS t ")
                .append("INNER JOIN children AS c ON ISNULL(t."+ pid + ", '') = c."+ id +") ");
        
        return sql_recursion.toString();
    }
    
    @SuppressWarnings("unchecked")
    public M setCreateInfo() {
        return (M) this.set("createuser", getUser().get("id")).set("createtime", Util.getSqlTime(new Date()));
    }
    
    @SuppressWarnings("unchecked")
    public M setUpdateInfo() {
        return (M) this.set("updateuser", getUser().get("id")).set("updatetime", Util.getSqlTime(new Date()));
    }
    
    public M addVersion(Object version) {
        Long add = 0L;
        if (!Util.isEmptyString(version))
            add = Long.valueOf(version.toString());
        
        return (M) this.set("version", add + 1);
    }
    
    public M getByColumn(String columnName, Object value, String columns) {
        if (Util.isEmptyString(value))
            return null;
        String sql = "SELECT "+ columns +" FROM "+ getTable().getName() +" WHERE "+ columnName +" = ?";
        return findFirst(sql, value);
    }
    
    public M getByColumn(String columnName, Object value) {
        if (Util.isEmptyString(value))
            return null;
        return this.getByColumn(columnName, value, "*");
    }
    
    public List<M> listByColumn(Object columnName, Object value, String columns) {
        String sql = "SELECT "+ columns +" FROM "+ getTable().getName() +" WHERE "+ columnName +" = ?";
        return find(sql, value);
    }
    
    public List<M> listByColumn(Object columnName, Object value) {
        return this.listByColumn(columnName, value, "*");
    }
    
    public void update4DelById(Object id) {
        this.set(getTable().getPrimaryKey()[0], id)
            .set("deletetime", Util.getSqlTime(new Date()))
            .set("deleteuser", this.getUser().get("id")).update();
    }
    
    public void delByColumn(String column, Object value) {
        String sql = "DELETE FROM "+ getTable().getName() +" WHERE "+ column +" = ?";
        Db.update(sql, value);
    }
    
    public M updatePK(Object oldid, Object newid) {
        String pk  = getTable().getPrimaryKey()[0];
        String sql = "UPDATE "+ getTable().getName() +" SET "+ pk +" = ? WHERE "+ pk +" = ?";
        Db.update(sql, newid, oldid);
        return this.findById(newid);
    }
    
    /**
     * update4DelByIDs
     */
    public void update4DelByIds(String ids) {
        Table table      = getTable();
        String tablename = table.getName();
        String pk        = table.getPrimaryKey()[0];
        if (!Util.isEmptyString(ids)) {
            String[] idarr   = ids.split(",");
            Stack<Object> st = new Stack<Object>();
            st.push(this.getUser().get("id"));
            for (String s : idarr) {
                st.push(s);
            }
            String sql = "UPDATE "+ tablename +" SET deletetime = SYSDATETIME(), deleteuser = ? WHERE "+ pk +" IN ("+ Util.sqlHolder(idarr.length) +")";
            Db.update(sql, st.toArray());
        }
    }
    
    /**
     * 重写save方法
     */
    public boolean save() {
        //如果需要，下行代码设置主键值，主键为UUID
//        this.set(getTable().getPrimaryKey(), Util.getUUID()); // 设置主键值
        if (getTable().hasColumnLabel("version")) { // 是否需要乐观锁控制
            this.set("version", Long.valueOf(0)); // 初始化乐观锁版本号
        }
        return super.save();
    }
    
    /**
     * 重写update方法
     */
    @SuppressWarnings("unchecked")
    public boolean update() {
        Table table = getTable();
        String name = table.getName();
        String pk   = table.getPrimaryKey()[0];
        
        if (getTable().hasColumnLabel("version")) { // 是否需要乐观锁
            // 1.数据是否还存在
            String sql = new StringBuffer("select version from ").append(name)
                    .append(" where ").append(pk).append(" = ? ").toString();
            Model<M> modelOld = findFirst(sql, get(pk));
            if (null == modelOld) { // 数据已经被删除
                throw new RuntimeException("数据库中此数据不存在，可能数据已经被删除，请退出编辑刷新数据后在操作");
            }
            
            // 2.乐观锁控制
            Set<String> modifyFlag = null;
            try {
                Field field = this.getClass().getSuperclass().getSuperclass().getDeclaredField("modifyFlag");
                field.setAccessible(true);
                Object object = field.get(this);
                if (null != object) {
                    modifyFlag = (Set<String>) object;
                }
                field.setAccessible(false);
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
                throw new RuntimeException("业务Model类必须继承BaseModel");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("BaseModel访问modifyFlag异常");
            }
            boolean versionModify = modifyFlag.contains("version");
            if (versionModify && getTable().hasColumnLabel("version")) { // 是否需要乐观锁控制
                Long versionDB = modelOld.getLong("version") == null ? 0L : modelOld.getLong("version"); // 数据库中的版本号
                Long versionForm = Long.valueOf(get("version").toString()); // 表单中的版本号
                if (!(versionForm > versionDB)) {
                    throw new RuntimeException("该信息已经被其他用户修改，为了保证数据一致性，请退出编辑界面，重新编辑！");
                }
            }
        }
        
        return super.update();
    }
    
    /**
     * 获取表映射对象
     * 
     * @return
     */
    public Table getTable() {
        return TableMapping.me().getTable(getClass());
    }

}