$(function(){
    /**登录表单验证规则**/
    var validator =   $("#login_form").validate({
        rules: {
            name : "required",//userName为input框id
            password: "required"
        },
        messages: {
            name: {
                required: " 请输入用户名"
            },
            password: {
                required: " 请输入密码"
            }
        }
     });
     /**框体隐藏后触发**/
     $('#loginModal').on('hidden.bs.modal', function () {
        validator.resetForm();
     });
     /**登录事件**/
    $("#login_btn").click(function(){
          if(!$("#login_form").valid()){
            return;
          }
         //获取用户名和密码
         var user = $("#name").val();
         var pwd = $("#password").val();
         $.ajax({
            url: "/login",
            data :{
                "userName" : user,
                "password" : pwd
            },
            success: function(r){
                 //隐藏登录窗口
                 $("#loginModal").modal('hide');
                 if(r == '登录成功'){
                    getLoginName();
                 }
            }
         });
    })
    /*注销事件*/
    $("#logout").click(function(){
         $.ajax({
            url:"/logout",
            success:function(r){
                if(r){ getLoginName();}
            }
        })
    })
    /*激活弹出组件*/
    $("[data-toggle='popover']").popover();
    /*获取登录的用户名字*/
    function getLoginName(){
         $.ajax({
            url:"/getLoginName",
            success:function(r){
                if(!r){
                     $("#login").show();
                     $("#register").show();
                     $("#loginName").html("");
                     $("#logout").hide();
                }else{
                     $("#login").hide();
                     $("#register").hide();
                     $("#loginName").html(r);
                     $("#logout").show();
                }
            }
        })
    };
    getLoginName();
})