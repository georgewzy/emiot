package com.em.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="system_operation_log")
public class SystemOperationLog extends BaseModel<SystemOperationLog> {
    
    public static final SystemOperationLog dao = new SystemOperationLog();
    
    public Page<SystemOperationLog> paging(SystemOperationLog log, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder()
            .append("FROM system_operation_log log ")
            .append("WHERE 1 = 1 ");
        if (!Util.isEmptyString(log.getStr("type"))) {
            paras.add(log.getStr("type"));
            sqlExceptSelect.append(" AND log.type = ? ");
        }
        if (!Util.isEmptyString(log.getStr("name"))) {
            paras.add("%"+ log.getStr("name") +"%");
            sqlExceptSelect.append(" AND log.name LIKE ? ");
        }
        if (!Util.isEmptyString(log.getStr("beizhu"))) {
            paras.add("%"+ log.getStr("beizhu") +"%");
            sqlExceptSelect.append(" AND log.beizhu LIKE ? ");
        }
        if (!Util.isEmptyString(log.getStr("username"))) {
            paras.add("%"+ log.getStr("username") +"%");
            sqlExceptSelect.append(" AND log.username LIKE ? ");
        }
        if (!Util.isEmptyString(log.getStr("employeename"))) {
            paras.add("%"+ log.getStr("employeename") +"%");
            sqlExceptSelect.append(" AND log.employeename LIKE ? ");
        }
        if (!Util.isEmptyString(log.get("eminfoid"))) {
            paras.add(log.get("eminfoid"));
            sqlExceptSelect.append(" AND log.eminfoid = ? ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "createtime DESC";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        String select = "SELECT * ";
        Page<SystemOperationLog> page = paginate(pageNumber, pageSize, select, sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    public void createLog(String type, String name, String beizhu) {
        SysUsers user = getUser();
        new SystemOperationLog()
            .set("id", Util.getUUID())
            .set("type", type)
            .set("name", name)
            .set("beizhu", beizhu)
            .set("username", user.get("username"))
            .set("employeename", user.get("name"))
            .set("createtime", Util.getSqlTime(new Date()))
            .save();
    }
    
    public void createLog(String type, String name, String beizhu, Object eminfoid) {
        SysUsers user = getUser();
        new SystemOperationLog()
            .set("id", Util.getUUID())
            .set("type", type)
            .set("name", name)
            .set("beizhu", beizhu)
            .set("username", user.get("username"))
            .set("employeename", user.get("name"))
            .set("eminfoid", eminfoid)
            .set("createtime", Util.getSqlTime(new Date()))
            .save();
    }
    
}
