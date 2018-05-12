<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
/* ztree */
//添加一级单位
function em_area_AddMainMenu() {
    var zTree = $.fn.zTree.getZTreeObj('j_em_area_ztree')
    var newNode = {name:"新增一级单位"}
    
    zTree.addNodes(null, newNode)
}

//单击事件
function em_area_ZtreeClick(event, treeId, treeNode) {
    event.preventDefault()
    
    var $box = $('#j_em_area_editbox'), $province = $box.find('#j_em_area_province_box'), $form = $box.find('#j_em_area_form'), form = $form[0],
        parentNode = treeNode.getParentNode()
    
    if ($box.attr('tid') == treeNode.tId) return
    form['id'].value         = treeNode.id || ''
    form['name'].value       = treeNode.name || ''
    form['parentname'].value = parentNode ? parentNode.name : ''
    if (treeNode.province) {
        $(form['province']).val(treeNode.province).selectpicker('refresh')
    } else {
        $(form['province']).val('').selectpicker('refresh')
    }
    
    if (treeNode.level == 0) {
        $province.show()
        $form.validator('setField', {province:'required'})
    } else {
        $province.hide()
        $form.validator('setField', {province:null})
    }
    
    $box
        .attr('tid', treeNode.tId)
        .show()
}
//开始拖拽
function em_area_BeforeNodeDrag(treeId, treeNodes) {
    //if (treeNodes[0].level == 0) return false
    return true
}
//监听拖拽事件
function em_area_BeforeNodeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    // 禁止插入第2层及以下
    var zTree = $.fn.zTree.getZTreeObj(treeId), json = zTree.transformToArray(treeNodes), allLevel = 0
    
    $.each(json, function(i, n) {
        if (allLevel < n.level) allLevel = n.level
    })
    
    if (targetNode.level)
        if (allLevel + targetNode.level + 1 > 2) return false
    
    return true
}
function em_area_getObj(node, zTree) {
    return {id:node.id, name:node.name, province:node.province, order:zTree.getNodeIndex(node), level:node.level}
}
//拖拽结束事件
function em_area_NodeDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
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
                obj = em_area_getObj(node, zTree)
            
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
            
            var obj = em_area_getObj(node, zTree)
            
            data.push(obj)
        })
    }
    
    if (parentNode) {
        setParent(parentNode)
        
        parentData = em_area_getObj(parentNode, zTree)
        
        if (parentNode.children) {
            parentData.children = setChildren(parentNode.children, childData)
        }
        data.push(parentData)
    } else {
        setSiblingsOrder()
    }
    
    if (data.length) em_area_saveTreeNode(data)
}

function em_area_getTreeNode(node, zTree, json) {
    if (json) {
        node.id       = json.id
        node.name     = json.name
        node.province = json.province
        
        zTree.updateNode(node)
    }
    node.order = zTree.getNodeIndex(node)
    
    var data = [], parentData, parentNode = node.getParentNode()
    var setParent = function(node) {
        var childData = em_area_getObj(node, zTree)
        
        if (!parentData) parentData = childData
        else parentData = $.extend({}, childData, {children:[parentData]})
        
        if (node.getParentNode()) setParent(node.getParentNode())
    }
    
    if (parentNode) {
        setParent(node)
        
        data.push(parentData)
    } else {
        data.push(em_area_getObj(node, zTree))
    }
    
    return data
}

function em_area_saveTreeNode(data) {
    var $form = $('#j_em_area_form')
    
    data = JSON.stringify(data)
    $form.bjuiajax('doAjax', {url:'em/area/save', data:{emareas:data}, callback:function(json) {
        $form
            .bjuiajax('ajaxDone', json)
            .navtab('refresh')
    }})
}

function em_area_save() {
    var $form = $('#j_em_area_form')
    var zTree = $.fn.zTree.getZTreeObj('j_em_area_ztree')
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
            
            data = em_area_getTreeNode(node, zTree, json)
            if (data.length) {
                em_area_saveTreeNode(data)
            }
        }
    })
}
//del
function em_area_NodeRemove(event, treeId, treeNode) {

    <shiro:hasPermission name="em.emarea.del">
    var $obj = $('#'+ treeId)
    
    if (treeNode.id) {
        $obj.bjuiajax('doAjax', {url:'em/area/del/'+ treeNode.id, callback:function(json) {
            $obj
                .bjuiajax('ajaxDone', json)
                //.navtab('refresh')
        }})
    }
    </shiro:hasPermission>
}
</script>
<div class="bjui-pageContent">
    <div class="bjui-fullHeight" style="float:left; margin-bottom:-20px; padding:10px; width:355px; overflow: auto;">
        <fieldset>
            <legend>单位树 <font class="green">点击单位-&gt;右边修改</font>　<a href="javascript:em_area_AddMainMenu();"><font class="red">添加一级单位</font></a></legend>
            <ul class="ztree" data-toggle="ztree" id="j_em_area_ztree" data-expand-all="false" data-on-click="em_area_ZtreeClick" data-edit-enable="true" data-add-hover-dom="edit" data-remove-hover-dom="edit" data-before-drag="em_area_BeforeNodeDrag" data-before-drop="em_area_BeforeNodeDrop" data-on-drop="em_area_NodeDrop" data-on-remove="em_area_NodeRemove">
                <c:forEach items="${list }" var="s">
                    <li data-id="${s.id }" data-pid="${s.parentid }" data-province="${s.province }">${s.name }</li>
                </c:forEach>
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:315px; margin-bottom:0; padding-top:10px; height:100%;">
        <fieldset>
            <legend>编辑单位</legend>
            <div id="j_em_area_editbox" style="display:none;">
                <form action="?" id="j_em_area_form">
                    <div class="form-group" id="j_em_area_province_box">
                        <label for="j_em_area_province" class="control-label x110">省份：</label>
                        <select id="j_em_area_province" name="province" data-toggle="selectpicker" data-rule="required">
                            <option></option>
                            <c:forEach items="${provinceList }" var="s">
                                <option value="${s.name }">${s.name }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group" id="j_em_area_parentid_box">
                        <label for="j_em_area_parentid" class="control-label x110">父级单位：</label>
                        <input type="hidden" name="parentid">
                        <input type="hidden" name="id">
                        <input type="text" name="parentname" id="j_em_area_parentid" size="15" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="j_em_area_name" class="control-label x110">单位名称：</label>
                        <input type="text" name="name" id="j_em_area_name" size="15" data-rule="required" placeholder="单位名称">
                    </div>
                    <div class="form-group" style="padding-top:8px; border-top:1px #DDD solid;">
                        <label class="control-label x110"></label>
                        <button type="button" class="btn btn-green" onclick="em_area_save(this);">保存修改</button>
                    </div>
                </form>
            </div>
        </fieldset>
    </div>
</div>