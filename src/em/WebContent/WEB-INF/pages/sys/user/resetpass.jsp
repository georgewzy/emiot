<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
function sys_userinfo_resetpassword_submit() {
    var $pass     = $('#j_sys_users_resetpassword_password'),
        $username = $('#j_sys_users_resetpassword_username'),
        $userpass = $('#j_sys_users_resetpassword_userpassword'),
        password  = $pass.val()
    
    $userpass.val(HMAC_SHA256_MAC($username.val(), password))
    $('#j_sys_users_resetpassword_form').submit()
}
</script>
<div class="bjui-pageContent">
    <form action="user/savePass" data-toggle="validate" data-reload-navtab="true" id="j_sys_users_resetpassword_form">
        <input type="hidden" name="user.id" id="j_sys_users_resetpassword_userid" value="${user.id }">
        <input type="hidden" name="user.username" id="j_sys_users_resetpassword_username" value="${user.username }">
        <input type="hidden" name="user.password" id="j_sys_users_resetpassword_userpassword" value="">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label class="control-label x85">姓名:</label>
                        ${user.name }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x85">登陆账号:</label>
                        ${user.username }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_sys_users_resetpassword_password" class="control-label x85">新密码:</label>
                        <input type="password" id="j_sys_users_resetpassword_password" name="" value="" data-rule="length(6~)" size="12">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<c:set var="foot_btn_submit">
<li><button type="button" class="btn-default" data-icon="check" onclick="sys_userinfo_resetpassword_submit()">确定重置</button></li>
</c:set>
<%@ include file="../../include/foot_edit.jsp" %>