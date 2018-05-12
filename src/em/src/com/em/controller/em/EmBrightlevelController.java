package com.em.controller.em;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmBrightlevel;
import com.em.tools.ExcelIO;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.tx.Tx;

@ControllerBind(controllerKey="/sys/em/brightlevel", viewPath="/pages/em/property")
public class EmBrightlevelController extends BaseController {

    @Override
    @RequiresPermissions("em.brightlevel.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmBrightlevel> list = EmBrightlevel.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        render("em_brightlevel_list.jsp");
    }
    
    @RequiresPermissions(value = {"em.brightlevel.add", "em.brightlevel.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<EmBrightlevel> list = new ArrayList<EmBrightlevel>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            EmBrightlevel old = EmBrightlevel.dao.findById(map.get("oldid"));
            EmBrightlevel obj = new EmBrightlevel().setAttrs(map).put("oldid", map.get("oldid"));
            if (old == null) {
                old = EmBrightlevel.dao.findById(map.get("id"));
                if (old != null) {
                    if (old.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的亮度等级已存在数据库中！");
                    else
                        old.delete();
                }
                obj.setCreateInfo().save();
            } else {
                if (!obj.get("id").equals(obj.get("oldid"))) {
                    EmBrightlevel replaceM = EmBrightlevel.dao.findById(obj.get("id"));
                    if (replaceM != null) {
                        if (replaceM.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的亮度等级已存在数据库中！");
                        else
                            replaceM.delete();
                    }
                    EmBrightlevel.dao.updatePK(obj.get("oldid"), obj.get("id"));
                    obj.setUpdateInfo().update();
                } else {
                    obj.setUpdateInfo().update();
                }
            }
            list.add(obj);
        }
        
        renderJson(list);
    }
    
    //del
    @RequiresPermissions("em.brightlevel.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmBrightlevel.dao.update4DelByIds(id);
        } else {
            EmBrightlevel.dao.update4DelById(id);
        }
        render(BjuiRender.success());
    }
    
    public void upload() {
        render("import.jsp");
    }
    
    @Before(Tx.class)
    public void doImport() throws IOException {
        File upfile = getFile().getFile();
        if (!upfile.exists()) {
            render(BjuiRender.error("要导入的文件不存在！"));
            return;
        }
        String[][] str = ExcelIO.EXIO.impExcel(upfile);
        
        EmBrightlevel b = null;
        for (int i = 0; i < str.length; i++) {
            b = new EmBrightlevel();
            String seq  = str[i][0];
            String name = str[i][1];
            if (Util.isEmptyString(seq)) {
                throw new RuntimeException("导入失败！第 ["+ (i + 1) +"] 行，顺序不能为空！");
            } else if (!Util.isInt(seq)) {
                throw new RuntimeException("导入失败！第 ["+ (i + 1) +"] 行，民族编码长度不能大于2！");
            }
            if (Util.isEmptyString(name)) {
                throw new RuntimeException("导入失败！第 ["+ (i + 1) +"] 行，名称不能为空！");
            }
            
            b
                .set("id", Util.getUUID())
                .set("seq", seq)
                .set("name", name);
            
            EmBrightlevel old = EmBrightlevel.dao.getByColumn("name", name);
            
            if (old == null) {
                b.setCreateInfo().save();
            }
        }
        render(BjuiRender.successAndCloseCurrent());
    }
}