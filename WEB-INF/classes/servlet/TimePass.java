package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import basic.*;
import org.json.*;

public class TimePass extends HttpServlet 
{
		/* we start with just the list of all the periods */
		private int dil = 0;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
			try
			{
        response.setContentType("text/javascript");
				dil = dil + 1;

        PrintWriter out = response.getWriter();
				out.print(dil);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        doGet(request, response);
    }
}
