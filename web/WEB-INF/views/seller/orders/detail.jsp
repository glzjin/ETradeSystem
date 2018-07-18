<%@ page import="Models.OrdersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/seller/header.jsp" />

    <form action="/buyer/orders/order_last" method="post">
        <jsp:include flush="true" page="../../includes/form.jsp" />
        <section class="invoice">
            <!-- title row -->
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="page-header">
                        <i class="fa fa-check"></i> 订单详情 ID：${Order.id}

                        <small class="pull-right"><span class="fa fa-user"></span>买家：${Order.user.userName}(ID: ${Order.user.id}) <span class="fa fa-bell-o"></span>状态：${Order.statusText()} <span class="fa fa-clock-o"></span>时间：${Order.datetime}</small>
                    </h2>
                </div>
                <!-- /.col -->
            </div>
            <!-- info row -->
            <div class="row invoice-info">
                <div class="col-sm-4 invoice-col">
                    寄送地址
                    <address>
                        <div class="form-group">
                            <label>真实姓名</label>
                            <input type="text" class="form-control" name="user_real_name" placeholder="请输入您的真实姓名" required maxlength="5" value="${Order.user.userRealName}" disabled>
                        </div>
                        <div class="form-group">
                            <label>电话号码</label>
                            <input type="number" class="form-control" name="user_phone" placeholder="请输入您的电话号码" required maxlength="11" minlength="11" value="${Order.user.userPhone}" disabled>
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type=" text" class="form-control" name="user_address" placeholder="请输入您的收货地址" required maxlength="40" minlength="5" value="${Order.user.userAddress}" disabled>
                        </div>
                    </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                    快递信息
                    <address>
                        <div class="form-group">
                            <label>快递公司名称</label>
                            <input type="text" class="form-control" name="deliver_company" placeholder="请输入快递公司名称" required value="${Order.deliverCompany}" maxlength="16" <c:if test="${Order.status != OrdersEntity.STATUS_DELIVER_PENDING}"> disabled</c:if>>
                        </div>
                        <div class="form-group">
                            <label>快递运单号</label>
                            <input type="text" class="form-control" name="deliver_order_id" placeholder="请输入快递单号" required value="${Order.deliverOrderId}" maxlength="16" <c:if test="${Order.status != OrdersEntity.STATUS_DELIVER_PENDING}"> disabled</c:if>>
                        </div>
                    </address>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

            <c:set var="totalPrice" value="0"/>
            <!-- Table row -->
            <div class="row">
                <div class="col-xs-12 table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>数量</th>
                            <th>商品</th>
                            <th>卖家</th>
                            <th>单价</th>
                            <th>总价</th>
                            <c:if test="${Order.status >= OrdersEntity.STATUS_RANK_PENDING}">
                                <th>评价</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${Order.orderItems}" var="single_order_item">
                                <tr>
                                    <td>${single_order_item.itemAmount}</td>
                                    <td>${single_order_item.item.itemName}</td>
                                    <td>${single_order_item.item.user.userName}(ID：${single_order_item.item.user.id})</td>
                                    <td>￥${single_order_item.itemCurrentPrice}</td>
                                    <td>￥${single_order_item.itemCurrentPrice * single_order_item.itemAmount}</td>
                                    <c:if test="${Order.status == OrdersEntity.STATUS_FINISHED}">
                                        <td>
                                            评分：<span class="rating" data-default-rating="${single_order_item.rank.rankScore}" data-stars="5" id="rateing-${single_order_item.item.id}" disabled=""></span><br>
                                            评语：<br>
                                                ${single_order_item.rank.rankDetail}
                                        </td>
                                    </c:if>
                                    <c:set var="totalPrice" value="${totalPrice + single_order_item.itemCurrentPrice * single_order_item.itemAmount}"/>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

            <div class="row">
                <!-- accepted payments column -->
                <div class="col-xs-6">
                    <p class="lead">付款方式:</p>

                    <p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
                        点击下单之后直接付款
                    </p>
                </div>
                <!-- /.col -->
                <div class="col-xs-6">
                    <p class="lead">总价</p>

                    <div class="table-responsive">
                        <table class="table">
                            <tbody><tr>
                                <th style="width:50%">总价:</th>
                                <td>￥${totalPrice}</td>
                            </tr>
                            </tbody></table>
                    </div>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

            <!-- this row will not appear when printing -->
            <div class="row no-print">
                <div class="col-xs-12">
                    <c:if test="${Order.status == OrdersEntity.STATUS_DELIVER_PENDING}">
                        <button type="submit" class="btn btn-success pull-right" onclick="javascript: form.action='./deliver?id=${Order.id}&page=${currentPage}';"><i class="fa fa-truck"></i> 发货
                        </button>
                    </c:if>
                </div>
            </div>
        </section>
    </form>


<jsp:include flush="true" page="../../includes/seller/footer.jsp" />


