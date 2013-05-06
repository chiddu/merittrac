package servlet;


import basic.*;

import java.util.*;
import org.json.*;

import java.io.PrintStream;

public class Para extends BaseField
{

	/* Dummy constructor, for use in BaseField */
	public Para()
	{
	}
	private String m_title;

	/* To be used by entities like
	ParaCombo */
	public Para(String title)
	{
		super("Para");
		m_title = title;
	}

	public BaseField createNew(String intr)
  {
    Para newEnt = new Para(intr);
    return newEnt;
  }
	public BaseField createNew(JSONObject objRep)
  {
    Para newEnt = new Para(objRep);
    return newEnt;
  }
	public Para(JSONObject objRep)
  {
    super("Para");
    this.load(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
			m_title = objRep.getString("title");
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
		jsonRep.put("title",m_title);
		return jsonRep;
	}

	public String getHtml(BaseCass theBase, String inputId)
	{
		return "<div class='ct_para" + inStyle + "'>" + m_title + " </div>";
	}

}
