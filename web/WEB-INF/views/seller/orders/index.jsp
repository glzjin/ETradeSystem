<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/seller/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">订单管理</h3>
        </div>
        <div class="box-body table-responsive">
            <table class="table table-bordered">
                <tbody><tr>
                    <th>ID</th>
                    <th>时间</th>
                    <th>总价</th>
                    <th>内容物</th>
                    <th>用户</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${List}" var="single_order">
                    <tr>
                        <th>${single_order.id}</th>
                        <th>${single_order.datetime}</th>
                        <th>${single_order.price}</th>
                        <th>
                            <c:forEach items="${single_order.orderItems}" var="single_order_item">
                                <a href="./items/detail?id=${single_order_item.item.id}">${single_order_item.item.itemName} ✖ ${single_order_item.itemAmount}(￥${single_order_item.itemCurrentPrice})</a><br>
                            </c:forEach>
                        </th>
                        <th>${single_order.user.userName}(ID: ${single_order.user.id})</th>
                        <th>
                            ${single_order.statusText()}
                        </th>
                        <th>
                            <a href="./orders/detail?id=${single_order.getId()}" role="button" class="btn btn-success btn-xs">详情</a>
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

<jsp:include flush="true" page="../../includes/seller/footer.jsp" />


