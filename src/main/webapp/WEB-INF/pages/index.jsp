<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<%@include file="common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>智能外呼系统</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">智能外呼系统</div>
        <%--<ul class="layui-nav layui-layout-right">--%>
            <%--<li class="layui-nav-item">--%>
                <%--<a href="javascript:;">--%>
                    <%--<img src="http://t.cn/RCzsdCq" class="layui-nav-img">--%>
                    <%--贤心--%>
                <%--</a>--%>
                <%--<dl class="layui-nav-child">--%>
                    <%--<dd><a href="">基本资料</a></dd>--%>
                    <%--<dd><a href="">安全设置</a></dd>--%>
                <%--</dl>--%>
            <%--</li>--%>
            <%--<li class="layui-nav-item"><a href="">退了</a></li>--%>
        <%--</ul>--%>
    </div>

    <div class="layui-side bg3">
        <div class="layui-side-scroll">
            <p class="nav-title">功能菜单</p>
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="leftsidebar layui-nav layui-nav-tree"  lay-filter="test" id="menu-ul">
                <c:forEach items="${list}" var="li">
                    <c:if test="${li.status ==0}">
                        <li class="layui-nav-item">
                            <a data-url="${ctx}/${li.url}">${li.resourceName}</a>
                        </li>
                    </c:if>
                    <c:if test="${li.status ==1}">
                        <li class="layui-nav-item">
                            <a data-url="${li.url}">${li.resourceName}</a>
                            <dl class="layui-nav-child">
                                <c:forEach items="${childList}" var="childLi">
                                    <dd><a data-url="${ctx}/${childLi.url}">${childLi.resourceName}</a></dd>
                                </c:forEach>
                            </dl>
                        </li>
                    </c:if>
                </c:forEach>
                <%--<li class="layui-nav-item layui-this"><a data-url="customer/showCustomer.do">客户管理</a></li>--%>
                <%--<li class="layui-nav-item"><a data-url="/callContent/page.do">内容管理</a></li>--%>
                <%--<li class="layui-nav-item">--%>
                    <%--<a data-url="./">任务管理</a>--%>
                    <%--<dl class="layui-nav-child">--%>
                        <%--<dd><a data-url="/callCategory/list.do">任务分类</a></dd>--%>
                        <%--<dd><a data-url="/callTask/list.do">任务配置</a></dd>--%>
                        <%--<dd><a data-url="/callFlow/list.do">流程配置</a></dd>--%>
                        <%--<dd><a data-url="/customerCallFlow/showMsg.do">外呼记录查询</a></dd>--%>
                        <%--&lt;%&ndash;<dd><a data-url="customer/showCustomer.do">外呼记录查询</a></dd>&ndash;%&gt;--%>
                    <%--</dl>--%>
                <%--</li>--%>
                <%--<li class="layui-nav-item">--%>
                    <%--<a data-url="./">统计分析</a>--%>
                    <%--<dl class="layui-nav-child">--%>
                        <%--<dd><a href="javascript:;">业务指标管理</a></dd>--%>
                        <%--<dd><a href="javascript:;">自定义报表配置</a></dd>--%>
                        <%--<dd><a href="">统计报表</a></dd>--%>
                    <%--</dl>--%>
                <%--</li>--%>
            </ul>
        </div>
    </div>
    <div class="layui-body" style="overflow: hidden;">
        <iframe width="100%" height="100%" id="iframepage"
                name="iframepage" marginheight="0" marginwidth="0" frameborder="0"
                scrolling="auto" src="customer/showCustomer.do"></iframe>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        2018 © 善达信息版权所有 v1.0
    </div>
</div>
<script src="${ctx}/resources/js/plugin/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
    $(function () {
        $('.layui-nav-item').first().addClass('layui-this');
    })
    $('.layui-nav a').click(function () {
        var len = $(this).parent().children().length;
        var url = $(this).attr('data-url');
        var url2 =  $(this).parent().find('dd').first().find('a').attr('data-url');
        if(len>1){
            document.getElementById("iframepage").src=url2;
            $('.layui-nav-item').removeClass('layui-this');
            $(this).parent().find('dd').first().addClass('layui-this');
        }else {
            document.getElementById("iframepage").src=url;
        }
    })
</script>
</body>
</html>