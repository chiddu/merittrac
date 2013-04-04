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
		}
	else
	{
		session.setAttribute("email",email);
	}
}

String submitValue =  request.getParameter("submit");

String strPageNum  = request.getParameter("pageNo");
BaseCass bms = new BaseCass("merittrac");

ArrayList<String> pages =  CassUtils.getPageList(bms);

int pageNo = 0;

if(strPageNum  == null)
{
	pageNo = 0;
}
else
{
	pageNo = Integer.parseInt(strPageNum);
}


for(int i = 0; i < pageNo; i++)
{
	String tringbum = bms.getColData("user_pages",email,i+ "");
	if(tringbum == null)
	{
		pageNo = i;
		break;
	}

}

String pageTitle = pages.get(pageNo);

String pageStr  = pageNo + "";
String pageData = bms.getColData("pages",pageStr,"dummy");
Page targetPage = new Page(pageData);

int pageMax = pages.size();

String huhahtml = targetPage.getHtml(bms , email);

%>
<%@ include file="page.jsp" %>
