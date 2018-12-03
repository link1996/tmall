<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>编辑分类</title>

<script>
    $(function () {

    });
</script>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li class="active">编辑分类</li>
    </ol>
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑分类</div>
        <div class="panel-body">
            <form action="admin_category_update" method="post" enctype="multipart/form-data" id="editForm">
                <table class="editTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input type="text" id="name" name="name" value="${c.name}" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td>
                            <input type="file" name="filepath" id="categoryPic" accept="image/*">
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>

        </div>
    </div>
</div>

