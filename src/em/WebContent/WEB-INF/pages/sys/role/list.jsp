<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
/* ztree */
//单击事件
function sys_role_ZtreeClick(event, treeId, treeNode) {
    event.preventDefault()
    
    var $editBox = $('#j_sys_role_editbox'),
        $form = $editBox.find('#j_sys_role_form'), form = $form[0],
        parentNode = treeNode.getParentNode()
    
    if ($editBox.attr('tid') == treeNode.tId) return
    form['id'].value   = treeNode.id || ''
    form['name'].value = treeNode.name || ''
    form['parentname'].value = parentNode ? parentNode.name : ''
    
    $editBox
        .attr('tid', treeNode.tId)
        .show()
}
//开始拖拽
function sys_role_BeforeNodeDrag(treeId, treeNodes) {
    if (treeNodes[0].level == 0) return false
    return true
}
//监听拖拽事件
function sys_role_BeforeNodeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    // 禁止插入第1层
    if (!targetNode || !targetNode.level) return false
    
    return true
}
function sys_role_getObj(node, zTree) {
    return {id:node.id, name:node.name, order:zTree.getNodeIndex(node), level:node.level}
}
//拖拽结束事件
function sys_role_NodeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
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
                obj = sys_role_getObj(node, zTree)
            
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
            
            var obj = sys_role_getObj(node, zTree)
            
            data.push(obj)
        })
    }
    
    if (parentNode) {
        setParent(parentNode)
        
        parentData = sys_role_getObj(parentNode, zTree)
        
        if (parentNode.children) {
            parentData.children = setChildren(parentNode.children, childData)
        }
        data.push(parentData)
    } else {
        setSiblingsOrder()
    }
    
    if (data.length) sys_role_saveTreeNode(data)
}

function sys_role_getTreeNode(node, zTree, json) {
    if (json) {
        node.id   = json.id
        node.name = json.name
        
        zTree.updateNode(node)
    }
    node.order = zTree.getNodeIndex(node)
    
    var data = [], parentData, parentNode = node.getParentNode()
    var setParent = function(node) {
        var childData = sys_role_getObj(node, zTree)
        
        if (!parentData) parentData = childData
        else parentData = $.extend({}, childData, {children:[parentData]})
        
        if (node.getParentNode()) setParent(node.getParentNode())
    }
    
    if (parentNode) {
        setParent(node)
        
        data.push(parentData)
    } else {
        data.push(sys_role_getObj(node, zTree))
    }
    
    return data
}

function sys_role_saveTreeNode(data) {
    var $form = $('#j_sys_role_form')
    
    data = JSON.stringify(data)
    data = {roles:data}
    $form.bjuiajax('doAjax', {url:'role/save', data:data, callback:function(json) {
        $form
            .bjuiajax('ajaxDone', json)
            .navtab('refresh')
    }})
}
// save property
function sys_role_save() {
    var $form = $('#j_sys_role_form')
    var zTree = $.fn.zTree.getZTreeObj('j_sys_role_ztree')
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
            
            data = sys_role_getTreeNode(node, zTree, json)
            if (data.length) {
                sys_role_saveTreeNode(data)
            }
        }
    })
}
//del
function sys_role_NodeRemove(event, treeId, treeNode) {
    <shiro:hasPermission name="sys.role.del">
    var $obj = $('#'+ treeId)
    
    if (treeNode.id) {
        $obj.bjuiajax('doAjax', {url:'role/del/'+ treeNode.id, callback:function(json) {
            $obj
                .bjuiajax('ajaxDone', json)
        }})
    }
    </shiro:hasPermission>
}
</script>
<div class="bjui-pageContent">
    <div class="bjui-fullHeight" style="float:left; padding:10px; width:315px; overflow: auto;">
        <fieldset>
            <legend>角色树 <font class="green">点击角色-&gt;右边修改</font></legend>
            <ul class="ztree" data-toggle="ztree" id="j_sys_role_ztree" data-expand-all="true" data-max-add-level="5" data-on-click="sys_role_ZtreeClick" data-edit-enable="true" data-add-hover-dom="edit" data-remove-hover-dom="edit" data-before-drag="sys_role_BeforeNodeDrag" data-before-drop="sys_role_BeforeNodeDrop" data-on-drop="sys_role_NodeDrop" data-on-remove="sys_role_NodeRemove">
                <c:forEach items="${list }" var="s">
                    <li data-id="${s.id }" data-pid="${s.parentid }">${s.name }</li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:310px; padding-top:10px; height:100%;">
        <div style="float:left; width:450px;">
            <fieldset>
                <legend>编辑角色</legend>
                <div id="j_sys_role_editbox" style="display:none;">
                    <form action="?" id="j_sys_role_form">
                        <div class="form-group" id="j_role_parentid_box">
                            <label for="j_role_parentid" class="control-label x85">父角色：</label>
                            <input type="hidden" name="parentid">
                            <input type="hidden" name="id">
                            <input type="text" name="parentname" id="j_role_parentid" size="15" readonly="readonly">
                        </div>
                        <div class="form-group">
                            <label for="j_role_name" class="control-label x85">角色名称：</label>
                            <input type="text" name="name" id="j_role_name" size="15" data-rule="required" placeholder="角色名称">
                        </div>
                        <div class="form-group">
                            <hr>
                            <label class="x85"></label>
                            <button type="button" class="btn btn-green" onclick="sys_role_save(this);">保存修改</button>
                        </div>
                    </form>
                </div>
            </fieldset>
        </div>
    </div>
</div>