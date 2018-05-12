<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_servicetype_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.servicetype.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.servicetype.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.servicetype.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.servicetype.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.servicetype.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_servicetype_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_servicetype_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备维护类型信息表',
            showToolbar: true,
            toolbarItem: em_servicetype_toolbar,
            local: 'local',
            data: return_em_servicetype_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/servicetype/save',
            delUrl: 'em/servicetype/del',
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