<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
/* ztree */
//单击事件
function sys_role_menu_ZtreeClick(event, treeId, treeNode) {
    event.preventDefault()
    
    //layout load
    $('#j_sys_role_menu_menubox').empty().show().bjuiajax('doLoad', {target:'#j_sys_role_menu_menubox', url:'role/loadMenu/'+ (treeNode.id || '')})
}

// save menu
function sys_role_menu_save(obj) {
    var zTree = $.fn.zTree.getZTreeObj('j_sys_role_menu_ztree'),
        node  = zTree.getSelectedNodes()[0],
        $obj  = $(obj)
    
    var menuData = sys_role_getMenus()
    
    $.extend(menuData, {id:node.id})
    
    $obj.bjuiajax('doAjax', {url:'role/saveMenu', data:menuData, loadingmask:true, callback:function(json) {
        $obj
            .bjuiajax('ajaxDone', json)
    }})
}
//return selected menus
function sys_role_getMenus() {
    var roleTreeId = 'j_sys_role_menu_ztree',
        menuTreeId = 'j_sys_role_menu_ztree_menu',
        roleTree   = $.fn.zTree.getZTreeObj(roleTreeId)
        menuTree   = $.fn.zTree.getZTreeObj(menuTreeId),
        current    = roleTree.getSelectedNodes()[0],
        nodes      = menuTree.getCheckedNodes(true),
        menuData   = []
    
    $.each(nodes, function(i, node) {
        menuData.push({id:node.id, name:node.name})
    })
    
    return {menus:JSON.stringify(menuData), currentId:current.id}
}

/******************** ztree - menu *********************/
function sys_role_menutree_ZtreeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId)
    
    zTree.checkNode(treeNode, !treeNode.checked, true)
}

function fixedMenuboxHeight() {
    var $menubox = $('#j_sys_role_menu_menubox'), $fieldset = $menubox.parent(), $parent = $fieldset.parent(), ph = $parent.height()
    
    $fieldset.height(ph - 10)
    $menubox.height(ph - 35)
}
fixedMenuboxHeight()
</script>
<div class="bjui-pageContent">
    <div style="float:left; padding:10px; width:300px; height:100%">
        <fieldset>
            <legend>角色树 <font class="green">点击角色-&gt;右边修改</font></legend>
            <ul class="ztree" data-toggle="ztree" id="j_sys_role_menu_ztree" data-expand-all="true" data-on-click="sys_role_menu_ZtreeClick">
                <c:forEach items="${list }" var="s">
                    <li data-id="${s.id }" data-pid="${s.parentid }">${s.name }</li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:310px; padding-top:10px; height:100%;">
        <div style="float:left; width:320px; margin-left:10px; height:100%;">
            <fieldset>
                <legend>角色菜单</legend>
                <div id="j_sys_role_menu_menubox" style="display:none;">
                    
                </div>
            </fieldset>
        </div>
    </div>
</div>