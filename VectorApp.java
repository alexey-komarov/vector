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
			Dlg.error(frm, "������ �������� ����� ./tao.conf!", TaoGlobal.config.exception);
			flag = false;
		}

		if (flag)
			TaoGlobal.cleartempdir();

		if ((flag) && (TaoGlobal.config.localParams.getProperty("db_name") == null)) {
			Dlg.error(frm, "� ����� ./tao.conf �� ������� ������������ ���� ������!",
				"� ����� tao.conf ������ �������������� ������ db_name=���_����");
			flag = false;
		}

		if ((flag) && (TaoGlobal.config.localParams.getProperty("db_host") == null)) {
			Dlg.error(frm, "� ����� ./tao.conf �� ������� ��� ������� ���� ������!",
				"� ����� tao.conf ������ �������������� ������ db_host=���_�������");
			flag = false;
		}

		frm.dispose();

		if (flag) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					FrmTaoLogin FrmLogin = new FrmTaoLogin("������-������", 400, 200);
					FrmLogin.setVisible(true);
				}
			});
		}
	}
}
