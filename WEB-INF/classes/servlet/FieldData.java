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

		String value = null;
		HashMap<String,String> cols = theBase.getColumns("input_field", m_fieldName);
		if(inputId != null)	
		{
			value = theBase.getColData("user_input", inputId, m_fieldName);
		}
		String type = cols.get("type");
		if(type == null)
			return "";

		if(type.equals("text"))
		{
			if(value != null)
			{
			return "<div class='ct_field'> <div class='ct_fieldname'> "
			+ m_fieldName + 	"</div> <div class='ct_fieldinput' > <input type=text name='" + m_fieldName + "' value='" +  value + "'> </input></div> </div>";
			}
			else
			{
			return "<div class='ct_field'> <div class='ct_fieldname'> "
			+ m_fieldName + 	"</div> <div class='ct_fieldinput' > <input type=text name='" + m_fieldName + "' > </input></div> </div>";
			}
			
		}
		else  
		{
			/* We need to worry only if the user has added  a previous variable */

			HashMap<String,String> conds = theBase.getColumns("condition", m_fieldName);
			Set<String> finalVals = null;


			/* The input condition covers for the non user preview part */
			if((inputId != null) && (conds.size() != 0))
			{

				/* We are not interested in the names of the conditions */
				Collection<String> rawData = conds.values();


				String values = cols.get("values");
				JSONArray arrata = new JSONArray(values);
				finalVals = MtxUtil.convertJsonSet(arrata);
				Set<String> holderVals  = null;

				for(String raw : rawData) /* key is dummy */
				{
					JSONObject obuya = new JSONObject(raw);
					JSONArray outVals = obuya.getJSONArray("output");
					JSONArray inVals = obuya.getJSONArray("input");
					String fname =  obuya.getString("fieldName");
					String invalue = theBase.getColData("user_input", inputId, fname);
					if(invalue == null)
					{
						continue;
					}
					Set<String> inputVals = MtxUtil.convertJsonSet(inVals);
					if(inputVals.contains(invalue))
					{
						holderVals = finalVals; /* Move current finalvals to a holder */
						finalVals = new HashSet<String>();

						Set<String> targetVals = MtxUtil.convertJsonSet(outVals);

						for(String eachVal : targetVals)
						{
							if(holderVals.contains(eachVal))
							{
								finalVals.add(eachVal);
							}
						}
					}
				}

			}
			/* Now we go through each one and then narrow down the list of values */


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

			if(finalVals == null)
				finalVals = MtxUtil.convertJsonSet(arrata);

			if(value == null)
				buff.append("<option value='' selected ></option>");

			int len = arrata.length();
			for(String val : finalVals)
			{
				buff.append("<option id='");
				buff.append(val);
				buff.append("' value='");
				buff.append(val);
				buff.append("' ");
				if((value != null) && value.equals(val))
				{
				buff.append("selected");
				}
				buff.append(" >");
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
