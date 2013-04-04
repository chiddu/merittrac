<%@ page import="java.util.*,basic.*, servlet.*, org.json.* " %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0023)http://127.0.0.1/search -->
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Groofer</title>
<%@ include file="common.html" %>

</head>

<body>
<div id="minHeight"></div>
<div class="header" align="center">
<div class="div_header">
<div class="logo"><a href="javascript:void(0)"><img src="./images/logo01.gif" alt="Groofer" border="0"></a></div>
<div class="header_search"> 
</div>
<div class="clear"></div>
</div>
</div>
<div align="center">
<div class="div_header">
<div class="div_body_main">
<div class="div_body_mid_column3">
<%-- 
main should be dividied into 3 parts.
1) Top the error message
2) Display the entire html (acquired)
3) Display the bottom , if not 1st page -> type prev link
4) Botton: Focus the input to the page
--%>
<div class="mid_main3">
<form method=post action="process.jsp" >
<input type=hidden name=pageNo value='<%= pageNo %>' />
<div class="error_display" >
</div>
<div class="mid_form">
<%= huhahtml %>
</div>
<div class="mid_form">
<div class="bottom_submit> 
<div class="bottom_left" > 
<% if(pageNo != 0)
{
int prevPage = pageNo - 1;
%>
<a href='process.jsp?pageNo=<%= prevPage %>' >
<input type=button name=prev_butt value='<< Previous' onclick="window.location.href=process.jsp?pageNo=<%= prevPage %>" class='fin_button'/>
</a>
<%
}
%>
</div>
<div class="bottom_right" >
<input type=Submit name=submit value='Submit' class='fin_button'/>
</div>
</div>
</div>



</form>
</div>
</div>

<div class="div_body_right_column" style='display:none'>


</div>

<div class="clear"></div>
</div>
</div>
</div>
<div class="footer" align="center">
<div class="div_footer" align="left">© 2012-2013 Crunchtime Software Services LLP. </div>
</div>


</body></html>
