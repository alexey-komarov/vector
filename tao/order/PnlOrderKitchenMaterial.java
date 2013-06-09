/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

import tao.global.TaoGlobal;
import tao.visual.*;
import tao.icons.*;
import tao.dialogs.*;
import tao.database.TaoDataModel;

public class PnlOrderKitchenMaterial extends JPanel {
	private TaoDirectoryRecord fMaterialBox = new TaoDirectoryRecord(101, "Name");
	private TaoDirectoryRecord fMaterialFacade = new TaoDirectoryRecord(101, "Name");
	private TaoDirectoryRecord fMaterialFacadeGlass = new TaoDirectoryRecord(101, "Name");

	private TaoDirectoryRecord fColorBase = new TaoDirectoryRecord(102, "Name");
	private TaoDirectoryRecord fColorBox = new TaoDirectoryRecord(102, "Name");
	private TaoDirectoryRecord fColorFacade = new TaoDirectoryRecord(102, "Name");
	private TaoDirectoryRecord fColorTable = new TaoDirectoryRecord(102, "Name");
	private JTextField fModel = new JTextField();

	private JPanel pMaterial = new TaoPanel();
	private JPanel pModel = new TaoPanel();
	private JPanel pMaterials = new JPanel();
	private JPanel pMaterialBox = new JPanel();
	private JPanel pMaterialFacade = new JPanel();
	private JPanel pMaterialFacadeGlass = new JPanel();

	private JPanel pColor = new TaoPanel();
	private JPanel pColors = new JPanel();
	private JPanel pColorBase = new JPanel();
	private JPanel pColorBox = new JPanel();
	private JPanel pColorFacade = new JPanel();
	private JPanel pColorTable = new JPanel();

	private JPanel pnlLeft = new TaoPanel();
	private JPanel pnlRight = new TaoPanel();
	private JPanel pnlTop = new JPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lModel = new JLabel(h1 + "Модель: " + h2, SwingConstants.LEFT);
	private JLabel lMaterialBox = new JLabel(h1 + "Материал короба: " + h2, SwingConstants.LEFT);
	private JLabel lMaterialFacade = new JLabel(h1 + "Материал Фасад: " + h2, SwingConstants.LEFT);
	private JLabel lMaterialFacadeGlass = new JLabel(h1 + "Фасад стекло: " + h2, SwingConstants.LEFT);

	private JLabel lColorBase = new JLabel(h1 + "Цвет: " + h2, SwingConstants.LEFT);
	private JLabel lColorBox = new JLabel(h1 + "   а) короба: " + h2, SwingConstants.LEFT);
	private JLabel lColorFacade = new JLabel(h1 + "   б) фасады: " + h2, SwingConstants.LEFT);
	private JLabel lColorTable = new JLabel(h1 + "   в) столешница: " + h2, SwingConstants.LEFT);

	private Box boxLeft = Box.createVerticalBox();
	private Box boxRight = Box.createVerticalBox();

	private int ID_Order = -1;

	public PnlOrderKitchenMaterial() {
		this.setLayout(new BorderLayout());
		pMaterial.setLayout(new BorderLayout());
		pMaterials.setLayout(new BorderLayout());
		pMaterialBox.setLayout(new BorderLayout());
		pMaterialFacade.setLayout(new BorderLayout());
		pMaterialFacadeGlass.setLayout(new BorderLayout());

		pColor.setLayout(new BorderLayout());
		pColors.setLayout(new BorderLayout());
		pColorBase.setLayout(new BorderLayout());
		pColorBox.setLayout(new BorderLayout());
		pColorFacade.setLayout(new BorderLayout());
		pColorTable.setLayout(new BorderLayout());
		pModel.setLayout(new BorderLayout());

		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());

		pMaterial.add(pMaterials, BorderLayout.CENTER);

		pMaterials.add(pMaterialBox, BorderLayout.NORTH);
		pMaterials.add(pMaterialFacade, BorderLayout.CENTER);
		pMaterials.add(pMaterialFacadeGlass, BorderLayout.SOUTH);

		pMaterialBox.add(lMaterialBox, BorderLayout.NORTH);
		pMaterialBox.add(fMaterialBox, BorderLayout.CENTER);

		pModel.add(lModel, BorderLayout.NORTH);
		pModel.add(fModel, BorderLayout.CENTER);

		pMaterialFacade.add(lMaterialFacade, BorderLayout.NORTH);
		pMaterialFacade.add(fMaterialFacade, BorderLayout.CENTER);

