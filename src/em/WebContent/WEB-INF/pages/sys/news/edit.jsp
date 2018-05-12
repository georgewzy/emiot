<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include/includetag.jsp"%>
<div class="bjui-pageContent">
    <div style="margin:0 auto; max-width:900px;">
        <form action="systemnews/save" data-toggle="validate" data-reload-navtab="true" method="post">
            <input type="hidden" name="systemnews.id" value="${systemnews.id }">
            <input type="hidden" name="systemnews.version" value="${systemnews.version + 1 }">
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <td>
                            <label for="j_systemnews_edit_cateid" class="control-label x100">所属类别：</label>
                            <select id="j_systemnews_edit_cateid" name="systemnews.cateid" data-toggle="selectpicker" data-rule="required">
                                <option></option>
                                <c:forEach items="${cateList }" var="s">
                                    <option value="${s.id }" ${s.id == systemnews.cateid ? 'selected' : '' }>${!empty s.parentid ? '&nbsp; - ' : '' }${s.name }</option>
                                </c:forEach>
                            </select><span class="required"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_systemnews_edit_title" class="control-label x100">标题:</label>
                            <input type="text" id="j_systemnews_edit_title" name="systemnews.title" value="${systemnews.title }" data-rule="required" size="40"><span class="required"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_systemnews_edit_titlecolor" class="control-label x100">标题颜色:</label>
                            <input type="text" id="j_systemnews_edit_titlecolor" name="systemnews.titlecolor" value="${systemnews.titlecolor }" data-toggle="colorpicker" data-bgcolor="true" size="10" readonly="readonly" <c:if test="${!empty systemnews.titlecolor }">style="background-color:${systemnews.titlecolor }"</c:if>>
                            <a href="javascript:;" title="清除颜色" data-toggle="clearcolor" data-target="#j_systemnews_edit_titlecolor">清除颜色</a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="" class="control-label x100">是否固顶:</label>
                            <input type="radio" name="systemnews.istop" id="j_systemnews_edit_istop1" data-toggle="icheck" value="1" data-label="是&nbsp;&nbsp;" ${systemnews.istop ? 'checked' : '' }>
                            <input type="radio" name="systemnews.istop" id="j_systemnews_edit_istop2" data-toggle="icheck" value="0" data-label="否" ${!systemnews.istop ? 'checked' : '' }>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="" class="control-label x100">显示到主页:</label>
                            <input type="radio" name="systemnews.ishome" id="j_systemnews_edit_ishome1" data-toggle="icheck" value="1" data-label="是&nbsp;&nbsp;" ${systemnews.ishome ? 'checked' : '' }>
                            <input type="radio" name="systemnews.ishome" id="j_systemnews_edit_ishome2" data-toggle="icheck" value="0" data-label="否" ${!systemnews.ishome ? 'checked' : '' }>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_systemnews_edit_createtime" class="control-label x100">发布时间:</label>
                            <input type="text" id="j_systemnews_edit_createtime" name="systemnews.createtime" value="<fmt:formatDate value="${systemnews.createtime }" pattern="yyyy-MM-dd HH:mm:ss" />" data-toggle="datepicker" data-pattern="yyyy-MM-dd HH:mm:ss" data-rule="required;datetime" size="20"><span class="required"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="control-label x100">文档内容:</label>
                            <div style="display:inline-block; vertical-align:middle;">
                                <textarea name="systemnews.content" id="j_systemnews_edit_content" style="width:700px;" data-toggle="kindeditor" data-min-height="300" data-upload-json="doupload/up">
                                    ${systemnews.content }
                                </textarea>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<%@ include file="../../include/foot_edit.jsp"%>