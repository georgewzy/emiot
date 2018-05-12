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
            gridTitle : '设备历史信息 - 工作状态',
            dataUrl: 'em/history/list?history.eminfoid=${eminfoid }',
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
                <th data-options="{name:'emstate', align:'center', width:80, type:'select', items:[{'false':'异常'}, {'true':'正常'}]}">设备状态</th>
                <th data-options="{name:'workstateid', align:'center', width:80, type:'select', items:itemsEmWorkState}">工作状态</th>
                <th data-options="{name:'onlinestateid', align:'center', width:80, type:'select', items:itemsEmOnlineState}">在线状态</th>
                <th data-options="{name:'commid', align:'center', width:80, type:'select', items:itemsEmComm}">通信方式</th>
                <th data-options="{name:'voltage', align:'center', width:60, render:renderNull}">电压</th>
                <th data-options="{name:'current', align:'center', width:60, render:renderNull}">电流</th>
                <th data-options="{name:'lightsourceid', align:'center', width:80, type:'select', items:itemsEmLightSource}">灯质源</th>
                <th data-options="{name:'lightid', align:'center', width:80, type:'select', items:itemsEmLight}">灯质</th>
                <th data-options="{name:'workcommtime', align:'center', width:160, render:renderNull}">通信时间</th>
            </tr>
        </thead>
    </table>
</div>