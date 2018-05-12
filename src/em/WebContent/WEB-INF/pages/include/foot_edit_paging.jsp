<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="includetag.jsp" %>
<div class="bjui-pageFooter" style="height:60px;">
    <ul>
        ${foot_btn_after }
        <li><button type="button" class="btn-close" data-icon="close">${!empty foot_title_cancel ? foot_title_cancel : '取消' }</button></li>
        <li><button type="submit" class="btn-default" data-icon="save">${!empty foot_title_save ? foot_title_save : '保存' }</button></li>
        ${foot_btn_before }
    </ul>
    
    <div class="clearfix"></div>
    <div style="margin:1px 0 3px; border-top:1px #ddd solid;"></div>
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