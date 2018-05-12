<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_history_position_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备历史信息 - 位置记录',
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
                <th data-options="{name:'positionid', align:'center', width:80, type:'select', items:itemsEmPosition}">定位方式</th>
                <th data-options="{name:'lng_agps', align:'center', width:90, render:renderNull}">AGPS经度</th>
                <th data-options="{name:'lat_agps', align:'center', width:90, render:renderNull}">AGPS纬度</th>
                <th data-options="{name:'lng_lbs', align:'center', width:90, render:renderNull}">LBS经度</th>
                <th data-options="{name:'lat_lbs', align:'center', width:90, render:renderNull}">LBS纬度</th>
                <th data-options="{name:'lng_gps', align:'center', width:90, render:renderNull}">GPS经度</th>
                <th data-options="{name:'lat_gps', align:'center', width:90, render:renderNull}">GPS纬度</th>
                <th data-options="{name:'positioncommtime', align:'center', width:160, render:renderNull}">通信时间</th>
            </tr>
        </thead>
    </table>
</div>