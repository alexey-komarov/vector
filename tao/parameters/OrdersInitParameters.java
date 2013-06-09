/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class OrdersInitParameters {
	private TaoParameters mainprm = new TaoParameters("������");

	public OrdersInitParameters() {
		mainprm.setDefBoolean("CanAdd", true, "����� ��������� ������");
		mainprm.setDefBoolean("CanEdit", true, "����� ������������� ������");
		mainprm.setDefBoolean("CanDelete", true, "����� ������� ������");
		mainprm.setDefBoolean("CanChangeClosed", true, "����� �������/������������� ��������");
		mainprm.setDefBoolean("CanPrint", true, "����� �������������");
	}
}
