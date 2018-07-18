<%@ page import="Helpers.RankHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/admin/header.jsp" />

<div class="box box-default">
    <div class="box-header with-border">
        <h3 class="box-title">${Item.itemName}</h3>
        <div>
            <span class="label label-success">卖家：${Item.user.userName}(ID: ${Item.user.id})</span> <span class="label label-warning">价格:￥${Item.itemPrice}</span> <span class="label label-danger">已售出 ${Item.itemSold} 件 / 还剩 ${Item.itemSku} 件</span>
        </div>
    </div>
    <div class="box-body">
        <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#description" data-toggle="tab">描述</a></li>
                <li><a href="#ranks" data-toggle="tab">评分</a></li>
            </ul>
            <div class="tab-content">
                <div class="active tab-pane" id="description">
                    ${Item.itemDescription}
                </div>
                <div class="tab-pane" id="ranks">
                    平均评分：${RankHelper.avrScore(Item)}
                    <c:set var="currentPage" value='${param["page"] == null ? 1 : param["page"]}'/>
                    <c:forEach items="${RankHelper.rankList(Item, currentPage)}" var="single_rank">
                        <!-- Post -->
                        <div class="post clearfix">
                            <div class="user-block">
                                <span class="username">
                                    ${single_rank.order.user.userName}
                                    <span class="rating pull-right btn-box-tool" data-default-rating="${single_rank.rankScore}" data-stars="5" disabled=""></span><br>
                                </span>
                            </div>
                            <!-- /.user-block -->
                            <p>
                                    ${single_rank.rankDetail}
                            </p>
                        </div>
                        <!-- /.post -->
                    </c:forEach>
                    <c:set var="totalPage" value='${RankHelper.rankListTotalPage(Item)}'/>
                    <ul class="pagination pagination-sm">
                        <c:forEach var="i" begin="1" end="${totalPage}">
                            <c:if test="${currentPage == i}">
                                <li class="active"><a href="#">${i}</a></li>
                            </c:if>
                            <c:if test="${currentPage != i}">
                                <li><a href="./detail?page=${i}">${i}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- /.box-body -->
</div>
<!-- /.box-default -->


<jsp:include flush="true" page="../../includes/admin/footer.jsp" />