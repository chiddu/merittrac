package basic;


import java.util.*;
import java.io.*;

import org.json.*;

import java.nio.*;
import java.nio.charset.*;

import org.apache.log4j.Level;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.Logger;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;


/* Just a bunch of static functions */
public class MtxUtil
{

	private static Logger logger = Logger.getLogger(MtxUtil.class);

	public static  boolean printlog = true;
	public static void writeFile(FileOutputStream fst, String data) throws Exception
	{
	 byte[] barr = data.getBytes() ;
	 fst.write(barr);
	 fst.close();
	}

	public static String implode(Collection coll)
	{
		return implode(",",coll);
	}

	/* Implode an array */
	public static String implode(String sep,Collection coll)
	{
		StringBuffer retStr = new StringBuffer();
		boolean comma = false;
		for(Object eachStr : coll)
		{
			if(comma)
			{
				retStr.append(sep);
			}
			else
				comma = true;
			retStr.append(eachStr.toString());
		}
		return retStr.toString();
	}

	/* Split into multiple guuid*/
	public static ArrayList<String> getUuidList(String concatStr)
	{
		int totlen = concatStr.length();
		int numbro = totlen/36; 
		ArrayList<String> golabarud  = new ArrayList<String>();

		for(int i = 0; i < numbro ; i++)
		{
			int sp = i*36;
			golabarud.add(concatStr.substring(sp, sp+36));
		}
		return golabarud;
	}
	/* Split into multiple guuid*/
	public static TreeSet<String> getUuidSet(String concatStr)
	{
		int totlen = concatStr.length();
		int numbro = totlen/36; 
		TreeSet<String> golabarud  = new TreeSet<String>();

		for(int i = 0; i < numbro ; i++)
		{
			int sp = i*36;
			golabarud.add(concatStr.substring(sp, sp+36));
		}
		return golabarud;
	}

	/* utility function for faster comparision */
	public static TreeSet<String> convertToTreeSet(Collection<String> concatStr)
	{
		TreeSet<String> eksi = new TreeSet<String>();
		for(String str : concatStr)
		{
			eksi.add(str);
		}
		return eksi;

	}

public static String readFile(FileInputStream fst) throws JSONException, IOException
{
	boolean bytesLeft = true;
	ByteArrayOutputStream bs = new  ByteArrayOutputStream();
	byte barray[] = new byte[1024];
	int offset = 0;
	while(bytesLeft)
	{
			int len = 1024;
			int actread = fst.read(barray,offset, 1024);
			if(actread > 0)
				bs.write(barray, 0, actread);
			if(actread < 1024)
			{
				bytesLeft = false;
			}
		}
		return bs.toString();
}


	/* Converts to a hash set */
	public static LinkedHashSet<String> 
		convertJsonSet(JSONArray arr) throws JSONException
	{
			
			LinkedHashSet<String> parentList = new LinkedHashSet<String>();
			int mlen = arr.length() ;
			for(int i =0 ; i < mlen; i= i+1)
			{
				parentList.add( (String)arr.get(i));
			}
			return parentList;
	}

	public static ArrayList<String> 
		convertJsonArray(JSONArray arr) throws JSONException
	{
			
			ArrayList<String> parentList = new ArrayList<String>();
			int mlen = arr.length() ;
			for(int i =0 ; i < mlen; i= i+1)
			{
				parentList.add( (String)arr.get(i));
			}
			return parentList;
	}

