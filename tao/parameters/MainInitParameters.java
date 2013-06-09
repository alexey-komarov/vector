/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class MainInitParameters {
	private TaoParameters mainprm = new TaoParameters("������� ����");

	public MainInitParameters() {
		mainprm.setDefBoolean("CanDirectories", true, "���������� �������������");
		mainprm.setDefBoolean("CanRoles", true, "���������� ������ �������������");
		mainprm.setDefBoolean("CanUsers", true, "���������� ��������������");
		mainprm.setDefBoolean("CanParameters", true, "��������� ����������");
		mainprm.setDefBoolean("CanCustomers", true, "���������");
		mainprm.setDefBoolean("CanFurniture", true, "���������");
		mainprm.setDefBoolean("CanMebel", true, "������");
		mainprm.setDefBoolean("CanEmployers", true, "����������");
		mainprm.setDefBoolean("CanOrders", true, "������");
	}
}
