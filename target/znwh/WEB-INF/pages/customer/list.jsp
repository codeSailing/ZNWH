<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<%@include file="../common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>客户管理</title>
</head>
<body>
<!-- 内容主体区域 -->
<div style="padding: 15px;">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md2">
            <div class="panel bdr">
                <div class="panel-heading">
                    <a href="/customerGroup/showCustomerGroup.do" class="layui-btn btn-bg1">管理分组</a>
                </div>
                <div class="panel-body">
                    <ul id="allTree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="layui-col-md10">
            <form class="layui-form btm-bdr search-form">
                <div class="layui-form-item">
                    <div class="layui-input-inline wd240">
                        <input type="text" name="searchParam" lay-verify="title" autocomplete="off" placeholder="输入客户姓名或手机号码查询" class="bdr-right0 layui-input">
                        <button class="layui-btn fl wd100 bdr-rad0 search-btn btn-bg1 btn-search" type="button">客户查询</button>
                    </div>
                    <div class="more mar-left120">
                        <%--<span></span>--%>
                        <a href="javascript:void(0);" class="more-btn fl mar-right20">
                            更多筛选条件
                        </a>
                        <button type="button" class="layui-btn fl btn-bg1" data-toggle="modal" id="addCustomerModleS">新建</button>
                        <button type="button" class="layui-btn fl btn-bg1" data-toggle="modal" id="groupingModle">分组</button>
                        <button type="button" class="layui-btn fl btn-bg1" data-toggle="modal" id="importCustomerModleS">导入</button>
                        <button type="button" class="layui-btn fl btn-bg1" data-toggle="modal" id="delCustomerS">删除</button>
                    </div>
                </div>
            </form>
            <form class="layui-form hide more-condition" id="moreQueryForm">
                <div class="layui-form-item mar-top15">
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">年龄：</label>
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" name="ageSmall" placeholder="" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid">～</div>
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" name="ageBig" placeholder="" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">地区：</label>
                        <div class="layui-input-inline">
                            <ul id="list3">
                                <li id="summary-stock3" class="clearfix">
                                    <div class="dd">
                                        <div id="store-selector3">
                                            <input class="form-control" name="area" id="areaName7" autocomplete="off" placeholder="--请选择--" style="overflow: hidden;white-space: normal;text-overflow: ellipsis;">
                                            <div class="text"></div>
                                        </div>
                                        <!--store-selector end-->
                                        <div id="store-prompt3"><strong></strong></div>
                                        <!--store-prompt end-->
                                    </div>
                                </li>
                            </ul>
                            <input name="areaCode" id="areaCode3" type="hidden" class="form-control">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label wd120">性别：</label>
                        <div class="layui-input-inline">
                            <input type="radio" name="sex" value="0" title="男" checked="">
                            <input type="radio" name="sex" value="1" title="女">
                        </div>
                    </div>
                    <%--<div class="layui-inline">
                        <label class="layui-form-label wd120">购买产品</label>
                        <div class="layui-input-inline">
                            <select name="modules" lay-verify="required" lay-search="">
                                <option value="">直接选择或搜索选择</option>
                                <option value="1">layer</option>
                                <option value="2">form</option>
                                <option value="3">layim</option>
                                <option value="4">element</option>
                                <option value="5">laytpl</option>
                                <option value="6">upload</option>
                                <option value="7">laydate</option>
                                <option value="8">laypage</option>
                                <option value="9">flow</option>
                                <option value="10">util</option>
                                <option value="11">code</option>
                                <option value="12">tree</option>
                                <option value="13">layedit</option>
                                <option value="14">nav</option>
                                <option value="15">tab</option>
                                <option value="16">table</option>
                                <option value="17">select</option>
                                <option value="18">checkbox</option>
                                <option value="19">switch</option>
                                <option value="20">radio</option>
                            </select>
                        </div>
                    </div>--%>
                </div>
            </form>
            <table class="layui-hide" id="demo" lay-filter="contentList"></table>
        </div>
    </div>
