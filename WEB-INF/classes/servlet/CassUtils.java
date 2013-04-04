package servlet;


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


import basic.*;



public class CassUtils
{

	public static ArrayList<String> getPageList(BaseCass bms) throws Exception
	{
			return getArr(bms, "misc", "pages", "list");
	}


	public static ArrayList<String> getArr(BaseCass bms,
		String colFamily, String key, String colkey) throws Exception
	{
		String instr = bms.getColData(colFamily, key,colkey);
		if(instr == null)
		{
			return new ArrayList<String>();
		}
			
		JSONArray obo = new JSONArray(instr);
		ArrayList<String> dosa = MtxUtil.convertJsonArray(obo);
		return dosa ;
	}



	public static Page getPage(BaseCass bms, int pageNo)
	{
		return getPage(bms, pageNo + "");
	}
	public static Page getPage(BaseCass bms, String pageStr)
	{
		String pageData = bms.getColData("pages",pageStr,"dummy");
		if(pageData != null)
		{
		Page targetPage = new Page(pageData);
		return targetPage;
		}
		return null;
	}

}
