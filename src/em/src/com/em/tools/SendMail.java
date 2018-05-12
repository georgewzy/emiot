package com.em.tools;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.em.constant.Constant;

public class SendMail {
	
	public static boolean sendMail(String mail, String msg) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(Constant.SYS_MAIL_SMTP); // 邮件服务器
		email.setAuthentication(Constant.SYS_MAIL_ADD, Constant.SYS_MAIL_PASS); // smtp认证的用户名和密码
		email.addTo(mail, ""); // 收信者
		email.setFrom(Constant.SYS_MAIL_ADD, Constant.SYS_MAIL_USER);	// 发信者
		email.setSubject("JY邮件"); // 标题
		email.setCharset("UTF-8"); // 编码格式
		email.setHtmlMsg(msg); // 加入内容
		email.send(); // 发送
		return true;
	}
	
	//注册邮件
	public static boolean sendRegMail(String mail, String password, String id, String url) throws EmailException  {
		String href = url +"member/doActive?id="+ id +"&code="+ SHA256.hmacDigest(password, mail);
		StringBuilder msg = new StringBuilder();
		msg.append("<div id=\"mailContentContainer\" class=\"qmbox\" style=\"height:auto;min-height:100px;_height:100px;word-wrap:break-word;font-size:14px;padding:0;font-family: 'lucida Grande',Verdana;\">");
		msg.append("<table style=\"margin: 25px auto;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"648\" align=\"center\">");
		msg.append("<tbody>");
		msg.append("<tr><td style='color:#00A498;'><h1 style='margin-bottom:10px;'>鼎龙旅行社</h1></td></tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 20px 20px 0px; background: none repeat scroll 0% 0% #ffffff; border-top: 5px solid #00A498; border-right: 1px solid #00A498;\">");
		msg.append("<p>"+ mail +", 你好 </p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 10px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<p>您已经成功注册为<strong>鼎龙旅行社</strong>会员！</p>");
		msg.append("<p>登陆账号：<strong>"+ mail +"</strong></p>");
		msg.append("<p>登陆密码：<strong>"+ password +"</strong></p>");
		msg.append("<p style=\"font-weight:bold\">请点击以下链接激活此帐号：<br />");
		msg.append("<a href=\""+ href +"\">"+ href +"</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-bottom: 1px solid #00A498; border-left: 1px solid #00A498; padding: 0px 20px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<hr style='color:#ccc;' />");
		msg.append("<p style='color:#060;font-size:9pt;'>想了解更多信息，请访问 <a href=\"http://sale.dinglongtour.com\" target=\"_blank\">http://sale.dinglongtour.com</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("</tbody>");
		msg.append("</table>");
		msg.append("</div>");
		return sendMail(mail, msg.toString());
	}

	//找回密码邮件
	public static boolean sendLostpwdMail(String mail, String password, String id, String url) throws EmailException  {
		String href = url +"member/doResetpwd?id="+ id +"&code="+ SHA256.hmacDigest(password, mail);
		StringBuilder msg = new StringBuilder();
		msg.append("<div id=\"mailContentContainer\" class=\"qmbox\" style=\"height:auto;min-height:100px;_height:100px;word-wrap:break-word;font-size:14px;padding:0;font-family: 'lucida Grande',Verdana;\">");
		msg.append("<table style=\"margin: 25px auto;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"648\" align=\"center\">");
		msg.append("<tbody>");
		msg.append("<tr><td style='color:#00A498;'><h1 style='margin-bottom:10px;'>鼎龙旅行社</h1></td></tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 20px 20px 0px; background: none repeat scroll 0% 0% #ffffff; border-top: 5px solid #00A498; border-right: 1px solid #00A498;\">");
		msg.append("<p>"+ mail +", 你好 </p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 10px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<p>您的登陆密码已经重置！</p>");
		msg.append("<p>登陆账号：<strong>"+ mail +"</strong></p>");
		msg.append("<p>登陆密码：<strong>"+ password +"</strong></p>");
		msg.append("<p style=\"font-weight:bold\">请点击以下链接登陆：<br />");
		msg.append("<a href=\""+ href +"\">"+ href +"</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-bottom: 1px solid #00A498; border-left: 1px solid #00A498; padding: 0px 20px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<hr style='color:#ccc;' />");
		msg.append("<p style='color:#060;font-size:9pt;'>想了解更多信息，请访问 <a href=\"http://sale.dinglongtour.com\" target=\"_blank\">http://sale.dinglongtour.com</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("</tbody>");
		msg.append("</table>");
		msg.append("</div>");
		return sendMail(mail, msg.toString());
	}
	
	//系统用户密码邮件
	public static boolean sendSysUsersPwdMail(String mail, String password, String url) throws EmailException  {
		String href = url;
		StringBuilder msg = new StringBuilder();
		msg.append("<div id=\"mailContentContainer\" class=\"qmbox\" style=\"height:auto;min-height:100px;_height:100px;word-wrap:break-word;font-size:14px;padding:0;font-family: 'lucida Grande',Verdana;\">");
		msg.append("<table style=\"margin: 25px auto;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"648\" align=\"center\">");
		msg.append("<tbody>");
		msg.append("<tr><td style='color:#00A498;'><h1 style='margin-bottom:10px;'>鼎龙旅行社</h1></td></tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 20px 20px 0px; background: none repeat scroll 0% 0% #ffffff; border-top: 5px solid #00A498; border-right: 1px solid #00A498;\">");
		msg.append("<p>"+ mail +", 您好 </p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-left: 1px solid #00A498; padding: 10px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<p>这是您的系统登陆密码邮件！</p>");
		msg.append("<p>登陆账号：<strong>"+ mail +"</strong></p>");
		msg.append("<p>登陆密码：<strong>"+ password +"</strong></p>");
		msg.append("<p style=\"font-weight:bold\">请点击以下链接登陆：<br />");
		msg.append("<a href=\""+ href +"\">"+ href +"</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("<tr>");
		msg.append("<td style=\"border-bottom: 1px solid #00A498; border-left: 1px solid #00A498; padding: 0px 20px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #00A498;\">");
		msg.append("<hr style='color:#ccc;' />");
		msg.append("<p style='color:#060;font-size:9pt;'>想了解更多信息，请访问 <a href=\"http://sale.dinglongtour.com\" target=\"_blank\">http://sale.dinglongtour.com</a></p>");
		msg.append("</td>");
		msg.append("</tr>");
		msg.append("</tbody>");
		msg.append("</table>");
		msg.append("</div>");
		return sendMail(mail, msg.toString());
	}
}
