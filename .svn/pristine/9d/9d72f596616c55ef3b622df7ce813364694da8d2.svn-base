var category = function ($,layui) {
    function init() {
        showTree();
        // 新增确认
        layui.use(['table','form','element','laypage','layer'], function(){
            var table=layui.table
                ,form=layui.form
                ,element=layui.elemengt
                ,laypage = layui.laypage
                ,layer=layui.layer;

            //展示已知数据
            table.render({
                elem: '#content-category'
                ,url:context +'/callCategory/data.do'  //数据请求路径
                ,id:'category'//初始化标识id
                , cols: [[ //标题栏
                    {type: 'checkbox'}
                    , {field: 'name', title: '分类名称', width: 220,}
                    , {field: 'descri', title: '分类描述', minWidth: 300}
                    , {field: 'right', title: '操作', width: 180, align: 'center', toolbar: '#bar-category'}
                ]]
                ,skin: 'row' //表格风格
                ,even: true
                ,page: true //是否显示分页
                // ,limits: [5, 7, 10]
                //,limit: 5 //每页默认显示的数量
                ,jump: function(obj) {
                    console.log(obj)
                }
            });
            var $ = layui.$, active = {
                reload: function(){
                    var name = $('#keyword');
                    table.reload('category', {
                        where: {
                            key: {
                                name: $.trim(name.val()),
                            }
                        }
                    });
                }
            };
            $('#search').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            //监听工具条
            table.on('tool(demo)', function(obj){
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if(layEvent === 'del'){
                    layer.confirm('删除后无法恢复，确定删除吗？', {
                        icon: 3,
                        title: "删除",
                        btn: ["确定", "取消"],
                        btn1: function(){
                            $.ajax({
                                type: "post",
                                url:context +"/callCategory/delete.do",
                                data: {
                                    "callCategoryIds":data.id
                                },
                                success: function (suc) {
                                    if(suc.status==0){
                                        layer.msg(suc.message,{
                                            time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                            icon: 1
                                        },function(){
                                            window.location.href =context +"/callCategory/list.do"
                                        });
                                    }else{
                                        layer.msg(suc.message, {time: 2000, icon: 5});
                                    }
                                }
                            });
                        }
                    });
                } else if(layEvent === 'edit'){
                    //向模态框中赋值
                    $("#id").val(data.id);
                    $("#name").val(data.name);
                    $("#descri").val(data.descri);
                    $('#edit').modal('show')
                }
            });

            $("#addNew").on("click", function () {
                var parentId=$("#addForm").find("#parentId").val();
                if(parentId=="parentId"){
                    layer.msg("任务分类的节点最多只有四层！", {time: 2000, icon: 5});
                    return;
                }

                $("textarea,input[ type='text' ] ").val("");
                $("#add").modal("show");
            });
            //添加操作
            $('#addSubmit').on('click', function () {
                var parentId=$("#addForm").find("#parentId").val();
                if(parentId==""){
                    layer.msg("请先选择所属任务分类！", {time: 2000, icon: 5});
                    return;
                }
                if(parentId=="parent0"){
                    $("#addForm").find("#parentId").val("");
                }
                var addName=$("#addForm").find("#addName").val();
                if(addName.trim()==""){
                    layer.msg("任务分类名称不能为空！", {time: 2000, icon: 5});
                    return;
                }
                var $form = $("#addForm");
                var params = $form.serialize();
                $.ajax({
                    type: "POST",
                    url: context +"/callCategory/add.do",
                    data: params,
                    success: function (suc) {
                        if(suc.status==0){
                            $("#add").modal("hide");
                            layer.msg(suc.message,{
                                time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                icon: 1
                            },function(){
                                window.location.href =context +"/callCategory/list.do"
                            });
                        }else{
                            layer.msg(suc.message, {time: 2000, icon: 5});
                        }
                    }
                });
            });
            /************************批量删除内容模态框****************************/
            $("#deleteMany").on("click", function () {
                var checkStatus = table.checkStatus('category'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 2000, icon: 5});
                    return;
                }
                layer.confirm('删除后无法恢复，确定删除吗？',{
                    icon: 3,
                    title: "删除",
                    btn: ["确定", "取消"],
                    btn1:function() {
                        var ids = "";
                        for (var i = 0; i < data.length; i++) {
                            ids += data[i].id + ",";
                        }
                        if (data.length > 0) {
                            $.ajax({
                                type: "post",
                                url: context + "/callCategory/delete.do",
                                data: {
                                    "callCategoryIds": ids
                                },
                                success: function (suc) {
                                    if (suc.status == 0) {
                                        layer.msg(suc.message, {
                                            time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                            icon: 1
                                        }, function () {
                                            window.location.href = context + "/callCategory/list.do"
                                        });
                                    } else {
                                        layer.msg(suc.message, {
                                            time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                            icon: 5
                                        }, function () {
                                            window.location.href = context + "/callCategory/list.do"
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
            //修改操作
            $('#updSubmit').on('click', function () {
                var name=$("#updForm").find("#name").val();
                if(name.trim()==""){
                    layer.msg("任务分类名称不能为空！", {time: 2000, icon: 5});
                    return;
                }
                var $form = $("#updForm");
                var params = $form.serialize();
                $.ajax({
                    type: "POST",
                    url: context +"/callCategory/update.do",
                    data: params,
                    success: function (suc) {
                        if(suc.status==0){
                            $("#edit").modal("hide");
                            layer.msg(suc.message,{
                                time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                icon: 1
                            },function(){
                                window.location.href =context +"/callCategory/list.do"
                            });
                        }else{
                            layer.msg(suc.message, {time: 2000, icon: 5});
                        }
                    }
                });
            });
        });

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
        function showTree(){
            $.ajaxSetup({
                cache: false
            })
            $.ajax({
                type: "GET",
                url: context + "/callCategory/findTree.do",
                data: {},
                success: function (data) {
                    var resu = eval(data.data);
                    if(resu==null){
                        /*添加第一条数据*/
                        $("#addForm").find("#parentId").val("parent0");
                    }
                    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, resu);
                }
            });
        }
        /********************************* 单击树节点触发的方法*****************************************/
        function zTreeOnClick(event, treeId, treeNode) {
            var parentId = treeNode.id;
            if(treeNode.level>=4){
                parentId="parentId";
            }
            $("#addForm").find("#parentId").val(parentId);
            var name = treeNode.name;
            layui.use('table', function() {
                var table = layui.table;
                table.reload('category', {
                    where: {
                        key: {
                            parentId: treeNode.id,
                        }
                    }
                });
            });
        }
    };
    return {
        init: function () {
            init();
        }

    }

}(jQuery, layui, document)