</div>
<%--分组弹框--%>
<div class="modal fade" id="grouping" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1" id="myModalLabel">
                    分组
                </h4>
            </div>
            <div class="modal-body">
                <input name="customerId" id="customerId" type="hidden">
                <input name="customerGroupId" id="cusGroupId" type="hidden">
                <ul id="groupTree" class="ztree"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="groupCommit">
                    确认
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
            </div>
        </div>
    </div>
</div>
<%--&lt;%&ndash;删除弹框&ndash;%&gt;--%>
<%--<div class="modal fade" id="delCustomerModle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
    <%--<div class="modal-dialog">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header bg1">--%>
                <%--<button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">--%>
                    <%--&times;--%>
                <%--</button>--%>
                <%--<h4 class="modal-title col1">--%>
                    <%--删除--%>
                <%--</h4>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--删除客户会将客户已有的外呼记录一并删除，您确定要删除吗？--%>
            <%--</div>--%>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-primary btn-bg1 bdr" id="commitDelCustomer">--%>
                    <%--确认--%>
                <%--</button>--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">--%>
                    <%--取消--%>
                <%--</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<%--导入弹窗--%>
<div class="modal fade" id="importCustomerModle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    导入
                </h4>
            </div>

            <div class="modal-body">
                <form class="layui-form">
                    <%--<div class="layui-form-item">--%>
                        <div class="layui-inline">
                            <label class="layui-form-label">
                                <%--<span class="red">*</span>--%>
                                分组：
                            </label>
                            <div class="layui-input-inline">
                                <input type="tel" id="importCustomerGroupIdName" class="layui-input wd180" lay-verify="required|phone" autocomplete="off" onclick="importAddTree();" placeholder="请选择分组(非必选)" readonly="readonly" class="layui-input">
                                <input id="importCustomerGroupId" type="hidden">
                                <div class="tree-float wd180" id="importAddTreeDiv" >
                                    <ul id="importAddTree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                    <%--</div>--%>
                    <div class="layui-inline">
                        <%--<div class="layui-inline">
                            <div class="layui-input-inline">--%>
                            <label class="layui-form-label">
                                <input type="file" id="filename">
                            </label>
                            <%--</div>
                        </div>--%>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="importCustomer">
                    导入
                </button>
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="downloadTemplate">
                    下载模板
                </button>
            </div>
        </div>
    </div>
</div>
<%--新增弹框--%>
<div class="modal fade" id="addCustomerModle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 640px;">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    新建
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="addCustomer">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                姓名：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="name" lay-verify="required|phone" autocomplete="off" class="layui-input" maxlength=10>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                分组：
                            </label>
                            <div class="layui-input-inline wd180">
                                <input type="tel" id="customerGroupIdName"  lay-verify="required|phone" autocomplete="off" onclick="showAddTree();" readonly="readonly" class="layui-input">
                                <input name="customerGroupId" id="customerGroupId" type="hidden">
                                <div class="tree-float wd180" id="addTreeDiv" >
                                    <ul id="addTree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                手机号码：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="telephone" lay-verify="required|phone" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                性别：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="radio" name="sex" value="0" title="男" checked="">
                                <input type="radio" name="sex" value="1" title="女">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                年龄：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="age" lay-verify="required|phone" autocomplete="off" class="layui-input" maxlength=3>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                地区：
                            </label>
                            <div class="layui-input-inline wd180">
                                <%--<input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">--%>
                                <ul id="list1">
                                    <li id="summary-stock" class="clearfix">
                                        <div class="dd">
                                            <div id="store-selector">
                                                <input class="form-control" name="areaName" id="areaName" autocomplete="off" style="overflow: hidden;white-space: normal;text-overflow: ellipsis;">
                                                <div class="text"></div>
                                            </div>
                                            <!--store-selector end-->
                                            <div id="store-prompt"><strong></strong></div>
                                            <!--store-prompt end-->
                                        </div>
                                    </li>
                                </ul>
                                <input name="areaCode" id="areaCode" type="hidden" class=" form-control">
                                <input name="id"  type="hidden">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                描述：
                            </label>
                            <div class="layui-input-block">
                                <textarea placeholder="请输入内容描述" class="layui-textarea textarea465" name="descri" maxlength=250 onkeyup="this.value=this.value.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g,'')"></textarea>
                            </div>
                        </div>
                        <%--<div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                购买产品
                            </label>
                            <div class="layui-input-inline wd140">
                                <select name="modules" lay-verify="required" lay-search="">
                                    <option value="">直接选择或搜索选择</option>
                                    <option value="1">layer</option>
                                    <option value="2">form</option>
                                    <option value="3">layim</option>
                                    <option value="4">element</option>
                                    <option value="5">laytpl</option>
                                    <option value="6">upload</option>
                                    <option value="7">laydate</option>
                                    <option value="8">laypage</option>
                                    <option value="9">flow</option>
                                    <option value="10">util</option>
                                    <option value="11">code</option>
                                    <option value="12">tree</option>
                                    <option value="13">layedit</option>
                                    <option value="14">nav</option>
                                    <option value="15">tab</option>
                                    <option value="16">table</option>
                                    <option value="17">select</option>
                                    <option value="18">checkbox</option>
                                    <option value="19">switch</option>
                                    <option value="20">radio</option>
                                </select>
                            </div>
                        </div>--%>
                        <%--<div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                购买日期
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="text" class="layui-input" id="date" placeholder="yyyy-MM-dd">
                            </div>
                        </div>--%>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="commitAddCustomer">
                    保存
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭
                </button>
            </div>
        </div>
    </div>
