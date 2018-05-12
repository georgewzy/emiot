package com.em.model.json;

import java.util.List;

public class TreeRole {

    private String id;
    private String name;
    private int level;
    private int order;
    private Integer parentid;
    private List<TreeRole> children;
    private List<TreeRole> list;
    
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
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public Integer getParentid() {
        return parentid;
    }
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    public List<TreeRole> getChildren() {
        return children;
    }
    public void setChildren(List<TreeRole> children) {
        this.children = children;
    }
    public List<TreeRole> getList() {
        return list;
    }
    public void setList(List<TreeRole> list) {
        this.list = list;
    }
    
}
