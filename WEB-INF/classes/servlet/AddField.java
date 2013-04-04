package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import basic.*;
import org.json.*;

public class AddField extends HttpServlet 
{

	public static HashSet<String> longs;
	static
	{
		longs = new HashSet<String>();
		longs.add("page");
	}
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
				return;
			}

			BaseCass bms = cns.getStorage();

		// System.out.println("Action is " + action);


//			Thread.sleep(5000);
			if(action.equalsIgnoreCase("addfield"))
			{
				addfield(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addcondition"))
			{
				addcondition(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addpage"))
			{
				addpage(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("gethtml"))
			{
				gethtml(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("listfields"))
			{
				listfields(bms,request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("addtopage"))
			{
				addtopage(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("removefrompage"))
			{
				removefrompage(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("listpages"))
			{
				listpages(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("listpages2"))
			{
				listfields(bms,request,response, obj,false);
				listpages(bms, request,response, obj);
				return;
			}
			else if(action.equalsIgnoreCase("showall"))
			{
				showall(bms,request,response, obj);
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


			public void removecondition(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				String name = request.getParameter("name");
				HashMap<String,String> conditions = bms.getColumns("condition",name);

				Set<String> keys = conditions.keySet();

				for(String eachkey : keys)
				{
					bms.deleteKey("condition", eachkey, name);
					bms.deleteKey("condition", name, eachkey);
				}
				obj.put("result", "success");
				writeResponse(response,obj);
				return;
			}

			public void addcondition(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				String field1 = request.getParameter("field1");
				String field2 = request.getParameter("field2");
				String cond1  = request.getParameter("values1");
				String outcome1 = request.getParameter("values2");
				String name = request.getParameter("name");

				JSONArray condArr = new JSONArray(cond1);
				JSONArray outArr = new JSONArray(outcome1);
				JSONObject obuja = new JSONObject();
				obuja.put(field1,condArr);
				obuja.put("a1b2c3", outArr);

				bms.saveColumn("condition", field2, name , obuja.toString());
				bms.saveColumn("condition", name, field2 , obuja.toString());

				obj.put("message", "The conditional rule has been added to the database");
				writeResponse(response,obj);
				return;
			}

			public void addfield(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getFieldList(bms);
				String name = request.getParameter("name");

				for(String eacho : inf)
				{
			// // System.out.println(eacho);
				// System.out.println(name);
				}
				// Thread.sleep(2000);
				if(inf.contains(name))
				{
					obj.put("message", name + " is already a field, please choose another name ");
					obj.put("action","boogie");
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
					obj.put("message", title + " is already a page, please choose another title ");
					
					writeResponse(response, obj);
					return;
				}
				inf.add(title);
				JSONArray jso = new JSONArray(inf);
				bms.saveColumn("misc", "pages", "list" , jso.toString());
				obj.put("message", "Added page successfully");
				obj.put("action","refreshPages");
				writeResponse(response, obj);

				return;
			}


/*
			public HashMap<String, String>  getFieldMap(Set<String> inmap)
			{
				HashMap<String, String> fieldMap  = new HashMap<String, String>();
				for(String eachkey : inmap)
				{
					String testmax = bms.getColData("input_field", eachkey, "page");
					if(testmax == null)
						continue;
					fieldMap.put(eachkey, testmax);
				}
				return fieldMap;
			}
			*/
			/* Return 2 sets of data
			a) List of available fields
			b) List of fields v/s pageid(s)
			*/

			public void listfields(BaseCass bms, HttpServletRequest request, 
				HttpServletResponse response, JSONObject obj) throws Exception
				{
					listfields(bms, request, response, obj, true);
				}

			public void listconditions(BaseCass bms, HttpServletRequest request, 
				HttpServletResponse response, JSONObject obj, boolean toWrite) throws Exception
			{
				Set<String> inf = getFieldList(bms);
				for(String eachfield : inf)
				{
					HashMap<String,String> samo = bms.getColumns("condition",eachfield);
					Set<String> keySet = samo.keySet();
					for(String key : keySet)
					{
						obj.put(key,samo.get(key));
					}
				}
				writeResponse(response, obj);
				return;
			}

			public void listfields(BaseCass bms, HttpServletRequest request, 
				HttpServletResponse response, JSONObject obj, boolean toWrite) throws Exception
			{
				Set<String> inf = getFieldList(bms);
				obj.put("fields", inf);
				for(String eachfield : inf)
				{
					HashMap<String,String> samo = bms.getColumns("input_field",eachfield);
					obj.put(eachfield,samo);
				}
				if(toWrite)
				writeResponse(response, obj);
				return;
			}
			public void listpages(BaseCass bms, HttpServletRequest request, 
				HttpServletResponse response, JSONObject obj) throws Exception
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


			public void gethtml(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getPageList(bms);

				String pageId = request.getParameter("pageId");

				if(pageId  == null)
				{
					obj.put("message" , "You apparently did not pass on any pageId");
					writeResponse(response, obj);
					return;
				}

				Page thePage =  retrievePage(bms,pageId);

				obj.put("html", thePage.getHtml(bms,null));

				writeResponse(response, obj);
				return;
			}

			/* Get a list of pageNames v/s pageIndex
			pageIndex => fieldName => 
			input_field => fieldName => 'page' => pageNumber

			/* Everytime a field is added to the page, the fieldname, and the page is passed to the server. along with the index  */
			/* Add  a new array for the fieldname v/s the page */

			public void addtopage(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				Set<String> inf = getPageList(bms);

				String pageId = request.getParameter("pageId");
				String field = request.getParameter("field");
				String type = request.getParameter("type");

				System.out.println( "DOOD"
				+ ":" + pageId  
				+ ":" + field
				+ ":" + type);

				if(field  == null)
				{
					obj.put("message" , "You apparently did not pass on any field");
					writeResponse(response, obj);
					return;
				}
				if(pageId  == null)
				{
					obj.put("message" , "You apparently did not pass on any pageId");
					writeResponse(response, obj);
					return;
				}
				BaseField bf = BaseField.createNew(type, field);
				if(bf == null)
				{
					obj.put("message" , "The type '"+type + "' did not return anything from the database ");
					writeResponse(response, obj);
					return;
				}

				Page thePage = retrievePage(bms,pageId);

				thePage.addField(bf);
				savePage(bms,thePage,pageId);

				if(type.equals("FieldData"))
				{
					Long pLong = Long.parseLong(pageId);
					bms.saveColumn("input_field", field, "page" , pageId);
					obj.put("message" , "The field " + field + " is now in page " + pageId);
				}
				else
				{
					obj.put("message" , type + " with data has been added");
				}

				obj.put("html", bf.getHtml(bms,null));

				writeResponse(response, obj);
				return;
			}

			public void showall(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{
				String query = "select * from user_input ";
				HashMap<String, HashMap<String,String>> bc = bms.getTwoDimKey("select * from user_input ");
				obj.put("all", bc);
				writeResponse(response, obj);
				return;
			}

			public Page retrievePage(BaseCass bms, String pageId)
			{
				Page thePage = null;
				String pageBlob = bms.getColData("pages", pageId, "dummy");
				if(pageBlob == null)
				{
						thePage = new Page();
				}
				else
				{
						thePage = new Page(pageBlob);
				}
				return thePage;
			}

				public void savePage(BaseCass bms, Page thePage, String pageId)
				{
					bms.saveColumn("pages", pageId, "dummy", thePage.toJSONString());
				}

			public synchronized void removefrompage(BaseCass bms, HttpServletRequest request, HttpServletResponse response, JSONObject obj) throws Exception
			{

				String pageId = request.getParameter("pageId");
				String index = request.getParameter("index");

				Page thePage = retrievePage(bms, pageId);
				int intParse =  Integer.parseInt(index);
				BaseField bf = thePage.remove(intParse);
				if(bf == null)
				{
					obj.put("message" , "The field in question does not seem to exist ");
					writeResponse(response, obj);
					return;
				}
				if(bf.getType().equals("FieldData"))
				{
					FieldData fd = (FieldData)bf;
					bms.deleteKey("input_field", fd.getName(), "page");
				}
				savePage(bms,thePage,pageId);
				obj.put("message" , "Field successfully removed");
				writeResponse(response, obj);
				return;
			}
}

