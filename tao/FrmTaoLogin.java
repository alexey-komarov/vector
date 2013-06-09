/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.SwingUtilities;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays;

import tao.FrmTaoMain;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.mysql.MySQL;
import tao.database.Database;
import tao.dialogs.Dlg;
public class FrmTaoLogin extends JFrame {
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JComboBox cmbLogin = new JComboBox();
	private JPasswordField pfPassword = new JPasswordField();

	private JPanel 
		pnlMain = new JPanel(),
		pnlLeft = new JPanel(),
		pnlRight = new JPanel();

	private JLabel 
		lbIco = new JLabel(),
		lbLogin = new JLabel("Имя:"),
		lbPassword = new JLabel("Пароль:");

	private JButton
		btnOk = new JButton(),
		btnCancel = new JButton();

	private void load_users() {
		int default_login_index = 0;
		String usernames = TaoGlobal.config.localParams.getProperty("users");
		String def_user = TaoGlobal.config.localParams.getProperty("default_user");

		this.setIconImage(Nuvola.actions.ll_kgpg_identity.getImage());

		if (usernames != null) {
			String[] loginlist = usernames.split(",");
			for (int k = 0; k < loginlist.length; k++) {
				cmbLogin.addItem(loginlist[k].trim());
				if ( (def_user != null) && (loginlist[k].trim().equals(def_user.trim())) )
					default_login_index = k;
			}
		}

		if (default_login_index != 0) {
			cmbLogin.setSelectedIndex(default_login_index);
		}
		pfPassword.requestFocus();
	}

	private void save_users() {
		String selected_user = (String) cmbLogin.getSelectedItem();
		String usernames = TaoGlobal.config.localParams.getProperty("users");
		String[] loginlist = usernames.split(",");

		TaoGlobal.config.localParams.setProperty("default_user", selected_user);

		boolean exists = false;

		for (int n = 0; n < loginlist.length; n++) {
			if (loginlist[n].trim().equals(selected_user.trim())) {
				exists = true;
				break;
			}
		}

		if (!exists) {
			usernames = usernames + ", " + selected_user;
			TaoGlobal.config.localParams.setProperty("users", usernames);
		}

		TaoGlobal.config.Save();

		if (TaoGlobal.config.exception != null)
			Dlg.error(this, "Ошибка записи файла tao.conf!", TaoGlobal.config.exception);
	}

	public FrmTaoLogin(String caption, int width, int height) {
		super(caption);
		load_users();

		AcOk acOk = new AcOk(this);
		AcCancel acCancel = new AcCancel();

		TaoGlobal.database = new Database();

		cmbLogin.setEditable(true);

		Box bxButtons = Box.createHorizontalBox();

		lbIco.setIcon(Nuvola.actions.lll_kgpg_identity);

		btnOk.setAction(acOk);
		btnCancel.setAction(acCancel);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnCancel);

		pnlLeft.setPreferredSize(new Dimension(60, 1));
		pnlLeft.add(lbIco, BorderLayout.CENTER);
		pnlLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

		pnlRight.setLayout(new GridLayout(0, 1));

		pnlRight.add(lbLogin);
		pnlRight.add(cmbLogin);
		pnlRight.add(lbPassword);
		pnlRight.add(pfPassword);
		pnlRight.add(new JLabel());
		pnlRight.add(bxButtons);

		pnlMain.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		pnlMain.setLayout(new BorderLayout());
		pnlMain.add(pnlLeft,BorderLayout.WEST);
		pnlMain.add(pnlRight,BorderLayout.CENTER);

		int x = (dim.width - width) >> 1;
		int y = (dim.height - height) >> 1;

		getRootPane().setDefaultButton(btnOk);

		this.setContentPane(pnlMain);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(x, y);
		this.setSize(width, height);
	}

	class AcOk extends AbstractAction {
		private FrmTaoLogin frmParent;
		AcOk(FrmTaoLogin frm) {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
			frmParent = frm;
		}

		public void actionPerformed(ActionEvent event) {
			String login = (String) frmParent.cmbLogin.getSelectedItem();
			String password = new String(frmParent.pfPassword.getPassword());
			String host = TaoGlobal.config.localParams.getProperty("db_host");
			String db = TaoGlobal.config.localParams.getProperty("db_name");

			if ((login != null) && (host != null) && (db != null) && (password != null) ) {
				TaoGlobal.database.connection = MySQL.connect(host, login, password, db);

				if (TaoGlobal.database.connection == null) {
					if (MySQL.exception != null) {
						Dlg.error(frmParent,"Ошибка входа в программу!", MySQL.exception);
					} else
						Dlg.error(frmParent,"Ошибка входа в программу!", MySQL.sql_exception);
					return;
				} else {
					try {
						try {
							TaoGlobal.database.loadUser(login);
						} catch (Exception e) {
							Dlg.error("Ошибка инициализации пользователя " + login, e);
							System.exit(0);
						}

						if (TaoGlobal.database.users.getAnul()) {
							Dlg.error("Вход под именем " + login + " запрещен!");
							return;
						}

						TaoGlobal.database.init();
						try{
							TaoGlobal.loadParameters();
						} catch (Exception e) {
							Dlg.error("Ошибка инициализации параметров", e);
							System.exit(0);
						}

						save_users();
						frmParent.setVisible(false);

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								FrmTaoMain FrmMain = new FrmTaoMain(frmParent.getTitle());
								FrmMain.setVisible(true);
							}
						});
					} catch(Exception e) {
						Dlg.error(frmParent, "Ошибка инициализации программы!", e); 
					}
				}
			}
		}
	}

	class AcCancel extends AbstractAction {
		AcCancel() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}
 
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
}
