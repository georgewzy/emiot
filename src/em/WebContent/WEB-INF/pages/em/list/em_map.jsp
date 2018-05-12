<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
var em_map_map = null

function em_map_init() {
    var $box   = $('#j_em_map_container')
    var map    = new BMap.Map('j_em_map_container')
    var lng    = '${em.lng }'
    var lat    = '${em.lat }'
    var place  = '${place }'
    
    if (!place) {
        if (!lng && !lat) {
            $box.alertmsg('info', '该设备的经纬度坐标不正确！')
            lng    = 121.479098
            lat    = 31.228289
        }
        map.centerAndZoom(new BMap.Point(lng, lat), 11)
    } else {
        map.centerAndZoom(place, 11)
    }
    
    map.addControl(new BMap.MapTypeControl())
    map.addControl(new BMap.NavigationControl())
    map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT}))
    map.enableScrollWheelZoom(true)
    
    em_map_map = map
    
    em_map_mark(map)
}
function em_map_mark(map) {
    var list = $.parseJSON('${list }')
    
    for (var i = 0; i < list.length; i++) {
        (function(x) {
            var obj = list[x]
            var lng = obj.lng
            var lat = obj.lat
            if (lng && lat) {
                var point  = new BMap.Point(lng, lat)
                var marker = new BMap.Marker(point)
                
                if (obj.typeicon) {
                    var myIcon = new BMap.Icon('${ctx }'+ obj.typeicon, new BMap.Size(20, 20))
                    
                    marker = new BMap.Marker(point, {icon:myIcon})
                }
                
                var sContent = '<div class="">'
                             + '    <div class="em_map_info_title">'+ obj.name +'</div>'
                             + '    <div class="">'
                             + '        <ul class="em_map_info_msg" style="margin:8px; list-style:circle;">'
                             + '            <li>当前状态：'+ (obj.emstate ? '正常' : '异常') +'</li>'
                             + '            <li>在线状态：'+ (obj.onlinestate || '--') +'</li>'
                             + '            <li>最后一次通信时间：'+ (obj.workcommtime || '--') +'</li>'
                             + '            <li>详情：<a href="javascript:;" onclick="em_map_info_to_detail(this);" data-id="'+ obj.id +'" data-name="'+ obj.name +'">点我查看该设备详细情况</a></li>'
                             + '        </ul>'
                             + '    </div>'
                             + '</div>'
                var infoWindow = new BMap.InfoWindow(sContent)
                
                map.addOverlay(marker)
                
                marker.addEventListener('click', function() {
                    this.openInfoWindow(infoWindow)
                })
            }
        }) (i)
    }
}
function em_map_move(lng, lat) {
    if (em_map_map) {
        em_map_map.panTo(new BMap.Point(lng, lat))
    }
}
function em_map_info_to_detail(obj) {
    var $obj = $(obj), id = $obj.data('id'), name = $obj.data('name')
    
    $obj.navtab({
        url   : 'emlist/'+ id,
        id    : 'em-info-list',
        title : '设备：'+ name
    })
}

em_map_info_refresh = function(id) {
    if (!id) return false
    
    var $infoid = $('#j_em_map_info_id')
    
    $infoid.bjuiajax('doAjax', {
        url      : 'emmap/getEm/'+ id,
        callback : function(json) {
            if (!json.lng || !json.lat) {
                $box.alertmsg('info', '该设备的安装坐标不正确！')
            } else {
                $infoid.navtab('switchTab', 'em-map-list')
                em_map_move(json.lng, json.lat)
            }
        }
    })
    
    return true
}

setTimeout('em_map_init()', 500)

</script>
<style type="text/css">
#j_em_map_container{height:100%;}
.em_map_info_title{margin-top:-5px; font-size:16px; font-weight:bold; line-height:30px; border-bottom:1px #EEE solid;}
.em_map_info_msg > li{margin:0 10px; font-size:14px; line-height:25px; list-style:circle;}
.em_map_info_msg > li a {font-size:14px;}
</style>
<div class="bjui-pageContent tableContent">
    <input type="hidden" id="j_em_map_info_areaids" value="${ids }">
    <input type="hidden" id="j_em_map_info_id" value="${em.id }">
    <div id="j_em_map_container"></div>
</div>