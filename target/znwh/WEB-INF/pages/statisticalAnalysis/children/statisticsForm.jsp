<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../../common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>统计报表</title>
</head>
<body>
<div style="padding: 15px;">
    <div class="leftcontent layui-row layui-col-space10">
        <div class="layui-col-md3">
            <div class="panel bdr">
                <div class="panel-heading">
                    等接口开发树状图
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="rightcontent layui-col-md9">
            <form class="contform layui-form btm-bdr">
                <div class="contform-heading layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="输入流程名称或描述查询" class="layui-input">
                    </div>
                    <button class="layui-btn">报表查询</button>
                    <label class="more mar-left120 mar-right20">
                        <%--<span></span>--%>
                        <a href="javascript:void(0);" class="more-btn">
                            更多筛选条件

                        </a>
                    </label>
                </div>
            </form>
            <form class="layui-form hide more-condition">
                <div class="layui-form-item mar-top15">
                    <div class="layui-inline">
                        <label class="layui-form-label">任务类型</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="tasktype" value="一次性" title="一次性" checked="" class="layui-input-inline">
                            <input type="radio" name="tasktype" value="周期性" title="周期性" class="layui-input-inline">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">执行日期</label>
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input" id="time-horizon" placeholder=" - ">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">外呼主题</label>
                        <div class="layui-input-inline">
                            <select name="quiz1">
                                <option value="">--请选择--</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                        <div class="layui-input-inline">
                            <select name="quiz2">
                                <option value="">--请选择--</option>
                                <option value="11">11</option>
                                <option value="22">22</option>
                                <option value="33">33</option>
                                <option value="44">44</option>
                                <option value="55">55</option>
                            </select>
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">状态</label>
                        <div class="layui-input-block">
                            <input type="radio" name="Status" value="待执行" title="待执行" checked="">
                            <input type="radio" name="Status" value="执行中" title="执行中">
                            <input type="radio" name="Status" value="执行完毕" title="执行完毕">
                            <input type="radio" name="Status" value="已废弃" title="已废弃">
                        </div>
                    </div>
                </div>
            </form>
            <table class="layui-hide" id="content-statisticsForm"></table>
            <div id="page-statisticsForm"></div>
        </div>
    </div>
</div>
<script>
    $('.more-btn').click(function () {
        if ($('.more-condition').css('display') == "none") {
            $(this).addClass('down');
            $('.more-condition').addClass('show');
        } else if ($('.more-condition').hasClass('show')) {
            $(this).removeClass('down');
            $('.more-condition').removeClass('show');
        }
    });
    layui.use(['table','form','element','laypage','layer'], function(){
        var table=layui.table
            ,form=layui.form
            ,element=layui.elemengt
            ,laypage = layui.laypage
            ,layer=layui.layer;

        //展示已知数据
        table.render({
            elem: '#content-statisticsForm'
            , cols: [[ //标题栏
                {type: 'checkbox'}

                , {field: 'taskname', title: '任务名称', width: 140,}
                , {field: 'taskcategory', title: '任务类型', width: 100,}
                , {field: 'tasktheme', title: '外呼主题', width: 140,}
                , {field: 'flowpath', title: '外呼会话流程', width: 140,}
                , {field: 'client', title: '外呼客户(人)', width: 140,}
                , {field: 'taskExecutions', title: '任务执行次数', width: 300,}
                , {field: 'callsnumber', title: '外呼次数', width: 100,}
                , {field: 'connectnumber', title: '接通率', width: 100,}
                , {field: 'meanduration', title: '平均通话时长(min)', width: 100,}
                , {field: 'right', title: '操作', width: 180, align: 'center', toolbar: '#bar-statisticsForm'}
            ]]
            , data: [{

            }]

        });
        laypage.render({
            elem: 'page-statisticsForm' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: 15 //数据总数，从服务端得到
            ,layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
            ,jump: function(obj){
                console.log(obj)
            }
        });
        laydate.render({
            elem: '#time-horizon'
            ,range: true
        });
    });
</script>
<script type="text/html" id="bar-statisticsForm">
    <a class="layui-btn layui-btn-primary layui-btn-xs">查看分析报表</a>
    <a class="layui-btn layui-btn-xs">配置分析报表</a>
</script>
</body>
</html>