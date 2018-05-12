package com.em.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysArea;
import com.em.model.SysEmployee;
import com.em.model.SysRole;
import com.em.model.SysUsers;
import com.em.model.SysUsersArea;
import com.em.model.SysUsersRole;
import com.em.model.em.EmArea;
import com.em.tools.SHA256;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey = "/sys/user", viewPath = "/pages/sys/user")
public class SysUsersController extends BaseController {

    @Override
    @RequiresPermissions("sys.user.query")
    public void index() {
        // TODO Auto-generated method stub
        render("list.jsp");
    }
    
    @RequiresPermissions("sys.user.query")
    public void list() {
        String orderField   = this.getOrderBy();
        Integer pageNumber  = getParaToInt("pageCurrent", 1);
        Integer pageSize    = getParaToInt("pageSize", this.pageSize);
        SysUsers user       = getModel(SysUsers.class, "user");
        SysUsers getUser    = getUser();
        
        if (!getUser.getStr("id").equals("super") && !Util.isEmptyString(getUser.get("areaids"))) {
            user.put("areaids", getUser.get("areaids"));
        }
        
        Page<SysUsers> page = SysUsers.dao.paging(user, pageSize, pageNumber, orderField);
        renderJson(page);
    }
    
    @RequiresPermissions(value={"sys.user.add", "sys.user.edit"}, logical=Logical.OR)
    public void edit() {
        String id              = getPara();
        SysUsers user          = SysUsers.dao.findById(id);
        List<SysRole> rolelist = SysRole.dao.list();
        if (user != null) {
            List<Object> roleidlist = SysUsersRole.dao.listRoleId(user.getStr("id"));
            user.put("roleidlist", roleidlist);
            setAttr("areaids", SysUsersArea.dao.getAreaIds(id));
        }
        setAttr("user", user);
        setAttr("rolelist", rolelist);
        setAttr("areaList", SysArea.dao.list());
        render("edit.jsp");
    }
    
