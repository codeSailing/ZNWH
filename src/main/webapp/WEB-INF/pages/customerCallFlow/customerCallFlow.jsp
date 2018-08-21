<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/11
  Time: 15:39
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
    <title>外呼记录查询</title>
</head>
<body>
<%--任务配置页面--%>
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <input type="hidden" id="searchCallTaskId" value="${callTaskId}"/>
        <div class="right-content " id="right-content" style="display: block;">
            <form class="contform layui-form btm-bdr search-form">
                <div class="contform-heading layui-form-item">
                    <div class="layui-input-inline wd240">
                        <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder=" 输入任务名称查询" class="layui-input">
                        <button  type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search">外呼查询</button>
                    </div>
                    <%--<span class="more-btn">更多筛选条件</span>--%>
                    <label class="more mar-left120 mar-right20">
                        <%--<span></span>--%>
                        <a href="javascript:void(0);" class="more-btn">
                            更多筛选条件
                        </a>
                    </label>
                    <%--<a href="javascript:void(0);" class="more-btn mar-left120">--%>
                    <%--更多筛选条件--%>
                    <%--</a>--%>
                </div>
            </form>
            <form class="more-condition layui-form hide search-form-more">
                <div class="layui-form-item mar-top15">
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">外呼主题：</label>
                        <div class="layui-input-inline wd120">
                            <select name="callContentSubject">
                                <option value="">--请选择--</option>
                                <c:forEach items="${subjectslist}" var="subjectslist">
                                    <option value="${subjectslist.id}">${subjectslist.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">任务分类：</label>
                        <div class="layui-input-inline wd120">
                            <select name="callCategory">
                                <option value="">--请选择--</option>
                                <c:forEach items="${callCategories}" var="callCategories">
                                    <option value="${callCategories.id}">${callCategories.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <%--</div>--%>
                    <%--<div class="layui-form-item">--%>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">状态：</label>
                        <div class="layui-input-block wd535">
                            <input type="radio" name="status" value="0" title="待执行"  class="layui-input">
                            <input type="radio" name="status" value="1" title="执行中" class="layui-input">
                            <input type="radio" name="status" value="2" title="执行完毕" class="layui-input">
                            <input type="radio" name="status" value="3" title="已废弃" class="layui-input-inline">
                        </div>
                    </div>
                    <%--<div class="layui-inline">
                        <label class="layui-form-label wd120">执行日期：</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="time-horizon" placeholder="开始时间-结束时间" name="executetime">
                        </div>
                    </div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-form-item">--%>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">执行结果：</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="result" value="1" title="正常挂断"  class="layui-input">
                            <input type="radio" name="result" value="2" title="忙线" class="layui-input">
                        </div>
                    </div>
                </div>
            </form>
            <table class="layui-hide" id="content-configuration" lay-filter="callTaskList"></table>
            <div id="page-configuration"></div>
        </div>


        <!-- 节点页面 -->
        <div id="detail-customer" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" >
                    <div class="table-container" >
                        <div class="modal-header bg1">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="">
                                节点详情
                            </h4>
                        </div>
                        <%--<div class="modal-body">--%>
                        <%--<table class="layui-hide" id="table-result" lay-filter="callTaskList"></table>--%>
                        <%--</div>--%>
                        <div class="modal-body" style="overflow-y: auto;height: 400px;" id="tbody-result">
                            <%--<div>
                                <table class="ui nine column table celled table-result" id="table-result">
                                    <thead class="">
                                        <tr>
                                            <th style="width: 80px" class="">序号</th>
                                            <th style="max-width: 340px">问题</th>
                                            <th style="width: 100px">答案</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tbody-result">
                                    </tbody>
                                </table>
                            </div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="../../resources/js/src/customerCallFlow/customerCallFlow.js"></script>
        <script>
            taskconfiguration.init();
        </script>
</body>
</html>

<script type="text/html" id="contentSubject">
    {{d.callContentSubject.name}}
</script>

<script type="text/html" id="theme">
    {{#  if(d.name==null){ }}
    <a data-toggle="modal" href="#detail-customer" onclick="NodeMsg({{d.telephone}},{{d.taskid}})">-</a>
    {{#  } else{}}
    <a data-toggle="modal" href="#detail-customer" onclick="NodeMsg({{d.telephone}},{{d.taskid}})">{{d.name}}</a>
    {{#  } }}
</script>



<script type="text/html" id="result">
    {{#  if(d.result=='1'){ }}
    正常挂断
    {{# }else if(d.result=='2'){ }}
    忙线
    {{#  } else{}}

    {{#  } }}
</script>
<script type="text/html" id="status">
    {{#  if(d.status=='1'){ }}
    执行中
    {{# }else if(d.status=='2'){ }}
    执行完毕
    {{# }else if(d.status=='3'){ }}
    直接挂断
    {{#  } else{}}

    {{#  } }}
</script>



