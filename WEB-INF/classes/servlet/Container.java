package servlet;

import basic.*;

public class Container
{


public static BaseCass m_cms;
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

/* Doin't want to rewrite BaseCass, so this natak */
private Container() throws Exception
{
	m_cms = new BaseCass("Metrix"); // TODO hardcoding
}

public BaseCass getStorage()
{
	return m_cms;
}


}
