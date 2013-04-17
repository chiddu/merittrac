package servlet;

import basic.*;

import java.util.*;
import org.json.*;
import java.io.PrintStream;


public class Page 
{

	/* Dummy constructor, for use in BaseField */

	private ArrayList<BaseField> m_baseList;
	private int pageId;
	private String m_title;

	public Page()
	{
	}

	public ArrayList<String> getFieldList()
	{
		ArrayList<String> list2 = new ArrayList<String>();
		for(BaseField bsf : m_baseList)
		{
			if(bsf.getType().equals("FieldData"))
			list2.add( ((FieldData)bsf).getName());
		}
		return list2;
	}
	public Page(int pId, String title)
	{
		pageId  = pId;
		m_title = title;
	}

	/* Duplicates are already discarded. */
	public void addField(BaseField newf)
	{
		if(m_baseList == null)
			m_baseList  = new ArrayList<BaseField>();
		m_baseList.add(newf);
	}

	public Page(String inval)
  {
		try
		{
			JSONObject objRep = new JSONObject(inval);
			this.loadDetails(objRep);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
  }

	public Page(JSONObject objRep)
  {
    this.loadDetails(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
			m_baseList  = new ArrayList<BaseField>();
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


	public BaseField remove(int i)
	{
		try
		{
			return  m_baseList.remove(i);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}


	public BaseField get(int i)
	{
		try
		{
			return  m_baseList.get(i);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public JSONObject toJSONObject() throws JSONException
  {
    JSONObject  jsonRep = new JSONObject();
		ArrayList<String> tstr = new ArrayList<String>();
		if(m_baseList != null)
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
		if(m_baseList != null)
		{
			for(BaseField bf : m_baseList)
			{
				String goba = bf.getHtml(theBase, inputId);
				buff.append(goba);
			}
		}
		return buff.toString();
	}

}
