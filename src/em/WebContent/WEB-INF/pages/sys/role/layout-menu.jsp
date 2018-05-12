<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <ul id="j_sys_role_menu_ztree_menu" class="ztree" data-toggle="ztree" data-expand-all="false" data-check-enable="true" data-on-click="sys_role_menutree_ZtreeClick">
        <c:forEach items="${menulist }" var="s">
            <li data-id="${s.id }" data-pid="${s.parentid }" data-checked="${!empty menuids && my:isContain(menuids, s.id) ? 'true' : 'false' }">${s.name }</li>
        </c:forEach>
    </ul>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn btn-green" onclick="sys_role_menu_save(this);">保存修改</button></li>
    </ul>
</div>