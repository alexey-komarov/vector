/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order;

import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.border.*;

import java.awt.event.*;

import java.awt.*;

import java.util.*;
import java.sql.ResultSet;

import tao.visual.*;

import tao.database.TaoDataModel;
import tao.global.TaoGlobal;
import tao.xml.TaoXMLTable;
import tao.icons.Nuvola;
import tao.dialogs.*;
import java.util.regex.*;

public class DlgMebel extends TaoDialog {
	private Box bxRecord = Box.createVerticalBox();
	private DlgMebel dlg;
	public boolean result = false;
	private boolean alt = false;

	private MebelRecord fMebel = new MebelRecord();
	private EmployeeRecord fEmployee = new EmployeeRecord();
	private JSpinner spQuantity;
	private JSpinner spPrice;
	private JSpinner spSquare;
	private TaoPanel tPnl;

	public DlgMebel(boolean aAlt) {
		super(TaoGlobal.frmMain, "");
		alt = aAlt;

		spQuantity = getNewIntField();
		spPrice = getNewFloatField();
		spSquare = getNewFloatField();
		spQuantity.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		this.setModal(true);

		dlg = this;
		JPanel pnlRecord = new JPanel();

		AcOk acOk = new AcOk();
		AcCancel acCancel = new AcCancel();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		JButton btnOk = new JButton(acOk);
		JButton btnCancel = new JButton(acCancel);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnCancel);

		pnlRecord.setLayout(new GridLayout(0, 1));
		this.add(bxRecord, BorderLayout.CENTER);
		this.add(bxButtons, BorderLayout.SOUTH);

		tPnl = new TaoPanel();
		tPnl.setLayout(new BorderLayout());
		tPnl.add(new JLabel("Изделие:"), BorderLayout.NORTH);
		tPnl.add(fMebel, BorderLayout.CENTER);
		bxRecord.add(tPnl);

		tPnl = new TaoPanel();
		tPnl.setLayout(new BorderLayout());
		tPnl.add(new JLabel("Цена:"), BorderLayout.NORTH);
		tPnl.add(spPrice, BorderLayout.CENTER);
		bxRecord.add(tPnl);

		tPnl = new TaoPanel();
		tPnl.setLayout(new BorderLayout());
		tPnl.add(new JLabel("Количество:"), BorderLayout.NORTH);
		tPnl.add(spQuantity, BorderLayout.CENTER);
		bxRecord.add(tPnl);

		tPnl = new TaoPanel();
		tPnl.setLayout(new BorderLayout());
		tPnl.add(new JLabel("Квадратура:"), BorderLayout.NORTH);
		tPnl.add(spSquare, BorderLayout.CENTER);
		bxRecord.add(tPnl);

		if (alt) {
			tPnl = new TaoPanel();
			tPnl.setLayout(new BorderLayout());
			tPnl.add(new JLabel("Менеджер:"), BorderLayout.NORTH);
			tPnl.add(fEmployee, BorderLayout.CENTER);
			bxRecord.add(tPnl);
		}

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

		fMebel.setAfterSelect(new AcAfterSelect());
	}

	public int getMebel_ID() {
		return fMebel.getID();
	}

	public int getEmployee_ID() {
		return fEmployee.getID();
	}

	public float objToFloat(Object aObject) {
		if (aObject instanceof Double)
			return ((Double)aObject).floatValue();
		else
			return (Float)aObject;
	}

	public float getSquare() {
		return objToFloat(spSquare.getValue());
	}

	public float getPrice() {
		return objToFloat(spPrice.getValue());
	}

	public float getQuantity() {
		return (Integer)spQuantity.getValue();
	}

	public void setMebel_ID(int aID, String aValue) {
		fMebel.setValue(aID, aValue);
	}

	public void setEmployee_ID(int aID, String aValue) {
		fEmployee.setValue(aID, aValue);
	}

	public void setSquare(float aValue) {
		spSquare.setValue(aValue);
	}

	public void setPrice(float aValue) {
		spPrice.setValue(aValue);
	}

	public void setQuantity(int aValue) {
		spQuantity.setValue(aValue);
	}

	private JSpinner getNewFloatField() {
		JSpinner result = new JSpinner(new SpinnerNumberModel(0.1, 0, Integer.MAX_VALUE, 0.1));
		result.setEditor(new JSpinner.NumberEditor(result,"##########.##"));
		return result;
	}

	private JSpinner getNewIntField() {
		JSpinner result = new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		result.setEditor(new JSpinner.NumberEditor(result,"############"));
		return result;
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			if (fMebel.getID() == -1) {
				Dlg.error("Не выбрано изделие!");
				return;
			}
			if ((alt) && (fEmployee.getID() == -1)) {
				Dlg.error("Не выбран менеджер!");
				return;
			}
			dlg.result = true;
			dlg.setVisible(false);
		}
	}

	class AcAfterSelect extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			spPrice.setValue(fMebel.defPrice);
			spSquare.setValue(fMebel.defSquare);
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
