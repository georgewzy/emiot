<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_comm_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.comm.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.comm.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.comm.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.comm.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.comm.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_comm_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_comm_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '通讯方式信息表',
            showToolbar: true,
            toolbarItem: em_comm_toolbar,
            local: 'local',
            data: return_em_comm_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/comm/save',
            delUrl: 'em/comm/del',
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