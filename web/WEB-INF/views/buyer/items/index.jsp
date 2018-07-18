<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/buyer/top_header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">商品搜索</h3>
        </div>
        <form action="./items" method="get">
            <div class="box-body">
                <div class="form-group">
                    <input type="text" name="keyword" class="form-control" placeholder="您想来点啥？" value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword"): ""%>">
                </div>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
                <button type="submit" class="btn btn-primary">搜索</button>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>
    <!-- /.box-default -->

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">商品列表</h3>
        </div>
        <div class="box-body">
            <c:if test="${List.isEmpty()}">
                没有东西喵~
            </c:if>
            <ul class="products-list product-list-in-box">
                <c:forEach items="${List}" var="single_item">
                    <li class="item">
                        <div class="product-img">
                            <a href="/buyer/items/detail?id=${single_item.id}"><img src="${single_item.itemImgUri}" alt="Product Image"></a>
                        </div>
                        <div class="product-info">
                            <a href="/buyer/items/detail?id=${single_item.id}" class="product-title">${single_item.itemName}
                                <span class="label label-warning pull-right">￥${single_item.itemPrice}</span></a>
                            <span class="product-description">
                                已售出 ${single_item.itemSold} 件 / 还剩 ${single_item.itemSku} 件
                            </span>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!-- /.box-body -->
        <div class="box-footer clearfix">
            <jsp:include flush="true" page="../../includes/pagenation.jsp" />
        </div>
        <!-- /.box-footer -->
    </div>
    <!-- /.box-default -->


<jsp:include flush="true" page="../../includes/buyer/top_footer.jsp" />


