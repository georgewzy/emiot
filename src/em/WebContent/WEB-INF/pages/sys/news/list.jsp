<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
function sys_systemnews_edit($trs, datas) {
    var $table    = $('#sys-systemnews-table')
    
    $table.navtab({
        id: 'systemnews-edit',
        url: 'systemnews/edit',
        title: '添加文档'
    })
    
    return false
}
function sys_systemnews_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("news.newslist.add") ? "add" : ""}')
    item.push('|')
    item.push('${my:hasPermission("news.newslist.del") ? "del" : ""}')
    
    return item.join(',')
}
//
function sys_systemnews_operation(val, data) {
    var html = '<shiro:hasPermission name="news.newslist.edit"><button class="btn-green" data-toggle="navtab" data-url="systemnews/edit/'+ data.id +'" data-id="systemnews-edit" data-title="编辑文档-'+ data.title +'">编辑</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="news.newslist.del"><button class="btn-red" data-toggle="doajax" data-url="systemnews/del?id='+ data.id +'" data-confirm-msg="确定要删除该文档吗？">删除</button></shiro:hasPermission>'
     
    return html
}
function renderSystemNews(value, data) {
    var titlecolor = data.titlecolor
    
    if (titlecolor) value = '<span style="color:'+ data.titlecolor +';">'+ value +'</span>'
    
    return '<a href="systemnews/view/'+ data.id +'" data-toggle="navtab" data-options="{id:\'systemnews-view\', title:\'在线文档\'}">'+ value +'</a>'
}
function itemsSystemNewsCate() {
    var arr = []
    <c:forEach items="${cateList }" var="s">
        arr.push({'${s.id }':"${!empty s.parentid ? '&nbsp; - ' : '' }${s.name }"})
    </c:forEach>
    return arr
}
</script>
<div class="bjui-pageContent">
    <table id="sys-systemnews-table" data-toggle="datagrid" class="table table-bordered" 
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle: '在线文档列表',
            showToolbar: true,
            toolbarItem: sys_systemnews_toolbar,
            dataUrl: 'systemnews/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            beforeEdit: sys_systemnews_edit,
            delUrl: 'systemnews/del',
            delPK: 'id',
            jsonPrefix: 'systemnews'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'cateid', align:'center', width:100, type:'select', items:itemsSystemNewsCate}">所属类别</th>
                <th data-options="{name:'title', width:400, render:renderSystemNews}">标题(点击查看)</th>
                <th data-options="{name:'istop', align:'center', width:40, type:'select', items:itemsBoolean}">固顶</th>
                <th data-options="{name:'ishome', align:'center', width:60, type:'select', items:itemsBoolean}">主页显示</th>
                <th data-options="{name:'empname', align:'center', width:80, render:renderNull}">发布人</th>
                <th data-options="{name:'createtime1', align:'center', width:160, render:renderNull}">发布时间</th>
                <th data-options="{align:'center', width:120, filter:false, quicksort:false, menu:false, render:sys_systemnews_operation}">操作</th>
            </tr>
        </thead>
    </table>
</div>