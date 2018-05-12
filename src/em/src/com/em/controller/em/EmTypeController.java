package com.em.controller.em;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONArray;
import com.em.constant.Constant;
import com.em.controller.BaseController;
import com.em.model.em.EmType;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey="/sys/em/type", viewPath="/pages/em/property")
public class EmTypeController extends BaseController {

    @Override
    @RequiresPermissions("em.emtype.query")
    public void index() {
        // TODO Auto-generated method stub
        List<EmType> list = EmType.dao.list();
        
        setAttr("list", JsonKit.toJson(list));
        render("em_type_list.jsp");
    }
    
    @RequiresPermissions(value = {"em.emtype.add", "em.emtype.edit"}, logical = Logical.OR)
    @Before(Tx.class)
    public void save() {
        JSONArray jsonArray = JSONArray.parseArray(getPara("json"));
        if (jsonArray == null) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        List<EmType> list = new ArrayList<EmType>();
        for (Object o : jsonArray) {
            Map<String, Object> map = (Map<String, Object>) o;
            EmType old = EmType.dao.findById(map.get("oldid"));
            EmType obj = new EmType().setAttrs(map).put("oldid", map.get("oldid"));
            if (old == null) {
                old = EmType.dao.findById(map.get("id"));
                if (old != null) {
                    if (old.get("deletetime") == null)
                        throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的类型已存在数据库中！");
                    else
                        old.delete();
                }
                obj.setCreateInfo().save();
            } else {
                if (!obj.get("id").equals(obj.get("oldid"))) {
                    EmType replaceM = EmType.dao.findById(obj.get("id"));
                    if (replaceM != null) {
                        if (replaceM.get("deletetime") == null)
                            throw new RuntimeException("保存失败，序号为 ["+ obj.get("id") +"] 的类型已存在数据库中！");
                        else
                            replaceM.delete();
                    }
                    EmType.dao.updatePK(obj.get("oldid"), obj.get("id"));
                    obj.setUpdateInfo().update();
                } else {
                    obj.setUpdateInfo().update();
                }
            }
            list.add(obj);
        }
        
        renderJson(list);
    }
    
    @RequiresPermissions("em.emtype.edit")
    public void editPic() {
        String id = getPara();
        EmType emtype = EmType.dao.findById(id);
        setAttr("emtype", emtype);
        setAttr("random", Math.random());
        render("em_type_uppic.jsp");
    }
    
    @RequiresPermissions("em.emtype.edit")
    public void savePic() {
        UploadFile uploadfile = getFile();
        EmType type           = getModel(EmType.class, "emtype");
        String path           = PathKit.getWebRootPath() +"/";
        
        if (!new File(path + PropKit.get("upload_emtype")).exists()) {
            new File(path + PropKit.get("upload_emtype")).mkdirs();
        }
        if (uploadfile != null) {
            String fileName = uploadfile.getFileName();
            String fileExt  = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); //扩展名
            long fileSize   = uploadfile.getFile().length();
            if (!(fileExt.equals("jpg") || fileExt.equals("png"))) {
                throw new RuntimeException("上传失败！上传的图片格式不正确 。");
            }
            
            if (fileSize > (1024 * 1024)) {
                throw new RuntimeException("上传失败！上传的图片超过了 1M。");
            }
            String pic = PropKit.get("upload_emtype") + "emtype"+ type.get("id") +"."+ fileExt;
            
            // 移除旧文件
            File oldFile = new File(path + pic);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            
            uploadfile.getFile().renameTo(new File(path + pic));
            type.set("pic", pic).setUpdateInfo().update();
        }
        
        render(BjuiRender.successAndCloseCurrent());
    }
    
    @RequiresPermissions("em.emtype.edit")
    public void editIcon() {
        String id = getPara();
        EmType emtype = EmType.dao.findById(id);
        setAttr("emtype", emtype);
        setAttr("random", Math.random());
        render("em_type_upicon.jsp");
    }
    
    @RequiresPermissions("em.emtype.edit")
    public void saveIcon() {
        UploadFile uploadfile = getFile();
        EmType type           = getModel(EmType.class, "emtype");
        String path           = PathKit.getWebRootPath() +"/";
        
        if (!new File(path + PropKit.get("upload_emtype")).exists()) {
            new File(path + PropKit.get("upload_emtype")).mkdirs();
        }
        if (uploadfile != null) {
            String fileName = uploadfile.getFileName();
            String fileExt  = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); //扩展名
            long fileSize   = uploadfile.getFile().length();
            if (!(fileExt.equals("jpg") || fileExt.equals("png"))) {
                throw new RuntimeException("上传失败！上传的图片格式不正确 。");
            }
            
            if (fileSize > (1024 * 1024)) {
                throw new RuntimeException("上传失败！上传的图片超过了 1M。");
            }
            String pic = PropKit.get("upload_emtype") + "emtype"+ type.get("id") +"_icon."+ fileExt;
            
            // 移除旧文件
            File oldFile = new File(path + pic);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            
            uploadfile.getFile().renameTo(new File(path + pic));
            type.set("icon", pic).setUpdateInfo().update();
        }
        
        render(BjuiRender.successAndCloseCurrent());
    }
    
    //del
    @RequiresPermissions("em.emtype.del")
    @Before(Tx.class)
    public void del() {
        String id = getPara("id");
        if (Util.isEmptyString(id)) {
            render(BjuiRender.error(Constant.ERR_URL));
            return;
        }
        if (id != null && id.indexOf(",") >= 0) {
            EmType.dao.update4DelByIds(id);
        } else {
            EmType.dao.update4DelById(id);
        }
        render(BjuiRender.success());
    }
}