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
<div class="shadow"></div>
<input type="hidden" id="flowId" value="${callFlow.id}">
<textarea name="" id="flowContent1" cols="30" rows="10" style="display: none;">${callFlow.callFlowInfo}</textarea>
<div id="WH" style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="flow-wrap layui-col-md12">
            <div class="layui-form-item" style="margin-bottom: -15px!important;">
                <div class="flow-head">
                    <h3>流程查看</h3>
                </div>
                <div class="flow-title layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">
                            <span style="color: red">*</span>
                            流程名称
                        </label>
                        <div class="layui-input-inline">
                            <input type="text"id="flowName" class="layui-input" value="${callFlow.name}">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">
                            流程描述
                        </label>
                        <div class="layui-input-inline">
                            <input type="text"id="flowContent" class="layui-input" value="${callFlow.descri}" maxlength="50">
                        </div>
                    </div>
                </div>
            </div>
            <div id="sample" style="position: relative;">
                <div style="width: 100%; display: flex; justify-content: center">
                    <div id="myPaletteDiv"
                         style="width: 105px; margin-right: 2px; background-color: #F0FAF5; border: solid 1px #ccc"></div>
                    <div id="myDiagramDiv" style="flex-grow: 1; height: 620px; border: solid 1px #ccc;background-color: #fff;"></div>
                </div>
                <input id="mySavedModel" type="hidden">
                <form id="test1" class="hide" style="position: absolute;top:20px;right:20px;background-color: rgb(246,253,250);z-index: 111111;width: 330px;border: 1px solid rgb(51,204,153);">
                    <div class="layui-form-item" style="margin-top: 15px;">
                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 100px;">
                                节点文本
                            </label>
                            <div class="layui-input-inline">
                                <textarea placeholder="请输入内容" id="content" cols="30" rows="10" class="layui-textarea" style="height: 100px;"></textarea>
                            </div>
                        </div>
                        <div class="layui-block" style="text-align: center;">
                            <button type="button" class="layui-btn btn-bg1" id="resetting" style="margin-left: 24px;">重置</button>
                            <button type="button" class="layui-btn btn-bg1" style="margin-right: 10px;" id="submitPart">确认</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/resources/js/src/callFlow/detail.js"></script>
<script>
    editFlow.init();
</script>
</body>
</html>
