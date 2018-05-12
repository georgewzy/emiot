<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
function em_service_edit($trs, datas) {
    var $table    = $('#j_em_service_table'),
        $selected = $table.data('selectedTrs'),
        id        = '',
        tit       = '编辑设备维护记录'
    
    if (datas) {
        id  = datas[0].id
    } else {
        tit = '添加设备维护记录'
    }
    
    $table.dialog({
        id    : 'em-service-edit',
        url   : 'em/service/edit/'+ id,
        data  : {eminfoid:'${em.id}', emtypeid:'${em.typeid }'},
        title : tit,
        width : 500,
        height: 400,
        mask  : true
    })
    
    return false
}
function em_service_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.emservice.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.emservice.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.emservice.add") || my:hasPermission("em.emservice.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.emservice.add") || my:hasPermission("em.emservice.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.emservice.del") ? "del" : ""}')
    
    return item.join(',')
}
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_service_table"
        data-options="{
            width: '100%',
            height: '100%',
            showToolbar: true,
            toolbarItem: em_service_toolbar,
            dataUrl: 'em/service/list?service.eminfoid=${em.id }',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            beforeEdit: em_service_edit,
            editUrl: 'em/service/save',
            delUrl: 'em/service/del',
            delPK: 'id',
            jsonPrefix: 'service'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'areaid', align:'center', width:180, type:'select', items:itemsEmArea}">设备归属</th>
                <th data-options="{name:'emtypeid', align:'center', width:80, type:'select', items:itemsEmType}">设备类型</th>
                <th data-options="{name:'code', align:'center', width:80}">设备编号</th>
                <th data-options="{name:'servicetypeid', align:'center', width:90, type:'select', items:itemsEmServicetype}">维护类型</th>
                <th data-options="{name:'serviceuser', align:'center', width:100, render:renderNull}">维护人</th>
                <th data-options="{name:'servicetime', align:'center', width:160, render:renderNull}">维护时间</th>
                <th data-options="{name:'beizhu', width:300, render:renderNull}">备注</th>
            </tr>
        </thead>
    </table>
</div>