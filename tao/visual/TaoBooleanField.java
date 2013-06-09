/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.Box;

public class TaoBooleanField extends JPanel {
	JRadioButton rbYes = new JRadioButton("Да");
	JRadioButton rbNo = new JRadioButton("Нет");

	public TaoBooleanField() {
		this.setLayout(new BorderLayout());
		Box bxMain = Box.createHorizontalBox();

		ButtonGroup group = new ButtonGroup();
		group.add(rbYes);
		group.add(rbNo);

		bxMain.add(rbYes);
		bxMain.add(Box.createHorizontalStrut(10));
		bxMain.add(rbNo);
		bxMain.add(Box.createHorizontalGlue());
		this.add(bxMain);

		rbYes.setSelected(true);
	}

	public void setValue(boolean aValue) {
		if (aValue) {
			rbYes.setSelected(true);
			rbNo.setSelected(false);
		} else {
			rbYes.setSelected(false);
			rbNo.setSelected(true);
		}
	}

	public boolean getValue() {
		if (rbYes.isSelected())
			return true;
		else
			return false;
	}
}
