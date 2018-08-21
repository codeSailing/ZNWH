<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<meta http-equiv="Refresh" CONTENT="0" URL="/login.do">--%>
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
    <title>业务指标管理</title>
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
                        <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="输入指标名称或描述查询" class="layui-input">
                    </div>
                    <button class="layui-btn">指标查询</button>

                    <a href="">占位符</a>
                    <button class="layui-btn" data-toggle="modal" data-target="#add">新建</button>
                    <button class="layui-btn" data-toggle="modal" data-target="#del">删除</button>
                </div>
            </form>
            <table class="layui-hide" id="content-Indicators"></table>
            <div id="page-Indicators"></div>
        </div>
    </div>
</div>

<%--新增业务弹窗--%>
<div id="add" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="add-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    新建
                </h4>
            </div>
            <div class="add-content">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">
                                <span>*</span>
                                指标名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">
                                <span>*</span>
                                是否启用：
                            </label>
                            <div class="layui-input-inline">
                                <input type="radio" name="tasktype" value="启用" title="启用" checked="" class="layui-input-inline">
                                <input type="radio" name="tasktype" value="暂不启用" title="暂不启用" class="layui-input-inline">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">指标描述：</label>
                        <div class="layui-input-block">
                            <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn">提交</button>
                            <button type="" class="layui-btn">取消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--修改业务弹窗--%>
<div id="edit" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="add-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    修改
                </h4>
            </div>
            <div class="add-content">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">
                                <span>*</span>
                                指标名称：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">
                                <span>*</span>
                                是否启用：
                            </label>
                            <div class="layui-input-inline">
                                <input type="radio" name="tasktype" value="启用" title="启用" checked="" class="layui-input-inline">
                                <input type="radio" name="tasktype" value="暂不启用" title="暂不启用" class="layui-input-inline">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">指标描述：</label>
                        <div class="layui-input-block">
                            <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn">提交</button>
                            <button type="" class="layui-btn">取消</button>
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
            elem: '#content-Indicators'
            , cols: [[ //标题栏
                {type: 'checkbox'}
                , {field: 'Indicatorsname', title: '指标名称', width: 150,}
                , {field: 'description', title: '指标描述', minWidth: 300}
                , {field: 'right', title: '操作', width: 220, align: 'center', toolbar: '#bar-Indicators'}
            ]]
            , data: [{

            }]

        });
        laypage.render({
            elem: 'page-Indicators' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: 15 //数据总数，从服务端得到
            ,layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
            ,jump: function(obj){
                console.log(obj)
            }
        });
    });
</script>
<script type="text/html" id="bar-Indicators">
    <a class="layui-btn layui-btn-primary layui-btn-xs" data-toggle="modal" data-target="#del">删除</a>
    <a class="layui-btn layui-btn-xs" data-toggle="modal" data-target="#edit">修改</a>
    <a class="layui-btn layui-btn-xs">计算机模型配置</a>
</script>
</body>
</html>
