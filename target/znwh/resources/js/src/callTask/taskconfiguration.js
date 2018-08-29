var taskconfiguration = function ($, layui) {
    function init() {
        showTree();
        // showSubjectTree()
        // showCustomerGroupTree();
        // showCallFlowTree();

        //HH:mm:ss
        var getTime = function (time) {//毫秒
            var d = new Date(time);
            var month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : '' + (d.getMonth() + 1);
            var day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate();
            var huors = d.getHours() < 10 ? '0' + d.getHours() : '' + d.getHours();
            var minutes = d.getMinutes() < 10 ? '0' + d.getMinutes() : '' + d.getMinutes();
            var seconds = d.getSeconds() < 10 ? '0' + d.getSeconds() : '' + d.getSeconds();
            return huors + ':' + minutes + ":" + seconds;
        };
        layui.use(['table', 'form', 'element', 'laypage', 'laydate', 'layer'], function () {
            var table = layui.table
                , form = layui.form
                , element = layui.element
                , laypage = layui.laypage
                , laydate = layui.laydate
                , layer = layui.layer;
            var ajaxParams = {};
            var editData;
            //展示已知数据
            table.render({
                elem: '#content-configuration'
                , url: context + '/callTask/data.do'  //数据请求路径
                , cols: [[ //标题栏
                    {checkbox: true, field: 'id', title: '全选'}
                    , {field: 'callCategoryName', title: '任务分类', width: 100, templet: '#category'}
                    , {field: 'name', title: '任务名称', width: 140,}
                    , {field: 'planType', title: '任务类型', width: 140, templet: '#planType'}
                    , {field: 'executeBeginDate', title: '执行时间', width: 300, templet: '#period'}
                    // , {field: 'callContentSubjectName', title: '外呼主题', width: 140, templet: '#contentSubject'}
                    , {field: 'callFlowName', title: '外呼会话流程', width: 140, templet: '#callFlow'}
                    , {field: 'status', title: '状态', width: 100, templet: '#status'}
                    , {field: 'right', title: '操作', width: 220, align: 'center', toolbar: '#bar-configuration'}
                ]]
                , skin: 'row' //表格风格
                , even: true
                , page: true //是否显示分页
                , id: 'callTask' //初始化标识id
                // ,limits: [5, 7, 10]
                //,limit: 5 //每页默认显示的数量
                , jump: function (obj) {
                    console.log(obj)
                }
            });
            var $ = layui.$, active = {
                reload: function () {
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

            form.verify({
                name: function (value, item) { //value：表单的值、item：表单的DOM对象
                    if (value == '') {
                        return '名称不能为空';
                    }
                    if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                        return '名称不能有特殊字符';
                    }
                    if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                        return '名称首尾不能出现下划线\'_\'';
                    }
                    var regStr = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
                    if (regStr.test(value)) {
                        return '不能输入表情符号！';
                    }
                },
                callContentSubject: function (value, item) {
                    if (value == null || value == '') {
                        return '请选择主题！';
                    }
                },
                customerGroup: function (value, item) {
                    if (value == null || value == '') {
                        return '请选择客户组！';
                    }
                },
                callFlowName: function (value, item) {
                    if (value == null || value == '') {
                        return '请选择流程！';
                    }
                },
                callcjNumber: function (value, item) {
                    if (value == null || value == '') {
                        return '请输入拨号出局码！';
                    }
                    var regStr = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
                    if (regStr.test(value)) {
                        return '不能输入表情符号！';
                    }
                },
                execDate: function (value, item) {
                    var executeOneTime = $('#add-planType-one').is(":hidden");
                    var executeTime = $('#add-planType-two').is(":hidden");
                    var modifyexecuteOneTime = $('#modify-planType-one').is(":hidden");
                    var modifyexecuteTime = $('#modify-planType-two').is(":hidden");
                    if (!executeOneTime) {//一次性
                        var executeDate = $('#Execdate').val();
                        var time = $('#ExectimeOne').val();
                        if (executeDate == '') {
                            return '日期不能为空！';
                        }
                    }
                    if (!executeTime) {//周期性
                        var executeBeginDate = $('#executeBeginDate').val();
                        var executeEndDate = $('#executeEndDate').val();
                        var time = $('#ExectimeTwo').val();
                        if (executeBeginDate == '' || executeEndDate == '') {
                            return '日期不能为空！';
                        }
                    }
                    //变计划
                    if (!modifyexecuteOneTime) {//一次性
                        var executeDate = $('#modifyExecdate').val();
                        var time = $('#modifyExectimeOne').val();
                        if (executeDate == '') {
                            return '日期不能为空！';
                        }
                    }
                    if (!modifyexecuteTime) {//周期性
                        var executeBeginDate = $('#modifyexecuteBeginDate').val();
                        var executeEndDate = $('#modifyexecuteEndDate').val();
                        var time = $('#modifyExectimeTwo').val();
                        // if(executeBeginDate == '' || executeEndDate == '' || time == ''){
                        //     return '日期必填！';
                        // }
                        if (executeBeginDate == '' || executeEndDate == '') {
                            return '日期不能为空！';
                        }
                    }
                },
                exectime: function (value, item) {
                    var executeOneTime = $('#add-planType-one').is(":hidden");
                    var executeTime = $('#add-planType-two').is(":hidden");
                    var modifyexecuteOneTime = $('#modify-planType-one').is(":hidden");
                    var modifyexecuteTime = $('#modify-planType-two').is(":hidden");
                    if (!executeOneTime) {//一次性
                        var executeDate = $('#Execdate').val();
                        var time = $('#ExectimeOne').val();
                        if (time == '') {
                            return '时间不能为空！';
                        }
                    }
                    if (!executeTime) {//周期性
                        var executeBeginDate = $('#executeBeginDate').val();
                        var executeEndDate = $('#executeEndDate').val();
                        var time = $('#ExectimeTwo').val();
                        if (time == '') {
                            return '时间不能为空！';
                        }
                    }
                    //变计划
                    if (!modifyexecuteOneTime) {//一次性
                        var executeDate = $('#modifyExecdate').val();
                        var time = $('#modifyExectimeOne').val();
                        if (time == '') {
                            return '时间不能为空！';
                        }
                    }
                    if (!modifyexecuteTime) {//周期性
                        var executeBeginDate = $('#modifyexecuteBeginDate').val();
                        var executeEndDate = $('#modifyexecuteEndDate').val();
                        var time = $('#modifyExectimeTwo').val();
                        // if(executeBeginDate == '' || executeEndDate == '' || time == ''){
                        //     return '日期必填！';
                        // }
                        if (time == '') {
                            return '时间不能为空！';
                        }
                    }
                },
                descri: function (value, item) {
                    var regStr = /[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF][\u200D|\uFE0F]|[\uD83C|\uD83D|\uD83E][\uDC00-\uDFFF]|[0-9|*|#]\uFE0F\u20E3|[0-9|#]\u20E3|[\u203C-\u3299]\uFE0F\u200D|[\u203C-\u3299]\uFE0F|[\u2122-\u2B55]|\u303D|[\A9|\AE]\u3030|\uA9|\uAE|\u3030/ig;
                    if (regStr.test(value)) {
                        return '不能输入表情符号！';
                    }
                }
                //我们既支持上述函数式的方式，也支持下述数组的形式
                //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
                , pass: [
                    /^[\S]{6,12}$/
                    , '密码必须6到12位，且不能出现空格'
                ]
                , num: function (value, item) {
                    // console.log(item);
                    var itemName = item.parentElement.parentElement.innerText.replace('：', '').replace('*', '')
                    if (value == null || value == '') {
                        return '请输入' + itemName + '！';
                    }
                    var reg = new RegExp("[^0-9]", "g");
                    if (reg.test(value)) {
                        return '只能输入整数！';
                    }
                }
            });
            /************************刷新table****************************/
            var reloadTable = function () {
                table.reload("callTask", { //此处是上文提到的 初始化标识id
                });
            };

            $('#search').on('click', function () {
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
            laydate.render({
                elem: '#time-horizon'
                , range: true
            });

            // 新建任务
            laydate.render({
                elem: '#ExectimeOne'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#ExectimeTwo'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#Execdate'
                , format: 'yyyy-MM-dd'
            });
            var executeBeginDate = laydate.render({
                elem: '#executeBeginDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    endMax = executeEndDate.config.max;
                    executeEndDate.config.min = date;
                    executeEndDate.config.min.month = date.month -1;
                }
            });
            var executeEndDate = laydate.render({
                elem: '#executeEndDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    if($.trim(value) == ''){
                        var curDate = new Date();
                        date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                    }
                    executeBeginDate.config.max = date;
                    executeBeginDate.config.max.month = date.month -1;
                }
            });
            //修改时间弹框
            laydate.render({
                elem: '#upExectimeOne'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#upExectimeTwo'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#upExecdate'
                , format: 'yyyy-MM-dd'
            });
            var upexecuteBeginDate = laydate.render({
                elem: '#upexecuteBeginDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    endMax = upexecuteEndDate.config.max;
                    upexecuteEndDate.config.min = date;
                    upexecuteEndDate.config.min.month = date.month -1;
                }
            });
            var upexecuteEndDate = laydate.render({
                elem: '#upexecuteEndDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    if($.trim(value) == ''){
                        var curDate = new Date();
                        date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                    }
                    upexecuteBeginDate.config.max = date;
                    upexecuteBeginDate.config.max.month = date.month -1;
                }
            });
            //变计划时间弹框
            laydate.render({
                elem: '#modifyExectimeOne'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#modifyExectimeTwo'
                , type: 'time'
                , format: 'HH:mm:ss'
            });
            laydate.render({
                elem: '#modifyExecdate'
                , format: 'yyyy-MM-dd'
            });
            var modifyexecuteBeginDate = laydate.render({
                elem: '#modifyexecuteBeginDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    endMax = modifyexecuteEndDate.config.max;
                    modifyexecuteEndDate.config.min = date;
                    modifyexecuteEndDate.config.min.month = date.month -1;
                }
            });
            var modifyexecuteEndDate = laydate.render({
                elem: '#modifyexecuteEndDate'
                , format: 'yyyy-MM-dd'
                , done:function(value,date){
                    if($.trim(value) == ''){
                        var curDate = new Date();
                        date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
                    }
                    modifyexecuteBeginDate.config.max = date;
                    modifyexecuteBeginDate.config.max.month = date.month -1;
                }
            });

            //弹出变计划
            $('#plan').on('click', function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                var distory = true;
                $.each(data, function (e, d) {
                    if (d.status == 1 && d.type == 0) {
                        distory = false;
                    }
                });
                if (distory) {
                    $("#planchange").modal("show");
                } else {
                    layer.msg("存在正在执行的任务，暂时无法批量变计划！", {time: 3000, icon: 5});
                    return;
                }
            });
            //添加新建
            $('.btn-add').click(function () {
                $('#right-content').css("display", "none");
                $('#add-task').css("display", "block");
            });

            //修改提交
            form.on('submit(update)', function (data) {
                // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                var form = $("#up-form").serialize();
                // console.log(form)
                $.ajax({
                    url: context + '/callTask/update.do',
                    type: 'post',
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            layer.msg('修改成功！', {time: 4000, icon: 1});
                            // $('#right-content').css("display", "block");
                            $('#edit-task').modal("hide");
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 4000, icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg('修改失败！请稍后重试！', {time: 4000, icon: 5});
                        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                    }
                });
                // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });

            //新增提交
            form.on('submit(add)', function (data) {
                // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                var form = $("#add-form").serialize();
                $.ajax({
                    url: context + '/callTask/add.do',
                    type: 'post',
                    data: form,
                    success: function (data) {
                        if (data.status == 0) {
                            layer.msg('保存成功！', {time: 4000, icon: 1});
                            $('#right-content').css("display", "block");
                            $('#add-task').css("display", "none");
                            // console.log(data.data.id);
                            $('#taskId').val(data.data.id);
                            reloadTable();
                            setTimeout(function () {
                                addHref()
                            }, 1000);
                        } else {
                            layer.msg(data.message, {time: 4000, icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg('新建失败！请稍后重试！', {time: 4000, icon: 5});
                        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                    }
                });
            });

            //变计划提交
            form.on('submit(modify)', function (data) {
                // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                // console.log(ids);
                var planType = $("input[id='modifyplanType']:checked").val();
                var params = {
                    ids: ids,
                    executeBeginDate: '',
                    executeEndDate: '',
                    executeTime: '',
                    planType: planType
                };
                if (planType == 0) {//一次性
                    params.executeBeginDate = $('#modifyExecdate').val();
                    params.executeEndDate = $('#modifyExecdate').val();
                    params.executeTime = $('#modifyExectimeOne').val();
                } else {
                    params.executeBeginDate = $('#modifyexecuteBeginDate').val();
                    params.executeEndDate = $('#modifyexecuteEndDate').val();
                    params.executeTime = $('#modifyExectimeTwo').val();
                }
                $.ajax({
                    url: context + '/callTask/modifyPlan.do',
                    type: 'post',
                    data: params,
                    success: function (data) {
                        if (data.status == 0) {
                            layer.msg('变计划成功！', {time: 4000, icon: 1});
                            $("#planchange").modal("hide");
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 4000, icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg('变计划失败！请稍后重试！', {time: 4000, icon: 5});
                        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                    }
                });
            });


            //新建关闭
            $('.close-div').click(function () {
                $('#right-content').css("display", "block");
                $('#add-task').css("display", "none");
            });
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

            //选择一次性/周期性 改变时间选择框
            form.on('radio(planType)', function (data) {
                // console.log(data)
                if (data.value == 1) {//周期性
                    $("#add-planType-two").show();
                    $("#add-planType-one").hide();
                } else if (data.value == 0) {//一次性
                    $("#add-planType-two").hide();
                    $("#add-planType-one").show();
                }
            });
            //修改任务 选择一次性/周期性 改变时间选择框
            form.on('radio(uplanType)', function (data) {
                // console.log(data)
                if (data.value == 1) {//周期性
                    $("#up-planType-two").show();
                    $("#up-planType-one").hide();
                } else if (data.value == 0) {//一次性
                    $("#up-planType-two").hide();
                    $("#up-planType-one").show();
                }
            });

            //选择一次性/周期性 改变时间选择框
            form.on('radio(modifylanType)', function (data) {
                // console.log(data)
                if (data.value == 1) {//周期性
                    $("#modify-planType-two").show();
                    $("#modify-planType-one").hide();
                } else if (data.value == 0) {//一次性
                    $("#modify-planType-two").hide();
                    $("#modify-planType-one").show();
                }
            });

            //弹出选流程
            $('#choose').on('click', function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                var distory = true;
                $.each(data, function (e, d) {
                    if (d.status == 1 && d.type == 0) {
                        distory = false;
                    }
                });
                if (distory) {
                    $("#fiowoption").modal("show");
                } else {
                    layer.msg("存在正在执行的任务，暂时无法批量选流程！", {time: 3000, icon: 5});
                    return;
                }
            });

            //批量选流程提交
            form.on('submit(choose)', function (data) {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                var callFlowId = $('#modify_callFlowId').val();
                var callFlowName = $('#modify_callFlowName').val();
                $.ajax({
                    url: context + '/callTask/chooseCallFollow.do',
                    type: 'get',
                    data: {ids: ids, callFlowId: callFlowId, callFlowName: callFlowName},
                    success: function (data) {
                        if (data.status == 0) {
                            layer.msg('选流程成功！', {time: 4000, icon: 1});
                            $("#fiowoption").modal("hide");
                            reloadTable();
                        } else {
                            layer.msg('选流程失败', {time: 4000, icon: 5});
                        }
                    },
                    error: function () {
                        layer.msg('选流程失败！请稍后重试！', {time: 4000, icon: 5});
                        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                    }
                });
            });

            //监听工具条
            table.on('tool(callTaskList)', function (obj) {
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'del') {
                    var distory = true;
                    // $.each(data,function (e,d) {
                    if (data.status == 1 && data.type == 0) {
                        distory = false;
                    }
                    // });
                    if (distory) {
                        layer.confirm("删除后无法恢复，确定删除吗？", {
                            icon: 3,
                            title: "删除",
                            btn: ["确定", "取消"],
                            btn1: function () {
                                $.ajax({
                                    type: "get",
                                    url: context + "/callTask/delete.do",
                                    data: {"ids": data.id},
                                    success: function (data) {
                                        if (data.status == 0) {
                                            layer.msg('删除成功！', {time: 4000, icon: 1});
                                            // showTree();
                                            reloadTable();
                                        } else {
                                            layer.msg(data.message, {time: 4000, icon: 5});
                                        }
                                    },
                                    error: function (data) {
                                        layer.msg("删除失败", {time: 4000, icon: 5});
                                    }
                                });
                            }
                        });
                    } else {
                        layer.msg("任务正在执行，暂时无法删除！", {time: 3000, icon: 5});
                        return;
                    }
                } else if (layEvent === 'edit') {
                    editData = data;
                    var distory = true;
                    // $.each(data,function (e,d) {
                    if (data.status == 1 && data.type == 0) {
                        distory = false;
                    }
                    // });
                    if (distory) {
                        $("#up-form").find("[name='id']").val(data.id);
                        $("#up-form").find("[name='name']").val(data.name);
                        // $("#up-form").find("input[type='radio'][name='uptype']").val(data.status);
                        if (data.planType == 1) {//周期性
                            $("#up-planType-two").show();
                            $("#up-planType-one").hide();
                        } else if (data.planType == 0) {//一次性
                            $("#up-planType-two").hide();
                            $("#up-planType-one").show();
                        }
                        $('[id=uptype]').each(function (i, item) {
                            if ($(item).val() == data.type) {
                                $(item).prop('checked', true);
                                form.render();
                            }
                        });

                        $("#up-form").find("[name='callCategoryId']").val(data.callCategoryId);
                        $("#up-form").find("[name='callContentSubjectName']").val(data.callContentSubjectName);
                        $("#up-form").find("[name='callContentSubjectId']").val(data.callContentSubjectId);
                        $("#up-form").find("[name='customerGroupId']").val(data.customerGroupId);
                        $("#up-form").find("[name='customerGroupName']").val(data.customerGroupName);
                        $('[id=upplanType]').each(function (i, item) {
                            if ($(item).val() == data.planType) {
                                $(item).prop('checked', true);
                                form.render();
                            }
                        });

                        if (data.planType == 0) {
                            $("#up-form").find("[name='executeOneDate']").val(data.executeBeginDate);
                            $("#up-form").find("[name='executeOneTime']").val(getTime(data.executeTime));
                        } else if (data.planType == 1) {
                            $("#up-form").find("[name='executeBeginDate']").val(data.executeBeginDate);
                            $("#up-form").find("[name='executeEndDate']").val(data.executeEndDate);
                            $("#up-form").find("[name='executeTime']").val(getTime(data.executeTime));
                        }
                        $("#up-form").find("[name='callFlowId']").val(data.callFlowId);
                        $("#up-form").find("[name='callFlowName']").val(data.callFlowName);
                        $("#up-form").find("[name='callcjNumber']").val(data.callcjNumber);
                        $("#up-form").find("[name='tryCount']").val(data.tryCount);
                        $("#up-form").find("[name='basicNum']").val(data.basicNum);

                        $('[id=upcallNumShowType]').each(function (i, item) {
                            if ($(item).val() == data.callNumShowType) {
                                $(item).prop('checked', true);
                                form.render();
                            }
                        });
                        // $("#up-form").find("[name='callNumShowType']").val(data.callNumShowType);
                        $("#up-form").find("[name='callNumShow']").val(data.callNumShow);
                        $("#up-form").find("[name='descri']").val(data.descri);
                        $("#edit-task").modal('show');
                    } else {
                        layer.msg("存在正在执行的任务，暂时无法修改！", {time: 3000, icon: 5});
                        return;
                    }
                } else if (layEvent === 'detail') {
                    $("#detail-form").find("[name='detailname']").text(data.name);
                    $("#detail-form").find("[name='detailtaskId']").val(data.id);
                    if(data.type!=1){
                        if (data.status == 0) {
                            $("#detail-form").find("[name='detailstatus']").text("待执行");
                        } else if (data.status == 1) {
                            $("#detail-form").find("[name='detailstatus']").text("执行中");
                        } else if (data.status == 2) {
                            $("#detail-form").find("[name='detailstatus']").text("已完成");
                        }
                    }else{
                        $("#detail-form").find("[name='detailstatus']").text("已废弃");
                    }


                    $("#detail-form").find("[name='detailcallcategory']").text(data.callCategoryName);
                    $("#detail-form").find("[name='detailsubject']").val(data.callContentSubjectName);
                    $("#detail-form").find("[name='detailcustomer']").text(data.customerGroupName);
                    //
                    if (data.planType == 0) {
                        $("#detail-form").find("[name='detailplanetype']").text('一次性');
                        $("#detail-form").find("[name='detailexecutime']").text(data.executeBeginDate + " " + getTime(data.executeTime));
                    } else if (data.planType == 1) {
                        $("#detail-form").find("[name='detailplanetype']").text('周期性');
                        $("#detail-form").find("[name='detailexecutime']").text(data.executeBeginDate + "至" + data.executeEndDate + " " + getTime(data.executeTime));
                    }
                    // $("#detail-form").find("[name='detailflow']").text(data.callFlow.name);
                    $("#detail-form").find("[name='detailflow']").text(data.callFlowName);
                    $("#detail-form").find("[name='detailcjnum']").text(data.callcjNumber);
                    $("#detail-form").find("[name='detailtrycount']").text(data.tryCount);
                    $("#detail-form").find("[name='detailbasicnum']").text(data.basicNum);

                    $("#detail-form").find("[name='detailshownum']").text(data.callNumShow);
                    $("#detail-form").find("[name='detaildescri']").val(data.descri);
                    $("#detail-task").modal('show');
                } else if (layEvent === 'destroyIt') {
                    var content = $(this).attr('content');
                    if (data.status == 1 && data.type == 0) {
                        layer.msg('任务正在执行中，暂时不能废弃！', {time: 4000, icon: 5});
                    } else {
                        layer.confirm("确认启用/废弃吗？", {
                            btn: ["确定", "取消"],
                            btn1: function () {
                                $.ajax({
                                    type: "get",
                                    url: context + "/callTask/modifyType.do",
                                    data: {"ids": data.id, type: content},
                                    success: function (data) {
                                        if (data.status == 0) {
                                            layer.msg(data.message, {time: 4000, icon: 1});
                                            // showTree();
                                            reloadTable();
                                        } else {
                                            layer.msg(data.message, {time: 4000, icon: 5});
                                        }
                                    },
                                    error: function (data) {
                                        layer.msg("操作失败", {time: 4000, icon: 5});
                                    }
                                });
                            }
                        });
                    }
                }
            });

            /************************批量删除内容模态框****************************/
            $("#deleteMany").on("click", function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                var distory = true;
                $.each(data, function (e, d) {
                    if (d.status == 1 && d.type == 0) {
                        distory = false;
                    }
                });
                if (distory) {
                    // $("#del-taskcontengt").modal("show");

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
                                    type: "get",
                                    url: context + "/callTask/delete.do",
                                    data: {
                                        "ids": ids
                                    },
                                    success: function (data) {
                                        if (data.status == 0) {
                                            layer.msg(data.message, {
                                                time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                                icon: 1
                                            }, function () {
                                                window.location.href = context + "/callTask/list.do"
                                            });
                                        } else {
                                            layer.msg(data.message, {
                                                time: 2000, //2秒关闭（如果不配置，默认是3秒）
                                                icon: 5
                                            }, function () {
                                                window.location.href = context + "/callTask/list.do"
                                            });
                                        }
                                    }
                                });
                            } else {
                                layer.msg("请选择要删除的任务分类！", {time: 2000, icon: 5});
                            }
                        }
                    });

                } else {
                    layer.msg("存在正在执行的任务，暂时无法批量删除！", {time: 3000, icon: 5});
                    return;
                }
            });

            /************************批量删除内容****************************/
            // $("#commitDelete").on("click", function () {
            //     var checkStatus = table.checkStatus('callTask'),
            //         data = checkStatus.data;//获取选中的数据
            //     var ids = "";
            //     for (var i in data) {
            //         ids += data[i].id + ",";
            //     }
            //     $.ajax({
            //         type: "get",
            //         url: context + "/callTask/delete.do",
            //         data: {"ids": ids},
            //         success: function (data) {
            //             if (data.status == 0) {
            //                 $("#del-taskcontengt").modal("hide");
            //                 layer.msg('删除成功！', {time: 4000, icon: 1});
            //                 reloadTable();
            //             } else {
            //                 layer.msg(data.message, {time: 4000, icon: 5});
            //             }
            //         },
            //         error: function (data) {
            //             layer.msg("删除失败!", {time: 4000, icon: 5});
            //         }
            //     });
            // });

            /************************批量启用内容模态框****************************/
            $("#startMany").on("click", function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                $("#start-taskcontengt").modal("show");
            });

            /************************批量启用内容****************************/
            $("#commitStart").on("click", function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                $.ajax({
                    type: "get",
                    url: context + "/callTask/modifyType.do",
                    data: {"ids": ids, type: 0},
                    success: function (data) {
                        if (data.status == 0) {
                            $("#start-taskcontengt").modal("hide");
                            layer.msg('启用成功', {time: 4000, icon: 1});
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 4000, icon: 5});
                        }
                    },
                    error: function (data) {
                        layer.msg("启用失败", {time: 4000, icon: 5});
                    }
                });
            });

            /************************批量废弃内容模态框****************************/
            $("#destroyMany").on("click", function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                if (data.length == 0) {
                    layer.msg("请选择数据！", {time: 1000, icon: 5});
                    return;
                }
                var distory = true;
                $.each(data, function (e, d) {
                    if (d.status == 1 && d.type == 0) {
                        distory = false;
                    }
                });
                if (distory) {
                    $("#ban-taskcontengt").modal("show");
                } else {
                    layer.msg("存在正在执行的任务，暂时无法批量废弃！", {time: 3000, icon: 5});
                    return;
                }

            });

            /************************批量废弃内容****************************/
            $("#commitDestroy").on("click", function () {
                var checkStatus = table.checkStatus('callTask'),
                    data = checkStatus.data;//获取选中的数据
                var ids = "";
                for (var i in data) {
                    ids += data[i].id + ",";
                }
                $.ajax({
                    type: "get",
                    url: context + "/callTask/modifyType.do",
                    data: {"ids": ids, type: 1},
                    success: function (data) {
                        if (data.status == 0) {
                            $("#ban-taskcontengt").modal("hide");
                            layer.msg('废弃成功！', {time: 4000, icon: 1});
                            reloadTable();
                        } else {
                            layer.msg(data.message, {time: 4000, icon: 5});
                        }
                    },
                    error: function (data) {
                        layer.msg("废弃失败!", {time: 4000, icon: 5});
                    }
                });
            });

            /**********修改重置按钮**********/
            $('#resetUpdate').click(function () {
                var data = editData;
                $("#up-form").find("[name='id']").val(data.id);
                $("#up-form").find("[name='name']").val(data.name);
                // $("#up-form").find("input[type='radio'][name='uptype']").val(data.status);
                if (data.planType == 1) {//周期性
                    $("#up-planType-two").show();
                    $("#up-planType-one").hide();
                } else if (data.planType == 0) {//一次性
                    $("#up-planType-two").hide();
                    $("#up-planType-one").show();
                }
                $('[id=uptype]').each(function (i, item) {
                    if ($(item).val() == data.type) {
                        $(item).prop('checked', true);
                        form.render();
                    }
                });

                $("#up-form").find("[name='callCategoryId']").val(data.callCategoryId);
                $("#up-form").find("[name='callContentSubjectName']").val(data.callContentSubjectName);
                $("#up-form").find("[name='callContentSubjectId']").val(data.callContentSubjectId);
                $("#up-form").find("[name='customerGroupId']").val(data.customerGroupId);
                $("#up-form").find("[name='customerGroupName']").val(data.customerGroupName);
                $('[id=upplanType]').each(function (i, item) {
                    if ($(item).val() == data.planType) {
                        $(item).prop('checked', true);
                        form.render();
                    }
                });

                if (data.planType == 0) {
                    $("#up-form").find("[name='executeOneDate']").val(data.executeBeginDate);
                    $("#up-form").find("[name='executeOneTime']").val(getTime(data.executeTime));
                } else if (data.planType == 1) {
                    $("#up-form").find("[name='executeBeginDate']").val(data.executeBeginDate);
                    $("#up-form").find("[name='executeEndDate']").val(data.executeEndDate);
                    $("#up-form").find("[name='executeTime']").val(getTime(data.executeTime));
                }
                $("#up-form").find("[name='callFlowId']").val(data.callFlowId);
                $("#up-form").find("[name='callFlowName']").val(data.callFlowName);
                $("#up-form").find("[name='callcjNumber']").val(data.callcjNumber);
                $("#up-form").find("[name='tryCount']").val(data.tryCount);
                $("#up-form").find("[name='basicNum']").val(data.basicNum);

                $('[id=upcallNumShowType]').each(function (i, item) {
                    if ($(item).val() == data.callNumShowType) {
                        $(item).prop('checked', true);
                        form.render();
                    }
                });
                // $("#up-form").find("[name='callNumShowType']").val(data.callNumShowType);
                $("#up-form").find("[name='callNumShow']").val(data.callNumShow);
                $("#up-form").find("[name='descri']").val(data.descri);
            });

            function setAjaxParam(event) {
                var $form = $(event.target).parents(".search-form");
                var $moreForm = $(".search-form-more");
                // var searchMore = $(".search-form-more").is(":hidden");
                // var data = $.param(uploadContractParams) + '&' + form;
                var params = $form.serializeArray();
                var planType="";
                $.each($("input[name='planType1']:checked"),function(){
                    if(this.checked){
                        planType+=$(this).val()+" ";
                    }
                });
                var status="";
                $.each($("input[name='status1']:checked"),function(){
                    if(this.checked){
                        status+=$(this).val()+" ";
                    }
                });
                params.push({"name":"planType","value":planType});
                params.push({"name":"status","value":status});
                var moreForms = $moreForm.serializeArray();
                var serarchParams = moreForms.concat(params);

                // console.log(serarchParams)
                $.each(serarchParams, function (key, value) {
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
                // ajaxParams['contentSubjectId'] = contentSubjectId_search;
                table.reload('callTask', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: ajaxParams
                    }
                });
            });
        });


        /************************左侧树状结构****************************/
        var zTreeObj;
        var subjectTree;//添加主题树
        var customerGroupTree;//添加客户组
        var callFlowTree;//添加流程树
        var updateSubjectTree;//修改主题树
        var updateCustomerGroupTree;//修改客户组树
        var updateCallFlowTree;//修改流程树
        var modifyCallFlowTree;//选流程树
        var searchSubjectTree;//搜索主题树
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
        //左侧任务分类菜单树
        function showTree() {
            $.ajax({
                type: "GET",
                url: context + "/callCategory/findTree.do",
                data: {},
                success: function (data) {
                    var resu = eval(data.data);
                    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, resu);
                }
            });
        }

        //主题树
        function showSubjectTree() {
            $.ajax({
                type: "GET",
                url: context + '/callContentSubject/tree.do',
                // data: {},
                success: function (data) {
                    var resu = eval(data.data);
                    // subjectTree = $.fn.zTree.init($("#add_contentSubject_tree"), setting, resu);
                    // updateSubjectTree = $.fn.zTree.init($("#up_callContentSubject_tree"), setting, resu);
                    searchSubjectTree = $.fn.zTree.init($("#search_contentSubject_tree"), setting, resu);
                }
            });
        }

        //客户组树(新增/修改)
        function showCustomerGroupTree() {
            $.ajax({
                type: "GET",
                url: context + '/customerGroup/showTree.do',
                data: {},
                success: function (data) {
                    var resu = eval(data.data);
                    customerGroupTree = $.fn.zTree.init($("#add_customerGroup_tree"), setting, resu);
                    updateCustomerGroupTree = $.fn.zTree.init($("#up_customerGroup_tree"), setting, resu);
                }
            });
        }

        //流程树(新增/修改)
        function showCallFlowTree() {
            $.ajax({
                type: "GET",
                url: context + '/callFlow/tree.do',
                data: {},
                success: function (data) {
                    var resu = eval(data.data);
                    callFlowTree = $.fn.zTree.init($("#add_callFlow_tree"), setting, resu);
                    updateCallFlowTree = $.fn.zTree.init($("#up_callFlow_tree"), setting, resu);
                    modifyCallFlowTree = $.fn.zTree.init($("#modify_callFlow_tree"), setting, resu);
                }
            });
        }

        /************************添加模块下拉树div****************************/
        //添加主题
        // showAddTree = function () {
        //     showSubjectTree();
        //     $("#add_contentSubject").slideDown("fast");
        //     $("body").bind("mousedown", onBodyDown);
        // };
        //添加客户组
        showAddCustomerGroupTree = function () {
            showCustomerGroupTree();
            $("#add_customerGroup").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        //添加流程
        showAddCallFlowNameTree = function () {
            showCallFlowTree();
            $("#add_callFlow").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };


        /************************修改模块下拉树div****************************/
        //修改主题
        // showUpdateTree = function () {
        //     $("#up_callContentSubject").slideDown("fast");
        //     $("body").bind("mousedown", onBodyDown);
        // };
        //修改客户组
        showUpdateCustomerGroupTree = function () {
            showCustomerGroupTree();
            $("#up_customerGroup").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        showUpdateCallFlowGroupTree = function () {
            showCallFlowTree();
            $("#up_callFlow").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        /***********选流程**************/
        showmodifyCallFlowGroupTree = function () {
            showCallFlowTree();
            $("#modify_callFlow").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        /***********主题搜索**************/
        showSearchTree = function () {
            showSubjectTree();
            $("#search_contentSubject").slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        };
        onBodyDown = function (event) {
            // if(){}
            // if (!$("#add_contentSubject").is(":hidden")) {
            //     hideMenu();
            // }
            if (!$("#add_customerGroup").is(":hidden")) {
                hideMenu();
            }
            if (!$("#add_callFlow").is(":hidden")) {
                hideMenu();
            }
            // if (!$("#up_callContentSubject").is(":hidden")) {
            //     hideMenu();
            // }
            if (!$("#up_customerGroup").is(":hidden")) {
                hideMenu();
            }
            if (!$("#up_callFlow").is(":hidden")) {
                hideMenu();
            }
            if (!$("#modify_callFlow").is(":hidden")) {
                hideMenu();
            }
            if (!$("#search_contentSubject").is(":hidden")) {
                hideMenu();
            }

            // if (!(event.target.id == "add_contentSubject" || $(event.target).parents("#add_contentSubject").length > 0)) {
            //     hideMenu();
            // }
        };
        hideMenu = function () {
            /*****************************************添加*******************************************/
            //隐藏添加主题树
            // if (!$("#add_contentSubject").is(":hidden") && event.target.id.indexOf('add_contentSubject') == -1) {
            //     $("#add_contentSubject").fadeOut("fast");
            //     $("body").unbind("mousedown", onBodyDown);
            // }
            //隐藏添加客户组树
            if (!$("#add_customerGroup").is(":hidden") && event.target.id.indexOf('add_customerGroup') == -1) {
                $("#add_customerGroup").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            //隐藏添加流程树
            if (!$("#add_callFlow").is(":hidden") && event.target.id.indexOf('add_callFlow') == -1) {
                $("#add_callFlow").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            /*****************************************修改*******************************************/
            //隐藏修改主题树
            // if (!$("#up_callContentSubject").is(":hidden") && event.target.id.indexOf('up_callContentSubject') == -1) {
            //     $("#up_callContentSubject").fadeOut("fast");
            //     $("body").unbind("mousedown", onBodyDown);
            // }
            //隐藏修改客户组树
            if (!$("#up_customerGroup").is(":hidden") && event.target.id.indexOf('up_customerGroup') == -1) {
                $("#up_customerGroup").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            //隐藏修改流程树
            if (!$("#up_callFlow").is(":hidden") && event.target.id.indexOf('up_callFlow') == -1) {
                $("#up_callFlow").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            //隐藏选流程树
            if (!$("#modify_callFlow").is(":hidden") && event.target.id.indexOf('modify_callFlow') == -1) {
                $("#modify_callFlow").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            //隐藏搜索主题树
            if (!$("#search_contentSubject").is(":hidden") && event.target.id.indexOf('search_contentSubject') == -1) {
                $("#search_contentSubject").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
        };


        /********************************* 单击树节点触发的方法*****************************************/
        function zTreeOnClick(event, treeId, treeNode) {
            var id = treeNode.id;
            var name = treeNode.name;
            /**
             * 1、获取树点击事件,判断是哪个树处理显示状态
             * 2、如果是左侧任务分类树，则刷新列表页面
             * 3、如果是添加主题树点击事件，则做添加主题业务处理；
             * 4、如果是修改主题树点击事件，则做修改主题业务处理；
             * 5、如果是添加客户组树点击事件，则做添加客户组业务处理；
             * 6、如果是修改客户组树点击事件，则做修改客户组业务处理；
             * 7、如果是添加流程树点击事件，则做添加流程业务处理；
             * 8、如果是修改流程树点击事件，则做修改流程业务处理；
             * */

                //判断是点击左侧树节点还是点击添加页面的树节点
                //添加主题
            // var addSubjectFlag = $("#add_contentSubject").is(":hidden");
            //修改主题
            // var updateSubjectFlag = $("#up_callContentSubject").is(":hidden");

            //添加客户组
            var addCustomerGroupFlag = $("#add_customerGroup").is(":hidden");
            //修改客户组
            var updateCustomerGroupFlag = $("#up_customerGroup").is(":hidden");

            //添加流程
            var addCallFlowFlag = $("#add_callFlow").is(":hidden");
            //修改流程
            var updateCallFlowFlag = $("#up_callFlow").is(":hidden");
            //选流程
            var modifyCallFlowFlag = $("#modify_callFlow").is(":hidden");
            //搜索主题
            var searchContentSubject = $("#search_contentSubject").is(":hidden");
            var parentId = treeNode.id;
            //选择任务分类
            if (event.target.id.indexOf('treeDemo') > -1) {
                $("#callCategoryId").val(id);//任务类别id
            }
            $("#addForm").find("#parentId").val(treeNode.id);
            var name = treeNode.name;
            // if (!addSubjectFlag) {//添加主题
            //     if (event.target.id.indexOf('add_contentSubject') > -1) {
            //         // alert("添加主题");
            //         $("#add_contentSubjectId").val(id);
            //         $("#add_contentSubjectName").val(name);
            //         $("#add_contentSubject").fadeOut("fast");
            //         $("body").unbind("mousedown", onBodyDown);
            //     }
            //     // hideMenu(event.target.id);
            // } else
            if (!addCustomerGroupFlag) {//添加客户组
                if (event.target.id.indexOf('add_customerGroup') > -1) {
                    // alert("添加主题");
                    $("#add_customerGroupId").val(id);
                    $("#add_customerGroupName").val(name);
                    $("#add_customerGroup").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            } else if (!addCallFlowFlag) {//添加流程
                if (event.target.id.indexOf('add_callFlow') > -1) {
                    // alert("添加主题");
                    $("#add_callFlowId").val(id);
                    $("#add_callFlowName").val(name);
                    $("#add_callFlow").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            }
            // else if (!updateSubjectFlag) {//修改主题
            //     if (event.target.id.indexOf('up_callContentSubject') > -1) {
            //         // alert("添加主题");
            //         $("#up_callContentSubjectId").val(id);
            //         $("#up_callContentSubjectName").val(name);
            //         $("#up_callContentSubject").fadeOut("fast");
            //         $("body").unbind("mousedown", onBodyDown);
            //     }
            // }
            else if (!updateCustomerGroupFlag) {//修改客户组
                if (event.target.id.indexOf('up_customerGroup') > -1) {
                    // alert("添加主题");
                    $("#up_customerGroupId").val(id);
                    $("#up_customerGroupName").val(name);
                    $("#up_customerGroup").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            } else if (!updateCallFlowFlag) {//修改流程
                if (event.target.id.indexOf('up_callFlow') > -1) {
                    // alert("添加主题");
                    $("#up_callFlowId").val(id);
                    $("#up_callFlowName").val(name);
                    $("#up_callFlow").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            } else if (!modifyCallFlowFlag) {//选流程
                if (event.target.id.indexOf('modify_callFlow') > -1) {
                    // alert("添加主题");
                    $("#modify_callFlowId").val(id);
                    $("#modify_callFlowName").val(name);
                    $("#modify_callFlow").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            }  else if (!searchContentSubject) {//选流程
                if (event.target.id.indexOf('search_contentSubject') > -1) {
                    // alert("添加主题");
                    $("#search_contentSubjectId").val(id);
                    $("#search_contentSubjectName").val(name);
                    $("#search_contentSubject").fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            } else {
                layui.use('table', function () {
                    var table = layui.table;
                    table.reload('callTask', {
                        where: {
                            key: {
                                parentId: parentId,
                            }
                        }
                    });
                });
            }
        }

        function addHref() {
            var taskId = $("#taskId").val();
            var url = context + "/ivr/testTime.do?taskId=" + taskId;
            window.location.href = url;
        }

        $('#watchCallRecord').click(function () {
            var callTaskId = $('#detailtaskId').val();
            var menu = window.parent.document.getElementsByTagName("dd");
            //修改左侧菜单 ‘外呼记录’ 为已标记状态
            $.each(menu,function (e,d) {
                // console.log(d.find('a').attr('data-url'));
                if(d.className == 'layui-this'){
                    d.className = '';
                }
                if(d.childNodes[0].getAttribute('data-url') == '/customerCallFlow/showMsg.do'){
                    d.className = 'layui-this';
                }
            });

            window.location.href = context + "/customerCallFlow/showMsg.do?callTaskId="+callTaskId;
        });

    };
    return {
        init: function () {
            init();
        }

    }
}(jQuery, layui, document)

