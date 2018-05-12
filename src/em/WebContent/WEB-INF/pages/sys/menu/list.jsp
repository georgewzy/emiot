<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
/* ztree */
//添加主菜单
function sys_menu_AddMainMenu() {
    var zTree = $.fn.zTree.getZTreeObj('j_sys_menu_ztree')
    var newNode = {name:"新增主菜单"}
    
    zTree.addNodes(null, newNode)
}

//单击事件
function sys_menu_ZtreeClick(event, treeId, treeNode) {
    event.preventDefault()
    
    var $box = $('#j_sys_menu_editbox'), $form = $box.find('#j_sys_menu_form'), form = $form[0],
        parentNode = treeNode.getParentNode()
    
    if ($box.attr('tid') == treeNode.tId) return
    form['id'].value       = treeNode.id || ''
    form['name'].value     = treeNode.name || ''
    form['url'].value      = treeNode.url || ''
    form['icon'].value     = treeNode.icon || ''
    form['targetid'].value = treeNode.targetid || ''
    if (!treeNode.target) $(form['target']).iCheck('uncheck')
    else $(form['target']).filter('[value='+ treeNode.target +']').iCheck('check')
    form['parentname'].value = parentNode ? parentNode.name : ''
    
    if (treeNode.level < 2) {
        $form.validator('setField', {url:null, target:null})
    } else {
        $form.validator('setField', {url:'required', target:'checked'})
    }
    
    $box
        .attr('tid', treeNode.tId)
        .show()
}
//开始拖拽
function sys_menu_BeforeNodeDrag(treeId, treeNodes) {
    //if (treeNodes[0].level == 0) return false
    return true
}
//监听拖拽事件
function sys_menu_BeforeNodeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    // 禁止插入第1层
    //if (targetNode.level == 0) return false
    
    return true
}
function sys_menu_getObj(node, zTree) {
    return {id:node.id, name:node.name, url:node.url, target:node.target, icon:node.icon, targetid:node.targetid, order:zTree.getNodeIndex(node), level:node.level}
}
//拖拽结束事件
function sys_menu_NodeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    var zTree = $.fn.zTree.getZTreeObj(treeId), data = [], parentData, childData = [], parentNode = treeNodes[0].getParentNode()
    
    var setParent = function(node) {
        var parent = node.getParentNode()
        
        if (parent) {
            if (parent.getParentNode()) setParent(parent)
            else parentNode = parent
        } else {
            parentNode = node
        }
    }
    var setChildren = function(nodes, o) {
        $.each(nodes, function(i, node) {
            var children = node.children,
                obj = sys_menu_getObj(node, zTree)
            
            o.push(obj)
            
            if (children && children.length) obj.children = setChildren(children, [])
        })
        
        return o
    }
    var setSiblingsOrder = function() {
        var allNodes = zTree.getNodesByFilter(function filter(node) {
            return node.level == treeNodes[0].level
        })
        
        $.each(allNodes, function(i, node) {
            if (!node.getParentNode())
                node.parentid = ''
            
            var obj = sys_menu_getObj(node, zTree)
            
            data.push(obj)
        })
    }
    
    if (parentNode) {
        setParent(parentNode)
        
        parentData = sys_menu_getObj(parentNode, zTree)
        
        if (parentNode.children) {
            parentData.children = setChildren(parentNode.children, childData)
        }
        data.push(parentData)
    } else {
        setSiblingsOrder()
    }
    
    if (data.length) sys_menu_saveTreeNode(data)
}

function sys_menu_getTreeNode(node, zTree, json) {
    if (json) {
        node.id       = json.id
        node.name     = json.name
        node.url      = json.url
        node.target   = json.target
        node.icon     = json.icon
        node.targetid = json.targetid
        
        zTree.updateNode(node)
    }
    node.order = zTree.getNodeIndex(node)
    
    var data = [], parentData, parentNode = node.getParentNode()
    var setParent = function(node) {
        var childData = sys_menu_getObj(node, zTree)
        
        if (!parentData) parentData = childData
        else parentData = $.extend({}, childData, {children:[parentData]})
        
        if (node.getParentNode()) setParent(node.getParentNode())
    }
    
    if (parentNode) {
        setParent(node)
        
        data.push(parentData)
    } else {
        data.push(sys_menu_getObj(node, zTree))
    }
    
    return data
}

function sys_menu_saveTreeNode(data) {
    var $form = $('#j_sys_menu_form')
    
    data = JSON.stringify(data)
    $form.bjuiajax('doAjax', {url:'menu/save', data:{menus:data}, callback:function(json) {
        $form
            .bjuiajax('ajaxDone', json)
            .navtab('refresh')
    }})
}

