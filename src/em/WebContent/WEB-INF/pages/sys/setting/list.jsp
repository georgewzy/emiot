<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="setting/save" data-toggle="validate" style="margin:0 auto; max-width:1000px;">
        <input type="hidden" name="setting.id" value="${setting.id }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label for="j_system_setting_savesyslogday" class="control-label">系统操作日志保存时间:</label>
                        <input type="text" id="j_system_setting_savesyslogday" name="setting.savesyslogday" value="${setting.savesyslogday }" size="15" data-rule="digits">
                        <label>天</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_system_setting_saveemlogday" class="control-label">设备操作日志保存时间:</label>
                        <input type="text" id="j_system_setting_saveemlogday" name="setting.saveemlogday" value="${setting.saveemlogday }" size="15" data-rule="digits">
                        <label>天</label>
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
    </form>
</div>