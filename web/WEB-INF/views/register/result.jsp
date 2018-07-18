<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../includes/auth/header.jsp" />
<c:if test="${toURL == null}">
    <c:set var="locationURL" value="/"/>
</c:if>
<c:if test="${toURL != null}">
    <c:set var="locationURL" value="${toURL}"/>
</c:if>
<p class="login-box-msg">注册结果</p>

<p>${resultMsg}</p>
<p id="count_down"></p>
<a href="${locationURL}" role="button" class="btn btn-primary btn-block btn-flat"><span class="fa fa-fast-forward"></span> 不想等待？点我！</a>
<script>
    var t=5;//设定跳转的时间
    setInterval("refer()",1000); //启动1秒定时
    function refer(){
        if(t==0){
            location= "${locationURL}";
        } else {
            document.getElementById('count_down').innerHTML=""+t+"秒后跳转。"; // 显示倒计时
            t--;
        }
    }
</script>
<jsp:include flush="true" page="../includes/auth/footer.jsp" />

