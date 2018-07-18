<%@ page import="Models.UsersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/admin/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">用户管理</h3>
        </div>
        <div class="box-body table-responsive">
            <table class="table table-bordered">
                <tbody><tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>用户角色</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${List}" var="single_user">
                    <tr>
                        <th>${single_user.getId()}</th>
                        <th>${single_user.getUserName()}</th>
                        <th>
                            ${single_user.userRoleTypeName()}
                        </th>
                        <th>
                            <c:if test="${single_user.id != user.id && single_user.userRoleType != UsersEntity.USER_ROLE_TYPE_ADMIN}">
                                <form role="form" method="post">
                                    <jsp:include flush="true" page="../../includes/form.jsp" />
                                    <c:if test="${single_user.userRoleType != UsersEntity.USER_ROLE_TYPE_BANNED}">
                                        <button type="submit" role="button" class="btn btn-danger btn-xs" onclick="javascript: form.action='./users/delete?id=${single_user.getId()}&page=${currentPage}';">封禁</button>
                                    </c:if>
                                    <a href="./users/edit?id=${single_user.getId()}&page=${currentPage}" role="button" class="btn btn-info btn-xs">编辑</a>
                                    <c:if test="${single_user.userRoleType == UsersEntity.USER_ROLE_TYPE_SELLER_UNAUTH}">
                                        <button type="submit" role="button" class="btn btn-success btn-xs" onclick="javascript: form.action='./users/auth?id=${single_user.getId()}&page=${currentPage}';">验证</button>
                                    </c:if>
                                </form>
                            </c:if>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <a role="button" href="/admin/users/add?page=${currentPage}" class="btn btn-success btn-sm">添加</a>
            <jsp:include flush="true" page="../../includes/pagenation.jsp" />
        </div>
        <!-- /.box-footer -->
    </div>
    <!-- /.box-default -->


<jsp:include flush="true" page="../../includes/admin/footer.jsp" />


