<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_alarmrecordtype_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.alarmrecordtype.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.alarmrecordtype.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.alarmrecordtype.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.alarmrecordtype.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.alarmrecordtype.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_alarmrecordtype_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_alarmrecordtype_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '报警记录事件类型信息表',
            showToolbar: true,
            toolbarItem: em_alarmrecordtype_toolbar,
            local: 'local',
            data: return_em_alarmrecordtype_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/alarmrecordtype/save',
            delUrl: 'em/alarmrecordtype/del',
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