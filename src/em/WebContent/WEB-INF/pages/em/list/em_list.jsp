<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<script type="text/javascript">
//单击事件
function em_info_ZtreeClick(event, treeId, treeNode) {
    event.preventDefault()
    
    var $target = $(event.target), url = treeNode.url, tit = treeNode.tit, children = treeNode.children
    
    if (!url) {
        if (!children) return
        $.each(children, function(i, n) {
            if (n.url) {
                url = n.url
                return false
            }
        })
    }
    if (!url) return false
    if (tit) $('#j_em_info_detail_title').html(tit)
    
    $target.bjuiajax('doLoad', {
        url    : url,
        target : '#j_em_info_detail_box'
    })
}

function em_info_current_click() {
    var ztree = $.fn.zTree.getZTreeObj('j_em_info_ztree')
    var node  = ztree.getNodeByTId('j_em_info_ztree_2')
    
    if (node) {
        ztree.selectNode(node)
    }
    
    $('#j_em_info_ztree').bjuiajax('doLoad', {
        url    : 'emlist/view/${em.id }',
        target : '#j_em_info_detail_box'
    })
}

setTimeout(function() {
    em_info_current_click()
}, 300)

</script>
<div class="bjui-pageContent">
    <div class="bjui-fullHeight" style="float:left; margin:-10px; padding:10px 0 0 10px; width:190px; ">
        <fieldset>
            <legend>设备信息树</legend>
            <ul class="ztree" data-toggle="ztree" id="j_em_info_ztree" data-expand-all="true" data-on-click="em_info_ZtreeClick">
                <li data-id="1" data-p-id="0">设备信息</li>
                <shiro:hasPermission name="eminfo.emCurrInfo.query">
                    <li data-id="11" data-pid="1" data-url="emlist/view/${em.id }" data-active="true" data-tit="当前信息">设备当前信息</li>
                </shiro:hasPermission>
                <shiro:hasPermission name="eminfo.emHisInfo.query">
                    <li data-id="12" data-pid="1">设备历史信息</li>
                    <li data-id="121" data-pid="12" data-url="emlist/position/${em.id }" data-tit="轨迹回放">轨迹回放</li>
                    <li data-id="122" data-pid="12" data-url="em/history/position/${em.id }" data-tit="位置记录">位置记录</li>
                    <li data-id="123" data-pid="12" data-url="em/history/workstate/${em.id }" data-tit="工作状态">工作状态</li>
                    <li data-id="124" data-pid="12" data-url="emlist/voltagecurrent/${em.id }" data-tit="变化曲线">变化曲线</li>
                    <li data-id="125" data-pid="12" data-url="emlist/battery/${em.id }" data-tit="电池状态">电池状态</li>
                    <li data-id="126" data-pid="12" data-url="emlist/onofftime/${em.id }" data-tit="开关时间">开关时间</li>
                    <li data-id="127" data-pid="12" data-url="emlist/recordhistory/${em.id }" data-tit="充放电量">充放电量</li>
<%--                     <li data-id="128" data-pid="12" data-url="em/history/recordlbs/${em.id }" data-tit="LBS信息">LBS信息</li> --%>
                </shiro:hasPermission>
                <shiro:hasPermission name="eminfo.emAlarmInfo.query">
                    <li data-id="13" data-pid="1" data-url="em/alarmrecord/${em.id }" data-tit="报警信息">设备报警信息</li>
                </shiro:hasPermission>
                <shiro:hasPermission name="eminfo.emRmtCtrl.query">
                    <li data-id="2" data-p-id="0">设备遥控遥测</li>
                    <li data-id="21" data-pid="2" data-url="emlist/editSetting/${em.id }" data-tit="设备参数">设备参数设置</li>
                    <li data-id="22" data-pid="2" data-url="emlist/remoteControl" data-tit="设备遥控">设备遥控</li>
                    <li data-id="23" data-pid="2" data-url="emlist/remoteControl" data-tit="设备遥测">设备遥测</li>
                </shiro:hasPermission>
                <shiro:hasPermission name="eminfo.emPLM.query">
                    <li data-id="3" data-p-id="0">设备PLM管理</li>
                    <li data-id="31" data-pid="3" data-url="em/service/${em.id }" data-tit="维护记录">设备维护记录</li>
                    <li data-id="33" data-pid="3" data-url="em/quality/${em.id }" data-tit="质保信息">设备质保信息</li>
                </shiro:hasPermission>
                <shiro:hasPermission name="eminfo.emOptLog.query">
                    <li data-id="4" data-pid="4" data-url="emlist/operationLog/${em.id }" data-tit="操作日志">设备操作日志</li>
                </shiro:hasPermission>
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:195px; margin-bottom:-60px; padding:0px 0 20px; height:100%;">
        <fieldset style="height:100%;">
            <legend>[${em.name }] <span id="j_em_info_detail_title" style="font-size:14px;">当前信息</span></legend>
            <div id="j_em_info_detail_box" style="padding:20px 0; width:100%; height:100%;">
                ....加载中....
            </div>
        </fieldset>
    </div>
</div>