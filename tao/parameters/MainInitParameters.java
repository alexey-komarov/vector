/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class MainInitParameters {
	private TaoParameters mainprm = new TaoParameters("Главное окно");

	public MainInitParameters() {
		mainprm.setDefBoolean("CanDirectories", true, "Управление справочниками");
		mainprm.setDefBoolean("CanRoles", true, "Управление ролями пользователей");
		mainprm.setDefBoolean("CanUsers", true, "Управление пользователями");
		mainprm.setDefBoolean("CanParameters", true, "Настройка параметров");
		mainprm.setDefBoolean("CanCustomers", true, "Заказчики");
		mainprm.setDefBoolean("CanFurniture", true, "Фурнитура");
		mainprm.setDefBoolean("CanMebel", true, "Мебель");
		mainprm.setDefBoolean("CanEmployers", true, "Сотрудники");
		mainprm.setDefBoolean("CanOrders", true, "Заказы");
	}
}
