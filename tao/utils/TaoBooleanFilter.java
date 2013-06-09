/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.utils;

public class TaoBooleanFilter {
	public int oper1 = 0;

	public boolean test(boolean aValue) {
		boolean result = true;
		switch(oper1) {
			case 1:
			result = aValue;
			break;
		case 2:
			result = !aValue;
			break;
		}
		return result;
	}
}
