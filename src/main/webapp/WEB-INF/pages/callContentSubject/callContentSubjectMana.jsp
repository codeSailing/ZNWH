<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/12
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>内容主题管理</title>
</head>
<body>
<div style="padding: 15px;">
    <div class="leftcontent layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="panel bdr">
                <div class="panel-heading"></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree hgh472"></ul>
                </div>
            </div>
        </div>
        <div class="rightcontent layui-col-md10">
            <form class="layui-form btm-bdr search-form">
                <div class=" layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="title" autocomplete="off" placeholder="输入主题名称查询"
                               class="layui-input">
                        <button type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1">主题查询</button>
                    </div>
                    <label class="more mar-left120 mar-right20">
                    </label>
                    <button class="layui-btn fl btn-bg1" data-toggle="modal" <%--data-target="#add-subject"--%> id="addNew" type="button">
                        新建
                    </button>
                    <button class="layui-btn fl btn-bg1" data-toggle="modal" type="button" id="deleteMany">
                        删除
                    </button>
                    <button class="layui-btn fl btn-bg1" type="button" onclick="history.go(-1)">返回内容管理</button>
                </div>
            </form>
            <table class="layui-hide" id="content-subjectmana" lay-filter="subjectList"></table>
            <div id="page-subjectmana"></div>
        </div>
    </div>
</div>

<%--新增弹框--%>
<div class="modal fade" id="add-subject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
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
                                主题名称：
                            </label>
                            <div class="layui-input-inline wd120">
                                <input type="text" name="name" lay-verify="title" autocomplete="off"
                                       class="layui-input" maxlength="200">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label wd120">
                            主题描述：
                        </label>
                        <div class="layui-input-block">
                            <textarea name="description" placeholder="请输入内容" class="layui-textarea wd400" maxlength="255"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1  bdr" id="commitAdd">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<%--修改弹框--%>
<div class="modal fade" id="updateSubject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    修改
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="updateForm">
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="parentId"/>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                主题名称：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="name" lay-verify="required|phone" autocomplete="off"
                                       class="layui-input" maxlength="200">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label wd120">
                            主题描述：
                        </label>
                        <div class="layui-input-block">
                            <textarea name="description" placeholder="请输入内容" class="layui-textarea wd400" maxlength="255"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitUpdate">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<%--删除弹框--%>
<div class="modal fade" id="del-subject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1" id="myModalLabel">
                    删除
                </h4>
            </div>
            <div class="modal-body">
                确认删除？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitDelete">
                    确认
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>

            </div>
        </div>
    </div>
</div>
<script type="text/html" id="bar-subjectmana">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="update">修改</a>
</script>
<script type="text/javascript" src="${ctx}/resources/js/src/callContentSubject/callContentSubject.js"></script>
<script>
    callContentSubject.init();
</script>
</body>
</html>