	public static TreeSet<String> 
		convertJsonArrayToSet(JSONArray arr) throws JSONException
	{
			
			TreeSet<String> parentList = new TreeSet<String>();
			int mlen = arr.length() ;
			for(int i =0 ; i < mlen; i= i+1)
			{
				parentList.add( (String)arr.get(i));
			}
			return parentList;
	}

public static boolean  isInt(String iss)
{
  int len = iss.length();
  for(int i = 0; i < len; i++)
  {
    if((iss.charAt(i) < '0') ||
    (iss.charAt(i) > '9'))
    {
      return false;
    }
  }
  return true;
}
public static void printLog1(String logMsg)
{
	System.out.println(Thread.currentThread().getName() + 
		":" + logMsg);
}

public static void printLog(String logMsg)
{
	if (printlog)
	{
		System.out.println(Thread.currentThread().getName() + 
		":" + logMsg);
	}
}


public static void addField(Document doc, Fieldable fld, int boost)
{
	if(boost > 0)
	{
		fld.setBoost(boost);
	}

	doc.add(fld);
}

/*
	toStore - one of 
	 Field.Store.YES
	 Field.Store.NO

 toAnalyze
	Field.Index.ANALYZED
	Field.Index.NOT_ANALYZED

*/

public static void addField(Document doc, String name, 
	String value, Field.Store toStore, Field.Index toAnalyze, int boost)
{
	if((value != null) && !("".equals(value)))
	{
		Field fld = new Field(name, value, toStore, toAnalyze);

		addField(doc, fld, boost); 
	}
}

public static void addField(Document doc, String name, 
	String value, Field.Store toStore, Field.Index toAnalyze)
{
	if((value != null) && !("".equals(value)))
	{
		Field fld = new Field(name, value, toStore, toAnalyze);
		doc.add(fld);
	}
}



public static void addLongField(Document doc, String name, long value, Field.Store toStore)
{
	MtxUtil.addLongField(doc, name, value, toStore, 1);
}

public static void addLongField(Document doc, String name, long value, Field.Store toStore, int boost)
{
	NumericField fld = new NumericField(name, toStore, true);
	fld = fld.setLongValue(value);
	addField(doc, fld, boost); 
}



public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
}

public static String genUid(Collection<String> inlist)
{
	return implode("",inlist);
}


public static ArrayList<String> split(String theBigString)
{
		String[] rules = theBigString.split(",");
		ArrayList<String> retstr = new ArrayList<String>();
		for( String rule :rules)
		{
			retstr.add(rule.trim());
		}
		return retstr;
}
	public static HashMap<String , String> getDepends(String theBigString)
	{

		HashMap<String , String> dependsMap = new HashMap<String , String>();
		String[] rules = theBigString.split(",");

		for ( String rule : rules)
		{
			String[] deplist = rule.split(":");
			if(deplist.length == 2)
			{
				dependsMap.put(deplist[0], deplist[1]);
			}
		}

	return dependsMap;
	}

public static void  printMap(String description, Map<String,String> ibumba)
{
	System.out.println("BEGIN Printing hash " + description + " ======");
	Set<String> keySet = ibumba.keySet();
	for(String key : keySet)
	{
		System.out.println("   " + key + " = " +
			ibumba.get(key));
	}
	System.out.println("END Printing hash " + description + " ======");
}

public static void  printArray(String description, String[] pArr)
{
	System.out.println("BEGIN Printing array " + description + " ======");
	if(pArr != null)
	{
		for(String key : pArr)
		{
			System.out.println("   " + key);
		}
	}
	System.out.println("END Printing array " + description + " ======");
}


public static void  printColl(String description, Collection<String> pArr)
{
	System.out.println("BEGIN Printing Collection " + description + " ======");
	if(pArr != null)
	{
		for(String key : pArr)
		{
			System.out.println("   " + key);
		}
	}
	System.out.println("END Printing Collection " + description + " ======");
}


public static void trimArr(String[] str)
{
	int len = str.length;
	for(int i =0 ; i  < len; i++)
	{
		str[i] = trimStr(str[i]);
	}
}

public static String trimStr(String str)
{
	String str2 = str.trim();
	if(str.length() == 0)
		return str;
	if(str2.charAt(0) == '"')
	{
		int ind2 = str2.length() - 1;
		if(str2.charAt(ind2) == '"')
		{
			String str3 = str2.substring(1,ind2);
			return str3;
		}
	}
	return str2;
}

