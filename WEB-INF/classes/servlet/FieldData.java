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

	public String getName()
	{
		return m_fieldName;
	}

	/* To be used by entities like
	FieldDataCombo */
	public FieldData(String fieldName)
	{
		super("FieldData");
		m_fieldName = fieldName.trim();
	}

	public BaseField createNew(String instr)
  {
    FieldData newEnt = new FieldData(instr);
    return newEnt;
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
	try
	{
		HashMap<String,String> cols = theBase.getColumns("input_field", m_fieldName);
		String type = cols.get("type");
		if(type.equals("text"))
		{
			return "<div class='ct_field'> <div class='ct_fieldname'> "
			+ m_fieldName + 	"</div> <div class='ct_fieldinput' > <input type=text name='" + m_fieldName + "' > </input></div> </div>";
			
		}
		else  /* Its basically a drop down */
		{
			StringBuffer buff = new StringBuffer();
			buff.append( "<div class='ct_field'> <div class='ct_fieldname'> ");
			buff.append(m_fieldName);
			buff.append("</div>");
			buff.append("<div class='ct_selectinput' > ");
			buff.append("<select name='");
			buff.append(m_fieldName);
			buff.append("' id='");
			buff.append(m_fieldName);
			buff.append("' >");
			
			String values = cols.get("values");
			JSONArray arrata = new JSONArray(values);

			int len = arrata.length();
			for(int i = 0; i < len; i++)
			{
				String val = (String)arrata.get(i);
				buff.append("<option id='>");
				buff.append("' value='>");
				buff.append(val);
				buff.append("</option>");
			}
			buff.append("</select>");
			buff.append("</div></div>");
			return buff.toString();
	}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return "";

}

}
