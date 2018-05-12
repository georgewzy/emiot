<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_brightlevel_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.brightlevel.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.brightlevel.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.brightlevel.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.brightlevel.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.brightlevel.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_brightlevel_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_brightlevel_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '亮度等级信息表',
            showToolbar: true,
            toolbarItem: em_brightlevel_toolbar,
            local: 'local',
            data: return_em_brightlevel_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/brightlevel/save',
            delUrl: 'em/brightlevel/del',
            delPK: 'id',
            importOption: {type:'dialog', options:{url:'em/brightlevel/upload', width:500, height:300, mask:true, title:'信息导入'}}
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:150, rule:'required;digits'}">序号</th>
                <th data-options="{name:'name', align:'center', width:260, rule:'required'}">名称</th>
            </tr>
        </thead>
    </table>
</div>