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
import tao.utils.TaoPrintToBrowser;
import java.io.*;
import tao.FrmTaoChild;

public class StandardReport {
	public StandardReport(int aID_Order) {
		FileArrayProvider fap = new FileArrayProvider();

		String[] lines;
		try {
			lines = fap.readLines("reports/standart_order.html");
		} catch (Exception e) {
			Dlg.error("Ошибка чтения файла \"reports/standart_order.html\"");
			return;
		}

		TaoDataModel order1 = new TaoDataModel();
		TaoDataModel order2 = new TaoDataModel();
		TaoDataModel order3 = new TaoDataModel();
		TaoDataModel order4 = new TaoDataModel();
		TaoDataModel order5 = new TaoDataModel();

		try {
			order1.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vorders WHERE ID_Order = '" + aID_Order + "'"));

			order3.refresh(TaoGlobal.database.query.getNewResultSet("SELECT f.`Art`, f.`Name`, " +
				"ofu.*, u.Name as Unit_Name " +
				"from order_furniture as ofu " +
				"inner join furniture as f on f.`ID_Furniture` = ofu.`ID_Furniture`" +
				"inner join units as u on u.`ID_Unit` = f.`ID_Unit` WHERE ID_Order=" + aID_Order ));

			order4.refresh(TaoGlobal.database.query.getNewResultSet("SELECT m.`Art`, m.`Name`, " +
				"om.*, om.quantity * om.price as Summ " +
				"from order_mebel as om " +
				"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel` " +
				"WHERE om.ID_Employee IS null AND ID_Order=" + aID_Order ));

			order5.refresh(TaoGlobal.database.query.getNewResultSet("SELECT m.`Art`, m.`Name`, " +
				"om.*, om.quantity * om.price as Summ, e.Family as mFamily, e.Name as mName, e.Phone as mPhone " +
				"from order_mebel as om " +
				"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel` " + 
				"inner join employers as e on e.`ID_Employee` = om.`ID_Employee` " +
				"WHERE om.ID_Employee IS NOT null AND ID_Order=" + aID_Order ));

			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vstandard_orders WHERE ID_Order = '" + aID_Order + "'"));
		} catch (Exception e) {
			Dlg.error("Ошибка открытия заказа", e);
			return;
		}

		String fFurniture = "";

		for (int i = 0; i < order3.getRowCount(); i++) {
			if (order3.getData(i, "Name") != null) {
				if (fFurniture.length() > 0)
					fFurniture = fFurniture + ", ";

				fFurniture = fFurniture + order3.getData(i, "Name").toString() + " " +
					order3.getData(i, "Quantity").toString() + " " +
					order3.getData(i, "Unit_Name").toString(); 
			}
		}

		String fManager = "";
		String fManagerAlt = "";
		String fContact = "";

		if (order1.getData(0, "Address") != null)

		fContact = fContact + order1.getData(0, "Address").toString();

		if (order1.getData(0, "Phone") != null)
			fContact = fContact + " Тел.: " + order1.getData(0, "Phone").toString();

		if (order1.getData(0, "Contact") != null)
			fContact = fContact + " " + order1.getData(0, "Contact").toString();

		if (order1.getData(0, "mFamily") != null)
			fManager = fManager + order1.getData(0, "mFamily").toString();

		if (order1.getData(0, "mName") != null)
			fManager = fManager + " " + order1.getData(0, "mName").toString();

		if (order1.getData(0, "mPhone") != null)
			fManager = fManager + " тел.:" + order1.getData(0, "mPhone").toString();

		fManager = fManager.trim();

		String fDate_Shiping_Wanting = "";
		String fDate_Shiping_Max = "";
		String fNumber = "";
		String fDate_Order_Begin = "";
		String fDate_Order_End = "";
		String fMaterial = "";
		String fColor = "";
		String fEdge = "";
		String fGlass = "";
		String fHandle = "";
		String fMebel_Info = "";
		String fPack_Info = "";
		String fClient_Info = "";
		String fSum = "";
		String fFloor = "";
		String fCustomer = "";
		String fM_Name = "";
		String fM_Square = "";
		String fM_Price = "";
		String fM_Quantity = "";
		String fM_Sum = "";
		String tmpStr = "";

		if (order1.getData(0, "Floor") != null)
			if (!order1.getData(0, "Floor").toString().equals("0"))
				if (!order1.getData(0, "Floor").toString().equals(""))
					fFloor = order1.getData(0, "Floor").toString() + "-й этаж.";

		if (order1.getData(0, "Lift") != null) {
			if ((Boolean)order1.getData(0, "Lift"))
				fFloor = fFloor + "Лифт есть.";
			else
				fFloor = fFloor + "Лифта нет.";
		}

		if (order1.getData(0, "Sum") != null) 
			fSum = order1.getData(0, "Sum").toString();

		if (order1.getData(0, "Prepay") != null)
			fSum = fSum + " (предоплата " + order1.getData(0, "Prepay").toString() + ")";

		fSum = fSum.trim();

		String report = "";

		if (order1.getData(0, "Date_Shiping_Wanting") != null)
		try {
			fDate_Shiping_Wanting = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Shiping_Wanting"));
		} catch (Exception e) {} 

		if (order1.getData(0, "Date_Shiping_Max") != null)
		try {
			fDate_Shiping_Max = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Shiping_Max"));
		} catch (Exception e) {} 

		if (order1.getData(0, "Number") != null)
			fNumber = order1.getData(0, "Number").toString();

		if (order1.getData(0, "Date_Order_Begin") != null)
		try {
			fDate_Order_Begin = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Begin"));
		} catch (Exception e) {} 

		if (order1.getData(0, "Date_Order_End") != null)
		try {
			fDate_Order_End = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_End"));
		} catch (Exception e) {} 

		if (order1.getData(0, "Customer") != null)
			fCustomer = order1.getData(0, "Customer").toString();

		if (order2.getRowCount() > 0) {
			if (order2.getData(0, "Material") != null)
				fMaterial = order2.getData(0, "Material").toString();

			if (order2.getData(0, "Color") != null)
				fColor = order2.getData(0, "Color").toString();

			if (order2.getData(0, "Edge") != null)
				fEdge = order2.getData(0, "Edge").toString();

			if (order2.getData(0, "Handle") != null)
				fHandle = order2.getData(0, "Handle").toString();

			if (order2.getData(0, "Glass") != null)
				fGlass = order2.getData(0, "Glass").toString();

			if (order2.getData(0, "Mebel_Info") != null)
				fMebel_Info = order2.getData(0, "Mebel_Info").toString();

			if (order2.getData(0, "Pack_Info") != null)
				fPack_Info = order2.getData(0, "Pack_Info").toString();

			if (order2.getData(0, "Client_Info") != null)
				fClient_Info = order2.getData(0, "Client_Info").toString();
		}

		String s;

		for (String line : lines) {
			line = line.replaceAll("%DATE_SHIPING_WANTING%", fDate_Shiping_Wanting);
			line = line.replaceAll("%DATE_SHIPING_MAX%", fDate_Shiping_Max);
			line = line.replaceAll("%NUMBER%", fNumber);
			line = line.replaceAll("%DATE_BEGIN%", fDate_Order_Begin);
			line = line.replaceAll("%DATE_END%", fDate_Order_End);
			line = line.replaceAll("%MATERIAL%", fMaterial);
			line = line.replaceAll("%COLOR%", fColor);
			line = line.replaceAll("%CLIENT%", fCustomer);
			line = line.replaceAll("%CONTACT%", fContact);
			line = line.replaceAll("%EDGE%", fEdge);
			line = line.replaceAll("%HANDLE%", fHandle);
			line = line.replaceAll("%GLASS%", fGlass);
			line = line.replaceAll("%MEBELINFO%", fMebel_Info);
			line = line.replaceAll("%PACKINFO%", fPack_Info);
			line = line.replaceAll("%CLIENTINFO%", fClient_Info);
			line = line.replaceAll("%MANAGER%", fManager);
			line = line.replaceAll("%FLOOR%", fFloor);
			line = line.replaceAll("%SUM%", fSum);
			line = line.replaceAll("%FURNITURE%", fFurniture);

			if (line.indexOf("%M_NUMBER%") >= 0) {
				for (int i = 0; i < order4.getRowCount(); i++) {
					fM_Name = "";
					fM_Square = "";
					fM_Price = "";
					fM_Quantity = "";
					fM_Sum = "";

					if (order4.getData(i, "Name") != null)
						fM_Name = order4.getData(i, "Name").toString();

					if (order4.getData(i, "Square") != null)
						fM_Square = order4.getData(i, "Square").toString();

					if (order4.getData(i, "Price") != null)
						fM_Price = order4.getData(i, "Price").toString();

					if (order4.getData(i, "Quantity") != null)
						fM_Quantity = order4.getData(i, "Quantity").toString();

					if (order4.getData(i, "Summ") != null)
						fM_Sum = order4.getData(i, "Summ").toString();

					tmpStr = line.replaceAll("%M_NUMBER%", i + 1 + "");
					tmpStr = tmpStr.replaceAll("%M_QUANTITY%", fM_Quantity);
					tmpStr = tmpStr.replaceAll("%M_NAME%", fM_Name);
					tmpStr = tmpStr.replaceAll("%M_PRICE%", fM_Price);
					tmpStr = tmpStr.replaceAll("%M_SUM%", fM_Sum);
					tmpStr = tmpStr.replaceAll("%M_SQUARE%", fM_Square);
					report = report + tmpStr;
				}
				line = "";
			} 

			if (line.indexOf("%MA_NUMBER%") >= 0) {
				for (int i = 0; i < order5.getRowCount(); i++) {
					fM_Name = "";
					fM_Quantity = "";
					fManagerAlt = "";

					if (order5.getData(i, "Name") != null)
						fM_Name = order5.getData(i, "Name").toString();

					if (order5.getData(i, "Quantity") != null)
						fM_Quantity = order5.getData(i, "Quantity").toString();

					if (order5.getData(i, "mFamily") != null)
						fManagerAlt = order5.getData(i, "mFamily").toString();

					if (order5.getData(i, "mName") != null)
						fManagerAlt = fManagerAlt + " " + order5.getData(i, "mName").toString();

					if (order5.getData(i, "mPhone") != null)
						fManagerAlt = fManagerAlt + " тел.:" + order5.getData(i, "mPhone").toString();

					fManagerAlt = fManagerAlt.trim();

					tmpStr = line.replaceAll("%MA_NUMBER%", i + 1 + "");
					tmpStr = tmpStr.replaceAll("%MA_QUANTITY%", fM_Quantity);
					tmpStr = tmpStr.replaceAll("%MA_NAME%", fM_Name);
					tmpStr = tmpStr.replaceAll("%MA_MANAGER%", fManagerAlt);
					report = report + tmpStr;
				}
				line = "";
			}
			report = report + line;
		}
		new TaoPrintToBrowser(report);
	}
}
