package com.em.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.em.tools.ImageUtil;
import com.em.tools.Util;
import com.em.tools.render.BjuiRender;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.NoUrlPara;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.PropKit;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey="/sys/doupload")
public class UploadController extends BaseController {
    
    @Override
    @Before(NoUrlPara.class)
    public void index() {
        // TODO Auto-generated method stub
        
    }
    
    // editor上传
    public void up() {
        UploadFile upload = this.getFile();
        File file = upload.getFile();
        String filedataname = upload.getOriginalFileName();
        //判断类型
        String dir = getPara("dir");
        //读取上传目录配置
        String ups = PropKit.get("upload_dir") + dir;
        //保存文件
        this.upload(file, ups, filedataname);
    }
        
    /**
     * 公用上传类 - editor
     */
    private void upload(File file, String folder, String filedataname) {
        String path = this.getRequest().getServletContext().getRealPath("/") + "/";
        String suffix = filedataname.substring(filedataname.lastIndexOf("."));
        String name = System.currentTimeMillis() + suffix; //保存文件名
        /*分年月存放到文件夹*/
        String ym = Util.getFormatDate("yyyyMM");
        String uppath = path + folder + ym + '/';
        try {
            File updirs = new File(uppath);
            if (!updirs.exists()) updirs.mkdirs(); //文件夹不存在则创建
            file.renameTo(new File(uppath + name));
            
//            Siteconfig config = Siteconfig.dao.getConfig();
            /*缩略图*/
//            if (!Util.isEmptyString(config.getBoolean("isthumb"))) {
//                if (filedataname.lastIndexOf(".png") > 0 || filedataname.lastIndexOf(".jpg") > 0 || filedataname.lastIndexOf(".gif") > 0) {
//                    int width  = config.getInt("imgwidth") != null ? config.getInt("imgwidth") : 800;
//                    int height = config.getInt("imgheight") != null ? config.getInt("imgheight") : 600;
//                    ImageUtil.createThumb(file, suffix.substring(1).toUpperCase(), uppath + name, width, height);
//                    file.delete();
//                } else {
//                    file.renameTo(new File(uppath + name));
//                }
//            } else {
//                file.renameTo(new File(uppath + name));
//            }
            /*水印*/
//            if (!Util.isEmptyString(config.getInt("addwatermark")) && config.getInt("addwatermark") > 0) {
//                if (filedataname.lastIndexOf(".png") > 0 || filedataname.lastIndexOf(".jpg") > 0 || filedataname.lastIndexOf(".gif") > 0) {
//                    String location  = !Util.isEmptyString(config.getStr("marklocation")) ? config.getStr("marklocation") : ImageUtil.MARK_LOCATION.BOTTOMRIGHT.toString();
//                    String targetImg = uppath + name;
//                    String formatName= suffix.substring(1).toUpperCase();
//                    if (config.getInt("addwatermark") == 1) {           // 图片水印
//                        String pressImg = config.getStr("watermarkimg");
//                        if (!Util.isEmptyString(pressImg)) {
//                            pressImg = path + pressImg;
//                            ImageUtil.pressImage(pressImg, targetImg, location, formatName);
//                        }
//                    } else if (config.getInt("addwatermark") == 2) {    // 文字水印
//                        String pressText = config.getStr("watermarktxt");
//                        if (!Util.isEmptyString(pressText)) {
//                            String fontName  = !Util.isEmptyString(config.getStr("marktxtfont")) ? config.getStr("marktxtfont") : "Verdana";
//                            int fontSize     = !Util.isEmptyString(config.getInt("marktxtsize")) ? config.getInt("marktxtsize") : 20;
//                            int fontColor    = !Util.isEmptyString(config.getStr("marktxtcolor")) ? Integer.valueOf(config.getStr("marktxtcolor"), 16) : 0xFFFFFF;
//                            fontName         = path + "fonts/" + fontName;
//                            Font font        = LoadFont.FontZT(fontName, Font.TRUETYPE_FONT, fontSize);
//                            Color color      = new Color(fontColor);
//                            ImageUtil.pressText(pressText, targetImg, font, color, fontSize, location, 0.9f, formatName);
//                        }
//                    }
//                }
//            }
            String returnname = folder + ym +"/"+ name;
            //返回JSON
            String json = "{\"error\":0, \"url\":\""+ returnname +"\"}";
            if (isNotIE()) {
                renderJson(json);
            } else {
                render(new JsonRender(json).forIE());
            }
        } catch (Exception e) {
            file.deleteOnExit(); //删除上传文件
        }
    }
    
