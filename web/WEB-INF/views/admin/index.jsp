<%@ page import="Helpers.StatsHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include flush="true" page="../includes/admin/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">首页</h3>
        </div>
        <div class="box-body">
            <div class="info-box" onclick="javascript:location.href='/admin/users'">
                <span class="info-box-icon bg-red"><i class="fa fa-users"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">用户数量</span>
                    <span class="info-box-number">${StatsHelper.getUsersCount()}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box" onclick="javascript:location.href='/admin/items'">
                <span class="info-box-icon bg-yellow"><i class="fa fa-gift"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">商品数量</span>
                    <span class="info-box-number">${StatsHelper.getItemsCount(null)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/admin/orders'">
                <span class="info-box-icon bg-blue"><i class="fa fa-sticky-note-o"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">订单数量</span>
                    <span class="info-box-number">${StatsHelper.getOrdersCount(null)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>

            <div class="info-box"  onclick="javascript:location.href='/admin/orders'">
                <span class="info-box-icon bg-green"><i class="fa fa-cny"></i></span>

                <div class="info-box-content">
                    <span class="info-box-text">交易总金额</span>
                    <span class="info-box-number">${StatsHelper.getTotalIncome(null)}</span>
                </div>
                <!-- /.info-box-content -->
            </div>
        </div>
        <!-- /.box-body -->
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../includes/admin/footer.jsp" />