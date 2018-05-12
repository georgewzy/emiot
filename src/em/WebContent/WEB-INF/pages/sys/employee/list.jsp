<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<%@ include file="../../include/datagrid_items.jsp"%>
<script type="text/javascript">
function sys_employee_edit($trs, datas) {
    var $table    = $('#sys-employee-table')
    
    $table.dialog({
        id: 'employee-add',
        url: 'employee/edit',
        title: '添加职工',
        mask: true,
        width: 400,
        height: 220
    })
    
    return false
}
function sys_employee_toolbar() {
    var item = []
    
    item.push('${my:hasPermission("sys.employee.add") ? "add" : ""}')
    item.push('|')
    item.push('${my:hasPermission("sys.employee.del") ? "del" : ""}')
    
    return item.join(',')
}
function sys_employee_customBtn() {
    return '<div class="btn-group" role="group">' +
           '<shiro:hasPermission name="sys.user.edit"><button class="btn-orange" onclick="sys_employee_reguser();" data-icon="user">注册用户</button></shiro:hasPermission>' +
           '</div>'
}
//
function sys_employee_operation(val, data) {
    var html = '<shiro:hasPermission name="sys.employee.edit"><button class="btn-green" data-toggle="dialog" data-url="employee/edit/'+ data.id +'" data-id="employee-edit" data-width="400" data-height="300" data-mask="true" data-title="编辑职工-'+ data.name +'">编辑</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="sys.user.edit"><button class="btn-orange" data-toggle="dialog" data-url="user/reg/'+ data.id +'" data-id="employee-reg" data-width="400" data-height="240" data-mask="true" data-title="注册用户-'+ data.name +'">注册用户</button>&nbsp;</shiro:hasPermission>'
             + '<shiro:hasPermission name="sys.employee.del"><button class="btn-red" data-toggle="doajax" data-url="employee/del?id='+ data.id +'" data-confirm-msg="确定要删除该用户吗？">删除</button></shiro:hasPermission>'
     
    return html
}
function itemsEmployeeArea() {
    var arr = []
    <c:forEach items="${emAreaList }" var="a">
        arr.push({'${a.id }':'${a.name }'})
        <c:forEach items="${a.children }" var="s">
            arr.push({'${s.id }':'${a.name } &gt; ${s.name }'})
            <c:forEach items="${s.children }" var="ss">
                arr.push({'${ss.id }':'${a.name } &gt; ${s.name } &gt; ${ss.name }'})
            </c:forEach>
        </c:forEach>
    </c:forEach>
    return arr
}
</script>
<div class="bjui-pageContent">
    <table id="sys-employee-table" data-toggle="datagrid" class="table table-bordered" 
        data-options="{
            width: '100%',
            height: '100%',
            gridTitle: '职工信息表',
            showToolbar: true,
            toolbarItem: sys_employee_toolbar,
            dataUrl: 'employee/list',
            showCheckboxcol: true,
            sortAll: true,
            filterAll: true,
            beforeEdit: sys_employee_edit,
            delUrl: 'employee/del',
            delPK: 'id',
            jsonPrefix: 'employee',
            importOption: {type:'dialog', options:{url:'employee/upload', width:500, height:300, mask:true, title:'职工信息导入'}},
            exportOption: {type:'file', options:{url:'employee/export', loadingmask:true}}
        }"
    >
        <thead>
            <tr>
                <th data-options="{name:'areaid', align:'center', width:180, type:'select', items:itemsEmployeeArea}">归属</th>
                <th data-options="{name:'name', align:'center', width:100}">职工姓名</th>
                <th data-options="{name:'sex', align:'center', width:50, type:'select', items:[{'1':'男'}, {'0':'女'}], render:function(value){ return value ? '男' : '女' }}">性别</th>
                <th data-options="{name:'mobile', align:'center', width:130, render:renderNull}">手机号</th>
                <th data-options="{name:'username', align:'center', width:80, render:renderNull}">登陆账号</th>
                <th data-options="{align:'center', width:265, filter:false, quicksort:false, menu:false, render:sys_employee_operation}">操作</th>
            </tr>
        </thead>
    </table>
</div>