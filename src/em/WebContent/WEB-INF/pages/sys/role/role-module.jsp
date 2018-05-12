<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
$('#sys_role_roleid_select, #sys_role_moduleid_select').change(function() {
    var $this = $(this), $select = $this.siblings('select'), $form = $this.closest('form')
    
    if ($this.val() && $select.val()) {
        $form.submit()
    }
})
$('.sys_role_operation_checkrow').on('ifChanged', function() {
    var $this = $(this), $td = $this.closest('td'), $checkbox = $td.find(':checkbox')
    
    if ($this.is(':checked')) {
        $checkbox.iCheck('check')
    } else {
        $checkbox.iCheck('uncheck')
    }
})
$('#sys_role_operation_checkall').on('ifChanged', function() {
    var $this = $(this), $checkbox = $('#j_sys_role_module_form').find('table').find(':checkbox')
    
    if ($this.is(':checked')) {
        $checkbox.iCheck('check')
    } else {
        $checkbox.iCheck('uncheck')
    }
})
</script>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="role/module" method="post">
        <div class="bjui-searchBar">
            <label>选择角色：</label>
            <select name="roleid" id="sys_role_roleid_select" data-toggle="selectpicker" data-rule="required">
                <option></option>
                <c:forEach items="${roleList }" var="s">
                    <option value="${s.id }"${!empty roleid && roleid == s.id ? ' selected' : '' }>${s.name }</option>
                </c:forEach>
            </select>&nbsp;
            <label>选择模块：</label>
            <select name="moduleid" id="sys_role_moduleid_select" data-toggle="selectpicker" data-rule="required">
                <option></option>
                <c:forEach items="${moduleList }" var="s">
                    <option value="${s.id }"${!empty moduleid && moduleid == s.id ? ' selected' : '' }>${s.shuoming } ( ${s.name } ) </option>
                </c:forEach>
            </select>&nbsp;
            <button type="submit" class="btn-default" data-icon="check">确定</button>
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <form id="j_sys_role_module_form" action="role/saveModule" method="post" data-toggle="ajaxform" data-reload="false">
        <input type="hidden" name="roleid" value="${roleid }">
        <input type="hidden" name="function_count" value="${fn:length(functionList) }">
        <input type="hidden" name="operation_count" value="${fn:length(operationList) }">
        <table class="table table-bordered table-hover table-striped table-top">
            <thead>
                <tr>
                    <th width="100">模块功能</th>
                    <th width="200">功能说明</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${functionList }" var="s" varStatus="i">
                    <tr>
                        <td align="center">${s.name }</td>
                        <td>${s.shuoming }</td>
                        <td>
                            <input type="hidden" name="functionList[${i.index }].id" value="${s.id }">
                            <c:forEach items="${operationList }" var="f" varStatus="j">
                                <input type="checkbox" id="sys_role_operation[${i.index }]_f${j.index }" name="operationList[${i.index }][${j.index }].id" value="${f.id }" data-toggle="icheck" data-label="${f.shuoming }&nbsp;" ${my:isContainObject(s.operationids, f.id) ? 'checked' : '' }>
                            </c:forEach>
                            &nbsp;|&nbsp;&nbsp;
                            <input type="checkbox" class="sys_role_operation_checkrow" id="sys_role_operation_checkrow_${i.index }" data-toggle="icheck" data-label="全选">
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn btn-close" data-icon="remove">关闭</button></li>
        <c:if test="${!empty roleid }">
            <li><button type="submit" class="btn btn-green" data-icon="save">保存修改</button></li>
        </c:if>
        <li><div style="padding-top:4px; padding-right:15px;"><input type="checkbox" id="sys_role_operation_checkall" data-toggle="icheck" data-label="全选/全不选"></div></li>
    </ul>
</div>