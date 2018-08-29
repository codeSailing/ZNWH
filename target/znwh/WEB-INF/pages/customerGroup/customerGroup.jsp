<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客户分组管理</title>
</head>
<body>
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="panel bdr">
                <div class="panel-heading">
                    <%--<a href="${ctx}/callContentSubject/page.do" class="layui-btn btn-bg1">管理主题</a>--%>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree hgh472"></ul>
                </div>
            </div>
        </div>
        <div class="layui-col-md10">
            <form class="layui-form btm-bdr search-form">
                <div class=" layui-form-item">
                    <div class="layui-input-inline wd240">
                        <input type="text" name="searchParam" lay-verify="title" autocomplete="off"
                               placeholder="输入分组名称查询"
                               class="bdr-right0 layui-input">
                        <button type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search">内容查询</button>
                    </div>
                    <button type="button" class="layui-btn fl btn-bg1 mar-left120" data-toggle="modal" id="addContengtModle">
                        新建
                    </button>
                    <button type="button" class="layui-btn fl btn-bg1" id="deleteMany">删除</button>
                    <a type="button" href="/customer/showCustomer.do" class="layui-btn fl btn-bg1">返回客户管理</a>
                </div>
            </form>
            <table class="layui-hide" id="content-subject" lay-filter="contentList"></table>
        </div>
    </div>
</div>
<!-- 新增内容模态框 -->
<div class="modal fade" id="addContengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    新建
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="addForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                选择分组：
                            </label>
                            <div class="layui-input-inline">
                                <input type="hidden" name="groupId" id="add_contentSubjectId"/>
                                <input type="text" readonly class="layui-input wd180" name="groupName"
                                       id="add_contentSubjectName" onclick="showAddTree();">
                                <div id="add_content"  class="tree-float wd180">
                                     <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 460px;padding-left:10px;height:150px">--%>
                                    <ul id="add_tree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                分组标题：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input wd180" name="name" id="addGroupName" maxlength=20>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label wd120">
                            <%--<span class="red">*</span>--%>
                            内容描述：
                        </label>
                        <div class="layui-input-block">
                            <textarea placeholder="请输入内容描述" class="layui-textarea wd400" name="descri" id="addGroupDescri" maxlength=250></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="layui-input-block">
                    <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitAdd">提交</button>
                    <%--<button type="reset" class="btn btn-primary btn-bg1">重置</button>--%>
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 修改内容模态框 -->
<div class="modal fade" id="updateContengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                    <input type="hidden" name="id"/>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                选择分组：
                            </label>
                            <div class="layui-input-inline">
                                <input type="hidden" name="groupId" id="update_contentSubjectId"/>
                                <input type="text" readonly class="layui-input wd180" name="groupName"
                                       id="update_contentSubjectName" onclick="showUpdateTree();">
                                <div id="update_content" class="tree-float wd180">
                                     <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 460px;padding-left:10px;height:150px">--%>
                                    <ul id="update_tree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                分组标题：
                            </label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input wd180" name="name" id="updGroupName" maxlength=20>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label wd120">
                            <span class="red">*</span>
                            内容描述：
                        </label>
                        <div class="layui-input-inline">
                            <textarea placeholder="请输入内容描述" class="layui-textarea wd400" name="descri" maxlength=250></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="layui-input-block">
                    <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitUpdate">提交</button>
                    <%--<button type="reset" class="btn btn-primary btn-bg1">重置</button>--%>
                    <button type="" class="btn btn-default" data-dismiss="modal">返回</button>
                </div>
            </div>
        </div>
    </div>
</div>

<%--删除弹框--%>
<div class="modal fade" id="deleteContengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
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
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitDelete">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<%--修改主题--%>
<div class="modal fade" id="updateSubject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    修改主题
                </h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="updateSubject_contentId"/>
                <input type="hidden" id="updateSubject_subjectId"/>
                <div class="panel-body">
                    <ul id="updateSubject_tree" class="ztree"></ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitUpdateSubject">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="bar-subject">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="update">修改</a>
</script>
<script type="text/javascript" src="${ctx}/resources/js/src/customerGroup/customerGroup.js"></script>
<script>
    customerGroup.init();
</script>
</body>
</html>

