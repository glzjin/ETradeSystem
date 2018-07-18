<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/admin/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">评价管理</h3>
        </div>
        <div class="box-body table-responsive">
            <table class="table table-bordered">
                <tbody><tr>
                    <th>对应订单ID</th>
                    <th>总价</th>
                    <th>卖家</th>
                    <th>买家</th>
                    <th>内容物</th>
                    <th>评分</th>
                    <th>评语</th>
                </tr>
                <c:forEach items="${List}" var="single_rank">
                    <tr>
                        <th><a href="./orders/detail?id=${single_rank.order.id}">${single_rank.order.id}</a></th>
                        <th>${single_rank.order.price}</th>
                        <th><a href="./users/edit?id=${single_rank.order.sellUser.id}">${single_rank.order.sellUser.userName}(ID: ${single_rank.order.sellUser.id})</a></th>
                        <th><a href="./users/edit?id=${single_rank.order.user.id}">${single_rank.order.user.userName}(ID: ${single_rank.order.user.id})</a></th>
                        <th>
                            <c:forEach items="${single_rank.order.orderItems}" var="single_order_item">
                                <a href="./items/detail?id=${single_order_item.item.id}">${single_order_item.item.itemName} ✖ ${single_order_item.itemAmount}(￥${single_order_item.itemCurrentPrice})</a><br>
                            </c:forEach>
                        </th>
                        <th>${single_rank.rankScore}</th>
                        <th>${single_rank.rankDetail}</th>
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


