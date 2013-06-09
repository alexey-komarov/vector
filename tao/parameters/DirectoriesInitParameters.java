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
				dictprm = new TaoParameters("Справочники." + dm.getData(i, "Name"));

				dictprm.setDefBoolean("CanAdd", true, "Разрешено добавлять элементы");
				dictprm.setDefBoolean("CanEdit", true, "Разрешено редактировать элементы");
				dictprm.setDefBoolean("CanDelete", true, "Разрешено удалять/восстанавливать элементы");
				dictprm.setDefBoolean("CanShowDeleted", true, "Разрешено просматривать удаленные элементы");
				dictprm.setDefBoolean("CanFilter", true, "Разрешено использовать фильтр");
			}
		}
	}
}
