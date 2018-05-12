<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function sys_area_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("sys.area.add") ? "add" : ""}')
    item.push('${my:hasPermission("sys.area.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("sys.area.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("sys.area.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("sys.area.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_sys_area_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table id="sys-area-table" data-toggle="datagrid" class="table table-bordered" 
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle: '管辖区域信息表',
            showToolbar: true,
            toolbarItem: sys_area_toolbar,
            local: 'local',
            data: return_sys_area_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'area/save',
            delUrl: 'area/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'seq', align:'center', width:60, rule:'required;digits'}">序号</th>
                <th data-options="{name:'name', align:'center', width:100, rule:'required'}">名称</th>
                <th data-options="{name:'beizhu', align:'center', width:200, render:renderNull}">备注</th>
            </tr>
        </thead>
    </table>
</div>