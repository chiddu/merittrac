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
Hello!  The time is now <%= getDate() %><br>
Hello!  The time is now <%= dd  %>
</BODY>
</HTML>
