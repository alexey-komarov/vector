/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JFileChooser;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JSeparator;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Properties;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.StringWriter;

import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.visual.TaoTextView;

public class DlgError extends TaoDialog {
	private JPanel
		pnlMain = new JPanel(new BorderLayout()),
		pnlLeft = new JPanel(new BorderLayout());

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	private JLabel lbIco = new JLabel();

	private TaoTextView
		taLongMsg,
		taShortMsg;

	private JButton
		btnOk = new JButton(),
		btnInfo = new JButton(),
		btnSave = new JButton("Save");

	private AcOk acOk;
	private AcSave acSave;
	private AcShowInfo acShowInfo;
	private AcHideInfo acHideInfo;

	private Box
		bxButtons = Box.createHorizontalBox(),
		bxSave = Box.createHorizontalBox(),
		bxRight = Box.createVerticalBox(),
		bxMain = Box.createHorizontalBox();

	public DlgError(JFrame frm, String shortMsg, String longMsg, Exception exception) {
		super(frm,"Ошибка");

		if (exception instanceof SQLException) {
			SQLException sqlexception = (SQLException)exception;
			String errCode = sqlexception.getSQLState(); 

			if (errCode.equals("28000")) { 
				shortMsg = shortMsg +" (Доступ запрещен!)";
			} else if (errCode.equals("42000")) {
				shortMsg = shortMsg +" (Доступ запрещен!)";
			} else if (errCode.equals("08S01")) {
				shortMsg = shortMsg +" (Не могу установить соединение с сервером баз данных!)";
			} else {
				shortMsg = shortMsg +" (Ошибка установки соединения с базой данной!)";
			}
		}

		taShortMsg = new TaoTextView(shortMsg);
		taLongMsg = new TaoTextView(longMsg);

		acOk = new AcOk();
		acShowInfo = new AcShowInfo(this);
		acHideInfo = new AcHideInfo(this);
		acSave = new AcSave(frm, exception);

		btnOk.setAction(acOk);
		btnInfo.setAction(acShowInfo);
		btnSave.setAction(acSave);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);

		btnSave.setPreferredSize(new Dimension(32, 32));
		if (exception != null) {
			bxSave.add(btnSave);
		}

		if (longMsg != null) {
			bxButtons.add(Box.createHorizontalStrut(10));
			bxButtons.add(btnInfo);
		}

		lbIco.setIcon(Nuvola.actions.ll_messagebox_critical);
		lbIco.setVerticalAlignment(JLabel.TOP);

		pnlLeft.add(lbIco, BorderLayout.CENTER);
		pnlLeft.setBorder(new EmptyBorder(0, 0, 0, 8));

		bxRight.add(taShortMsg, BorderLayout.CENTER);
		bxRight.add(Box.createVerticalStrut(10));
		bxRight.add(new JSeparator());
		bxRight.add(Box.createVerticalStrut(10));
		bxRight.add(bxButtons);
		bxRight.add(Box.createVerticalStrut(10));

		pnlMain.setBorder(new EmptyBorder(8, 8, 8, 8));
		pnlMain.add(pnlLeft, BorderLayout.WEST);
		pnlMain.add(bxRight, BorderLayout.CENTER);

		this.setContentPane(pnlMain);
		this.setResizable(false);
		this.setModal(true);

		getRootPane().setDefaultButton(btnOk);

		pack();
		taShortMsg.setColumns(1);
		pack();
		getRootPane().setDefaultButton(btnOk);

		int x = (dim.width - this.getWidth()) >> 1;
		int y = (dim.height - this.getHeight()) >> 1;
		this.setLocation(x, y);
	}

	public String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	class AcShowInfo extends AbstractAction {
		private DlgError frmParent;
		private Component vs = Box.createVerticalStrut(10);
		private Component sp = new JSeparator(); 

		public AcShowInfo(DlgError frm) {
			putValue(Action.NAME, "Дополнительная информация");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_1downarrow);
			frmParent = frm;
		}

		public void actionPerformed(ActionEvent event) {
			btnInfo.setAction(frmParent.acHideInfo);
			frmParent.bxRight.add(sp);
			frmParent.bxRight.add(vs);
			frmParent.bxRight.add(frmParent.taLongMsg);
			frmParent.pnlLeft.add(bxSave, BorderLayout.SOUTH);
			frmParent.pack();
			taLongMsg.setColumns(1);
			frmParent.pack();
			frmParent.repaint();
		}
	}

	class AcHideInfo extends AbstractAction {
		private DlgError frmParent;
		AcHideInfo(DlgError frm) {
			putValue(Action.NAME, "Дополнительная информация");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_1uparrow);
			frmParent = frm;
		}

		public void actionPerformed(ActionEvent event) {
			btnInfo.setAction(frmParent.acShowInfo);
			frmParent.bxRight.remove(frmParent.taLongMsg);
			frmParent.pnlLeft.remove(frmParent.bxSave);

			frmParent.pack();
			frmParent.repaint();
		}
	}

	class AcSave extends AbstractAction {
		private Exception exception;
		private JFrame frmParent;

		public AcSave(JFrame frm, Exception aexception) {
			putValue(Action.NAME, "");
			putValue(Action.SHORT_DESCRIPTION, "Сохранить отчет");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_save);
			exception = aexception;
			frmParent = frm;
		}

		public String fileName(int number) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String stDate = formatter.format(new Date());

			if (number == 0) {
				return "/TaoError_" + stDate + ".txt";
			} else {
				return "/TaoError_" + stDate + "(" + Integer.toString(number) +  ").txt";
			}
		}

		public void actionPerformed(ActionEvent event) {
			String wd = System.getProperty("user.dir");
			JFileChooser fc = new JFileChooser(wd);

			try {
				File file = new File(wd + fileName(0) );
				int i = 1;
				while (file.exists()) {
					file = new File(wd + fileName(i++) );
				} 

				fc.setSelectedFile(file);

				int rc = fc.showDialog(null, "Выберите файл");
				if (rc == JFileChooser.APPROVE_OPTION) {
					PrintWriter out = new PrintWriter(new FileWriter(fc.getSelectedFile()));
					out.print(getStackTrace(exception));
					out.close();
				}
			} catch(Exception e) {
				Dlg.error(frmParent, "Ошибка записи файла", e);
			}
		}
	}
}
