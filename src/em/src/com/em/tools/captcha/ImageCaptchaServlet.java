package com.em.tools.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;

public class ImageCaptchaServlet extends HttpServlet {

	/**
	 * 产生图片验证码
	 */
	private static final long serialVersionUID = 5212337621895682272L;

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		setDisableCacheHeader(response);
		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
	}
    	
}