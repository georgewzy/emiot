<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="em_record_lbs_history"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备历史信息 - LBS信息',
            dataUrl: 'em/history/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            delUrl: 'em/history/del',
            delPK: 'id',
            jsonPrefix: 'history'
        }"
    >
        <!--             select id,eminfoid,basecount,base1_rssi,lng_lbs,lat_lbs,commtime,postime -->
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:80, type:'select', render:renderNull}">ID</th>
                <th data-options="{name:'basecount', align:'center', width:90, render:renderNull}">lbs个数</th>
                <th data-options="{name:'base1_rssi', align:'center', width:90, render:renderNull}">RSSI</th>
                <th data-options="{name:'lng_lbs', align:'center', width:90, render:renderNull}">LBS经度</th>
                <th data-options="{name:'lat_lbs', align:'center', width:90, render:renderNull}">LBS纬度</th>
                <th data-options="{name:'commtime', align:'center', width:90, render:renderNull}">通信时间</th>
                <th data-options="{name:'postime', align:'center', width:90, render:renderNull}">定位时间</th>
            </tr>
        </thead>
    </table>
</div>