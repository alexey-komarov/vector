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

public class HomeReport {
	public HomeReport(int aID_Order) {
		FileArrayProvider fap = new FileArrayProvider();

		String[] lines;
		try {
			lines = fap.readLines("reports/home_order.html");
		} catch (Exception e) {
			Dlg.error("Ошибка чтения файла \"reports/home_order.html\"");
			return;
		}

		TaoDataModel order1 = new TaoDataModel();
		TaoDataModel order2 = new TaoDataModel();
		TaoDataModel order3 = new TaoDataModel();
		TaoDataModel order4 = new TaoDataModel();
		TaoDataModel order5 = new TaoDataModel();

		try {
			order1.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vorders WHERE ID_Order = '" + aID_Order + "'"));
			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vhome_orders WHERE ID_Order = '" + aID_Order + "'"));

			order3.refresh(TaoGlobal.database.query.getNewResultSet("SELECT f.`Art`, f.`Name`, " +
				"ofu.*, u.Name as Unit_Name " +
				"from order_furniture as ofu " +
				"inner join furniture as f on f.`ID_Furniture` = ofu.`ID_Furniture`" + 
				"inner join units as u on u.`ID_Unit` = f.`ID_Unit` WHERE ID_Order=" + aID_Order ));

			order4.refresh(TaoGlobal.database.query.getNewResultSet("SELECT m.`Art`, m.`Name`, " +
				"om.*, om.quantity * om.price as Summ "+
				"from order_mebel as om " +
				"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel` " + 
				"WHERE om.ID_Employee IS null AND ID_Order=" + aID_Order ));

			} catch (Exception e) {
				Dlg.error("Ошибка открытия заказа", e);
				return;
			}

			String fFurniture = "";
			String fMaterial = "";
			String fColor = "";
			String fColor_box = "";
			String fColor_facade = "";
			String fHandle = "";
			String fEdge = "";
			String fGlass = "";
			String fGuide = "";
			String fNotes = "";
			String fClient_Info = "";
			String fMebel_Info = "";
			String fPack_Info = "";
			String fCustomer = "";
			String fPhone = "";
			String fAddress = "";
			String fSumm = "";
			String fPrepay = "";

			String fM_Name = "";
			String fM_Square = "";
			String fM_Price = "";
			String fM_Quantity = "";
			String fM_Sum = "";

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
			String tmpStr = "";

			if (order1.getData(0, "mFamily") != null)
				fManager = fManager + order1.getData(0, "mFamily").toString();

			if (order1.getData(0, "mName") != null)
				fManager = fManager + " " + order1.getData(0, "mName").toString();

			if (order1.getData(0, "mPhone") != null)
				fManager = fManager + " тел.:" + order1.getData(0, "mPhone").toString();

			if (order2.getData(0, "Mebel_Info") != null)
				fMebel_Info = order2.getData(0, "Mebel_Info").toString();

			if (order2.getData(0, "Pack_Info") != null)
				fPack_Info = order2.getData(0, "Pack_Info").toString();

			fManager = fManager.trim();

			String fDate_Order_Begin = "";
			String fDate_Order_End = "";

			if (order1.getData(0, "Date_Order_Begin") != null) {
				try {
				  fDate_Order_Begin = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Begin"));
				} catch(Exception e) {}
			}

			if (order1.getData(0, "Date_Order_End") != null) {
				try {
					fDate_Order_End = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_End"));
				} catch(Exception e) {}
			}

			if (order2.getRowCount() > 0) {
				if (order2.getData(0, "Material") != null)
					fMaterial = order2.getData(0, "Material").toString();

				if (order2.getData(0, "Color_Base") != null)
					fColor = order2.getData(0, "Color_Base").toString();

				if (order2.getData(0, "Color_Box") != null)
					fColor_box = order2.getData(0, "Color_Box").toString();

				if (order2.getData(0, "Color_Facade") != null)
					fColor_facade = order2.getData(0, "Color_Facade").toString();

				if (order2.getData(0, "Edge") != null)
					fEdge = order2.getData(0, "Edge").toString();

				if (order2.getData(0, "Handle") != null)
					fHandle = order2.getData(0, "Handle").toString();

				if (order2.getData(0, "Glass") != null)
					fGlass = order2.getData(0, "Glass").toString();

				if (order2.getData(0, "Guide") != null)
					fGuide = order2.getData(0, "Guide").toString();

				if (order2.getData(0, "Client_Info") != null)
					fClient_Info = order2.getData(0, "Client_Info").toString();
			}

			if (order1.getData(0, "Customer") != null)
				fCustomer = order1.getData(0, "Customer").toString();

			if (order1.getData(0, "Address") != null)
				fAddress = order1.getData(0, "Address").toString();

			if (order1.getData(0, "Phone") != null)
				fPhone = order1.getData(0, "Phone").toString();

			if (order1.getData(0, "Sum") != null)
				fSumm = order1.getData(0, "Sum").toString();

			if (order1.getData(0, "Prepay") != null)
				fPrepay = order1.getData(0, "Prepay").toString();

			String report = "";

			for (String line : lines) {
				line = line.replaceAll("%DATE_BEGIN%", fDate_Order_Begin);
				line = line.replaceAll("%DATE_END%", fDate_Order_End);

				line = line.replaceAll("%MATERIAL%", fMaterial);
				line = line.replaceAll("%COLOR%", fColor);
				line = line.replaceAll("%COLOR_BOX%", fColor_box);
				line = line.replaceAll("%COLOR_FACADE%", fColor_facade);
				line = line.replaceAll("%EDGE%", fEdge);
				line = line.replaceAll("%HANDLE%", fHandle);
				line = line.replaceAll("%GLASS%", fGlass);
				line = line.replaceAll("%GUIDE%", fGuide);
				line = line.replaceAll("%FURNITURE%", fFurniture);
				line = line.replaceAll("%CLIENT_INFO%", fClient_Info);
				line = line.replaceAll("%MANAGER%", fManager);
				line = line.replaceAll("%CUSTOMER%", fCustomer);
				line = line.replaceAll("%MEBELINFO%", fMebel_Info);
				line = line.replaceAll("%PACKINFO%", fPack_Info);
				line = line.replaceAll("%PHONE%", fPhone);
				line = line.replaceAll("%ADDRESS%", fAddress);
				line = line.replaceAll("%SUMM%", fSumm);
				line = line.replaceAll("%PREPAY%", fPrepay);

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
			report = report + line;
		}
		new TaoPrintToBrowser(report);
	}
}
