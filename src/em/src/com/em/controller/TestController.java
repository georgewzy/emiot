package com.em.controller;

import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey="/aaa", viewPath="/pages/sys")
public class TestController extends BaseController {

	@Override
	public void index() {
		// TODO Auto-generated method stub
		renderText("OK!");
	}

}
