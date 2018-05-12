package com.em.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="sys_operation")
public class SysOperation extends BaseModel<SysOperation> {
    
public final static SysOperation dao = new SysOperation();
    
    public List<SysOperation> list() {
        return find("SELECT * FROM sys_operation WHERE deletetime IS NULL ORDER BY sys_operation.seq");
    }
}