    // uploadify 上传入口
    public void uploadify() {
        UploadFile upload = this.getFile();
        File file = upload.getFile();
        String filedataname = upload.getOriginalFileName().toLowerCase();
        String dir = getPara("dir");
        Integer width  = getParaToInt("width");
        Integer height = getParaToInt("height");
        //读取上传目录配置
        String ups = PropKit.get("upload_dir") + dir;
        //保存文件
        this.uploadify(file, ups, filedataname, width, height);
    }
    
    /**
     * 公用上传 - uploadify
     */
    private void uploadify(File file, String dir, String filedataname, Integer width, Integer height) {
        /*上传文件*/
        String path         = this.getRequest().getServletContext().getRealPath("/") +"/"+ dir;
        String suffix       = filedataname.substring(filedataname.lastIndexOf("."));
        String name         = System.currentTimeMillis() + suffix; //保存文件名
        File updirs         = new File(path);
        try {
            if (!updirs.exists()) updirs.mkdirs(); //文件夹不存在则创建
            File newFile    = new File(path + name);
            if (newFile.exists()) {
                newFile.delete();
            }
            /*缩略图*/
//            if (!Util.isEmptyString(width) && !Util.isEmptyString(height)) {
//                if (filedataname.lastIndexOf(".png") > 0 || filedataname.lastIndexOf(".jpg") > 0 || filedataname.lastIndexOf(".gif") > 0) {
//                    ImageUtil.createThumb(file, suffix.substring(1).toUpperCase(), path + name, width, height);
//                }
//                file.delete();
//            } else {
//                file.renameTo(newFile);
//            }
            //返回JSON
            renderJson("{\"statusCode\":200, \"filename\":\""+ dir + name +"\"}");
        } catch (Exception e) {
            //删除上传文件
            file.deleteOnExit();
        }
    }
    
    /*********** for kindeditor ***********/
    //缩放
    private void zoom(/*Siteconfig config, */File file, String fileExt, String savePath) {
        if (fileExt.equals("png") || fileExt.equals("jpg") || fileExt.equals("gif")) {
            //int width  = config.getInt("imgwidth") != null ? config.getInt("imgwidth") : 800;
            //int height = config.getInt("imgheight") != null ? config.getInt("imgheight") : 600;
            int width  = 800;
            int height = 600;
            ImageUtil.createThumb(file, fileExt, savePath, width, height);
            file.delete();
        } else {
            file.renameTo(new File(savePath));
        }
    }
    //加水印
//    private void mark(Siteconfig config, String targetImg, String fileExt, String path) {
//        if (fileExt.equals("png") || fileExt.equals("jpg") || fileExt.equals("gif")) {
//            String location  = !Util.isEmptyString(config.getStr("marklocation")) ? config.getStr("marklocation") : ImageUtil.MARK_LOCATION.BOTTOMRIGHT.toString();
//            if (config.getInt("addwatermark") == 1) {           // 图片水印
//                String pressImg = config.getStr("watermarkimg");
//                if (!Util.isEmptyString(pressImg)) {
//                    pressImg = path + pressImg;
//                    ImageUtil.pressImage(pressImg, targetImg, location, fileExt);
//                }
//            } else if (config.getInt("addwatermark") == 2) {    // 文字水印
//                String pressText = config.getStr("watermarktxt");
//                if (!Util.isEmptyString(pressText)) {
//                    String fontName  = !Util.isEmptyString(config.getStr("marktxtfont")) ? config.getStr("marktxtfont") : "Verdana";
//                    int fontSize     = !Util.isEmptyString(config.getInt("marktxtsize")) ? config.getInt("marktxtsize") : 20;
//                    int fontColor    = !Util.isEmptyString(config.getStr("marktxtcolor")) ? Integer.valueOf(config.getStr("marktxtcolor").substring(1), 16) : 0xFFFFFF;
//                    fontName         = path + "fonts/" + fontName;
//                    Font font        = LoadFont.FontZT(fontName, Font.TRUETYPE_FONT, fontSize);
//                    Color color      = new Color(fontColor);
//                    ImageUtil.pressText(pressText, targetImg, font, color, fontSize, location, 0.9f, fileExt);
//                }
//            }
//        }
//    }
    
