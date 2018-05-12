<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
  "tooltip": {
    "trigger": "item",
    "formatter": "{a} <br/>{b} : {c} ({d}%)"
  },
  "legend": {
    "orient": "vertical",
    "x": "left",
    "data": ${tits }
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
          "pie",
          "funnel"
        ],
        "option": {
          "funnel": {
            "x": "25%",
            "width": "50%",
            "funnelAlign": "left",
            "max": 1548
          }
        }
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
  "series": [
    {
      "name": "故障报警统计",
      "type": "pie",
      "radius": "55%",
      "center": [
        "70%",
        "60%"
      ],
      "data": ${data}
    }
  ]
}