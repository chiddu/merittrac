<%@ page import="java.util.*,basic.*, servlet.*" %>
<%
String email = (String)session.getAttribute("email");
if(email == null)
{
	/* Include the login page, forget other logic */
		email = request.getParameter("email");
		if(email == null)
		{

	%>
	<%@ include file="login.jsp" %>
	<%
	return;
		}
	else
	{
		session.setAttribute("email",email);
	}
}


String strPageNum  = request.getParameter("pageNo");
BaseCass bms = new BaseCass("merittrac");

ArrayList<String> pages =  CassUtils.getPageList(bms);

String submitVal = (String)request.getParameter("submit");


Page targetPage = null;

int pageNo = 0;

if(strPageNum  == null)
{
	pageNo = 0;
}
else
{
	pageNo = Integer.parseInt(strPageNum);
}
ArrayList<String> errorMsgs = new ArrayList<String>();

if((submitVal != null) && submitVal.equals("Submit"))
{

	targetPage  = CassUtils.getPage(bms, pageNo);	
	ArrayList<String> fields = targetPage.getFieldList();
	for( String eachField : fields )
	{
		String exVal = request.getParameter(eachField);
		if(exVal != null)
		{
			bms.saveColumn("user_input", email, eachField, exVal);
		}
		else
		{
			errorMsgs.add(eachField + " needs to have a valid input");
		}
	}
	if(errorMsgs.size() == 0)
	{
			bms.saveColumn("user_pages", email, pageNo + "" , System.currentTimeMillis() + "");
			pageNo = pageNo + 1;
	}
}
else for(int i = 0; i < pageNo; i++)
{
	String tringbum = bms.getColData("user_pages",email,i+ "");
	if(tringbum == null)
	{
		pageNo = i;
		break;
	}

}
String pageTitle = pages.get(pageNo);
int pageMax = pages.size();



if(pageMax > pageNo)
{
	if(targetPage == null)
	{
		targetPage  = CassUtils.getPage(bms, pageNo);	
	}
	String huhahtml = targetPage.getHtml(bms , email);
%>
<%@ include file="page.jsp" %>
<%

}
else
{
	session.removeAttribute("email");
%>
<%@ include file="thankyou.jsp" %>
<%
}
%>
