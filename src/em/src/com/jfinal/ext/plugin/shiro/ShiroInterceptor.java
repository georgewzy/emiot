/**
 * Copyright (c) 2011-2013, dafei 李飞 (myaniu AT gmail DOT com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jfinal.ext.plugin.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

import com.em.tools.Util;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;

public class ShiroInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        // 判断url
        String key = ai.getControllerKey();
        if (key.toLowerCase().indexOf("/sys") >= 0) {
            ClearShiro clear = ai.getMethod().getAnnotation(ClearShiro.class);
            if (clear == null) {
                if (!ShiroKit.isAuth()) {
                    String sessionid = ai.getController().getPara("sessionid");
                    if (!Util.isEmptyString(sessionid)) {
                        Subject requestSubject = new Subject.Builder().sessionId(sessionid).buildSubject();
                        if (requestSubject.isAuthenticated()) {
                            ai.invoke();
                            return;
                        }
                    }
                    ai.getController().forwardAction(ShiroKit.getLoginUrl());
                    return;
                }
            }
        }
        
        // 权限拦截
        AuthzHandler ah = ShiroKit.getAuthzHandler(ai.getActionKey());
        // 存在访问控制处理器。
        if (ah != null) {
            try {
                // 执行权限检查。
                ah.assertAuthorized();
            } catch (UnauthenticatedException lae) {
                // RequiresGuest，RequiresAuthentication，RequiresUser，未满足时，抛出未经授权的异常。
                // 如果没有进行身份验证，返回HTTP401状态码,或者跳转到默认登录页面
                if (StrKit.notBlank(ShiroKit.getLoginUrl())){
                    //保存登录前的页面信息,只保存GET请求。其他请求不处理。
                    if(ai.getController().getRequest().getMethod().equalsIgnoreCase("GET")){
                        ai.getController().setSessionAttr(ShiroKit.getSavedRequestKey(), ai.getActionKey());
                    }
                    //重定向到登录页面
                    ai.getController().redirect(ShiroKit.getLoginUrl());
                } else {
                    //没有设定登录页面，则返回401错误
                    ai.getController().renderError(401);
                }
                return;
            } catch (AuthorizationException ae) {
                // RequiresRoles，RequiresPermissions授权异常
                // 如果没有权限访问对应的资源，返回HTTP状态码403，或者调转到为授权页面
                if (StrKit.notBlank(ShiroKit.getUnauthorizedUrl())) {
                    //设定flash信息，需要启用flash插件才行
                    //ai.getController().setFlash(FlashManager.FLASH_MSG_KEY, "对不起，您无权访问本页：" + ai.getActionKey()+",系统自动返回到当前页面。");
                    //重定向到未授权页面，比如登录页面
                    //if ()
                    ai.getController().renderError(403);
                    //ai.getController().redirect(ShiroKit.getUnauthorizedUrl());
                } else {
                    //返回403错误
                    ai.getController().renderError(403);
                }
                return;
            }
        }
        // 执行正常逻辑
        ai.invoke();
    }
}
