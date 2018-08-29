var outBoundTaskRecord = function ($, layui) {
    function init() {
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
                elem: '#content-record'
                , url: context + '/outBoundTaskRecord/data.do'  //数据请求路径
                , cols: [[ //标题栏
                    {checkbox: true, field: 'id', title: '全选'}
                    , {field: 'taskName', title: '任务名称', }
                    , {field: 'phone', title: '客户号码', templet: '#theme'}
                    , {field: 'beginDate', title: '通话开始时间',templet:'<div>{{layui.laytpl.toDateString(d.beginDate,"yyyy-MM-dd HH:mm:ss")}}</div>'}
                    , {field: 'updateDate', title: '通话更新时间',templet:'<div>{{layui.laytpl.toDateString(d.updateDate,"yyyy-MM-dd HH:mm:ss")}}</div>'}
                    , {field: 'status', title: '状态', templet: '#status'}
                   /* , {field: 'customerLabel', title: '客户标签',}*/
                ]]
                , skin: 'row' //表格风格
                , even: true
                , page: true //是否显示分页
                , id: 'record' //初始化标识id
                // ,limits: [5, 7, 10]
                //,limit: 5 //每页默认显示的数量
               /* , jump: function (obj) {
                    console.log(obj)
                }*/
            });

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
                table.reload('record', {
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
    function NodeMsg(recordId){
        var tbody=window.document.getElementById("tbody-result");
        $.ajax({
            type: "GET",
            url: context + '/outBoundTaskRecord/getTaskInfo.do',
            data: {"recordId": recordId},
            success: function (f) {
                var data=f.data;
                var str = "";
                for(var i=0;i<data.length;i++){
                    str +="<p title='"+data[i]+"'>"+ data[i]+ "</p>"
                }
                tbody.innerHTML = str;
            },
            error: function () {
                alert("查询失败")
            }


        })
}

