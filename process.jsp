<%@ page import="java.util.*,basic.*, servlet.*" %>
<%
Object email = session.getAttribute("email");
if(email == null)
{
	/* Include the login page, forget other logic */
		email = request.getParameter("email");
		if(email == null)
		{

	%>
	<%@ include file="login.jsp" %>
	<%
	}
	else
	{
		session.setAttribute("email",email);
	}

	return;
}


%>


