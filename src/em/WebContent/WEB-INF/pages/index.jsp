<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="include/includetag.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>设备管理系统</title>
<!-- bootstrap - css -->
<link href="${ctx }B-JUI/themes/css/bootstrap.css" rel="stylesheet">
<!-- core - css -->
<link href="${ctx }B-JUI/themes/css/style.css" rel="stylesheet">
<link href="${ctx }B-JUI/themes/blue/core.css" id="bjui-link-theme" rel="stylesheet">
<!-- plug - css -->
<link href="${ctx }B-JUI/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
<link href="${ctx }B-JUI/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="${ctx }B-JUI/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
<link href="${ctx }B-JUI/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
<link href="${ctx }B-JUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
<!--[if lte IE 7]>
<link href="${ctx }B-JUI/themes/css/ie7.css" rel="stylesheet">
<![endif]-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lte IE 9]>
    <script src="${ctx }B-JUI/other/html5shiv.min.js"></script>
    <script src="${ctx }B-JUI/other/respond.min.js"></script>
<![endif]-->
<!-- jquery -->
<script src="${ctx }B-JUI/js/jquery-1.7.2.min.js"></script>
<script src="${ctx }B-JUI/js/jquery.cookie.js"></script>
<!--[if lte IE 9]>
<script src="${ctx }B-JUI/other/jquery.iframe-transport.js"></script>
<![endif]-->
<!-- B-JUI -->
<script src="${ctx }B-JUI/js/bjui-all.min.js"></script>
<!-- plugins -->
<!-- swfupload for uploadify && kindeditor -->
<script src="${ctx }B-JUI/plugins/swfupload/swfupload.js"></script>
<!-- kindeditor -->
<script src="${ctx }B-JUI/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
<script src="${ctx }B-JUI/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
<!-- colorpicker -->
<script src="${ctx }B-JUI/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- ztree -->
<script src="${ctx }B-JUI/plugins/ztree/jquery.ztree.all-3.5.js"></script>
<script src="${ctx }B-JUI/plugins/ztree/jquery.ztree.exhide-3.5.js"></script>
<!-- nice validate -->
<script src="${ctx }B-JUI/plugins/niceValidator/jquery.validator.js"></script>
<script src="${ctx }B-JUI/plugins/niceValidator/jquery.validator.themes.js"></script>
<!-- bootstrap plugins -->
<script src="${ctx }B-JUI/plugins/bootstrap.min.js"></script>
<script src="${ctx }B-JUI/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
<script src="${ctx }B-JUI/plugins/bootstrapSelect/defaults-zh_CN.min.js"></script>
<!-- icheck -->
<script src="${ctx }B-JUI/plugins/icheck/icheck.min.js"></script>
<!-- dragsort -->
<script src="${ctx }B-JUI/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
<!-- ECharts -->
<script src="${ctx }B-JUI/plugins/echarts/echarts.js"></script>
<!-- other plugins -->
<script src="${ctx }B-JUI/plugins/other/jquery.autosize.js"></script>
<link href="${ctx }B-JUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<script src="${ctx }B-JUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="${ctx }B-JUI/plugins/download/jquery.fileDownload.js"></script>
<script src="${ctx }js/aes.js"></script>
<script src="${ctx }js/sha256.js"></script>
<script src="${ctx }js/jquery.jqprint-0.3.js"></script>
<script src="${ctx }js/jquery.rowspan.js"></script>
<!-- MAP -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=8K1W6fpCHEvHHcDwCr5jaQmi"></script>
<!-- init -->
<script type="text/javascript">
$(function() {
    BJUI.init({
        JSPATH       : '${ctx }B-JUI/',         //[可选]框架路径
        PLUGINPATH   : '${ctx }B-JUI/plugins/', //[可选]插件路径
        loginInfo    : {url:'${ctx }login/logintimeout', title:'登录', width:500, height:280}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]
        ajaxTimeout  : 200000, //[可选]全局Ajax请求超时时间(毫秒)
        alertTimeout : 3000,  //[可选]信息提示[info/correct]自动关闭延时(毫秒)
        pageInfo     : {total:'totalRow', pageCurrent:'pageCurrent', pageSize:'pageSize', orderField:'em.code', orderDirection:'asc'}, //[可选]分页参数
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         clientPaging     : true, //[可选]是否在客户端响应分页及排序参数
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'green' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
    //时钟
    var today = new Date(), time = today.getTime()
    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
    setInterval(function() {
        today = new Date(today.setSeconds(today.getSeconds() + 1))
        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
    }, 1000)
    
    // full - height
    $(document).on(BJUI.eventType.afterInitUI, function(e) {
        var $box = $(e.target)
        
        $box.find('.bjui-fullHeight').each(function() {
            var $full = $(this), $parent = $full.parent(), $other = $full.siblings(),
                pH = $parent.outerHeight(), otherH = 0
            
            if ($full.css('float') == 'none') {
                $other.each(function() {
                    var $o = $(this)
                    
                    if ($o.css('float') == 'none')
                        otherH += $(this).outerHeight()
                })
            }
            
            $full.height(pH - otherH)
        })
    })
    
    // main - menu
    $('#bjui-accordionmenu')
        .collapse()
        .on('hidden.bs.collapse', function(e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if ($a.hasClass('collapsed')) $heading.removeClass('active')
            })
        })
        .on('shown.bs.collapse', function (e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if (!$a.hasClass('collapsed')) $heading.addClass('active')
            })
        })
        
    $(document).on('click', 'ul.menu-items li > a', function(e) {
        var $a = $(this), $li = $a.parent(), options = $a.data('options').toObj(), $children = $li.find('> .menu-items-children')
        var onClose = function() {
            $li.removeClass('active')
        }
        var onSwitch = function() {
            $('#bjui-accordionmenu').find('ul.menu-items li').removeClass('switch')
            $li.addClass('switch')
        }
        
        $li.addClass('active')
        if (options) {
            options.url      = $a.attr('href')
            options.onClose  = onClose
            options.onSwitch = onSwitch
            if (!options.title) options.title = $a.text()
            
            if (!options.target || options.target == 'navtab')
                $a.navtab(options)
            else
                $a.dialog(options)
        }
        if ($children.length) {
            $li.toggleClass('open')
        }
        
        e.preventDefault()
    })
    
})

