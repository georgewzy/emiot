<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="include/includetag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统登录</title>
<script src="${ctx }js/jquery-1.7.2.min.js"></script>
<script src="${ctx }js/jquery.cookie.js"></script>
<script src="${ctx }js/sha256.js"></script>
<script src="${ctx }js/aes.js"></script>
<link href="${ctx }B-JUI/themes/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
* {font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
body{margin:0; padding:0;}
a:link {color: #285e8e;}
.main_box {
    width:100%;
    height:450px;
    background: url(${ctx }images/bg_login.jpg);
    background-size:100% 100%;
    background-repeat:no-repeat;
}
.login_logo {margin:0 auto; padding-top:30px; width:880px; height:90px;}
.login_box {margin:0 auto; width:870px;}
.link_box{height:160px;}
.foot_box{height:100px; background:#f2f2f4;}
.content_box{margin:0 auto; padding-top:15px; width:800px; line-height:180%;}
.login_banner {float:left; width:573px; height:290px; background:url(${ctx }images/login_banner.png) no-repeat;}
.login_form {float:right; padding:5px 20px; width:296px; font-size:14px; color:white; background:rgba(0,0,0,.3);}
.form-title{margin-bottom:10px; font-size:16px; line-height:40px; text-align:center; border-bottom:2px #ff6600 solid;}
.login_msg {text-align: center; font-size: 16px;}

.login_box .form-control {display: inline-block; *display: inline; zoom: 1; width:auto; font-size: 14px;}
.login_box .form-control.long {width:216px;}
.login_box .form-control.short {width: 70px;}
.login_box .form-group {margin-bottom: 15px;}
.login_box .form-group label.t {width: 120px; text-align: right; cursor: pointer;}
.login_box .form-group.space {padding-top:5px; border-top: 1px #FFF dotted;}
.login_box .form-group img {height: 34px;}
.login_box .m {cursor: pointer;}
.bottom {text-align: center; font-size: 12px;}
.btn-primary {background-color:#ff8140; border-color:#ff8140;}
.btn-primary:hover {background-color:#ff6600; border-color:#ff6600;}
.btn-primary.disabled,
.btn-primary[disabled]{background-color:#ffb58f; border-color:#ffb58f;}
</style>
<script type="text/javascript">
var COOKIE_NAME = 'sys_em_username';
$(function() {
    //choose_bg();
    changeCode();
    if ($.cookie(COOKIE_NAME)){
        $("#j_username").val($.cookie(COOKIE_NAME));
        $("#j_password").focus();
        $("#j_remember").attr('checked', true);
    } else {
        $("#j_username").focus();
    }
    $("#captcha_img").click(function(){
        changeCode();
    });
    $("#login_form").submit(function(){
        var issubmit = true;
        var i_index  = 0;
        $(this).find('.in').each(function(i){
            if ($.trim($(this).val()).length == 0) {
                $(this).css('border', '1px #ff0000 solid');
                issubmit = false;
                if (i_index == 0)
                    i_index  = i;
            }
        });
        if (!issubmit) {
            $(this).find('.in').eq(i_index).focus();
            return false;
        }
        var $remember = $("#j_remember");
        if ($remember.attr('checked')) {
            $.cookie(COOKIE_NAME, $("#j_username").val(), { path: '/', expires: 15 });
        } else {
            $.cookie(COOKIE_NAME, null, { path: '/' });  //删除cookie
        }
        
        $("#login_ok").attr("disabled", true).val('登陆中..');
        
        var key = CryptoJS.enc.Base64.parse($("#j_randomKey").val());
        var iv = CryptoJS.enc.Latin1.parse('0102030405060708');
        var password = CryptoJS.AES.encrypt($("#j_password").val(), key, {iv:iv, mode:CryptoJS.mode.CBC, padding:CryptoJS.pad.Pkcs7 });
        
        $("#j_password").val(password)
        
        return true;
    });
});
function changeCode(){
    $("#captcha_img").attr("src", "${ctx }login/getCaptcha?t="+ (new Date().getTime()));
}
function choose_bg() {
    var bg = Math.floor(Math.random() * 9 + 1);
    $('body').css('background-image', 'url(${ctx }images/loginbg_0'+ bg +'.jpg)');
}
</script>
</head>
<body>
<!--[if lte IE 7]>
<style type="text/css">
#errorie {position: fixed; top: 0; z-index: 100000; height: 30px; background: #FCF8E3;}
#errorie div {width: 900px; margin: 0 auto; line-height: 30px; color: orange; font-size: 14px; text-align: center;}
#errorie div a {color: #459f79;font-size: 14px;}
#errorie div a:hover {text-decoration: underline;}
</style>
<div id="errorie"><div>您还在使用老掉牙的IE，请升级您的浏览器到 IE8以上版本 <a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div></div>
<![endif]-->
<div class="main_box">
    <div class="login_logo">
        <img src="${ctx }images/logo.png" >
    </div>
    <div class="login_box">
        <div class="login_banner"></div>
        <div class="login_form">
            <input type="hidden" value="${randomKey }" id="j_randomKey" />
            <form action="${ctx }login/clklogin" id="login_form" method="post">
                <input type="hidden" name="jfinal_token" value="${jfinal_token }" />
                <div class="form-title">系统登录</div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input type="text" class="form-control long" id="j_username" value="" name="username" placeholder="登录账号">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input type="password" class="form-control long" id="j_password" value="" name="passwordhash" placeholder="登录密码">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-ok"></span></span>
                        <input type="text" class="form-control short" id="j_captcha" value="" name="captcha" placeholder="验证码">
                        <img id="captcha_img" alt="点击更换" title="点击更换" class="m" style="display: table-cell;">
                    </div>
                    
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label><input id="j_remember" type="checkbox" value="true">记住登陆账号!</label>
                    </div>
                </div>
                <div class="form-group" style="margin-bottom:0;">
                    <input type="submit" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;" class="btn btn-primary btn-lg" style="width:100%;">&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
                <c:if test="${!empty message}">
                    <div class="login_msg">
                        <font color="red">${message }</font>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>
<div class="link_box">
    <div class="content_box">
        <div class="row">
            <div class="col-md-3">
                <h4>关于平台</h4>
                <span>平台帮助</span><br>
                <span>意见反馈</span><br>
                <span>开放平台</span>
            </div>
            <div class="col-md-3">
                <h4>客户端下载</h4>
                <span>WAP版</span><br>
                <span>IOS客户端</span><br>
                <span>Android客户端</span>
            </div>
            <div class="col-md-3">
                <h4>服务&合作</h4>
                <span>开放平台</span><br>
                <span>链接网站</span><br>
                <span>广告服务</span>
            </div>
            <div class="col-md-3">
                <h4>联系客服</h4>
                <span>Service@sinoais.com</span><br>
                <span>4006-921-021</span><br>
            </div>
        </div>
    </div>
</div>
<div class="foot_box">
    <div class="content_box">
        <p class="text-center">关于平台　平台帮助　意见反馈　开放平台　链接网站　广告服务　客户端下载</p>
        <p class="text-center" style="padding-top:5px;">Copyright &copy2015-2020 SINOAIS.COM　　　沪ICP备15037414号 </p>
    </div>
</div>
</body>
</html>