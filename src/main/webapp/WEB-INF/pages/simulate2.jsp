<%--
  Created by IntelliJ IDEA.
  User: psf
  Date: 2018/7/10
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
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
                            <label  class="col-sm-2 control-label">定时任务倒计时：</label>
                            <div class="col-sm-10">
                                <input type="text" name="time" id="time" class="form-control" readonly>
                                <input type="hidden" name="ctx" id="ctx" value="${ctx}">
                                <input type="hidden" name="taskId" id="taskId" value="${taskId}">

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
<script>
change5();
</script>
</html>
