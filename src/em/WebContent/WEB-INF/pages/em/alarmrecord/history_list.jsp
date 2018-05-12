<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_history_alarmrecord_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备历史信息 - 报警信息',
            dataUrl: 'em/alarmrecord/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            delUrl: 'em/alarmrecord/del',
            delPK: 'id',
            jsonPrefix: 'ar'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'emcode', align:'center', width:90, render:renderNull}">设备编号</th>
                <th data-options="{name:'emtypeid', align:'center', width:80, type:'select', items:itemsEmType}">设备类型</th>
                <th data-options="{name:'alarmtypeid', align:'center', width:150, type:'select', items:itemsEmAlarmtype}">事件类型</th>
                <th data-options="{name:'type', align:'center', width:90, type:'select', items:itemsEmAlarmrecordtype}">记录类型</th>
                <th data-options="{name:'recordtime', align:'center', width:160, render:renderNull}">发生时间</th>
            </tr>
        </thead>
    </table>
</div>