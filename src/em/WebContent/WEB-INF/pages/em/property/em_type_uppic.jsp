<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<script type="text/javascript">
</script>
<div class="bjui-pageContent">
    <form action="em/type/savePic" data-toggle="validate" data-reload-navtab="true" enctype="multipart/form-data">
        <input type="hidden" name="emtype.id" value="${emtype.id }">
        <input type="hidden" name="emtype.name" value="${emtype.name }">
        <table class="table table-hover">
            <tbody>
                <tr>
                    <td>
                        <label class="control-label x100">设备类型名称:</label>
                        ${emtype.name }
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="control-label x100">设备类型图片:</label>
                        <input type="file" name="file" accept="image/png, image/jpeg">
                        * jpg 或 png格式，1M 以内。
                    </td>
                </tr>
                <c:if test="${!empty emtype.pic }">
                    <tr>
                        <td>
                            <label class="control-label x100">当前图片:</label>
                            <img src="${ctx }${emtype.pic }?t=${random }" width="200">
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="../../include/foot_edit.jsp"%>