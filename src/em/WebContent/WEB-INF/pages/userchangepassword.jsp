<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="include/includetag.jsp"%>
<script type="text/javascript">
function sys_userinfo_changepass_submit(obj) {
    var $oldpass  = $('#j_userinfo_changepass_userpassword_old'),
        $userold  = $('#j_userinfo_changepass_oldpass'),
        $newpass  = $('#j_userinfo_changepass_newpass'),
        $conpass  = $('#j_userinfo_changepass_confirmpass'),
        $userpass = $('#j_userinfo_changepass_userpassword'), 
        username  = $('#j_userinfo_changepass_username').val()
    
    $oldpass.val(HMAC_SHA256_MAC(username, $userold.val()))
    $userpass.val(HMAC_SHA256_MAC(username, $newpass.val()))
    
    $('#j_userinfo_changepass_form').submit()
}
</script>
<div class="bjui-pageContent">
    <form action="user/changePassword" data-toggle="validate" method="post" id="j_userinfo_changepass_form">
        <input type="hidden" name="user.id" value="${user.id }">
        <input type="hidden" name="user.username" value="${user.username }" id="j_userinfo_changepass_username">
        <input type="hidden" name="user.password" value="" id="j_userinfo_changepass_userpassword">
        <input type="hidden" name="user.oldpass" value="" id="j_userinfo_changepass_userpassword_old">
        <table class="table table-bordered table-hover" style="margin-top:20px;">
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
                    <label for="j_userinfo_changepass_oldpass" class="control-label x85">旧密码:</label>
                    <input type="password" id="j_userinfo_changepass_oldpass" name="" value="" size="16" class="required" data-rule="required">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="j_userinfo_changepass_newpass" class="control-label x85">新密码:</label>
                    <input type="password" id="j_userinfo_changepass_newpass" name="" value="" size="16" class="required" data-rule="新密码:required;length(6~)">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="j_userinfo_changepass_confirmpass" class="control-label x85">确认密码:</label>
                    <input type="password" id="j_userinfo_changepass_confirmpass" name="" value="" size="16" class="required" data-rule="required;match(#j_userinfo_changepass_newpass)">
                </td>
            </tr>
        </table>
    </form>
</div>
<c:set var="foot_btn_submit">
    <li><button type="button" class="btn-default" data-icon="check" onclick="sys_userinfo_changepass_submit(this);">确定修改</button></li>
</c:set>
<%@ include file="include/foot_edit.jsp"%>