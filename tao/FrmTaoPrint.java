/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.awt.*;
import java.io.*;
import java.awt.print.*;
import javax.swing.*;
import java.awt.event.*;
import tao.visual.*;
import tao.icons.Nuvola;
import java.text.SimpleDateFormat;
import tao.dialogs.Dlg;
import java.util.Date;
import tao.nottao.DocumentRenderer;

public class FrmTaoPrint extends FrmTaoChild {
	private JToolBar bar = new JToolBar();
	private JPanel pnlMain = new JPanel();
	private AcPrint acPrint = new AcPrint();
	private AcSave acSave = new AcSave();
	private JButton btnSave = new JButton(acSave);
	private JButton btnPrint = new JButton(acPrint);
	private TaoPrintPane printPane;
	private JScrollPane scrText;
	private String HTML;

	public FrmTaoPrint(String aHTML) {
		super("Печать", Nuvola.devices.s_printer);
		this.resizable = false;
		HTML = aHTML;

		printPane = new TaoPrintPane(aHTML);
		scrText = new JScrollPane(printPane);

		pnlMain.setLayout(new BorderLayout());
		this.getContentPane().add(pnlMain);

		pnlMain.add(scrText, BorderLayout.CENTER);
		pnlMain.add(bar, BorderLayout.NORTH);
		bar.add(btnPrint);
		bar.add(btnSave);
		bar.setFloatable(false);

		pack();
		this.setSize(new Dimension(768, 512));

		printPane.setCaretPosition(0);
	}

	public String fileName(int number) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String stDate = formatter.format(new Date());

		if (number == 0) {
			return "/Report_" + stDate + ".html";
		} else {
			return "/Report_" + stDate + "(" + Integer.toString(number) +  ").html";
		}
	}

	public class AcPrint extends AbstractAction {
		AcPrint() {
			putValue(Action.NAME, "Печать");
			putValue(Action.SHORT_DESCRIPTION, "Распечать");
			putValue(Action.SMALL_ICON,Nuvola.devices.s_printer);
		}

		public void actionPerformed(ActionEvent event) {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(printPane);
			if (job.printDialog()) {
				try {
					job.print();
				}
				catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
	}

	public class AcSave extends AbstractAction {
		AcSave() {
			putValue(Action.NAME, "Сохранить");
			putValue(Action.SHORT_DESCRIPTION, "Сохранить отчёт в HTML файл");
			putValue(Action.SMALL_ICON,Nuvola.devices.s_printer);
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

				int rc = fc.showDialog(null, "Веберите файл");
				if (rc == JFileChooser.APPROVE_OPTION) {
					PrintWriter out = new PrintWriter(new FileWriter(fc.getSelectedFile()));
					out.print(HTML);
					out.close();
				}
			} catch(Exception e) {
				Dlg.error("Ошибка записи файла", e);
			}
		}
	}
}
