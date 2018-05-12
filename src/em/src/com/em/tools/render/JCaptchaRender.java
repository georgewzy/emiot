package com.em.tools.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import com.em.tools.captcha.CaptchaServiceSingleton;
import com.jfinal.kit.StrKit;
import com.jfinal.render.Render;
 
public class JCaptchaRender extends Render {
 
    private String randomCodeKey;
    public JCaptchaRender(String randomCodeKey) {
        if (StrKit.isBlank(randomCodeKey))
            throw new IllegalArgumentException("randomCodeKey can not be blank");
        this.randomCodeKey = randomCodeKey;
    }
    @Override
    public void render() {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
//          String captchaId = request.getSession(true).getId();
            BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(randomCodeKey, request.getLocale());
            ImageIO.write(challenge, "jpg", sos);
            sos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (sos != null)
                try {sos.close();} catch (IOException e) {e.printStackTrace();}
        }
    }
 
}
