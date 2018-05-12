package com.em.tools.captcha;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaServiceSingleton {

	private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService();
    
	/**
	 * 使用自定义的验证码
	 */
	static {
	     instance = new DefaultManageableImageCaptchaService(
	        new FastHashMapCaptchaStore(),
	        new CaptchaEngine(),
	        180,
	        100000,
	        75000);
	     } 
	
    public static ImageCaptchaService getInstance(){
        return instance;
    }
    
}
