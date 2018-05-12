package com.em.config;

import com.em.tools.MsgInterceptor;
import com.em.tools.render.EmErrorRender;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.Render;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class BaseConfig extends JFinalConfig {
    
    Routes routes;
    
    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        loadPropertyFile("config.properties");                    // 加载少量必要配置，随后可用getProperty(...)获取值
        me.setDevMode(getPropertyToBoolean("devMode"));            // 是否开发模式
        me.setViewType(ViewType.JSP);                             // 设置视图类型为Jsp，否则默认为FreeMarker
        me.setBaseViewPath("/WEB-INF/");
        me.setMaxPostSize(1024 * 1024 * 100); //100MB
        me.setErrorView(401, "/401.html");
        me.setErrorView(403, "/403.html");
        me.setErrorRenderFactory(new IErrorRenderFactory() {
            @Override
            public Render getRender(int errorCode, String view) {
                // TODO Auto-generated method stub
                return new EmErrorRender(errorCode, view);
            }
        });
    }
    
    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        AutoBindRoutes abRoutes = new AutoBindRoutes();
        abRoutes.autoScan(false); //关闭自动扫描 - 通过注解绑定路由
        me.add(abRoutes);
        routes = me;
    }
    
    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置数据库连接池插件
        C3p0Plugin plugin = new C3p0Plugin(getProperty("jdbcUrl"),
                                           getProperty("user"), 
                                           getProperty("password").trim());
        plugin.setDriverClass(getProperty("driver"));
        me.add(plugin);
        //自动绑定model与表插件
        AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(plugin);
        autoTableBindPlugin.setShowSql(true);
        autoTableBindPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
        autoTableBindPlugin.autoScan(false);//关闭自动扫描 - 通过注解绑定表
        autoTableBindPlugin.setDialect(new AnsiSqlDialect());
        me.add(autoTableBindPlugin);
        //EhCachePlugin插件
        me.add(new EhCachePlugin());
        //加载Shiro插件
        ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
        shiroPlugin.setLoginUrl("/login/");
        shiroPlugin.setSuccessUrl("/sys/");
        shiroPlugin.setUnauthorizedUrl("/login/err401");
        me.add(shiroPlugin);
    }
    
    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        /*开启model的session支持*/
        me.add(new SessionInViewInterceptor(false));
        /*后台登陆验证\权限验证 全局拦截*/
        me.add(new ShiroInterceptor());
        /*消息拦截器*/
        me.add(new MsgInterceptor());
    }
    
    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
        /*
        me.add(new Handler() {
            @Override
            public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
                // TODO Auto-generated method stub
                request.setAttribute("URL", getProperty("site_url"));
                nextHandler.handle(target, request, response, isHandled);
            }
        });*/
        
        ContextPathHandler path = new ContextPathHandler("BASE_PATH");
        me.add(path);
    }
    
    @Override
    public void afterJFinalStart() {
        // TODO Auto-generated method stub
    }

    /**
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        JFinal.start("WebRoot", 80, "/", 5);
    }
}
