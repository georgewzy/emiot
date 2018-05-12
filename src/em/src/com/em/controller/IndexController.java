package com.em.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.em.model.SysMenu;
import com.em.model.SysRole;
import com.em.model.SysUsersRole;
import com.em.model.SysUsers;
import com.em.model.SystemNews;
import com.em.model.em.EmArea;
import com.em.model.em.EmInfo;
import com.em.tools.Util;
import com.em.tools.tag.MyFunctionTag;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;

@ControllerBind(controllerKey="/sys/", viewPath="/pages")
public class IndexController extends BaseController {

    @Override
    public void index() {
        // TODO Auto-generated method stub
        SysUsers user = getUser();
        if (user == null) user = new SysUsers();
        List<Object> roleids = SysUsersRole.dao.listRoleId(user.getStr("id"));
        if (!Util.isEmptyString(roleids)) {
            List<SysMenu> menuList = SysMenu.dao.listByRoleids(roleids.toArray(), null);
            if (menuList != null) {
                for (SysMenu m : menuList) {
                    List<SysMenu> children = SysMenu.dao.listChildrenByRoleids(roleids.toArray(), m.getStr("id"));
                    m.put("children", children);
                }
            }
            List<SysRole> roleList = SysRole.dao.list(roleids.toArray());
            setAttr("menuList", menuList);
            setAttr("roleList", roleList);
        }
        
        // 设备归属
        List<EmArea> areaList = EmArea.dao.list();
        // 设备列表
        List<EmInfo> emList = EmInfo.dao.list(new EmInfo());
        // 将设备设置到相应的归属
        for (EmArea a : areaList) {
            List<EmInfo> list = this.listEm(emList, a.getStr("id"));
            if (!user.getStr("id").equals("super")) {
                if (MyFunctionTag.isContainObject(user.getStr("areaids"), a.get("id"))) {
                    if (list != null && !list.isEmpty()) {
                        a.put("emList", list);
                    }
                }
            } else {
                a.put("emList", list);
            }
        }
        
//        List<EmType> typeList = EmType.dao.list();
//        for (EmType t : typeList) {
//            EmInfo em = new EmInfo();
//            //em.put("areaids", user.get("areaids"));
//            em.set("typeid", t.get("id"));
//            List<EmInfo> emList = EmInfo.dao.list(em);
//            t.put("emList", emList);
//        }
        
        //setAttr("typeList", typeList);
        setAttr("areaList", areaList);
        setAttr("user", user);
    }
    
    private List<EmInfo> listEm(List<EmInfo> emList, String areaid) {
        List<EmInfo> newList = new ArrayList<EmInfo>();
        List<EmInfo> removes = new ArrayList<EmInfo>();
        for (EmInfo e : emList) {
            if (StringUtils.equals(e.getStr("areaid"), areaid)) {
                newList.add(e);
                removes.add(e);
            }
        }
        //if (!removes.isEmpty()) emList.removeAll(removes);
        if (!newList.isEmpty()) emList.removeAll(newList);
        return newList;
    }
    
    /*private List<SysMenu> listMenu(List<SysMenu> oList, String parentid) {
        List<SysMenu> newList    = new ArrayList<SysMenu>();
        List<SysMenu> removeList = new ArrayList<SysMenu>();
        for (SysMenu m : oList) {
            if (StringUtils.equals(m.getStr("parentid"), parentid)) {
                newList.add(m);
                removeList.add(m);
            }
        }
        if (!removeList.isEmpty()) oList.removeAll(removeList);
        
        return newList;
    }*/
    
    @ActionKey("/sys/main")
    public void main() {
        List<EmInfo> list = EmInfo.dao.list(new EmInfo());
        List<SystemNews> newsList = SystemNews.dao.list4Home(10);
        setAttr("list", JsonKit.toJson(list));
        setAttr("newsList", newsList);
        render("index_main.jsp");
    }
    
    @ActionKey("/sys/changepass")
    public void changepass() {
        setAttr("user", getUser());
        render("userchangepassword.jsp");
    }
    
}
