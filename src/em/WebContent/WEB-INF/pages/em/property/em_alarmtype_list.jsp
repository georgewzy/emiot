<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_alarmtype_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.alarmtype.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.alarmtype.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.alarmtype.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.alarmtype.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.alarmtype.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_alarmtype_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_alarmtype_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '报警类型信息表',
            showToolbar: true,
            toolbarItem: em_alarmtype_toolbar,
            local: 'local',
            data: return_em_alarmtype_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/alarmtype/save',
            delUrl: 'em/alarmtype/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:100, rule:'required;digits'}">序号</th>
                <th data-options="{name:'name', align:'center', width:200, rule:'required'}">名称</th>
            </tr>
        </thead>
    </table>
</div>