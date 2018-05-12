package com.em.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="sys_area")
public class SysArea extends BaseModel<SysArea> {

    public static final SysArea dao = new SysArea();
    
    private String name = "name";
    
    public List<SysArea> list() {
        SysArea area = new SysArea();
        
        area.get(name);
        
        return find("SELECT * FROM sys_area WHERE deletetime IS NULL ORDER BY sys_area.seq");
    }
    
    public SysArea getBy(Object seq) {
        return findFirst("SELECT * FROM sys_area WHERE seq = ?", seq);
    }
    
    public Map<String, String> listMap() {
        List<SysArea> list      = find("SELECT * FROM sys_area WHERE deletetime IS NULL ORDER BY sys_area.seq");
        Map<String, String> map = new HashMap<String, String>();
        for (SysArea a : list) {
            map.put(a.getStr("id"), a.getStr("name"));
        }
        return map;
    }
    
    public List<SysArea> listByRange() {
        String userid  = getUser().getStr("id");
        String areaids = SysUsersArea.dao.getAreaIds(userid);
        if (Util.isEmptyString(areaids))
            return this.list();
        
        Object[] arr = areaids.split(",");
        return find("SELECT id, name FROM sys_area WHERE deletetime IS NULL AND areaid IN ("+ Util.sqlHolder(arr.length) +") ", arr);
    }
    
}
