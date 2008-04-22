package org.apache.myfaces.custom.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import java.util.Locale;

import org.apache.myfaces.custom.date.HtmlInputDate.UserData;

import junit.framework.TestCase;

public class UserDataTest extends TestCase {
	
	private UserData userData;
	
	private String getDefaultTimeZoneId() {
		return TimeZone.getDefault().getID();
	}
	
	public void testConstructorDoesNotSetDateVariablesWhenTheDateIsNull() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId() , false, "date");
		assertNull(userData.getYear());
		assertNull(userData.getDay());
		assertNull(userData.getHours());
	}
	
	public void testParseReturnsNullWhenTypeIsDateAndComponentNotUsed() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "date");
		userData.setDay("");
		userData.setMonth("-1");
		userData.setYear("");
		try {
			Date date = userData.parse();
			assertNull(date);
		} catch (ParseException e) {
			fail();
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeDate() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "date");
		userData.setDay("40");
		userData.setMonth("-1");
		userData.setYear("2005");
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	public void testParseGivesCorrectDateWhenValidDataIsEnteredForTypeDate() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "date");
		userData.setDay("20");
		userData.setMonth("7");
		userData.setYear("2006");
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		calendar.set(Calendar.DAY_OF_MONTH, 20);
		calendar.set(Calendar.MONTH, 6);
		calendar.set(Calendar.YEAR, 2006);
		
		//only day, month and year is considered for equality
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");		
		try {
			Date date1 = userData.parse();
			Date date2 = calendar.getTime();
			assertEquals(formatter.format(date1), formatter.format(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void testParseReturnsNullWhenTypeIsTimeAndComponentNotUsed() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "time");
		userData.setHours("");
		userData.setMinutes("");
		userData.setSeconds("");
		
		try {
			assertNull(userData.parse());
		} catch (ParseException e) {
			fail();
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeTime() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "time");
		userData.setHours("25");
		userData.setMinutes("");
		userData.setSeconds("10");
		
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	public void testParseGivesCorrectDateWhenValidDataIsEnteredForTypeTime() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), false, "time");
		userData.setHours("10");
		userData.setMinutes("50");
		userData.setSeconds("30");
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 50);
		calendar.set(Calendar.SECOND, 30);
		
		//hour, minute, seconds is considered for equality
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");		
		try {
			Date date1 = userData.parse();
			Date date2 = calendar.getTime();
			assertEquals(formatter.format(date1), formatter.format(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void testParseReturnsNullWhenTypeIsFullAndComponentNotUsed() {
		userData = new UserData(null, Locale.ENGLISH, "GMT+2:00", true, "full");
		userData.setDay("");
		userData.setMonth("-1");
		userData.setYear("");
		userData.setHours("");
		userData.setMinutes("");
		userData.setSeconds("");
		userData.setAmpm("-1");
		
		try {
			assertNull(userData.parse());
		} catch (ParseException e) {
			fail();
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeFullCase1() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), true, "full");
		//date is used, time is not
		userData.setDay("40");
		userData.setMonth("-1");
		userData.setYear("");
		userData.setHours("");
		userData.setMinutes("");
		userData.setSeconds("");
		userData.setAmpm("-1");
		
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeFullCase2() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), true, "full");
		//date is used time is not
		userData.setDay("");
		userData.setMonth("0");
		userData.setYear("2005");
		userData.setHours("");
		userData.setMinutes("");
		userData.setSeconds("");
		userData.setAmpm("-1");
		
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeFullCase3() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), true, "full");
		//date is used time is not
		userData.setDay("5");
		userData.setMonth("10");
		userData.setYear("2005");
		userData.setHours("");
		userData.setMinutes("");
		userData.setSeconds("");
		userData.setAmpm("-1");
		
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	public void testParseThrowsParseExceptionWhenInvalidDataIsEnteredForTypeFullCase4() {
		userData = new UserData(null, Locale.getDefault(), getDefaultTimeZoneId(), true, "full");
		//date is not used but time is
		userData.setDay("");
		userData.setMonth("-1");
		userData.setYear("");
		userData.setHours("");
		userData.setMinutes("30");
		userData.setSeconds("");
		userData.setAmpm("-1");
		
		try {
			userData.parse();
			fail();
		} catch (ParseException e) {
		}
	}
	
	

}
