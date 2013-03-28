package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import basic.*;
import org.json.*;

public class AddField extends HttpServlet 
{


	public void writeResponse(HttpServletResponse response,
		JSONObject obj) throws Exception
		{
			PrintWriter out = response.getWriter();
			out.print(obj.toString());
		}
	/* we start with just the list of all the periods */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		try
		{
			response.setContentType("text/javascript");
			Container cns = Container.getContainer();

			String action = request.getParameter("action");

			/* The out object, we are all doing json here */
			JSONObject obj = new JSONObject();

			if(action == null)
			{
				obj.put("message" , "There seems to be an empty response fired ");
				writeResponse(response, obj);
			}

			BaseCass bms = cns.getStorage();

			if(action.equalsIgnoreCase("addfield"))
			{
				addfield(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addpage"))
			{
				addpage(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("listfields"))
			{
				listfields(bms,request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addtopage"))
			{
				// addtopage(request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addcondition"))
			{
				// addcondition(request,response, obj);
				return;
			}
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

			public Set<String> getFieldList(BaseCass bms) throws Exception
			{
					return getSet(bms, "misc", "fields", "list");
			}
			public Set<String> getSet(BaseCass bms,
				String colFamily, String key, String colkey) throws Exception
			{
				String instr = bms.getColData(colFamily, key,colkey);
				if(instr == null)
				{
					return new LinkedHashSet<String>();
				}
					
				JSONArray obo = new JSONArray(instr);
				Set<String> dosa = MtxUtil.convertJsonSet(obo);
				return dosa ;
			}

			public Set<String> getPageList(BaseCass bms) throws Exception
			{
				return getSet(bms, "misc", "pages", "list");
			}

			public void addfield(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getFieldList(bms);
				String name = request.getParameter("name");

				if(inf.contains(name))
				{
					obj.put("message", name + " is already a field, please choose another name ");
					writeResponse(response, obj);
					return;
				}

				String type = request.getParameter("type");
				bms.saveColumn("input_field", name, "type" , type);

				String values = request.getParameter("values");
				if(values != null)
				{
					bms.saveColumn("input_field", name, "values" , values);
				}

				inf.add(name);
				JSONArray jso = new JSONArray(inf);
				bms.saveColumn("misc", "fields", "list" , jso.toString());

				obj.put("message", "Added field successfully");
				writeResponse(response,obj);
				return;
			}

			public void addpage(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getPageList(bms);
				String title = request.getParameter("title");

				if(inf.contains(title))
				{
					obj.put("message", title + " is already a field, please choose another title ");
					writeResponse(response, obj);
					return;
				}
				inf.add(title);
				JSONArray jso = new JSONArray(inf);
				bms.saveColumn("misc", "pages", "list" , jso.toString());
				obj.put("message", "Added page successfully");
				return;
			}

			public void listfields(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getFieldList(bms);
				obj.put("fields", inf);
				for(String eachfield : inf)
				{
					HashMap<String,String> samo = bms.getColumns("input_field",eachfield);
					obj.put(eachfield,samo);
				}
				writeResponse(response, obj);
				return;
			}
			public void listpages(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getPageList(bms);
				obj.put("pages", inf);
				for(String eachfield : inf)
				{
					HashMap<String,String> samo = bms.getColumns("pages",eachfield);
					obj.put(eachfield,samo);
				}
				writeResponse(response, obj);
				return;
			}
}
