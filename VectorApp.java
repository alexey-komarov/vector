/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

import tao.FrmTaoLogin;
import tao.config.*;
import tao.global.*;
import tao.dialogs.*;
import tao.order.*;
import tao.database.*;
import tao.icons.*;
import tao.mysql.*;
import tao.nottao.*;
import tao.parameters.*;
import tao.utils.*;
import tao.visual.*;

import tao.xml.*;

import javax.swing.*;

public class VectorApp {

	public static void main(String[] args) {
		boolean flag = true;

		TaoGlobal.init();

		TaoGlobal.config = new Config("tao.conf");

		JFrame frm = new JFrame();

		if (TaoGlobal.config.localParams == null) {
			Dlg.error(frm, "Ошибка открытия файла ./tao.conf!", TaoGlobal.config.exception);
			flag = false;
		}

		if (flag)
			TaoGlobal.cleartempdir();

		if ((flag) && (TaoGlobal.config.localParams.getProperty("db_name") == null)) {
			Dlg.error(frm, "В файле ./tao.conf не указано наименование база данных!",
				"В файле tao.conf должна присутствовать строка db_name=имя_базы");
			flag = false;
		}

		if ((flag) && (TaoGlobal.config.localParams.getProperty("db_host") == null)) {
			Dlg.error(frm, "В файле ./tao.conf не указано имя сервера базы данных!",
				"В файле tao.conf должна присутствовать строка db_host=имя_сервера");
			flag = false;
		}

		frm.dispose();

		if (flag) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					FrmTaoLogin FrmLogin = new FrmTaoLogin("Вектор-мебель", 400, 200);
					FrmLogin.setVisible(true);
				}
			});
		}
	}
}
