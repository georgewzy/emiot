<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="emlist/saveSetting" data-toggle="validate" style="margin:0 auto; max-width:1000px;">
        <input type="hidden" name="emsetting.id" value="${emsetting.id }">
        <input type="hidden" name="emconfig.id" value="${emconfig.id }">
        <input type="hidden" name="emsetting.eminfoid" value="${emsetting.eminfoid }">
        <input type="hidden" name="emconfig.eminfoid" value="${emconfig.eminfoid }">
        <fieldset>
            <legend>设备参数设置</legend>
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <td>
                            <label for="j_em_setting_edit_setting_ip" class="control-label x120">IP地址:</label>
                            <input type="text" id="j_em_setting_edit_setting_ip" name="emsetting.ip" value="${emsetting.ip }" size="16">
                        </td>
                        <td>
                            <label for="j_em_setting_edit_setting_port" class="control-label x120">端口号:</label>
                            <input type="text" id="j_em_setting_edit_setting_port" name="emsetting.port" value="${emsetting.port }" size="16">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_setting_edit_setting_apn" class="control-label x120">APN:</label>
                            <input type="text" id="j_em_setting_edit_setting_apn" name="emsetting.apn" value="${emsetting.apn }" size="16">
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_setting_edit_setting_lightid" class="control-label x120">灯质:</label>
                            <select id="j_em_setting_edit_setting_lightid" name="emsetting.lightid" data-toggle="selectpicker" data-width="160">
                                <option></option>
                                <c:forEach items="${lightList }" var="s">
                                    <option value="${s.id }"${emsetting.lightid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <label for="j_em_setting_edit_setting_lightsourceid" class="control-label x120">灯质源:</label>
                            <select id="j_em_setting_edit_setting_lightsourceid" name="emsetting.lightsourceid" data-toggle="selectpicker" data-width="160">
                                <option></option>
                                <c:forEach items="${lightSourceList }" var="s">
                                    <option value="${s.id }"${emsetting.lightsourceid == s.id ? ' selected' : '' }>${s.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_em_setting_edit_setting_charginglimit" class="control-label x120">充电电压限值(mV):</label>
                            <input type="text" id="j_em_setting_edit_setting_charginglimit" name="emsetting.charginglimit" value="${emsetting.charginglimit }" size="16" data-rule="digits">
                        </td>
                        <td>
                            <label for="j_em_setting_edit_setting_dischargelimit" class="control-label x120">放电电压限值(mV):</label>
                            <input type="text" id="j_em_setting_edit_setting_dischargelimit" name="emsetting.dischargelimit" value="${emsetting.dischargelimit }" size="16" data-rule="digits">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="control-label x120">强制工作允许:</label>
                            <input type="radio" name="emsetting.forcedwork" id="j_em_setting_edit_setting_forcedwork1" value="1" data-toggle="icheck" data-label="是&nbsp;&nbsp;" ${emsetting.forcedwork ? 'checked' : '' }>
                            <input type="radio" name="emsetting.forcedwork" id="j_em_setting_edit_setting_forcedwork2" value="0" data-toggle="icheck" data-label="否" ${!emsetting.forcedwork ? 'checked' : '' }>
                        </td>
                        <td>
                            <label class="control-label x120">同步工作允许:</label>
                            <input type="radio" name="emsetting.syncwork" id="j_em_setting_edit_setting_syncwork1" value="1" data-toggle="icheck" data-label="是&nbsp;&nbsp;" ${emsetting.syncwork ? 'checked' : '' }>
                            <input type="radio" name="emsetting.syncwork" id="j_em_setting_edit_setting_syncwork2" value="0" data-toggle="icheck" data-label="否" ${!emsetting.syncwork ? 'checked' : '' }>
                        </td>
                    </tr>
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
                    <tr>
                        <td colspan="2">
                            <br>
                            <label class="control-label x120"></label>
                            <button type="submit" class="btn-green" data-icon="save">保存设置</button>
                            <br>
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </form>
</div>