<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp" %>
<div class="bjui-pageContent">
    <form action="em/quality/save" data-toggle="validate" style="margin:0 auto; max-width:1000px;">
        <input type="hidden" name="quality.id" value="${quality.id }">
        <input type="hidden" name="quality.eminfoid" value="${em.id }">
        <input type="hidden" name="quality.emtypeid" value="${em.typeid }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label class="control-label x120">设备名称(编号):</label>
                        ${em.name }(${em.code })
                    </td>
                    <td>
                        <label class="control-label x120">设备类型:</label>
                        ${em.typename }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_quality_edit_enddate" class="control-label x120">质保终止日期:</label>
                        <input type="text" id="j_em_quality_edit_enddate" name="quality.enddate" value="${quality.enddate }" size="20" data-toggle="datepicker" data-rule="date">
                    </td>
                    <td>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_em_quality_edit_beizhu" class="control-label x120">备注:</label>
                        <textarea id="j_em_quality_edit_beizhu" name="quality.beizhu" cols="40" rows="1" data-toggle="autoheight">${quality.beizhu }</textarea>
                    </td>
                    <td>
                        
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