<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function news_newscate_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("news.newscate.add") ? "add" : ""}')
    item.push('${my:hasPermission("news.newscate.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("news.newscate.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("news.newscate.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("news.newscate.del") ? "del" : ""}')
    
    return item.join(',')
}
function return_news_newscate_data() {
    return ${list }
}
function itemsNewsParentCate() {
    var arr = []
    arr.push({'':''})
    <c:forEach items='${parentList }' var='s'>
       arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_news_newscate_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '文档类别信息表',
            showToolbar: true,
            toolbarItem: news_newscate_toolbar,
            local: 'local',
            data: return_news_newscate_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            inlineEditMult: false,
            editUrl: 'systemnewscate/save',
            delUrl: 'systemnewscate/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:100, rule:'required;digits'}">序号</th>
                <th data-options="{name:'name', align:'center', width:100, rule:'required'}">名称</th>
                <th data-options="{name:'parentid', align:'center', width:100, type:'select', items:itemsNewsParentCate}">父级类别</th>
                <th data-options="{name:'modulename', align:'center', width:200, rule:'required;letters'}">模块名称（用于权限控制）</th>
            </tr>
        </thead>
    </table>
</div>