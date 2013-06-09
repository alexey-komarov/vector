/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.utils;

import java.util.Calendar;

public class TaoDateFilter {
	public int oper1 = 0;
	public Calendar value1 = Calendar.getInstance();
	public int oper2 = 0;
	public int oper3 = 0;
	public Calendar value2 = Calendar.getInstance();

	public boolean test(Calendar aValue) {

		boolean result = true;
		boolean result1 = true;
		boolean result2 = true;

		if (aValue != null) {
			aValue.set(Calendar.HOUR_OF_DAY, 0);
			aValue.set(Calendar.MINUTE, 0);
			aValue.set(Calendar.SECOND, 0);
			aValue.set(Calendar.MILLISECOND, 0);
		}

		switch(oper1) {
			case 7:
				result1 = aValue == null;
				break;
			case 1:
				result1 = aValue.compareTo(value1) == 0;
				break;
			case 2:
				result1 = aValue.compareTo(value1) != 0;
				break;
			case 3:
				result1 = aValue.after(value1);
				break;
			case 4:
				result1 = (aValue.after(value1) || (aValue.compareTo(value1) == 0));
				break;
			case 5:
				result1 = aValue.before(value1);
				break;
			case 6:
				result1 = (aValue.before(value1) || (aValue.compareTo(value1) ==0));
				break;
			case 8:
				result1 = aValue != null;
				break;
		}

		switch(oper3) {
			case 7:
				result1 = aValue == null;
				break;
			case 1:
				result2 = aValue.compareTo(value2) == 0;
				break;
			case 2:
				result2 = aValue.compareTo(value2) != 0;
				break;
			case 3:
				result2 = aValue.after(value2);
				break;
			case 4:
				result2 = (aValue.after(value2) || (aValue.compareTo(value2) == 0));
				break;
			case 5:
				result2 = aValue.before(value2);
				break;
			case 6:
				result2 = (aValue.before(value2) || (aValue.compareTo(value2) == 0));
				break;
			case 8:
				result1 = aValue != null;
				break;
		}

		switch (oper2) {
			case 0:
				result = result1;
				break;
			case 1:
				result = result1 && result2;
				break;
			case 2:
				result = result1 || result2;
				break;
			case 3:
				result = result1 && (! result2);
				break;
		}
		return result;
	}
}
