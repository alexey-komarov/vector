/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class UsersInitParameters {
	private TaoParameters usersprm = new TaoParameters("Пользователи");

	public UsersInitParameters() {
		usersprm.setBoolean("CanAdd", true, "Разрешено добавлять пользователей");
		usersprm.setBoolean("CanEdit", true, "Разрешено редактировать пользователей");
		usersprm.setBoolean("CanDelete", true, "Разрешено удалять/восстанавливать пользователей");
		usersprm.setBoolean("CanShowDeleted", true, "Разрешено просматривать удаленных пользователей");
	}
}
