<%@ page import="Models.OrdersEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/buyer/header.jsp" />


    <form action="/buyer/orders/order_last" method="post">
        <jsp:include flush="true" page="../../includes/form.jsp" />
        <section class="invoice">
            <!-- title row -->
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="page-header">
                        <i class="fa fa-check"></i> 订单详情 ID：${Order.id}

                        <small class="pull-right"><span class="fa fa-user"></span>买家：${Order.user.userName}(ID: ${Order.sellUser.id}) <span class="fa fa-bell-o"></span>状态：${Order.statusText()} <span class="fa fa-clock-o"></span>时间：${Order.datetime}</small>
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
                            <input type="text" class="form-control" name="user_real_name" placeholder="请输入您的真实姓名" required maxlength="5" value="${user.userRealName}" disabled>
                        </div>
                        <div class="form-group">
                            <label>电话号码</label>
                            <input type="number" class="form-control" name="user_phone" placeholder="请输入您的电话号码" required maxlength="11" minlength="11" value="${user.userPhone}" disabled>
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type=" text" class="form-control" name="user_address" placeholder="请输入您的收货地址" required maxlength="40" minlength="5" value="${user.userAddress}" disabled>
                        </div>
                    </address>
                </div>
                <!-- /.col -->

                <c:if test="${Order.status >= OrdersEntity.STATUS_RECEIVE_PENDING}">
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
                </c:if>
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
                                    <td><a href="./items/detail?id=${single_order_item.item.id}">${single_order_item.item.itemName}</a><br></td></td>
                                    <td>${single_order_item.item.user.userName}(ID：${single_order_item.item.user.id})</td>
                                    <td>￥${single_order_item.itemCurrentPrice}</td>
                                    <td>￥${single_order_item.itemCurrentPrice * single_order_item.itemAmount}</td>
                                    <c:set var="totalPrice" value="${totalPrice + single_order_item.itemCurrentPrice * single_order_item.itemAmount}"/>
                                    <c:if test="${Order.status == OrdersEntity.STATUS_RANK_PENDING}">
                                        <td>
                                            评分：<span class="rating" data-default-rating="5" data-stars="5" data-bind="rateing-number-${single_order_item.item.id}" id="rateing-${single_order_item.item.id}"></span><br>
                                            <input type="number" class="form-control" id="rateing-number-${single_order_item.item.id}" name="rateing_number_${single_order_item.item.id}" value="5" style="display:none">
                                            评语：<textarea class="textarea" rows="5" name="rateing_text_${single_order_item.item.id}" placeholder="请输入您的评语" style="width: 100%;"></textarea>
                                        </td>
                                    </c:if>
                                    <c:if test="${Order.status == OrdersEntity.STATUS_FINISHED}">
                                        <td>
                                            评分：<span class="rating" data-default-rating="${single_order_item.rank.rankScore}" data-stars="5" id="rateing-${single_order_item.item.id}" disabled=""></span><br>
                                            评语：<br>
                                            ${single_order_item.rank.rankDetail}
                                        </td>
                                    </c:if>
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
                    <c:if test="${Order.status == OrdersEntity.STATUS_RECEIVE_PENDING}">
                        <button type="submit" class="btn btn-success pull-right" onclick="javascript: form.action='./confirm?id=${Order.id}&page=${currentPage}';"><i class="fa fa-check-circle-o"></i> 确认收货
                        </button>
                    </c:if>
                    <c:if test="${Order.status == OrdersEntity.STATUS_RANK_PENDING}">
                        <button type="submit" class="btn btn-success pull-right" onclick="javascript: form.action='./rank?id=${Order.id}&page=${currentPage}';"><i class="fa fa-check-circle-o"></i> 评价
                        </button>
                    </c:if>
                </div>
            </div>
        </section>
    </form>


<jsp:include flush="true" page="../../includes/buyer/footer.jsp" />


