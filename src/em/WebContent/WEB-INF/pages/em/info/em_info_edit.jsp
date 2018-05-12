<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="em/save" data-toggle="validate" data-reload-navtab="true" style="margin:0 auto; max-width:1000px;">
        <input type="hidden" name="em.id" value="${em.id }">
        <input type="hidden" name="emconfig.id" value="${emconfig.id }">
        <input type="hidden" name="emsetting.id" value="${emsetting.id }">
        <input type="hidden" name="em.version" value="${em.version + 1 }">
        <fieldset>
            <legend>设备属性</legend>
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <td>
                            <label for="j_em_edit_areaid" class="control-label x120">所属区域:</label>
                            <select id="j_em_edit_areaid" name="em.areaid" data-toggle="selectpicker" data-rule="required" data-width="200" data-live-search="true">
                                <option></option>
                                <c:forEach items="${areaList }" var="a">
                                    <optgroup label="${a.name }">
                                        <c:forEach items="${a.children }" var="s">
                                            <option value="${s.id }" ${s.id == em.areaid ? 'selected' : '' }>${s.name }</option>
                                            <c:forEach items="${s.children }" var="ss">
                                                <option value="${ss.id }" ${ss.id == em.areaid ? 'selected' : '' }>&nbsp; - ${ss.name }</option>
                                            </c:forEach>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select><span class="required"></span>
                        </td>
                        <td>
                            <label for="j_em_edit_typeid" class="control-label x120">设备类型:</label>
                            <select id="j_em_edit_typeid" name="em.typeid" data-toggle="selectpicker" data-rule="required" data-width="200">
                                <option></option>
                                <c:forEach items="${typeList }" var="s">
                                    <option value="${s.id }"${em.typeid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select><span class="required"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_code" class="control-label x120">设备编号:</label>
                            <input type="text" id="j_em_edit_code" name="em.code" value="${em.code }" data-rule="required" size="20"><span class="required"></span>
                        </td>
                        <td>
                            <label for="j_em_edit_name" class="control-label x120">设备名称:</label>
                            <input type="text" id="j_em_edit_name" name="em.name" value="${em.name }" data-rule="required" size="20"><span class="required"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_address" class="control-label x120">通信地址:</label>
                            <input type="text" id="j_em_edit_address" name="em.address" value="${em.address }" size="20">
                        </td>
                        <td>
                            <label for="j_em_edit_cardno" class="control-label x120">卡号:</label>
                            <input type="text" id="j_em_edit_cardno" name="em.cardno" value="${em.cardno }" size="20">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_lng" class="control-label x120">经度:</label>
                            <input type="text" id="j_em_edit_lng" name="em.lng" value="${em.lng }" size="20">
                        </td>
                        <td>
                            <label for="j_em_edit_lat" class="control-label x120">纬度:</label>
                            <input type="text" id="j_em_edit_lat" name="em.lat" value="${em.lat }" size="20">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_powerid" class="control-label x120">供电方式:</label>
                            <select id="j_em_edit_powerid" name="em.powerid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${powerList }" var="s">
                                    <option value="${s.id }"${em.powerid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_edit_ah" class="control-label x120">电池容量:</label>
                            <input type="text" id="j_em_edit_ah" name="em.ah" value="${em.ah }" size="20" data-rule="digits">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_batteryid" class="control-label x120">电池个数:</label>
                            <select id="j_em_edit_batteryid" name="em.batteryid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${batteryList }" var="s">
                                    <option value="${s.id }"${em.batteryid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_edit_batterytypeid" class="control-label x120">电池配置:</label>
                            <select id="j_em_edit_batterytypeid" name="em.batterytypeid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${batteryTypeList }" var="s">
                                    <option value="${s.id }"${em.batterytypeid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_commid" class="control-label x120">通信方式:</label>
                            <select id="j_em_edit_commid" name="em.commid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${commList }" var="s">
                                    <option value="${s.id }"${em.commid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_edit_cpid" class="control-label x120">通信协议:</label>
                            <select id="j_em_edit_cpid" name="em.cpid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${commPList }" var="s">
                                    <option value="${s.id }"${em.cpid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_excursusid" class="control-label x120">附记:</label>
                            <select id="j_em_edit_excursusid" name="em.excursusid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${excursusList }" var="s">
                                    <option value="${s.id }"${em.excursusid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_edit_brightlevelid" class="control-label x120">亮度等级:</label>
                            <select id="j_em_edit_brightlevelid" name="em.brightlevelid" data-toggle="selectpicker" data-width="200">
                                <option></option>
                                <c:forEach items="${brightlevelList }" var="s">
                                    <option value="${s.id }"${em.brightlevelid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset>
            <legend>设备设置</legend>
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <td>
                            <label for="j_em_edit_setting_ip" class="control-label x120">IP地址:</label>
                            <input type="text" id="j_em_edit_setting_ip" name="emsetting.ip" value="${emsetting.ip }" size="16">
                        </td>
                        <td>
                            <label for="j_em_edit_setting_port" class="control-label x120">端口号:</label>
                            <input type="text" id="j_em_edit_setting_port" name="emsetting.port" value="${emsetting.port }" size="16">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_setting_apn" class="control-label x120">APN:</label>
                            <input type="text" id="j_em_edit_setting_apn" name="emsetting.apn" value="${emsetting.apn }" size="16">
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_setting_lightid" class="control-label x120">灯质:</label>
                            <select id="j_em_edit_setting_lightid" name="emsetting.lightid" data-toggle="selectpicker" data-width="160">
                                <option></option>
                                <c:forEach items="${lightList }" var="s">
                                    <option value="${s.id }"${emsetting.lightid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_edit_setting_lightsourceid" class="control-label x120">灯质源:</label>
                            <select id="j_em_edit_setting_lightsourceid" name="emsetting.lightsourceid" data-toggle="selectpicker" data-width="160">
                                <option></option>
                                <c:forEach items="${lightSourceList }" var="s">
                                    <option value="${s.id }"${emsetting.lightsourceid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_setting_charginglimit" class="control-label x120">充电电压限值(mV):</label>
                            <input type="text" id="j_em_edit_setting_charginglimit" name="emsetting.charginglimit" value="${emsetting.charginglimit }" size="16" data-rule="digits">
                        </td>
                        <td>
                            <label for="j_em_edit_setting_dischargelimit" class="control-label x120">放电电压限值(mV):</label>
                            <input type="text" id="j_em_edit_setting_dischargelimit" name="emsetting.dischargelimit" value="${emsetting.dischargelimit }" size="16" data-rule="digits">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="control-label x120">强制工作允许:</label>
                            <input type="radio" name="emsetting.forcedwork" id="j_em_edit_setting_forcedwork1" value="1" data-toggle="icheck" data-label="是&nbsp;&nbsp;" ${emsetting.forcedwork ? 'checked' : '' }>
                            <input type="radio" name="emsetting.forcedwork" id="j_em_edit_setting_forcedwork2" value="0" data-toggle="icheck" data-label="否" ${!emsetting.forcedwork ? 'checked' : '' }>
                        </td>
                        <td>
                            <label class="control-label x120">同步工作允许:</label>
                            <input type="radio" name="emsetting.syncwork" id="j_em_edit_setting_syncwork1" value="1" data-toggle="icheck" data-label="是&nbsp;&nbsp;" ${emsetting.syncwork ? 'checked' : '' }>
                            <input type="radio" name="emsetting.syncwork" id="j_em_edit_setting_syncwork2" value="0" data-toggle="icheck" data-label="否" ${!emsetting.syncwork ? 'checked' : '' }>
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset>
            <legend>设备配置</legend>
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <td>
                            <label for="j_em_edit_config_events" class="control-label x120">事件上报标志:</label>
                            <input type="text" id="j_em_edit_config_events" name="emconfig.events" value="${emconfig.events }" size="20">
                        </td>
                        <td>
                            <label for="j_em_edit_config_scope" class="control-label x120">位置范围(米):</label>
                            <input type="text" id="j_em_edit_config_scope" name="emconfig.scope" value="${emconfig.scope }" size="20">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_config_voltageupper" class="control-label x120">电压上限:</label>
                            <input type="text" id="j_em_edit_config_voltageupper" name="emconfig.voltageupper" value="${emconfig.voltageupper }" size="20">
                        </td>
                        <td>
                            <label for="j_em_edit_config_voltagelimit" class="control-label x120">电压下限:</label>
                            <input type="text" id="j_em_edit_config_voltagelimit" name="emconfig.voltagelimit" value="${emconfig.voltagelimit }" size="20">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_edit_config_currentupper" class="control-label x120">电流上限:</label>
                            <input type="text" id="j_em_edit_config_currentupper" name="emconfig.currentupper" value="${emconfig.currentupper }" size="20">
                        </td>
                        <td>
                            <label for="j_em_edit_config_currentlimit" class="control-label x120">电流下限:</label>
                            <input type="text" id="j_em_edit_config_currentlimit" name="emconfig.currentlimit" value="${emconfig.currentlimit }" size="20">
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp" %> 