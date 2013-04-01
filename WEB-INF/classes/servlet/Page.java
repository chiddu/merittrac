package servlet;

import basic.*;

import java.util.*;
import org.json.*;
import java.io.PrintStream;


public class Page 
{

	/* Dummy constructor, for use in BaseField */

	private LinkedHashSet<BaseField> m_baseList;
	private int pageId;
	private String m_title;

	public Page()
	{
	}

	public Page(int pId, String title)
	{
		pageId  = pId;
		m_title = title;
	}

	/* Duplicates are already discarded. */
	public void addField(BaseField newf)
	{
		m_baseList.add(newf);
	}

	public Page(JSONObject objRep)
  {
    this.loadDetails(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
			Object parentObj = objRep.get("list");
			if(parentObj != null)
			{
				JSONArray arr = (JSONArray)parentObj;
				int len = arr.length();
				for(int i = 0 ; i < len ; i++)
				{
					String inStr = (String) arr.getString(i);
					BaseField bf = BaseField.restoreToObject(inStr) ;
					m_baseList.add(bf);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public String toJSONString()
	{
		try
		{
			JSONObject oka = this.toJSONObject();
			return oka.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
	public JSONObject toJSONObject() throws JSONException
  {
    JSONObject  jsonRep = new JSONObject();
		ArrayList<String> tstr = new ArrayList<String>();
		for(BaseField bsf : m_baseList)
		{
			tstr.add(bsf.toString());
		}
		jsonRep.put("list", tstr);
		return jsonRep;
	}
	
	public String getHtml(BaseCass theBase, String inputId)
	{
		StringBuffer buff = new StringBuffer();
		for(BaseField bf : m_baseList)
		{
			String goba = bf.getHtml(theBase, inputId);
			buff.append(goba);
		}
		return buff.toString();
	}

}
