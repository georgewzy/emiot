package com.em.controller.sys;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysMenu;
import com.em.model.SysModule;
import com.em.model.SysModuleFunction;
import com.em.model.SysModuleFunctionOperation;
import com.em.model.SysOperation;
import com.em.model.SysRole;
import com.em.model.SysRoleMenu;
import com.em.model.json.TreeMenu;
import com.em.model.json.TreeRole;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.em.tools.shiro.EmShiroRealm;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/role", viewPath="/pages/sys/role")
public class SysRoleController extends BaseController {

    @Override
    @RequiresPermissions("sys.role.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SysRole> list = SysRole.dao.list();
        setAttr("list", list);
        render("list.jsp");
    }
    
    @RequiresPermissions(value={"sys.role.add", "sys.role.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        String roles  = getPara("roles");
        
        /* save roles */
        List<TreeRole> list = JSONArray.parseArray(roles, TreeRole.class);
        SysRole role    = null;
        for (TreeRole t : list) {
            role = new SysRole();
            role
                .set("parentid", null)
                .set("name", t.getName())
                .set("seq", t.getOrder())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("sys.role.add")) {
                    throw new RuntimeException("对不起，你没有添加角色的权限！");
                }
                role.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("sys.role.edit")) {
                    throw new RuntimeException("对不起，你没有修改角色的权限！");
                }
                role.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) {
                this.saveChildren(role.getStr("id"), t.getChildren());
            }
        }
        
        render(BjuiRender.success());
    }
    
    // save children roles
    private void saveChildren(String parentid, List<TreeRole> chidren) {
        SysRole role = null;
        for (TreeRole t : chidren) {
            role = new SysRole();
            role
                .set("parentid", parentid)
                .set("name", t.getName())
                .set("seq", t.getOrder())
                .set("level", t.getLevel());
            if (Util.isEmptyString(t.getId())) {
                if (noPermissions("sys.role.add")) {
                    throw new RuntimeException("对不起，你没有添加角色的权限！");
                }
                role.set("id", Util.getUUID()).setCreateInfo().save();
            } else {
                if (noPermissions("sys.role.edit")) {
                    throw new RuntimeException("对不起，你没有修改角色的权限！");
                }
                role.set("id", t.getId()).setUpdateInfo().update();
            }
            if (t.getChildren() != null) this.saveChildren(role.getStr("id"), t.getChildren());
        }
    }
    
    //del
    @RequiresPermissions("sys.role.del")
    @Before(Tx.class)
    public void del() {
        String id    = getPara();
        SysRole role = SysRole.dao.findById(id);
        if (role == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        role.update4DelById(id);
        //del children
        SysRole.dao.delChildren(id);
        
        render(BjuiRender.success());
    }
    
    /**********************************/

    public void loadMenu() {
        String roleid          = getPara();
        List<SysMenu> menulist = SysMenu.dao.list();
        List<Object> menuids   = SysRoleMenu.dao.listMenuId(roleid);
        setAttr("menulist", menulist);
        setAttr("menuids", menuids);
        render("layout-menu.jsp");
    }
    
    public void loadModule() {
        String roleid                        = getPara();
        List<SysModuleFunction> functionList = SysModuleFunction.dao.listByRoleid(roleid);
        List<SysOperation> operationList     = SysOperation.dao.list();
        
        setAttr("roleid", roleid);
        setAttr("functionList", functionList);
        setAttr("operationList", operationList);
        render("layout-module.jsp");
    }
    
    // role - menu
    public void menu() {
        List<SysRole> list = SysRole.dao.list();
        setAttr("list", list);
        render("role-menu.jsp");
    }
    
    // role - save menu
    public void saveMenu() {
        String menus     = getPara("menus");
        String currentId = getPara("currentId");
        if (!Util.isEmptyString(currentId)) {
            SysRoleMenu.dao.delByColumn("roleid", currentId); // remove old
            List<TreeMenu> menulist = JSONArray.parseArray(menus, TreeMenu.class);
            for (TreeMenu t : menulist) {
                new SysRoleMenu()
                    .set("id", Util.getUUID())
                    .set("roleid", currentId)
                    .set("menuid", t.getId())
                    .save();
            }
        }
        render(BjuiRender.success());
    }
    
    // role - module
    public void module() {
        String roleid   = getPara("roleid");
        String moduleid = getPara("moduleid");
        List<SysRole> roleList     = SysRole.dao.list();
        List<SysModule> moduleList = SysModule.dao.list();
        
        if (!Util.isEmptyString(roleid) && !Util.isEmptyString(moduleid)) {
            List<SysModuleFunction> functionList = SysModuleFunction.dao.listByRoleid(roleid, moduleid);
            List<SysOperation> operationList = SysOperation.dao.list();
            setAttr("functionList", functionList);
            setAttr("operationList", operationList);
        }
        
        setAttr("roleid", roleid);
        setAttr("moduleid", moduleid);
        setAttr("roleList", roleList);
        setAttr("moduleList", moduleList);
        render("role-module.jsp");
    }
    
    // role - save module
    @Before(Tx.class)
    public void saveModule() {
        int function_count  = getParaToInt("function_count", 0);
        int operation_count = getParaToInt("operation_count", 0);
        String roleid       = getPara("roleid");
        
        if (!Util.isEmptyString(roleid)) {
            for (int i = 0; i < function_count; i++) {
                SysModuleFunction function = getModel(SysModuleFunction.class, "functionList["+ i +"]");
                SysModuleFunctionOperation.dao.del(function.getStr("id"), roleid);// remove old role modulefunction operation
                if (function != null && !Util.isEmptyString(function.get("id"))) {
                    for (int j = 0; j < operation_count; j++) {
                        SysOperation oper = getModel(SysOperation.class, "operationList["+ i +"]["+ j +"]");
                        if (oper != null && !Util.isEmptyString(oper.get("id"))) {
                            new SysModuleFunctionOperation()
                                .set("id", Util.getUUID())
                                .set("roleid", roleid)
                                .set("modulefunctionid", function.get("id"))
                                .set("operationid", oper.get("id"))
                                .save();
                        }
                    }
                }
            }
            /* 清除相关用户的权限缓存 */
            Collection<Realm> realms = ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms();
            EmShiroRealm realm       = null;
            if (realms != null && !realms.isEmpty()) {
                for (Realm r : realms) {
                    if (r.getName().equals("emRealm"))
                        realm = (EmShiroRealm) r;
                }
            }
            
            if (realm != null) {
                List<String> usernameList = SysRole.dao.listUsername(roleid);
                for (String name : usernameList) {
                    realm.clearCachedAuthorizationInfo(name);
                }
            }
        }
        
        render(BjuiRender.success());
    }
    
}
