/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.utils;

public class TaoStringFilter {
	public int oper1 = 0;
	public String value1 = "";
	public int oper2 = 0;
	public int oper3 = 0;
	public String value2 = "";

	public boolean test(String aValue) {
		boolean result = true;
		boolean result1 = true;
		boolean result2 = true;
		switch(oper1) {
			case 1:
				result1 = ((aValue.toUpperCase().compareTo(value1.toUpperCase())) == 0);
				break;
			case 2:
				result1 = ! ((aValue.toUpperCase().compareTo(value1.toUpperCase())) == 0);
				break;
			case 3:
				result1 = (aValue.toUpperCase().indexOf(value1.toUpperCase()) >= 0);
				break;
			case 4:
				result1 = ! (aValue.toUpperCase().indexOf(value1.toUpperCase()) >= 0);
				break;
		}

		switch(oper3) {
			case 1:
				result2 = ((aValue.toUpperCase().compareTo(value2.toUpperCase())) == 0);
				break;
			case 2:
				result2 = ! ((aValue.toUpperCase().compareTo(value2.toUpperCase())) == 0);
				break;
			case 3:
				result2 = (aValue.toUpperCase().indexOf(value2.toUpperCase()) >= 0);
				break;
			case 4:
				result2 = ! (aValue.toUpperCase().indexOf(value2.toUpperCase()) >= 0);
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
