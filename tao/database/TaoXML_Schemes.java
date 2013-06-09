/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database;

import tao.global.TaoGlobal;
import tao.dialogs.Dlg;

import java.sql.ResultSet;
import java.util.Properties;
import java.awt.Container;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.JFrame;

import tao.xml.TaoXMLFrame;

public class TaoXML_Schemes extends TaoDatabase {
	Properties pSchemes = new Properties();

	public TaoXML_Schemes() {
		super();
	}

	public void reload() throws Exception {
		fillProperties(pSchemes, "SELECT * FROM xml_schemes WHERE ID_User = 1");
		fillProperties(pSchemes, "SELECT * FROM xml_schemes WHERE ID_User = " +
			TaoGlobal.database.users.getID_User());
	}

	public String getScheme(String aID_Scheme) {
		return pSchemes.getProperty(aID_Scheme);
	}

	public String getScheme(Component cmp) {
		String cname = cmp.getClass().toString();
		String words[] = cname.split(" ");
		return pSchemes.getProperty(words[1]);
	}

	public void setScheme(String aID_Scheme, String aXML) {
		pSchemes.setProperty(aID_Scheme, aXML);
	}

	public void saveScheme(String aID_Scheme) throws Exception {
		TaoGlobal.database.visual.schemeStore(aID_Scheme, pSchemes.getProperty(aID_Scheme));
	}

	public void saveAndSetScheme(String aID_Scheme, String aXML) {
		setScheme(aID_Scheme, aXML);
		try {
			saveScheme(aID_Scheme);
		} catch (Exception e) {
			Dlg.error("Ошибка сохранения схемы!", e);
		}
	}

	private String clName(Component cmp) {
		String cname = cmp.getClass().toString();
		String words[] = cname.split(" ");
		return words[1];
	}

	private String splittersXML(Container cnt) {
		String result = "";
		int splNum = 0;
		JSplitPane spl;
		for (int k = 0; k < cnt.getComponentCount(); k++) {
			if (cnt.getComponent(k) instanceof JSplitPane) {
				spl = (JSplitPane)cnt.getComponent(k);

				result = result + "<Splitter Number=\"" + k++ + "\" Location=\"" +
					spl.getDividerLocation() + "\"/>";
			} else if (cnt.getComponent(k) instanceof Container)
				result = result + splittersXML((Container)cnt.getComponent(k));
		}
		return result;
	}

	public void saveAndSetScheme(Container cnt) {
		String xml;

		String swidth = "Width=\"" + cnt.getSize().width + "\"";
		String sheight = "Height=\"" + cnt.getSize().height + "\"";
		String sx = "X=\"" + cnt.getLocation().x +"\"";
		String sy = "Y=\"" + cnt.getLocation().y +"\"";

		xml = "<frame";

		if (cnt instanceof Frame) {
			Frame frm = (Frame)cnt;
			TaoXMLFrame xmlFrame = new TaoXMLFrame(TaoGlobal.database.schemes.getScheme(frm));
			String oswidth = " Width=\"" + xmlFrame.getSize().width + "\"";
			String osheight = " Height=\"" + xmlFrame.getSize().height + "\"";
			String osx = " X=\"" + xmlFrame.getLocation().x + "\"";
			String osy = " Y=\"" + xmlFrame.getLocation().y + "\"";

			switch(frm.getExtendedState()) {
				case Frame.ICONIFIED: 
					xml = xml + " State=\"ICONIFIED\"";
					xml = xml + " " + oswidth + " " +osheight;
					xml = xml +" " + osx + " " + osy;
					break;
				case Frame.MAXIMIZED_BOTH: 
					xml = xml + " State=\"MAXIMIZED_BOTH\"";
					xml = xml + " " + oswidth + " " + osheight;
					xml = xml +" " + osx + " " + osy;
					break;
				case Frame.MAXIMIZED_HORIZ: 
					xml = xml + " State=\"MAXIMIZED_HORIZ\" ";
					xml = xml + " " + swidth + " " + osheight;
					xml = xml +" " + sx + " " + osy;
					break;
				case Frame.MAXIMIZED_VERT: 
					xml = xml + " State=\"MAXIMIZED_VERT\" ";
					xml = xml + " " + oswidth + " " +osheight;
					xml = xml +" " + osx + " " + sy;
					break;
				default:
					xml = xml + " " + swidth + " " + sheight;
					xml = xml +" " + sx + " " + sy;
					break;
			}
		}

		if (cnt instanceof JInternalFrame) {
			JInternalFrame frm = (JInternalFrame)cnt;
			TaoXMLFrame xmlFrame = new TaoXMLFrame(TaoGlobal.database.schemes.getScheme(frm));
			String oswidth = " Width=\"" + xmlFrame.getSize().width + "\"";
			String osheight = " Height=\"" + xmlFrame.getSize().height + "\"";
			String osx = " X=\"" + xmlFrame.getLocation().x + "\"";
			String osy = " Y=\"" + xmlFrame.getLocation().y + "\"";

			if (frm.isIcon()) {
				xml = xml + " " + oswidth + " " + osheight;
				xml = xml +" " + osx + " " + osy;
			} else if (frm.isMaximum()) {
				xml = xml + " State=\"MAXIMIZED_BOTH\"";
				xml = xml + " " + oswidth + " " + osheight;
				xml = xml +" " + osx + " " + osy;
			} else {
				xml = xml + " " + swidth + " " + sheight;
				xml = xml +" " + sx + " " + sy;
			}
		} 

		xml = xml + ">";

		if (!(cnt instanceof JFrame))
			xml = xml + splittersXML(cnt);

		xml = xml + "</frame>";

		setScheme(clName(cnt), xml);

		try {
			saveScheme(clName(cnt));
		} catch (Exception e) {
			Dlg.error("Ошибка сохранения схемы!", e);
		}
	}
}
