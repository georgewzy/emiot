<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
function sys_user_setarea_ZtreeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId)
    
    zTree.checkNode(treeNode, !treeNode.checked, true)
}
// save
function sys_user_setarea_save(obj) {
    var $obj     = $(obj)
    var areaData = sys_user_setarea_getAreas()
    
    $obj.bjuiajax('doAjax', {url:'user/saveArea', data:areaData, loadingmask:true})
}
//return selected areas
function sys_user_setarea_getAreas() {
    var tree     = $.fn.zTree.getZTreeObj('j_sys_user_setarea_ztree'),
        nodes    = tree.getCheckedNodes(true),
        areaData = []
    
    $.each(nodes, function(i, node) {
        areaData.push(node.id)
    })
    
    return {areaids:areaData.join(','), ids:'${ids }'}
}
</script>
<div class="bjui-pageContent">
    <form action="user/saveArea" data-toggle="validate" data-reload-navtab="true">
        <input type="hidden" name="ids" value="${ids }">
        <table class="table table-bordered">
            <tbody>
                <tr>
                    <td align="right" width="120">待设置用户:</td>
                    <td>
                        <c:forEach items="${userList }" var="s" varStatus="i">${s.name }&nbsp;&nbsp;</c:forEach>
                    </td>
                </tr>
                <%-- 
                <tr>
                    <td align="right">可管辖区域:</td>
                    <td>
                        <c:forEach items="${areaList }" var="s" varStatus="i">
                            <p style="white-space:nowrap;"><input type="checkbox" name="areaids" id="j_users_setarea_areaid_${s.id }" value="${s.id }" data-toggle="icheck" data-label="${s.name }" ${my:isContainObject(areaids, s.id) ? 'checked' : '' }>&nbsp;&nbsp;</p>
                        </c:forEach>
                    </td>
                </tr>
                --%>
                <tr>
                    <td align="right">可管辖区域:</td>
                    <td>
                        <ul id="j_sys_user_setarea_ztree" class="ztree" data-toggle="ztree" data-expand-all="true" data-check-enable="true" data-on-click="sys_user_setarea_ZtreeClick" data-setting="{check:{chkboxType:{Y:'s', N:'s'}}}">
                            <c:forEach items="${areaList }" var="s">
                                <li data-id="${s.id }" data-pid="${s.parentid }" data-checked="${!empty areaids && my:isContainObject(areaids, s.id) ? 'true' : 'false' }">${s.name }</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<c:set var="foot_btn_submit">
    <li><button type="button" class="btn-default" data-icon="save" onclick="sys_user_setarea_save(this);">保存</button></li>
</c:set>
<%@ include file="../../include/foot_edit.jsp" %>