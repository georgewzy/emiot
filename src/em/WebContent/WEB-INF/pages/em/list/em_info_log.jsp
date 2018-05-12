<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function itemsSystemOperationType() {
    var arr = []
    <c:forEach items="${typeMap }" var="s">
        arr.push({'${s.key }':'${s.value }'})
    </c:forEach>
    return arr
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" 
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '系统操作日志表',
            showToolbar: false,
            dataUrl: 'emlist/listOperationLog?log.eminfoid=${eminfoid }',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            jsonPrefix: 'log',
            linenumberAll: true
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'type', align:'center', width:80, type:'select', items:itemsSystemOperationType}">操作类型</th>
                <th data-options="{name:'name', align:'center', width:100}">操作事件</th>
                <th data-options="{name:'beizhu', width:400}">详细操作</th>
                <th data-options="{name:'username', align:'center', width:100, render:renderNull}">操作账号</th>
                <th data-options="{name:'employeename', align:'center', width:80, render:renderNull}">员工姓名</th>
                <th data-options="{name:'createtime', align:'center', width:160, render:renderNull}">记录时间</th>
            </tr>
        </thead>
    </table>
</div>

