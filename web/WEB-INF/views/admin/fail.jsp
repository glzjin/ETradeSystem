<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/auth/header.jsp" />
<p class="login-box-msg">操作失败</p>

<p>${error_msg}</p>
<p id="count_down"></p>
<a href="/" role="button" class="btn btn-primary btn-block btn-flat"><span class="fa fa-fast-forward"></span> 不想等待？点我！</a>
<script>
    var t=5;//设定跳转的时间
    setInterval("refer()",1000); //启动1秒定时
    function refer(){
        if(t==0){
            location="/"; //#设定跳转的链接地址
        } else {
            document.getElementById('count_down').innerHTML=""+t+"秒后跳转到首页"; // 显示倒计时
            t--;
        }
    }
</script>
<jsp:include flush="true" page="../includes/auth/footer.jsp" />
