<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="include/includetag.jsp" %>
<script type="text/javascript">
j_login_changecode()

$('#j_login_captcha_img').click(function(){
    j_login_changecode()
})

function j_login_changecode(){
    $('#j_login_captcha_img').attr('src', '${ctx }login/getCaptcha')
}

function sys_login_submit() {
    var key      = CryptoJS.enc.Base64.parse($('#j_login_password_key').val())
    var iv       = CryptoJS.enc.Latin1.parse('0102030405060708')
    var password = CryptoJS.AES.encrypt($('#j_login_password').val(), key, {iv:iv, mode:CryptoJS.mode.CBC, padding:CryptoJS.pad.Pkcs7 })
    
    $('#j_login_password_hash').val(password)
    $('#j_login_form').submit()
    
    $('#j_login_captcha').val('')
    j_login_changecode()
}

$('#j_login_captcha').bind('keyup', function(event) {
    if (event.keyCode == 13) {
        sys_login_submit()
    }
})
</script>
<div class="bjui-pageContent">
    <form action="${ctx }login/ajaxlogin" data-toggle="validate" method="post" id="j_login_form">
        <input type="hidden" id="j_login_password_key" name="" value="${randomKey }">
        <input type="hidden" id="j_login_password_hash" name="password">
        <br><br>
        <div class="form-group">
            <label for="j_login_username" class="control-label x85">账户：</label>
            <input type="text" data-rule="required" id="j_login_username" name="username" value="${username }" placeholder="身份证号、职工号" size="25">
        </div>
        <br>
        <div class="form-group">
            <label for="j_login_password" class="control-label x85">密码：</label>
            <input type="password" data-rule="required" id="j_login_password" name="" value="" placeholder="密码" size="25">
        </div>
        <br>
        <div class="form-group">
            <label for="j_ajaxlogo_captcha" class="control-label x85">验证码：</label>
            <input type="text" name="captcha" id="j_login_captcha" value="" placeholder="验证码" size="10">
            <label class="control-label x80"><img id="j_login_captcha_img" alt="点击更换" title="点击更换" src=""/></label>
        </div>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="remove">取消</button></li>
        <li><button type="button" onclick="sys_login_submit()" class="btn-default" data-icon="check">登录</button></li>
    </ul>
</div>