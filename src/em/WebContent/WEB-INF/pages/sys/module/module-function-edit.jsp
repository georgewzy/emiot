<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
function sys_modulefunction_save_callback(json) {
    var $form = $('#sys_modulefunction_form')
    
    $form.bjuiajax('ajaxDone', json)
    if (json[BJUI.keys.statusCode] == BJUI.statusCode.ok) {
        $form
            .navtab('reload', {url:'module?moduleid=${function.moduleid}'})
            .dialog('closeCurrent')
    }
}
</script>
<div class="bjui-pageContent">
    <form action="module/saveFunction" data-toggle="validate" data-callback="sys_modulefunction_save_callback" id="sys_modulefunction_form">
        <input type="hidden" name="function.id" value="${function.id }">
        <table class="table table-hover table-fat">
            <tbody>
                <tr>
                    <td>
                        <label for="j_modulefunction_edit_name" class="control-label x100">模块名称:</label>
                        <input type="hidden" name="function.moduleid" value="${function.moduleid }">
                        ${function.modulename }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_modulefunction_edit_name" class="control-label x100">模块功能名称:</label>
                        <input type="text" id="j_modulefunction_edit_name" name="function.name" value="${function.name }" data-rule="required;letters" size="25">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_modulefunction_edit_shuoming" class="control-label x100">模块功能说明:</label>
                        <input type="text" id="j_modulefunction_edit_shuoming" name="function.shuoming" value="${function.shuoming }" data-rule="required" size="25">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_modulefunction_edit_seq" class="control-label x100">模块功能顺序:</label>
                        <input type="text" id="j_modulefunction_edit_seq" name="function.seq" value="${function.seq }" data-rule="required;digits" size="8">
                        <span class="required"></span>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp" %>