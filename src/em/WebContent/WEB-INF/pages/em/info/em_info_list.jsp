<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
function em_em_edit($trs, datas) {
    var $table    = $('#j_em_em_table'),
        $selected = $table.data('selectedTrs'),
        id        = '',
        tit       = '编辑设备'
    
    if (datas) {
        id  = datas[0].id
        tit = tit +'-'+ datas[0].name
    } else {
        tit = '添加设备'
    }
    
    $table.navtab({
        id    : 'em-add',
        url   : 'em/edit/'+ id,
        title : tit
    })
    
    return false
}
function em_em_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.em.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.em.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.em.add") || my:hasPermission("em.em.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.em.add") || my:hasPermission("em.em.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.em.del") ? "del" : ""}')
    
    return item.join(',')
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_em_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备信息表',
            showToolbar: true,
            toolbarItem: em_em_toolbar,
            dataUrl: 'em/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            beforeEdit: em_em_edit,
            editUrl: 'em/save',
            delUrl: 'em/del',
            delPK: 'id',
            jsonPrefix: 'em'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'areaid', align:'center', width:180, type:'select', items:itemsEmArea}">归属</th>
                <th data-options="{name:'typeid', align:'center', width:80, type:'select', items:itemsEmType}">类型</th>
                <th data-options="{name:'name', align:'center', width:100, render:renderNull}">设备名称</th>
                <th data-options="{name:'code', align:'center', width:100, render:renderNull}">设备编号</th>
                <th data-options="{name:'cardno', align:'center', width:100, render:renderNull}">卡号</th>
                <th data-options="{name:'address', align:'center', width:170, render:renderNull}">通信地址</th>
                <th data-options="{name:'commid', align:'center', width:70, type:'select', items:itemsEmComm}">通信方式</th>
                <th data-options="{name:'cpid', align:'center', width:70, type:'select', items:itemsEmCommP}">通信协议</th>
                <th data-options="{name:'lng', align:'center', width:90, render:renderNull}">经度</th>
                <th data-options="{name:'lat', align:'center', width:90, render:renderNull}">纬度</th>
                <th data-options="{name:'powerid', align:'center', width:70, type:'select', items:itemsEmPower}">供电方式</th>
                <th data-options="{name:'batteryid', align:'center', width:80, type:'select', items:itemsEmBattery}">电池个数</th>
                <th data-options="{name:'batterytypeid', align:'center', width:80, type:'select', items:itemsEmBatteryType}">电池类型</th>
                <th data-options="{name:'lightid', align:'center', width:80, type:'select', items:itemsEmLight}">灯质</th>
                <th data-options="{name:'lightsourceid', align:'center', width:80, type:'select', items:itemsEmLightSource}">灯质源</th>
                <th data-options="{name:'brightlevelid', align:'center', width:80, type:'select', items:itemsEmBrightlevel}">亮度等级</th>
                <th data-options="{name:'excursusid', align:'center', width:90, type:'select', items:itemsEmExcursus}">附记</th>
                <th data-options="{name:'charginglimit', align:'center', width:80, render:renderNull}">充电电压限值</th>
                <th data-options="{name:'dischargelimit', align:'center', width:80, render:renderNull}">放电电压限值</th>
                <th data-options="{name:'forcedwork', align:'center', width:80, type:'select', items:itemsBoolean}">强制工作允许</th>
                <th data-options="{name:'syncwork', align:'center', width:80, type:'select', items:itemsBoolean}">同步工作允许</th>
            </tr>
        </thead>
    </table>
</div>