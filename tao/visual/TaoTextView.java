/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

public class TaoTextView extends JTextArea {
	public TaoTextView(String aText) {
		super(aText);
		this.setEditable(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setBorder(new EmptyBorder(4, 4, 4, 4));
		this.setOpaque(false);
	}
}
