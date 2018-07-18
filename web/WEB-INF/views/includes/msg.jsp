<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Helpers.MsgHelper" %>
<%if (session.getAttribute("msg") != null) {%>
<div class="callout callout-info">
    <h4>消息</h4>

    <p>${msg}</p>
</div>
<%
        MsgHelper.removeMsg(session);
    }
%>
