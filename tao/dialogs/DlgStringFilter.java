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
import tao.utils.TaoStringFilter;

public class DlgStringFilter extends TaoDialog {
	public TaoStringFilter result = new TaoStringFilter();
	private DlgStringFilter dlg;
	private JComboBox cmbOper1 = new JComboBox();
	private JComboBox cmbOper2 = new JComboBox();
	private JComboBox cmbOper3 = new JComboBox();
	private Box bxFilter1 = Box.createVerticalBox();
	private JTextField tfValue1 = new JTextField();
	private JTextField tfValue2 = new JTextField();

	public DlgStringFilter(TaoStringFilter aStringFilter, String aCaption) {
		super(TaoGlobal.frmMain, "������ �� \"" + aCaption + "\"");
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
		pnlFilter1.add(new JLabel("������� 1"), BorderLayout.NORTH);
		pnlFilter1.setBackground(new Color(0xD8, 0xBF, 0xD8));

		pnlFilter2.add(pnl5, BorderLayout.CENTER);

		pnlFilter3.add(pnl3, BorderLayout.CENTER);
		pnlFilter3.add(pnl4, BorderLayout.SOUTH);
		pnlFilter3.add(new JLabel("������� 2"), BorderLayout.NORTH);
		pnlFilter3.setBackground(new Color(0xD8,0xBF,0xD8));

		cmbOper1.addItem("<html><font color=#FF0000>�� ������������</font></html>");
		cmbOper1.addItem("�����");
		cmbOper1.addItem("�� �����");
		cmbOper1.addItem("��������");
		cmbOper1.addItem("�� ��������");

		cmbOper2.addItem("<html><font color=#FF0000>�� ������������</font></html>");
		cmbOper2.addItem("�");
		cmbOper2.addItem("���");
		cmbOper2.addItem("� ��");

		cmbOper3.addItem("<html><font color=#FF0000>�� ������������</font></html>");
		cmbOper3.addItem("�����");
		cmbOper3.addItem("�� �����");
		cmbOper3.addItem("��������");
		cmbOper3.addItem("�� ��������");

		pnl1.add(new JLabel("�������� ��� ���������: "), BorderLayout.NORTH);
		pnl1.add(cmbOper1, BorderLayout.CENTER);

		pnl2.add(new JLabel("��������: "), BorderLayout.NORTH);
		pnl2.add(tfValue1, BorderLayout.CENTER);

		pnl3.add(new JLabel("�������� ��� ���������: "), BorderLayout.NORTH);
		pnl3.add(cmbOper3, BorderLayout.CENTER);

		pnl4.add(new JLabel("��������: "), BorderLayout.NORTH);
		pnl4.add(tfValue2, BorderLayout.CENTER);

		pnl5.add(new JLabel("�������: "), BorderLayout.NORTH);
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
		this.setSize(new Dimension(400, this.getSize().height));

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

		if (aStringFilter != null) {
			cmbOper1.setSelectedIndex(aStringFilter.oper1);
			cmbOper2.setSelectedIndex(aStringFilter.oper2);
			cmbOper3.setSelectedIndex(aStringFilter.oper3);
			tfValue1.setText(aStringFilter.value1);
			tfValue2.setText(aStringFilter.value2);
		}
		updateControls();
	}

	private void updateControls() {
		if (cmbOper1.getSelectedIndex() == 0) {
			tfValue1.setEnabled(false);
			tfValue2.setEnabled(false);
			cmbOper2.setEnabled(false);
			cmbOper3.setEnabled(false);
		} else {
			cmbOper2.setEnabled(true);
			tfValue1.setEnabled(true);
			if (cmbOper2.getSelectedIndex() == 0) {
				cmbOper3.setEnabled(false);
				tfValue2.setEnabled(false);
			} else {
				cmbOper3.setEnabled(true);
				if (cmbOper3.getSelectedIndex() == 0) {
					tfValue2.setEnabled(false);
				} else {
					tfValue2.setEnabled(true);
				}
			}
		}
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "��");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dlg.result.oper1 = cmbOper1.getSelectedIndex();
			dlg.result.value1 = tfValue1.getText();
			dlg.result.oper2 = cmbOper2.getSelectedIndex();
			dlg.result.oper3 = cmbOper3.getSelectedIndex();
			dlg.result.value2 = tfValue2.getText();
			dlg.setVisible(false);
		}
	}

	class AcCancel extends AbstractAction {
		AcCancel() {
			putValue(Action.NAME, "������");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			result = null;
			dlg.setVisible(false);
		}
	}
}
