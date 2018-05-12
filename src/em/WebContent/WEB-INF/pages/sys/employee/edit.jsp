<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<div class="bjui-pageContent">
    <form action="employee/save" data-toggle="validate" data-reload-navtab="true" method="post">
        <input type="hidden" name="employee.id" value="${employee.id }">
        <input type="hidden" name="employee.version" value="${employee.version + 1 }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label for="j_employee_edit_areaid" class="control-label x85">职工归属:</label>
                        <select id="j_employee_edit_areaid" name="employee.areaid" data-toggle="selectpicker" data-width="200" data-live-search="true">
                            <option></option>
                            <c:forEach items="${areaList }" var="a">
                                <option value="${a.id }" ${a.id == employee.areaid ? 'selected' : '' }>${a.name }</option>
                                <c:forEach items="${a.children }" var="s">
                                    <option value="${s.id }" ${s.id == employee.areaid ? 'selected' : '' }>&nbsp; - ${s.name }</option>
                                    <c:forEach items="${s.children }" var="ss">
                                        <option value="${ss.id }" ${ss.id == employee.areaid ? 'selected' : '' }>&nbsp;&nbsp;&nbsp;-- ${ss.name }</option>
                                    </c:forEach>
                                </c:forEach>
                                <option data-divider="true"></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_employee_edit_name" class="control-label x85">职工姓名:</label>
                        <input type="text" id="j_employee_edit_name" name="employee.name" value="${employee.name }" data-rule="required" size="20"><span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="" class="control-label x85">性别:</label>
                        <input type="radio" name="employee.sex" id="j_employee_edit_sex1" data-toggle="icheck" value="1" data-label="男" ${empty employee.sex || employee.sex ? 'checked' : '' }>
                        <input type="radio" name="employee.sex" id="j_employee_edit_sex2" data-toggle="icheck" value="0" data-label="女" ${!empty employee.sex && !employee.sex ? 'checked' : '' }>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_employee_edit_mobile" class="control-label x85">手机号码:</label>
                        <input type="text" id="j_employee_edit_mobile" name="employee.mobile" value="${employee.mobile }" size="20">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp"%>