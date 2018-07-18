<%@ page import="Models.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/admin/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">用户编辑</h3>
        </div>
        <form role="form" action="/admin/users/edit?page=${currentPage}&id=${User.id}" method="post">
            <jsp:include flush="true" page="../../includes/form.jsp" />
            <div class="box-body">
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" class="form-control" value="${User.userName}" disabled>
                    <input type="text" class="form-control" name="username" value="${User.userName}" style="display:none" autocomplete="false" >
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" class="form-control" name="password" placeholder="留空则不修改" minlength="6" maxlength="16" autocomplete="false" >
                </div>
                <div class="form-group">
                    <label>注册角色</label>
                    <select class="form-control" name="user_role_type">
                        <option value="${UsersEntity.USER_ROLE_TYPE_SELLER_AUTHED}"<c:if test="${User.userRoleType == UsersEntity.USER_ROLE_TYPE_SELLER_AUTHED}"> selected</c:if>>已验证卖家</option>
                        <option value="${UsersEntity.USER_ROLE_TYPE_BUYER}"<c:if test="${User.userRoleType == UsersEntity.USER_ROLE_TYPE_BUYER}"> selected</c:if>>买家</option>
                        <option value="${UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH}"<c:if test="${User.userRoleType == UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH}"> selected</c:if>>未验证卖家</option>
                        <option value="${UsersEntity.USER_ROLE_TYPE_BANNED}"<c:if test="${User.userRoleType == UsersEntity.USER_ROLE_TYPE_BANNED}"> selected</c:if>>已封禁用户</option>
                    </select>
                </div>
            </div>
            <!-- /.box-body -->

            <div class="box-footer">
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../../includes/admin/footer.jsp" />