function sys_menu_save() {
    var $form = $('#j_sys_menu_form')
    var zTree = $.fn.zTree.getZTreeObj('j_sys_menu_ztree')
    var node  = zTree.getSelectedNodes()[0]
    
    if (!$form.data('validator')) {
        $form.validator({
            validClass : 'ok',
            theme      : 'red_right_effect'
        })
    }
    
    $form.isValid(function(v) {
        if (v) {
            var json = $form.serializeJson()
            
            data = sys_menu_getTreeNode(node, zTree, json)
            if (data.length) {
                sys_menu_saveTreeNode(data)
            }
        }
    })
}
//del
function sys_menu_NodeRemove(event, treeId, treeNode) {

    <shiro:hasPermission name="sys.menu.del">
    var $obj = $('#'+ treeId)
    
    if (treeNode.id) {
        $obj.bjuiajax('doAjax', {url:'menu/del/'+ treeNode.id, callback:function(json) {
            $obj
                .bjuiajax('ajaxDone', json)
                //.navtab('refresh')
        }})
    }
    </shiro:hasPermission>
}
</script>
<div class="bjui-pageHeader hide">
    <div class="alert alert-warning form-block">* 一级菜单显示在横向菜单位置</div>
</div>
<div class="bjui-pageContent">
    <div class="bjui-fullHeight" style="float:left; padding:10px; width:355px; overflow: auto;">
        <fieldset>
            <legend>菜单树 <font class="green">点击菜单-&gt;右边修改</font>　<a href="javascript:sys_menu_AddMainMenu();"><font class="red">添加主菜单</font></a></legend>
            <ul class="ztree" data-toggle="ztree" id="j_sys_menu_ztree" data-expand-all="false" data-on-click="sys_menu_ZtreeClick" data-edit-enable="true" data-add-hover-dom="edit" data-remove-hover-dom="edit" data-before-drag="sys_menu_BeforeNodeDrag" data-before-drop="sys_menu_BeforeNodeDrop" data-on-drop="sys_menu_NodeDrop" data-on-remove="sys_menu_NodeRemove">
                <c:forEach items="${list }" var="s">
                    <li data-id="${s.id }" data-pid="${s.parentid }" data-url="${s.url }" data-target="${s.target }" data-icon="${s.icon }" data-targetid="${s.targetid }">${s.name }</li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:315px; margin-bottom:-60px; padding-top:10px; height:100%;">
        <fieldset>
            <legend>编辑菜单</legend>
            <div id="j_sys_menu_editbox" style="display:none;">
                <form action="?" id="j_sys_menu_form">
                    <div class="form-group" id="j_menu_parentid_box">
                        <label for="j_menu_parentid" class="control-label x110">父菜单：</label>
                        <input type="hidden" name="parentid">
                        <input type="hidden" name="id">
                        <input type="text" name="parentname" id="j_menu_parentid" size="15" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="j_menu_name" class="control-label x110">菜单名称：</label>
                        <input type="text" name="name" id="j_menu_name" size="15" data-rule="required" placeholder="菜单名称">
                    </div>
                    <div class="form-group">
                        <label for="j_menu_targetid" class="control-label x110">targetid：</label>
                        <input type="text" name="targetid" id="j_menu_targetid" size="15" placeholder="targetid">
                    </div>
                    <div class="form-group">
                        <label for="j_menu_url" class="control-label x110">菜单URL：</label>
                        <input type="text" name="url" id="j_menu_url" size="15" placeholder="菜单URL">
                    </div>
                    <div class="form-group">
                        <label class="control-label x110">菜单target：</label>
                        <input type="radio" name="target" id="j_menu_target_navtab" value="navtab" data-toggle="icheck" data-label="navtab ">
                        <input type="radio" name="target" id="j_menu_target_dialog" value="dialog" data-toggle="icheck" data-label="dialog ">
                        <input type="radio" name="target" id="j_menu_target_blank" value="blank" data-toggle="icheck" data-label="_blank">
                    </div>
                    <div class="form-group">
                        <label for="j_menu_icon" class="control-label x110">菜单icon：</label>
                        <input type="text" name="icon" id="j_menu_icon" size="15" placeholder="菜单icon">
                    </div>
                    <div class="form-group" style="padding-top:8px; border-top:1px #DDD solid;">
                        <label class="control-label x110"></label>
                        <button type="button" class="btn btn-green" onclick="sys_menu_save(this);">保存修改</button>
                    </div>
                </form>
            </div>
        </fieldset>
    </div>
</div>