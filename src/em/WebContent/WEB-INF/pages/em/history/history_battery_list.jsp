<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_history_workstate_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备历史信息 - 电池信息',
            dataUrl: 'em/history/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            delUrl: 'em/history/del',
            delPK: 'id',
            jsonPrefix: 'history'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'emcode', align:'center', width:90, render:renderNull}">设备编号</th>
                <th data-options="{name:'emtypeid', align:'center', width:80, type:'select', items:itemsEmType}">设备类型</th>
                <th data-options="{name:'battery1_power', align:'center', width:90, render:renderNull}">电池1剩余电量</th>
                <th data-options="{name:'battery1_voltage', align:'center', width:65, render:renderNull}">电池1电压</th>
                <th data-options="{name:'battery1_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池1状态</th>
                <th data-options="{name:'battery2_power', align:'center', width:90, render:renderNull}">电池2剩余电量</th>
                <th data-options="{name:'battery2_voltage', align:'center', width:65, render:renderNull}">电池2电压</th>
                <th data-options="{name:'battery2_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池2状态</th>
                <th data-options="{name:'battery3_power', align:'center', width:90, render:renderNull}">电池3剩余电量</th>
                <th data-options="{name:'battery3_voltage', align:'center', width:65, render:renderNull}">电池3电压</th>
                <th data-options="{name:'battery3_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池3状态</th>
                <th data-options="{name:'battery4_power', align:'center', width:90, render:renderNull}">电池4剩余电量</th>
                <th data-options="{name:'battery4_voltage', align:'center', width:65, render:renderNull}">电池4电压</th>
                <th data-options="{name:'battery4_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池4状态</th>
                <th data-options="{name:'battery5_power', align:'center', width:90, render:renderNull}">电池5剩余电量</th>
                <th data-options="{name:'battery5_voltage', align:'center', width:65, render:renderNull}">电池5电压</th>
                <th data-options="{name:'battery5_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池5状态</th>
                <th data-options="{name:'battery6_power', align:'center', width:90, render:renderNull}">电池6剩余电量</th>
                <th data-options="{name:'battery6_voltage', align:'center', width:65, render:renderNull}">电池6电压</th>
                <th data-options="{name:'battery6_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池6状态</th>
                <th data-options="{name:'battery7_power', align:'center', width:90, render:renderNull}">电池7剩余电量</th>
                <th data-options="{name:'battery7_voltage', align:'center', width:65, render:renderNull}">电池7电压</th>
                <th data-options="{name:'battery7_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池7状态</th>
                <th data-options="{name:'battery8_power', align:'center', width:90, render:renderNull}">电池8剩余电量</th>
                <th data-options="{name:'battery8_voltage', align:'center', width:65, render:renderNull}">电池8电压</th>
                <th data-options="{name:'battery8_stateid', align:'center', width:65, type:'select', items:itemsEmBatteryState}">电池8状态</th>
                <th data-options="{name:'batterycommtime', align:'center', width:160, render:renderNull}">通信时间</th>
            </tr>
        </thead>
    </table>
</div>