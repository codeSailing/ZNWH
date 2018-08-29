var callContent = function ($, layui) {
    function init() {
        //初始化layui
        layui.use(['table', 'form', 'element', 'upload'], function () {
            //定义ztree对象，用于后面的合并操作
            var zTreeObj;
            var add_tree;
            var update_tree;
            var updateSubject_tree;
            showTree();
            //用于查询的contentSubjectId
            var contentSubjectId_search;
            var ajaxParams = {};
            var table = layui.table
                , form = layui.form
                , element = layui.element
                , upload = layui.upload;
            form.render('radio');
            //展示已知数据
            table.render({
                elem: '#content-subject'
                , url: context + "/callContent/data.do" //数据接口
                , page: true //是否显示分页
                , skin: 'row'//表格风格
                , even: true //开启隔行背景
                , id: 'callContent' //初始化标识id
                , cols: [[ //标题栏
                    {checkbox: true, field: 'id', title: '全选'}
                    , {field: 'contentSubjectName', title: '主题分类', width: 120}
                    , {field: 'title', title: '内容标题', width: 120}
                    , {field: 'detail', title: '内容详情', minWidth: 350, templet: '#detailTpl'}
                    , {field: 'content', title: '内容描述', minWidth: 350}
                    , {
                        field: 'updateTime', title: '更新时间', minWidth: 120,
                        templet: '<div>{{ layui.laytpl.toDateString(d.updateTime,"yyyy-MM-dd HH:mm:ss") }}</div>'
                    }
                    , {title: '操作', width: 200, align: 'center', toolbar: '#bar-subject'}
                ]]
            });

            /************************监听每行按钮事件****************************/
            table.on('tool(contentList)', function (obj) {
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
                                url: context + "/callContent/delete.do",
                                data: {"contentIds": data.id},
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
                    $("#updateContengt").find("[name='id']").val(data.id);
                    $("#update_contentSubjectId").val(data.contentSubjectId);
                    $("#update_contentSubjectName").val(data.contentSubjectName);
                    $("#updateContengt").find("[name='title']").val(data.title);
                    $("#updateContengt").find("[name='content']").val(data.content);
                    $("#update_detail").val("");
                    $("#update_contentType").find("[value='" + data.contentType + "']").prop("checked", true);
                    form.render('radio'); //重新渲染
                    $("#update_detailPack").val(data.detail);
                    if(data.contentType == 0){ //文本
                        $("#update_detail").val(data.detail);
                        $("#update_audioDiv").hide();
                        $("#update_detailDiv").show();
                    }else{ //音频
                        $("#update_detailDiv").hide();
                        $("#update_audioDiv").show();
                    }
                    $("#updateContengt").modal("show");
                } else if (layEvent == "updateSubject") { //修改主题
                    $("#updateSubject_contentId").val(data.id);
                    $("#updateSubject_subjectId").val("");
                    //将内部ztree节点合并并取消选中状态
                    updateSubject_tree.expandAll(false);
                    updateSubject_tree.cancelSelectedNode();
                    $("#updateSubject").modal("show");
                }
            });

            /************************查询参数赋值****************************/
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
            }

            function clearAjaxParams() {
                ajaxParams = {};
            }

            /************************查询****************************/
            $('.btn-search').on('click', function (event) {
                clearAjaxParams();
                setAjaxParam(event);
                ajaxParams['contentSubjectId'] = contentSubjectId_search;
                table.reload('callContent', {
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
                table.reload("callContent", { //此处是上文提到的 初始化标识id
                });
            };

            $("#addNew").on("click", function () {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var nodes = treeObj.getSelectedNodes();
                if (nodes.length == 0) {
                    $("textarea,input[type!='radio']").val("");
                } else {
                    $("#add_contentSubjectId").val(nodes[0].id);
                    $("#add_contentSubjectName").val(nodes[0].name);
                    $("input[ name='title' ] ").val("");
                    $("textarea,input[ name='title' ] ").val("");
                }
                $("#addContengt").modal("show");
            });

            /************************新增内容****************************/
            $("#commitAdd").on("click", function () {
                var contentSubjectId = $("#addForm").find("#add_contentSubjectId").val();
                if (contentSubjectId == "") {
                    layer.msg("请先选择主题分类！", {time: 2000, icon: 5});
                    return;
                }
                var title = $("#addForm").find("#title").val();
                if (title.trim() == "") {
                    layer.msg("内容标题不能为空！", {time: 2000, icon: 5});
                    return;
                }
                //判断新增的是文本类的内容还是音频内容
                var contentType = $("#contentType").find(":checked").val();
                if (contentType == 0) { //文本
                    var detail = $("#addForm").find("#detail").val();
                    if (detail == "") {
                        layer.msg("内容详情不能为空！", {time: 2000, icon: 5});
                        return;
                    }
                    //往公共的内容容器中添加数据
                    $("#addForm").find("#detailPack").val(detail);
                    addContent();
                } else { //音频
                    var file = $("#addForm").find(".layui-upload-choose").text();
                    if (file == "") {
                        layer.msg("亲，请上传音频文件", {time: 2000, icon: 5});
                        return;
                    }
                    $("#addForm").find("#commitAudio").click();
                }
            });

            //新增内容执行的方法
            function addContent() {
                var form = $("#addForm").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/callContent/save.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#addContengt").modal("hide");
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
            }

            /************************修改内容****************************/
            $("#commitUpdate").on("click", function () {
                var title = $("#updateForm").find("[name='title']").val();
                if (title.trim() == "") {
                    layer.msg("内容标题不能为空！", {time: 2000, icon: 5});
                    return;
                }
                //判断修改后的是文本类的内容还是音频内容
                var contentType = $("#update_contentType").find(":checked").val();
                if (contentType == 0) { //文本
                    var detail = $("#updateForm").find("#update_detail").val();
                    if (detail == "") {
                        layer.msg("内容详情不能为空！", {time: 2000, icon: 5});
                        return;
                    }
                    //往公共的内容容器中添加数据
                    $("#updateForm").find("#update_detailPack").val(detail);
                    updateContent();
                } else { //音频
                    var file = $("#updateForm").find(".layui-upload-choose").text();
                    if (file == "") {
                        layer.msg("亲，请上传音频文件", {time: 2000, icon: 5});
                        return;
                    }
                    $("#updateForm").find("#update_commitAudio").click();
                }
            });

            function updateContent(){
                var form = $("#updateForm").serialize();
                $.ajax({
                    type: "POST",
                    url: context + "/callContent/save.do",
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            $("#updateContengt").modal("hide");
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
            }

            /************************批量删除内容模态框****************************/
            $("#deleteMany").on("click", function () {
                var checkStatus = table.checkStatus('callContent'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                // $("#deleteContengt").modal("show");

                layer.confirm('删除后无法恢复，确定删除吗？', {
                    icon: 3,
                    title: "删除",
                    btn: ["确定", "取消"],
                    btn1: function () {
                        var ids = "";
                        for (var i = 0; i < data.length; i++) {
                            ids += data[i].id + ",";
                        }
                        if (data.length > 0) {
                            $.ajax({
                                type: "post",
                                url: context + "/callContent/delete.do",
                                data: {
                                    "contentIds": ids
                                },
                                success: function (data) {
                                    if (data.status == 0) {
                                        layer.msg(data.message, {
                                            time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                            icon: 1
                                        }, function () {
                                            window.location.href = context + "/callContent/page.do"
                                        });
                                    } else {
                                        layer.msg(data.message, {
                                            time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                            icon: 5
                                        }, function () {
                                            window.location.href = context + "/callContent/page.do"
                                        });
                                    }
                                }
                            });
                        } else {
                            layer.msg("请选择要删除的任务分类！", {time: 2000, icon: 5});
                        }
                    }
                });
            });

            /************************批量删除内容****************************/
            // $("#commitDelete").on("click", function () {
            //     var checkStatus = table.checkStatus('callContent'),
            //         data = checkStatus.data;//获取选中的数据
            //     var ids = "";
            //     for (var i in data) {
            //         ids += data[i].id + ",";
            //     }
            //     $.ajax({
            //         type: "POST",
            //         url: context + "/callContent/delete.do",
            //         data: {"contentIds": ids},
            //         success: function (data) {
            //             if (data.status == 0) {
            //                 $("#deleteContengt").modal("hide");
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

            /************************修改主题****************************/
            $("#commitUpdateSubject").on("click", function () {
                var contentIds = $("#updateSubject_contentId").val();
                var subjectId = $("#updateSubject_subjectId").val();
                if (!subjectId) {
                    layer.msg("请选择主题", {time: 2000, icon: 5});
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: context + "/callContent/updateSubject.do",
                    data: {"contentIds": contentIds, "subjectId": subjectId},
                    success: function (data) {
                        if (data.status == 0) {
                            $("#updateSubject").modal("hide");
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

            /************************批量修改主题****************************/
            $("#updateSubjectMany").on("click", function () {
                var checkStatus = table.checkStatus('callContent'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length <= 0) {
                    layer.msg("请选择需要修改主题的内容", {time: 2000, icon: 5});
                    return;
                }
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                $("#updateSubject_contentId").val(ids);
                $("#updateSubject_subjectId").val("");
                //将内部ztree节点合并并取消选中状态
                updateSubject_tree.expandAll(false);
                updateSubject_tree.cancelSelectedNode();
                $("#updateSubject").modal("show");
            });

            /************************左侧树状结构****************************/
                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                    treeId: "",
                    treeObj: null,
                    view: {
                        showLine: true,
                        showIcon: false,
                        selectedMulti: false,
                        dblClickExpand: false
                        // addDiyDom: addDiyDom
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
                        //展示左侧树状
                        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, resu);
                        //展示新增页面树状
                        add_tree = $.fn.zTree.init($("#add_tree"), setting, resu);
                        //展示修改页面树状
                        update_tree = $.fn.zTree.init($("#update_tree"), setting, resu);
                        //展示修改主题页面树状
                        updateSubject_tree = $.fn.zTree.init($("#updateSubject_tree"), setting, resu);
                    },
                    error: function (data) {
                        layer.msg("查询主题失败", {time: 2000, icon: 5});
                    }
                });
            }

            /********************************* 单击树节点触发的方法*****************************************/
            function zTreeOnClick(event, treeId, treeNode) {
                var id = treeNode.id;
                var name = treeNode.name;
                //判断是点击左侧树节点还是点击添加页面的树节点
                var addFlag = $("#addContengt").is(":hidden");
                var updateFlag = $("#updateContengt").is(":hidden");
                var updateSubjectFlag = $("#updateSubject").is(":hidden");
                if (!addFlag) { //点击添加页面的树节点
                    //需要给点击的节点信息添加到输入框中
                    $("#add_contentSubjectId").val(id);
                    $("#add_contentSubjectName").val(name);
                    hideMenu();
                } else if (!updateFlag) { //点击修改页面的树节点
                    //需要给点击的节点信息添加到输入框中
                    $("#update_contentSubjectId").val(id);
                    $("#update_contentSubjectName").val(name);
                    hideMenu_update();
                } else if (!updateSubjectFlag) { //点击修改主题页面的树节点
                    $("#updateSubject_subjectId").val(id);
                } else { //点击左侧树状进行查询操作
                    $("#add_contentSubjectId").val(id);
                    $("#add_contentSubjectName").val(name);
                    //对全局变量赋值
                    contentSubjectId_search = treeNode.id;
                    ajaxParams['contentSubjectId'] = id;
                    table.reload('callContent', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        , where: {
                            key: ajaxParams
                        }
                    });
                }
            }

            /************************添加模块下拉树div****************************/
            showAddTree = function () {
                $("#add_content").slideDown("fast");
                $("#addContengt").bind("mousedown", onBodyDown);
            };
            onBodyDown = function (event) {
                if (!(event.target.id == "add_content" || $(event.target).parents("#add_content").length > 0)) {
                    hideMenu();
                }
            };
            hideMenu = function () {
                $("#add_content").fadeOut("fast");
                $("#addContengt").unbind("mousedown", onBodyDown);
            };

            /************************修改模块下拉树div****************************/
            showUpdateTree = function () {
                $("#update_content").slideDown("fast");
                $("#updateContengt").bind("mousedown", onBodyDown_update);
            };
            onBodyDown_update = function (event) {
                if (!(event.target.id == "update_content" || $(event.target).parents("#update_content").length > 0)) {
                    hideMenu_update();
                }
            };
            hideMenu_update = function () {
                $("#update_content").fadeOut("fast");
                $("#updateContengt").unbind("mousedown", onBodyDown_update);
            };

            /************************新增内容模块选择文本类型****************************/
            form.on('radio(contentType)', function (data) {
                var checked_value = data.value;
                if (checked_value == 0) { //选择的是文本
                    $("#audioDiv").hide();
                    $("#detailDiv").show();
                } else { //选择的是音频
                    $("#detailDiv").hide();
                    $("#audioDiv").show();
                }
            });

            /************************修改内容模块选择文本类型****************************/
            form.on('radio(update_contentType)', function (data) {
                var checked_value = data.value;
                if (checked_value == 0) { //选择的是文本
                    $("#update_audioDiv").hide();
                    $("#update_detailDiv").show();
                } else { //选择的是音频
                    $("#update_detailDiv").hide();
                    $("#update_audioDiv").show();
                }
            });

            /************************新增页面上传音频文件****************************/
            upload.render({
                elem: '#audioFile' //“选择文件”按钮的ID
                , url: context + '/callContent/saveAudio.do' //后台接收地址
                , auto: false //不自动上传设置
                , accept: 'audio' //允许上传的文件类型
                ,exts: 'pcm|raw' //设置智能上传格式
                // , size: 5000 //最大允许上传的文件大小
                // , multiple: true //设置是否多个文件上传
                , bindAction: '#commitAudio' //“上传”按钮的ID
                , done: function (res) { //上传成功后执行的方法
                    if (res.status == 0) {
                        //往公共的内容容器中添加数据
                        $("#addForm").find("#detailPack").val(res.data.fileUrl);
                        if ($("#addForm").find("#detailPack").val() == "") {
                            layer.msg("亲，请上传音频文件", {time: 2000, icon: 5});
                            return;
                        }
                        addContent();
                    } else {
                        layer.msg("亲，上传音频出了点问题哟", {time: 2000, icon: 5});
                    }
                }
            });

            /************************修改页面上传音频文件****************************/
            upload.render({
                elem: '#update_audioFile' //“选择文件”按钮的ID
                , url: context + '/callContent/saveAudio.do' //后台接收地址
                , auto: false //不自动上传设置
                , accept: 'audio' //允许上传的文件类型
                ,exts: 'pcm|raw' //设置智能上传格式
                // , size: 5000 //最大允许上传的文件大小
                // , multiple: true //设置是否多个文件上传
                , bindAction: '#update_commitAudio' //“上传”按钮的ID
                , done: function (res) { //上传成功后执行的方法
                    if (res.status == 0) {
                        //往公共的内容容器中添加数据
                        $("#updateForm").find("#update_detailPack").val(res.data.fileUrl);
                        if ($("#updateForm").find("#update_detailPack").val() == "") {
                            layer.msg("亲，请上传音频文件", {time: 2000, icon: 5});
                            return;
                        }
                        updateContent();
                    } else {
                        layer.msg("亲，上传音频出了点问题哟", {time: 2000, icon: 5});
                    }
                }
            });

            /************************播放音频文件****************************/
            playAudio = function (id) {
                var audio = document.getElementById(id);
                audio.play();
            }
            
            closeAudio = function (id) {
                var audio = document.getElementById(id);
                audio.pause();
                audio.load();
            }
        });
    };
    return {
        init: function () {
            init();
        }
    }
}(jQuery, layui, document);