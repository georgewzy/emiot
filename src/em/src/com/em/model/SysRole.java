package com.em.model;

import java.util.ArrayList;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_role")
public class SysRole extends BaseModel<SysRole> {
    
    public final static SysRole dao = new SysRole();
    
    public List<SysRole> list() {
        String sql = "SELECT * FROM sys_role WHERE deletetime IS NULL ORDER BY level, sys_role.seq";
        return find(sql);
    }
    
    public List<SysRole> list(Object[] roleids) {
        String sql = "SELECT * FROM sys_role WHERE id IN ("+ Util.sqlHolder(roleids.length) +") AND deletetime IS NULL";
        return find(sql, roleids);
    }
    
    // list children by parentid
    public List<SysRole> listChildren(int parentid) {
        //Object[] paras = new Object[] {parentid};
        //String sql = sql_recursion.toString() + "select roleid, parentid, rolename from children";
        //r/eturn find(sql.toString(), paras);
        return null;
    }
    
    // update for delete children by parentid
    public void delChildren(String parentid) {
        String sql = getRecursionSql("id", "id", "parentid") + "UPDATE sys_role set deletetime = SYSDATETIME(), deleteuser = ? WHERE id IN (SELECT id FROM children)";
        Db.update(sql.toString(), parentid, getUser().get("id"));
    }
    
    // list roleid by rolenames
    public List<Integer> listIds(Object[] rolenames) {
        String sql = "SELECT roleid FROM sys_role WHERE rolename IN ("+ Util.sqlHolder(rolenames.length) +")";
        List<SysRole> roleList = find(sql, rolenames);
        if (roleList.size() == 0) return null;
        List<Integer> roleids  = new ArrayList<Integer>();
        for (SysRole r : roleList) {
            roleids.add(r.getInt("id"));
        }
        return roleids;
    }
    
    // list username by roleid
    public List<String> listUsername(Object roleid) {
        String sql = "SELECT u.username FROM sys_users_role ur LEFT JOIN sys_users u ON ur.userid = u.id WHERE ur.roleid = ? ";
        return Db.query(sql, roleid);
    }
    
}
