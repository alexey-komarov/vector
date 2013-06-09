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

public class KitchenReport {
	public KitchenReport(int aID_Order) {
		FileArrayProvider fap = new FileArrayProvider();

		String[] lines;

		try {
			lines = fap.readLines("reports/kitchen_order.html");
		} catch (Exception e) {
			Dlg.error("Ошибка чтения файла \"reports/kitchen_order.html\"");
			return;
		}

		TaoDataModel order1 = new TaoDataModel();
		TaoDataModel order2 = new TaoDataModel();
		TaoDataModel order3 = new TaoDataModel();
		TaoDataModel order4 = new TaoDataModel();
		TaoDataModel order5 = new TaoDataModel();

		try {
			order1.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vorders WHERE ID_Order = '" + aID_Order + "'"));
			order2.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vkitchen_orders WHERE ID_Order = '" + aID_Order + "'"));

			order3.refresh(TaoGlobal.database.query.getNewResultSet("SELECT f.`Art`, f.`Name`, " +
				"  ofu.*, u.Name as Unit_Name " +
				"from order_furniture as ofu " +
				"inner join furniture as f on f.`ID_Furniture` = ofu.`ID_Furniture`" + 
				"inner join units as u on u.`ID_Unit` = f.`ID_Unit` WHERE ID_Order=" + aID_Order ));

			order4.refresh(TaoGlobal.database.query.getNewResultSet("SELECT f.`Art`, f.`Name`, " +
				"  ofu.*, u.Name as Unit_Name " +
				"from order_Sanitary as ofu " +
				"inner join furniture as f on f.`ID_Furniture` = ofu.`ID_Furniture`" + 
				"inner join units as u on u.`ID_Unit` = f.`ID_Unit` WHERE ID_Order=" + aID_Order ));
		} catch (Exception e) {
			Dlg.error("Ошибка открытия заказа", e);
			return;
		}

		String fFurniture = "";
		String fSanitary = "";
		String fModel = "";
		String fMaterial = "";
		String fMaterial_box = "";
		String fMaterial_facade = "";
		String fMaterial_facade_glass = "";
		String fColor = "";
		String fColor_box = "";
		String fColor_facade = "";
		String fTable = "";
		String fColor_Table = "";
		String fWall_Panel = "";
		String fPlinth = "";
		String fSocle = "";
		String fDrying = "";
		String fLighting = "";
		String fBottle_Holder = "";
		String fMetall = "";
		String fTop = "";
		String fHandle = "";
		String fGlass = "";
		String fGuide = "";
		String fNotes = "";
		String fInstallation = "";
		String fCustomer = "";
		String fPhone = "";
		String fAddress = "";
		String fSumm = "";
		String fPrepay = "";

		for (int i = 0; i < order3.getRowCount(); i++) {
			if (order3.getData(i, "Name") != null) {
				if (fFurniture.length() > 0)
					fFurniture = fFurniture + ", ";

				fFurniture = fFurniture + order3.getData(i, "Name").toString() + " " +
					order3.getData(i, "Quantity").toString() + " " +
					order3.getData(i, "Unit_Name").toString();
			}
		}

		for (int i = 0; i < order4.getRowCount(); i++) {
			if (order4.getData(i, "Name") != null) {
				if (fSanitary.length() > 0)
					fSanitary = fSanitary + ", ";

				fSanitary = fSanitary + order4.getData(i, "Name").toString();
			}
		}

		String fManager = "";

		if (order1.getData(0, "mFamily") != null)
			fManager = fManager + order1.getData(0, "mFamily").toString();

		if (order1.getData(0, "mName") != null)
			fManager = fManager + " " + order1.getData(0, "mName").toString();

		if (order1.getData(0, "mPhone") != null)
			fManager = fManager + " тел.:" + order1.getData(0, "mPhone").toString();

		fManager = fManager.trim();

		String fDate_Order_Begin = "";
		String fDate_Order_End = "";

		if (order1.getData(0, "Date_Order_Begin") != null)
		try {
			fDate_Order_Begin = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Begin"));
		} catch (Exception e) {}

		if (order1.getData(0, "Date_Order_End") != null)
		try {
			fDate_Order_End = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_End"));
		} catch (Exception e) {}

		if (order2.getRowCount() > 0) {
			if (order2.getData(0, "Model") != null)
				fModel = order2.getData(0, "Model").toString();

			if (order2.getData(0, "Box_Material") != null)
				fMaterial_box = order2.getData(0, "Box_Material").toString();

			if (order2.getData(0, "Facade_Material") != null)
				fMaterial_facade = order2.getData(0, "Facade_Material").toString();

			if (order2.getData(0, "Facade_Glass_Material") != null)
				fMaterial_facade_glass = order2.getData(0, "Facade_Glass_Material").toString();

			if (order2.getData(0, "Color") != null)
				fColor = order2.getData(0, "Color").toString();

			if (order2.getData(0, "Box_Color") != null)
				fColor_box = order2.getData(0, "Box_Color").toString();

			if (order2.getData(0, "Facade_Color") != null)
				fColor_facade = order2.getData(0, "Facade_Color").toString();

			if (order2.getData(0, "Table") != null)
				fTable = order2.getData(0, "Table").toString();

			if (order2.getData(0, "Table_Color") != null)
				fColor_Table = order2.getData(0, "Table_Color").toString();

			if (order2.getData(0, "Wall_Panel") != null)
				fWall_Panel = order2.getData(0, "Wall_Panel").toString();

			if (order2.getData(0, "Plinth") != null)
				fPlinth = order2.getData(0, "Plinth").toString();

			if (order2.getData(0, "Socle") != null)
				fSocle = order2.getData(0, "Socle").toString();

			if (order2.getData(0, "Drying") != null)
				fDrying = order2.getData(0, "Drying").toString();

			if (order2.getData(0, "Lighting") != null)
				fLighting = order2.getData(0, "Lighting").toString();

			if (order2.getData(0, "Bottle_Holder") != null)
				fBottle_Holder = order2.getData(0, "Bottle_Holder").toString();

			if (order2.getData(0, "Metall") != null)
				fMetall = order2.getData(0, "Metall").toString();

			if (order2.getData(0, "Top") != null)
				fTop = order2.getData(0, "Top").toString();

			if (order2.getData(0, "Handle") != null)
				fHandle = order2.getData(0, "Handle").toString();

			if (order2.getData(0, "Glass") != null)
				fGlass = order2.getData(0, "Glass").toString();

			if (order2.getData(0, "Guide") != null)
				fGuide = order2.getData(0, "Guide").toString();

			if (order2.getData(0, "Notes") != null)
				fNotes = order2.getData(0, "Notes").toString();

			if (order2.getData(0, "Installation") != null)
				fInstallation = order2.getData(0, "Installation").toString();
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

			line = line.replaceAll("%MODEL%", fModel);
			line = line.replaceAll("%MATERIAL_BOX%", fMaterial_box);
			line = line.replaceAll("%MATERIAL_FACADE%", fMaterial_facade);
			line = line.replaceAll("%MATERIAL_FACADE_GLASS%", fMaterial_facade_glass);
			line = line.replaceAll("%COLOR%", fColor);
			line = line.replaceAll("%COLOR_BOX%", fColor_box);
			line = line.replaceAll("%COLOR_FACADE%", fColor_facade);
			line = line.replaceAll("%TABLE%", fTable);
			line = line.replaceAll("%COLOR_TABLE%", fColor_Table);
			line = line.replaceAll("%WALL_PANEL%", fWall_Panel);
			line = line.replaceAll("%PLINTH%", fPlinth);
			line = line.replaceAll("%SOCLE%", fSocle);
			line = line.replaceAll("%DRYING%", fDrying);
			line = line.replaceAll("%LIGHTING%", fLighting);
			line = line.replaceAll("%BOTTLE_HOLDER%", fBottle_Holder);
			line = line.replaceAll("%METALL%", fMetall);
			line = line.replaceAll("%TOP%", fTop);
			line = line.replaceAll("%HANDLE%", fHandle);
			line = line.replaceAll("%GLASS%", fGlass);
			line = line.replaceAll("%GUIDE%", fGuide);
			line = line.replaceAll("%FURNITURE%", fFurniture);
			line = line.replaceAll("%SANITARY%", fSanitary);
			line = line.replaceAll("%NOTES%", fNotes);
			line = line.replaceAll("%INSTALLATION%", fInstallation);
			line = line.replaceAll("%MANAGER%", fManager);
			line = line.replaceAll("%CUSTOMER%", fCustomer);
			line = line.replaceAll("%PHONE%", fPhone);
			line = line.replaceAll("%ADDRESS%", fAddress);
			line = line.replaceAll("%SUMM%", fSumm);
			line = line.replaceAll("%PREPAY%", fPrepay);

			report = report + line;
		}
		new TaoPrintToBrowser(report);
	}
}