</div>

<%--修改弹框--%>
<div class="modal fade" id="updCustomerModle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 640px;">
            <div class="modal-header bg1">
                <button type="button" class="close col1" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title col1">
                    修改
                </h4>
            </div>
            <div class="modal-body">
                <form class="layui-form" id="updCustomer">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                姓名：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="name" lay-verify="required|phone" autocomplete="off" class="layui-input" maxlength=10>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                分组：
                            </label>
                            <div class="layui-input-inline wd180">
                                <input type="tel" id="updCustomerGroupIdName"  lay-verify="required|phone" autocomplete="off" onclick="showUpdateTree();" class="layui-input">
                                <input name="customerGroupId" id="updCustomerGroupId" type="hidden">
                                <div class="tree-float wd180" id="updTreeDiv">
                                    <ul id="updTree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <span class="red">*</span>
                                手机号码：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="telephone" lay-verify="required|phone" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                性别：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="radio" name="sex" id="uptSex" value="0" title="男" checked="">
                                <input type="radio" name="sex" id="uptSex" value="1" title="女">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                年龄：
                            </label>
                            <div class="layui-input-inline wd140">
                                <input type="tel" name="age" lay-verify="required|phone" autocomplete="off" class="layui-input" maxlength=3>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                地区：
                            </label>
                            <div class="layui-input-inline wd180">
                                <%--<input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">--%>
                                <ul id="list1-s">
                                    <li id="summary-stock-s" class="clearfix">
                                        <div class="dd">
                                            <div  class="store-selector-s" id="store-selector-s">
                                                <input class="form-control layui-input" name="areaName" id="areaName-s"
                                                       autocomplete="off" style="margin-bottom: -26px" placeholder="选择区域">
                                                </input>
                                                <div class="text"></div>
                                            </div>
                                            <!--store-selector end-->
                                            <div id="store-prompt-s"><strong></strong></div>
                                            <!--store-prompt end-->
                                        </div>
                                    </li>
                                </ul>
                                <input type="hidden" class="form-control m-bot15" placeholder="选择区域" name="areaCode" id="areaCode-s">
                                <input name="id"  type="hidden">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label wd120">
                                <%--<span class="red">*</span>--%>
                                描述：
                            </label>
                            <div class=" layui-input-inline">
                                <textarea placeholder="请输入内容描述" class="textarea465 layui-textarea" name="descri" maxlength=250></textarea>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-bg1 bdr" id="updCommitAddCustomer">
                    提交
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/resources/js/src/customer/customer.js"></script>
<script>
    customer.init();
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="update">修改</a>
    <a class="layui-btn layui-btn-xs" lay-event="updateGroup">分组</a>
</script>
</body>
</html>