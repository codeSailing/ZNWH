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
    <title>任务客户外呼记录查询</title>
</head>
<body>
<%--任务客户外呼记录页面--%>
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="right-content " id="right-content" style="display: block;">
            <form class="contform layui-form btm-bdr search-form">
                <div class="contform-heading layui-form-item">
                    <div class="layui-input-inline wd240">
                        <input type="text" name="taskName" lay-verify="title" autocomplete="off" placeholder=" 输入任务名称查询" class="layui-input">
                        <button  type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search">查询</button>
                    </div>
                    <label class="more mar-left120 mar-right20">
                        <a href="javascript:void(0);" class="more-btn">
                            更多筛选条件
                        </a>
                    </label>
                </div>
            </form>
            <form class="more-condition layui-form hide search-form-more">
                <div class="layui-form-item mar-top15">
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">执行状态：</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="status" value="0" title="接通"  class="layui-input">
                            <input type="radio" name="status" value="1" title="挂断" class="layui-input">
                        </div>
                    </div>
                </div>
            </form>
            <table class="layui-hide" id="content-record"></table>
            <div id="page-configuration"></div>
        </div>


        <!-- 记录详情页面 -->
        <div id="detail-customer" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" >
                    <div class="table-container" >
                        <div class="modal-header bg1">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="">
                                记录详情
                            </h4>
                        </div>
                        <div class="modal-body" style="overflow-y: auto;height: 400px;" id="tbody-result">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="${ctx}/resources/js/src/outBoundTaskRecord/record.js"></script>
        <script>
            outBoundTaskRecord.init();
        </script>
</body>
</html>


<script type="text/html" id="theme">
    {{#  if(d.phone==null){ }}
    <a data-toggle="modal" href="#detail-customer" style="color:#4b47d1" onclick="NodeMsg({{d.id}})">-</a>
    {{#  } else{}}
    <a data-toggle="modal" href="#detail-customer" style="color:#4b47d1" onclick="NodeMsg({{d.id}})">{{d.phone}}</a>
    {{#  } }}
</script>

<script type="text/html" id="status">
    {{#  if(d.status=='0'){ }}
    接通
    {{# }else if(d.status=='1'){ }}
    挂断
    {{#  } else{}}

    {{#  } }}
</script>



