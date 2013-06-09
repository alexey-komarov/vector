/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.xml;

import tao.dialogs.Dlg;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.util.Vector;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

public class TaoXMLFrame {
	private Document document = null;
	private Vector<Element> splitters = new Vector<Element>();
	private String xmlHead = "<?xml version=\"1.0\" encoding=\"windows-1251\"?>";

	public TaoXMLFrame(String xml) {
		if (xml == null)
			xml = xmlHead + "<frame></frame>";
		else if (xml.indexOf("<?xml") != 0)
			xml = xmlHead + xml;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(new InputSource(new StringReader(xml)));
			if (!document.getDocumentElement().getTagName().toUpperCase().equals("FRAME")) {
				document = null;
			} else {
				NodeList nodes_i = document.getDocumentElement().getChildNodes();

				for (int i = 0; i < nodes_i.getLength(); i++) {
					Node node_i = nodes_i.item(i);
					if ( (node_i.getNodeType() == Node.ELEMENT_NODE) &&
					     (((Element) node_i).getTagName().toUpperCase().equals("SPLITTER")) )
					{
						splitters.addElement((Element) node_i);
					}
				}
			}
		} catch (Exception e) {
			Dlg.error("Ошибка разбора XML", e + " [" + xml +"]", e);
		}
	}

	public Dimension getSize() {
		int width = 0;
		int height = 0;

		try {
			width = new Integer(document.getDocumentElement().getAttribute("Width").toString());
			height = new Integer(document.getDocumentElement().getAttribute("Height").toString());
		} catch (Exception e) {}
			return new Dimension(width, height);
		}

		public Point getLocation() {
			int x = 0;
			int y = 0;
			try {
				x = new Integer(document.getDocumentElement().getAttribute("X").toString());
				y = new Integer(document.getDocumentElement().getAttribute("Y").toString());
			} catch (Exception e) {}

			return new Point(x, y);
		}

		public int getState() {
		try {
			String state = document.getDocumentElement().getAttribute("State").toString();

			if (state.equals("ICONIFIED"))
				return 0;
			else if (state.equals("MAXIMIZED_BOTH"))
				return Frame.MAXIMIZED_BOTH;
			else if (state.equals("MAXIMIZED_HORIZ"))
				return Frame.MAXIMIZED_HORIZ;
			else if (state.equals("MAXIMIZED_VERT"))
				return Frame.MAXIMIZED_VERT;
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public int getSplitterLocation(int aNumber) {
		if (splitters == null) {
			return -1;
		} else {
			try {
				return Integer.parseInt(splitters.elementAt(aNumber).getAttribute("Location"));
			} catch (Exception e) {
				return -1;  
			}
		}
	}

	public String getXML() {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);

			return result.getWriter().toString();
		} catch(Exception e) {
			return "";
		}
	}

	public void applyScheme(JFrame frm) {
		Dimension d = this.getSize();

		if (d.width == 0)
			d.width = 600;

		if (d.height == 0)
			d.width = 400;

		if (frm.isResizable()) {
			frm.setSize(d);
		}

		frm.setLocation(this.getLocation());

		switch (this.getState()) {
			case Frame.MAXIMIZED_BOTH:
				frm.setExtendedState(Frame.MAXIMIZED_BOTH);
				break;
			case Frame.MAXIMIZED_HORIZ: 
				frm.setExtendedState(Frame.MAXIMIZED_HORIZ);
				break;
			case Frame.MAXIMIZED_VERT:
				frm.setExtendedState(Frame.MAXIMIZED_VERT);
				break;
		}
	}

	public void applyScheme(JInternalFrame frm) {
		Dimension d = this.getSize();

		if ((d != null) && (d.width > 0) && (d.height > 0))
			if (frm.isResizable()) {
				frm.setSize(d);
			}

			frm.setLocation(this.getLocation());
			setSplittersXML(frm);

			switch (this.getState()) {
				case Frame.MAXIMIZED_BOTH:
					try {
						frm.setMaximum(true);
					} catch (Exception e) {}
				}
	}

	private void setSplittersXML(Container cnt) {
		int splNum = 0;
		int Location = -1;
		JSplitPane spl;
		for (int k = 0; k < cnt.getComponentCount(); k++) {
			if (cnt.getComponent(k) instanceof JSplitPane) {
				spl = (JSplitPane)cnt.getComponent(k);
				Location = getSplitterLocation(splNum++);
				if (Location >= 0) {
					spl.setDividerLocation(Location);
				}
			} else if (cnt.getComponent(k) instanceof Container)
				setSplittersXML((Container)cnt.getComponent(k));
		}
	}

	public void addShowDeleted() {}
}
