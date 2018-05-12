<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function sys_operation_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("sys.operation.add") ? "add" : ""}')
    item.push('${my:hasPermission("sys.operation.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("sys.operation.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("sys.operation.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("sys.operation.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_sys_operation_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="sys-operation-table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '模块操作信息表',
            showToolbar: true,
            toolbarItem: sys_operation_toolbar,
            local: 'local',
            data: return_sys_operation_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'operation/save',
            delUrl: 'operation/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'seq', align:'center', width:100, rule:'required;digits'}">顺序</th>
                <th data-options="{name:'name', align:'center', width:200, rule:'required;letters', edit:false}">操作名称</th>
                <th data-options="{name:'shuoming', align:'center', width:200, rule:'required'}">说明</th>
            </tr>
        </thead>
    </table>
</div>