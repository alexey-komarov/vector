/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.utils;

import tao.dialogs.Dlg;
import tao.global.TaoGlobal;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import tao.FrmTaoChild;
import java.awt.Desktop;
import tao.FrmTaoPrint;
import java.net.URI;

public class TaoPrintToBrowser {
	public TaoPrintToBrowser(String a) {
		String path = TaoGlobal.config.localParams.getProperty("tempdir");
		String fileName = path + "/report" + TaoGlobal.getFileNamePostfix() + ".html";

		try {
			File file = new File(fileName); 
			FileWriter fw = new FileWriter(file);
			Writer output = new BufferedWriter(fw);
			output.write(a);
			output.close();
		} catch (Exception e) {
			Dlg.error("Ошибка сохранения отчета в файл " + fileName, e); 
			return;
		}

		Desktop desktop = null;
		if (desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(URI.create(fileName));
				} catch (Exception ex) {
					Dlg.error("Ошибка открытия бразузера по умолчанию!", ex);
					FrmTaoChild frmTaoPrint = new FrmTaoPrint(a);
					try {
						TaoGlobal.rootPane.add(frmTaoPrint);
						frmTaoPrint.setVisible(true);
					} catch(Exception e) {
						Dlg.error("Ошибка открытия окна печати", e);
					}
				}
			}
		}
	}
}
