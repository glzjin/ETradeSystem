<%@ page import="Helpers.StatsHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/buyer/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">首页</h3>
        </div>
        <div class="box-body">

            <div class="info-box" onclick="javascript:location.href='/buyer/cart_infos'">
                <span class="info-box-icon bg-red"><i class="fa fa-cart-plus"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">购物车商品数量</span>
                    <span class="info-box-number">${StatsHelper.getCartInfosCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box" onclick="javascript:location.href='/buyer/favorite_infos'">
                <span class="info-box-icon bg-yellow"><i class="fa fa-heart"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">收藏商品数量</span>
                    <span class="info-box-number">${StatsHelper.getFavoriteInfosCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/buyer/orders'">
                <span class="info-box-icon bg-blue"><i class="fa fa-sticky-note-o"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">订单数量</span>
                    <span class="info-box-number">${StatsHelper.getOrdersCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/buyer/orders'">
                <span class="info-box-icon bg-green"><i class="fa fa-cny"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">消费总金额</span>
                    <span class="info-box-number">${StatsHelper.getTotalConsume(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

        </div>
        <!-- /.box-body -->
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../includes/buyer/footer.jsp" />