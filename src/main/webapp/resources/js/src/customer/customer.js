var customer = function ($, layui) {
    function init() {
        //更多筛选条件
        $('.more-btn').click(function () {
            if ($('.more-condition').css('display') == "none") {
                $(this).addClass('down');
                $('.more-condition').addClass('show');
            } else if ($('.more-condition').hasClass('show')) {
                $(this).removeClass('down');
                $('.more-condition').removeClass('show');
            }
        });
        // var addTree;
        // var addTree;
        // var updTree;
        // var groupTree;
        //showTree();
        var contentSubjectId_search;
        var ajaxParams = {};
        var regPhone = /^1([3578]\d|4[57])\d{8}$/;//验证手机号格式
        var patrn = /^[0-9]*$/;//年龄
        // 新增确认
        //layui调用模块
        var table;
        var form;
        layui.use(['table', 'form', 'element', 'laydate'], function () {
            table = layui.table;
            form = layui.form;
            var laydate = layui.laydate;
            var element = layui.element;
            laydate.render({
                elem: '#date'
            });
            //展示已知数据
            table.render({
                elem: '#demo'
                , url: context + "/customer/pageCustomer.do" //数据接口
                , page: true //是否显示分页
                , skin: 'row'//表格风格
                , even: true //开启隔行背景
                , id: 'customer' //初始化标识id
                , cols: [[ //标题栏
                    // {type:'checkbox'}
                    {checkbox: true, field: 'id', title: '全选'}
                    /*,{field: 'id', title: 'ID', width: 80}*/
                    , {field: 'name', title: '姓名', align: 'center', width: 120}
                    , {field: 'telephone', title: '手机号', align: 'center', minWidth: 150}
                    , {field: 'groupName', title: '分组', align: 'center', minWidth: 150}
                    , {field: 'sex', title: '性别', align: 'center', width: 60}
                    , {field: 'age', title: '年龄', align: 'center', width: 60}
                    , {field: 'areaName', title: '地区', align: 'center', width: 150}
                    , {field: 'descri', title: '描述', align: 'center', width: 100},
                    {
                        field: 'addTime', title: '添加时间', align: 'center', width: 150,
                        templet: '<div>{{ layui.laytpl.toDateString(d.addTime,"yyyy-MM-dd HH:mm:ss") }}</div>'
                    },
                    {title: '操作', width: 178, align: 'center', toolbar: '#barDemo'}
                ]]
                , done: function (res, page, count) {
                    //如果是异步请求数据方式，res即为你接口返回的信息。
                    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度

                    //分类显示中文名称
                    $("[data-field='sex']").children().each(function () {
                        if ($(this).text() == '0') {
                            $(this).text("男")
                        } else if ($(this).text() == '1') {
                            $(this).text("女")
                        }
                    })

                    console.log(res);

                    //得到当前页码
                    console.log(page);
                    //console.log(limit);
                    //得到数据总量
                    console.log(count);
                }
                //,skin: 'line' //表格风格
//            ,even: true
//            ,page: true //是否显示分页
//            ,limits: [5, 7, 10]
//            ,limit: 5 //每页默认显示的数量
            });

            /************************监听每行按钮事件****************************/
            table.on('tool(contentList)', function (obj) {
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent == "delete") { //删除
                    layer.confirm("删除客户会将客户已有的外呼记录一并删除，您确定要删除吗？", {
                        icon: 3,
                        title: "删除",
                        btn: ["确定", "取消"],
                        btn1: function () {
                            $.ajax({
                                type: "POST",
                                url: context + "/customer/delCustomer.do",
                                data: {"customerId": data.id},
                                success: function (data) {
                                    if (data.status == 0) {
                                        layer.msg(data.message, {time: 2000, icon: 1});
                                        reloadTable();
                                    } else {
                                        layer.msg(data.message, {time: 2000, icon: 5});
                                    }
                                },
                                error: function (data) {
                                    layer.msg("删除失败", {time: 2000, icon: 5});
                                }
                            });
                        }
                    });
                } else if (layEvent == "update") { //修改
                    $.ajax({
                        type: "GET",
                        url: context + "/customer/detailedCustomer.do",
                        data: {"id": data.id},
                        success: function (data) {
                            if (data.status == 0) {
                                //已经选择的用户组
                                var groupIdS = data.data.customerBetweenGroupList;
                                if (groupIdS.length > 0) {
                                    var ids = "";
                                    for (var i in groupIdS) {
                                        ids += groupIdS[i].customerGroupId + ",";
                                    }
                                    $("#updCustomerModle").find("[name='customerGroupId']").val(ids);
                                } else {
                                    $("#updCustomerModle").find("[name='customerGroupId']").val('');
                                }
                                $("#updCustomerModle").find("[name='id']").val(data.data.customer.id);
                                $("#updCustomerModle").find("[name='name']").val(data.data.customer.name);
                                $("#updCustomerModle").find("[name='age']").val(data.data.customer.age);
                                $('[id=uptSex]').each(function (i, item) {
                                    if ($(item).val() == data.data.customer.sex) {
                                        $(item).prop('checked', true);
                                        form.render();
                                    }
                                });
                                //$("#updCustomerModle").find("[name='sex']").val(data.data.customer.sex);
                                //$("#updCustomerModle").find("[name='sex'][value='0']").prop("checked", "checked");
                                //$("input[name='sex'][@type=radio][value='"+data.data.customer.sex+"']").attr("checked",true);

                                //$("#updCustomerModle").find("[name='sex'][value='" + data.data.customer.sex + "']").prop("checked", "checked");
                                $("#updCustomerModle").find("[name='telephone']").val(data.data.customer.telephone);
                                $("#updCustomerModle").find("[name='areaCode']").val(data.data.customer.areaCode);
                                $("#updCustomerModle").find("[name='areaName']").val(data.data.customer.areaName);
                                $("#updCustomerModle").find("[name='descri']").val(data.data.customer.descri);
                                $("#updCustomerModle").find("#updCustomerGroupIdName").val(data.data.customer.groupName);
                                $("#updCustomerModle").modal("show");
                            } else {

                            }
                        },
                        error: function (data) {
                            layer.msg("修改失败", {time: 2000, icon: 5});
                        }
                    });
                } else if (layEvent == "updateGroup") {
                    $("#customerId").val('');
                    $("#cusGroupId").val('');
                    $.ajax({
                        type: "GET",
                        url: context + "/customer/detailedCustomer.do",
                        data: {"id": data.id},
                        success: function (data) {
                            if (data.status == 0) {
                                //已经选择的用户组
                                var groupIdS = data.data.customerBetweenGroupList;
                                if (groupIdS.length > 0) {
                                    var ids = "";
                                    for (var i in groupIdS) {
                                        ids += groupIdS[i].customerGroupId + ",";
                                    }
                                    $("#grouping").find("[name='customerGroupId']").val(ids);
                                }
                                $("#grouping").find("[name='customerId']").val(data.data.customer.id);
                                zTree_Menu = $.fn.zTree.getZTreeObj("groupTree");
                                var customerGroupId = $("#grouping").find("[name='customerGroupId']").val();
                                if (customerGroupId != '') {
                                    var customerGroupIdS = (customerGroupId.substring(0, customerGroupId.lastIndexOf(','))).split(',');
                                    console.log("截取后的id为:" + customerGroupIdS);
                                    var node;
                                    for (var i = 0; i < customerGroupIdS.length; i++) {
                                        node = zTree_Menu.getNodeByParam("id", customerGroupIdS[i]);
                                        zTree_Menu.checkNode(node, true);//指定选中ID的节点
                                        zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开
                                    }
                                }
                                $("#grouping").modal("show");
                            } else {

                            }
                        },
                        error: function (data) {
                            layer.msg("失败", {time: 2000, icon: 5});
                        }
                    });
                }
            });
            $("#grouping").on("hidden.bs.modal", function() {
                $(this).removeData("bs.modal");
                zTree_Menu = $.fn.zTree.getZTreeObj("groupTree");
                zTree_Menu.checkAllNodes(false);
                zTree_Menu.cancelSelectedNode();
                zTree_Menu.expandAll(false);
            });
            $("#updCustomerModle").on("hidden.bs.modal", function() {
                $(this).removeData("bs.modal");
                zTree_Menu = $.fn.zTree.getZTreeObj("updTree");
                zTree_Menu.checkAllNodes(false);
                zTree_Menu.cancelSelectedNode();
                zTree_Menu.expandAll(false);
            });

            function setAjaxParam(event) {
                var $form = $(event.target).parents(".search-form");
                var params = $form.serializeArray();
                // console.log(params)
                $.each(params, function (key, value) {
                    if (textReplace.test(value.value.trim())) {
                        parent.layer.msg("搜索字段不能包含特殊字符", {
                            time: 2000, //2s后自动关闭
                            icon: 5
                        });
                        return false;
                    }
                    ajaxParams[value.name] = value.value.trim();
                });
                params = $("#moreQueryForm").serializeArray();
                var ageSmall = params[0].value;
                var ageBig = params[1].value;
                if (!patrn.test(ageSmall.trim())) {
                    parent.layer.msg("年龄必须为整数", {
                        time: 2000, //2s后自动关闭
                        icon: 5
                    });
                    return false;
                }
                if (!patrn.test(ageBig.trim())) {
                    parent.layer.msg("年龄必须为整数", {
                        time: 2000, //2s后自动关闭
                        icon: 5
                    });
                    return false;
                }
                $.each(params, function (key, value) {
                    ajaxParams[value.name] = value.value.trim();
                });
            }

            function clearAjaxParams() {
                ajaxParams = {};
            }

            /************************查询****************************/
            $('.btn-search').on('click', function (event) {
                clearAjaxParams();
                setAjaxParam(event);
                table.reload('customer', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: ajaxParams
                    }
                });
            });

            /************************批量分组模态框****************************/
            $("#groupingModle").on("click", function () {
                var checkStatus = table.checkStatus('customer'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                /****批量分组中的树进行消除勾选和收起***/
                var treeObj = $.fn.zTree.getZTreeObj("groupTree");
                treeObj.checkAllNodes(false);
                treeObj.expandAll(false);
                $("#grouping").modal("show");
            });
            /************************批量分组****************************/
            $("#groupCommit").on("click", function () {
                //代表是点击左侧过来的
                var checkStatus = table.checkStatus('customer'),
                    data = checkStatus.data;//获取选中的数据
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                //代表单个
                if (ids == '') {
                    ids = $("#customerId").val();
                }
                var cusGroupId = $("#cusGroupId").val();
                console.log('已选择用户id为:' + ids + ';组id为:' + cusGroupId);
                $.ajax({
                    type: "POST",
                    url: context + "/customerGroup/dataGrouping.do",
                    data: {"customerId": ids, "cusGroupId": cusGroupId},
                    success: function (data) {
                        if (data.status == 0) {
                            $("#grouping").modal("hide");
                            layer.msg(data.message, {time: 2000, icon: 1});
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 2000, icon: 5});
                        }
                    },
                    error: function (data) {
                        layer.msg("失败", {time: 2000, icon: 5});
                    }
                });
            });

            /************************批量删除内容模态框****************************/
            $("#delCustomerS").on("click", function () {
                var checkStatus = table.checkStatus('customer'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                // $("#delCustomerModle").modal("show");
                layer.confirm('删除客户会将客户已有的外呼记录一并删除，您确定要删除吗？', {
                    icon: 3,
                    title: "删除",
                    btn: ["确定", "取消"],
                    btn1: function () {
                        var ids = "";
                        for (var i in data) {
                            ids += data[i].id + ",";
                        }
                        $.ajax({
                            type: "POST",
                            url: context + "/customer/delCustomer.do",
                            data: {"customerId": ids},
                            success: function (data) {
                                if (data.status == 0) {
                                    $("#delCustomerModle").modal("hide");
                                    layer.msg(data.message, {time: 2000, icon: 1});
                                    reloadTable();
                                } else {
                                    layer.msg(data.message, {time: 2000, icon: 5});
                                }
                            },
                            error: function (data) {
                                layer.msg("删除失败", {time: 2000, icon: 5});
                            }
                        });

                    }
                });
            });

            // /************************批量删除内容****************************/
            // $("#commitDelCustomer").on("click", function () {
            //     var checkStatus = table.checkStatus('customer'),
            //         data = checkStatus.data;//获取选中的数据
            //     var ids = "";
            //     for (var i in data) {
            //         ids += data[i].id + ",";
            //     }
            //     $.ajax({
            //         type: "POST",
            //         url: context + "/customer/delCustomer.do",
            //         data: {"customerId": ids},
            //         success: function (data) {
            //             if (data.status == 0) {
            //                 $("#delCustomerModle").modal("hide");
            //                 layer.msg(data.message, {time: 2000, icon: 1});
            //                 reloadTable();
            //             } else {
            //                 layer.msg(data.message, {time: 2000, icon: 5});
            //             }
            //         },
            //         error: function (data) {
            //             layer.msg("删除失败", {time: 2000, icon: 5});
            //         }
            //     });
            // });

            /*******************导入打开模态框**********************/
            $("#importCustomerModleS").on("click", function () {
                var treeObj = $.fn.zTree.getZTreeObj("importAddTree");
                treeObj.checkAllNodes(false);
                treeObj.expandAll(false);
                $("#filename").val('');
                $("#importCustomerModle").modal("show");
            });
            /********************下载模板************************/
            $("#downloadTemplate").on("click", function () {
                window.location.href = '/customer/exportExcel.do';
                $("#importCustomerModle").modal("hide");
            });
            /*************************确认导入*********************/
            $("#importCustomer").on("click", function () {
                var importCustomerGroupId= $("#importCustomerGroupId").val();
                console.log(importCustomerGroupId);
                var data = new FormData();
                var fileName = $("#filename").val();
                if (fileName == null || fileName == '') {
                    layer.msg("请选择需要导入的模板！", {time: 1000, icon: 5});
                    return;
                } else {
                    var fileName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
                    if (fileName!="xls"){
                        layer.msg("请使用默认模板！", {time: 2000, icon: 5});
                        return;
                    }
                    var filename = document.getElementById("filename").files[0].size;
                    var filesize=filename/1024;
                    if (filesize>30000){
                        layer.msg("文件过大！", {time: 2000, icon: 5});
                        return;
                    }
                    data.append("file", document.getElementById("filename").files[0]);
                    data.append("importCustomerGroupId",importCustomerGroupId);
                }
                $.ajax({
                    type: "POST",
                    url: context + "/customer/saveImport.do",
                    data: data,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success: function (msg) {
                        $("#filename").val('');
                        $("#importCustomerModle").modal("hide");
                        reloadTable();
                        if (msg.status == 0) {
                            layer.msg(msg.message, {time: 2000, icon: 1});
                        } else {
                            layer.msg(msg.message, {time: 2000, icon: 5});
                        }
                    },
                    error: function (msg) {

                    }
                });
            });

            /************************刷新table****************************/
            var reloadTable = function () {
                table.reload("customer", { //此处是上文提到的 初始化标识id
                });
            };
            /***********新增客户弹出模态框*************/
            $("#addCustomerModleS").on("click", function () {
                var treeObj = $.fn.zTree.getZTreeObj("addTree");
                treeObj.checkAllNodes(false);
                treeObj.expandAll(false);
                $("[name='name']").val('');
                $("[name='telephone']").val('');
                $("#customerGroupIdName").val('');
                $("#customerGroupId").val('');
                $("[name='age']").val('');
                $("#areaName").val('');
                $("#areaCode").val('');
                $("[name='descri']").val('');
                zTree_Menu = $.fn.zTree.getZTreeObj("addTree");
                var customerGroupId = ajaxParams['groupIdS'];
                console.log(customerGroupId);
                if (customerGroupId != '' && customerGroupId != undefined) {
                    $("#addCustomerModle").find("[name='customerGroupId']").val(customerGroupId);
                    $("#addCustomerModle").find("#customerGroupIdName").val(ajaxParams['groupIdSName']);
                    var customerGroupIdS = (customerGroupId.substring(0, customerGroupId.lastIndexOf(','))).split(',');
                    console.log("截取后的id为:" + customerGroupIdS);
                    var node;
                    for (var i = 0; i < customerGroupIdS.length; i++) {
                        node = zTree_Menu.getNodeByParam("id", customerGroupIdS[i]);
                        zTree_Menu.checkNode(node, true);//指定选中ID的节点
                        zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开
                    }
                }
                $("#addCustomerModle").modal("show");
            });
            /*************************新增客户****************************/
            $("#commitAddCustomer").on("click", function () {
                var name = $("#addCustomerModle").find("[name='name']").val();
                if (textReplace.test(name.trim())) {
                    layer.msg("姓名不能含有特殊字符", {icon: 5, time: 2000});
                    return;
                }
                if (name.trim() == '') {
                    layer.msg('姓名不能为空!', {time: 2000, icon: 5});
                    return;
                }
                var telephone = $("#addCustomerModle").find("[name='telephone']").val();
                if (textReplace.test(telephone.trim())) {
                    layer.msg("手机号不能含有特殊字符", {icon: 5, time: 2000});
                    return;
                }
                if (!regPhone.test(telephone.trim()) || telephone.trim().length != 11) {
                    layer.msg("手机号码不正确", {icon: 5, time: 2000});
                    return;
                }
                if (telephone.trim() == '') {
                    layer.msg('手机号不能为空!', {time: 2000, icon: 5});
                    return;
                }
                var age = $("#addCustomerModle").find("[name='age']").val();
                if (age.trim() != '') {
                    if (textReplace.test(age.trim())) {
                        layer.msg("年龄不能含有特殊字符", {icon: 5, time: 2000});
                        return;
                    }
                    if (!patrn.test(age.trim())) {
                        layer.msg('年龄必须为纯数字!', {time: 2000, icon:5});
                        return;
                    }
                    if (age.trim() > 125) {
                        layer.msg('年龄范围在0~125之间!', {time: 2000, icon: 5});
                        return;
                    }
                    if (age.trim() <= 0) {
                        layer.msg('年龄范围在0~125之间!', {time: 2000, icon: 5});
                        return;
                    }
                }
                /*if (age.trim() == '') {
                    layer.msg('年龄不能为空!', {time: 2000, icon: 1});
                    return;
                }*/
                /*
                var areaCode = $("#addCustomerModle").find("#areaCode").val();
                if (areaCode == '') {
                    layer.msg('地区不能为空!', {time: 2000, icon: 1});
                    return;
                }*/
                var form = $("#addCustomer").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/customer/addCustomer.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#addCustomerModle").modal("hide");
                            layer.msg(data.message, {time: 2000, icon: 1});
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 2000, icon: 5});
                        }
                    },
                    error: function (data) {
                        layer.msg("添加失败", {time: 2000, icon: 5});
                    }
                });
            });

            /*************************修改客户****************************/
            $("#updCommitAddCustomer").on("click", function () {
                var name = $("#updCustomerModle").find("[name='name']").val();
                if (textReplace.test(name.trim())) {
                    layer.msg("姓名不能含有特殊字符", {icon: 5, time: 2000});
                    return;
                }
                if (name.trim() == '') {
                    layer.msg('姓名不能为空!', {time: 2000, icon: 5});
                    return;
                }
                var telephone = $("#updCustomerModle").find("[name='telephone']").val();
                if (textReplace.test(telephone.trim())) {
                    layer.msg("手机号不能含有特殊字符", {icon: 5, time: 2000});
                    return;
                }
                if (!regPhone.test(telephone.trim()) || telephone.trim().length != 11) {
                    layer.msg("手机号码不正确", {icon: 5, time: 2000});
                    return;
                }
                if (telephone.trim() == '') {
                    layer.msg('手机号不能为空!', {time: 2000, icon: 5});
                    return;
                }
                var age = $("#updCustomerModle").find("[name='age']").val();
                if (age.trim() != '') {
                    if (textReplace.test(age.trim())) {
                        layer.msg("年龄不能含有特殊字符", {icon: 5, time: 2000});
                        return;
                    }
                    if (age.trim() > 125) {
                        layer.msg('年龄范围在0~125之间!', {time: 2000, icon: 5});
                        return;
                    }
                    if (age.trim() <= 0) {
                        layer.msg('年龄范围在0~125之间!', {time: 2000, icon: 5});
                        return;
                    }
                    if (age.trim() == '') {
                        layer.msg('年龄不能为空!', {time: 2000, icon: 5});
                        return;
                    }
                    if (!patrn.test(age.trim())) {
                        layer.msg('年龄必须为纯数字!', {time: 2000, icon: 5});
                        return;
                    }
                }
                /*var areaCode = $("#updCustomerModle").find("#areaCode-s").val();
                if (areaCode == '') {
                    layer.msg('地区不能为空!', {time: 2000, icon: 5});
                    return;
                }*/
                var form = $("#updCustomer").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/customer/updateCustomer.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#updCustomerModle").modal("hide");
                            layer.msg(data.message, {time: 2000, icon: 1});
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 2000, icon: 5});
                        }
                    },
                    error: function (data) {
                        layer.msg("修改失败", {time: 2000, icon: 5});
                    }
                });
            });
        });

        /***********************添加模块下拉树div****************************/
        showAddTree = function () {
            $("#addTreeDiv").slideDown("fast");
            $("#addCustomerModle").bind("mousedown", onBodyDown);
        };
        onBodyDown = function (event) {
            if (!(event.target.id == "addTreeDiv" || $(event.target).parents("#addTreeDiv").length > 0)) {
                hideMenu();
            }
        };
        hideMenu = function () {
            $("#addTreeDiv").fadeOut("fast");
            $("#addCustomerModle").unbind("mousedown", onBodyDown);
        };

        /***********************导入模块下拉树div****************************/
        importAddTree = function () {
            $("#importAddTreeDiv").slideDown("fast");
            $("#importCustomerModle").bind("mousedown", onBodyDown_imp);
        };
        onBodyDown_imp = function (event) {
            if (!(event.target.id == "importAddTreeDiv" || $(event.target).parents("#importAddTreeDiv").length > 0)) {
                hideMenu_imp();
            }
        };
        hideMenu_imp = function () {
            $("#importAddTreeDiv").fadeOut("fast");
            $("#importCustomerModle").unbind("mousedown", onBodyDown_imp);
        };

        /************************修改模块下拉树div****************************/
        showUpdateTree = function () {
            $("#updTreeDiv").slideDown("fast");
            $("#updCustomerModle").bind("mousedown", onBodyDown_update);
        };
        onBodyDown_update = function (event) {
            if (!(event.target.id == "updTreeDiv" || $(event.target).parents("#updTreeDiv").length > 0)) {
                hideMenu_update();
            }
        };
        hideMenu_update = function () {
            $("#updTreeDiv").fadeOut("fast");
            $("#updCustomerModle").unbind("mousedown", onBodyDown_update);
        };

        /************************导入模块文件上传****************************/
        $("#filename").on("change",function (e) {
            var name = e.currentTarget.files[0].name;
            $("#updata-text").text(name);
        });


        /**********************左侧树*******************/
        var zTreeObj;
        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            data: {
                simpleData: {   //简单的数据源，一般开发中都是从数据库里读取，API有介绍，这里只是本地的
                    enable: true,
                    idKey: "id",  //id和pid，
                    pIdKey: "groupId",
                    rootPId: null   //根节点
                }
            },
            view: {
                showLine: true,
                showIcon: false,
                selectedMulti: false,
                dblClickExpand: false,
                // addDiyDom: addDiyDom
            },
            callback: {
                beforeCheck: true,
                onClick: onCheck
            }

        };

        var pids = '';
        var pidsName = '';
        var cids = '';
        var cidsName = '';

        function onCheck(e, treeId, treeNode) {
            pids = '';
            pidsName = '';
            cids = '';
            cidsName = '';
            contentSubjectId_search = treeNode.id;
            var data;
            $.each(resu, function (e, d) {
                if (treeNode.id == d.id) {
                    data = d
                }
            });
            getPid(resu, data, treeNode.id);

            getChidId(resu, data);
            console.log(pids + "" + treeNode.id + "," + cids);
            var groupId = pids + "" + treeNode.id + "," + cids;
            var groupIdName = pidsName + "" + treeNode.name + "," + cidsName;
            ajaxParams['groupIdS'] = groupId;
            ajaxParams['groupIdSName'] = groupIdName;
            ajaxParams['groupId'] = contentSubjectId_search;
            table.reload('customer', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    key: ajaxParams
                }
            });
            // var treeObj = $.fn.zTree.getZTreeObj("allTree"),
            //     nodes = treeObj.getCheckedNodes(true),
            //     v = "";
            // for (var i = 0; i < nodes.length; i++) {
            //     v += nodes[i].name + ",";
            //     console.log("节点id:" + nodes[i].id + "节点名称" + v); //获取选中节点的值
            // }

        }

        function getPid(resu, data, oid) {
            if (data.groupId != null) {
                $.each(resu, function (e, d) {
                    //根据当前元素 gid 查询父节点
                    if (d.id == data.groupId) {
                        console.log("父节点111");
                        console.log(d);
                        pids = pids + d.id + ",";
                        pidsName = pidsName + d.name + ",";
                        getPid(resu, d, d.id);
                    }
                });
            }
            // pids = pids+oid;
        }

        function getChidId(resu, data) {
            $.each(resu, function (e, d) {
                //根据当前元素 gid 查询父节点
                if (d.groupId == data.id && d.id != data.id) {
                    console.log("子节点222");
                    console.log(d);
                    cids = cids + d.id + ",";
                    cidsName = cidsName + d.name + ",";
                    getChidId(resu, d);
                }
            });
        }


        /*************新增树 导入树 修改树  分组树*************/
        $('#customerGroupIdName').click(function () {
            $('#addTreeDiv').show();
        });
        $('#importCustomerGroupIdName').click(function () {
            $('#importAddTreeDiv').show();
        });
        $("#updCustomerGroupIdName").click(function () {
            //var treeObj = $("#updTree");
            //将以选的树选中
            //$.fn.zTree.init(treeObj, addSetting, resu);
            var zTree_Menu = $.fn.zTree.getZTreeObj("updTree");
            var customerGroupId = $("#updCustomerModle").find("[name='customerGroupId']").val();
            if (customerGroupId != '') {
                var customerGroupIdS = (customerGroupId.substring(0, customerGroupId.lastIndexOf(','))).split(',');
                console.log("截取后的id为:" + customerGroupIdS);
                var node;
                for (var i = 0; i < customerGroupIdS.length; i++) {
                    node = zTree_Menu.getNodeByParam("id", customerGroupIdS[i]);
                    zTree_Menu.checkNode(node, true);//指定选中ID的节点
                    zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开
                }
            }
            $('#updTreeDiv').show();
        })
        var addTree;
        var importTree;
        var updTree;
        var groupTree;
        var addSetting = {
            check: {
                //chkboxType: { "Y": "ps", "N": "ps" },
                chkStyle: "checkbox",//复选框类型
                enable: true //每个节点上是否显示 CheckBox
                , autoCheckTrigger: true
            },
            data: {
                simpleData: {   //简单的数据源，一般开发中都是从数据库里读取，API有介绍，这里只是本地的
                    enable: true,
                    idKey: "id",  //id和pid，
                    pIdKey: "groupId",
                    rootPId: null   //根节点
                }
            },
            view: {
                showLine: true,
                showIcon: false,
                selectedMulti: false,
                dblClickExpand: false,
                // addDiyDom: addDiyDom
            },
            callback: {
                beforeCheck: true,
                onCheck: addCheck
            }
        };
        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        var resu;
        $(document).ready(function () {
            $.ajax({
                type: "GET",
                url: context + "/customerGroup/showTree.do",
                data: {},
                success: function (data) {
                    console.log(data)
                    resu = eval(data.data);

                    zTreeObj = $.fn.zTree.init($("#allTree"), setting, resu);
                    addTree = $.fn.zTree.init($("#addTree"), addSetting, resu);
                    importTree = $.fn.zTree.init($("#importAddTree"), addSetting, resu);
                    updTree = $.fn.zTree.init($("#updTree"), addSetting, resu);
                    groupTree = $.fn.zTree.init($("#groupTree"), addSetting, resu);
                },
                error: function (data) {
                    layer.msg("查询主题失败", {time: 2000, icon: 5});
                }
            });

        });

        function addCheck(e, treeId, treeNode) {
            /****点击添加里面的树*****/
            var treeObj = $.fn.zTree.getZTreeObj("addTree"),
                nodes = treeObj.getCheckedNodes(true),
                v = "";
            cgIds = "";
            for (var i = 0; i < nodes.length; i++) {
                v += nodes[i].name + ",";
                cgIds += nodes[i].id + ",";
                console.log("节点id:" + nodes[i].id + "节点名称" + v); //获取选中节点的值
            }
            $('#customerGroupIdName').val(v);
            $('#customerGroupId').val(cgIds);
            /****点击导入里面的树*****/
            var treeImpObj = $.fn.zTree.getZTreeObj("importAddTree"),
                nodesImp = treeImpObj.getCheckedNodes(true),
                vImp = "";
            cgIdsImp = "";
            for (var i = 0; i < nodesImp.length; i++) {
                vImp += nodesImp[i].name + ",";
                cgIdsImp += nodesImp[i].id + ",";
                console.log("节点id:" + nodesImp[i].id + "节点名称" + vImp); //获取选中节点的值
            }
            $('#importCustomerGroupIdName').val(vImp);
            $('#importCustomerGroupId').val(cgIdsImp);
            /******点击修改里面的树********/
            var treeObjUpd = $.fn.zTree.getZTreeObj("updTree"),
                nodesUpd = treeObjUpd.getCheckedNodes(true),
                vUpd = "";
            cgIdsUpd = "";
            for (var i = 0; i < nodesUpd.length; i++) {
                vUpd += nodesUpd[i].name + ",";
                cgIdsUpd += nodesUpd[i].id + ",";
                console.log("节点id:" + nodesUpd[i].id + "节点名称" + vUpd); //获取选中节点的值
            }
            $('#updCustomerGroupIdName').val(vUpd);
            $('#updCustomerGroupId').val(cgIdsUpd);
            /******点击分组里面的树********/
            var treeObjGroup = $.fn.zTree.getZTreeObj("groupTree"),
                nodesGroup = treeObjGroup.getCheckedNodes(true),
                vGroup = "";
            cgIdsGroup = "";
            for (var i = 0; i < nodesGroup.length; i++) {
                vGroup += nodesGroup[i].name + ",";
                cgIdsGroup += nodesGroup[i].id + ",";
                console.log("节点id:" + nodesGroup[i].id + "节点名称" + vGroup); //获取选中节点的值
            }
            $('#cusGroupId').val(cgIdsGroup);
        }
    };

    // 地区联动
    var iplocation;
    !function ($) {
        $.extend({
            _jsonp: {
                scripts: {},
                counter: 1,
                charset: "gb2312",
                head: document.getElementsByTagName("head")[0],
                name: function (callback) {
                    var name = "_jsonp_" + (new Date).getTime() + "_" + this.counter;
                    this.counter++;
                    var cb = function (json) {
                        eval("delete " + name),
                            callback(json),
                            $._jsonp.head.removeChild($._jsonp.scripts[name]),
                            delete $._jsonp.scripts[name]
                    };
                    return eval(name + " = cb"),
                        name
                },
                load: function (a, b) {
                    var c = document.createElement("script");
                    c.type = "text/javascript",
                        c.charset = this.charset,
                        c.src = a,
                        this.head.appendChild(c),
                        this.scripts[b] = c
                }
            },
            getJSONP: function (a, b) {
                var c = $._jsonp.name(b),
                    a = a.replace(/{callback};/, c);
                return $._jsonp.load(a, c),
                    this
            }
        });
        getArea(100);

        /******************获取所有的省份信息 ********************/
        function getArea(areaCode) {
            $.ajax({
                type: "GET",
                url: "/area/findAreaInfo.do",
                async: false,
                data: {"areaCode": areaCode},
                success: function (data) {
                    // console.log(data);
                    if (data.status == 0) {
                        var provinceInfo = data.data;
                        iplocation = provinceInfo;
                        if (data.status == 0 && provinceInfo != null) {
                            var provinceHtml = '<div class="content"><div data-widget="tabs" class="m JD-stock" id="JD-stock">'
                                + '<div class="mt">'
                                + '    <ul class="tab">'
                                + '        <li data-index="0" data-widget="tab-item" class="curr" ><a href="#none" class="hover"><em id="chooseProvince">请选择</em><i></i></a></li>'
                                + '        <li data-index="1" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseCity">请选择</em><i></i></a></li>'
                                + '        <li data-index="2" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseArea">请选择</em><i></i></a></li>'
                                + '        <li data-index="3" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseTown">请选择</em><i></i></a></li>'
                                + '    </ul>'
                                + '    <div class="stock-line"></div>'
                                + '</div>'
                                + '<div class="mc" data-area="0" data-widget="tab-content" id="stock_province_item">'
                                + '    <ul class="area-list">';
                            for (var i in provinceInfo) {
                                provinceHtml += ' <li><a href="#none" data-value="' + provinceInfo[i].areaCode + '">' + provinceInfo[i].areaName + '</a></li>';
                            }
                            provinceHtml += '    </ul>'
                                + '</div>'
                                + '<div class="mc" data-area="1" data-widget="tab-content" id="stock_city_item"></div>'
                                + '<div class="mc" data-area="2" data-widget="tab-content" id="stock_area_item"></div>'
                                + '<div class="mc" data-area="3" data-widget="tab-content" id="stock_town_item"></div>'
                                + '</div></div>';
                            $("#store-selector .text").after(provinceHtml);
                        }
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                    if ("ajaxtimeout" == ajaxData) {
                        window.location.href = context + "/index.jsp?tag=1"
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                    }
                }
            });
        }
    }
    (jQuery);

    var cName = "ipLocation";
    var currentLocation = "安徽省";
    var currentProvinceId = 100100;

//根据省份ID获取名称
    function getNameById(provinceId) {
        for (var o in iplocation) {
            if (iplocation[o] && iplocation[o].areaCode == provinceId) {
                return iplocation[o].areaName;
            }
        }
        return "安徽省";
    }

    var isUseServiceLoc = true; //是否默认使用服务端地址

    function getAreaList(result) {
        var html = ["<ul class='area-list'>"];
        var longhtml = [];
        var longerhtml = [];
        $.ajax({
            type: "GET",
            url: "/area/findAreaInfo.do",
            async: false,
            data: {"areaCode": result},
            success: function (data) {
                // console.log(data);
                if (data.status == 0) {
                    var cityInfo = data.data;
                    if (data.status == 0 && cityInfo != null) {
                        if (cityInfo && cityInfo.length > 0) {
                            for (var i = 0; i < cityInfo.length; i++) {
                                cityInfo[i].areaName = cityInfo[i].areaName.replace(" ", "");
                                if (cityInfo[i].areaName.length > 12) {
                                    longerhtml.push("<li class='longer-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else if (cityInfo[i].areaName.length > 5) {
                                    longhtml.push("<li class='long-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else {
                                    html.push("<li><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                            }
                        }
                        else {
                            html.push("<li><a href='#none' data-value='" + currentAreaInfo.currentFid + "'> </a></li>");
                        }
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                if ("ajaxtimeout" == ajaxData) {
                    window.location.href = context + "/index.jsp?tag=1"
                } else {
                    layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                }
            }
        });
        html.push(longhtml.join(""));
        html.push(longerhtml.join(""));
        html.push("</ul>");
        return html.join("");
    }

    function cleanKuohao(str) {
        if (str && str.indexOf("(") > 0) {
            str = str.substring(0, str.indexOf("("));
        }
        if (str && str.indexOf("（") > 0) {
            str = str.substring(0, str.indexOf("（"));
        }
        return str;
    }

    function getStockOpt(id, name) {
        if (currentAreaInfo.currentLevel == 3) {
            currentAreaInfo.currentAreaId = id;
            currentAreaInfo.currentAreaName = name;
            if (!page_load) {
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
            }
        }
        else if (currentAreaInfo.currentLevel == 4) {
            currentAreaInfo.currentTownId = id;
            currentAreaInfo.currentTownName = name;
        }
        //添加20140224
        $('#store-selector').removeClass('hover');
        //setCommonCookies(currentAreaInfo.currentProvinceId,currentLocation,currentAreaInfo.currentCityId,currentAreaInfo.currentAreaId,currentAreaInfo.currentTownId,!page_load);
        if (page_load) {
            page_load = false;
        }
        //替换gSC
        var address = currentAreaInfo.currentProvinceName + currentAreaInfo.currentCityName + currentAreaInfo.currentAreaName + currentAreaInfo.currentTownName;
        $("#store-selector .text div").html(currentAreaInfo.currentProvinceName + cleanKuohao(currentAreaInfo.currentCityName) + cleanKuohao(currentAreaInfo.currentAreaName) + cleanKuohao(currentAreaInfo.currentTownName)).attr("title", address);
    }

    function getAreaListcallback(r) {
        currentDom.html(getAreaList(r));
        if (currentAreaInfo.currentLevel >= 2) {
            currentDom.find("a").click(function () {
                if (page_load) {
                    page_load = false;
                }
                if (currentDom.attr("id") == "stock_area_item") {
                    currentAreaInfo.currentLevel = 3;
                }
                else if (currentDom.attr("id") == "stock_town_item") {
                    currentAreaInfo.currentLevel = 4;
                }
                getStockOpt($(this).attr("data-value"), $(this).html());
            });
            if (page_load) { //初始化加载
                currentAreaInfo.currentLevel = currentAreaInfo.currentLevel == 2 ? 3 : 4;
                if (currentAreaInfo.currentAreaId && new Number(currentAreaInfo.currentAreaId) > 0) {
                    getStockOpt(currentAreaInfo.currentAreaId, currentDom.find("a[data-value='" + currentAreaInfo.currentAreaId + "']").html());
                }
                else {
                    getStockOpt(currentDom.find("a").eq(0).attr("data-value"), currentDom.find("a").eq(0).html());
                }
            }
        }
    }

    function chooseProvince(provinceId) {
        provinceContainer.hide();
        // currentAreaInfo.currentLevel = 1;
        currentAreaInfo.currentProvinceId = provinceId;
        currentAreaInfo.currentProvinceName = getNameById(provinceId);
        if (!page_load) {
            currentAreaInfo.currentCityId = 0;
            currentAreaInfo.currentCityName = "";
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainer.eq(0).removeClass("curr").find("em").html(currentAreaInfo.currentProvinceName);
        areaTabContainer.eq(1).addClass("curr").show().find("em").html("请选择");
        areaTabContainer.eq(2).hide();
        areaTabContainer.eq(3).hide();
        cityContainer.show();
        areaContainer.hide();
        townaContainer.hide();
        cityContainer.html(getAreaList(provinceId));
        cityContainer.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#store-selector").unbind("mouseout");
            chooseCity($(this).attr("data-value"), $(this).html());
        });
        /*if (page_load) { //初始化加载
         if (currentAreaInfo.currentCityId && new Number(currentAreaInfo.currentCityId) > 0) {
         chooseCity(currentAreaInfo.currentCityId, cityContainer.find("a[data-value='" + currentAreaInfo.currentCityId + "']").html());
         }
         else {
         chooseCity(cityContainer.find("a").eq(0).attr("data-value"), cityContainer.find("a").eq(0).html());
         }
         }*/
    }

    function chooseCity(cityId, cityName) {
        provinceContainer.hide();
        cityContainer.hide();
        currentAreaInfo.currentLevel = 2;
        currentAreaInfo.currentCityId = cityId;
        currentAreaInfo.currentCityName = cityName;
        if (!page_load) {
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainer.eq(1).removeClass("curr").find("em").html(cityName);
        areaTabContainer.eq(2).addClass("curr").show().find("em").html("请选择");
        areaTabContainer.eq(3).hide();
        areaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
        townaContainer.hide();
        $('#areaCode').val(cityId);
        $('#areaName').val($("#chooseProvince").text() + "-" + cityName);
        areaContainer.show().html(getAreaList(cityId));
        areaContainer.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#store-selector").unbind("mouseout");
            chooseArea($(this).attr("data-value"), $(this).html());
        });
        try {
            // getActicity(cityId);
        } catch (e) {
        }
    }

    function chooseArea(areaId, areaName) {
        $('#store-selector').removeClass('hover');
        $('#areaCode').val(areaId);
        $('#areaName').val($("#chooseProvince").text() + "-" + $("#chooseCity").text() + "-" + areaName);
        try {
            // getActicity(areaId);
        } catch (e) {
        }
        /*  provinceContainer.hide();
         cityContainer.hide();
         areaContainer.hide();
         currentAreaInfo.currentLevel = 3;
         currentAreaInfo.currentAreaId = areaId;
         currentAreaInfo.currentAreaName = areaName;
         if (!page_load) {
         currentAreaInfo.currentTownId = 0;
         currentAreaInfo.currentTownName = "";
         }
         areaTabContainer.eq(2).removeClass("curr").find("em").html(areaName);
         areaTabContainer.eq(3).addClass("curr").show().find("em").html("请选择");
         townaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
         currentDom = townaContainer;
         townaContainer.show().html(getAreaList(areaId));*/
    }

    var areaTabContainer = $("#JD-stock .tab li");
    var provinceContainer = $("#stock_province_item");
    var cityContainer = $("#stock_city_item");
    var areaContainer = $("#stock_area_item");
    var townaContainer = $("#stock_town_item");
    var currentDom = provinceContainer;
//当前地域信息
    var currentAreaInfo;

//初始化当前地域信息
    function CurrentAreaInfoInit() {
        currentAreaInfo = {
            "currentProvinceId": 100100,
            "currentProvinceName": "安徽省",
            "currentCityId": 1001001000,
            "currentCityName": "合肥市",
            "currentAreaId": 100100100010001,
            "currentAreaName": "庐阳区",
            "currentTownId": 0,
            "currentTownName": ""
        };
        var ipLoc = getCookie("ipLoc-djd");
        ipLoc = ipLoc ? ipLoc.split("-") : [100100, 1001001000, 0, 0];
        if (ipLoc.length > 0 && ipLoc[0]) {
            currentAreaInfo.currentProvinceId = ipLoc[0];
            currentAreaInfo.currentProvinceName = getNameById(ipLoc[0]);
        }
        if (ipLoc.length > 1 && ipLoc[1]) {
            currentAreaInfo.currentCityId = ipLoc[1];
        }
        if (ipLoc.length > 2 && ipLoc[2]) {
            currentAreaInfo.currentAreaId = ipLoc[2];
        }
        if (ipLoc.length > 3 && ipLoc[3]) {
            currentAreaInfo.currentTownId = ipLoc[3];
        }
    }

    var page_load = true;
    (function () {
        $("#store-selector").unbind("mouseover").bind("mouseover", function () {
            $('#store-selector').addClass('hover');
            $("#store-selector .content,#JD-stock").show();
        }).find("dl").remove();
        $("#store-selector").unbind("mouseout").bind("mouseout", function () {
            $('#store-selector').removeClass('hover');
            if ($("#areaName").val() != "" && $("#areaName").val() != null) {
                $("#areaName-error").remove();
                $("#areaName").removeClass(".error");
            }
            // $("#store-selector .content,#JD-stock").show();
        }).find("dl").remove();
        CurrentAreaInfoInit();
        areaTabContainer.eq(0).find("a").click(function () {
            areaTabContainer.removeClass("curr");
            areaTabContainer.eq(0).addClass("curr").show();
            provinceContainer.show();
            cityContainer.hide();
            areaContainer.hide();
            townaContainer.hide();
            areaTabContainer.eq(1).hide();
            areaTabContainer.eq(2).hide();
            areaTabContainer.eq(3).hide();
        });
        areaTabContainer.eq(1).find("a").click(function () {
            areaTabContainer.removeClass("curr");
            areaTabContainer.eq(1).addClass("curr").show();
            provinceContainer.hide();
            cityContainer.show();
            areaContainer.hide();
            townaContainer.hide();
            areaTabContainer.eq(2).hide();
            areaTabContainer.eq(3).hide();
        });
        areaTabContainer.eq(2).find("a").click(function () {
            areaTabContainer.removeClass("curr");
            areaTabContainer.eq(2).addClass("curr").show();
            provinceContainer.hide();
            cityContainer.hide();
            areaContainer.show();
            townaContainer.hide();
            areaTabContainer.eq(3).hide();
        });
        provinceContainer.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //   $("#store-selector").unbind("mouseout");
            chooseProvince($(this).attr("data-value"));
        }).end();
        chooseProvince(currentAreaInfo.currentProvinceId);
    })();

    function getCookie(name) {
        var start = document.cookie.indexOf(name + "=");
        var len = start + name.length + 1;
        if ((!start) && (name != document.cookie.substring(0, name.length))) {
            return null;
        }
        if (start == -1) return null;
        var end = document.cookie.indexOf(';', len);
        if (end == -1) end = document.cookie.length;
        return unescape(document.cookie.substring(len, end));

    };
    // 修改地区联动
    !function ($) {
        $.extend({
            _jsonp: {
                scripts: {},
                counter: 1,
                charset: "gb2312",
                head: document.getElementsByTagName("head")[0],
                name: function (callback) {
                    var name = "_jsonp_" + (new Date).getTime() + "_" + this.counter;
                    this.counter++;
                    var cb = function (json) {
                        eval("delete " + name),
                            callback(json),
                            $._jsonp.head.removeChild($._jsonp.scripts[name]),
                            delete $._jsonp.scripts[name]
                    };
                    return eval(name + " = cb"),
                        name
                },
                load: function (a, b) {
                    var c = document.createElement("script");
                    c.type = "text/javascript",
                        c.charset = this.charset,
                        c.src = a,
                        this.head.appendChild(c),
                        this.scripts[b] = c
                }
            },
            getJSONP: function (a, b) {
                var c = $._jsonp.name(b),
                    a = a.replace(/{callback};/, c);
                return $._jsonp.load(a, c),
                    this
            }
        });
        getAreas(100);

        /******************获取所有的省份信息 ********************/
        function getAreas(areaCode) {
            $.ajax({
                type: "GET",
                url: "/area/findAreaInfo.do",
                async: false,
                data: {"areaCode": areaCode},
                success: function (data) {
                    if (data.status == 0) {
                        var provinceInfo = data.data;
                        iplocation = provinceInfo;
                        if (data.status == 0 && provinceInfo != null) {
                            var provinceHtml = '<div class="content"><div data-widget="tabs" class="m JD-stock-s" id="JD-stock-s">'
                                + '<div class="mt">'
                                + '    <ul class="tab">'
                                + '        <li data-index="0" data-widget="tab-item" class="curr" ><a href="#none" class="hover"><em id="chooseProvinces">请选择</em><i></i></a></li>'
                                + '        <li data-index="1" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseCitys">请选择</em><i></i></a></li>'
                                + '        <li data-index="2" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseAreas">请选择</em><i></i></a></li>'
                                + '        <li data-index="3" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseTowns">请选择</em><i></i></a></li>'
                                + '    </ul>'
                                + '    <div class="stock-line"></div>'
                                + '</div>'
                                + '<div class="mc" data-area="0" data-widget="tab-content" id="stock_province_items">'
                                + '    <ul class="area-list">';
                            for (var i in provinceInfo) {
                                provinceHtml += ' <li><a href="#none" data-value="' + provinceInfo[i].areaCode + '">' + provinceInfo[i].areaName + '</a></li>';
                            }
                            provinceHtml += '    </ul>'
                                + '</div>'
                                + '<div class="mc" data-area="1" data-widget="tab-content" id="stock_city_items"></div>'
                                + '<div class="mc" data-area="2" data-widget="tab-content" id="stock_area_items"></div>'
                                + '<div class="mc" data-area="3" data-widget="tab-content" id="stock_town_items"></div>'
                                + '</div></div>';
                            $("#summary-stock-s .text").after(provinceHtml);
                        } else {
                            layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                        }
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                    if ("ajaxtimeout" == ajaxData) {
                        window.location.href = context + "/index.jsp?tag=1"
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                    }
                }
            });
        }
    }
    (jQuery);

    var cName = "ipLocation";
    var currentLocation = "安徽省";
    var currentProvinceId = 100100;

//根据省份ID获取名称
    function getNameByIds(provinceId) {
        for (var o in iplocation) {
            if (iplocation[o] && iplocation[o].areaCode == provinceId) {
                return iplocation[o].areaName;
            }
        }
        return "安徽省";
    }

    var isUseServiceLoc = true; //是否默认使用服务端地址

    function getAreaLists(result) {
        var html = ["<ul class='area-list'>"];
        var longhtml = [];
        var longerhtml = [];
        $.ajax({
            type: "GET",
            url: "/area/findAreaInfo.do",
            async: false,
            data: {"areaCode": result},
            success: function (data) {
                if (data.status == 0) {
                    var cityInfo = data.data;
                    if (data.status == 0 && cityInfo != null) {
                        if (cityInfo && cityInfo.length > 0) {
                            for (var i = 0; i < cityInfo.length; i++) {
                                cityInfo[i].areaName = cityInfo[i].areaName.replace(" ", "");
                                if (cityInfo[i].areaName.length > 12) {
                                    longerhtml.push("<li class='longer-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else if (cityInfo[i].areaName.length > 5) {
                                    longhtml.push("<li class='long-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else {
                                    html.push("<li><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                            }
                        }
                        else {
                            html.push("<li><a href='#none' data-value='" + currentAreaInfo.currentFid + "'> </a></li>");
                        }
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                if ("ajaxtimeout" == ajaxData) {
                    window.location.href = context + "/index.jsp?tag=1"
                } else {
                    layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                }
            }
        });
        html.push(longhtml.join(""));
        html.push(longerhtml.join(""));
        html.push("</ul>");
        return html.join("");
    }

    function cleanKuohaos(str) {
        if (str && str.indexOf("(") > 0) {
            str = str.substring(0, str.indexOf("("));
        }
        if (str && str.indexOf("（") > 0) {
            str = str.substring(0, str.indexOf("（"));
        }
        return str;
    }

    function getStockOpts(id, name) {
        if (currentAreaInfo.currentLevel == 3) {
            currentAreaInfo.currentAreaId = id;
            currentAreaInfo.currentAreaName = name;
            if (!page_load) {
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
            }
        }
        else if (currentAreaInfo.currentLevel == 4) {
            currentAreaInfo.currentTownId = id;
            currentAreaInfo.currentTownName = name;
        }
        //添加20140224
        $('#summary-stock-s').removeClass('hover');
        //setCommonCookies(currentAreaInfo.currentProvinceId,currentLocation,currentAreaInfo.currentCityId,currentAreaInfo.currentAreaId,currentAreaInfo.currentTownId,!page_load);
        if (page_load) {
            page_load = false;
        }
        //替换gSC
        var address = currentAreaInfo.currentProvinceName + currentAreaInfo.currentCityName + currentAreaInfo.currentAreaName + currentAreaInfo.currentTownName;
        $("#summary-stock-s .text div").html(currentAreaInfo.currentProvinceName + cleanKuohaos(currentAreaInfo.currentCityName) + cleanKuohaos(currentAreaInfo.currentAreaName) + cleanKuohaos(currentAreaInfo.currentTownName)).attr("title", address);
    }

    function getAreaListcallback(r) {
        currentDoms.html(getAreaLists(r));
        if (currentAreaInfo.currentLevel >= 2) {
            currentDoms.find("a").click(function () {
                if (page_load) {
                    page_load = false;
                }
                if (currentDoms.attr("id") == "stock_area_items") {
                    currentAreaInfo.currentLevel = 3;
                }
                else if (currentDoms.attr("id") == "stock_town_items") {
                    currentAreaInfo.currentLevel = 4;
                }
                getStockOpts($(this).attr("data-value"), $(this).html());
            });
            if (page_load) { //初始化加载
                currentAreaInfo.currentLevel = currentAreaInfo.currentLevel == 2 ? 3 : 4;
                if (currentAreaInfo.currentAreaId && new Number(currentAreaInfo.currentAreaId) > 0) {
                    getStockOpts(currentAreaInfo.currentAreaId, currentDoms.find("a[data-value='" + currentAreaInfo.currentAreaId + "']").html());
                }
                else {
                    getStockOpts(currentDoms.find("a").eq(0).attr("data-value"), currentDoms.find("a").eq(0).html());
                }
            }
        }
    }

    function chooseProvinces(provinceId) {

        provinceContainers.hide();
        // currentAreaInfo.currentLevel = 1;
        currentAreaInfo.currentProvinceId = provinceId;
        currentAreaInfo.currentProvinceName = getNameByIds(provinceId);
        if (!page_load) {
            currentAreaInfo.currentCityId = 0;
            currentAreaInfo.currentCityName = "";
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainers.eq(0).removeClass("curr").find("em").html(currentAreaInfo.currentProvinceName);
        areaTabContainers.eq(1).addClass("curr").show().find("em").html("请选择");
        areaTabContainers.eq(2).hide();
        areaTabContainers.eq(3).hide();
        cityContainers.show();
        areaContainers.hide();
        townaContainers.hide();
        cityContainers.html(getAreaLists(provinceId));
        cityContainers.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#summary-stock-s").unbind("mouseout");
            chooseCitys($(this).attr("data-value"), $(this).html());
        });
        /*if (page_load) { //初始化加载
         if (currentAreaInfo.currentCityId && new Number(currentAreaInfo.currentCityId) > 0) {
         chooseCity(currentAreaInfo.currentCityId, cityContainers.find("a[data-value='" + currentAreaInfo.currentCityId + "']").html());
         }
         else {
         chooseCity(cityContainers.find("a").eq(0).attr("data-value"), cityContainers.find("a").eq(0).html());
         }
         }*/
    }

    function chooseCitys(cityId, cityName) {
        provinceContainers.hide();
        cityContainers.hide();
        currentAreaInfo.currentLevel = 2;
        currentAreaInfo.currentCityId = cityId;
        currentAreaInfo.currentCityName = cityName;
        if (!page_load) {
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainers.eq(1).removeClass("curr").find("em").html(cityName);
        areaTabContainers.eq(2).addClass("curr").show().find("em").html("请选择");
        areaTabContainers.eq(3).hide();
        areaContainers.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
        townaContainers.hide();
        $('#areaCode-s').val(cityId);
        $('#areaName-s').val($("#chooseProvinces").text() + "-" + cityName);
        areaContainers.show().html(getAreaLists(cityId));
        areaContainers.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#summary-stock-s").unbind("mouseout");
            chooseAreas($(this).attr("data-value"), $(this).html());
        });
        try {
            // getActicity(cityId);
        } catch (e) {
        }
    }

    function chooseAreas(areaId, areaName) {
        $('#summary-stock-s').removeClass('hover');
        $('#areaCode-s').val(areaId);
        $('#areaName-s').val($("#chooseProvinces").text() + "-" + $("#chooseCitys").text() + "-" + areaName);
        try {
            // getActicity(areaId);
        } catch (e) {
        }
        /*  provinceContainers.hide();
         cityContainers.hide();
         areaContainers.hide();
         currentAreaInfo.currentLevel = 3;
         currentAreaInfo.currentAreaId = areaId;
         currentAreaInfo.currentAreaName = areaName;
         if (!page_load) {
         currentAreaInfo.currentTownId = 0;
         currentAreaInfo.currentTownName = "";
         }
         areaTabContainers.eq(2).removeClass("curr").find("em").html(areaName);
         areaTabContainers.eq(3).addClass("curr").show().find("em").html("请选择");
         townaContainers.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
         currentDoms = townaContainers;
         townaContainers.show().html(getAreaList(areaId));*/
    }

    var areaTabContainers = $("#JD-stock-s .tab li");
    var provinceContainers = $("#stock_province_items");
    var cityContainers = $("#stock_city_items");
    var areaContainers = $("#stock_area_items");
    var townaContainers = $("#stock_town_items");
    var currentDoms = provinceContainers;
//当前地域信息
    var currentAreaInfo;

//初始化当前地域信息
    function CurrentAreaInfoInits() {
        currentAreaInfo = {
            "currentProvinceId": 100100,
            "currentProvinceName": "安徽省",
            "currentCityId": 1001001000,
            "currentCityName": "合肥市",
            "currentAreaId": 100100100010001,
            "currentAreaName": "庐阳区",
            "currentTownId": 0,
            "currentTownName": ""
        };
        var ipLoc = getCookies("ipLoc-djd");
        ipLoc = ipLoc ? ipLoc.split("-") : [100100, 1001001000, 0, 0];
        if (ipLoc.length > 0 && ipLoc[0]) {
            currentAreaInfo.currentProvinceId = ipLoc[0];
            currentAreaInfo.currentProvinceName = getNameByIds(ipLoc[0]);
        }
        if (ipLoc.length > 1 && ipLoc[1]) {
            currentAreaInfo.currentCityId = ipLoc[1];
        }
        if (ipLoc.length > 2 && ipLoc[2]) {
            currentAreaInfo.currentAreaId = ipLoc[2];
        }
        if (ipLoc.length > 3 && ipLoc[3]) {
            currentAreaInfo.currentTownId = ipLoc[3];
        }
    }

    var page_load = true;
    (function () {
        $("#summary-stock-s").unbind("mouseover").bind("mouseover", function () {
            $('#summary-stock-s').addClass('hover');
            $("#summary-stock-s .content,#JD-stock-s").show();
        }).find("dl").remove();
        $("#summary-stock-s").unbind("mouseout").bind("mouseout", function () {
            $('#summary-stock-s').removeClass('hover');
            if ($("#areaName-s").val() != "" && $("#areaName-s").val() != null) {
                $("#areaName-s-error").remove();
                $("#areaName-s").removeClass(".error");
            }
            $("#summary-stock-s .content,#JD-stock-s").hide();
            // $("#summary-stock-s .content,#JD-stock").show();
        }).find("dl").remove();
        CurrentAreaInfoInits();
        areaTabContainers.eq(0).find("a").click(function () {

            areaTabContainers.removeClass("curr");
            areaTabContainers.eq(0).addClass("curr").show();
            provinceContainers.show();
            cityContainers.hide();
            areaContainers.hide();
            townaContainers.hide();
            areaTabContainers.eq(1).hide();
            areaTabContainers.eq(2).hide();
            areaTabContainers.eq(3).hide();
        });
        areaTabContainers.eq(1).find("a").click(function () {
            areaTabContainers.removeClass("curr");
            areaTabContainers.eq(1).addClass("curr").show();
            provinceContainers.hide();
            cityContainers.show();
            areaContainers.hide();
            townaContainers.hide();
            areaTabContainers.eq(2).hide();
            areaTabContainers.eq(3).hide();
        });
        areaTabContainers.eq(2).find("a").click(function () {
            areaTabContainers.removeClass("curr");
            areaTabContainers.eq(2).addClass("curr").show();
            provinceContainers.hide();
            cityContainers.hide();
            areaContainers.show();
            townaContainers.hide();
            areaTabContainers.eq(3).hide();
        });
        provinceContainers.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //   $("#summary-stock-s").unbind("mouseout");
            chooseProvinces($(this).attr("data-value"));
        }).end();
        chooseProvinces(currentAreaInfo.currentProvinceId);
    })();

    function getCookies(name) {
        var start = document.cookie.indexOf(name + "=");
        var len = start + name.length + 1;
        if ((!start) && (name != document.cookie.substring(0, name.length))) {
            return null;
        }
        if (start == -1) return null;
        var end = document.cookie.indexOf(';', len);
        if (end == -1) end = document.cookie.length;
        return unescape(document.cookie.substring(len, end));

    };
    //筛选条件地区
    !function ($) {
        $.extend({
            _jsonp: {
                scripts: {},
                counter: 1,
                charset: "gb2312",
                head: document.getElementsByTagName("head")[0],
                name: function (callback) {
                    var name = "_jsonp_" + (new Date).getTime() + "_" + this.counter;
                    this.counter++;
                    var cb = function (json) {
                        eval("delete " + name),
                            callback(json),
                            $._jsonp.head.removeChild($._jsonp.scripts[name]),
                            delete $._jsonp.scripts[name]
                    };
                    return eval(name + " = cb"),
                        name
                },
                load: function (a, b) {
                    var c = document.createElement("script");
                    c.type = "text/javascript",
                        c.charset = this.charset,
                        c.src = a,
                        this.head.appendChild(c),
                        this.scripts[b] = c
                }
            },
            getJSONP: function (a, b) {
                var c = $._jsonp.name(b),
                    a = a.replace(/{callback};/, c);
                return $._jsonp.load(a, c),
                    this
            }
        });
        getAreas(100);

        /******************获取所有的省份信息 ********************/
        function getAreas(areaCode) {
            $.ajax({
                type: "GET",
                url: "/area/findAreaInfo.do",
                async: false,
                data: {"areaCode": areaCode},
                success: function (data) {
                    if (data.status == 0) {
                        var provinceInfo = data.data;
                        iplocation = provinceInfo;
                        if (data.status == 0 && provinceInfo != null) {
                            var provinceHtml = '<div class="content"><div data-widget="tabs" class="m JD-stock3" id="JD-stock3">'
                                + '<div class="mt">'
                                + '    <ul class="tab">'
                                + '        <li data-index="0" data-widget="tab-item" class="curr" ><a href="#none" class="hover"><em id="chooseProvince3">请选择</em><i></i></a></li>'
                                + '        <li data-index="1" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseCity3">请选择</em><i></i></a></li>'
                                + '        <li data-index="2" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseArea3">请选择</em><i></i></a></li>'
                                + '        <li data-index="3" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em id="chooseTown3">请选择</em><i></i></a></li>'
                                + '    </ul>'
                                + '    <div class="stock-line"></div>'
                                + '</div>'
                                + '<div class="mc" data-area="0" data-widget="tab-content" id="stock_province_item3">'
                                + '    <ul class="area-list">';
                            for (var i in provinceInfo) {
                                provinceHtml += ' <li><a href="#none" data-value="' + provinceInfo[i].areaCode + '">' + provinceInfo[i].areaName + '</a></li>';
                            }
                            provinceHtml += '    </ul>'
                                + '</div>'
                                + '<div class="mc" data-area="1" data-widget="tab-content" id="stock_city_item3"></div>'
                                + '<div class="mc" data-area="2" data-widget="tab-content" id="stock_area_item3"></div>'
                                + '<div class="mc" data-area="3" data-widget="tab-content" id="stock_town_item3"></div>'
                                + '</div></div>';
                            $("#summary-stock3 .text").after(provinceHtml);
                        } else {
                            layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                    if ("ajaxtimeout" == ajaxData) {
                        window.location.href = context + "/index.jsp?tag=1"
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 2, time: 2000});
                    }
                }
            });
        }
    }
    (jQuery);

    var cName = "ipLocation";
    var currentLocation = "安徽省";
    var currentProvinceId = 100100;

//根据省份ID获取名称
    function getNameByIds(provinceId) {
        for (var o in iplocation) {
            if (iplocation[o] && iplocation[o].areaCode == provinceId) {
                return iplocation[o].areaName;
            }
        }
        return "安徽省";
    }

    var isUseServiceLoc = true; //是否默认使用服务端地址

    function getAreaLists(result) {
        var html = ["<ul class='area-list'>"];
        var longhtml = [];
        var longerhtml = [];
        $.ajax({
            type: "GET",
            url: "/area/findAreaInfo.do",
            async: false,
            data: {"areaCode": result},
            success: function (data) {
                if (data.status == 0) {
                    var cityInfo = data.data;
                    if (data.status == 0 && cityInfo != null) {
                        if (cityInfo && cityInfo.length > 0) {
                            for (var i = 0; i < cityInfo.length; i++) {
                                cityInfo[i].areaName = cityInfo[i].areaName.replace(" ", "");
                                if (cityInfo[i].areaName.length > 12) {
                                    longerhtml.push("<li class='longer-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else if (cityInfo[i].areaName.length > 5) {
                                    longhtml.push("<li class='long-area'><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                                else {
                                    html.push("<li><a href='#none' data-value='" + cityInfo[i].areaCode + "'>" + cityInfo[i].areaName + "</a></li>");
                                }
                            }
                        }
                        else {
                            html.push("<li><a href='#none' data-value='" + currentAreaInfo.currentFid + "'> </a></li>");
                        }
                    } else {
                        layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                var ajaxData = XMLHttpRequest.getResponseHeader("ajaxtimeout");
                if ("ajaxtimeout" == ajaxData) {
                    window.location.href = context + "/index.jsp?tag=1"
                } else {
                    layer.msg("获取地区数据失败，请刷新页面！", {icon: 5, time: 2000});
                }
            }
        });
        html.push(longhtml.join(""));
        html.push(longerhtml.join(""));
        html.push("</ul>");
        return html.join("");
    }

    function cleanKuohaos(str) {
        if (str && str.indexOf("(") > 0) {
            str = str.substring(0, str.indexOf("("));
        }
        if (str && str.indexOf("（") > 0) {
            str = str.substring(0, str.indexOf("（"));
        }
        return str;
    }

    function getStockOpts(id, name) {
        if (currentAreaInfo.currentLevel == 3) {
            currentAreaInfo.currentAreaId = id;
            currentAreaInfo.currentAreaName = name;
            if (!page_load) {
                currentAreaInfo.currentTownId = 0;
                currentAreaInfo.currentTownName = "";
            }
        }
        else if (currentAreaInfo.currentLevel == 4) {
            currentAreaInfo.currentTownId = id;
            currentAreaInfo.currentTownName = name;
        }
        //添加20140224
        $('#summary-stock3').removeClass('hover');
        //setCommonCookies(currentAreaInfo.currentProvinceId,currentLocation,currentAreaInfo.currentCityId,currentAreaInfo.currentAreaId,currentAreaInfo.currentTownId,!page_load);
        if (page_load) {
            page_load = false;
        }
        //替换gSC
        var address = currentAreaInfo.currentProvinceName + currentAreaInfo.currentCityName + currentAreaInfo.currentAreaName + currentAreaInfo.currentTownName;
        $("#summary-stock3 .text div").html(currentAreaInfo.currentProvinceName + cleanKuohaos(currentAreaInfo.currentCityName) + cleanKuohaos(currentAreaInfo.currentAreaName) + cleanKuohaos(currentAreaInfo.currentTownName)).attr("title", address);
    }

    function getAreaListcallback(r) {
        currentDom3.html(getAreaLists(r));
        if (currentAreaInfo.currentLevel >= 2) {
            currentDom3.find("a").click(function () {
                if (page_load) {
                    page_load = false;
                }
                if (currentDom3.attr("id") == "stock_area_item3") {
                    currentAreaInfo.currentLevel = 3;
                }
                else if (currentDom3.attr("id") == "stock_town_item3") {
                    currentAreaInfo.currentLevel = 4;
                }
                getStockOpts($(this).attr("data-value"), $(this).html());
            });
            if (page_load) { //初始化加载
                currentAreaInfo.currentLevel = currentAreaInfo.currentLevel == 2 ? 3 : 4;
                if (currentAreaInfo.currentAreaId && new Number(currentAreaInfo.currentAreaId) > 0) {
                    getStockOpts(currentAreaInfo.currentAreaId, currentDom3.find("a[data-value='" + currentAreaInfo.currentAreaId + "']").html());
                }
                else {
                    getStockOpts(currentDom3.find("a").eq(0).attr("data-value"), currentDom3.find("a").eq(0).html());
                }
            }
        }
    }

    function chooseProvince3(provinceId) {

        provinceContainer3.hide();
        // currentAreaInfo.currentLevel = 1;
        currentAreaInfo.currentProvinceId = provinceId;
        currentAreaInfo.currentProvinceName = getNameByIds(provinceId);
        if (!page_load) {
            currentAreaInfo.currentCityId = 0;
            currentAreaInfo.currentCityName = "";
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainer3.eq(0).removeClass("curr").find("em").html(currentAreaInfo.currentProvinceName);
        areaTabContainer3.eq(1).addClass("curr").show().find("em").html("请选择");
        areaTabContainer3.eq(2).hide();
        areaTabContainer3.eq(3).hide();
        cityContainer3.show();
        areaContainer3.hide();
        townaContainer3.hide();
        cityContainer3.html(getAreaLists(provinceId));
        cityContainer3.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#summary-stock3").unbind("mouseout");
            chooseCity3($(this).attr("data-value"), $(this).html());
        });
        /*if (page_load) { //初始化加载
         if (currentAreaInfo.currentCityId && new Number(currentAreaInfo.currentCityId) > 0) {
         chooseCity(currentAreaInfo.currentCityId, cityContainer3.find("a[data-value='" + currentAreaInfo.currentCityId + "']").html());
         }
         else {
         chooseCity(cityContainer3.find("a").eq(0).attr("data-value"), cityContainer3.find("a").eq(0).html());
         }
         }*/
    }

    function chooseCity3(cityId, cityName) {
        provinceContainer3.hide();
        cityContainer3.hide();
        currentAreaInfo.currentLevel = 2;
        currentAreaInfo.currentCityId = cityId;
        currentAreaInfo.currentCityName = cityName;
        if (!page_load) {
            currentAreaInfo.currentAreaId = 0;
            currentAreaInfo.currentAreaName = "";
            currentAreaInfo.currentTownId = 0;
            currentAreaInfo.currentTownName = "";
        }
        areaTabContainer3.eq(1).removeClass("curr").find("em").html(cityName);
        areaTabContainer3.eq(2).addClass("curr").show().find("em").html("请选择");
        areaTabContainer3.eq(3).hide();
        areaContainer3.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
        townaContainer3.hide();
        $('#areaCode3').val(cityId);
        $('#areaName7').val($("#chooseProvince3").text() + "-" + cityName);
        areaContainer3.show().html(getAreaLists(cityId));
        areaContainer3.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //  $("#summary-stock3").unbind("mouseout");
            chooseArea3($(this).attr("data-value"), $(this).html());
        });
        try {
            // getActicity3(cityId);
        } catch (e) {
        }
    }

    function chooseArea3(areaId, areaName) {
        $('#summary-stock3').removeClass('hover');
        $('#areaCode3').val(areaId);
        $('#areaName7').val($("#chooseProvince3").text() + "-" + $("#chooseCity3").text() + "-" + areaName);
        try {
            // getActicity3(areaId);
        } catch (e) {
        }
        /*  provinceContainer3.hide();
         cityContainer3.hide();
         areaContainer3.hide();
         currentAreaInfo.currentLevel = 3;
         currentAreaInfo.currentAreaId = areaId;
         currentAreaInfo.currentAreaName = areaName;
         if (!page_load) {
         currentAreaInfo.currentTownId = 0;
         currentAreaInfo.currentTownName = "";
         }
         areaTabContainer3.eq(2).removeClass("curr").find("em").html(areaName);
         areaTabContainer3.eq(3).addClass("curr").show().find("em").html("请选择");
         townaContainer3.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
         currentDom3 = townaContainer3;
         townaContainer3.show().html(getAreaList(areaId));*/
    }

    var areaTabContainer3 = $("#JD-stock3 .tab li");
    var provinceContainer3 = $("#stock_province_item3");
    var cityContainer3 = $("#stock_city_item3");
    var areaContainer3 = $("#stock_area_item3");
    var townaContainer3 = $("#stock_town_item3");
    var currentDom3 = provinceContainer3;
//当前地域信息
    var currentAreaInfo;

//初始化当前地域信息
    function CurrentAreaInfoInits() {
        currentAreaInfo = {
            "currentProvinceId": 100100,
            "currentProvinceName": "安徽省",
            "currentCityId": 1001001000,
            "currentCityName": "合肥市",
            "currentAreaId": 100100100010001,
            "currentAreaName": "庐阳区",
            "currentTownId": 0,
            "currentTownName": ""
        };
        var ipLoc = getCookies("ipLoc-djd");
        ipLoc = ipLoc ? ipLoc.split("-") : [100100, 1001001000, 0, 0];
        if (ipLoc.length > 0 && ipLoc[0]) {
            currentAreaInfo.currentProvinceId = ipLoc[0];
            currentAreaInfo.currentProvinceName = getNameByIds(ipLoc[0]);
        }
        if (ipLoc.length > 1 && ipLoc[1]) {
            currentAreaInfo.currentCityId = ipLoc[1];
        }
        if (ipLoc.length > 2 && ipLoc[2]) {
            currentAreaInfo.currentAreaId = ipLoc[2];
        }
        if (ipLoc.length > 3 && ipLoc[3]) {
            currentAreaInfo.currentTownId = ipLoc[3];
        }
    }

    var page_load = true;
    (function () {
        $("#summary-stock3").unbind("mouseover").bind("mouseover", function () {
            $('#summary-stock3').addClass('hover');
            $("#summary-stock3 .content,#JD-stock3").show();
        }).find("dl").remove();
        $("#summary-stock3").unbind("mouseout").bind("mouseout", function () {
            $('#summary-stock3').removeClass('hover');
            if ($("#areaName7").val() != "" && $("#areaName7").val() != null) {
                $("#areaName7-error").remove();
                $("#areaName7").removeClass(".error");
            }
            $("#summary-stock3 .content,#JD-stock3").hide();
            // $("#summary-stock3 .content,#JD-stock").show();
        }).find("dl").remove();
        CurrentAreaInfoInits();
        areaTabContainer3.eq(0).find("a").click(function () {

            areaTabContainer3.removeClass("curr");
            areaTabContainer3.eq(0).addClass("curr").show();
            provinceContainer3.show();
            cityContainer3.hide();
            areaContainer3.hide();
            townaContainer3.hide();
            areaTabContainer3.eq(1).hide();
            areaTabContainer3.eq(2).hide();
            areaTabContainer3.eq(3).hide();
        });
        areaTabContainer3.eq(1).find("a").click(function () {
            areaTabContainer3.removeClass("curr");
            areaTabContainer3.eq(1).addClass("curr").show();
            provinceContainer3.hide();
            cityContainer3.show();
            areaContainer3.hide();
            townaContainer3.hide();
            areaTabContainer3.eq(2).hide();
            areaTabContainer3.eq(3).hide();
        });
        areaTabContainer3.eq(2).find("a").click(function () {
            areaTabContainer3.removeClass("curr");
            areaTabContainer3.eq(2).addClass("curr").show();
            provinceContainer3.hide();
            cityContainer3.hide();
            areaContainer3.show();
            townaContainer3.hide();
            areaTabContainer3.eq(3).hide();
        });
        provinceContainer3.find("a").click(function () {
            if (page_load) {
                page_load = false;
            }
            //   $("#summary-stock3").unbind("mouseout");
            chooseProvince3($(this).attr("data-value"));
        }).end();
        chooseProvince3(currentAreaInfo.currentProvinceId);
    })();

    function getCookies(name) {
        var start = document.cookie.indexOf(name + "=");
        var len = start + name.length + 1;
        if ((!start) && (name != document.cookie.substring(0, name.length))) {
            return null;
        }
        if (start == -1) return null;
        var end = document.cookie.indexOf(';', len);
        if (end == -1) end = document.cookie.length;
        return unescape(document.cookie.substring(len, end));

    };


    return {
        init: function () {
            init();
        }
    }
}(jQuery, layui, document)