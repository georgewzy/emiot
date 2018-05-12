package com.em.model;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_menu")
public class SysMenu extends BaseModel<SysMenu> {
    
    public final static SysMenu dao = new SysMenu();
    
    public List<SysMenu> list() {
        String sql = "SELECT * FROM sys_menu m WHERE deletetime IS NULL ORDER BY level, m.seq";
        return find(sql);
    }
    
    // update for delete children by parentid
    public void delChildren(String parentid) {
        String sql = getRecursionSql("id", "id", "parentid") + "UPDATE sys_menu set deletetime = SYSDATETIME(), deleteuser = ? WHERE id IN (SELECT id FROM children)";
        Db.update(sql.toString(), parentid, getUser().get("id"));
    }
    
    // list by roleids
    public List<SysMenu> listByRoleids(Object[] roleids, String parentid) {
        Object[] paras = new Object[] {};
        paras = ArrayUtils.addAll(paras, roleids);
        StringBuilder sql = new StringBuilder();
        sql
            .append("SELECT DISTINCT menu.* ")
            .append("FROM sys_menu menu ")
            .append("LEFT JOIN sys_role_menu rm ON menu.id = rm.menuid ")
            .append("WHERE menu.deletetime IS NULL AND rm.roleid IN ("+ Util.sqlHolder(roleids.length) +") ");
        if (parentid != null) {
            paras = ArrayUtils.add(paras, parentid);
            sql.append("AND menu.parentid = ? ");
        } else {
            sql.append("AND menu.parentid IS NULL ");
        }
        sql.append("ORDER BY menu.level, menu.seq");
        return find(sql.toString(), paras);
    }
    
    // list children by roleids
    public List<SysMenu> listChildrenByRoleids(Object[] roleids, String parentid) {
        Object[] paras = new Object[] {parentid};
        paras = ArrayUtils.addAll(paras, roleids);
        StringBuilder sql = new StringBuilder(" SELECT DISTINCT children.* FROM children LEFT JOIN sys_role_menu rm ON children.id = rm.menuid WHERE rm.roleid IN ("+ Util.sqlHolder(roleids.length) +") ");
        if (parentid != null) {
            paras = ArrayUtils.add(paras, parentid);
            sql.append("AND children.id != ? ");
        } else {
            sql.append("AND children.parentid IS NOT NULL ");
        }
        sql.append("ORDER BY children.level, children.seq");
        return find(getRecursionSql("id, parentid, name, url, target, icon, seq, level, targetid", "id", "parentid") + sql.toString(), paras);
    }
}