		pMaterialFacadeGlass.add(lMaterialFacadeGlass, BorderLayout.NORTH);
		pMaterialFacadeGlass.add(fMaterialFacadeGlass, BorderLayout.CENTER);

		pColorBox.add(lColorBox, BorderLayout.NORTH);
		pColorBox.add(fColorBox, BorderLayout.CENTER);

		pColorFacade.add(lColorFacade, BorderLayout.NORTH);
		pColorFacade.add(fColorFacade, BorderLayout.CENTER);

		pColorBase.add(lColorBase, BorderLayout.NORTH);
		pColorBase.add(fColorBase, BorderLayout.CENTER);

		pColorTable.add(lColorTable, BorderLayout.NORTH);
		pColorTable.add(fColorTable, BorderLayout.CENTER);

		pColors.add(pColorBase, BorderLayout.NORTH);
		pColors.add(pColor, BorderLayout.CENTER);

		pColor.add(pColorBox, BorderLayout.NORTH);
		pColor.add(pColorFacade, BorderLayout.CENTER);
		pColor.add(pColorTable, BorderLayout.SOUTH);

		boxLeft.add(pModel);
		boxLeft.add(pMaterial);

		boxRight.add(pColors);
		boxRight.add(new JPanel());

		pnlLeft.add(boxLeft, BorderLayout.CENTER);
		pnlRight.add(boxRight, BorderLayout.CENTER);
		pnlLeft.setPreferredSize(new Dimension(400, 0));
		pnlTop.add(pnlLeft, BorderLayout.WEST);
		pnlTop.add(pnlRight, BorderLayout.CENTER);

		this.add(pnlTop, BorderLayout.NORTH);
	}

	public void fillFields(Properties p) {
		p.setProperty("ID_Box_Material", "null");
		p.setProperty("ID_Facade_Material", "null");
		p.setProperty("ID_Facade_Glass_Material", "null");
		p.setProperty("ID_Color", "null");
		p.setProperty("ID_Box_Color", "null");
		p.setProperty("ID_Facade_Color", "null");
		p.setProperty("ID_Table_Color", "null");

		if (fMaterialBox.getID() != -1)
			p.setProperty("ID_Box_Material", "" + fMaterialBox.getID());

		if (fMaterialFacade.getID() != -1)
			p.setProperty("ID_Facade_Material", "" + fMaterialFacade.getID());

		if (fMaterialFacadeGlass.getID() != -1)
			p.setProperty("ID_Facade_Glass_Material", "" + fMaterialFacadeGlass.getID());

		if (fColorBase.getID() != -1)
			p.setProperty("ID_Color", "" + fColorBase.getID());

		if (fColorBox.getID() != -1)
			p.setProperty("ID_Box_Color", "" + fColorBox.getID());

		if (fColorFacade.getID() != -1)
			p.setProperty("ID_Facade_Color", "" + fColorFacade.getID());

		if (fColorTable.getID() != -1)
			p.setProperty("ID_Table_Color", "" + fColorTable.getID());

		p.setProperty("Model", "\"" + fModel.getText().replaceAll("\"", "\\\\\"") + "\"");
	}

	public void load(int aID_Order) throws Exception {
		Object obj;
		TaoDataModel dmOrder = new TaoDataModel();
		ID_Order = aID_Order;
		dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vkitchen_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			obj = dmOrder.getData(0, "ID_Box_Material");
			if (obj instanceof Integer)
				fMaterialBox.setValue((Integer)obj, dmOrder.getData(0, "Box_Material").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Facade_Material");
			if (obj instanceof Integer)
				fMaterialFacade.setValue((Integer)obj, dmOrder.getData(0, "Facade_Material").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Facade_Glass_Material");
			if (obj instanceof Integer)
				fMaterialFacadeGlass.setValue((Integer)obj, dmOrder.getData(0, "Facade_Glass_Material").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Color");
			if (obj instanceof Integer)
				fColorBase.setValue((Integer)obj, dmOrder.getData(0, "Color").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Box_Color");
			if (obj instanceof Integer)
				fColorBox.setValue((Integer)obj, dmOrder.getData(0, "Box_Color").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Facade_Color");
			if (obj instanceof Integer)
				fColorFacade.setValue((Integer)obj, dmOrder.getData(0, "Facade_Color").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Table_Color");
			if (obj instanceof Integer)
				fColorTable.setValue((Integer)obj, dmOrder.getData(0, "Table_Color").toString());
		} catch(Exception e) {};

		try {
			fModel.setText(dmOrder.getData(0, "Model").toString());
		} catch(Exception e) {};
	}
}
