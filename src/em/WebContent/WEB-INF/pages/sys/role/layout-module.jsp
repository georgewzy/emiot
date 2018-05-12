<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
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
<div class="bjui-pageContent">
    <form id="j_sys_role_module_form" action="role/saveModule" method="post">
        <input type="hidden" name="roleid" value="${roleid }">
        <input type="hidden" name="function_count" value="${fn:length(functionList) }">
        <input type="hidden" name="operation_count" value="${fn:length(operationList) }">
        <table class="table table-bordered table-hover table-striped table-top">
            <thead>
                <tr>
                    <th width="200">模块说明</th>
                    <th width="100">模块功能</th>
                    <th width="100">功能说明</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="moduleshuoming" value="null" />
                <c:forEach items="${functionList }" var="s" varStatus="i">
                    <tr>
                        <td><c:if test="${!empty moduleshuoming && moduleshuoming != s.moduleshuoming }">${s.moduleshuoming }(${s.modulename })</c:if></td>
                        <td align="center">${s.modulefunctionname }</td>
                        <td>${s.modulefunctionshuoming }</td>
                        <td>
                            <input type="hidden" name="functionList[${i.index }].modulefunctionid" value="${s.modulefunctionid }">
                            <c:forEach items="${operationList }" var="f" varStatus="j">
                                <input type="checkbox" id="sys_role_operation[${i.index }]_f${j.index }" name="operationList[${i.index }][${j.index }].operationid" value="${f.operationid }" data-toggle="icheck" data-label="${f.operationshuoming }&nbsp;" ${my:isContainObject(s.operationids, f.operationid) ? 'checked' : '' }>
                            </c:forEach>
                            &nbsp;|&nbsp;&nbsp;
                            <input type="checkbox" class="sys_role_operation_checkrow" id="sys_role_operation_checkrow_${i.index }" data-toggle="icheck" data-label="全选">
                        </td>
                    </tr>
                    <c:set var="moduleshuoming" value="${s.moduleshuoming }" />
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn btn-green" onclick="sys_role_module_save(this);">保存修改</button></li>
        <li><div style="padding-top:4px; padding-right:15px;"><input type="checkbox" id="sys_role_operation_checkall" data-toggle="icheck" data-label="全选/全不选"></div></li>
    </ul>
</div>