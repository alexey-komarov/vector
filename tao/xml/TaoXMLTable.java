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

import java.util.Vector;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

public class TaoXMLTable {
	private Document document = null;
	private Vector<Element> fields = new Vector<Element>();
	private String xmlHead = "<?xml version=\"1.0\" encoding=\"windows-1251\"?>";
	public TaoXMLTable(String xml) {
		if (xml.indexOf("<?xml") != 0) {
			xml = xmlHead + xml;
		}

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(new InputSource(new StringReader(xml)));

			NodeList nodes_i = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodes_i.getLength(); i++) {
				Node node_i = nodes_i.item(i);

				if ( (node_i.getNodeType() == Node.ELEMENT_NODE) &&
				     (((Element) node_i).getTagName().toUpperCase().equals("FIELD")) )
				{
					fields.addElement((Element) node_i);
				}
			}
		} catch (Exception e) {
			Dlg.error("Ошибка разбора XML", e + " [" + xml +"]", e);
		}
	}

	private Element getFieldByName(String aName) {
		Element result = null;
		for (int i = 0; i < fields.size(); i++) {
			if (fields.elementAt(i).getAttribute("Name").equals(aName)) {
				result = fields.elementAt(i);
				break;
			}
		}
		return result;
	}

	//Получить заголовок поля
	public String getCaption(String aName) {
		Element field = getFieldByName(aName);
		if (field == null) {
			return aName;
		} else {
			return field.getAttribute("Caption");
		}
	}

	//Получить имя поля по номеру
	public String getName(int aIndex) {
		return fields.elementAt(aIndex).getAttribute("Name");
	}

	public String getCaption(int aIndex) {
		Element field = fields.elementAt(aIndex);
		if (field == null) {
			return "";
		} else {
			return field.getAttribute("Caption");
		}
	}

	//Получить ширину
	public int getWidth(String aName) {
		Element field = getFieldByName(aName);
		int result = -1;
		if (field != null) {
			try {
				result = new Integer(field.getAttribute("Width").toString());
			} catch (Exception e) {}
		}
		return result;
	}

	//установить ширину
	public void setWidth(String aName, int aWidth) {
		Element field = getFieldByName(aName);
		int result = -1;
		if (field != null) {
			field.setAttribute("Width", Integer.toString(aWidth));
		}
	}

	public int getWidth(int aIndex) {
		try {
			return new Integer(fields.elementAt(aIndex).getAttribute("Width").toString());
		}
		catch(Exception e) {
			return 0;
		}
	}

	public String getType(int aIndex) {
		try {
			return fields.elementAt(aIndex).getAttribute("Type").toString();
		} catch(Exception e) {
			return "";
		}
	}

	public String getDirectory(int aIndex) {
		try {
			return fields.elementAt(aIndex).getAttribute("Directory").toString();
		}
		catch(Exception e) {
			return "";
		}
	}

	public Boolean getHidden(int aIndex) {
		return getBoolean("Hidden", aIndex);
	}

	public Boolean getIdent(int aIndex) {
		return getBoolean("Ident", aIndex);
	}

	public Boolean getReadOnly(int aIndex) {
		return getBoolean("ReadOnly", aIndex);
	}

	public Boolean getNewOnly(int aIndex) {
		return getBoolean("NewOnly", aIndex);
	}

	public boolean getBoolean(String attr, int aIndex) {
		try {
			if (fields.elementAt(aIndex).getAttribute(attr).equals("1"))
				return true;
			else
				return false;
		} catch(Exception e) {
			return false;
		}
	}

	public int getFieldCount() {
		return fields.size();
	}

	//видимое ли это поле?
	public boolean getVisible(String aName) {
		Element field = getFieldByName(aName);

		if (field == null) {
			return true;
		} else {
			if (field.getAttribute("Visible").equals("0")) {
				return false;
			} else {
				return true;
			}
		}
	}

	public boolean getVisible(int aIndex) {
		if (fields.elementAt(aIndex) == null) {
			return false;
		} else {
				if (fields.elementAt(aIndex).getAttribute("Visible").equals("0")) {
					return false;
			} else {
				return true;
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
}
