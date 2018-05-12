<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
var em_list_current_map = null

function em_list_current_init() {
    var $box   = $('#j_em_list_current_container')
    var map    = new BMap.Map('j_em_list_current_container')
    var lng    = parseFloat('${em.lng }')
    var lat    = parseFloat('${em.lat }')
    
    if (!lng || !lat) {
        $box.alertmsg('info', '该设备的经纬度坐标不正确！')
        lng    = 121.479098
        lat    = 31.228289
    }
    
    var point = new BMap.Point(lng, lat)
    
    map.centerAndZoom(point, 17)
    //map.addControl(new BMap.MapTypeControl())
    //map.addControl(new BMap.NavigationControl())
    //map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT}))
    //map.enableScrollWheelZoom(false)
    map.disableScrollWheelZoom()
    map.disableDragging()
    
    em_list_current_map = map
    
    em_list_current_mark(map)
    
    // N标记
    var em_label_n = new BMap.Label('N', {position:new BMap.Point(lng, lat), offset : new BMap.Size(0, -135)})
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
    var circle_1 = new BMap.Circle(point, 100, {strokeColor:'blue', strokeWeight:2, strokeStyle:'dashed', strokeOpacity:0.5, fillColor:''})
    var circle_2 = new BMap.Circle(point, 180, {strokeColor:'green', strokeWeight:2, strokeOpacity:0.5, fillColor:''})
    // 设备初始位置
    var em_dot = new BMap.Circle(point, 8, {strokeColor:'green', strokeWeight:1, strokeOpacity:1, fillColor:''})
    // 设备当前位置
    var current_lng = "${current.positionid == 1 ? current.lng_agps : '' }${current.positionid == 2 ? current.lng_lbs : '' }${current.positionid == 3 ? current.lng_gps : '' }",
        current_lat = "${current.positionid == 1 ? current.lat_agps : '' }${current.positionid == 2 ? current.lat_lbs : '' }${current.positionid == 3 ? current.lat_gps : '' }"
    
    var em_distance = ''
    
    if (current_lng && current_lat) {
        var em_current_dot = new BMap.Circle(new BMap.Point(current_lng, current_lat), 8, {strokeColor:'red', strokeWeight:1, strokeOpacity:1, fillColor:''})
        
        map.addOverlay(em_current_dot)
        // 计算飘移距离
        em_distance = parseInt(map.getDistance(point, new BMap.Point(current_lng, current_lat))) + ' 米'
        $('#em_label_distance').html(em_distance)
    }
    // 飘移距离
    var em_label_distance = new BMap.Label('&nbsp;飘移距离：'+ em_distance, {position:new BMap.Point(lng, lat), offset : new BMap.Size(-130, 106)})
    em_label_distance.setStyle({
        color      : 'red',
        fontSize   : '14px',
        height     : '22px',
        lineHeight : '18px',
        backgroundColor:'#FFF'/*,
        border: 'none',
        fontStyle:'italic'*/
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
function em_list_current_mark(map) {
    
}

setTimeout(function() {
    em_list_current_init()
}, 100)
</script>
<style type="text/css">
.em_list_info_box label{font-size:14px;}
.em_list_info_ul > li{line-height:22px;}
.em_list_info_labeltit{display:inline-block; padding-right:10px; width:60px; text-align:right;}
.anchorBL{display:none;}  
</style>
<div class="em_list_info_box clearfix">
    <div style="float:left; width:320px; text-align:center">
        <img src="${ctx }${em.pic }" style="max-width:300px; max-height:260px;">
    </div>
    <div style="float:left; width:160px;">
        <p><label>型号：</label>${em.typename }</p>
        <br>
        <label>当前状态</label>
        <ul class="em_list_info_ul">
            <li><span class="em_list_info_labeltit">在线状态</span>${current.onlinestate }</li>
            <li><span class="em_list_info_labeltit">工作状态</span>${current.workstate }</li>
            <li><span class="em_list_info_labeltit">设备状态</span>${!empty current.emstate ? (current.emstate ? '正常' : '异常') : '' }</li>
            <li><span class="em_list_info_labeltit">定位方式</span>${current.position }</li>
            <li><span class="em_list_info_labeltit">当前电压</span>${current.voltage }</li>
            <li><span class="em_list_info_labeltit">当前电流</span>${current.current }</li>
        </ul>
        <br>
        <label>最后通信时间</label>
       <ul class="em_list_info_ul">
            <li>${current.workcommtime}</li>
        </ul>
    </div>
    <div style="float:left; width:280px; text-align:center" class="hide">
        <div id="j_em_list_current_container" style="width:260px; height:260px;"></div>
        <%--  <img src="${ctx }images/em/em_position.png"> --%>
    </div>
    <div>
        <br>
        <p style="margin-top:28px; margin-bottom:0;"><label>设定位置</label></p>
        <ul class="em_list_info_ul">
            <li><span class="em_list_info_labeltit">经度</span>${em.lng }</li>
            <li><span class="em_list_info_labeltit">纬度</span>${em.lat }</li>
        </ul>
        <br>
        <p style="margin-bottom:0;"><label>当前位置</label></p>
        <ul class="em_list_info_ul">
            <li><span class="em_list_info_labeltit">经度</span>${current.positionid == 1 ? current.lng_agps : '' }${current.positionid == 2 ? current.lng_lbs : '' }${current.positionid == 3 ? current.lng_gps : '' }</li>
            <li><span class="em_list_info_labeltit">纬度</span>${current.positionid == 1 ? current.lat_agps : '' }${current.positionid == 2 ? current.lat_lbs : '' }${current.positionid == 3 ? current.lat_gps : '' }</li>
        </ul>
        <p style="margin-top:10px; margin-bottom:0;"><label>飘移距离：</label><span id="em_label_distance"></span></p>
    </div>
</div>
<div class="em_list_info_box clearfix" style="margin-top:35px; width:750px;">
    <div style="float:left; padding-left:50px; width:240px;">
        <p style="margin-top:10px; text-indent:15px;"><label>航标信息</label></p>
        <ul class="em_list_info_ul">
            <li><span class="em_list_info_labeltit">编号</span>${em.code }</li>
            <li><span class="em_list_info_labeltit">名称</span>${em.name }</li>
            <li><span class="em_list_info_labeltit">设计经度</span>${em.lng }</li>
            <li><span class="em_list_info_labeltit">设计纬度</span>${em.lat }</li>
            <li><span class="em_list_info_labeltit">供电方式</span>${em.powername }</li>
            <li><span class="em_list_info_labeltit">通信方式</span>${em.commname }</li>
            <li><span class="em_list_info_labeltit">附记</span>${em.excursusname }</li>
            <li><span class="em_list_info_labeltit">卡号</span>${em.cardno }</li>
        </ul>
    </div>
    <div style="float:left;">
        <div style="min-width:500px; height:248px" data-toggle="echarts" data-type="bar,line" data-url="echarts/emvoltage/${em.id }"></div>
    </div>
</div>