package com.em.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_users_role")
public class SysUsersRole extends BaseModel<SysUsersRole> {

    public final static SysUsersRole dao = new SysUsersRole();
    
    // list only roleid
    public List<Object> listRoleId(String userid) {
        Object[] paras = new Object[] {userid};
        String sql = "SELECT roleid FROM sys_users_role WHERE userid = ?";
        List<SysUsersRole> list = find(sql, paras);
        return conver2Object(list, "roleid");
    }
    
    // del by userid
    public void delByUserid(String userid) {
        String sql = "DELETE FROM sys_users_role WHERE userid = ?";
        Db.update(sql, userid);
    }
    
}
