/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.BorderFactory;
import javax.swing.border.*;

public class TaoBorder extends CompoundBorder {
	public TaoBorder() {
		super();
		this.insideBorder = new CompoundBorder(new EtchedBorder(), new EmptyBorder(4, 4, 4, 4));
		this.outsideBorder = new EmptyBorder(4, 4, 4, 4);
	}
}
