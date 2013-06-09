/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dialog;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.sql.ResultSet;

import tao.visual.TaoTable;
import tao.visual.TaoTextArea;
import tao.visual.TaoPanel;
import tao.visual.TaoDBComboBox;
import tao.visual.TaoBooleanField;

import tao.database.TaoDataModel;
import tao.global.TaoGlobal;
import tao.xml.TaoXMLTable;
import tao.icons.Nuvola;

public class DlgNewPassword extends TaoDialog {
	public boolean result = false;
	private DlgNewPassword dlg;
	private JPasswordField pf1 = new JPasswordField();
	private JPasswordField pf2 = new JPasswordField();
	private Box bxRecord = Box.createVerticalBox();

	public DlgNewPassword() {
		super(TaoGlobal.frmMain, "Ввод пароля");
		this.setModal(true);
		dlg = this;
		JPanel pnlRecord = new JPanel();

		AcOk acOk = new AcOk();
		AcCancel acCancel = new AcCancel();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));
		this.add(bxRecord, BorderLayout.CENTER);

		JButton btnOk = new JButton(acOk);
		JButton btnCancel = new JButton(acCancel);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnCancel);

		pnlRecord.setLayout(new GridLayout(0, 1));
		this.add(bxButtons, BorderLayout.SOUTH);

		TaoPanel pnl1 = new TaoPanel();
		TaoPanel pnl2 = new TaoPanel();

		pnl1.setLayout(new BorderLayout());
		pnl2.setLayout(new BorderLayout());

		pnl1.add(new JLabel("Введите пароль:"), BorderLayout.NORTH);
		pnl1.add(pf1, BorderLayout.CENTER);

		pnl2.add(new JLabel("Повторите пароль:"), BorderLayout.NORTH);
		pnl2.add(pf2, BorderLayout.CENTER);

		bxRecord.add(pnl1);
		bxRecord.add(pnl2);

		this.pack();
		this.setPreferredSize(new Dimension(this.getSize().width << 1, this.getSize().height));
		this.setResizable(false);
		getRootPane().setDefaultButton(btnOk);
		this.pack();

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getPassword() {
		return new String(pf1.getPassword());
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			String pass1 = new String(pf1.getPassword()).trim();
			String pass2 = new String(pf2.getPassword()).trim();

			if (pass1.length() == 0) {
				Dlg.error("Введите пароль!");
				pf1.requestFocus();
				return;
			}

			if (!pass1.equals(pass2)) {
				Dlg.error("Пароли должны совпадать!");
				pf1.requestFocus();
				return;
			}

			dlg.result = true;
			dlg.setVisible(false);
		}
	}

	class AcCancel extends AbstractAction {
		AcCancel() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			dlg.setVisible(false);
		}
	}
}
