/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import javax.swing.text.View;

public class TaoPrintPane extends JEditorPane implements Printable, Serializable {
	protected int currentPage = -1;
	protected JEditorPane jeditorPane;
	protected double pageEndY = 0;
	protected double pageStartY = 0;
	protected boolean scaleWidthToFit = true;

	public TaoPrintPane(String text) {
		setContentType("text/html");
		setText(text);
	}

	public int print (Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor (Color.black);

		RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		Dimension d = this.getSize();
		double panelWidth = d.width;
		double panelHeight = d.height;
		double pageWidth = pf.getImageableWidth();
		double pageHeight = pf.getImageableHeight();

		double scale = 0.73;

		int totalNumPages = (int)Math.ceil(scale * panelHeight / pageHeight);

		if (pageIndex >= totalNumPages)
			return Printable.NO_SUCH_PAGE;

		g2.translate(pf.getImageableX(), pf.getImageableY());
		g2.translate(0f, -pageIndex * pageHeight);
		g2.scale(scale, scale);

		this.paint(g2);

		return Printable.PAGE_EXISTS;
	}
}
