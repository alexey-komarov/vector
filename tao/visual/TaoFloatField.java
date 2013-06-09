/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;
import javax.swing.*;

public class TaoFloatField extends JSpinner {
	public TaoFloatField() {
		super(new SpinnerNumberModel(0.0, 0, Integer.MAX_VALUE, 0.1));
		this.setEditor(new JSpinner.NumberEditor(this, "############.##"));
	}
}
