/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.*;
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

public class DlgParamBoolValue extends TaoDialog {
	public int result = -1;
	private DlgParamBoolValue dlg;
	private JComboBox cmbValue = new JComboBox();
	private Box bxRecord = Box.createVerticalBox();
	private String prmName;
	private int curValue;

	public DlgParamBoolValue(String aPrm, int aValue) {
		super(TaoGlobal.frmMain, "Редактирование параметра");
		prmName = aPrm;
		curValue = aValue;

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

		boolean boolValue = ((Boolean)TaoGlobal.parameters.defParameters.get(prmName)).booleanValue();
		String defaultValue = (boolValue ? "<font color=#009000>Да</font>" : "<font color=#900000>Нет</font>");

		cmbValue.addItem("<html><font color=#000090>По умолчанию [" + defaultValue + "]</font></html>");
		cmbValue.addItem("<html><font color=#009000>Да</font></html>");
		cmbValue.addItem("<html><font color=#900000>Нет</font></html>");

		cmbValue.setSelectedIndex(curValue);

		pnl1.add(new JLabel("Выберите значение: "), BorderLayout.NORTH);
		pnl1.add(cmbValue, BorderLayout.CENTER);

		TaoTextArea taPrmName = new TaoTextArea(TaoGlobal.parameters.descr_parameters.get(prmName));
		pnl2.add(taPrmName, BorderLayout.CENTER);

		bxRecord.add(pnl2);
		bxRecord.add(pnl1);

		this.pack();
		this.setPreferredSize(new Dimension(this.getSize().width << 1, this.getSize().height));
		this.setResizable(false);
		getRootPane().setDefaultButton(btnOk);
		taPrmName.setColumns(1);
		this.pack();

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dlg.result = cmbValue.getSelectedIndex();
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
