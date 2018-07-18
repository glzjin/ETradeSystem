<%@ page import="Helpers.StatsHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/seller/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">首页</h3>
        </div>
        <div class="box-body">

            <div class="info-box" onclick="javascript:location.href='/seller/items'">
                <span class="info-box-icon bg-yellow"><i class="fa fa-gift"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">商品数量</span>
                    <span class="info-box-number">${StatsHelper.getItemsCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/seller/orders'">
                <span class="info-box-icon bg-blue"><i class="fa fa-sticky-note-o"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">订单数量</span>
                    <span class="info-box-number">${StatsHelper.getOrdersCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box" onclick="javascript:location.href='/seller/rank_infos'">
                <span class="info-box-icon bg-green"><i class="fa fa-smile-o"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">评价数量</span>
                    <span class="info-box-number">${StatsHelper.getRankInfosCount(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/seller/orders'">
                <span class="info-box-icon bg-red"><i class="fa fa-cny"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">交易总金额</span>
                    <span class="info-box-number">${StatsHelper.getTotalIncome(user)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>
        </div>
        <!-- /.box-body -->
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../includes/seller/footer.jsp" />