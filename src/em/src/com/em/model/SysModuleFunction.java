package com.em.model;

import java.util.List;

import com.em.tools.Util;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

@TableBind(tableName="sys_module_function")
public class SysModuleFunction extends BaseModel<SysModuleFunction> {
    
public final static SysModuleFunction dao = new SysModuleFunction();
    
    public List<SysModuleFunction> list(String moduleid) {
        Object[] paras = new Object[] {moduleid};
        String sql = "SELECT * FROM sys_module_function WHERE deletetime IS NULL AND moduleid = ? ORDER BY sys_module_function.seq";
        return find(sql, paras);
    }
    
    public List<SysModuleFunction> list() {
        StringBuilder sql = new StringBuilder()
            .append("SELECT mf.id, mf.moduleid, m.shuoming AS moduleshuoming, mf.shuoming ")
            .append("FROM sys_module_function mf ")
            .append("INNER JOIN sys_module m ON mf.moduleid = m.moduleid ")
            .append("WHERE mf.deletetime IS NULL AND m.deletetime IS NULL ")
            .append("ORDER BY m.seq, mf.seq");
        
        return find(sql.toString());
    }
    
    public List<SysModuleFunction> listByRoleid(String roleid) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT mf.id, mf.moduleid, mf.name, mf.shuoming, m.name AS modulename, m.shuoming AS moduleshuoming, ")
            .append("STUFF((SELECT ',' + operationid FROM sys_module_function_operation WHERE modulefunctionid = mf.id AND roleid = ? FOR XML PATH('')), 1, 1, '') AS operationids ")
            //.append("(SELECT STRING_AGG(operationid, ',') AS operations FROM sys_module_function_operation WHERE modulefunctionid = mf.id AND roleid = ?) AS operationids ")
            .append("FROM sys_module_function mf ")
            .append("INNER JOIN sys_module m ON mf.moduleid = m.id ")
            .append("WHERE mf.deletetime IS NULL AND m.deletetime IS NULL ")
            .append("ORDER BY m.seq, mf.seq");
        
        return find(sql.toString(), roleid);
    }
    
    public List<SysModuleFunction> listByRoleid(String roleid, String moduleid) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT mf.id, mf.moduleid, mf.name, mf.shuoming, m.name AS modulename, m.shuoming AS moduleshuoming, ")
            .append("STUFF((SELECT ',' + operationid FROM sys_module_function_operation WHERE modulefunctionid = mf.id AND roleid = ? FOR XML PATH('')), 1, 1, '') AS operationids ")
            //.append("(SELECT STRING_AGG(operationid, ',') AS operations FROM sys_module_function_operation WHERE modulefunctionid = mf.id AND roleid = ?) AS operationids ")
            .append("FROM sys_module_function mf ")
            .append("INNER JOIN sys_module m ON mf.moduleid = m.id ")
            .append("WHERE mf.deletetime IS NULL AND m.deletetime IS NULL AND m.id = ? ")
            .append("ORDER BY m.seq, mf.seq");
        
        return find(sql.toString(), roleid, moduleid);
    }
    
    public List<SysModuleFunction> listByRoleids(Object[] roleids) {
        if (Util.isEmptyString(roleids)) return null;
        
        StringBuilder sql = new StringBuilder()
            .append("SELECT mf.id, mf.moduleid, mf.name, mf.shuoming, m.name AS modulename, m.shuoming AS moduleshuoming, ")
            .append("STUFF((SELECT ',' + sys_operation.name FROM sys_module_function_operation LEFT JOIN sys_operation ON operationid = sys_operation.id WHERE modulefunctionid = mf.id AND roleid IN ("+ Util.sqlHolder(roleids.length) +") FOR XML PATH('')), 1, 1, '') AS operationnames ")
            //.append("(SELECT STRING_AGG(sys_operation.name, ',') AS operationnames FROM sys_module_function_operation LEFT JOIN sys_operation ON operationid = sys_operation.id WHERE modulefunctionid = mf.id AND roleid IN ("+ Util.sqlHolder(roleids.length) +")) AS operationnames ")
            .append("FROM sys_module_function mf ")
            .append("INNER JOIN sys_module m ON mf.moduleid = m.id ")
            .append("WHERE mf.deletetime IS NULL AND m.deletetime IS NULL ")
            .append("ORDER BY m.seq, mf.seq");
        
        return find(sql.toString(), roleids);
    }
    
    public boolean isDel(String id) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT count(*) FROM sys_module_function_operation mfo ")
            .append("LEFT JOIN sys_module_function mf ON mf.id = mfo.modulefunctionid ")
            .append("WHERE mf.id = ? ");
        return Db.queryInt(sql.toString(), id) == 0 ? true : false;
    }
    
    public void delByModuleid(String moduleid) {
        String sql = "UPDATE sys_module_function SET deletetime=SYSDATETIME(), deleteuser=? WHERE moduleid=?";
        Db.update(sql, getUser().get("id"), moduleid);
    }
    
    public void delByModulefunctionid(String id) {
        String sql = "UPDATE sys_module_function SET deletetime=SYSDATETIME(), deleteuser=? WHERE id=?";
        Db.update(sql, getUser().get("id"), id);
    }
    
    public SysModuleFunction getBy(Object moduleid, Object name) {
        String sql = "SELECT * FROM sys_module_function WHERE moduleid = ? AND name = ? ";
        return findFirst(sql, moduleid, name);
    }
    
    public int getSeqBy(Object moduleid) {
        String sql = "SELECT seq FROM sys_module_function WHERE moduleid = ? ORDER BY seq DESC";
        return Db.queryInt(sql, moduleid);
    }
    
}
