/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order.report;

import tao.nottao.FileArrayProvider;
import tao.dialogs.Dlg;
import tao.FrmTaoPrint;
import tao.database.TaoDataModel;
import tao.global.*;
import tao.FrmTaoChild;
import tao.utils.TaoPrintToBrowser;

public class MKZReport {
	public MKZReport(int aTypeOrder, int aID_Order) {
		FileArrayProvider fap = new FileArrayProvider();

		String[] lines;
		try {
			lines = fap.readLines("reports/mkz.html");
		} catch (Exception e) {
			Dlg.error("Ошибка чтения файла \"reports/mkz.html\"");
			return;
		}

		TaoDataModel order1 = new TaoDataModel();
		TaoDataModel order2 = new TaoDataModel();
		TaoDataModel order3 = new TaoDataModel();
		TaoDataModel order4 = new TaoDataModel();
		TaoDataModel order5 = new TaoDataModel();

		try {
			order1.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vorders WHERE ID_Order = '" + aID_Order + "'"));

		if (aTypeOrder == 1) {
			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vstandard_orders WHERE ID_Order = '" + aID_Order + "'"));

			order3.refresh(TaoGlobal.database.query.getNewResultSet("SELECT m.`Art`, m.`Name`, " +
				"om.*, om.quantity * om.price as Summ " +
				"from order_mebel as om " +
				"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel` " +
				"WHERE om.ID_Employee IS null AND ID_Order=" + aID_Order ));
		}

		if (aTypeOrder == 2) {
			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vhome_orders WHERE ID_Order = '" + aID_Order + "'"));
			order3.refresh(TaoGlobal.database.query.getNewResultSet("SELECT m.`Art`, m.`Name`, " +
				"om.*, om.quantity * om.price as Summ "+
				"from order_mebel as om " +
				"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel` " + 
				"WHERE om.ID_Employee IS null AND ID_Order=" + aID_Order ));
		}

		if (aTypeOrder == 3)
			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vkitchen_orders WHERE ID_Order = '" + aID_Order + "'"));

		} catch (Exception e) {
			Dlg.error("Ошибка открытия заказа", e);
			return;
		}

		String fNumber = "";
		String fDate_Begin = "";
		String fDate_End = "";
		String fClient = "";

		if (order1.getData(0, "Number") != null)
			fNumber = order1.getData(0, "Number").toString();

		if (order1.getData(0, "Date_Order_Begin") != null)
		try {
			fDate_Begin = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Begin"));
		} catch (Exception e) {}

		if (order1.getData(0, "Date_Order_End") != null)
		try {
			fDate_End = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_End"));
		} catch (Exception e) {}

		if (order1.getData(0, "Customer") != null)
			fClient = order1.getData(0, "Customer").toString();

		String fMaterial = "";
		if ((aTypeOrder == 1) && (order2.getRowCount() > 0)) {
			if (order2.getData(0, "Material") != null)
				fMaterial = "материал: <b>" + order2.getData(0, "Material").toString() + "</b>";

			if (order2.getData(0, "Color") != null)
				fMaterial = fMaterial + " цвет: <b>" + order2.getData(0, "Color").toString() + "</b>";

			if (order2.getData(0, "Edge") != null)
				fMaterial = fMaterial + " кромка: <b>" + order2.getData(0, "Edge").toString() + "</b>";
		}

		if ((aTypeOrder == 2)  && (order2.getRowCount() > 0)) {
			if (order2.getData(0, "Material") != null)
				fMaterial = "материал: <b>" + order2.getData(0, "Material").toString() + "</b>";

			if (order2.getData(0, "Color_Base") != null)
				fMaterial = fMaterial + " цвет: <b>" + order2.getData(0, "Color_Base").toString() + "</b>";

			if (order2.getData(0, "Color_Box") != null)
				fMaterial = fMaterial + " цвет короба: <b>" + order2.getData(0, "Color_Box").toString() + "</b>";

			if (order2.getData(0, "Color_Facade") != null)
				fMaterial = fMaterial + " цвет фасадов: <b>" + order2.getData(0, "Color_Facade").toString() + "</b>";

			if (order2.getData(0, "Edge") != null)
				fMaterial = fMaterial + " кромка: <b>" + order2.getData(0, "Edge").toString() + "</b>";
		}

		if ((aTypeOrder == 3) && (order2.getRowCount() > 0)) {
			if (order2.getData(0, "Box_Material") != null)
				fMaterial = "материал короба: <b>" + order2.getData(0, "Box_Material").toString() + "</b>";

			if (order2.getData(0, "Facade_Material") != null)
				fMaterial = fMaterial + " материал фасада: <b>" + order2.getData(0, "Facade_Material").toString() + "</b>";

			if (order2.getData(0, "Facade_Glass_Material") != null)
				fMaterial = fMaterial + " стекло фасада: <b>" + order2.getData(0, "Facade_Glass_Material").toString() + "</b>";

			if (order2.getData(0, "Color") != null)
				fMaterial = fMaterial + " цвет: <b>" + order2.getData(0, "Color").toString() + "</b>";

			if (order2.getData(0, "Box_Color") != null)
				fMaterial = fMaterial + " цвет короба: <b>" + order2.getData(0, "Box_Color").toString() + "</b>";

			if (order2.getData(0, "Facade_Color") != null)
				fMaterial = fMaterial + " цвет фасадов: <b>" + order2.getData(0, "Facade_Color").toString() + "</b>";

			if (order2.getData(0, "Table") != null)
				fMaterial = fMaterial + " cтолешница: <b>" + order2.getData(0, "Table").toString() + "</b>";

			if (order2.getData(0, "Wall_Panel") != null)
				fMaterial = fMaterial + " стеновая панель: <b>" + order2.getData(0, "Wall_Panel").toString() + "</b>";
		}

		fMaterial = fMaterial.trim();
		String s;
		String tmpStr = "";
		String reprt = "";
		String fMebel = "";
		String fArt = "";
		String fAmount = "";
		String report = "";

		for (String line : lines) {
			line = line.replaceAll("%DATE_BEGIN%", fDate_Begin);
			line = line.replaceAll("%DATE_END%", fDate_End);
			line = line.replaceAll("%CLIENT%", fClient);
			line = line.replaceAll("%NUMBER%", fNumber);
			line = line.replaceAll("%MATERIAL%", fMaterial);

			if (line.indexOf("%ART%") >= 0) {
				if (aTypeOrder < 3) {
				for (int i = 0; i < order3.getRowCount(); i++) {
					fArt= "";
					fMebel = "";
					fAmount = "";

					if (order3.getData(i, "Art") != null)
						fArt = order3.getData(i, "Art").toString();

					if (order3.getData(i, "Name") != null)
						fMebel = order3.getData(i, "Name").toString();

					if (order3.getData(i, "Quantity") != null)
						fAmount = order3.getData(i, "Quantity").toString();

					tmpStr = line.replaceAll("%ART%", fArt);
					tmpStr = tmpStr.replaceAll("%MEBEL%", fMebel);
					tmpStr = tmpStr.replaceAll("%AMOUNT%", fAmount);
					report = report + tmpStr;
				}

				line = "";
			}

			if (aTypeOrder == 3) {
				fArt= "";
				fMebel = "Кухня";
				fAmount = "";

				if (order1.getData(0, "Quantity") != null)
					fAmount = order1.getData(0, "Quantity").toString();

				if (order2.getRowCount() > 0)
					if (order2.getData(0, "Model") != null)
						fMebel = fMebel + " " + order2.getData(0, "Model").toString();

					tmpStr = line.replaceAll("%ART%", fArt);
					tmpStr = tmpStr.replaceAll("%MEBEL%", fMebel);
					tmpStr = tmpStr.replaceAll("%AMOUNT%", fAmount);
					report = report + tmpStr;
					line = "";
				}
			}
			report = report + line;
		}
		new TaoPrintToBrowser(report);
	}
}
