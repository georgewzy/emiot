<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<div class="bjui-pageContent">
    <table class="table table-hover">
        <tbody>
            <tr>
                <td>
                    <h4 <c:if test="${!empty systemnews.titlecolor }">style="color:${systemnews.titlecolor }"</c:if>>${systemnews.title }</h4>
                </td>
            </tr>
            <tr>
                <td>
                    ${empname }  <fmt:formatDate value="${systemnews.createtime }" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
            </tr>
            <tr>
                <td>
                    ${systemnews.content }
                </td>
            </tr>
        </tbody>
    </table>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>