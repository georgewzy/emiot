package com.em.model;

import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="sys_role_menu")
public class SysRoleMenu extends BaseModel<SysRoleMenu> {

    public final static SysRoleMenu dao = new SysRoleMenu();
    
    // list only roleid
    public List<Object> listMenuId(String roleid) {
        String sql = "SELECT menuid FROM sys_role_menu WHERE roleid=?";
        List<SysRoleMenu> list = find(sql, roleid);
        return conver2Object(list, "menuid");
    }
    
    // list only roleid
    public List<Object> listMenuIdByRoleids(Object ... roleids) {
        String sql = "SELECT menuid FROM sys_role_menu WHERE roleid IN ("+ Util.sqlHolder(roleids.length) +")";
        List<SysRoleMenu> list = find(sql, roleids);
        return conver2Object(list, "menuid");
    }
    
}
