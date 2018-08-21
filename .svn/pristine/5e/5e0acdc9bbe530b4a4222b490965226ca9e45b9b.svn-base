<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/11
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>流程配置</title>
</head>
<body>
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="flowForm">
            <form class="contform layui-form btm-bdr">
                <div class="contform-heading layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="输入流程名称或描述查询" id="searchParams" class="layui-input">
                        <button type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search">流程查询</button>
                    </div>
                    <button class="layui-btn fl btn-bg1 mar-left120" id="addFlow" type="button">新建</button>
                    <%--<button class="layui-btn fl btn-bg1 mar-left120" data-toggle="modal" data-target="#add-callFlow" id="addFlow" type="button">新建</button>--%>
                    <button type="button" class="layui-btn fl btn-bg1" id="deleteMany" data-toggle="modal">删除</button>
                </div>
            </form>
            <table class="layui-hide" id="content-flow" lay-filter="contentList"></table>
            <div id="page-flow"></div>
        </div>
    </div>
</div>

<%--新增--%>
<div class="modal fade" id="add-callFlow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    流程创建
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" action='' enctype="multipart/form-data" method="post" name="fileForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                流程名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" name="name" required class="layui-input wd240">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                流程描述：
                            </label>
                            <div class="layui-input-inline">
                                <textarea type="text" name="descri" maxlength="200"  rows="3" cols="50" class="textarea423 layui-textarea"></textarea>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" name="descri" class="layui-input">--%>
                            <%--</div>--%>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                资源文件：
                            </label>
                            <div class="layui-input-inline" id="resourcePath">
                                <%--<label for="upload">--%>
                                <input type="file" id="upload" class="file mar-top15 wd240" name="filename">
                                <%--</label>--%>
                            </div>
                        </div>
                    </div>
                    <%--<div class="layui-form-item layui-form-text">--%>
                        <%--<label class="layui-form-label wd120">--%>
                            <%--<span class="red">*</span>--%>
                            <%--资源文件：--%>
                        <%--</label>--%>
                        <%--<div class="layui-input-inline">--%>
                            <%--<input type="text" class="layui-input">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="modal-footer">
                        <div class="layui-input-block">
                            <button type="button" class="btn btn-primary btn-bg1 bdr" id="add-submit">提交</button>
                            <button type="reset" class="btn btn-primary btn-bg1 bdr">
                                重置
                            </button>
                            <button type="reset" class="btn btn-default" data-dismiss="modal">返回</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--修改--%>
<div class="modal fade" id="updateFlow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    修改
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="updateForm">
                    <input type="hidden" name="updateId"/>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                流程名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input wd240" required name="updateName">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                流程描述：
                            </label>
                            <div class="layui-input-inline">
                                <textarea type="text" name="updateDescri" maxlength="200" rows="3" cols="50" class="textarea423 layui-textarea"></textarea>
                            </div>
                            <%--<div class="layui-input-inline">--%>
                                <%--<input type="text" class="layui-input" name="updateDescri">--%>
                            <%--</div>--%>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label wd120">
                            <span class="red">*</span>
                            资源路径：
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input wd240" name="updateResourcePath" readonly>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="layui-input-block">
                            <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitUpdate">提交</button>
                            <button type="button"   id="resetUpdate" class="btn btn-primary btn-bg1 bdr">重置</button>
                            <button type="reset" class="btn btn-default" data-dismiss="modal">返回</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--删除弹框--%>
<div class="modal fade" id="del" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
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
<script type="text/javascript" src="../../../resources/js/src/callFlow/callFlow.js"></script>
<script>
    callFlow.init();
</script>
<script id="resourcePath_handlebars" type="text/x-handlebars-template">
    <ul>
        {{#each this}}
        <li>{{this}}<input name="path" value="{{this}}" type="radio"/></li>
        {{/each}}
    </ul>
</script>
<script type="text/html" id="bar-flow">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="delete" data-target="#del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-xs" lay-event="look">查看</a>
</script>
</body>
</html>
