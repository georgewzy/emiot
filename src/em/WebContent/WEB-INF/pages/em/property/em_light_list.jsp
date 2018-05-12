<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_light_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.light.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.light.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.light.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.light.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.light.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_em_light_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_light_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '灯质信息表',
            showToolbar: true,
            toolbarItem: em_light_toolbar,
            local: 'local',
            data: return_em_light_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/light/save',
            delUrl: 'em/light/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:40, rule:'required;digits'}">序号</th>
                <th data-options="{name:'typename', align:'center', width:80, rule:'required'}">种类</th>
                <th data-options="{name:'name', align:'center', width:100, rule:'required'}">名称</th>
                <th data-options="{name:'cycles', align:'center', width:50, rule:'digits'}">周期数</th>
                <th data-options="{name:'n10', align:'center', width:60, rule:'digits', render:renderNull}">10n</th>
                <th data-options="{name:'ff10', align:'center', width:60, rule:'digits', render:renderNull}">10ff</th>
                <th data-options="{name:'n20', align:'center', width:60, rule:'digits', render:renderNull}">20n</th>
                <th data-options="{name:'ff20', align:'center', width:60, rule:'digits', render:renderNull}">20ff</th>
                <th data-options="{name:'n30', align:'center', width:60, rule:'digits', render:renderNull}">30n</th>
                <th data-options="{name:'ff30', align:'center', width:60, rule:'digits', render:renderNull}">30ff</th>
                <th data-options="{name:'n40', align:'center', width:60, rule:'digits', render:renderNull}">40n</th>
                <th data-options="{name:'ff40', align:'center', width:60, rule:'digits', render:renderNull}">40ff</th>
                <th data-options="{name:'n50', align:'center', width:60, rule:'digits', render:renderNull}">50n</th>
                <th data-options="{name:'ff50', align:'center', width:60, rule:'digits', render:renderNull}">50ff</th>
                <th data-options="{name:'n60', align:'center', width:60, rule:'digits', render:renderNull}">60n</th>
                <th data-options="{name:'ff60', align:'center', width:60, rule:'digits', render:renderNull}">60ff</th>
                <th data-options="{name:'n70', align:'center', width:60, rule:'digits', render:renderNull}">70n</th>
                <th data-options="{name:'ff70', align:'center', width:60, rule:'digits', render:renderNull}">70ff</th>
                <th data-options="{name:'n80', align:'center', width:60, rule:'digits', render:renderNull}">80n</th>
                <th data-options="{name:'ff80', align:'center', width:60, rule:'digits', render:renderNull}">80ff</th>
                <th data-options="{name:'n90', align:'center', width:60, rule:'digits', render:renderNull}">90n</th>
                <th data-options="{name:'ff90', align:'center', width:60, rule:'digits', render:renderNull}">90ff</th>
            </tr>
        </thead>
    </table>
</div>