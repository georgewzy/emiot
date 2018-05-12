<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="module/save" data-toggle="validate" data-reload-navtab="true">
        <input type="hidden" name="module.id" value="${module.id }">
        <table class="table table-hover table-fat">
            <tbody>
                <tr>
                    <td>
                        <label for="j_module_edit_name" class="control-label x100">模块名称:</label>
                        <input type="text" id="j_module_edit_name" name="module.name" value="${module.name }" data-rule="required;letters" size="25">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_module_edit_shuoming" class="control-label x100">模块说明:</label>
                        <input type="text" id="j_module_edit_shuoming" name="module.shuoming" value="${module.shuoming }" data-rule="required" size="25">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_module_edit_seq" class="control-label x100">模块顺序:</label>
                        <input type="text" id="j_module_edit_seq" name="module.seq" value="${module.seq }" data-rule="required;digits" size="8">
                        <span class="required"></span>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp" %>