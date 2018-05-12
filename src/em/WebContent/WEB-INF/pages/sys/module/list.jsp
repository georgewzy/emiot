<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
function sys_module_showfunction(obj, index) {
    var $obj = $(obj), $tr = $obj.closest('tr'), $child = $tr.siblings('.sys_module_function_'+ index)
    
    if ($child.is(':hidden')) {
        $child.fadeIn()
        $obj.find('> i').attr('class', 'fa fa-minus-square-o')
    } else {
        $child.fadeOut()
        $obj.find('> i').attr('class', 'fa fa-plus-square-o')
    }
}
function sys_module_openfunction(id) {
    if (id) {
        $('#sys_module_openfunction_'+ id).trigger('click') 
    }
}
sys_module_openfunction('${moduleid }')
</script>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="module" method="post">
        <input type="hidden" name="pageSize" value="${page.pageSize}">
        <input type="hidden" name="pageCurrent" value="${page.pageNumber}">
        <div class="bjui-searchBar">
            <label>模块名称：</label><input type="text" value="${module.name }" name="module.name" size="10">&nbsp;
            <label>模块说明：</label><input type="text" value="${module.shuoming }" name="module.shuoming" size="10">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>
            &nbsp;<a class="btn btn-orange" href="javascript:;" data-toggle="reloadsearch" data-clear-query="true" data-icon="undo">清空查询</a>
            <shiro:hasPermission name="sys.module.add">&nbsp;<a class="btn btn-green" href="module/edit" data-toggle="dialog" data-width="500" data-height="300" data-mask="true" data-icon="plus">添加模块</a></shiro:hasPermission>
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <div style="width:650px; height:100%;">
        <form action="module/saveOrder" id="sys_module_form" class="pageForm" data-toggle="validate" method="post">
            <input type="hidden" name="count" value="${fn:length(page.list) }">
            <table class="table table-bordered table-hover table-striped table-top">
                <thead>
                    <tr>
                        <th></th>
                        <th>顺序</th>
                        <th>模块名称</th>
                        <th>模块说明</th>
                        <th width="220">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list }" var="s" varStatus="i">
                        <tr>
                            <td align="center"><a href="javascript:;" id="sys_module_openfunction_${s.id }" onclick="sys_module_showfunction(this, ${i.index });"><i class="fa fa-plus-square-o"></i></a></td>
                            <td>
                                <input type="hidden" name="moduleList[${i.index }].id" value="${s.id }">
                                <input type="text" name="moduleList[${i.index }].seq" value="${s.seq }" size="6" data-rule="digits">
                            </td>
                            <td>${s.name }</td>
                            <td>${s.shuoming }</td>
                            <td data-noedit="true">
                                <shiro:hasPermission name="sys.module.edit"><a class="btn btn-green" href="module/edit/${s.id }" data-toggle="dialog" data-width="500" data-height="300" data-mask="true" data-icon="edit">编辑</a></shiro:hasPermission>
                                <shiro:hasPermission name="sys.module.add">&nbsp;<a class="btn btn-blue" href="module/editFunction/${s.id }" data-toggle="dialog" data-width="500" data-height="300" data-mask="true" data-icon="dot-circle-o">添加子模块</a></shiro:hasPermission>
                                <shiro:hasPermission name="sys.module.del">&nbsp;<a href="module/del/${s.id }" class="btn btn-red row-del" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删</a></shiro:hasPermission>
                            </td>
                        </tr>
                        <c:if test="${!empty s.functionList && fn:length(s.functionList) > 0 }">
                            <tr></tr>
                            <tr style="display:none;" class="sys_module_function_${i.index }">
                                <td colspan="6">
                                    <input type="hidden" name="moduleList[${i.index }].function_count" value="${fn:length(s.functionList) }">
                                    <div style="padding:10px;">
                                        <table class="table table-bordered table-hover table-striped table-top">
                                            <tbody>
                                                <tr>
                                                    <td><strong>功能顺序</strong></td>
                                                    <td><strong>功能名称</strong></td>
                                                    <td><strong>功能说明</strong></td>
                                                    <td width="150"><strong>操作</strong></td>
                                                </tr>
                                                <c:forEach items="${s.functionList }" var="f" varStatus="j">
                                                    <tr>
                                                        <td>
                                                            <input type="hidden" name="functionList[${i.index }][${j.index }].id" value="${f.id }">
                                                            <input type="text" name="functionList[${i.index }][${j.index }].seq" value="${f.seq }" size="6" data-rule="digits">
                                                        </td>
                                                        <td>${f.name }</td>
                                                        <td>${f.shuoming }</td>
                                                        <td>
                                                            <shiro:hasPermission name="sys.module.edit"><a class="btn btn-green" href="module/editFunction/${s.id }-${f.id}" data-toggle="dialog" data-width="500" data-height="300" data-mask="true" data-icon="edit">编辑</a></shiro:hasPermission>
                                                            <shiro:hasPermission name="sys.module.del">&nbsp;<a href="module/delFunction/${f.id }" class="btn btn-red row-del" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删</a></shiro:hasPermission>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <li><button type="submit" class="btn-default" data-icon="save">保存顺序</button></li>
    </ul>
</div>