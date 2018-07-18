<%@ page import="Models.ItemsEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/buyer/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">购物车商品列表</h3>
        </div>
        <div class="box-body">
            <c:if test="${List.isEmpty()}">
                没有东西喵~
            </c:if>
            <ul class="products-list product-list-in-box">
                <c:forEach items="${List}" var="single_cart_info">
                    <c:set var="single_item" value="${single_cart_info.item}"/>
                    <li class="item">
                        <div class="product-img">
                            <a href="/buyer/items/detail?id=${single_item.id}"><img src="${single_item.itemImgUri}" alt="Product Image"></a>
                        </div>
                        <div class="product-info">
                            <a href="/buyer/items/detail?id=${single_item.id}" class="product-title">${single_item.itemName}
                                <span class="label label-warning pull-right">￥${single_item.itemPrice}</span></a><br>
                                <span class="label label-danger pull-right">
                                    <form role="form" method="post">
                                        <jsp:include flush="true" page="../../includes/form.jsp" />
                                        <button type="submit" class="btn btn-danger btn-xs" onclick="javascript: form.action='./cart_infos/delete?item_id=${single_item.getId()}&page=${currentPage}';"><span class="fa fa-trash"></span></button>
                                    </form>
                                </span><br>
                            </a>
                            <span class="product-description">
                                <c:if test="${single_item.status == ItemsEntity.STATUS_OFF}">
                                    <span class="label label-danger">已下架</span></a><br>
                                </c:if>
                                已售出 ${single_item.itemSold} 件 / 还剩 ${single_item.itemSku} 件
                            </span>
                            <span class="product-description">
                                <form role="form" method="post">
                                    <jsp:include flush="true" page="../../includes/form.jsp" />
                                    数量：<button type="submit" class="btn btn-default btn-xs" onclick="javascript: form.action='./cart_infos/edit?item_id=${single_item.getId()}&page=${currentPage}&operation=-1';"><span class="fa fa-minus"></span></button>
                                    ${single_cart_info.itemAmount}
                                    <button type="submit" class="btn btn-default btn-xs" onclick="javascript: form.action='./cart_infos/edit?item_id=${single_item.getId()}&page=${currentPage}&operation=1';"><span class="fa fa-plus"></span></button>
                                </form>
                            </span>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <a href="/buyer/orders/order_pre" role="button" class="btn btn-info"><span class="fa fa-check"> 结账</span></a>
            <jsp:include flush="true" page="../../includes/pagenation.jsp" />
        </div>
        <!-- /.box-footer -->
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../../includes/buyer/footer.jsp" />


