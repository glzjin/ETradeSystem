<%@ page import="Models.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/auth/header.jsp" />

<p class="login-box-msg">注册</p>

<form action="/register" method="post">
    <jsp:include flush="true" page="../includes/form.jsp" />
    <div class="form-group has-feedback">
        <label>用户名</label>
        <input type="text" class="form-control" placeholder="请输入您想设置的用户名" name="username" required maxlength="16">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
    </div>
    <div class="form-group has-feedback">
        <label>密码</label>
        <input type="password" class="form-control" placeholder="请输入您想设置的密码" name="password" required minlength="6" maxlength="16">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
    </div>
    <div class="form-group">
        <label>注册角色</label>
        <select class="form-control" name="user_role_type">
            <option value="${UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH}">卖家</option>
            <option value="${UsersEntity.USER_ROLE_TYPE_BUYER}">买家</option>
        </select>
    </div>
    <div class="row">
        <div class="col-xs-8">
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
            <button type="submit" class="btn btn-primary btn-block btn-flat"> 注册</button>
        </div>
        <!-- /.col -->
    </div>
</form>

<a href="/login" class="text-center">登录</a>
<jsp:include flush="true" page="../includes/auth/footer.jsp" />
