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
import tao.utils.TaoFloatFilter;

public class DlgFloatFilter extends TaoDialog {
	public TaoFloatFilter result = new TaoFloatFilter();
	private DlgFloatFilter dlg;
	private JComboBox cmbOper1 = new JComboBox();
	private JComboBox cmbOper2 = new JComboBox();
	private JComboBox cmbOper3 = new JComboBox();
	private Box bxFilter1 = Box.createVerticalBox();

	private JSpinner spValue1;
	private JSpinner spValue2;

	public DlgFloatFilter(TaoFloatFilter aFloatFilter, String aCaption) {
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
		TaoPanel pnlFilter2 = new TaoPanel();
		TaoPanel pnlFilter3 = new TaoPanel();

		TaoPanel pnl1 = new TaoPanel();
		TaoPanel pnl2 = new TaoPanel();
		TaoPanel pnl3 = new TaoPanel();
		TaoPanel pnl4 = new TaoPanel();
		TaoPanel pnl5 = new TaoPanel();

		pnlFilter1.setLayout(new BorderLayout());
		pnlFilter2.setLayout(new BorderLayout());
		pnlFilter3.setLayout(new BorderLayout());
		pnl1.setLayout(new BorderLayout());
		pnl2.setLayout(new BorderLayout());
		pnl3.setLayout(new BorderLayout());
		pnl4.setLayout(new BorderLayout());
		pnl5.setLayout(new BorderLayout());

		pnlFilter1.add(pnl1, BorderLayout.CENTER);
		pnlFilter1.add(pnl2, BorderLayout.SOUTH);
		pnlFilter1.add(new JLabel("Условие 1"), BorderLayout.NORTH);
		pnlFilter1.setBackground(new Color(0xD8, 0xBF, 0xD8));

		pnlFilter2.add(pnl5, BorderLayout.CENTER);

		pnlFilter3.add(pnl3, BorderLayout.CENTER);
		pnlFilter3.add(pnl4, BorderLayout.SOUTH);
		pnlFilter3.add(new JLabel("Условие 2"), BorderLayout.NORTH);
		pnlFilter3.setBackground(new Color(0xD8,0xBF,0xD8));

		cmbOper1.addItem("<html><font color=#FF0000>Не использовать</font></html>");
		cmbOper1.addItem("Равно");
		cmbOper1.addItem("Не равно");
		cmbOper1.addItem("Больше");
		cmbOper1.addItem("Больше или равно");
		cmbOper1.addItem("Меньше");
		cmbOper1.addItem("Меньше или равно");

		cmbOper2.addItem("<html><font color=#FF0000>Не использовать</font></html>");
		cmbOper2.addItem("И");
		cmbOper2.addItem("ИЛИ");
		cmbOper2.addItem("И НЕ");

		cmbOper3.addItem("<html><font color=#FF0000>Не использовать</font></html>");
		cmbOper3.addItem("Равно");
		cmbOper3.addItem("Не равно");
		cmbOper3.addItem("Больше");
		cmbOper3.addItem("Больше или равно");
		cmbOper3.addItem("Меньше");
		cmbOper3.addItem("Меньше или равно");

		spValue1 = getNewFloatField();
		spValue2 = getNewFloatField();

		pnl1.add(new JLabel("Выберите тип сравнения: "), BorderLayout.NORTH);
		pnl1.add(cmbOper1, BorderLayout.CENTER);

		pnl2.add(new JLabel("Значение: "), BorderLayout.NORTH);
		pnl2.add(spValue1, BorderLayout.CENTER);

		pnl3.add(new JLabel("Выберите тип сравнения: "), BorderLayout.NORTH);
		pnl3.add(cmbOper3, BorderLayout.CENTER);

		pnl4.add(new JLabel("Значение: "), BorderLayout.NORTH);
		pnl4.add(spValue2, BorderLayout.CENTER);

		pnl5.add(new JLabel("Условие: "), BorderLayout.NORTH);
		pnl5.add(cmbOper2, BorderLayout.CENTER);

		bxFilter1.add(pnlFilter1);
		bxFilter1.add(pnlFilter2);
		bxFilter1.add(pnlFilter3);

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

		cmbOper1.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				updateControls();
			}
		});

		cmbOper2.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				updateControls();
			}
		});

		cmbOper3.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				updateControls();
			}
		});

		if (aFloatFilter != null) {
			cmbOper1.setSelectedIndex(aFloatFilter.oper1);
			cmbOper2.setSelectedIndex(aFloatFilter.oper2);
			cmbOper3.setSelectedIndex(aFloatFilter.oper3);
			spValue1.setValue(aFloatFilter.value1);
			spValue2.setValue(aFloatFilter.value2);
		}
		updateControls();
	}

	private void updateControls() {
		if (cmbOper1.getSelectedIndex() == 0) {
			spValue1.setEnabled(false);
			spValue2.setEnabled(false);
			cmbOper2.setEnabled(false);
			cmbOper3.setEnabled(false);
		} else {
			cmbOper2.setEnabled(true);
			spValue1.setEnabled(true);
			if (cmbOper2.getSelectedIndex() == 0) {
				cmbOper3.setEnabled(false);
				spValue2.setEnabled(false);
			} else {
				cmbOper3.setEnabled(true);
				if (cmbOper3.getSelectedIndex() == 0) {
					spValue2.setEnabled(false);
				} else {
					spValue2.setEnabled(true);
				}
			}
		}
	}

	private JSpinner getNewFloatField() {
		JSpinner result = new JSpinner(new SpinnerNumberModel(0.1, 0, Integer.MAX_VALUE, 0.1));
		result.setEditor(new JSpinner.NumberEditor(result,"##########.##"));
		return result;
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
		dlg.result.oper1 = cmbOper1.getSelectedIndex();
		try {
			dlg.result.value1 = Float.parseFloat(spValue1.getValue().toString());
		} catch (Exception e) {}

		dlg.result.oper2 = cmbOper2.getSelectedIndex();
		dlg.result.oper3 = cmbOper3.getSelectedIndex();
		try {
		  dlg.result.value2 = Float.parseFloat(spValue2.getValue().toString());
		} catch (Exception e) {}
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
