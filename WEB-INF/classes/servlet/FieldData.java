package servlet;


import basic.*;

import java.util.*;
import org.json.*;

import java.io.PrintStream;

public class FieldData extends BaseField
{

	/* Dummy constructor, for use in BaseField */
	public FieldData()
	{
	}

	private String m_fieldName;

	/* To be used by entities like
	FieldDataCombo */
	public FieldData(String fieldName)
	{
		super("FieldData");
		m_fieldName = fieldName;
	}

	public BaseField createNew(JSONObject objRep)
  {
    FieldData newEnt = new FieldData(objRep);
    return newEnt;
  }
	public FieldData(JSONObject objRep)
  {
    super("FieldData");
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


	/* Two conditions. 
	If there is a condition associated with this field, then
	for the input value
	*/
	public String getHtml(BaseCass theBase, String inputId)
	{
		HashMap<String,String> cols = getColumns("input_field", m_name);
		String type = cols.get("type");
	if(type.equals("text"))
	{
		return "<div class='ct_field'> <div class='ct_fieldname'> "
		+ m_name + 	"</div> <div class='ct_fieldinput' > <input type=text name='" + m_name + "' > </input></div> </div>";
		
	}
	else  /* Its basically a drop down */
	{
		value = "N/A";
		return "<div class='ct_display'> <div class='ct_fieldname'> "
		+ m_fieldName + 		
		"</div> <div class='ct_fieldvalue' > "
		+ value + 		
		"</div> </div>";
	}

}

}
