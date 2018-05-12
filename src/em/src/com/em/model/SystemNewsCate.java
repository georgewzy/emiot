package com.em.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="system_news_cate")
public class SystemNewsCate extends BaseModel<SystemNewsCate> {
    
    public static final SystemNewsCate dao = new SystemNewsCate();
    
    public List<SystemNewsCate> list() {
        String sql = "SELECT *, id AS oldid FROM system_news_cate WHERE deletetime IS NULL";
        List<SystemNewsCate> list = find(sql);
        List<SystemNewsCate> newList = new ArrayList<SystemNewsCate>();
        // parent
        List<SystemNewsCate> parents = this.listSort(list, null);
        for (SystemNewsCate c : parents) {
            newList.add(c);
            newList.addAll(this.listSort(list, c.get("id")));
        }
        
        return newList;
    }
    
    private List<SystemNewsCate> listSort(List<SystemNewsCate> list, Object parentid) {
        List<SystemNewsCate> newList = new ArrayList<SystemNewsCate>();
        List<SystemNewsCate> removes = new ArrayList<SystemNewsCate>();
        for (SystemNewsCate e : list) {
            if (StringUtils.equals(String.valueOf(e.get("parentid")), String.valueOf(parentid))) {
                newList.add(e);
                removes.add(e);
            }
        }
        if (!newList.isEmpty()) list.removeAll(newList);
        return newList;
    }
    
    public List<SystemNewsCate> listParent() {
        String sql = "SELECT * FROM system_news_cate WHERE parentid IS NULL AND deletetime IS NULL";
        return find(sql);
    }
    
    // upload child
    public void uploadChild(Object newpid, Object oldpid) {
        String sql = "UPDATE system_news_cate SET parentid = ? WHERE parentid = ?";
        Db.update(sql, newpid, oldpid);
    }
    
    // upload news
    public void uploadNews(Object newpid, Object oldpid) {
        String sql = "UPDATE system_news SET cateid = ? WHERE cateid = ?";
        Db.update(sql, newpid, oldpid);
    }
    
}
