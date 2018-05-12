package com.em.model.json;

import java.util.List;

public class TreeMenu {
    
    private String id;
    private String name;
    private String parentid;
    private String url;
    private String target;
    private String icon;
    private int order;
    private int level;
    private String targetid;
    private String province;
    private List<TreeMenu> children;
    private List<TreeMenu> list;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentid() {
        return parentid;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getTargetid() {
        return targetid;
    }
    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }
    public List<TreeMenu> getChildren() {
        return children;
    }
    public void setChildren(List<TreeMenu> children) {
        this.children = children;
    }
    public List<TreeMenu> getList() {
        return list;
    }
    public void setList(List<TreeMenu> list) {
        this.list = list;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    
}
