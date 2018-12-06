<%--
  Created by IntelliJ IDEA.
  User: link
  Date: 2018/12/2
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%
  String contextPath = request.getServletContext().getContextPath();
  response.sendRedirect(contextPath+"/forehome");
%>
