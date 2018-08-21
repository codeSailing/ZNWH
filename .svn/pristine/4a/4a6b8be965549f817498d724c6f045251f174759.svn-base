/**
 * Created by Xing ye on 2017/11/20.
 */
//访问路径上下文
var context;
//分页
var textReplace = new RegExp('[`~!@$%^&*()=|{}\':;\',\\[\\].<>/?~！@￥……&*（）——|{}【】‘；：”“\'。，、？]');
var timestamp = new Date().getTime();
var page = {
    layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'] //自定义分页布局
    //,curr: 5 //设定初始在第 5 页
    //, limits: [10,20, 30, 40, 50]
    //, limit: 10
    , prev: "上一页"
    , next: "下一页"
    , groups: 5 //只显示 1 个连续页码
    //, first: "首页"
    //, last: "末页"
    , theme: '#ffcb2d'
};
$(function () {
    $.ajaxSetup({ cache: false }); //保证在ie浏览器获取数据不从缓存中获取
//初始化layui
    var layer = layui.layer;
    layui.use(['table','layer'], function () {
         layer = layui.layer;
        layui.laytpl.toDateString = function (d, format) {
            var date = new Date(d || new Date())
                , ymd = [
                    this.digit(date.getFullYear(), 4)
                    , this.digit(date.getMonth() + 1)
                    , this.digit(date.getDate())
                ]
                , hms = [
                    this.digit(date.getHours())
                    , this.digit(date.getMinutes())
                    , this.digit(date.getSeconds())
                ];

            format = format || 'yyyy-MM-dd HH:mm:ss';

            return format.replace(/yyyy/g, ymd[0])
                .replace(/MM/g, ymd[1])
                .replace(/dd/g, ymd[2])
                .replace(/HH/g, hms[0])
                .replace(/mm/g, hms[1])
                .replace(/ss/g, hms[2]);
        };

        //数字前置补零
        layui.laytpl.digit = function (num, length, end) {
            var str = '';
            num = String(num);
            length = length || 2;
            for (var i = num.length; i < length; i++) {
                str += '0';
            }
            return num < Math.pow(10, length) ? str + (num | 0) : num;
        };

    });

    /******************列表全选操作 ********************/
    // $(".checkboxall").on("click", function () {
    //     if ($(this).is(':checked')) {
    //         $(this).parents("table").find("input[name='ids']").prop("checked", true);
    //      //   $("input[name='ids']").prop("checked", true);
    //     } else {
    //         $(this).parents("table").find("input[name='ids']").prop("checked", false);
    //     }
    // });

    //时间函数
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份

            "d+": this.getDate(), //日

            "h+": this.getHours(), //小时

            "m+": this.getMinutes(), //分

            "s+": this.getSeconds(), //秒

            "q+": Math.floor((this.getMonth() + 3) / 3), //季度

            "S": this.getMilliseconds() //毫秒

        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };

    /******************更多筛选条件 显示隐藏 ********************/
    // $('.search-form .show-more').click(function(){
    //     var content =$('.search-form .show-more').attr("content");
    //     if(content==0){
    //         $('.search-form .show-more').css('transform', 'rotateX(180deg)');
    //         $('.search-form .show-more').attr("content",1);
    //         $('#moreQueryForm').fadeIn();
    //     }else{
    //         $('.search-form .show-more').css('transform','rotateX(0deg)');
    //         $('.search-form .show-more').attr("content",0);
    //         $('#moreQueryForm').fadeOut();
    //         reloadQuery();
    //     }
    // });



});
/******************初始化更多筛选条件********************/
// function reloadQuery(){
//     layui.use(['form'], function() {
//         var form = layui.form;
//         $("#moreQueryForm").find("input").each(function(){
//             $(this).val("");
//         });
//         $("#moreQueryForm").find("select").each(function(){
//             var filter = $(this).attr("lay-filter");
//             $(this).val("");
//             form.render('select',filter);
//         });
//         $("#departMentContent").addClass("hide");
//
//     })
// }

