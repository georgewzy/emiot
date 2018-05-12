package com.em.model;

import java.util.ArrayList;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="sys_users")
public class SysUsers extends BaseModel<SysUsers> {
    
    public final static SysUsers dao = new SysUsers();
    
    public Page<SysUsers> paging(SysUsers user, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder()
//            .append("FROM (")
//            .append("SELECT us.id, us.username, us.enabled, emp.name, us.createtime, ")
//            .append("MIN(rr.level) AS level, MIN(rr.seq) AS seq, ")
//            .append("STUFF((SELECT ',' + sys_role.name FROM sys_role LEFT JOIN sys_users_role ON sys_role.id = sys_users_role.roleid WHERE sys_users_role.userid = us.id FOR XML PATH('')), 1, 1, '') AS rolenames, ")
//            .append("STUFF((SELECT ',' + sys_area.name FROM sys_area LEFT JOIN sys_users_area ON sys_area.id = sys_users_area.areaid WHERE sys_users_area.userid = us.id FOR XML PATH('')), 1, 1, '') AS areanames ")
            .append("FROM sys_users us ")
            .append("LEFT JOIN sys_employee emp ON us.id = emp.userid ")
            .append("LEFT JOIN sys_users_role ur ON ur.userid = us.id ")
            .append("LEFT JOIN sys_role rr ON ur.roleid = rr.id ")
            .append("WHERE us.deletetime IS NULL ");
        if (!Util.isEmptyString(user.getStr("username"))) {
            paras.add("%"+ user.getStr("username") +"%");
            sqlExceptSelect.append(" AND us.username LIKE ? ");
        }
        if (!Util.isEmptyString(user.getStr("name"))) {
            paras.add("%"+ user.getStr("name") +"%");
            sqlExceptSelect.append(" AND emp.name LIKE ? ");
        }
        if (!Util.isEmptyString(user.get("rolenames"))) {
            paras.add("%"+ user.get("rolenames") +"%");
            sqlExceptSelect.append(" AND rolenames LIKE ? ");
        }
        if (!Util.isEmptyString(user.get("areanames"))) {
            paras.add("%"+ user.get("areanames") +"%");
            sqlExceptSelect.append(" AND areanames LIKE ? ");
        }
        
        // areaids
        if (!Util.isEmptyString(user.get("areaids"))) {
            String[] arr = user.getStr("areaids").split(",");
            for (String id : arr) {
                paras.add(id);
            }
            sqlExceptSelect.append(" AND emp.areaid IN ("+ Util.sqlHolder(arr.length) +") ");
        }
        
        sqlExceptSelect.append("GROUP BY us.id, us.username, us.enabled, emp.name, us.createtime ");
        
        
        if (Util.isEmptyString(orderField)) orderField = "level,  createtime DESC";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        StringBuilder select = new StringBuilder()
            .append("SELECT us.id, us.username, us.enabled, emp.name, us.createtime, ")
            .append("MIN(rr.level) AS level, MIN(rr.seq) AS seq, ")
            .append("STUFF((SELECT ',' + sys_role.name FROM sys_role LEFT JOIN sys_users_role ON sys_role.id = sys_users_role.roleid WHERE sys_users_role.userid = us.id FOR XML PATH('')), 1, 1, '') AS rolenames, ")
            .append("STUFF((SELECT ',' + em_area.name FROM em_area LEFT JOIN sys_users_area ON em_area.id = sys_users_area.areaid WHERE sys_users_area.userid = us.id ORDER BY em_area.level, em_area.seq FOR XML PATH('')), 1, 1, '') AS areanames ");
        Page<SysUsers> page = paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    public SysUsers getUser(String username) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT u.id, u.username, u.password, emp.name, emp.id AS empid ")
            .append("FROM sys_users u ")
            .append("LEFT JOIN sys_employee emp ON u.id = emp.userid ")
            .append("WHERE u.username = ? OR emp.mobile = ? ");
        return findFirst(sql.toString(), username, username);
    }
    
    public SysUsers getById(String userid) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT u.id, u.username, u.password, emp.name, emp.id AS empid ")
            .append("FROM sys_users u ")
            .append("LEFT JOIN sys_employee emp ON u.id = emp.userid ")
            .append("WHERE u.id = ? ");
        return findFirst(sql.toString(), userid);
    }
    
    public void delByEmployeeid(String employeeid) {
        String sql = "UPDATE sys_users SET deletetime = SYSDATETIME(), deleteuser = ? WHERE id IN (SELECT userid FROM sys_employee WHERE id = ?)";
        Db.update(sql, getUser().get("id"), employeeid);
    }
    
}
