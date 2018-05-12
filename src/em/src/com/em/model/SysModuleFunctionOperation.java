package com.em.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_module_function_operation")
public class SysModuleFunctionOperation extends BaseModel<SysModuleFunctionOperation> {
    
public final static SysModuleFunctionOperation dao = new SysModuleFunctionOperation();
    
    // delete by modulefunctionid && roleid
    public void del(String modulefunctionid, String roleid) {
        Object[] paras = new Object[] {modulefunctionid, roleid};
        Db.update("DELETE FROM sys_module_function_operation WHERE modulefunctionid=? AND roleid=?", paras);
    }
    
    public void delByModuleid(String moduleid) {
        String sql = "DELETE sys_module_function_operation WHERE modulefunctionid IN (SELECT id FROM sys_module_function WHERE moduleid=?)";
        Db.update(sql, moduleid);
    }
    
    public void delByModulefunctionid(String modulefunctionid) {
        String sql = "DELETE sys_module_function_operation WHERE modulefunctionid = ?";
        Db.update(sql, modulefunctionid);
    }
    
}