public static Charset charset = Charset.forName("UTF-8");
public static CharsetEncoder encoder = charset.newEncoder();
public static CharsetDecoder decoder = charset.newDecoder();

public static ByteBuffer str_to_bb(String msg){
  try{
    return encoder.encode(CharBuffer.wrap(msg));
  }catch(Exception e){e.printStackTrace();}
  return null;
}

public static String bb_to_str(byte[] byteArr)
{
	ByteBuffer buf = ByteBuffer.wrap(byteArr);
	return bb_to_str(buf);
}

public static String bb_to_str(ByteBuffer buffer){
  String data = "";
  try{
    int old_position = buffer.position();
    data = decoder.decode(buffer).toString();
    // reset buffer's position to its original so it is not altered:
    buffer.position(old_position);  
  }catch (Exception e){
    e.printStackTrace();
    return "";
  }
  return data;
}

public static long bytesToLong(ByteBuffer inbuf)
{
	byte[] by = inbuf.array();
	long value = 0;
//	for (int i = 0; i < by.length; i++)
//	{
//		 value += ((long) by[i] & 0xffL) << (8 * i);
//	 }
	for (int i = 0; i < by.length; i++)
	{
		 value = (value << 8) + (by[i] & 0xff);
	 }
	 return value;
}
public static long bytesToLong(byte[] by) 
{
	long value = 0;
//	for (int i = 0; i < by.length; i++)
//	{
//		 value += ((long) by[i] & 0xffL) << (8 * i);
//	 }
	for (int i = 0; i < by.length; i++)
	{
		 value = (value << 8) + (by[i] & 0xff);
	 }
	 return value;
}

public static byte[] longToBytes(long v) {
    byte[] writeBuffer = new byte[ 8 ];

    writeBuffer[0] = (byte)(v >>> 56);
    writeBuffer[1] = (byte)(v >>> 48);
    writeBuffer[2] = (byte)(v >>> 40);
    writeBuffer[3] = (byte)(v >>> 32);
    writeBuffer[4] = (byte)(v >>> 24);
    writeBuffer[5] = (byte)(v >>> 16);
    writeBuffer[6] = (byte)(v >>>  8);
    writeBuffer[7] = (byte)(v >>>  0);

    return writeBuffer;
}
public static HashMap convertToMap(JSONObject jObj)
{
	HashMap<String, String> hash = new HashMap<String, String>();
	Map jMap = jObj.getMap();
	Set<Object> keyz = jMap.keySet();

	for(Object key : keyz)
	{
		hash.put(key.toString(), jMap.get(key).toString());
	}
	return hash;
}



public static void pause(org.apache.log4j.Logger logger )
{
	if(logger.getLevel() == Level.DEBUG)
	{
		MtxUtil.pause();
	}
}

public static void pause(String spitThis)
{
	try
	{
		System.out.println("PAUSE:" + spitThis);
//    System.in.read();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}

}
public static void pause()
{
	try
	{
//		System.in.read();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}

}
public static int debugMonth(long inda)
{
	MyDate dd = new MyDate(inda);
	return dd.getMonth() ;
}
public static String debugDate(String inda)
{
	try
	{
	long la = Long.parseLong(inda);
	return debugDate(la);
	}
	catch(Exception ex)
	{
	}
	return "";
}

public static String debugDate(long inda)
{
	MyDate dd = new MyDate(inda);
		String marg = dd.getYear() + 1900 + "/" +
		(dd.getMonth() + 1) + "/" +
		dd.getDate()   + " " +
		dd.getHours()   + ":" +
		dd.getMinutes() + ":" +
		dd.getSeconds()  ;
		return marg;
}

	/*changed so that a meta entity is allowed to generate its own unique
	id .
	And also to check for duplicates whenever a new id is generated 
	*/
	public static String generateUniqueId() 
	{
		 String tmpUid =  UUID.randomUUID().toString();
		 return tmpUid;
	}
}