    //保存远程图片
//    public void saveRemote() {
//        String img = getPara("img");
//        if (Util.isEmptyString(img)) {
//            render(MyDwzRender.error());
//            return;
//        }
//        try {
//            URL url = new URL(img);
//            java.io.BufferedInputStream bis = new BufferedInputStream(url.openStream());
//            byte[] bytes = new byte[1024];
//            //文件保存目录
//            String basePath = this.getRequest().getServletContext().getRealPath("/") + "/";
//            String savePath = basePath + "upload/";
//            //文件保存目录URL
//            String saveUrl  = "upload/";
//            String dirName = "image";
//            //扩展名
//            String fileExt = img.substring(img.lastIndexOf(".") + 1).toLowerCase();
//            //允许的扩展名
//            HashMap<String, String> extMap = new HashMap<String, String>();
//            extMap.put("image", "gif,jpg,jpeg,png");
//            if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
//                fileExt = "jpg";
//            }
//            //创建文件夹
//            savePath += dirName + "/";
//            saveUrl += dirName + "/";
//            File saveDirFile = new File(savePath);
//            if (!saveDirFile.exists()) {
//                saveDirFile.mkdirs();
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String ymd = sdf.format(new Date());
//            savePath += ymd + "/";
//            saveUrl += ymd + "/";
//            File dirFile = new File(savePath);
//            if (!dirFile.exists()) {
//                dirFile.mkdirs();
//            }
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//            File saveFile = new File(savePath + newFileName);
//            /*是否缩放图片,要缩放则先将文件命名为其他*/
//            Siteconfig config = Siteconfig.dao.getConfig();
//            if (!Util.isEmptyString(config.get("isthumb"))) {
//                saveFile = new File(savePath +"_"+ newFileName);
//            }
//            OutputStream bos = new FileOutputStream(saveFile);
//            int len;
//            while ((len = bis.read(bytes)) > 0) {
//                bos.write(bytes, 0, len);
//            }
//            bis.close();
//            bos.flush();
//            bos.close();
//            /*是否缩放*/
//            if (!Util.isEmptyString(config.get("isthumb"))) {
//                this.zoom(config, saveFile, fileExt, savePath + newFileName);
//            }
//            /*是否加水印*/
//            if (!Util.isEmptyString(config.get("addwatermark")) && config.getInt("addwatermark") > 0) {
//                this.mark(config, savePath + newFileName, fileExt, basePath);
//            }
//            render(MyDwzRender.successRefresh(saveUrl + newFileName));
//        } catch (Exception e) {
//            render(MyDwzRender.error());
//            e.printStackTrace();
//        }
//    }
    
