/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner.NumberEditor;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Color;

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
import tao.utils.TaoBooleanFilter;

public class DlgBooleanFilter extends TaoDialog {
	public TaoBooleanFilter result = new TaoBooleanFilter();
	private DlgBooleanFilter dlg;
	private JComboBox cmbOper1 = new JComboBox();
	private Box bxFilter1 = Box.createVerticalBox();

	public DlgBooleanFilter(TaoBooleanFilter aBooleanFilter, String aCaption) {
		super(TaoGlobal.frmMain, "Фильтр по \"" + aCaption + "\"");	

		this.setModal(true);
		dlg = this;
		JPanel pnlRecord = new JPanel();

		AcOk acOk = new AcOk();
		AcCancel acCancel = new AcCancel();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));
		this.add(bxFilter1, BorderLayout.CENTER);

		JButton btnOk = new JButton(acOk);
		JButton btnCancel = new JButton(acCancel);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnCancel);

		pnlRecord.setLayout(new GridLayout(0, 1));
		this.add(bxButtons, BorderLayout.SOUTH);

		TaoPanel pnlFilter1 = new TaoPanel();
		TaoPanel pnl1 = new TaoPanel();

		pnlFilter1.setLayout(new BorderLayout());
		pnl1.setLayout(new BorderLayout());

		pnlFilter1.add(pnl1, BorderLayout.CENTER);
		pnlFilter1.add(new JLabel("Условие 1"), BorderLayout.NORTH);
		pnlFilter1.setBackground(new Color(0xD8, 0xBF, 0xD8));

		cmbOper1.addItem("<html><font color=#FF0000>Не использовать</font></html>");
		cmbOper1.addItem("Да");
		cmbOper1.addItem("Нет");

		pnl1.add(new JLabel("Выберите тип сравнения: "), BorderLayout.NORTH);
		pnl1.add(cmbOper1, BorderLayout.CENTER);

		bxFilter1.add(pnlFilter1);

		this.pack();
		this.setPreferredSize(new Dimension(this.getSize().width << 1, this.getSize().height));
		this.setResizable(false);
		getRootPane().setDefaultButton(btnOk);

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);

		if (aBooleanFilter != null) {
			cmbOper1.setSelectedIndex(aBooleanFilter.oper1);
		}
		updateControls();
	}

	private void updateControls() {
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dlg.result.oper1 = cmbOper1.getSelectedIndex();
			dlg.setVisible(false);
		}
	}

	class AcCancel extends AbstractAction {
		AcCancel() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			result = null;
			dlg.setVisible(false);
		}
	}
}
