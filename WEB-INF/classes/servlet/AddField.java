package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import proto.*;
import org.json.*;

public class AddField extends HttpServlet 
{


		public writeResponse(HttpServletResponse response,
			JSONObject obj)
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

				if(!action)
				{
					obj.put("message" , "There seems to be an empty response fired ");
					writeResponse(response, obj);
				}

				BaseCass rms = cns.getStorage();

				if(action.equalsIgnoreCase("addfield"))
				{
					addfield(request,response, obj);
				}
				else if(action.equalsIgnoreCase("addpage"))
				{
					addpage(request,response, obj);
				}
				else if(action.equalsIgnoreCase("listfields"))
				{
					listfields(request,response, obj);
				}
				else if(action.equalsIgnoreCase("addtopage"))
				{
					addtopage(request,response, obj);
				}
				else if(action.equalsIgnoreCase("addcondition"))
				{
					addcondition(request,response, obj);
				}

				JSONObject obj = new JSONObject();
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

				public Set<String> getFieldList(BaseCass rms)
				{
					String instr = rms.getColData("misc", "fields","list");
					Set<String> = MtxUtil.convertJsonToSet(instr);
					return instr;
				}
				public Set<String> getPageList(BaseCass rms)
				{
					String instr = rms.getColData("misc", "pages","list");
					Set<String> = MtxUtil.convertJsonToSet(instr);
					return instr;
				}

				public void addfield((BaseCass rms, HttpServletRequest request, HttpServletResponse response, JSONObject obj)
				{
					Set<String> inf = getFieldList(rns);
					String name = request.getParameter("name");

					if(inf.contains(name))
					{
						obj.put("message", name + " is already a field, please choose another name ");
						writeReponse(response, obj);
						return;
					}

					String type = request.getParameter("type");
					rms.saveColumn("input_field", name, "type" , type);

					String values = request.getParameter("values");
					if(values != null)
					{
						rms.saveColumn("input_field", name, "values" , values);
					}

					inf.add(name);
					JSONArray jso = new JSONArray(inf);
					rms.saveColumn("misc", "fields", "list" , jso.toString())

					obj.put("message", "Added field successfully");
//					getfields(request,response, obj);
					return;
				}

				public void addpage((HttpServletRequest request, HttpServletResponse response, JSONObject obj)
				{
					Set<String> inf = getPageList(rns);
					String title = request.getParameter("title");

					if(inf.contains(title))
					{
						obj.put("message", title + " is already a field, please choose another title ");
						writeReponse(response, obj);
						return;
					}
					inf.add(title);
					JSONArray jso = new JSONArray(inf);
					rms.saveColumn("misc", "pages", "list" , jso.toString())
					obj.put("message", "Added page successfully");
					return;
				}

				public void listfields((HttpServletRequest request, HttpServletResponse response, JSONObject obj)
				{
					Set<String> inf = getFieldList(rns);
					obj.put("fields", inf);
					for(String eachfield : inf)
					{
						HashMap<String,String> samo = cms.		
					}
					String title = request.getParameter("title");

					if(inf.contains(title))
					{
						obj.put("message", title + " is already a field, please choose another title ");
						writeReponse(response, obj);
						return;
					}
					inf.add(title);
					JSONArray jso = new JSONArray(inf);
					rms.saveColumn("misc", "pages", "list" , jso.toString())
					obj.put("message", "Added page successfully");
					return;
				}
}
