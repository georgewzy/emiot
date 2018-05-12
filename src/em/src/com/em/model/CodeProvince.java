package com.em.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;

@TableBind(tableName="code_province")
public class CodeProvince extends BaseModel<CodeProvince> {
    
    public static final CodeProvince dao = new CodeProvince();
    
    public List<CodeProvince> list() {
        return find("SELECT * FROM code_province ORDER BY id ");
    }
    
}
