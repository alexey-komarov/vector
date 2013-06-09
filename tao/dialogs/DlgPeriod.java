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
import javax.swing.SpinnerDateModel;

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
import tao.visual.TaoDatePicker;
import tao.utils.TaoPeriod;

public class DlgPeriod extends TaoDialog {
	public TaoPeriod result = null;
	private DlgPeriod dlg;
	private TaoDatePicker dt1 = new TaoDatePicker();
	private TaoDatePicker dt2 = new TaoDatePicker();
	private Box bxRecord = Box.createVerticalBox();

	public DlgPeriod(TaoPeriod aPeriod) {
		super(TaoGlobal.frmMain, "Ввод периода");
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

		dt1.setValue(aPeriod.getDate1());
		dt2.setValue(aPeriod.getDate2());

		pnl1.add(new JLabel("C: "), BorderLayout.WEST);
		pnl1.add(dt1, BorderLayout.CENTER);

		pnl2.add(new JLabel("По: "), BorderLayout.WEST);
		pnl2.add(dt2, BorderLayout.CENTER);

		bxRecord.add(pnl1);
		bxRecord.add(pnl2);

		this.pack();
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

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dlg.result = new TaoPeriod();
			dlg.result.setDate1(((SpinnerDateModel)dt1.getModel()).getDate());
			dlg.result.setDate2(((SpinnerDateModel)dt2.getModel()).getDate());
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
