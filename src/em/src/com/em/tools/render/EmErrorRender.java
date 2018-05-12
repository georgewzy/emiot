package com.em.tools.render;

import com.jfinal.render.ErrorRender;

public class EmErrorRender extends ErrorRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4334383343059236215L;

	public EmErrorRender(int errorCode, String view) {
		// TODO Auto-generated constructor stub
		super(errorCode, view);
	}

	public String getView() { // 覆盖父类 getView()
		String url = request.getRequestURI();
		switch (errorCode) {
        case 404:
        case 500:
            if (url.indexOf("/sys") >= 0) {
                if (url.endsWith("/sys")) {
                    view = "/toSys.html";
                } else {
                    view = "/ajax404.html";
                }
            } else {
                view = "/error.jsp";
            }
            break;
        case 401:
            if (url.indexOf("/sys") >= 0) {
                view = "/ajax401.html";
            } else {
                view = "/401.jsp";
            }
            break;
        case 403:
            if (url.indexOf("/sys") >= 0) {
                view = "/ajax403.html";
            } else {
                view = "/403.jsp";
            }
            break;
        default:
            view = "/error.jsp";
            break;
        }
		return view;
	}
	
}
