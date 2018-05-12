<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
    <c:if test="${!empty isTest && isTest }">
       "title" : {
          "text": "变化曲线 - 未获取到设备的电压电流记录，图示为《测试数据》"
        },
    </c:if>
    <c:if test="${empty isTest || isTest }">
        "title" : {
          "text": "开关时间  —— 0 关灯    1 开灯    2 强制开灯    3 强制关灯    4 白天亮灯   5 异常灭灯"
        },
    </c:if>
    "tooltip" : {
        "trigger": "axis"
    },
    "legend": {
        "data":["状态"],
        "x":"120px",
        "y":"40px"
    },
    "toolbox": {
        "show" : true,
        "feature" : {
            "mark" : {"show": true},
            "dataView" : {"show": true, "readOnly": false},
            "magicType" : {"show": true, "type": ["line", "bar", "stack", "tiled"]},
            "restore" : {"show": true},
            "saveAsImage" : {"show": true}
        }
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
            "name" : "状态",
            "type" : "value",
            "max":1
        }
    ],
    "series" : [
        {
            "name":"状态",
            "type":"line",
            "data":${data_onoffstate }
        }
    ]
}