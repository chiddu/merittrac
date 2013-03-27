package servlet;

import proto.*;

public class Container
{


public static CombinedStorage m_cms;
private static Container m_this;


private static synchronized void getContainer1() throws Exception
{
	if( m_this == null)
	{
		m_this = new Container();
	}
}

public static Container getContainer() throws Exception
{
	if(m_this != null)
		return m_this;
	Container.getContainer1();
	return m_this;
}

/* Doin't want to rewrite CombinedStorage, so this natak */
private Container() throws Exception
{
	m_cms = new CombinedStorage("Metrix"); // TODO hardcoding
}

public CombinedStorage getCombinedStorage()
{
	return m_cms;
}


}
