package basic;


import java.nio.ByteBuffer;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.thrift.*;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import org.apache.cassandra.thrift.CqlResult;
import org.apache.cassandra.thrift.CqlRow;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;



import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.*;


import java.util.*;
import java.io.*;

import java.nio.charset.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.json.*;


public class BaseCass 
{

	private static org.apache.log4j.Logger logger = 
		org.apache.log4j.Logger.getLogger(BaseCass.class);



	TTransport m_transport ;
	TProtocol m_protocol ;
	Cassandra.Client m_client ;
	String m_folderName;
	String m_keyspace;
	ConsistencyLevel m_consistencyLevel = ConsistencyLevel.ONE;




public BaseCass(String folderName, String keyspace) throws Exception
{
	m_dbRead = 0;
	m_dbWrite = 0;
	m_folderName = folderName;
	init(keyspace);
}

public BaseCass(String keyspace) throws Exception
{
	m_dbRead = 0;
	m_dbWrite = 0;
	init(keyspace);
}

private void init(String keyspace) throws Exception
{
	m_keyspace = keyspace;
	m_transport = new TFramedTransport(new TSocket("localhost", 9160));
	m_protocol = new TBinaryProtocol(m_transport);
	m_client = new Cassandra.Client(m_protocol);
	m_transport.open();
	m_client.set_keyspace(keyspace);

}


public Cassandra.Client getClient()
{
	return m_client;
}



public  String getData(String columnFamily, String key)
{
	return getColData( columnFamily, key, "dummy");
}

public synchronized String getColData(String columnFamily, String key, long colname)
{
	logger.debug("Colname is " + colname);
	logger.debug("key is " + key);
	logger.debug("cf is " + columnFamily);
	long t1 = System.currentTimeMillis();
	String jsonValue =  null;
	ColumnPath colPathName = new ColumnPath(columnFamily);
	try
	{
		byte[] colnameKey = MtxUtil.longToBytes(colname ); 
		ByteBuffer buf0 = ByteBuffer.wrap(colnameKey);


		byte[] userIDKey = key.getBytes(); 
		ByteBuffer buf = ByteBuffer.wrap(userIDKey);
		colPathName.setColumn(buf0);
		ColumnOrSuperColumn cosc =  m_client.get(buf, colPathName, ConsistencyLevel.ONE);
		if(cosc != null)
		{
			Column col =  cosc.getColumn();
			if(col != null)
			{
				jsonValue =  MtxUtil.bb_to_str(col.value);
			}
		}
	}
	catch(NotFoundException ex)
	{
//		ex.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		System.exit(1);
	}
	long t2 = System.currentTimeMillis();
	this.addRead(t2 - t1);
	return jsonValue;
}

public  synchronized String getColData(String columnFamily, String key, String colname)
{
	long t1 = System.currentTimeMillis();
	String jsonValue =  null;
	ColumnPath colPathName = new ColumnPath(columnFamily);
	try
	{
		colPathName.setColumn(colname.getBytes("UTF-8"));

		byte[] userIDKey = key.getBytes(); 

		ByteBuffer buf = ByteBuffer.wrap(userIDKey);
		ColumnOrSuperColumn cosc =  m_client.get(buf, colPathName, ConsistencyLevel.ONE);
		if(cosc != null)
		{
			Column col =  cosc.getColumn();
			if(col != null)
			{
				jsonValue =  MtxUtil.bb_to_str(col.value);
			}
		}
	}
	catch(NotFoundException ex)
	{
//		ex.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addRead(t2 - t1);
	return jsonValue;
}


public  synchronized String getLongData(String columnFamily, long key, long colname)
{
	long t1 = System.currentTimeMillis();
	String jsonValue =  null;
	ColumnPath colPathName = new ColumnPath(columnFamily);

	byte[] byteColName = MtxUtil.longToBytes(colname);
	try
	{
		colPathName.setColumn(byteColName);

		byte[] userIDKey = MtxUtil.longToBytes(key);

		ByteBuffer buf = ByteBuffer.wrap(userIDKey);
		ColumnOrSuperColumn cosc =  m_client.get(buf, colPathName, ConsistencyLevel.ONE);
		if(cosc != null)
		{
			Column col =  cosc.getColumn();
			if(col != null)
			{
				jsonValue =  MtxUtil.bb_to_str(col.value);
			}
		}
	}
	catch(NotFoundException ex)
	{
		ex.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addRead(t2 - t1);
	return jsonValue;
}


public  synchronized String getLongData(String columnFamily, String key, long colname)
{
	long t1 = System.currentTimeMillis();
	String jsonValue =  null;
	ColumnPath colPathName = new ColumnPath(columnFamily);

	byte[] byteColName = MtxUtil.longToBytes(colname);
	try
	{
		colPathName.setColumn(byteColName);

		byte[] userIDKey = key.getBytes(); 

		ByteBuffer buf = ByteBuffer.wrap(userIDKey);
		ColumnOrSuperColumn cosc =  m_client.get(buf, colPathName, ConsistencyLevel.ONE);
		if(cosc != null)
		{
			Column col =  cosc.getColumn();
			if(col != null)
			{
				jsonValue =  MtxUtil.bb_to_str(col.value);
			}
		}
	}
	catch(NotFoundException ex)
	{
		ex.printStackTrace();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addRead(t2 - t1);
	return jsonValue;
}


public synchronized void saveColumn(String colFamily, String rowidStr, String key, long value)
{
	long t1 = System.currentTimeMillis();
	try
	{
		ColumnParent parent = new ColumnParent(colFamily);

		logger.debug("ColumnFamily is " + colFamily);
		logger.debug("Key is " + key);
		logger.debug("value is " + value);
		logger.debug("timestamp is " + t1);
		logger.debug("rowidStr is " + rowidStr);

		// define row id
		ByteBuffer rowid = ByteBuffer.wrap(rowidStr.getBytes());

		Column description = new Column();
		description.setName(key.getBytes());
		description.setValue(MtxUtil.longToBytes(value));

		description.setTimestamp(t1);

		m_client.insert(rowid, parent, description, m_consistencyLevel);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addWrite(t2 - t1);
}

public synchronized void saveColumn(String colFamily, String rowidStr, String key, String value)
{
	long t1 = System.currentTimeMillis();
	try
	{
		ColumnParent parent = new ColumnParent(colFamily);

		logger.debug("ColumnFamily is " + colFamily);
		logger.debug("Key is " + key);
		logger.debug("value is " + value);
		logger.debug("timestamp is " + t1);
		logger.debug("rowidStr is " + rowidStr);

		// define row id
		ByteBuffer rowid = ByteBuffer.wrap(rowidStr.getBytes());

		Column description = new Column();
		description.setName(key.getBytes());
		description.setValue(value.getBytes());

		description.setTimestamp(t1);

		m_client.insert(rowid, parent, description, m_consistencyLevel);
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addWrite(t2 - t1);
}


public synchronized void deleteKey(String columnfamily, String rowkey ,
	String columnName)
{
	long t1 = System.currentTimeMillis();
	try
	{
		ColumnPath parent = new ColumnPath(columnfamily);
		parent.setColumn(columnName.getBytes());

		ByteBuffer rowid = ByteBuffer.wrap(rowkey.getBytes());
		m_client.remove(rowid, parent, t1, ConsistencyLevel.ALL);

	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	long t2 = System.currentTimeMillis();
	this.addWrite(t2 - t1);
}



public synchronized void close() throws Exception
{
	m_transport.close();
}

public synchronized void save() throws Exception
{
	m_transport.close();
}


public void printStats()
{
	System.out.println("Time reading database : " + m_dbRead);
	System.out.println("Time writing database : " + m_dbWrite);

	System.out.println("Time searching lucene : " + m_luceneRead);
	System.out.println("Time  indexing lucene : " + m_luceneWrite);
}


private long m_luceneRead;
private long m_luceneWrite;
	public long m_dbRead;
	public long m_dbWrite;


private boolean initReader() throws Exception
{
	return true;
}



	public void addWrite(long input)
	{
		m_dbWrite = m_dbWrite + input;
	}
	public void addRead(long input)
	{
		m_dbRead = m_dbRead + input;
	}



private  CqlResult executeQuery(String query) 
throws UnsupportedEncodingException, InvalidRequestException,UnavailableException,  TimedOutException, SchemaDisagreementException, TException { 
	logger.info("Executing query : " + query);
return m_client.execute_cql_query(
	ByteBuffer.wrap(query.getBytes("UTF-8")),Compression.NONE);
}


public synchronized HashMap<String,String> getColumns(String colFamily, String key) throws Exception
{

	HashMap<String, String> aurek = new HashMap<String, String>();
	SlicePredicate predicate = new SlicePredicate();
	SliceRange sliceRange = new SliceRange();
	sliceRange.setStart(new byte[0]);
	sliceRange.setFinish(new byte[0]);
	predicate.setSlice_range(sliceRange);

	ColumnParent parent = new ColumnParent(colFamily);
	byte[] userIDKey = key.getBytes();
	ByteBuffer userIDKey2 = ByteBuffer.wrap(userIDKey);

	List<ColumnOrSuperColumn> results =
		m_client.get_slice(userIDKey2,
		parent, predicate, ConsistencyLevel.ONE);

	for (ColumnOrSuperColumn result : results) {
		Column column = result.column;
		String valueId  =  MtxUtil.bb_to_str(column.getName());
		String value  =  MtxUtil.bb_to_str(column.value);

		aurek.put(valueId, value);
	}
	return aurek;

}

}
