<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function em_type_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("em.emtype.add") ? "add" : ""}')
    item.push('${my:hasPermission("em.emtype.edit") ? "edit" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.emtype.edit") ? "cancel" : ""}')
    item.push('${my:hasPermission("em.emtype.edit") ? "save" : ""}')
    item.push('|')
    item.push('${my:hasPermission("em.emtype.del") ? "del" : ""}')
    
    return item.join(',')
}
function em_type_operation(val, data) {
    var html = '<shiro:hasPermission name="em.emtype.edit">'
             + '<button class="btn-green" data-toggle="dialog" data-url="em/type/editPic/'+ data.id +'" data-id="emtype-edit" data-width="600" data-height="300" data-mask="true" data-title="上传图片-'+ data.name +'">上传图片</button>&nbsp;'
             + '<button class="btn-blue" data-toggle="dialog" data-url="em/type/editIcon/'+ data.id +'" data-id="emtype-edit" data-width="600" data-height="300" data-mask="true" data-title="上传ICON -'+ data.name +'">上传ICON</button>&nbsp;'
             + '</shiro:hasPermission>'
     
    return html
}
function return_em_type_pic(value, data) {
    return value ? '<img src="${ctx }'+ value +'?t='+ Math.random() +'" style="max-height:100px; max-width:200px;">' : ''
}
function return_em_type_icon(value, data) {
    return value ? '<img src="${ctx }'+ value +'?t='+ Math.random() +'" style="max-height:80px; max-width:100px;">' : ''
}
function return_em_type_data() {
    return ${list }
}
</script>
<div class="bjui-pageContent">
    <table data-toggle="datagrid" class="table table-bordered" id="j_em_emtype_table"
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '设备类型信息表',
            showToolbar: true,
            toolbarItem: em_type_toolbar,
            local: 'local',
            data: return_em_type_data,
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            inlineEdit: true,
            editUrl: 'em/type/save',
            delUrl: 'em/type/del',
            delPK: 'id'
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'id', align:'center', width:100, rule:'required;digits'}">序号</th>
                <th data-options="{name:'name', align:'center', width:200, rule:'required'}">名称</th>
                <th data-options="{name:'pic', align:'center', width:200, add:false, edit:false, render:return_em_type_pic}">图片</th>
                <th data-options="{name:'icon', align:'center', width:120, add:false, edit:false, render:return_em_type_icon}">ICON</th>
                <th data-options="{align:'center', width:180, filter:false, quicksort:false, menu:false, render:em_type_operation}">操作</th>
            </tr>
        </thead>
    </table>
</div>