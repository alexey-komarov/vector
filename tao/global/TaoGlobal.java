/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.global;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import java.text.SimpleDateFormat;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Cursor;
import javax.swing.RootPaneContainer;

import java.util.Hashtable;

import tao.database.Database;
import tao.config.Config;

import tao.parameters.*;
import tao.dialogs.Dlg;
import java.util.regex.*;
import java.util.Calendar;
import java.util.Date;

import java.sql.*;
import java.io.*;

public class TaoGlobal {
	public static JToolBar taskbar = new JToolBar();
	public static JToolBar infobar = new JToolBar();
	public static Database database = null;
	public static Config config = null;
	public static JPanel divider = new JPanel();
	public static JFrame frmMain;
	public static Pattern ident_pattern = Pattern.compile("[a-zA-Z][\\w]*");
	public static TaoParametersCollection parameters = new TaoParametersCollection();
	public static JDesktopPane rootPane;
	public static int ttCounter = 0;
	public static int ID_Furniture_Group_Last = -1;
	public static int ID_Customer_Group_Last = -1;
	public static String LastImageDir = null;
	private static Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
	private static Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	public static void init() {
		divider.setBackground(new Color(SystemColor.WINDOW_BORDER));
		divider.setPreferredSize(new Dimension(0, 1));
		divider.setMaximumSize(new Dimension(0, 1));
		taskbar.setVisible(false);
	}

	public static void loadParameters() throws Exception {
		new DirectoriesInitParameters();
		new MainInitParameters();
		new OrdersInitParameters();
		parameters.loadUserParameters(database.users.getID_User());
	}

	public static void updateTaskBar(JButton aBtn) {
		if (taskbar.getComponentCount() == 0) {
			taskbar.setVisible(false);
		} else {
			taskbar.setVisible(true);
			for (int k = 0; k < taskbar.getComponentCount(); k++) {
				if (taskbar.getComponent(k) instanceof JButton) {
					JButton btn = (JButton)taskbar.getComponent(k); 
					if (btn == aBtn) {
						btn.setFont(new Font("Lucida", Font.BOLD, 12));
						btn.setBackground(new Color(255, 255, 255));
					} else {
						btn.setBackground(new Color(220, 230, 230));
						btn.setFont(new Font("Lucida", Font.PLAIN, 12));
					}
				}
			}
		} 
		taskbar.repaint();
	}

	public static boolean isCorrectIndent(String aValue) {
		return ident_pattern.matcher(aValue).matches();
	}

	public static String currSQLDate() {
		Calendar cal = Calendar.getInstance();
		java.sql.Date result = new java.sql.Date(((Date)cal.getTime()).getTime());
		return "\"" + result  + "\"";
	}

	public static String currDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd MMMMMMMMMM yyyy");
		Calendar cal = Calendar.getInstance();
		StringBuilder result = new StringBuilder(df.format(cal.getTime()));
		return result + "";
	}

	public static String stringSQLDate(java.sql.Date aDate) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMMMMMMMMM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		StringBuilder result = new StringBuilder(df.format(cal.getTime()));
		return result + "";
	}

	public static String stringSQLDateTime(java.sql.Timestamp aDate) {
		SimpleDateFormat df = new SimpleDateFormat("dd MMMMMMMMMM yyyy HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		StringBuilder result = new StringBuilder(df.format(cal.getTime()));
		return result + "";
	}

	public static String getFileNamePostfix() {
		Calendar cal = Calendar.getInstance();
		TaoGlobal.ttCounter++;
		return "_" + System.currentTimeMillis() + "_" + TaoGlobal.ttCounter;
	}

	public static void startWaitCursor() {
		RootPaneContainer root =(RootPaneContainer)rootPane.getTopLevelAncestor();
		root.getGlassPane().setCursor(hourglassCursor);
		root.getGlassPane().setVisible(true);
	}

   /** Sets cursor for specified component with normal cursor */
	public static void stopWaitCursor() {
		RootPaneContainer root = (RootPaneContainer)rootPane.getTopLevelAncestor();
		root.getGlassPane().setCursor(normalCursor);
		root.getGlassPane().setVisible(false);
	}

	public static void cleartempdir() {
		try {
			if (TaoGlobal.config.localParams.getProperty("tempdir") != null) {
				File directory = new File(TaoGlobal.config.localParams.getProperty("tempdir"));
				File[] files = directory.listFiles();

				for (File file : files)
					file.delete();
			}
		} catch (Exception e) {}
	}
}
