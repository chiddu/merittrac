<%@ page import="java.util.*" %>
<HTML>
<BODY>
<%!
    Date theDate = new Date();
    Date getDate()
    {
        System.out.println( "In getDate() method" );
        return theDate;
    }
%>
<%
Date dd = new Date();
%>
<%@include file="pair2.jsp" %>
</BODY>
</HTML>
