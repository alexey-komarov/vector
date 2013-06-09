/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JDialog;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.BorderFactory;
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

import java.util.regex.*;

public class TaoRecordDialog extends TaoDialog {
	private TaoXMLTable scheme;
	private Hashtable<String, Component> htObj = new Hashtable<String, Component>();
	private Box bxRecord = Box.createVerticalBox();
	private TaoRecordDialog dlg;
	private boolean flgNew;
	public boolean result = false;
	public TaoRecordDialog(String caption, TaoXMLTable aScheme, boolean aNew) throws Exception {
		super(TaoGlobal.frmMain, caption);
		flgNew = aNew;
		this.setModal(true);
		scheme = aScheme;

		if (scheme == null)
			throw new Exception("Отсутствует схема");

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

		Component obj;
		JTextField tf;
		TaoTextArea ta;
		TaoDBComboBox dbCombo;
		String dirName;
		TaoBooleanField bf;

		ResultSet rsDirectory;
		for (int i = 0; i < scheme.getFieldCount(); i++) {
			if (scheme.getHidden(i))
				continue;

			obj = null;

			TaoPanel tPnl = new TaoPanel();
			tPnl.setLayout(new BorderLayout());
			tPnl.add(new JLabel(scheme.getCaption(i) + ":"), BorderLayout.NORTH);

			if (scheme.getType(i).equals("Integer")) {
				if (getReadOnly(i)) {
					tf = new JTextField();
					tf.setEditable(false);
					obj = tf;
				} else {
					obj = getNewIntField();
				}
			}

			if (scheme.getType(i).equals("Float")) {
				if (getReadOnly(i)) {
					tf = new JTextField();
					tf.setEditable(false);
					obj = tf;
				} else {
					obj = getNewFloatField();
				}
			}

			if (scheme.getType(i).equals("String")) {
				tf = new JTextField();
				tPnl.add(tf, BorderLayout.CENTER);

				if (getReadOnly(i))
					tf.setEditable(false);

				obj = tf;
			}
			if (scheme.getType(i).equals("Text")) {
				ta = new TaoTextArea("");

				if (getReadOnly(i))
					ta.setEditable(false);

				ta.scrText.setPreferredSize(new Dimension(0, 64));
				tPnl.add(ta.scrText, BorderLayout.CENTER);

				obj = ta;
			}

			if (scheme.getType(i).equals("Directory")) {
				dirName = scheme.getDirectory(i);
				dbCombo = new TaoDBComboBox();
				dbCombo.setEditable(false);
				if (dirName.length() > 0) {
					rsDirectory = TaoGlobal.database.directories.getNewDirectory(dirName);
					dbCombo.setSource(rsDirectory);
				}

				if (getReadOnly(i)) 
					dbCombo.setEnabled(false);

				obj = dbCombo;
			}

			if (scheme.getType(i).equals("Boolean")) {
				bf = new TaoBooleanField();
				obj = bf;
			}
			if (obj != null) {
				if (obj instanceof TaoTextArea) {
			} else {
				tPnl.add(obj, BorderLayout.CENTER);
			}

			htObj.put(scheme.getName(i), obj);

			if (tPnl.getComponentCount() > 1)
				bxRecord.add(tPnl);
			}
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
	}

	public boolean getReadOnly(int aIndex) {
		return ((scheme.getReadOnly(aIndex)) | ((!flgNew) && scheme.getNewOnly(aIndex)));
	}

	public Object getFieldValue(String aName) {
		return htObj.get(aName);
	}

	public void setValues(Vector row) {
		Object cmp;
		Object value;
		JTextField tf;
		TaoTextArea ta;
		JSpinner sp;
		TaoDBComboBox dbcb;
		TaoBooleanField bf;
		for (int i = 0; i < row.size(); i++) {
			value = row.elementAt(i);
			cmp = dlg.getFieldValue(scheme.getName(i));

			if ((cmp != null) && (value != null)) {
				if (cmp instanceof JTextField) {
					tf = (JTextField)cmp;
					tf.setText(value.toString());
				}
				if (cmp instanceof TaoTextArea) {
					ta = (TaoTextArea)cmp;
					ta.setText(value.toString());
				}
				if (cmp instanceof JSpinner) {
					sp = (JSpinner)cmp;

					if (value != null)
						sp.setValue(value);
				}
				if (cmp instanceof TaoDBComboBox) {
					dbcb = (TaoDBComboBox)cmp;
					dbcb.setValue(value);
				}
				if (cmp instanceof TaoBooleanField) {
					bf = (TaoBooleanField)cmp;
					bf.setValue(((Boolean)value).booleanValue());
				}
			}
		}
	}

	public Enumeration getFields() {
		return htObj.keys();
	}

	public String getSQLField(String aName) {
		Object cmp = dlg.getFieldValue(aName);
		if (cmp instanceof JTextField) {
			JTextField tf = (JTextField)cmp;
			return "\"" + tf.getText().replaceAll("\"", "\\\\\"") + "\""; 
		}
		if (cmp instanceof JSpinner) {
			JSpinner sp = (JSpinner)cmp;
			return sp.getValue().toString();
		}
		if (cmp instanceof TaoTextArea) {
			TaoTextArea ta = (TaoTextArea)cmp;
			return "\"" + ta.getText().replaceAll("\"", "\\\\\"") + "\""; 
		}
		if (cmp instanceof TaoDBComboBox) {
			TaoDBComboBox dbcb = (TaoDBComboBox)cmp;
			return dbcb.getValue(); 
		}
		if (cmp instanceof TaoBooleanField) {
			TaoBooleanField bf = (TaoBooleanField)cmp;
			if (bf.getValue())
				return "1";
			else
				return "0";
		}
		return "";
	}

	private JSpinner getNewFloatField() {
		JSpinner result = new JSpinner(new SpinnerNumberModel(0.0, 0, Integer.MAX_VALUE, 0.1));
		result.setEditor(new JSpinner.NumberEditor(result,"##########.##"));
		return result;
	}

	private JSpinner getNewIntField() {
		JSpinner result = new JSpinner(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		result.setEditor(new JSpinner.NumberEditor(result,"############"));
		return result;
	}

	private boolean testFields() {
		JTextField tf;
		String value;

		for (int i = 0; i < scheme.getFieldCount(); i++) {
			if (scheme.getType(i).equals("String")) {
				if (scheme.getIdent(i)) {
					tf = (JTextField)htObj.get(scheme.getName(i));
					value = tf.getText().trim();

					if (!TaoGlobal.isCorrectIndent(value)) {
						tf.requestFocus();
						Dlg.error("В поле " + scheme.getCaption(i) + " разрешены только латинские символы и цифры.");
						return false;
					}
				}
			}
		}
		return true;
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			if (testFields()) {
				dlg.result = true;
				dlg.setVisible(false);
			}
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
