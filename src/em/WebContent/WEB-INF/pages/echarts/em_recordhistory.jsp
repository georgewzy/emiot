<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
    <c:if test="${!empty isTest && isTest }">
        "title" : {
          "text": "充放电量 - 未获取到设备的充放电量记录，图示为《测试数据》"
        },
    </c:if>
    "tooltip" : {
        "trigger": "axis"
    },
    "legend": {
        "data":["充电电量","放电电量"],
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
        "end" : ${ah }
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
            "name" : "充电电量",
            "type" : "value",
            <c:if test="${!empty ah }">
            "max":${ah }
            </c:if>
        },
        {
            "name" : "放电电量",
            "type" : "value",
            <c:if test="${!empty ah }">
            "max":${ah }
            </c:if>
        }
    ],
    "series" : [
        {
            "name":"充电电量",
            "type":"line",
            "data":${data_chargesoc }
        },
        {
            "name":"放电电量",
            "type":"line",
            "yAxisIndex": 1,
            "data":${data_dischargesoc }
        }
    ]
}