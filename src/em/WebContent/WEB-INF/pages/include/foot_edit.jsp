<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="includetag.jsp" %>
<div class="bjui-pageFooter">
    <ul>
        ${foot_btn_after }
        <li><button type="button" class="btn-close" data-icon="close">${!empty foot_title_cancel ? foot_title_cancel : '取消' }</button></li>
        <c:if test="${empty foot_btn_submit }">
            <li><button type="submit" class="btn-default" data-icon="save">${!empty foot_title_save ? foot_title_save : '保存' }</button></li>
        </c:if>
        ${foot_btn_submit }
        ${foot_btn_before }
    </ul>
</div>