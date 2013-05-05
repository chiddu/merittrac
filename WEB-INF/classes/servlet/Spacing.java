package servlet;


import basic.*;

import java.util.*;
import org.json.*;

import java.io.PrintStream;

public class Spacing extends BaseField
{

	/* Dummy constructor, for use in BaseField */
	public Spacing()
	{
	}

	private int m_pixels;

	/* To be used by entities like
	ParaCombo */
	public Spacing(String title)
	{
		super("Spacing");
		m_pixels  = Integer.parseInt(title);
	}

	public BaseField createNew(String intr)
  {
    Spacing newEnt = new Spacing(intr);
    return newEnt;
  }
	public BaseField createNew(JSONObject objRep)
  {
    Spacing newEnt = new Spacing(objRep);
    return newEnt;
  }
	public Spacing(JSONObject objRep)
  {
    super("Spacing");
    this.load(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
			m_pixels = objRep.getInt("pixels");
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
		jsonRep.put("pixels",m_pixels);
		return jsonRep;
	}

	public String getHtml(BaseCass theBase, String inputId)
	{
		StringBuffer bf = new StringBuffer();
		for(int i = 0 ; i < m_pixels; i++)
		{
			bf.append( "<div class='ct_clear' ></div>\n");
		}
		return bf.toString();
	}


}
