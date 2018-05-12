<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
function return_em_alarmrecord_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent" style="bottom:10px !important;">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_history_position_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备报警信息',
            local: 'local',
            data: return_em_alarmrecord_data,
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
                <th data-options="{name:'alarmtypeid', align:'center', width:150, type:'select', items:itemsEmAlarmtype}">事件类型</th>
                <th data-options="{name:'type', align:'center', width:80, type:'select', items:itemsEmAlarmrecordtype}">记录类型</th>
                <th data-options="{name:'recordtime', align:'center', width:160, render:renderNull}">发生时间</th>
            </tr>
        </thead>
    </table>
</div>