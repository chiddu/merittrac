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
    super("Page");
    this.load(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
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
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public JSONObject toJSONObject() throws JSONException
  {
    JSONObject  jsonRep = new JSONObject();
    this.getBaseStuff(jsonRep);
    if(entityList != null)
      jsonRep.put("list", entityList);
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
