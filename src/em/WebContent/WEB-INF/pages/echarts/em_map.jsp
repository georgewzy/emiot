<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../include/includetag.jsp"%>
{
  "tooltip": {
    "trigger": "item"
  },
  "setTheme": {
    "theme": "macarons"
  },
  "legend": {
    "orient": "vertical",
    "x": "left",
    "data": [
      "设备分布"
    ]
  },
  "dataRange": {
    "min": 0,
    "max": ${max },
    "x": "left",
    "y": "bottom",
    "text": [
      "最多",
      "最少"
    ],
    "calculable": true
  },
  "toolbox": {
    "show": true,
    "orient": "vertical",
    "x": "right",
    "y": "center",
    "feature": {
      "mark": {
        "show": true
      },
      "dataView": {
        "show": true,
        "readOnly": false
      },
      "restore": {
        "show": true
      },
      "saveAsImage": {
        "show": true
      }
    }
  },
  "series": [
    {
      "name": "设备分布",
      "type": "map",
      "mapType": "china",
      "roam": false,
      "itemStyle": {
        "normal": {
          "label": {
            "show": true
          }
        },
        "emphasis": {
          "label": {
            "show": true
          }
        }
      },
      "data": ${datas }
    }
  ]
}