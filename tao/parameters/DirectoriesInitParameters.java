/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;
import tao.database.TaoDataModel;

public class DirectoriesInitParameters {
	private TaoDataModel dm;
	private TaoParameters dictprm;

	public DirectoriesInitParameters() throws Exception {
		dm = TaoGlobal.database.directories.getDirectory(1);
		for (int i = 0; i < dm.getRowCount(); i++) {
			if ((Boolean)dm.getData(i, "Parameters")) {
				dictprm = new TaoParameters("�����������." + dm.getData(i, "Name"));

				dictprm.setDefBoolean("CanAdd", true, "��������� ��������� ��������");
				dictprm.setDefBoolean("CanEdit", true, "��������� ������������� ��������");
				dictprm.setDefBoolean("CanDelete", true, "��������� �������/��������������� ��������");
				dictprm.setDefBoolean("CanShowDeleted", true, "��������� ������������� ��������� ��������");
				dictprm.setDefBoolean("CanFilter", true, "��������� ������������ ������");
			}
		}
	}
}
