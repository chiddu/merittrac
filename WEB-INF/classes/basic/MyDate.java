package basic;



import java.util.*;

/* Will be changed to use the GregorianCalendar object */
public class MyDate extends java.util.Date 
{

public MyDate(long dod)
{
	super(dod);
}

public MyDate(int year, int month, int date)
{
	super(year - 1900,month - 1,date);
}

public MyDate(String year, String month, String date)
{
	super(0,0,0,0,0,0);

	int numb = Integer.parseInt(year);
	this.setYear(numb - 1900);

	numb = Integer.parseInt(month);
	this.setMonth(numb -1);

	numb = Integer.parseInt(date);
	this.setDate(numb);

}
public void setSeconds2(int secs)
{
	super.setSeconds(secs);
}

}
