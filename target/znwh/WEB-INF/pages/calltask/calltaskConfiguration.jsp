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
        <title>任务配置</title>
    </head>
    <body class="calltaskbody">
        <%--任务配置主页面--%>
        <div style="padding: 15px;">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2">
                <div class="panel bdr">
                    <div class="panel-heading">
                    </div>
                    <div class="panel-body">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="layui-col-md10" id="right-content" style="display: block;">
                <form class="contform layui-form btm-bdr search-form">
                    <div class="contform-heading layui-form-item">
                        <div class="layui-input-inline wd240">
                            <input type="text" name="name" lay-verify="title" autocomplete="off" placeholder=" 输入任务名称查询" class="layui-input">
                            <button type="button" class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search" lay-filter="search">任务查询</button>
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
                        <div class="layui-btn-container">
                            <button class="btn-add layui-btn fl btn-bg1" type="button">新建</button>
                            <button class="layui-btn fl btn-bg1" data-toggle="modal" data-target="" type="button" id="plan">变计划</button>
                            <button class="layui-btn fl btn-bg1" data-toggle="modal" data-target="" type="button" id="choose">选流程</button>
                            <button class="layui-btn fl btn-bg1" data-toggle="modal" data-target="" type="button" id="startMany">启用</button>
                            <button class="layui-btn fl btn-bg1" data-toggle="modal" data-target="" type="button" id="destroyMany">废弃</button>
                            <button class="layui-btn fl btn-bg1" data-toggle="modal" data-target="" type="button" id="deleteMany">删除</button>
                        </div>
                    </div>
                </form>
                <form class="more-condition layui-form hide search-form-more">
                    <div class="layui-form-item mar-top15">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">任务类型：</label>
                            <div class="layui-input-inline" style="height: 42px;">
                                <input type="checkbox" name="planType1" value="0" title="一次性"  lay-skin="primary">
                                <input type="checkbox" name="planType1" value="1" title="周期性"  lay-skin="primary">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">执行日期：</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="executeDate" id="time-horizon" placeholder="开始时间-结束时间">
                            </div>
                        </div>
                        <%--<div class="layui-inline">--%>
                            <%--<label class="layui-form-label wd120">外呼主题：</label>--%>
                            <%--<div class="layui-input-inline wd120">--%>
                                <%--&lt;%&ndash;<select name="callContentSubject">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<option value="">--请选择--</option>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<c:forEach var="subject" items="${subjectslist}">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<c:if test="${subject.id != 1}">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<option value="${subject.id}">${subject.name}</option>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</select>  value="${searchSubject.id}" &ndash;%&gt;--%>
                                <%--<input type="hidden" name="callContentSubjectId" id="search_contentSubjectId"/>--%>
                                <%--<input type="text" readonly class="layui-input" name="callContentSubject"--%>
                                       <%--placeholder="请选择主题" id="search_contentSubjectName" onclick="showSearchTree();">--%>
                                <%--<div id="search_contentSubject"  class="tree-float wd120">--%>
                                     <%--&lt;%&ndash;style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">&ndash;%&gt;--%>
                                    <%--<ul id="search_contentSubject_tree" class="ztree"></ul>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">状态：</label>
                            <div class="layui-input-block wd535" style="height: 42px;">
                                <input type="checkbox" name="status1" value="0" title="待执行"   lay-skin="primary">
                                <input type="checkbox" name="status1" value="1" title="执行中"  lay-skin="primary">
                                <input type="checkbox" name="status1" value="2" title="执行完毕"  lay-skin="primary">
                                <input type="checkbox" name="status1" value="-1" title="已废弃"  lay-skin="primary">
                            </div>
                        </div>
                    </div>
                </form>
                <a  target="_blank" id="addHref"></a>
                <input type="hidden" id="taskId"/>
                <table class="layui-hide" id="content-configuration" lay-filter="callTaskList"></table>
                <div id="page-configuration"></div>
            </div>
            <%--新建任务页面--%>
            <div class="layui-col-md9" id="add-task" style="display: none;">
                <div class="">
                    <div class="">
                        <div class="modal-header">
                            <h4 class="modal-title col2">
                                新建任务
                            </h4>
                        </div>
                        <div class="add-body">
                            <form class="layui-form mar-top15" id="add-form">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            任务名称：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="name" class="layui-input wd180" lay-verify="name" maxlength="20">
                                            <input type="text" style="display:none;" name="status" value="0" class="layui-input wd180" >
                                            <input type="text" style="display:none;" name="callCategoryId" id="callCategoryId" value="1" class="layui-input wd180" >
                                            <input type="text" style="display:none;" name="callCategoryName" id="callCategoryName" value="" class="layui-input wd180" >
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            是否启用：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="radio" name="type" value="0" title="启用" checked="" class="layui-input-inline">
                                            <input type="radio" name="type" value="1" title="暂不启用" class="layui-input-inline">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <%--<div class="layui-inline pad-right10">--%>
                                        <%--<label class="layui-form-label wd140">--%>
                                            <%--<span class="red">*</span>--%>
                                            <%--营销主题：--%>
                                        <%--</label>--%>
                                        <%--<div class="layui-input-inline wd180">--%>
                                            <%--<input type="hidden" name="callContentSubjectId" id="add_contentSubjectId"/>--%>
                                            <%--<input type="text" readonly class="layui-input" name="callContentSubjectName"--%>
                                                   <%--id="add_contentSubjectName" onclick="showAddTree();" lay-verify="callContentSubject">--%>
                                            <%--<div id="add_contentSubject"--%>
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <%--<ul id="add_contentSubject_tree" class="ztree"></ul>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="layui-inline pad-right10">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            客户组：
                                        </label>
                                        <div class="layui-input-inline wd180">
                                            <%--<input type="text" style="display:none;" name="customerGroupName" id="customerGroupName" value="" class="layui-input wd180" >--%>
                                            <%--<select name="customerGroupId">--%>
                                                <%--<option value="-1">--请选择--</option>--%>
                                                <%--<c:forEach var="customerGroupList" items="${customerGroupList}">--%>
                                                    <%--<c:if test="${customerGroupList.id != 1}">--%>
                                                        <%--<option value="${customerGroupList.id}">${customerGroupList.name}</option>--%>
                                                    <%--</c:if>--%>
                                                    <%--&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>

                                            <input type="hidden" name="customerGroupId" id="add_customerGroupId"/>
                                            <input type="text" unselectable='on' readonly class="layui-input" name="customerGroupName"
                                                   id="add_customerGroupName" onclick="showAddCustomerGroupTree();" lay-verify="customerGroup">
                                            <div id="add_customerGroup" class="tree-float wd180">
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <ul id="add_customerGroup_tree" class="ztree"></ul>
                                            </div>
                                        </div>
                                        <%--<button class="btn btn-primary fl btn2">选择</button>--%>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            计划类型：
                                        </label>
                                        <div class="layui-input-inline" id="qqq">
                                            <input type="radio" lay-filter="planType" name="planType" value="0" title="一次性" checked="" class="layui-input-inline">
                                            <input type="radio" lay-filter="planType" name="planType" value="1" title="周期性" class="layui-input-inline">
                                        </div>
                                    </div>
                                </div>
                                <%--<div class="layui-form-item">--%>
                                    <%--<div class="layui-inline">--%>
                                        <%--<label class="layui-form-label wd140">--%>
                                            <%--<span class="red">*</span>--%>
                                            <%--计划类型：--%>
                                        <%--</label>--%>
                                        <%--<div class="layui-input-inline" id="qqq">--%>
                                            <%--<input type="radio" lay-filter="planType" name="planType" value="0" title="一次性" checked="" class="layui-input-inline">--%>
                                            <%--<input type="radio" lay-filter="planType" name="planType" value="1" title="周期性" class="layui-input-inline">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <div class="layui-form-item" id="add-planType-one">
                                    <div class="layui-inline">
                                        <%--<i class="layui-icon">&#xe637;</i>--%>
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行日期：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" id="Execdate" readonly="readonly" onfocus="this.blur()" class="layui-input wd180" name="executeOneDate" placeholder="" lay-verify="execDate">
                                            <%--<input type="text" style="display: none" class="layui-input wd180" name="executeEndDate" placeholder="">--%>
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行时间：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" class="layui-input wd180" onfocus="this.blur()" readonly id="ExectimeOne" name="executeOneTime" placeholder="" lay-verify="exectime">
                                        </div>
                                        <%--<i class="layui-icon">&#xe637;</i>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item" id="add-planType-two" style="display: none;">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            有效日期：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" id="executeBeginDate" readonly  onfocus="this.blur()" class="layui-input wd90" name="executeBeginDate" placeholder="开始时间" lay-verify="execDate">
                                            <input type="text" class="layui-input wd90" readonly onfocus="this.blur()" id="executeEndDate" name="executeEndDate" placeholder="结束时间" lay-verify="execDate">
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行时间：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" class="layui-input wd180" readonly onfocus="this.blur()" id="ExectimeTwo" name="executeTime" placeholder="" lay-verify="exectime">
                                        </div>
                                        <%--<button class="layui-btn"></button>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline pad-right10">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼流程：
                                        </label>
                                        <%--<div class="layui-input-inline wd180">--%>
                                            <%--<select name="callFlowId">--%>
                                                <%--<option value="">--请选择--</option>--%>
                                                <%--<option value="1">1</option>--%>
                                                <%--<option value="2">2</option>--%>
                                                <%--<option value="3">3</option>--%>
                                            <%--</select>--%>
                                        <%--</div>--%>
                                        <div class="layui-input-inline wd180">
                                            <%--<input type="text" style="display:none;" name="callFlowName" id="callFlowName" class="layui-input wd180" >--%>
                                            <%--<select name="callFlowId">--%>
                                                <%--<option value="-1">--请选择--</option>--%>
                                                <%--<c:forEach var="callFlowlist" items="${callFlowlist}">--%>
                                                    <%--<c:if test="${callFlowlist.id != 1}">--%>
                                                        <%--<option value="${callFlowlist.id}">${callFlowlist.name}</option>--%>
                                                    <%--</c:if>--%>
                                                    <%--&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>

                                            <input type="hidden" name="callFlowId" id="add_callFlowId"/>
                                            <input type="text" readonly unselectable='on' class="layui-input" name="callFlowName"
                                                   id="add_callFlowName" onclick="showAddCallFlowNameTree();" lay-verify="callFlowName">
                                            <div id="add_callFlow" class="tree-float wd180">
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <ul id="add_callFlow_tree" class="ztree"></ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            拨号出局码：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" maxlength="15" name="callcjNumber" value="2" lay-verify="callcjNumber" class="layui-input wd180">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            失败尝试次数：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" maxlength="2" name="tryCount" value="3" lay-verify="num" class="layui-input wd180">
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼基数：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" name="basicNum" maxlength="2" value="3" lay-verify="num" class="layui-input wd180">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼显示号码：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="radio" name="callNumShowType" value="0" title="随机" checked="" class="layui-input-inline">
                                            <input type="radio" name="callNumShowType" value="1" title="统一号码" class="layui-input-inline">
                                        </div>
                                        <div class="layui-inline">
                                            <div class="layui-input-inline">
                                                <input type="text" name="callNumShow" value="18888888888" lay-verify="phone" class="layui-input wd180">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">任务描述：</label>
                                        <div class="layui-input-inline wd535">
                                            <textarea name="descri"  maxlength="200" lay-verify="descri" placeholder="请输入内容" class="layui-textarea"></textarea>
                                        </div>
                                    </div>

                                </div>
                                <div class="add-footer">
                                    <label class="layui-form-label wd140"></label>
                                    <button type="button" lay-submit class="btn btn-primary btn-bg1 bdr" id="add-commit" lay-filter="add">
                                        提交
                                    </button>
                                    <button type="reset" class="btn btn-primary btn-bg1 bdr">
                                        重置
                                    </button>
                                    <button  type="reset" class="btn btn-default close-div" data-dismiss="modal">
                                        返回
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <%--修改任务页面--%>
            <div id="edit-task" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 800px;">
                    <div class="modal-content">
                        <div class="modal-header bg1">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="">
                                修改任务
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="layui-form mar-top15" id="up-form">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            任务名称：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" style="display:none;" name="id" class="layui-input wd180" required>
                                            <input type="text" id="upname" name="name" lay-verify="name"  maxlength="20" class="layui-input wd180" required>
                                            <input type="text" style="display:none;" name="status" id="upstatus" value="0" class="layui-input wd180" >
                                            <input type="text" style="display:none;" name="callCategoryId" id="upcallCategoryId" value="1" class="layui-input wd180" >
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            是否启用：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="radio" name="type" id="uptype" value="0" title="启用" checked=""  class="layui-input-inline">
                                            <input type="radio" name="type" id="uptype" value="1" title="暂不启用" class="layui-input-inline">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <%--<div class="layui-inline pad-right10">--%>
                                        <%--<label class="layui-form-label wd140">--%>
                                            <%--<span class="red">*</span>--%>
                                            <%--营销主题：--%>
                                        <%--</label>--%>
                                        <%--<div class="layui-input-inline wd180">--%>
                                            <%--&lt;%&ndash;<select name="callContentSubjectId" id="upsubject">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<option value="-1">--请选择--</option>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<c:forEach var="subject" items="${subjectslist}">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<c:if test="${subject.id != 1}">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<option value="${subject.id}">${subject.name}</option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</select>&ndash;%&gt;--%>

                                            <%--<input type="hidden" name="callContentSubjectId" id="up_customerGroupId"/>--%>
                                            <%--<input type="text" readonly class="layui-input" name="callContentSubjectName"--%>
                                                   <%--id="up_callContentSubjectName"  lay-verify="callContentSubject" onclick="showUpdateTree();">--%>
                                            <%--<div id="up_callContentSubject"--%>
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <%--<ul id="up_callContentSubject_tree" class="ztree"></ul>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="layui-inline pad-right10">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            客户组：
                                        </label>
                                        <%--<div class="layui-input-inline">--%>
                                            <%--<input type="text" name="customerGroupId" class="layui-input wd180">--%>
                                        <%--</div>--%>

                                        <div class="layui-input-inline wd180">
                                            <%--<select name="customerGroupId">--%>
                                                <%--<option value="-1">--请选择--</option>--%>
                                                <%--<c:forEach var="customerGroupList" items="${customerGroupList}">--%>
                                                    <%--<c:if test="${customerGroupList.id != 1}">--%>
                                                        <%--<option value="${customerGroupList.id}">${customerGroupList.name}</option>--%>
                                                    <%--</c:if>--%>
                                                    <%--&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>

                                            <input type="hidden" name="customerGroupId" id="up_customerGroupId"/>
                                            <input type="text" readonly class="layui-input" name="customerGroupName"
                                                   id="up_customerGroupName" lay-verify="customerGroup" onclick="showUpdateCustomerGroupTree();">
                                            <div id="up_customerGroup" class="tree-float wd180">
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <ul id="up_customerGroup_tree" class="ztree"></ul>
                                            </div>
                                        </div>
                                        <%--<button class="btn btn-primary fl btn2">选择</button>--%>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            计划类型：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="radio" lay-filter="uplanType" id="upplanType" name="planType" value="0" title="一次性" checked="" class="layui-input-inline">
                                            <input type="radio" lay-filter="uplanType" id="upplanType" name="planType" value="1" title="周期性" class="layui-input-inline">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item" id="up-planType-one">
                                    <div class="layui-inline">
                                        <%--<i class="layui-icon">&#xe637;</i>--%>
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行日期：
                                        </label>
                                        <div class="layui-input-inline">

                                            <input type="text" id="upExecdate"  lay-verify="exectime" readonly class="layui-input wd180" name="executeOneDate" placeholder="">
                                            <%--<input type="text" style="display: none" class="layui-input wd180" name="executeEndDate" placeholder="">--%>
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行时间：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" class="layui-input wd180" lay-verify="exectime" readonly id="upExectimeOne" name="executeOneTime" placeholder="">
                                        </div>
                                        <%--<i class="layui-icon">&#xe637;</i>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item" id="up-planType-two" style="display: none;">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            有效日期：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" id="upexecuteBeginDate" lay-verify="exectime" readonly class="layui-input wd90" name="executeBeginDate" placeholder="开始时间">
                                            <input type="text" class="layui-input wd90" lay-verify="exectime" readonly id="upexecuteEndDate" name="executeEndDate" placeholder="结束时间 ">
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行时间：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" class="layui-input wd180" lay-verify="exectime" readonly id="upExectimeTwo" name="executeTime" placeholder="">
                                        </div>
                                        <%--<button class="layui-btn"></button>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline pad-right10">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼流程：
                                        </label>
                                        <div class="layui-input-inline wd180">
                                            <%--<select name="callFlowId">--%>
                                                <%--<option value="-1">--请选择--</option>--%>
                                                <%--<c:forEach var="callFlowlist" items="${callFlowlist}">--%>
                                                    <%--<c:if test="${callFlowlist.id != 1}">--%>
                                                        <%--<option value="${callFlowlist.id}">${callFlowlist.name}</option>--%>
                                                    <%--</c:if>--%>
                                                    <%--&lt;%&ndash;<option value="2">2</option>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<option value="3">3</option>&ndash;%&gt;--%>
                                                <%--</c:forEach>--%>
                                            <%--</select>--%>

                                            <input type="hidden" name="callFlowId" id="up_callFlowId"/>
                                            <input type="text" readonly class="layui-input" name="callFlowName"
                                                   id="up_callFlowName"  lay-verify="callFlowName" onclick="showUpdateCallFlowGroupTree();">
                                            <div id="up_callFlow" class="tree-float wd180">
                                                 <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                                <ul id="up_callFlow_tree" class="ztree"></ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            拨号出局码：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text"  maxlength="15"  lay-verify="callcjNumber" name="callcjNumber" class="layui-input wd180">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            失败尝试次数：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text" maxlength="2" name="tryCount"  lay-verify="num" class="layui-input wd180">
                                        </div>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼基数：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="text"  maxlength="2" name="basicNum"  lay-verify="num" class="layui-input wd180">
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼显示号码：
                                        </label>
                                        <div class="layui-input-inline">
                                            <input type="radio" id="upcallNumShowType" name="callNumShowType" value="0" title="随机" checked="" class="layui-input-inline">
                                            <input type="radio" id="upcallNumShowType" name="callNumShowType" value="1" title="统一号码" class="layui-input-inline">
                                        </div>
                                        <div class="layui-inline">
                                            <div class="layui-input-inline">
                                                <input type="text" name="callNumShow"  lay-verify="phone" class="layui-input wd180">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">任务描述：</label>
                                        <div class="layui-input-inline wd535">
                                            <textarea name="descri"  maxlength="200" lay-verify="descri" placeholder="请输入内容" class="layui-textarea"></textarea>
                                        </div>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <label class="layui-form-label wd140"></label>
                                    <button type="button" lay-submit class="btn btn-primary btn-bg1 bdr" id="up-commit" lay-filter="update">
                                        提交
                                    </button>
                                    <button type="button" class="btn btn-primary btn-bg1 bdr" id="resetUpdate">
                                        重置
                                    </button>
                                    <button type="reset" class="btn btn-default close-div" data-dismiss="modal">
                                        返回
                                    </button>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <%--任务详情页面--%>
            <div id="detail-task" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 850px;">
                        <div class="modal-header bg1">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="">
                                任务详情
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="layui-form" id="detail-form">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            任务名称：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailname" readonly="readonly">
                                            <input type="hidden" class="layui-input" name="detailtaskId" id="detailtaskId" readonly="readonly">
                                        </div>

                                        <%--<span class="detailtask" name="detailname"></span>--%>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            状态：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailstatus" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailstatus"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <%--<div class="layui-inline">--%>
                                        <%--<label class="layui-form-label wd140">--%>
                                            <%--<span class="red">*</span>--%>
                                            <%--营销主题：--%>
                                        <%--</label>--%>
                                        <%--<div class="layui-inline">--%>
                                            <%--<input type="text" class="layui-input" name="detailsubject" readonly="readonly">--%>
                                        <%--</div>--%>
                                        <%--&lt;%&ndash;<span class="detailtask" name="detailsubject"></span>&ndash;%&gt;--%>
                                    <%--</div>--%>
                                    <div class="layui-inline wd45">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            客户组：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailcustomer" readonly="readonly">
                                        </div>
                                        <%--<div class="layui-input-inline">--%>
                                            <%--<input type="text" name="upcustomerGroupId" class="layui-input wd180" value="">--%>
                                        <%--</div>--%>

                                        <%--<span class="detailtask" name="detailcustomer"></span>--%>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            任务分类：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailcallcategory" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailcallcategory"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            计划类型：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailplanetype" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailplanetype"></span>--%>
                                    </div>
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            执行时间：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailexecutime" readonly="readonly">
                                        </div>
                                        <%--<span name="detailexecutime"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline wd45">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼流程：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailflow" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailflow"></span>--%>
                                    </div>
                                    <div class="layui-inline wd45">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            拨号出局码：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailcjnum" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailcjnum"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline wd45">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            失败尝试次数：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" class="layui-input" name="detailtrycount" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailtrycount"></span>--%>
                                    </div>
                                    <div class="layui-inline wd45">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼基数：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text"  maxlength="2" class="layui-input" name="detailbasicnum" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailbasicnum"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <label class="layui-form-label wd140">
                                            <span class="red">*</span>
                                            外呼显示号码：
                                        </label>
                                        <div class="layui-inline">
                                            <input type="text" maxlength="20" class="layui-input" name="detailshownum" readonly="readonly">
                                        </div>
                                        <%--<span class="detailtask" name="detailshownum"></span>--%>
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <label class="layui-form-label wd140">任务描述：</label>
                                    <div class="layui-input-inline wd535">
                                        <textarea type="text" name="detaildescri" readonly="readonly" class="layui-textarea"></textarea>
                                    </div>
                                    <%--<div class="layui-inline">--%>
                                        <%--<input type="text" class="layui-input" name="detaildescri" readonly="readonly">--%>
                                    <%--</div>--%>
                                    <%--<span class="detailtask" name="detaildescri"></span>--%>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary btn-bg1 bdr" id="watchCallRecord">查看外呼记录</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                        </div>
                    </div>
                </div>
            </div>

    <%--变计划弹窗--%>
    <div id="planchange" class="modal fade" style="top: -39px;"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg1">
                    <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title col1">
                        变计划
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="layui-form" id="modify-form">
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-inline">--%>
                                <%--<label class="layui-form-label wd120">--%>
                                    <%--<span class="red">*</span>--%>
                                    <%--计划类型：--%>
                                <%--</label>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="radio" name="tasktype" value="一次性" title="一次性" checked="" class="layui-input-inline">--%>
                                    <%--<input type="radio" name="tasktype" value="周期性" title="周期性" class="layui-input-inline">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-inline">--%>
                                <%--<label class="layui-form-label wd120">有效日期：</label>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" class="layui-input" placeholder=" 开始时间-结束时间 ">--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="layui-form-item">--%>
                            <%--<div class="layui-inline">--%>
                                <%--<label class="layui-form-label wd120">--%>
                                    <%--<span class="red">*</span>--%>
                                    <%--执行时间：--%>
                                <%--</label>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" class="layui-input">--%>
                                <%--</div>--%>
                                <%--<button class="btn btn-primary fl btn-bg1 btn"></button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label class="layui-form-label wd140">
                                        <span class="red">*</span>
                                        计划类型：
                                    </label>
                                    <div class="layui-input-inline">
                                        <input type="radio" lay-filter="modifylanType" id="modifyplanType" name="planType" value="0" title="一次性" checked="" class="layui-input-inline">
                                        <input type="radio" lay-filter="modifylanType" id="modifyplanType" name="planType" value="1" title="周期性" class="layui-input-inline" >
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item" id="modify-planType-one">
                                <div class="layui-inline">
                                    <%--<i class="layui-icon">&#xe637;</i>--%>
                                    <label class="layui-form-label wd140">
                                        <span class="red">*</span>
                                        执行日期：
                                    </label>
                                    <div class="layui-input-inline">

                                        <input type="text" id="modifyExecdate"  readonly class="layui-input wd180" name="executeOneDate" placeholder="" lay-verify="execDate">
                                        <%--<input type="text" style="display: none" class="layui-input wd180" name="executeEndDate" placeholder="">--%>
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label wd140">
                                        <span class="red">*</span>
                                        执行时间：
                                    </label>
                                    <div class="layui-input-inline">
                                        <input type="text" class="layui-input wd180" readonly id="modifyExectimeOne" name="executeOneTime" placeholder="" lay-verify="exectime">
                                    </div>
                                    <%--<i class="layui-icon">&#xe637;</i>--%>
                                </div>
                            </div>
                            <div class="layui-form-item" id="modify-planType-two" style="display: none;">
                                <div class="layui-inline">
                                    <label class="layui-form-label wd140">
                                        <span class="red">*</span>
                                        有效日期：
                                    </label>
                                    <div class="layui-input-inline"  style="display: flex;">
                                        <input type="text" id="modifyexecuteBeginDate" readonly class="layui-input wd90" name="executeBeginDate" placeholder="开始时间" lay-verify="execDate">
                                        <input type="text" class="layui-input wd90" readonly id="modifyexecuteEndDate" name="executeEndDate" placeholder="结束时间 " lay-verify="execDate">
                                    </div>
                                </div>
                                <div class="layui-inline">
                                    <label class="layui-form-label wd140">
                                        <span class="red">*</span>
                                        执行时间：
                                    </label>
                                    <div class="layui-input-inline">
                                        <input type="text" class="layui-input wd180" readonly id="modifyExectimeTwo" name="executeTime" placeholder="" lay-verify="exectime">
                                    </div>
                                    <%--<button class="layui-btn"></button>--%>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="modifySubmit" lay-submit lay-filter="modify" class="btn btn-primary btn-bg1 bdr">
                                    提交
                                </button>
                                <button type="reset" class="btn btn-default" data-dismiss="modal">取消
                                </button>
                            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

        <%--改流程弹窗--%>
    <div id="fiowoption" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg1">
                    <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title col1">
                        改流程
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="layui-form">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label wd120">
                                    <span class="red">*</span>
                                    外呼流程：
                                </label>
                                <div class="layui-input-inline">
                                    <input type="hidden" name="callFlowId" id="modify_callFlowId"/>
                                    <input type="text" readonly class="layui-input wd180" name="callFlowName"
                                           id="modify_callFlowName" onclick="showmodifyCallFlowGroupTree();" lay-verify="callFlowName">
                                    <div id="modify_callFlow"  class="tree-float wd180">
                                         <%--style="display:none; position: absolute;overflow-y :auto;overflow-x :auto; z-index:999;background-color:ghostwhite; width: 360px;padding-left:10px;height:150px">--%>
                                        <ul id="modify_callFlow_tree" class="ztree"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="layui-input-block">
                                <button type="button" lay-filter="choose"   lay-submit id="chooseSubmit" class="btn btn-primary btn-bg1 bdr">提交</button>
                                <button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


        <%--废弃弹框--%>
        <div class="modal fade" id="ban-taskcontengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg1">
                        <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title col1">
                            废弃
                        </h4>
                    </div>
                    <div class="modal-body">
                        确认废弃？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitDestroy">
                            确认
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <%--启用弹框--%>
        <div class="modal fade" id="start-taskcontengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg1">
                        <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title col1">
                            启用
                        </h4>
                    </div>
                    <div class="modal-body">
                        确认启用？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitStart">
                            确认
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <%--删除弹框--%>
        <div class="modal fade" id="del-taskcontengt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg1">
                        <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title col1" id="">
                            删除
                        </h4>
                    </div>
                    <div class="modal-body">
                        确认删除？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitDelete">
                            确认
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消
                        </button>
                    </div>
                </div>
            </div>
        </div>
            <script type="text/javascript" src="../../resources/js/src/callTask/taskconfiguration.js"></script>
        <script type="text/html" id="bar-configuration">
            {{# if(d.status == 1){ }}
            <a class="layui-btn layui-btn-xs" data-toggle="modal" data-target="" type="button" lay-event="detail" >详情</a>
            {{# } else { }}
            <a class="layui-btn layui-btn-primary layui-btn-xs" data-toggle="modal" data-target="" lay-event="del" type="button">删除</a>
            <a class="layui-btn layui-btn-xs" data-toggle="modal" data-target="" lay-event="edit" type="button">修改</a>
            <a class="layui-btn layui-btn-xs" data-toggle="modal" data-target="" type="button" lay-event="detail" >详情</a>
            {{#  if(d.type==0){ }}
            <a class="layui-btn layui-btn-primary layui-btn-xs" data-toggle="modal" data-target="" lay-event="destroyIt" content="1" type="button">废弃</a>
            {{#  } else{}}
            <a class="layui-btn layui-btn-primary layui-btn-xs" data-toggle="modal" data-target="" lay-event="destroyIt" content="0" type="button">启用</a>
            {{#  } }}
            {{#  } }}
        </script>
        <script>
            taskconfiguration.init();
        </script>
    </body>
</html>
<%--<script type="text/html" id="category">--%>
    <%--{{d.callCategoryId}}--%>
<%--</script>--%>
<%--<script type="text/html" id="contentSubject">--%>
    <%--{{d.callContentSubjectId}}--%>
<%--</script>--%>
<%--<script type="text/html" id="callFlow">--%>
    <%--<a>{{d.callFlowId}}</a>--%>
<%--</script>--%>
<script type="text/html" id="planType">
    {{#  if(d.planType=='0'){ }}
    一次性
    {{#  } else{}}
    周期性
    {{#  } }}
</script>
<%--<script type="text/html" id="right">--%>
    <%----%>
<%--</script>--%>

<script type="text/html" id="status">
    {{# if(d.type == 0){ }}
    {{#  if(d.status=='0'){ }}
    待执行
    {{# }else if(d.status=='1'){ }}
    执行中
    {{#  } else{}}
    执行完毕
    {{#  } }}
    {{# }else if(d.type == 1){ }}
    已废弃
    {{# } }}
</script>
<script type="text/html" id="period">
    {{#  if(d.planType=='0'){ }}
    {{#
    var executeBeginDate = d.executeBeginDate;
    var date = new Date();
    date.setTime(d.executeTime);
    return executeBeginDate+' '+date.Format("hh:mm:ss");
    }}
    {{#  } else{}}
    {{#
    var executeBeginDate = d.executeBeginDate;
    var executeEndDate = d.executeEndDate;
    var date = new Date();
    date.setTime(d.executeTime);
    return '('+executeBeginDate+'至'+executeEndDate+') '+date.Format("hh:mm:ss");
    }}
    {{#  } }}
</script>




