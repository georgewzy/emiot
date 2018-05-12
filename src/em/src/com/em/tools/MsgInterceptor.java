package com.em.tools;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class MsgInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        Controller ctrl = ai.getController();
        try {
            ai.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            // 删除上传的临时文件
            if (ctrl.getRequest().getContentType() != null && ctrl.getRequest().getContentType().indexOf("multipart/form-data") != -1) {
                if (ctrl.getFiles() != null) {
                    for (UploadFile file : ctrl.getFiles()) {
                        if (file.getFile().exists()) {
                            file.getFile().delete();
                        }
                    }
                }
            }
            
            String msg   = e.getMessage();
            String[] arr = msg != null ? msg.split("Exception:") : null;
            if (Util.isAjax(ctrl.getRequest())) {
                if (arr != null && arr.length > 1) msg = arr[arr.length - 1];
                msg = msg.replace("\n", "").replace("\r", "").replace("\"", "'");
                ctrl.renderText("{\"statusCode\":300, \"message\":\""+ msg +"\"}");
            } else {
                ctrl.renderError(404);
            }
        }
    }
    
}
