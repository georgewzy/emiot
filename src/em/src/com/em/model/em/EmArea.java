package com.em.model.em;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.em.model.BaseModel;
import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="em_area")
public class EmArea extends BaseModel<EmArea> {
    
    public final static EmArea dao = new EmArea();
    
    public List<EmArea> list() {
        String sql = "SELECT * FROM em_area m WHERE deletetime IS NULL ORDER BY level, m.seq";
        return find(sql);
    }
    
    public List<EmArea> list4Select() {
        List<EmArea> list = this.list();
        List<EmArea> parentList = this.listChildren(list, null);
        for (EmArea e : parentList) {
            List<EmArea> children = this.listChildren(list, e.getStr("id"));
            e.put("children", children);
            for (EmArea ee : children) {
                ee.put("children", this.listChildren(list, ee.getStr("id")));
            }
        }
        return parentList;
    }
    
    private List<EmArea> listChildren(List<EmArea> oList, String parentid) {
        List<EmArea> newList    = new ArrayList<EmArea>();
        List<EmArea> removeList = new ArrayList<EmArea>();
        for (EmArea m : oList) {
            if (StringUtils.equals(m.getStr("parentid"), parentid)) {
                newList.add(m);
                removeList.add(m);
            }
        }
        if (!removeList.isEmpty()) oList.removeAll(removeList);
        
        return newList;
    }
    
    // get childrenids by parentid
    public String getChildrenidsByPid(String parentid) {
        StringBuffer sql = new StringBuffer()
            .append("WITH children(id, parentid) ")
            .append("AS ( ")
            .append("SELECT id, parentid FROM em_area aaa WHERE ISNULL(parentid, '') = ? ")
            .append("UNION ALL ")
            .append("SELECT t.id, t.parentid FROM em_area AS t ")
            .append("INNER JOIN children ON ISNULL(t.parentid, '') = children.id ")
            .append(") ")
            .append("SELECT STUFF((SELECT ',' + children.id FROM children WHERE ISNULL(children.parentid, '') != '' FOR XML PATH('')), 1, 1, '') AS ids ");
        return Db.queryStr(sql.toString(), parentid);
    }
    
    // list group by province
    public List<EmArea> listGroupProvince() {
        String sql = "SELECT province FROM em_area WHERE deletetime IS NULL AND ISNULL(province, '') != '' GROUP BY province ";
        return find(sql);
    }
    
    // get childrenids by province
    public String getChildrenids(String province) {
        StringBuffer sql = new StringBuffer()
            .append("WITH children(id, parentid) ")
            .append("AS ( ")
            .append("SELECT id, parentid FROM em_area aaa WHERE ISNULL(province, '') = ? ")
            .append("UNION ALL ")
            .append("SELECT t.id, t.parentid FROM em_area AS t ")
            .append("INNER JOIN children ON ISNULL(t.parentid, '') = children.id ")
            .append(") ")
            .append("SELECT STUFF((SELECT ',' + children.id FROM children WHERE ISNULL(children.parentid, '') != '' FOR XML PATH('')), 1, 1, '') AS ids ");
        return Db.queryStr(sql.toString(), province);
    }
    
    // update for delete children by parentid
    public void delChildren(String parentid) {
        String sql = getRecursionSql("id", "id", "parentid") + "UPDATE em_area set deletetime = SYSDATETIME(), deleteuser = ? WHERE id IN (SELECT id FROM children)";
        Db.update(sql.toString(), parentid, getUser().get("id"));
    }
    
    // list by role_areaid
    public List<EmArea> listByRoleids(Object userid, String parentid) {
        Object[] paras = new Object[] {};
        paras = ArrayUtils.add(paras, userid);
        StringBuilder sql = new StringBuilder();
        sql
            .append("SELECT DISTINCT area.* ")
            .append("FROM em_area area ")
            .append("LEFT JOIN sys_users_area rm ON area.id = rm.areaid ")
            .append("WHERE area.deletetime IS NULL AND rm.userid = ? ");
        if (parentid != null) {
            paras = ArrayUtils.add(paras, parentid);
            sql.append("AND area.parentid = ? ");
        } else {
            sql.append("AND area.parentid IS NULL ");
        }
        sql.append("ORDER BY area.level, area.seq");
        return find(sql.toString(), paras);
    }
    
    // get Father
    public EmArea getFather(Object areaid) {
        StringBuilder sql = new StringBuilder()
            .append("WITH children(id, parentid, province) ")
            .append("AS ( ")
            .append("SELECT id, parentid, province FROM em_area aaa WHERE id = ? ")
            .append("UNION ALL ")
            .append("SELECT t.id, t.parentid, t.province FROM em_area AS t ")
            .append("INNER JOIN children ON ISNULL(children.parentid, '') = t.id ")
            .append(") ")
            .append("SELECT id, province FROM children WHERE parentid IS NULL");
        
        return findFirst(sql.toString(), areaid);
    }
    
}
