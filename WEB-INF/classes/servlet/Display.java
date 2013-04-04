package servlet;

import basic.*;


import java.util.*;
import org.json.*;

import java.io.PrintStream;

public class Display extends BaseField
{

	/* Dummy constructor, for use in BaseField */
	public Display()
	{
	}

	private String m_fieldName;

	/* To be used by entities like
	DisplayCombo */
	public Display(String fieldName)
	{
		super("Display");
		m_fieldName = fieldName.trim();
	}

	public BaseField createNew(JSONObject objRep)
  {
    Display newEnt = new Display(objRep);
    return newEnt;
  }
	public BaseField createNew(String intr)
  {
    Display newEnt = new Display(intr);
    return newEnt;
  }
	public Display(JSONObject objRep)
  {
    super("Display");
    this.load(objRep);
  }

	public void loadDetails(JSONObject objRep) 
	{
		try
		{
			m_fieldName = objRep.getString("fieldName");
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
		jsonRep.put("fieldName",m_fieldName);
		return jsonRep;
	}

	/* Get the value from the database 
	field_value -> inputid -> fieldname ->  value
*/
	public String getHtml(BaseCass theBase, String inputId)
	{
		String value = null;
		try
		{
			value = theBase.getColData("user_input", inputId, m_fieldName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		if(value == null)
			value = "<em> User-defined </em>";
		return "<div class='ct_display'> <div class='ct_fieldname'> "
		+ m_fieldName + 		
		"</div> <div class='ct_fieldvalue' > "
		+ value + 		
		"</div> </div>";
	}

}
