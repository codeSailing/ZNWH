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