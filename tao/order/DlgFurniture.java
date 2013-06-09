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

public class DlgFurniture extends TaoDialog {
	private Box bxRecord = Box.createVerticalBox();
	private DlgFurniture dlg;
	public boolean result = false;

	private FurnitureRecord fFurniture = new FurnitureRecord();
	private EmployeeRecord fEmployee = new EmployeeRecord();
	private JSpinner spQuantity;
	private TaoPanel tPnl;

	public DlgFurniture() {
		super(TaoGlobal.frmMain, "");

		spQuantity = getNewIntField();

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
		tPnl.add(fFurniture, BorderLayout.CENTER);
		bxRecord.add(tPnl);

		tPnl = new TaoPanel();
		tPnl.setLayout(new BorderLayout());
		tPnl.add(new JLabel("Количество:"), BorderLayout.NORTH);
		tPnl.add(spQuantity, BorderLayout.CENTER);
		bxRecord.add(tPnl);

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

	public int getFurniture_ID() {
		return fFurniture.getID();
	}

	public float objToFloat(Object aObject) {
		if (aObject instanceof Double)
			return ((Double)aObject).floatValue();
		else
			return (Float)aObject;
	}

	public float getQuantity() {
		return (Integer)spQuantity.getValue();
	}

	public void setFurniture_ID(int aID, String aValue) {
		fFurniture.setValue(aID, aValue);
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
			if (fFurniture.getID() == -1) {
				Dlg.error("Не выбрано изделие!");
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
