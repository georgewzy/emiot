package com.em.tools.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class JyJsonRender extends Render {
    
    private static final String CONTENT_TYPE = "text/html;charset=" + getEncoding();
    
    private static int CODE_SUCCESS = 200;
    
    private static int CODE_ERROR   = 300;
    
    private static int CODE_TIMEOUT = 301;
    
    public static String MSG_SUCCESS = "操作成功！";
    
    public static String MSG_ERROR   = "操作失败！";
    
    public static String MSG_TIMEOUT = "会话超时！";
    
    private int status = CODE_SUCCESS;
    
    private String message = "";
    
    public JyJsonRender() {}
    
    public JyJsonRender status(int status) {
        this.status = status;
        return this;
    }
    
    public JyJsonRender message(String message) {
        this.message = message;
        return this;
    }
    
    public static JyJsonRender success() {
        return success(MSG_SUCCESS);
    }
    
    public static JyJsonRender error() {
        return error(MSG_ERROR);
    }
    
    public static JyJsonRender timeout() {
        return timeout(MSG_TIMEOUT);
    }
    
    public static JyJsonRender success(String message) {
        return callback(CODE_SUCCESS, message);
    }
    
    public static JyJsonRender error(String message) {
        return callback(CODE_ERROR, message);
    }
    
    public static JyJsonRender timeout(String message) {
        return callback(CODE_TIMEOUT, message);
    }
    
    public static JyJsonRender successAndCloseCurrent() {
        return successAndCloseCurrent(MSG_SUCCESS);
    }
    
    public static JyJsonRender successAndCloseCurrent(String message) {
        return successAndCloseCurrent(message, "");
    }
    
    public static JyJsonRender successAndCloseCurrentAndRefreshTab(String tabid) {
        return callback(CODE_SUCCESS, MSG_SUCCESS);
    }
    
    public static JyJsonRender successAndCloseCurrent(String message, String tabid) {
        return callback(CODE_SUCCESS, message);
    }
    
    public static JyJsonRender callback(int status, String message) {
        JyJsonRender render = new JyJsonRender();
        render.status(status);
        render.message(message);
        return render;
    }
    
    @Override
    public void render() {
        // TODO Auto-generated method stub
        PrintWriter writer = null;
        String callback = "\"status\":{0},\"message\":\"{1}\"";
        callback = "{\n" + MessageFormat.format(callback, new Object[] { this.status, this.message }) + "\n}";
        try {
            this.response.setHeader("Pragma", "no-cache");
            this.response.setHeader("Cache-Control", "no-cache");
            this.response.setDateHeader("Expires", 0L);
            this.response.setContentType(CONTENT_TYPE);
            writer = this.response.getWriter();
            writer.write(callback);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}