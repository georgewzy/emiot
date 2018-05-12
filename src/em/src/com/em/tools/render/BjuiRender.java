package com.em.tools.render;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class BjuiRender extends Render {
    
    private static final String CONTENT_TYPE = "text/html;charset=" + getEncoding();
    
    private static int CODE_SUCCESS = 200;
    
    private static int CODE_ERROR   = 300;
    
    private static int CODE_TIMEOUT = 301;
    
    public static String MSG_SUCCESS = "操作成功！";
    
    public static String MSG_ERROR   = "操作失败！";
    
    public static String MSG_TIMEOUT = "会话超时！";
    
    private int statusCode = CODE_SUCCESS;
    
    private String message = "";
    
    private String tabid = "";
    
    private String dialogid = "";
    
    private String divid = "";
    
    private String datagrid = "";
    
    private boolean closeCurrent = false;
    
    private String forward = "";
    
    private String forwardConfirm = "";
    
    public BjuiRender() {}
    
    public BjuiRender statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    
    public BjuiRender message(String message) {
        this.message = message;
        return this;
    }
    
    public BjuiRender tabid(String tabid) {
        this.tabid = tabid;
        return this;
    }
    
    public BjuiRender dialogid(String dialogid) {
        this.dialogid = dialogid;
        return this;
    }
    
    public BjuiRender divid(String divid) {
        this.divid = divid;
        return this;
    }
    
    public BjuiRender datagrid(String datagrid) {
        this.datagrid = datagrid;
        return this;
    }
    
    public BjuiRender closeCurrent(boolean closeCurrent) {
        this.closeCurrent = closeCurrent;
        return this;
    }
    
    public BjuiRender forward(String forward) {
        this.forward = forward;
        return this;
    }
    
    public BjuiRender forwardConfirm(String forwardConfirm) {
        this.forwardConfirm = forwardConfirm;
        return this;
    }
    
    public static BjuiRender success() {
        return success(MSG_SUCCESS);
    }
    
    public static BjuiRender error() {
        return error(MSG_ERROR);
    }
    
    public static BjuiRender timeout() {
        return timeout(MSG_TIMEOUT);
    }
    
    public static BjuiRender success(String message) {
        return callback(CODE_SUCCESS, message);
    }
    
    public static BjuiRender error(String message) {
        return callback(CODE_ERROR, message);
    }
    
    public static BjuiRender timeout(String message) {
        return callback(CODE_TIMEOUT, message);
    }
    
    public static BjuiRender successAndRefreshTab(String tabid) {
        return callback(CODE_SUCCESS, MSG_SUCCESS, tabid);
    }
    
    public static BjuiRender success(String message, String tabid) {
        return callback(CODE_SUCCESS, message, tabid);
    }
    
    public static BjuiRender successAndCloseCurrent() {
        return successAndCloseCurrent(MSG_SUCCESS);
    }
    
    public static BjuiRender successAndCloseCurrent(String message) {
        return successAndCloseCurrent(message, "");
    }
    
    public static BjuiRender successAndCloseCurrentAndRefreshTab(String tabid) {
        return callback(CODE_SUCCESS, MSG_SUCCESS, tabid, true);
    }
    
    public static BjuiRender successAndCloseCurrent(String message, String tabid) {
        return callback(CODE_SUCCESS, message, tabid, true);
    }
    
    public static BjuiRender successAndCloseCurrentAndRefreshDatagrid(String datagrid) {
        BjuiRender render = new BjuiRender();
        render.statusCode(CODE_SUCCESS);
        render.message(MSG_SUCCESS);
        render.datagrid(datagrid);
        render.closeCurrent(true);
        return render;
    }
    
    public static BjuiRender callback(int statusCode, String message) {
        return callback(statusCode, message, "");
    }
    
    public static BjuiRender callback(int statusCode, String message, String tabid) {
        return callback(statusCode, message, tabid, false);
    }
    
    public static BjuiRender callback(int statusCode, String message, String tabid, boolean closeCurrent) {
        return callback(statusCode, message, tabid, closeCurrent, "");
    }
    
    public static BjuiRender callback(int statusCode, String message, String tabid, boolean closeCurrent, String forward) {
        return callback(statusCode, message, tabid, closeCurrent, forward, "");
    }
    
    public static BjuiRender callback(int statusCode, String message, String tabid, boolean closeCurrent, String forward, String forwardConfirm) {
        BjuiRender render = new BjuiRender();
        render.statusCode(statusCode);
        render.message(message);
        render.tabid(tabid);
        render.closeCurrent(closeCurrent);
        render.forward(forward);
        render.forwardConfirm(forwardConfirm);
        return render;
    }
    
    @Override
    public void render() {
        // TODO Auto-generated method stub
        PrintWriter writer = null;
        String callback = "\"statusCode\":{0},\"message\":\"{1}\",\"tabid\":\"{2}\",\"dialogid\":\"{3}\",\"divid\":\"{4}\",\"datagrid\":\"{5}\",\"closeCurrent\":{6},\"forward\":\"{7}\",\"forwardConfirm\":\"{8}\"";
        callback = "{\n" + MessageFormat.format(callback, new Object[] { this.statusCode, this.message, this.tabid, this.dialogid, this.divid, this.datagrid, this.closeCurrent, this.forward, this.forwardConfirm }) + "\n}";
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
