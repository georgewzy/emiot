package com.em.model;

import java.util.ArrayList;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_module")
public class SysModule extends BaseModel<SysModule> {
    
public final static SysModule dao = new SysModule();
    
    public List<SysModule> list(SysModule module) {
        List<Object> paras = new ArrayList<Object>();
        StringBuilder sql  = new StringBuilder()
            .append("SELECT m.id, m.name, m.shuoming, m.seq ")
            .append("FROM sys_module m ")
            .append("WHERE m.deletetime IS NULL ");
        if (!Util.isEmptyString(module.getStr("name"))) {
            paras.add("%" + module.getStr("name") +"%");
            sql.append(" AND m.name LIKE ? ");
        }
        if (!Util.isEmptyString(module.getStr("shuoming"))) {
            paras.add("%" + module.getStr("shuoming") +"%");
            sql.append(" AND m.shuoming LIKE ? ");
        }
        sql.append("ORDER BY m.seq");
        return find(sql.toString(), paras.toArray());
    }
    
    public List<SysModule> list() {
        return find("SELECT id, name, shuoming FROM sys_module WHERE deletetime IS NULL ORDER BY sys_module.seq");
    }
    
    public boolean isDel(String moduleid) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT count(*) FROM sys_module_function_operation rmo ")
            .append("LEFT JOIN sys_module_function mf ON mf.id = rmo.modulefunctionid ")
            .append("LEFT JOIN sys_module m ON m.id = mf.moduleid ")
            .append("WHERE m.id=? ");
        return Db.queryInt(sql.toString(), moduleid) == 0 ? true : false;
    }
}
