package com.em.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.SysModuleFunction;
import com.em.model.SystemNewsCate;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/systemnewscate", viewPath="/pages/sys/newscate")
public class SystemNewsCateController extends BaseController {

    @Override
    @RequiresPermissions("news.newscate.query")
    public void index() {
        // TODO Auto-generated method stub
        List<SystemNewsCate> list = SystemNewsCate.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        setAttr("parentList", SystemNewsCate.dao.listParent());
        render("list.jsp");
    }
    
    @RequiresPermissions(value = {"news.newscate.add", "news.newscate.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<SystemNewsCate> list = new ArrayList<SystemNewsCate>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            SystemNewsCate old = SystemNewsCate.dao.findById(map.get("oldid"));
            SystemNewsCate obj = new SystemNewsCate().setAttrs(map).put("oldid", map.get("oldid"));
            if (old == null) {
                old = SystemNewsCate.dao.findById(map.get("id"));
                if (old != null) {
                    if (old.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的文档类别已存在数据库中！");
                    else
                        old.delete();
                }
                // 存到授权模块
                // bdc0190cb7a64e34a0d74a1911914209 = 在线文档模块ID
                Object moduleid     = "bdc0190cb7a64e34a0d74a1911914209";
                SysModuleFunction f = SysModuleFunction.dao.getBy(moduleid, obj.get("modulename"));
                int maxSeq          = SysModuleFunction.dao.getSeqBy(moduleid);
                if (f != null) {
                    if (f.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的模块名称已存在数据库中！");
                    else
                        f.delete();
                }
                
                f = new SysModuleFunction();
                f.set("id", Util.getUUID()).set("moduleid", moduleid).set("name", obj.get("modulename")).set("shuoming", "文档类别-"+ obj.get("name")).set("seq", maxSeq + 1).setCreateInfo().save(); // 保存权限模块
                obj.setCreateInfo().set("moduleid", f.get("id")).save(); // 保存类别
            } else {
                // 更新授权模块
                // bdc0190cb7a64e34a0d74a1911914209 = 在线文档模块ID
                Object moduleid     = "bdc0190cb7a64e34a0d74a1911914209";
                SysModuleFunction f = SysModuleFunction.dao.findById(obj.get("moduleid"));
                if (f != null) {
                    if (!f.get("name").equals(obj.get("modulename"))) {
                        SysModuleFunction oldM = SysModuleFunction.dao.getBy(moduleid, obj.get("modulename"));
                        if (oldM != null) {
                            if (oldM.get("deletetime") == null)
                                throw new RuntimeException("更新失败，序号为 ["+ obj.get("id") +"] 的模块名称已存在数据库中！");
                            else
                                oldM.delete();
                        }
                        f.set("name", obj.get("modulename")).set("shuoming", "文档类别-"+ obj.get("name")).setUpdateInfo().update();
                    }
                } else {
                    f = SysModuleFunction.dao.getBy(moduleid, obj.get("modulename"));
                    int maxSeq = SysModuleFunction.dao.getSeqBy(moduleid);
                    if (f != null) {
                        if (f.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的模块名称已存在数据库中！");
                        else
                            f.delete();
                    }
                    f = new SysModuleFunction();
                    f.set("id", Util.getUUID()).set("moduleid", moduleid).set("name", obj.get("modulename")).set("shuoming", "文档类别-"+ obj.get("name")).set("seq", maxSeq + 1).setCreateInfo().save(); // 保存权限模块
                }
                
                if (!obj.get("id").equals(obj.get("oldid"))) {
                    SystemNewsCate replaceM = SystemNewsCate.dao.findById(obj.get("id"));
                    if (replaceM != null) {
                        if (replaceM.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的文档类别已存在数据库中！");
                        else
                            replaceM.delete();
                    }
                    
                    SystemNewsCate.dao.updatePK(obj.get("oldid"), obj.get("id"));
                    obj.setUpdateInfo().set("moduleid", f.get("id")).update();
                    
                    // 更新子类
                    SystemNewsCate.dao.uploadChild(obj.get("id"), obj.get("oldid"));
                    // 更新新闻
                    SystemNewsCate.dao.uploadNews(obj.get("id"), obj.get("oldid"));
                } else {
                    obj.setUpdateInfo().set("moduleid", f.get("id")).update();
                }
            }
            list.add(obj);
        }
        
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("news.newscate.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            // 移除授权模块
            for (String s : id.split(",")) {
                SystemNewsCate cate = SystemNewsCate.dao.findById(s);
                if (cate != null) {
                    SysModuleFunction.dao.deleteById(cate.get("moduleid"));
                }
            }
            SystemNewsCate.dao.update4DelByIds(id);
        } else {
            SystemNewsCate cate = SystemNewsCate.dao.findById(id);
            if (cate != null) {
                SysModuleFunction.dao.deleteById(cate.get("moduleid"));
            }
            SystemNewsCate.dao.update4DelById(id);
        }
        render(BjuiRender.success());
    }
}