/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TaoTextArea extends JTextArea {
	public JScrollPane scrText;

	public TaoTextArea(String aText) {
		super(aText);
		scrText = new JScrollPane(this);

		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		scrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}
