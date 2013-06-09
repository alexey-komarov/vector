/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class TaoDatePicker extends JSpinner {
	private JPanel pnl = new JPanel();
	private JCheckBox cb = new JCheckBox();
	private TaoDatePicker sp;

	public TaoDatePicker() {
		sp = this;
		on();
		initializeUI();
		pnl.setLayout(new BorderLayout());
		pnl.add(cb, BorderLayout.WEST);
		pnl.add(this, BorderLayout.CENTER);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton = (AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean selected = buttonModel.isSelected();
				sp.setEnabled(selected);
			}
		};

		cb.addChangeListener(changeListener);
	}

	public JPanel getPanel() {
		return pnl;
	}

	public void off() {
		cb.setSelected(false);
	}

	public void on() {
		cb.setSelected(true);
	}

	private void initializeUI() {
		SpinnerDateModel model = new SpinnerDateModel();
		this.setModel(model);
		Calendar calendar = Calendar.getInstance();
		this.setValue(calendar.getTime());
		this.setEditor(new JSpinner.DateEditor(this, "dd MMMMMMMMMM yyyy"));
	}

	public String getSQL() {
		if (cb.isSelected()) {
			java.sql.Date result = new java.sql.Date(((Date) this.getValue()).getTime());
			return "\"" + result + "\"";
		} else
			return "null";
	}

	public void setDate(java.sql.Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(aDate);
		this.setValue(calendar.getTime());
	}
}
