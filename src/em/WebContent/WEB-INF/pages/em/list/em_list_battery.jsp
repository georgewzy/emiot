<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
</script>
<div style="position:absolute; top:30px; left:120px;">电池状态：<i class="fa fa-star"></i> 异常　 <i class="fa fa-play fa-rotate-270"></i> 旁路</div>
<div style="width:100%; height:100%" data-toggle="echarts" data-type="line,scatter" data-url="echarts/battery/${em.id }"></div>
