<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="includetag.jsp" %>
<script type="text/javascript">
function itemsSysArea() {
    var arr = []
    <c:forEach items="${areaList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmArea() {
    var arr = []
    <c:forEach items="${emAreaList }" var="a">
        <c:forEach items="${a.children }" var="s">
            arr.push({'${s.id }':'${a.name } &gt; ${s.name }'})
            <c:forEach items="${s.children }" var="ss">
                arr.push({'${ss.id }':'${a.name } &gt; ${s.name } &gt; ${ss.name }'})
            </c:forEach>
        </c:forEach>
    </c:forEach>
    return arr
}
function itemsEmType() {
    var arr = []
    <c:forEach items="${typeList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmGroup() {
    var arr = []
    <c:forEach items="${groupList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmPower() {
    var arr = []
    <c:forEach items="${powerList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmBattery() {
    var arr = []
    <c:forEach items="${batteryList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmBatteryType() {
    var arr = []
    <c:forEach items="${batteryTypeList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmBatteryState() {
    var arr = []
    <c:forEach items="${batteryStateList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmLight() {
    var arr = []
    <c:forEach items="${lightList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmLightSource() {
    var arr = []
    <c:forEach items="${lightSourceList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmComm() {
    var arr = []
    <c:forEach items="${commList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmCommP() {
    var arr = []
    <c:forEach items="${commPList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmBrightlevel() {
    var arr = []
    <c:forEach items="${brightlevelList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmExcursus() {
    var arr = []
    <c:forEach items="${excursusList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmPosition() {
    var arr = []
    <c:forEach items="${positionList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmWorkState() {
    var arr = []
    <c:forEach items="${workStateList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmOnlineState() {
    var arr = []
    <c:forEach items="${onlineStateList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmAlarmtype() {
    var arr = []
    <c:forEach items="${alarmtypeList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmAlarmrecordtype() {
    var arr = []
    <c:forEach items="${alarmrecordtypeList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
function itemsEmServicetype() {
    var arr = []
    <c:forEach items="${servicetypeList }" var="s">
        arr.push({'${s.id }':'${s.name }'})
    </c:forEach>
    return arr
}
</script>