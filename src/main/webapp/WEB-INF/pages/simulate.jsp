<%--
&lt;%&ndash;
  Created by IntelliJ IDEA.
  User: psf
  Date: 2018/7/10
  Time: 15:46
  To change this template use File | Settings | File Templates.
&ndash;%&gt;
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="common/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <title>智能外呼系统</title>
</head>
<body class="main">
<div class="wrapper">
    <div class="row">
        <div class="col-md-12">
            <section class="panel">
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">选择电话号码：</label>
                            <div class="col-sm-10">
                                <select name="phones" id="phones" class="form-control" onchange="show_sub(this.options[this.options.selectedIndex].value)">
                                    <option checked>请选择</option>
                                    <c:forEach var="item" items="${phones}">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <input type="hidden" name="userPhone" id="phoneStr" value="${userPhone}">
                        </div>

                        <div class="form-group">
                            <label  class="col-sm-2 control-label">语音播放：</label>
                            <div class="col-sm-10">
                                <input type="text" name="voice" id="voice" class="form-control" readonly>
                                <input type="hidden" name="userName" id="currentNode" value="${currentNode}">
                                <input type="hidden" name="userPhone" id="userPhone">
                                <input type="hidden" name="callContent" id="callContent" value="${callContent}">
                                <input type="hidden" name="taskid" id="taskid" value="${taskid}">
                                <input type="hidden" name="callFlowId" id="callFlowId" value="${callFlowId}">
                                <input type="hidden" name="ctx" id="ctx" value="${ctx}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">通话内容：</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3"  name="desc" id="desc" placeholder="请输入内容"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="layui-btn" onclick="submitForm()">发送</button>
                                <button type="button" class="layui-btn" onclick="interrupt()">挂断</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">对话信息：</label>
                            <div class="col-sm-10">
                                <div id="flowContent">
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </section>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/resources/js/src/ivrtest.js"></script>
</html>
--%>