// datagrid - render null
function renderNull(value) {
    return value == null ? '' : value
}
//datagrid - render boolean
function renderBoolean(value) {
    return value ? '是' : ''
}
//datagrid - render int 2 boolean
function renderInt2Boolean(value) {
    return value == '1' ? '是' : '否'
}
//datagrid - item boolean
function itemsBoolean() {
    return [{'true':'是'}, {'false':'否'}]
}
//datagrid - item Sex(int)
function itemsSex() {
    return [{'1':'男'}, {'0':'女'}]
}
//datagrid - item State(int)
function itemsState() {
    return [{'true':'启用'}, {'false':'禁用'}]
}

//console.log('IE:'+ (!$.support.leadingWhitespace))
//菜单-事件
function MainMenuClick(event, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId)
        
        zTree.expandNode(treeNode)
        return
    }
    
    if (treeNode.target && treeNode.target == 'dialog')
        $(event.target).dialog({id:treeNode.targetid, url:treeNode.url, title:treeNode.name, mask:true, width:600, height:400})
    else
        $(event.target).navtab({id:treeNode.targetid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
    
    event.preventDefault()
}

//设备zTree 隐藏没有设备的节点
$(document).one(BJUI.eventType.afterInitUI, function(e) {
    var tree  = $.fn.zTree.getZTreeObj('bjui-hnav-tree-me'),
        nodes = tree.getNodesByParam('level', 0, null)
    
    var hideChild = function(node) {
        var em_children = tree.getNodesByFilter(function filter(node) {
            return node.eminfo
        }, false, node)
        
        if (!em_children.length) {
            tree.hideNode(node)
        } else {
            $.each(node.children, function(i, n) {
                if (n.children) hideChild(n)
                else {
                    if (!n.eminfo) tree.hideNode(n)
                }
            })
        }
    }
    
    $.each(nodes, function(i, n) {
        if (n.children) hideChild(n)
        else {
            if (!n.eminfo)
                tree.hideNode(n)
        }
    })
    
})

//zTree 单、双击定义
var em_map_info_timer   = null // delay click for dblclick
var em_map_info_refresh = function() {return false}

//设备信息 -- 单击事件（详细信息）
function EmInfoMenuClick(event, treeId, treeNode) {
    em_map_info_timer && clearTimeout(em_map_info_timer)
    
    event.preventDefault()
    
    if (!treeNode.eminfo) {
        //判断是否重复打开
        var areaids = $('#j_em_map_info_areaids').val()
        
        if (areaids) {
            var arrids = areaids.split(',')
            
            if (arrids.myIndexOf(treeNode.id) != -1) {
                $(event.target).navtab('switchTab', 'em-map-list')
                return false
            }
        }
        $(event.target).navtab({id:'em-map-list', url:'emmap/?areaid='+ treeNode.id, title:'地图分布'})
    } else {
        //$(event.target).navtab({id:'em-map-list', url:'emmap/'+ treeNode.id, title:'地图分布'})
        em_map_info_timer = setTimeout(function() {
            $(event.target).navtab({id:'em-info-list', url:'emlist/'+ treeNode.id, title:'设备：'+ treeNode.name})
        }, 300)
    }
}
//设备信息 - 双击事件（地图）
function EmInfoMenuDblClick(event, treeId, treeNode) {
    em_map_info_timer && clearTimeout(em_map_info_timer)
    if (!treeNode.level) return false
    event.preventDefault()
    
    var emid = $('#j_em_map_info_id').val()
    
    if (!emid && !em_map_info_refresh())
        $(event.target).navtab({id:'em-map-list', url:'emmap/'+ treeNode.id, title:'地图分布'})
    else
        em_map_info_refresh(treeNode.id)
}

function CtoH(str){ 
    var result = ''
    
    for (var i = 0; i < str.length; i++){
        if (str.charCodeAt(i) == 12288) {
            result += String.fromCharCode(str.charCodeAt(i) - 12256)
            continue
        }
        if (str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375) result += String.fromCharCode(str.charCodeAt(i) - 65248)
        else result += String.fromCharCode(str.charCodeAt(i))
    }
    
    return result
}

</script>

<style type="text/css">
html, body, div, span, applet, object, iframe,
 p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, font, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
dl, dt, dd, ol, ul, li,
fieldset, form, input, textarea, select, button, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td{font-family:"Verdana", "Tahoma", "Lucida Grande", "Simsun", "Hiragino Sans GB", sans-serif;}
span.required {
  display: inline-block;
  width: 16px;
  min-height: 24px;
  vertical-align: middle;
  background: url(${ctx }B-JUI/themes/css/img/error-bg.png) no-repeat center right;
}
.ztree_main span{font-family:"Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
#bjui-header{height:38px;}
ul.hasdot{margin-left:10px;}
ul.hasdot > li{list-style:disc;}
</style>
</head>
<body>
    <!--[if lte IE 7]>
        <div id="errorie"><div>您还在使用老掉牙的IE，正常使用系统前请升级您的浏览器到 IE8以上版本 <a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div></div>
    <![endif]-->
    <object classid="clsid:4B3CB088-9A00-4D24-87AA-F65C58531039" id="SynCardOcx1" codeBase="SynCardOcx.CAB#version=1,0,0,1" width="102" height="126" style="display:none;">
    </object>
    <header id="bjui-header">
        <div class="bjui-navbar-header">
            <button type="button" class="bjui-navbar-toggle btn-default" data-toggle="collapse" data-target="#bjui-navbar-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="bjui-navbar-logo" href="#"><img src="../images/logo.png" height="30"></a>
        </div>
        <nav id="bjui-navbar-collapse">
            <ul class="bjui-navbar-right">
                <li class="datetime"><div><span id="bjui-date"></span> <span id="bjui-clock"></span></div></li>
                <li><a href="#">角色：<c:forEach items="${roleList }" var="s" varStatus="i">${s.name }${!i.last ? '&nbsp;' : '' }</c:forEach></a></li>
                <li><a href="${ctx }sys/changepass" data-toggle="dialog" data-id="sys_user_changepass" data-mask="true" data-width="400" data-height="300">修改密码</a></li>
                <li><a href="${ctx }logout" style="font-weight:bold;">&nbsp;<i class="fa fa-power-off"></i> 注销登陆</a></li>
                <li class="dropdown"><a href="#" class="dropdown-toggle theme blue" data-toggle="dropdown" title="切换皮肤"><i class="fa fa-tree"></i></a>
                    <ul class="dropdown-menu" role="menu" id="bjui-themes">
                        <li><a href="javascript:;" class="theme_default" data-toggle="theme" data-theme="default">&nbsp;<i class="fa fa-tree"></i> 黑白分明&nbsp;&nbsp;</a></li>
                        <li><a href="javascript:;" class="theme_orange" data-toggle="theme" data-theme="orange">&nbsp;<i class="fa fa-tree"></i> 橘子红了</a></li>
                        <li><a href="javascript:;" class="theme_purple" data-toggle="theme" data-theme="purple">&nbsp;<i class="fa fa-tree"></i> 紫罗兰</a></li>
                        <li class="active"><a href="javascript:;" class="theme_blue" data-toggle="theme" data-theme="blue">&nbsp;<i class="fa fa-tree"></i> 天空蓝</a></li>
                        <li><a href="javascript:;" class="theme_green" data-toggle="theme" data-theme="green">&nbsp;<i class="fa fa-tree"></i> 绿草如茵</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </header>
    <div id="bjui-container" class="clearfix">
        <div id="bjui-leftside">
            <div id="bjui-sidebar-s">
                <div class="collapse"></div>
            </div>
            <div id="bjui-sidebar">
                <div class="toggleCollapse"><h2><i class="fa fa-bars"></i> 导航栏 <i class="fa fa-bars"></i></h2><a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a></div>
                <div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu" data-heightbox="#bjui-sidebar" data-offsety="26">
                    <%-- <div class="panel panel-default collapse in">
                        <div class="panel-heading active">
                            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse-map" class=""><i class="fa fa-check-square-o"></i>&nbsp;地图分布<b><i class="fa fa-angle-down"></i></b></a></h4>
                        </div>
                        <div id="bjui-collapse-map" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <ul id="bjui-hnav-tree-map" class="ztree ztree_main" data-toggle="ztree" data-on-click="EmMapMenuClick" data-expand-all="true" data-faicon="${p.icon }">
                                    <c:forEach items="${groupList }" var="g">
                                        <li data-id="${g.equipmentgroupid }" data-pid="" data-faicon="folder-open-o" data-faicon-close="folder-o">${g.equipmentgroupname }</li>
                                        <c:forEach items="${g.emList }" var="s">
                                            <li data-id="${s.equipmentid }" data-pid="${s.equipmentgroupid }" data-faicon="caret-right">${s.equipmentname }</li>
                                        </c:forEach>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div> --%>
                    <div class="panel panel-default collapse in">
                        <div class="panel-heading active">
                            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse-me" class=""><i class="fa fa-check-square-o"></i>&nbsp;设备信息<b><i class="fa fa-angle-down"></i></b></a></h4>
                        </div>
                        <div id="bjui-collapse-me" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <ul id="bjui-hnav-tree-me" class="ztree ztree_main" data-toggle="ztree" data-on-click="EmInfoMenuClick" data-setting="{callback:{onDblClick:EmInfoMenuDblClick}}" data-expand-all="true">
                                    <c:forEach items="${areaList }" var="a">
                                        <c:if test="${a.level == 0 }"><li data-id="${a.id }" data-faicon="folder-open-o" data-faicon-close="folder-o" data-emcount="${fn:length(a.emList) }">${a.name }</li></c:if>
                                        <c:if test="${a.level > 0 }"><li data-id="${a.id }" data-pid="${a.parentid }" data-areainfo="true" data-faicon="caret-right">${a.name }</li></c:if>
                                        <c:forEach items="${a.emList }" var="s">
                                            <li data-id="${s.id }" data-p-id="${s.areaid }" data-faicon="caret-right" data-eminfo="true">${s.name }</li>
                                        </c:forEach>
                                    </c:forEach>
                                    <%-- 
                                    <c:forEach items="${typeList }" var="t">
                                        <li data-id="${t.id }" data-faicon="folder-open-o" data-faicon-close="folder-o">${t.name }</li>
                                        <c:forEach items="${t.emList }" var="s">
                                            <li data-id="${s.id }" data-p-id="${s.typeid }" data-faicon="caret-right">${s.name }</li>
                                        </c:forEach>
                                    </c:forEach>
                                    --%>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${menuList }" var="p" varStatus="i">
                        <div class="panel panel-default collapse in">
                            <div class="panel-heading<%-- ${i.first ? ' active' : '' } --%>">
                                <h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse${i.index }" class="collapsed"<%-- class="${!i.first ? 'collapsed' : '' }" --%>><i class="fa fa-check-square-o"></i>&nbsp;${p.name }<b><i class="fa fa-angle-down"></i></b></a></h4>
                            </div>
                            <div id="bjui-collapse${i.index }" class="panel-collapse collapse<%-- ${i.first ? ' in' : '' } --%>">
                                <div class="panel-body">
                                    <ul id="bjui-hnav-tree${i.index }" class="ztree ztree_main" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="true" data-faicon="${p.icon }">
                                        <c:forEach items="${p.children }" var="s">
                                            <c:if test="${s.level == 1 }">
                                                <li data-id="${s.id }" data-pid="${s.parentid }" data-faicon="folder-open-o" data-faicon-close="folder-o">${s.name }</li>
                                            </c:if>
                                            <c:if test="${s.level != 1 }">
                                                <li data-id="${s.id }" data-pid="${s.parentid }" data-url="${s.url }" data-targetid="${s.targetid }" data-target="${s.target }" data-faicon="${empty s.icon ? 'caret-right' : s.icon }">${s.name }</li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="bjui-navtab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <ul class="navtab-tab nav nav-tabs">
                        <li data-url="main" data-faicon="home"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                <div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                <div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">#maintab#</a></li>
            </ul>
            <div class="navtab-panel tabsPageContent">
                <div class="navtabPage unitBox">
                    <div class="bjui-pageHeader" style="background:#FFF;">
                        <div style="padding: 0 15px;">
                            <h4>设备管理系统</h4>
                            <div class="alert alert-success" role="alert" style="margin:0 0 5px; padding:5px 15px 0;">
                                
                            </div>
                            <div class="row">
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer id="bjui-footer">
        Copyright &copy; 2015　<a href="#">设备管理系统</a>
    </footer>
</body>
</html>