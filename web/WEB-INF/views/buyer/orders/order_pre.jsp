<%@ page import="Models.ItemsEntity" %>
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
                        <i class="fa fa-check"></i> 结账
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
                            <input type="text" class="form-control" name="user_real_name" placeholder="请输入您的真实姓名" required maxlength="5" value="${user.userRealName}">
                        </div>
                        <div class="form-group">
                            <label>电话号码</label>
                            <input type="number" class="form-control" name="user_phone" placeholder="请输入您的电话号码" required maxlength="11" minlength="11" value="${user.userPhone}" step="1" onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value=parseInt(this.value, 10);}"
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type=" text" class="form-control" name="user_address" placeholder="请输入您的收货地址" required maxlength="40" minlength="5" value="${user.userAddress}">
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
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${List}" var="single_cart_info">
                                <c:if test="${single_cart_info.item.status == ItemsEntity.STATUS_ON}">
                                <tr>
                                    <td>${single_cart_info.itemAmount}</td>
                                    <td>${single_cart_info.item.itemName}</td>
                                    <td>${single_cart_info.item.user.userName}(ID：${single_cart_info.item.user.id})</td>
                                    <td>￥${single_cart_info.item.itemPrice}</td>
                                    <td>￥${single_cart_info.item.itemPrice * single_cart_info.itemAmount}</td>
                                    <c:set var="totalPrice" value="${totalPrice + single_cart_info.item.itemPrice * single_cart_info.itemAmount}"/>
                                </tr>
                                </c:if>
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
                    <button type="submit" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> 下单并付款
                    </button>
                </div>
            </div>
        </section>
    </form>


<jsp:include flush="true" page="../../includes/buyer/footer.jsp" />


