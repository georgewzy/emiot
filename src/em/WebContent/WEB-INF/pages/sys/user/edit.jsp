<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="user/save" data-toggle="validate" data-reload-navtab="true">
        <input type="hidden" name="user.id" value="${user.id }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label for="j_user_edit_userroles_name" class="control-label x85">所属角色:</label>
                        <select id="j_user_edit_userroles_name" name="rolesid" data-toggle="selectpicker" data-rule="required" multiple>
                            <option></option>
                            <c:forEach items="${rolelist }" var="s">
                                <option value="${s.id }"${!empty user.roleidlist && my:isContain(user.roleidlist, s.id) ? ' selected' : '' }>${s.name }</option>
                            </c:forEach>
                        </select><span></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_user_edit_username" class="control-label x85">用户账号:</label>
                        <input type="text" id="j_user_edit_username" name="user.username" <c:if test="${!empty user.username }">readonly="readonly"</c:if> value="${user.username }" data-rule="required" size="15"><span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_user_edit_password" class="control-label x85">用户密码:</label>
                        <input type="password" id="j_user_edit_password" name="user.password" value="" <c:if test="${empty user.username }">data-rule="required"</c:if> size="15"><span class="required"></span>
                        <c:if test="${!empty user.id }">不改请留空</c:if>
                    </td>
                </tr>
                <%-- 
                <tr>
                    <td>
                        <label class="control-label x85">管辖区域:</label>
                        <div style="display:inline-block; vertical-align:middle;">
                            <c:forEach items="${areaList }" var="s" varStatus="i">
                                <p style="white-space:nowrap;"><input type="checkbox" name="areaids" id="j_users_setarea_areaid_${s.id }" value="${s.id }" data-toggle="icheck" data-label="${s.name }" ${my:isContainObject(areaids, s.id) ? 'checked' : '' }>&nbsp;&nbsp;</p>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
                --%>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp" %>