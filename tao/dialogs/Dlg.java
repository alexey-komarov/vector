/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JFrame;
import tao.global.TaoGlobal;

public class Dlg {
	public static void error(JFrame frm, String shortMsg) {
		DlgError dlg = new DlgError(frm, shortMsg, null, null);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(JFrame frm, String shortMsg, String longMsg) {
		DlgError dlg = new DlgError(frm, shortMsg, longMsg, null);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(JFrame frm, String shortMsg, String longMsg, Exception exception) {
		DlgError dlg = new DlgError(frm, shortMsg, longMsg, exception);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(JFrame frm, String shortMsg, Exception exception) {
		DlgError dlg = new DlgError(frm, shortMsg, "" + exception, exception);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(String shortMsg, Exception exception) {
		DlgError dlg = new DlgError(TaoGlobal.frmMain, shortMsg, "" + exception, exception);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(String shortMsg, String longMsg, Exception exception) {
		DlgError dlg = new DlgError(TaoGlobal.frmMain, shortMsg, longMsg, exception);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(String shortMsg, String longMsg) {
		DlgError dlg = new DlgError(TaoGlobal.frmMain, shortMsg, longMsg, null);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void error(String shortMsg) {
		DlgError dlg = new DlgError(TaoGlobal.frmMain, shortMsg, null, null);
		dlg.setVisible(true);
		dlg = null;
	}

	public static void info(String shortMsg) {
		DlgInfo dlg = new DlgInfo(TaoGlobal.frmMain, shortMsg);
		dlg.setVisible(true);
		dlg = null;
	}

	public static boolean queryYN(String aQuestion) {
		DlgQueryYN dlg = new DlgQueryYN(TaoGlobal.frmMain, aQuestion);
		dlg.setVisible(true);
		boolean result = dlg.result;
		dlg.dispose();
		return result;
	}

	public static String newPassword() {
		DlgNewPassword dlg = new DlgNewPassword();
		dlg.setVisible(true);
		boolean result = dlg.result;
		String sResult = null;

		if (result)
			sResult = dlg.getPassword();

		dlg.dispose();
		return sResult;
	}

	public static void User_Roles(int aID_User) {
		try {
			DlgRoles dlg = new DlgRoles(aID_User);
			dlg.dispose();
			dlg.setVisible(true);
		} catch (Exception e) {
			error("Ошибка чтения ролей пользователя", e);
		}
	}

	public static int param_BoolValue(String aPrm, int aValue) {
		DlgParamBoolValue dlg = new DlgParamBoolValue(aPrm, aValue);
		dlg.setVisible(true);
		int result = dlg.result;
		dlg.dispose();
		return result;
	}
}
