var taskconfiguration = function ($, layui) {
    function init() {
        var searchCallTaskId = $('#searchCallTaskId').val();
        layui.use(['table', 'form', 'element', 'laypage', 'laydate', 'layer'], function () {
            var table = layui.table
                , form = layui.form
                , element = layui.element
                , laypage = layui.laypage
                , laydate = layui.laydate
                , layer = layui.layer;
            var ajaxParams = {};
            //展示已知数据
            table.render({
                elem: '#content-configuration'
                , url: context + '/customerCallFlow/data.do'  //数据请求路径
                , cols: [[ //标题栏
                    {checkbox: true, field: 'id', title: '全选'}
                    , {field: 'theme', title: '外呼主题', width: "10%",}
                    , {field: 'name', title: '客户', width: "15%",templet: '#theme'}
                    , {field: 'category', title: '任务分类', width: "10%",}
                    , {field: 'taskName', title: '任务名称', width: "10%",}
                    , {field: 'executetime', title: '执行时间', width: "15%",templet:'<div>{{layui.laytpl.toDateString(d.executetime,"yyyy-MM-dd HH:mm")}}</div>'}
                    , {field: 'status', title: '状态', width: "8%", templet: '#status'}
                    , {field: 'result', title: '执行结果', width: "8%",templet:'#result'}
                    , {field: 'callTime', title: '通话时长(s)', width: "10%",}
                    , {field: 'customer_tab', title: '客户标签', width: "10%",}
                ]]
                , skin: 'row' //表格风格
                , even: true
                , page: true //是否显示分页
                , id: 'callTask1' //初始化标识id
                , where: {
                    key: {
                        searchCallTaskId: searchCallTaskId
                    }
                }
                // ,limits: [5, 7, 10]
                //,limit: 5 //每页默认显示的数量
               /* , jump: function (obj) {
                    console.log(obj)
                }*/
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
            /************************刷新table****************************/
            var reloadTable = function () {
                table.reload("callFlow", { //此处是上文提到的 初始化标识id
                });
            };

            // $('#search').on('click', function () {
            //     var type = $(this).data('type');
            //     active[type] ? active[type].call(this) : '';
            // });
            laydate.render({
                elem: '#time-horizon'
                , range: true
            });
            function setAjaxParam(event) {
                var $form = $(event.target).parents(".search-form");
                var $moreForm = $(".search-form-more");
                var params = $form.serializeArray();
                var moreForms = $moreForm.serializeArray();
                var serarchParams = moreForms.concat(params);
                console.log(serarchParams)
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
                table.reload('callTask1', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        key: ajaxParams
                    }
                });
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

        });
    };

    return {
        init: function () {
            init();
        }

    }

}(jQuery, layui, document)
//展示弹窗该任务的节点内容
    function NodeMsg(phone,taskid){
        var tbody=window.document.getElementById("tbody-result");
        $.ajax({
            type: "GET",
            url: context + '/customerCallFlow/nodalMesg.do',
            data: {"telPhone": phone,"taskId":taskid},
            success: function (data) {
                    var str = "";
                    var k=0;
                    debugger;
                    for (i in data) {
                        k=k+1
                        if(data[i].content != ''){
                            str +=
                                "<p title='"+data[i].content+"'>"+"坐席：" + data[i].content + "</p>"
                        }
                        if(data[i].answer != ''){
                            str +=
                                "<p title='"+data[i].answer+"'>"+"客户：" + data[i].answer + "</p>"
                        }
                    }
                    tbody.innerHTML = str;
            },
            error: function () {
                alert("查询失败")
            }


        })
    //window.location.href=context + '/customerCallFlow/nodalMesg.do';
}

