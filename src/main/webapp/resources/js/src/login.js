$(".login_btn").click(function(){
    var accountName=$("#accountName").val();
    var password=$("#password").val();

    if(accountName==null || accountName==""){
        layer.msg('用户名不能为空！');
        return false;
    }
    if(password==null || password==""){
        layer.msg('密码不能为空！');
        return false;
    }
    $.ajax({
        url:context +"/realLogin.do",
        data:{accountName:accountName,password:password},
        async:false,
        success:function(data){
            if(data.status==0){
                layer.msg('登录成功！');
                window.location.href=context +"/loginSuccess.do?accountName="+accountName;
            }else{
                layer.msg('登录失败！');
            }
        },
        error:function(data){
            layer.msg('登录失败！');
        }

    });

});

/**
 * 键盘监听事件(全局监听)
 * 一个form表单下要有至少2个input文本框,否则按回车会提交表单，刷新页面
 */
$(function(){
    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode==13){
            var accountName=$("#accountName").val();
            var password=$("#password").val();

            if(accountName==null || accountName==""){
                layer.msg('用户名不能为空！');
                return false;
            }
            if(password==null || password==""){
                layer.msg('密码不能为空！');
                return false;
            }

            $.ajax({
                url:context +"/realLogin.do",
                data:{accountName:accountName,password:password},
                async:false,
                success:function(data){
                    if(data.status==0){
                        layer.msg('登录成功！');
                        window.location.href=context +"/loginSuccess.do?accountName="+accountName;
                    }else{
                        layer.msg('登录失败！');
                    }
                },
                error:function(data){
                    layer.msg('登录失败！');
                }

            });

        }

    }

    });
//$("#password").on('keyup',function(e){//监听密码框键盘事件
//    var keyNum;
//    if(window.event){// IE
//        keyNum = e.keyCode
//    }else if(e.which){// Netscape/Firefox/Opera
//        keyNum = e.which
//    }
//
//    //我这里是监控回车键
//    if( keyNum==13) {
//
//    }
//});