    //上传文件 - json
    public void upFile() {
        List<UploadFile> files = this.getFiles();
        //文件保存目录路径
        String basePath = this.getRequest().getServletContext().getRealPath("/") + "/";
        String savePath = basePath + "upload/";
        //文件保存目录URL
        String saveUrl  = "upload/";
        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        //最大文件大小
        long maxSize = 52428800; //50MB
        if(files == null || files.size() == 0){
            renderJson(getError("请选择文件。"));
            return;
        }
        //检查目录
        File uploadDir = new File(savePath);
        if(!uploadDir.isDirectory()){
            renderJson(getError("上传目录不存在。"));
            return;
        }
        //检查目录写权限
        if(!uploadDir.canWrite()){
            renderJson(getError("上传目录没有写权限。"));
            return;
        }
        String dirName = getPara("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if(!extMap.containsKey(dirName)){
            renderJson(getError("目录名不正确。"));
            return;
        }
        //创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        for (UploadFile f : files) {
            String fileName = f.getFileName();
            long fileSize   = f.getFile().length();
            //检查文件大小
            if (fileSize > maxSize) {
                renderJson(getError("上传文件大小超过限制。"));
                return;
            }
            //检查扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
                renderJson(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
                return;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            try {
                File uploadedFile = new File(savePath, newFileName);
                /*是否缩放图片*/
//                Siteconfig config = Siteconfig.dao.getConfig();
//                if (!Util.isEmptyString(config.get("isthumb"))) {
//                    this.zoom(config, f.getFile(), fileExt, savePath + newFileName);
//                } else {
//                    f.getFile().renameTo(uploadedFile);
//                }
//                /*是否加水印*/
//                if (!Util.isEmptyString(config.get("addwatermark")) && config.getInt("addwatermark") > 0) {
//                    this.mark(config, savePath + newFileName, fileExt, basePath);
//                }
                f.getFile().renameTo(uploadedFile);
            } catch(Exception e) {
                renderJson(getError("上传文件失败。"));
                e.printStackTrace();
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("error", 0);
            obj.put("url", saveUrl + newFileName);
            obj.put("origin_name", fileName + ("(点击下载)"));
            if (isNotIE()) {
                renderJson(obj.toJSONString());
            } else {
                render(new JsonRender(obj.toJSONString()).forIE());
            }
        }
    }
    
    //图片空间 - json
    public void fileManager() {
        String rootPath = this.getRequest().getServletContext().getRealPath("/") + "/upload/"; //根目录
        String rootUrl  = "upload/";
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png"}; //图片扩展名
        String dirName = getPara("dir");
        if (dirName != null) {
            if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                renderText("Invalid Directory name.");
                return;
            }
            rootPath += dirName + "/";
            rootUrl  += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        //根据path参数，设置各路径和URL
        String path = getPara("path") != null ? getPara("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }
        //排序形式，name or size or type
        String order = getPara("order") != null ? getPara("order").toLowerCase() : "name";
        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            renderText("Access is not allowed.");
            return;
        }
        //最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            renderText("Parameter is not valid.");
            return;
        }
        //目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if(!currentPathFile.isDirectory()){
            renderText("Directory does not exist.");
            return;
        }
        //遍历目录取的文件信息
        List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", moveupDirPath);
        result.put("current_dir_path", currentDirPath);
        result.put("current_url", currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        renderJson(result.toJSONString());
    }
    
    /*返回json消息*/
    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }
    
    /*排序类*/
    private class NameComparator implements Comparator<Hashtable<String, Object>> {
        public int compare(Hashtable<String, Object> a, Hashtable<String, Object> b) {
            Hashtable<String, Object> hashA = a;
            Hashtable<String, Object> hashB = b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
            }
        }
    }
    private class SizeComparator implements Comparator<Hashtable<String, Object>> {
        public int compare(Hashtable<String, Object> a, Hashtable<String, Object> b) {
            Hashtable<String, Object> hashA = a;
            Hashtable<String, Object> hashB = b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
                    return 1;
                } else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
    private class TypeComparator implements Comparator<Hashtable<String, Object>> {
        public int compare(Hashtable<String, Object> a, Hashtable<String, Object> b) {
            Hashtable<String, Object> hashA = a;
            Hashtable<String, Object> hashB = b;
            if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
            }
        }
    }
    
}