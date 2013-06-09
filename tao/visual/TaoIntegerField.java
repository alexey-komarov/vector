/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;
import javax.swing.*;

public class TaoIntegerField extends JSpinner {
	public TaoIntegerField() {
		super(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		this.setEditor(new JSpinner.NumberEditor(this, "############"));
	}
}
