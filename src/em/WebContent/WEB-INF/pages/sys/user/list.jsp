<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function sys_userinfo_edit($trs, datas) {
    $('#sys-userinfo-table').dialog({
        id    : 'user-edit',
        url   : 'user/edit/'+ id,
        width : 400,
        height: 240,
        title : '添加用户',
        mask  : true
    })
    
    return false
}
function sys_userinfo_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("sys.user.del") ? "del" : ""}')
    
    return item.join(',')
}
function sys_userinfo_customBtn() {
    return '<shiro:hasPermission name="sys.user.edit"><button class="btn-orange" onclick="sys_userinfo_resetpassword();">重置密码</button></shiro:hasPermission>'
}
function sys_userinfo_operation(val, data) {
    var html = '<shiro:hasPermission name="sys.user.edit"><button class="btn-green" data-toggle="dialog" data-url="user/edit/'+ data.id +'" data-id="userinfo-edit" data-width="400" data-height="240" data-mask="true" data-title="编辑用户-'+ data.username +'">编辑</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="sys.user.edit"><button class="btn-orange" data-toggle="dialog" data-url="user/resetPass/'+ data.id +'" data-id="userinfo-edit" data-width="400" data-height="240" data-mask="true" data-title="重置密码-'+ data.username +'">重置密码</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="sys.user.edit"><button class="btn-blue" data-toggle="navtab" data-url="user/setArea/?id='+ data.id +'" data-id="userinfo-setarea" data-title="设置可操作管辖区域-'+ data.username +'">设置管辖区域</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="sys.user.del"><button class="btn-red" data-toggle="doajax" data-url="user/del?id='+ data.id +'" data-confirm-msg="确定要删除该用户吗？">删除</button></shiro:hasPermission>'
     
    return html
}
function sys_userinfo_customBtn() {
    return '<div class="btn-group" role="group">' +
           '<shiro:hasPermission name="sys.user.edit"><button class="btn-blue" onclick="sys_userinfo_setArea_selected(this);" data-icon="check-square-o">选中项：设置管辖区域</button></shiro:hasPermission>' +
           '</div>'
}
function sys_userinfo_setArea_selected() {
    var $table = $('#sys-userinfo-table'),
        datas  = $table.data('selectedDatas'),
        json   = []
    
    if (!datas || !datas.length) {
        $table.alertmsg('info', '请至少选择一个要设置管辖区域的用户！')
        return;
    }
    
    $.each(datas, function(i, n) {
        json.push({id:n.id, name:n.name})
    })
    
    $table.navtab({
        url    : 'user/setArea',
        id     : 'userinfo-setarea',
        type   : 'POST',
        data   : {json:JSON.stringify(json)},
        title  : '批量设置用户操作单位'
    })
    
    return false
}
</script>
<div class="bjui-pageContent">
    <table id="sys-userinfo-table" data-toggle="datagrid" class="table table-bordered" 
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle : '用户信息表',
            showToolbar: true,
            toolbarItem: sys_userinfo_toolbar,
            toolbarCustom: sys_userinfo_customBtn,
            dataUrl: 'user/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            beforeEdit: sys_userinfo_edit,
            delUrl: 'user/del',
            delPK: 'userid',
            jsonPrefix: 'user',
            linenumberAll: true,
            importOption: {type:'dialog', options:{url:'user/upload', width:500, height:300, mask:true, title:'用户信息导入'}},
            exportOption: {type:'file', options:{url:'user/export', loadingmask:true}}
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'username', align:'center', width:200}">登陆名</th>
                <th data-options="{name:'name', align:'center', width:200, render:renderNull}">姓名</th>
                <th data-options="{name:'rolenames', align:'center', width:200, render:renderNull}">所属角色</th>
                <th data-options="{name:'areanames', align:'center', width:200, render:renderNull}">管辖区域</th>
                <th data-options="{align:'center', width:320, filter:false, quicksort:false, menu:false, render:sys_userinfo_operation}">操作</th>
            </tr>
        </thead>
    </table>
</div>

