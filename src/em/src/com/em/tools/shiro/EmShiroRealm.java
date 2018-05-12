package com.em.tools.shiro;

import java.util.HashSet;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.em.constant.Constant;
import com.em.model.SysMenu;
import com.em.model.SysModuleFunction;
import com.em.model.SysUsersRole;
import com.em.model.SysUsers;
import com.em.tools.SHA256;
import com.em.tools.Util;

/**
 * @author K'naan
 * 自定义的Shiro Realm
 */
public class EmShiroRealm extends AuthorizingRealm {

	/**
	 * 授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		/*用户权限*/
		HashSet<String> permissions = new HashSet<String>();
		//获取当前登陆的用户
		Subject current = SecurityUtils.getSubject();
		Session session = current.getSession(false);
		SysUsers user = (SysUsers) session.getAttribute(Constant.SYS_SESSION_ADMIN);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户名查找对象
        if (user != null) {
            //添加角色(Set集合<字符串>)
            //SysRole role = SysRole.dao.findById(user.get("roleid"));
            //info.addRole(role.getStr("name"));
            //添加权限
            /*菜单权限(url)*/
            List<Object> roleids   = SysUsersRole.dao.listRoleId(user.getStr("id"));
            List<SysMenu> menulist = SysMenu.dao.listByRoleids(roleids.toArray(), null);
            for (SysMenu m : menulist) {
                String menuurl = m.getStr("url");
                if (!Util.isEmptyString(menuurl))
                    permissions.add(menuurl);
            }
            
            /* 模块功能 */
            List<SysModuleFunction> functionList = SysModuleFunction.dao.listByRoleids(roleids.toArray());
            for (SysModuleFunction func : functionList) {
                String modulename         = func.getStr("modulename");
                String modulefunctionname = func.getStr("name");
                String operationnames     = func.getStr("operationnames");
                
                if (!Util.isEmptyString(modulename))
                    permissions.add(modulename);
                if (!Util.isEmptyString(modulefunctionname))
                    permissions.add(modulefunctionname);
                if (!Util.isEmptyString(operationnames)) {
                    for (String s : operationnames.split(",")) {
                        permissions.add(s);
                        permissions.add(modulename +"."+ modulefunctionname +"."+ s);
                    }
                }
            }
            
            /*后台管理权限*/
            permissions.add("admin_manager");
            info.addStringPermissions(permissions);
            return info;
        }
		
        return null;
	}

	/**
	 * 登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名去查找对象
        SysUsers user = SysUsers.dao.getUser(token.getUsername());
        Session session  = SecurityUtils.getSubject().getSession();
        if (user != null) {
            String password = SHA256.hmacDigest(String.valueOf(token.getPassword()), user.getStr("username"));
			/*将user放入Session*/
			session.setAttribute(Constant.SYS_SESSION_ADMIN, user);
			token.setPassword(password.toCharArray());
			AuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), user.getStr("password"), getName());
			
			return info;
        }
        return null;
	}

	/**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }
 
    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
