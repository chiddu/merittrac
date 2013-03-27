package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import proto.*;
import org.json.*;

public class AddField extends HttpServlet 
{
		/* we start with just the list of all the periods */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
			try
			{
        response.setContentType("text/javascript");
				Container cns = Container.getContainer();
				CassStorage rms = cns.getStorage();

				String name = request.getParameter("name");
				String type = request.getParameter("type");
				rms.saveColumn("input_field", name, "type" , type);
        PrintWriter out = response.getWriter();

				JSONObject obj = new JSONObject();
				obj.put("type", type);
				obj.put("name", name);
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
