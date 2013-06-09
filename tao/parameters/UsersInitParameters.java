/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class UsersInitParameters {
	private TaoParameters usersprm = new TaoParameters("������������");

	public UsersInitParameters() {
		usersprm.setBoolean("CanAdd", true, "��������� ��������� �������������");
		usersprm.setBoolean("CanEdit", true, "��������� ������������� �������������");
		usersprm.setBoolean("CanDelete", true, "��������� �������/��������������� �������������");
		usersprm.setBoolean("CanShowDeleted", true, "��������� ������������� ��������� �������������");
	}
}
