<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_position_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.position.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.position.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.position.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.position.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.position.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_position_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_position_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '定位方式信息表',
            showToolbar: true,
            toolbarItem: em_position_toolbar,
            local: 'local',
            data: return_em_position_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/position/save',
            delUrl: 'em/position/del',
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