    @RequiresPermissions("sys.user.add")
    public void reg() {
        String id            = getPara();
        SysEmployee employee = SysEmployee.dao.findById(id);
        SysUsers user        = null;
        if (employee == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (!Util.isEmptyString(employee.get("userid"))) {
            user = SysUsers.dao.findById(employee.get("userid"));
            if (user != null) {
                render(BjuiRender.error("该员工已注册！"));
                return;
            }
        }
        user = new SysUsers();
        user.put("name", employee.get("name")).put("employeeid", id);
        List<SysRole> rolelist  = SysRole.dao.list();
        setAttr("rolelist", rolelist);
        setAttr("user", user);
        setAttr("areaids", SysUsersArea.dao.getAreaIds(id));
        setAttr("areaList", SysArea.dao.list());
        render("reg.jsp");
    }
    
    @RequiresPermissions(value={"sys.user.add", "sys.user.edit"}, logical=Logical.OR)
    @Before(Tx.class)
    public void save() {
        SysUsers user = getModel(SysUsers.class, "user");
        if (!Util.isEmptyString(user.get("password"))) {
            user.set("password", SHA256.hmacDigest(user.getStr("password"), user.getStr("username")));
        } else {
            user.remove("password");
        }
        /* save user */
        if (Util.isEmptyString(user.get("id"))) {
            SysUsers old = SysUsers.dao.getByColumn("username", user.get("username"));
            if (old != null) {
                render(BjuiRender.error("注册失败！已存在登陆账号为[ "+ user.get("username") +" ]的用户！"));
                return;
            }
            user
                .set("id", Util.getUUID())
                .set("enabled", true)
                .setCreateInfo()
                .save();
        } else {
            user
                .setUpdateInfo()
                .update();
            
            SysUsersRole.dao.delByColumn("userid", user.get("id")); // remove old role
            SysUsersArea.dao.delByColumn("userid", user.get("id")); // remove old area
        }
        /* save roles */
        String[] roles =  getParaValues("rolesid");
        if (!Util.isEmptyString(roles)) {
            for (String roleid : roles) {
                new SysUsersRole()
                    .set("id", Util.getUUID())
                    .set("userid", user.get("id"))
                    .set("roleid", roleid)
                    .save();
            }
        }
        /* save areas */
        /*String[] areaids = getParaValues("areaids");
        for (String s : areaids) {
            new SysUsersArea()
                .set("id", Util.getUUID())
                .set("userid", user.get("id"))
                .set("areaid", s)
                .save();
        }*/
        
        if (!Util.isEmptyString(user.get("isreg"))) {
            new SysEmployee().set("id", user.get("employeeid")).set("userid", user.get("id")).setUpdateInfo().update();
            render(BjuiRender.successAndCloseCurrentAndRefreshTab("sys-user-list"));
        } else {
            render(BjuiRender.successAndCloseCurrent());
        }
    }
    
    public void changePassword() {
        SysUsers user    = getModel(SysUsers.class, "user");
        SysUsers sysUser = getUser();
        if (!StringUtils.equals(user.getStr("oldpass"), sysUser.getStr("password"))) {
            render(BjuiRender.error("旧密码不正确！"));
            return;
        }
        user.update();
        render(BjuiRender.successAndCloseCurrent());
    }
    
    //重置密码
    public void resetPass() {
        SysUsers user = SysUsers.dao.getById(getPara());
        setAttr("user", user);
        render("resetpass.jsp");
    }
    
    public void savePass() {
        SysUsers user = getModel(SysUsers.class, "user");
        user.update();
        render(BjuiRender.successAndCloseCurrent());
    }
    
    //设置可操作管辖区域
    public void setArea() {
        String id    = getPara("id");
        String json  = getPara("json");
        String[] ids = new String[] {};
        List<SysUsers> userList = new ArrayList<SysUsers>();
        if (!Util.isEmptyString(id)) {
            SysUsers user = SysUsers.dao.getById(id);
            userList.add(user);
            ids = (String[]) ArrayUtils.add(ids, id);
            // 已添加的管辖区域
            setAttr("areaids", SysUsersArea.dao.getAreaIds(id));
        } else {
            JSONArray arr = JSONArray.parseArray(json);
            for (Object j : arr) {
                Map<String, String> map = (Map<String, String>) j;
                SysUsers user = new SysUsers();
                user.set("id", map.get("id"));
                user.put("name", map.get("name"));
                userList.add(user);
                ids = (String[]) ArrayUtils.add(ids, map.get("id"));
            }
        }
        
        setAttr("ids", StringUtils.join(ids, ","));
        setAttr("userList", userList);
        setAttr("areaList", EmArea.dao.list());
        //setAttr("areaList", SysArea.dao.list());
        render("user-setarea.jsp");
    }
    
    @Before(Tx.class)
    public void saveArea() {
        String ids     = getPara("ids");
        String areaids = getPara("areaids");
        
        if (Util.isEmptyString(areaids)) {
            for (String userid : ids.split(",")) {
                SysUsersArea.dao.delByColumn("userid", userid); //移除旧的管辖区域
            }
            render(BjuiRender.successAndCloseCurrent());
            return;
        }
        for (String userid : ids.split(",")) {
            SysUsersArea.dao.delByColumn("userid", userid); //移除旧的管辖区域
            
            //添加新的管辖区域
            for (String s : areaids.split(",")) {
                new SysUsersArea()
                    .set("id", Util.getUUID())
                    .set("userid", userid)
                    .set("areaid", s)
                    .save();
            }
        }
        
        render(BjuiRender.successAndCloseCurrentAndRefreshTab("sys-user-list"));
    }
    
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            SysUsers.dao.update4DelByIds(id);
            /* del role and emp -> userid */
            for (String s : id.split(",")) {
                SysUsersRole.dao.delByUserid(s);
                SysEmployee.dao.removeUserid(s);
            }
        } else {
            new SysUsers().update4DelById(id);
            /* del role */
            SysUsersRole.dao.delByUserid(id);
            /* del emp -> userid */
            SysEmployee.dao.removeUserid(id);
        }
        render(BjuiRender.success());
    }
    
}
