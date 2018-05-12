<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
var em_list_position_map = null

function em_list_position_init() {
    var $box   = $('#j_em_list_position_container')
    var map    = new BMap.Map('j_em_list_position_container')
    var lng    = parseFloat('${em.lng }')
    var lat    = parseFloat('${em.lat }')
    
    if (!lng || !lat) {
        $box.alertmsg('info', '该设备的经纬度坐标不正确！')
        lng    = 121.479098
        lat    = 31.228289
    }
    
    var point = new BMap.Point(lng, lat)
    
    map.centerAndZoom(point, 17)
    map.disableScrollWheelZoom()
    map.disableDragging()
    
    em_list_position_map = map
    
    em_list_position_mark(map)
    
    // N标记
    var em_label_n = new BMap.Label('N', {position:new BMap.Point(lng, lat), offset : new BMap.Size(0, -255)})
    em_label_n.setStyle({
        color      : 'red',
        fontSize   : '20px',
        height     : '30px',
        lineHeight : '30px',
        backgroundColor:'none',
        border: 'none',
        fontStyle:'italic'
    })
    // 中心十字线
    var em_line_opt = {strokeColor:'blue', strokeWeight:2, strokeOpacity:0.5},
        em_line_top = new BMap.Polyline([
            new BMap.Point(lng, lat),
            new BMap.Point(lng, lat + 0.1)
        ], em_line_opt),
        em_line_right = new BMap.Polyline([
            new BMap.Point(lng, lat),
            new BMap.Point(lng + 0.1, lat)
        ], em_line_opt),
        em_line_bottom = new BMap.Polyline([
            new BMap.Point(lng, lat),
            new BMap.Point(lng, lat - 0.1)
        ], em_line_opt),
        em_line_left = new BMap.Polyline([
            new BMap.Point(lng, lat),
            new BMap.Point(lng - 0.1, lat)
        ], em_line_opt)
    
    // 设备飘移范围
    var circle_1 = new BMap.Circle(point, 350, {strokeColor:'blue', strokeWeight:2, strokeStyle:'dashed', strokeOpacity:0.5, fillColor:''})
    var circle_2 = new BMap.Circle(point, 380, {strokeColor:'green', strokeWeight:2, strokeOpacity:0.5, fillColor:''})
    // 设备初始位置
    var em_dot = new BMap.Circle(point, 8, {strokeColor:'green', strokeWeight:1, strokeOpacity:1, fillColor:''})
    // 设备当前位置
    var current_lng = "${current.positionid == 1 ? current.lng_agps : '' }${current.positionid == 2 ? current.lng_lbs : '' }${current.positionid == 3 ? current.lng_gps : '' }",
        current_lat = "${current.positionid == 1 ? current.lat_agps : '' }${current.positionid == 2 ? current.lat_lbs : '' }${current.positionid == 3 ? current.lat_gps : '' }"
    
    if (!current_lng)
        current_lng = '${current.lng_gps }' || '${current.lng_agps }' || '${current.lng_lbs }'
    if (!current_lat)
        current_lat = '${current.lng_gps }' || '${current.lat_agps }' || '${current.lat_lbs }'
    
    var em_distance = ''
    
    if (current_lng && current_lat) {
        var em_current_dot = new BMap.Circle(new BMap.Point(current_lng, current_lat), 8, {strokeColor:'red', strokeWeight:1, strokeOpacity:1, fillColor:''})
        
        map.addOverlay(em_current_dot)
        // 计算飘移距离
        em_distance = parseInt(map.getDistance(point, new BMap.Point(current_lng, current_lat))) + ' 米'
    }
    // 飘移距离
    var em_label_distance = new BMap.Label('投放点', {position:new BMap.Point(lng, lat), offset : new BMap.Size(-46, 1)})
    em_label_distance.setStyle({
        color      : 'red',
        fontSize   : '14px',
        height     : '22px',
        lineHeight : '18px',
        backgroundColor:'#FFF'
    })
    
    map.addOverlay(em_label_n)
    map.addOverlay(em_line_top)
    map.addOverlay(em_line_right)
    map.addOverlay(em_line_bottom)
    map.addOverlay(em_line_left)
    map.addOverlay(circle_1)
    map.addOverlay(circle_2)
    map.addOverlay(em_dot)
    map.addOverlay(em_label_distance)
    
}
function em_list_position_mark(map) {
    var list = $.parseJSON('${list }')
    
    for (var i = 0; i < list.length; i++) {
        (function(x) {
            var obj = list[x], positionid = obj.positionid
            var lng = null
            var lat = null
            
            if (positionid) {
                if (positionid == 1) {
                    lng = obj.lng_agps
                    lat = obj.lat_agps
                } else if (positionid == 2) {
                    lng = obj.lng_lbs
                    lat = obj.lat_lbs
                } else {
                    lng = obj.lng_gps
                    lat = obj.lat_gps
                }
            } 
            
            if (!lng)
                lng = obj.lng_gps || obj.lng_lbs || obj.lng_agps
            if (!lat)
                lat = obj.lat_gps || obj.lat_lbs || obj.lat_agps
            
            if (lng && lat) {
                var em_dot = new BMap.Circle(new BMap.Point(lng, lat), 8, {strokeColor:'#222222', strokeWeight:1, strokeOpacity:1, fillColor:''})
                map.addOverlay(em_dot)
            }
        }) (i)
    }
}

setTimeout(function() {
    em_list_position_init()
}, 100)

function em_list_position_submit(obj) {
    var $box = $('#j_em_list_position_form'), $starttime = $('#j_em_list_position_starttime'), $endtime = $('#j_em_list_position_endtime')
    
    if (!$starttime.val() && !$endtime.val()) {
        $(obj).alertmsg('info', '请至少输入一个日期！')
        return
    }
    if (!$box.data('validator')) {
        $box.validator({
            validClass : 'ok',
            theme      : 'red_right_effect'
        })
    }
    $box.isValid(function(v) {
        if (v) {
            $(obj).bjuiajax('refreshLayout', {
                url  : 'emlist/position/${em.id }',
                data : {'his.starttime':$starttime.val(), 'his.endtime':$endtime.val()},
                target : '#j_em_info_detail_box'
            })
        }
    })
}
</script>
<style type="text/css">
.anchorBL{display:none;}  
</style>
<div style="width:800px;">
    <div style="float:left; width:520px; text-align:center">
        <div id="j_em_list_position_container" style="width:500px; height:500px;"></div>
    </div>
    <div style="float:left;">
        <div style="">
            <p><label class="control-label x85">编号：</label>${em.code }</p>
            <p><label class="control-label x85">名称：</label>${em.name }</p>
            <p><label class="control-label x85">附记：</label>${em.excursusname }</p>
        </div>
        <form style="margin-top:60px;" id="j_em_list_position_form">
            <p><label class="control-label x85">起始日期：</label><input type="text" id="j_em_list_position_starttime" name="his.starttime" value="${his.starttime }" data-toggle="datepicker" data-rule="date" size="16"></p>
            <p><label class="control-label x85">结束日期：</label><input type="text" id="j_em_list_position_endtime" name="his.endtime" value="${his.endtime }" data-toggle="datepicker" data-rule="date" size="16"></p>
            <p><label class="control-label x85"></label><button type="button" onclick="em_list_position_submit(this);" class="btn-blue">确定</button></p>
        </form>
    </div>
</div>