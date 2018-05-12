package com.em.tools;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @note 上传资源重定向 
 * @author xiaoxiao
 */
public class UpfilesFilter implements Filter {

	private FilterConfig config;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filter) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request   = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String ctx   = servletRequest.getServletContext().getContextPath();
		String path  = request.getRequestURI();
		String[] url = path.split("/upload/");
		response.sendRedirect(ctx + config.getInitParameter("uploadUrl") + url[1]);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config = config;
	}

}
