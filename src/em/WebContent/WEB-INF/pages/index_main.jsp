<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="include/includetag.jsp"%>
<script type="text/javascript">
function echarts_pie_callback(param) {
    var name = param.name
    
    if (name) {
        $(this).navtab({
            url   : 'em/alarmrecord/listAlarmRecord',
            title : '报警信息',
            id    : 'em-history-list'
        })
    }
}
function echarts_map_callback(param) {
    var name = param.name
    
    if (name && param.seriesName) {
        $(this).navtab({
            url   : 'emmap',
            data  : {place:name},
            type  : 'post',
            title : '地图分布',
            id    : 'em-map-list'
        })
    }
}
</script>
<style type="text/css">
.j_indexman_newslist{margin:0 10px;}
.j_indexman_newslist > li{list-style:disc; height:24px;}
.j_indexman_newslist > li span{}
</style>
<div class="bjui-pageContent tableContent">
    <div style="padding:15px;">
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">设备整体状况</h3>
                </div>
                <div class="panel-body">
                    <div style="min-width:400px; height:350px" data-toggle="echarts" data-type="pie,funnel" data-url="echarts/embug" data-event="CLICK" data-callback="echarts_pie_callback"></div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">设备地图分布</h3>
                </div>
                <div class="panel-body">
                    <div style="min-width:400px; height:350px" data-toggle="echarts" data-type="map" data-url="echarts/emmap" data-event="CLICK" data-callback="echarts_map_callback"></div>
                </div>
            </div>
        </div>
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">在线文档</h3>
                </div>
                <div class="panel-body">
                    <ul class="j_indexman_newslist">
                        <c:forEach items="${newsList }" var="s">
                            <li><a href="systemnews/view/${s.id }" data-toggle="navtab" data-options="{id:'systemnews-view', title:'在线文档'}"><span<c:if test="${!empty s.titlecolor }"> style="color:${s.titlecolor };"</c:if>>${s.title }</span><span style="color:#999;">(<fmt:formatDate value="${s.createtime }" pattern="yyyy-MM-dd HH:mm:ss" />)</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>