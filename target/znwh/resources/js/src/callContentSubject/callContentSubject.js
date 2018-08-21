var callContentSubject = function ($, layui) {
    function init() {
        //初始化layui
        layui.use(['table', 'form', 'element'], function () {
            //定义左侧选中的节点id
            var contentSubjectId_search;
            showTree();
            var ajaxParams = {};
            var table = layui.table
                , form = layui.form
                , element = layui.element;

            //展示已知数据
            table.render({
                elem: '#content-subjectmana'
                , url: context + "/callContentSubject/data.do" //数据接口
                , page: true //是否显示分页
                , skin: 'row'//表格风格
                , even: true //开启隔行背景
                , id: 'callContentSubject' //初始化标识id
                , cols: [[ //标题栏
                    {checkbox: true, field: 'id', title: '全选'}
                    , {field: 'name', title: '主题名称'}
                    , {field: 'description', title: '主题描述'}
                    , {title: '操作', align: 'center', toolbar: '#bar-subjectmana'}
                ]]
            });

            /************************监听每行按钮事件****************************/
            table.on('tool(subjectList)', function (obj) {
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent == "delete") { //删除
                    layer.confirm("删除后无法恢复，确定删除吗？", {
                        icon: 3,
                        title: "删除",
                        btn: ["确定", "取消"],
                        btn1: function () {
                            $.ajax({
                                type: "POST",
                                url: context + "/callContentSubject/delete.do",
                                data: {"contentSubjectIds": data.id},
                                success: function (data) {
                                    if (data.status == 0) {
                                        layer.msg(data.message, {time: 2000, icon: 1});
                                        showTree();
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
                    $("#updateSubject").find("[name='id']").val(data.id);
                    $("#updateSubject").find("[name='parentId']").val(data.parentId);
                    $("#updateSubject").find("[name='name']").val(data.name);
                    $("#updateSubject").find("[name='description']").val(data.description);
                    $("#updateSubject").modal("show");
                }
            })

            function setAjaxParam(event) {
                var $form = $(event.target).parents(".search-form");
                var params = $form.serializeArray();
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
                $.each(params, function (key, value) {
                    ajaxParams[value.name] = value.value.trim();
                });
            }

            function clearAjaxParams() {
                ajaxParams = {};
            }

            /************************查询****************************/
            $('.search-btn').on('click', function (event) {
                clearAjaxParams();
                setAjaxParam(event);
                table.reload('callContentSubject', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: ajaxParams
                    }
                });
            });

            /************************刷新table****************************/
            var reloadTable = function () {
                table.reload("callContentSubject", { //此处是上文提到的 初始化标识id
                });
            };

            $("#addNew").on("click", function () {
                 $("textarea,input[ name='name' ] ").val("");
                $("#add-subject").modal("show");
            });
            /************************新增主题****************************/
            $("#commitAdd").on("click", function () {
                var parentId = $("#add-subject").find("#parentId").val();
                if (!parentId) {
                    layer.msg("请选择左侧主题进行添加操作", {time: 2000, icon: 5});
                    return;
                }
                var name=$("#addForm").find("[name='name']").val();
                if(name.trim()==""){
                    layer.msg("主题名称不能为空！", {time: 2000, icon: 5});
                    return;
                }

                var form = $("#addForm").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/callContentSubject/add.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#add-subject").modal("hide");
                            layer.msg(data.message, {time: 2000, icon: 1});
                            showTree();
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

            /************************修改主题****************************/
            $("#commitUpdate").on("click", function () {
                var name=$("#updateSubject").find("[name='name']").val();
                if(name.trim()==""){
                    layer.msg("主题名称不能为空！", {time: 2000, icon: 5});
                    return;
                }

                var form = $("#updateForm").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/callContentSubject/update.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#updateSubject").modal("hide");
                            layer.msg(data.message, {time: 2000, icon: 1});
                            showTree();
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

            /************************批量删除主题模态框****************************/
            $("#deleteMany").on("click", function () {
                var checkStatus = table.checkStatus('callContentSubject'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }

                layer.confirm('删除后无法恢复，确定删除吗？',{
                    icon: 3,
                    title: "删除",
                    btn: ["确定", "取消"],
                    btn1:function() {
                        var ids = "";
                        for (var i in data) {
                            ids += data[i].id + ",";
                        }
                        if (data.length > 0) {
                            $.ajax({
                                type: "POST",
                                url: context + "/callContentSubject/delete.do",
                                data: {"contentSubjectIds": ids},
                                success: function (data) {
                                    if (data.status == 0) {
                                        $("#del-subject").modal("hide");
                                        layer.msg(data.message, {time: 2000, icon: 1});
                                        showTree();
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
                    }
                });
                // $("#del-subject").modal("show");
            });

            // /************************批量删除主题****************************/
            // $("#commitDelete").on("click", function () {
            //     var checkStatus = table.checkStatus('callContentSubject'),
            //         data = checkStatus.data;//获取选中的数据
            //     var ids = "";
            //     for (var i in data) {
            //         ids += data[i].id + ",";
            //     }
            //     $.ajax({
            //         type: "POST",
            //         url: context + "/callContentSubject/delete.do",
            //         data: {"contentSubjectIds": ids},
            //         success: function (data) {
            //             if (data.status == 0) {
            //                 $("#del-subject").modal("hide");
            //                 layer.msg(data.message, {time: 2000, icon: 1});
            //                 showTree();
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

            /************************左侧树状结构****************************/
            var zTreeObj;
            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                treeId: "",
                treeObj: null,
                view: {
                    showLine: true,
                    showIcon: false,
                    selectedMulti: false,
                    dblClickExpand: false,
                    addDiyDom: addDiyDom
                },
                data: {
                    simpleData: {   //简单的数据源，一般开发中都是从数据库里读取，API有介绍，这里只是本地的
                        enable: true,
                        idKey: "id",  //id和pid，
                        pIdKey: "parentId",
                        rootPId: null   //根节点
                    }
                },
                callback: {

                    /********************************* 回调函数。单击节点触发事件*****************************************/
                    onClick: zTreeOnClick
                }
            };

            // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
            function showTree() {
                $.ajax({
                    type: "GET",
                    url: context + "/callContentSubject/tree.do",
                    data: {},
                    success: function (data) {
                        var resu = eval(data.data);
                        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, resu);
                    },
                    error: function (data) {
                        layer.msg("查询主题失败", {time: 2000, icon: 5});
                    }
                });
            }

            /********************************* 单击树节点触发的方法*****************************************/
            function zTreeOnClick(event, treeId, treeNode) {
                contentSubjectId_search = treeNode.id;
                ajaxParams['id'] = treeNode.id;
                table.reload('callContentSubject', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: ajaxParams
                    }
                });
                $("#add-subject").find("#parentId").val(treeNode.id);
            }

            function addDiyDom(treeId, treeNode) {
                var switchObj = $("#" + treeNode.tId + "_switch"),
                    icoObj = $("#" + treeNode.tId + "_ico");
                switchObj.remove();
                icoObj.parent().before(switchObj);
                var spantxt = $("#" + treeNode.tId + "_span").html();
                if (spantxt.length > 8) {
                    spantxt = spantxt.substring(0, 8) + "...";
                    $("#" + treeNode.tId + "_span").html(spantxt);
                }
            };
        });
    };
    return {
        init: function () {
            init();
        }
    }
}(jQuery, layui, document);