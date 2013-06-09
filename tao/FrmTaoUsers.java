/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.database.TaoDataModel;

import tao.visual.TaoTable;
import tao.visual.TaoTableBar;
import tao.visual.TaoDirectoryPanel;
import tao.database.TaoStoredProc;

public class FrmTaoUsers extends FrmTaoChild {
	private FrmTaoUsers frm;
	private AcAfterUserInsert acAfterUserInsert = new AcAfterUserInsert();
	private int ID_Directory = 3;
	private TaoDirectoryPanel pnlDir;

	public FrmTaoUsers() throws Exception {
		super("Управление пользователями", Nuvola.apps.s_users);
		frm = this;
		pnlDir = new TaoDirectoryPanel(ID_Directory, null, true);
		pnlDir.setAfterInsert(acAfterUserInsert);
		pnlDir.addToBar(new AcSetPassword());
		pnlDir.addToBar(new AcSetRoles());
		this.add(pnlDir);
		pnlDir.initHotKeys(null);
		pack();
	}

	public class AcAfterUserInsert extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			TaoStoredProc sp = new TaoStoredProc("UpdateUsers", 0);
			try {
				sp.execute();
			} catch (Exception e) {
				Dlg.error("Не удалось создать пользователя", e);
			}
		}
	}

	class AcSetPassword extends AbstractAction {
		AcSetPassword() {
			putValue(Action.NAME, "Пароль");
			putValue(Action.SHORT_DESCRIPTION, "Установить пароль");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_kgpg_identity);
		}

		public void actionPerformed(ActionEvent event) {
			String pass = Dlg.newPassword();
			if (pass != null) {
				try {
					String login = pnlDir.getSelectedField("Login").toString();
					TaoGlobal.database.query.setPassword(login, pass);
					Dlg.info("Пароль изменен!");
				} catch (Exception e) {
					Dlg.error("Не удалось установить пароль", e);
				}
			}
		}
	}

	class AcSetRoles extends AbstractAction {
		AcSetRoles() {
			putValue(Action.NAME, "Роли");
			putValue(Action.SHORT_DESCRIPTION, "Управления ролями пользователя");
			putValue(Action.SMALL_ICON,Nuvola.apps.s_roles);
		}
 
		public void actionPerformed(ActionEvent event) {
			try {
				int ID_User = ((Integer)pnlDir.getSelectedField("ID_User")).intValue();
				Dlg.User_Roles(ID_User);
			} catch (Exception e) {
				Dlg.error("Ошибка чтения ролей пользователя!", e);
			}
		}
	}
}
