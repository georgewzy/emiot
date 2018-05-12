package com.em.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_users_area")
public class SysUsersArea extends BaseModel<SysUsersArea> {

    public static final SysUsersArea dao = new SysUsersArea();
    
    public String getAreaIds(String userid) {
        String sql = "SELECT STUFF((SELECT ',' + areaid FROM sys_users_area WHERE userid = ? FOR XML PATH('')), 1, 1, '') AS areaids ";
        return Db.queryStr(sql, userid);
    }
    
}
