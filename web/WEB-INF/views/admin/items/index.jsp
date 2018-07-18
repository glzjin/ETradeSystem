<%@ page import="Models.ItemsEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/admin/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">商品管理</h3>
        </div>
        <div class="box-body table-responsive">
            <table class="table table-bordered">
                <tbody><tr>
                    <th>ID</th>
                    <th>名称</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>销量</th>
                    <th>所属卖家</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${List}" var="single_item">
                    <tr>
                        <th>${single_item.id}</th>
                        <th>${single_item.itemName}</th>
                        <th>${single_item.itemPrice}</th>
                        <th>${single_item.itemSku}</th>
                        <th>${single_item.itemSold}</th>
                        <th><a href="./users/edit?id=${single_item.user.id}">${single_item.user.userName}(ID: ${single_item.user.id})</th>
                        <th>${single_item.statusText()}</th>
                        <th>
                            <form role="form" method="post">
                                <jsp:include flush="true" page="../../includes/form.jsp" />
                                <c:if test="${single_item.status == ItemsEntity.STATUS_ON}">
                                    <button type="submit" role="button" class="btn btn-danger btn-xs" onclick="javascript: form.action='./items/delete?id=${single_item.getId()}&page=${currentPage}';">下架</button>
                                </c:if>
                                <c:if test="${single_item.status == ItemsEntity.STATUS_OFF}">
                                    <button type="submit" role="button" class="btn btn-danger btn-xs" onclick="javascript: form.action='./items/delete?id=${single_item.getId()}&page=${currentPage}';">上架</button>
                                </c:if>
                                <a href="./items/edit?id=${single_item.getId()}&page=${currentPage}" role="button" class="btn btn-info btn-xs">编辑</a>
                                <a href="./items/detail?id=${single_item.getId()}&page=${currentPage}" role="button" class="btn btn-success btn-xs">预览</a>
                            </form>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <jsp:include flush="true" page="../../includes/pagenation.jsp" />
        </div>
        <!-- /.box-footer -->
    </div>
    <!-- /.box-default -->


<jsp:include flush="true" page="../../includes/admin/footer.jsp" />


