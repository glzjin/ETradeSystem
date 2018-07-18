<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="pagination pagination-sm no-margin pull-right">
    <c:forEach var="i" begin="1" end="${totalPage}">
        <c:if test="${currentPage == i}">
            <li class="active"><a href="#">${i}</a></li>
        </c:if>
        <c:if test="${currentPage != i}">
            <li><a href="${requestScope['javax.servlet.forward.request_uri']}?page=${i}">${i}</a></li>
        </c:if>
    </c:forEach>
</ul>