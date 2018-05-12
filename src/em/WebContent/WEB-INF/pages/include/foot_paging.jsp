<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="includetag.jsp" %>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="120">120</option>
                <option value="150">150</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }" data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }">
    </div>
</div>