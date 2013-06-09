/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class UsersInitParameters {
	private TaoParameters usersprm = new TaoParameters("ѕользователи");

	public UsersInitParameters() {
		usersprm.setBoolean("CanAdd", true, "–азрешено добавл€ть пользователей");
		usersprm.setBoolean("CanEdit", true, "–азрешено редактировать пользователей");
		usersprm.setBoolean("CanDelete", true, "–азрешено удал€ть/восстанавливать пользователей");
		usersprm.setBoolean("CanShowDeleted", true, "–азрешено просматривать удаленных пользователей");
	}
}
