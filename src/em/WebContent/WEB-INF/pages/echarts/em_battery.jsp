<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
    <c:if test="${!empty isTest && isTest }">
    "title" : {
      "text": "电池状态 - 未获取到设备的电池状态记录，图示为《测试数据》"
    },
    </c:if>
    "tooltip" : {
        "trigger": "axis"
    },
    "legend": {
        "data":${tits },
        "x":"120px",
        "y":"40px"
    },
    "toolbox": {
        "show" : true,
        "feature" : {
            "mark" : {"show": true},
            "dataView" : {"show": false, "readOnly": false},
            "magicType" : {"show": true, "type": []},
            "restore" : {"show": true},
            "saveAsImage" : {"show": true}
        }
    },
    "grid": {
        "y": "${batteryNum > 4 ? '160' : '110' }px"
    },
    "dataZoom" : {
        "show" : true,
        "realtime" : true,
        "start" : 0,
        "end" : 100
    },
    "calculable" : true,
    "xAxis" : [
        {
            "type" : "category",
            "boundaryGap" : false,
            "axisLine": {"onZero": false},
            "data" : ${times }
        }
    ],
    "yAxis" : [
        {
            "name" : "电压",
            "type" : "value"
        },
        {
            "name" : "剩余电量",
            "type" : "value"
        }
    ],
    "series" : ${datas }
}