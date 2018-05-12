<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="em/service/save" data-toggle="validate" style="margin:0 auto; max-width:1000px;">
        <input type="hidden" name="service.id" value="${service.id }">
        <input type="hidden" name="service.eminfoid" value="${service.eminfoid }">
        <input type="hidden" name="service.emtypeid" value="${service.emtypeid }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label class="control-label x120">设备名称(编号):</label>
                        ${em.name }(${em.code })
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x120">设备类型:</label>
                        ${em.typename }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_service_edit_servicetypeid" class="control-label x120">维护类型:</label>
                        <select id="j_em_service_edit_servicetypeid" name="service.servicetypeid" data-toggle="selectpicker" data-width="200" data-rule="required">
                            <option></option>
                            <c:forEach items="${servicetypeList }" var="s">
                                <option value="${s.id }"${service.servicetypeid == s.id ? ' selected' : '' }>${s.name }</option>
                            </c:forEach>
                        </select>
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_service_edit_serviceuser" class="control-label x120">维护人:</label>
                        <input type="text" id="j_em_service_edit_serviceuser" name="service.serviceuser" value="${service.serviceuser }" size="20" data-rule="required">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_service_edit_servicetime" class="control-label x120">维护时间:</label>
                        <input type="text" id="j_em_service_edit_servicetime" name="service.servicetime" value="<fmt:formatDate value="${service.servicetime }" pattern="yyyy-MM-dd HH:mm:ss"/>" size="20" data-toggle="datepicker" data-pattern="yyyy-MM-dd HH:mm:ss" data-rule="required;datetime">
                        <span class="required"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_service_edit_servicetime" class="control-label x120">备注:</label>
                        <textarea id="j_em_service_edit_servicetime" name="service.beizhu" rows="1" cols="30" data-toggle="autoheight">${service.beizhu }</textarea>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp" %> 