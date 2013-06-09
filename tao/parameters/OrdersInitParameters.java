/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class OrdersInitParameters {
	private TaoParameters mainprm = new TaoParameters("Заказы");

	public OrdersInitParameters() {
		mainprm.setDefBoolean("CanAdd", true, "Можно добавлять заказы");
		mainprm.setDefBoolean("CanEdit", true, "Можно редактировать заказы");
		mainprm.setDefBoolean("CanDelete", true, "Можно удалять заказы");
		mainprm.setDefBoolean("CanChangeClosed", true, "Можно удалять/редактировать закрытые");
		mainprm.setDefBoolean("CanPrint", true, "Можно распечатывать");
	}
}
