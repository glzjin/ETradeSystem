<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/auth/header.jsp" />

<p class="login-box-msg">登录</p>

<form action="/login" method="post">
  <jsp:include flush="true" page="../includes/form.jsp" />
  <div class="form-group has-feedback">
    <input type="text" class="form-control" placeholder="用户名" name="username" required maxlength="16">
    <span class="glyphicon glyphicon-user form-control-feedback"></span>
  </div>
  <div class="form-group has-feedback">
    <input type="password" class="form-control" placeholder="密码" name="password" required minlength="6" maxlength="16">
    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
  </div>
  <div class="row">
    <div class="col-xs-8">
    </div>
    <!-- /.col -->
    <div class="col-xs-4">
      <button type="submit" class="btn btn-primary btn-block btn-flat"> 登录</button>
    </div>
    <!-- /.col -->
  </div>
</form>

<a href="/register" class="text-center">注册</a>
<jsp:include flush="true" page="../includes/auth/footer.jsp" />