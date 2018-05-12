<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
    <c:if test="${!empty isTest && isTest }">
        "title" : {
          "text": "变化曲线 - 未获取到设备的电压电流记录，图示为《测试数据》"
        },
    </c:if>
    "tooltip" : {
        "trigger": "axis"
    },
    "legend": {
        "data":["电压","电流"],
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
            "name" : "电压",
            "type" : "value"
            <c:if test="${!empty config.voltageupper }">
            ,"max":${config.voltageupper * 1.2 }
            </c:if>
        },
        {
            "name" : "电流",
            "type" : "value"
            <c:if test="${!empty config.currentupper }">
            ,"max":${config.currentupper * 1.2 }
            </c:if>
        }
    ],
    "series" : [
        {
            "name":"电压",
            "type":"line",
            "data":${data_voltage }
        },
        {
            "name":"电流",
            "type":"line",
            "yAxisIndex": 1,
            "data":${data_current }
        }
    ]
}