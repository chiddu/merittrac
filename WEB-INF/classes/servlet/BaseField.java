package servlet;


import basic.*;



import java.util.*;
import java.io.File;
import java.io.PrintStream;

import org.json.*;


public abstract class BaseField 
{

	protected  String type;

	private static HashMap<String,BaseField> masterlist;

	static 
	{
		/* This has to contain an exhaustive list of all BaseFields in 
			the system. No shortcut right now */
		/* The key here is the type of Entity */
		masterlist = new HashMap<String,BaseField>() ; 

		masterlist.put("FieldData", new FieldData());
		masterlist.put("Display", new Display());
		masterlist.put("Para", new Para());
		masterlist.put("Spacing", new Spacing());
	}


	/* No real need for defining this abstract, but this will ensure
	that any derived class will define this method and there will be
	consistency */
	public void load(JSONObject objRep)  
	{
		this.commonRestore(objRep);
		this.loadDetails(objRep);
	}

	public static BaseField restoreToObject(String inStr) 
	{
		try
		{
			JSONObject jsonRep = new JSONObject(inStr); 
			BaseField bf = BaseField.restoreToObject(jsonRep);
			return bf;
		}
		catch (Exception ex)
		{
			System.out.println(inStr);
			ex.printStackTrace();
		}
		return null;
	}

	public static BaseField createNew(String intr, String fieldVal)
	{
		BaseField basa = masterlist.get(intr);
		if(basa != null)
		{
				basa = basa.createNew(fieldVal);
		}
		return basa;
	}

	public static BaseField restoreToObject(JSONObject jsonRep)  throws Exception
	{
		String typeStr = "type";
		String  type = (String)jsonRep.get("type") ;
		BaseField unit = masterlist.get(type);
		BaseField finalunit = unit.createNew(jsonRep);
		finalunit.type  = type;
		return finalunit;
	}
	public BaseField(BaseField inType)
	{
		this.type = inType.toString();
	}

	public String getType()
	{
		return this.type;
	}

	public BaseField(String inType)
	{
		this.type = inType;
	}

	/* Dummy used only for the factory 
		constructors */
	protected BaseField()
	{
	}

	public BaseField(JSONObject inObj) throws JSONException
	{
			this.type = (String)inObj.get("type") ;
	}

	public boolean equals(Object rhsObj)
	{

		String n1 = this.getClass().getName();	
		String n2 = rhsObj.getClass().getName();	

		if(n1.equals(n2))
		{
			BaseField u1 = (BaseField) rhsObj;
			if(u1.getType().equals( this.getType()))
				return true;
		}
		return false;
	}

	public void commonRestore( JSONObject jsonRep) 
	{
		try
		{
			this.type = jsonRep.getString("type");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void getBaseStuff(JSONObject jsonRep) throws JSONException
	{
		jsonRep.put( "type", this.type );
	}

 public void print(PrintStream strm,String tab)
 {
   strm.println(tab + "Begin BaseField");
	 strm.println(tab + "type      = " + this.type);
   strm.println(tab + "End BaseField");
	}

 public void printBrief(PrintStream strm,String tab)
 {
	 strm.print( "type      = " + this.type + "," );
	}

	public String toString() 
	{
		String retVal = null;
		try
		{
			retVal  =  toJsonString(); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return retVal;
	}


	public String toJsonString() throws JSONException
	{
		JSONObject  jsonRep = this.toJSONObject();
		return jsonRep.toString();
	}

	public abstract BaseField createNew(JSONObject objRep) ;
	public abstract BaseField createNew(String input) ;
	public abstract JSONObject toJSONObject() throws JSONException;
	public abstract void loadDetails(JSONObject objRep);
	public abstract String getHtml(BaseCass theBase, String inputId);
}
