<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <title>任务分类</title>
</head>
<body>
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="panel bdr">
                <div class="panel-heading">
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree hgh472"></ul>

                </div>
            </div>
        </div>
        <div class="layui-col-md10">
            <form class="contform layui-form btm-bdr">
                <div class="contform-heading layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" name="keyword" id="keyword" lay-verify="title" autocomplete="off" placeholder="输入分类名称查询" class="layui-input">
                        <button class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search" type="button" id="search" data-type="reload">分类查询</button>
                    </div>
                    <button class="layui-btn fl btn-bg1 mar-left120" data-toggle="modal"  type="button" <%--data-target="#add"--%> id="addNew" >新建</button>
                    <button class="layui-btn fl btn-bg1" data-toggle="modal"  type="button" <%--data-target="#del"--%> id="deleteMany">删除</button>
                </div>
            </form>
            <table class="layui-hide" id="content-category" lay-filter="demo"></table>
            <div id="page-category"></div>
        </div>
    </div>
</div>
<%--删除弹框--%>
<%--<div class="modal fade" id="del" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1" id="deleteLabel">
                    删除
                </h4>
            </div>
            <div class="modal-body">
                确认删除？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1" id="delSubmit">
                    确认
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
            </div>
        </div>
    </div>
</div>--%>
<%--新增弹框--%>
<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1" id="addLabel">
                    新建
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="addForm">
                    <input type="hidden" name="parentId" id="parentId"/>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                分类名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="name"  id="addName" lay-verify="required" autocomplete="off" class="layui-input" maxlength="200">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                分类描述：
                            </label>
                            <div class="layui-input-inline">
                                <textarea name="descri" id="addDescri" placeholder="请输入内容" class="layui-textarea wd400"  maxlength="200"></textarea>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="addSubmit">
                    提交
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
            </div>
        </div>
    </div>
</div>
<%--修改弹框--%>
<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1" id="updLabel">
                    修改
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="updForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                分类名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="name" id ="name" lay-verify="required" autocomplete="off" class="layui-input" maxlength="200">
                                <input type="hidden" name="id" id ="id">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                分类描述：
                            </label>
                            <div class="layui-input-inline">
                                <textarea name="descri" id ="descri" placeholder="请输入内容" class="layui-textarea wd400"  maxlength="200"></textarea>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="updSubmit">
                    修改
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="bar-category">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
</script>
<script type="text/javascript" src="${ctx}/resources/js/src/category/category.js"></script>
<script>
    category.init();
</script>
</body>
</html>
