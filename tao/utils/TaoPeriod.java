/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

public class TaoPeriod {
	private Date date1 = Calendar.getInstance().getTime();
	private Date date2 = Calendar.getInstance().getTime();
	private SimpleDateFormat df = new SimpleDateFormat("dd MMMMMMMMMM yyyy");

	public Date getDate1() {
		return date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate1(Date aDate) {
		date1 = aDate;
	}

	public void setDate2(Date aDate) {
		date2 = aDate;
	}

	public String getHTMLPeriod() {
		StringBuilder strdate1 = new StringBuilder(df.format(date1.getTime()));
		StringBuilder strdate2 = new StringBuilder(df.format(date2.getTime()));
		return "<html><font color=green>" + strdate1 + " &#151; " + strdate2 + "</font></html>";
	}
}
