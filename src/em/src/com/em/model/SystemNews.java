package com.em.model;

import java.util.ArrayList;
import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Page;

@TableBind(tableName="system_news")
public class SystemNews extends BaseModel<SystemNews> {
    
    public static final SystemNews dao = new SystemNews();
    
    public Page<SystemNews> paging(SystemNews news, int pageSize, int pageNumber, String orderField) {
        List<Object> paras            = new ArrayList<Object>();
        StringBuilder sqlExceptSelect = new StringBuilder()
            .append("FROM system_news news ")
            .append("LEFT JOIN sys_users u ON news.createuser = u.id ")
            .append("LEFT JOIN sys_employee emp ON u.id = emp.userid ")
            .append("WHERE news.deletetime IS NULL ");
        if (!Util.isEmptyString(news.get("cateid"))) {
            paras.add(news.get("cateid"));
            sqlExceptSelect.append(" AND news.cateid = ?");
        }
        if (!Util.isEmptyString(news.getStr("title"))) {
            paras.add("%"+ news.getStr("title") +"%");
            sqlExceptSelect.append(" AND news.title LIKE ? ");
        }
        if (!Util.isEmptyString(news.getStr("empname"))) {
            paras.add("%"+ news.getStr("empname") +"%");
            sqlExceptSelect.append(" AND emp.name LIKE ? ");
        }
        if (!Util.isEmptyString(news.get("istop"))) {
            paras.add(news.get("istop"));
            sqlExceptSelect.append(" AND news.istop = ?");
        }
        if (!Util.isEmptyString(news.get("ishome"))) {
            paras.add(news.get("ishome"));
            sqlExceptSelect.append(" AND news.ishome = ?");
        }
        if (!Util.isEmptyString(news.getStr("createtime1"))) {
            paras.add("%"+ news.getStr("createtime1") +"%");
            sqlExceptSelect.append(" AND CONVERT(VARCHAR(19), news.createtime, 21) LIKE ? ");
        }
        
        // cateids
        if (!Util.isEmptyString(news.get("cateids"))) {
            List<Object> cateids = news.get("cateids");
            paras.addAll(cateids);
            sqlExceptSelect.append(" AND news.cateid IN ("+ Util.sqlHolder(cateids.size()) +") ");
        }
        
        if (Util.isEmptyString(orderField)) orderField = "news.istop DESC, news.createtime DESC";
        sqlExceptSelect.append(" ORDER BY "+ orderField);
        String select = "SELECT news.*, news.createtime AS createtime1, emp.name AS empname";
        Page<SystemNews> page = paginate(pageNumber, pageSize, select, sqlExceptSelect.toString(), paras.toArray());
        return page;
    }
    
    public List<SystemNews> list4Home(int num) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT TOP "+ num +" ")
            .append("news.*, emp.name AS empname ")
            .append("FROM system_news news ")
            .append("LEFT JOIN sys_users u ON news.createuser = u.id ")
            .append("LEFT JOIN sys_employee emp ON u.id = emp.userid ")
            .append("WHERE news.deletetime IS NULL AND news.ishome = 1 ")
            .append("ORDER BY news.istop DESC, news.createtime DESC ");
        
        return find(sql.toString());
    }
    
}
