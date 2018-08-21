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
    <title>自定义报表配置</title>
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
                    <button class="btn btn-primary fl btn2">报表查询</button>

                    <a href="">占位符</a>
                    <button class="btn btn-primary fl btn2">新建</button>
                    <button class="btn btn-primary fl btn2" data-toggle="modal" data-target="#del">删除</button>
                </div>
            </form>
            <table class="layui-hide" id="content-customForm"></table>
            <div id="page-customForm"></div>
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
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    layui.use(['table','form','element','laypage','layer'], function(){
        var table=layui.table
            ,form=layui.form
            ,element=layui.elemengt
            ,laypage = layui.laypage
            ,layer=layui.layer;

        //展示已知数据
        table.render({
            elem: '#content-customForm'
            , cols: [[ //标题栏
                {type: 'checkbox'}
                , {field: 'formname', title: '报表名称', width: 200,}
                , {field: 'description', title: '报表描述', minWidth: 350}
                , {field: 'keyboarder', title: '更新人员', minWidth: 150}
                , {field: 'updatetime', title: '更新时间', minWidth: 150}
                , {field: 'right', title: '操作', width: 180, align: 'center', toolbar: '#bar-customForm'}
            ]]
            , data: [{

            }]

        });
        laypage.render({
            elem: 'page-customForm' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: 15 //数据总数，从服务端得到
            ,layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
            ,jump: function(obj){
                console.log(obj)
            }
        });
    });
</script>
<script type="text/html" id="bar-customForm">
    <a class="layui-btn layui-btn-primary layui-btn-xs" data-toggle="modal" data-target="#del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
</script>
</body>
</html>
