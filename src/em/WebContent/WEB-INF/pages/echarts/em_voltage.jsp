<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
  "title" : {
    "text": "设备电池电压"
  },
  "tooltip": {
    "trigger": "axis"
  },
  "legend": {
    "data": [
      "电压"
    ]
  },
  "toolbox": {
    "show": true,
    "feature": {
      "mark": {
        "show": true
      },
      "dataView": {
        "show": true,
        "readOnly": false
      },
      "magicType": {
        "show": true,
        "type": [
          "line",
          "bar"
        ]
      },
      "restore": {
        "show": true
      },
      "saveAsImage": {
        "show": true
      }
    }
  },
  "calculable": true,
  "xAxis": [
    {
      "type": "category",
      "data": ${tits }
    }
  ],
  "yAxis": [
    {
      "type": "value",
      "splitArea": {
        "show": true
      }
    }
  ],
  "series": [
    {
      "name": "电压",
      "type": "bar",
      "stack": "sum",
      "barCategoryGap": "50%",
      "itemStyle": {
        "normal": {
            "color": "tomato",
            "barBorderColor": "tomato",
            "barBorderWidth": 0,
            "barBorderRadius":0,
            "label" : {
                "show": true, "position": "insideTop"
            }
        }
    },
      "data": ${datas }
      <c:if test="${maxV == 999 && minV == 999 }">
      ,
      "markLine":{
        "data":[
            [
                {"name": "电压", "value": ${minV }, "xAxis": -1, "yAxis": ${minV }},
                {"name": "下限", "xAxis": ${batteryNum }, "yAxis": ${minV }}
            ],
            [
                {"name": "电压", "value": ${maxV }, "xAxis": -1, "yAxis": ${maxV }},
                {"name": "上限", "xAxis": ${batteryNum }, "yAxis": ${maxV }}
            ]
        ]
      }
      </c:if>
    }
  ]
}