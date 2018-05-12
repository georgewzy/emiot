package com.em.model;

import java.util.ArrayList;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="sys_employee")
public class SysEmployee extends BaseModel<SysEmployee> {
    
    public final static SysEmployee dao = new SysEmployee();
    
    public Page<SysEmployee> paging(SysEmployee emp, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder()
            .append("FROM sys_employee emp ")
            .append("LEFT JOIN sys_users u ON emp.userid = u.id ")
            .append("WHERE emp.deletetime is null ");
        if (!Util.isEmptyString(emp.get("areaid"))) {
            paras.add(emp.get("areaid"));
            sqlExceptSelect.append(" AND emp.areaid = ?");
        }
        if (!Util.isEmptyString(emp.getStr("name"))) {
            paras.add("%"+ emp.getStr("name") +"%");
            sqlExceptSelect.append(" AND emp.name LIKE ? ");
        }
        if (!Util.isEmptyString(emp.getStr("mobile"))) {
            paras.add("%"+ emp.getStr("mobile") +"%");
            sqlExceptSelect.append(" AND emp.mobile LIKE ? ");
        }
        if (!Util.isEmptyString(emp.getStr("userid"))) {
            paras.add("%"+ emp.getStr("userid") +"%");
            sqlExceptSelect.append(" AND emp.userid LIKE ? ");
        }
        if (!Util.isEmptyString(emp.get("sex"))) {
            paras.add(emp.get("sex"));
            sqlExceptSelect.append(" AND emp.sex = ?");
        }
        if (!Util.isEmptyString(emp.get("username"))) {
            paras.add("%"+ emp.get("username") +"%");
            sqlExceptSelect.append(" AND u.username LIKE ? ");
        }
        // areaids
        if (!Util.isEmptyString(emp.get("areaids"))) {
            String[] arr = emp.getStr("areaids").split(",");
            for (String id : arr) {
                paras.add(id);
            }
            sqlExceptSelect.append(" AND emp.areaid IN ("+ Util.sqlHolder(arr.length) +") ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "emp.createtime DESC";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        String select = "SELECT emp.*, u.username";
        Page<SysEmployee> page = paginate(pageNumber, pageSize, select, sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    // remove userid
    public void removeUserid(String userid) {
        String sql = "UPDATE sys_employee SET userid = NULL WHERE userid = ?";
        Db.update(sql, userid);
    }
    
}
