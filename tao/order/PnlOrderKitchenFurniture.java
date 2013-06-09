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

public class PnlOrderKitchenFurniture extends JPanel {
	private FurnitureRecord fTable = new FurnitureRecord();
	private FurnitureRecord fWallPanel = new FurnitureRecord();
	private FurnitureRecord fPlinth = new FurnitureRecord();
	private FurnitureRecord fSocle = new FurnitureRecord();
	private FurnitureRecord fDrying = new FurnitureRecord();
	private FurnitureRecord fLighting = new FurnitureRecord();
	private FurnitureRecord fBottleHolder = new FurnitureRecord();
	private FurnitureRecord fMetall = new FurnitureRecord();
	private FurnitureRecord fTop = new FurnitureRecord();
	private FurnitureRecord fHandle = new FurnitureRecord();
	private FurnitureRecord fGlass = new FurnitureRecord();
	private FurnitureRecord fGuide = new FurnitureRecord();

	private JPanel pTable = new JPanel();
	private JPanel pWallPanel = new JPanel();
	private JPanel pPlinth = new JPanel();
	private JPanel pSocle = new JPanel();
	private JPanel pDrying = new JPanel();
	private JPanel pLighting = new JPanel();
	private JPanel pBottleHolder = new JPanel();
	private JPanel pMetall = new JPanel();
	private JPanel pTop = new JPanel();
	private JPanel pHandle = new JPanel();
	private JPanel pGlass = new JPanel();
	private JPanel pGuide = new JPanel();
	private JPanel pnlLeft = new TaoPanel();
	private JPanel pnlRight = new TaoPanel();
	private JPanel pnlCenter = new TaoPanel();
	private JPanel pnlTop = new JPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lTable = new JLabel(h1 + "Столешница: " + h2, SwingConstants.LEFT);
	private JLabel lWallPanel = new JLabel(h1 + "Стеновая панель: " + h2, SwingConstants.LEFT);
	private JLabel lPlinth = new JLabel(h1 + "Плинтус пристенный: " + h2, SwingConstants.LEFT);
	private JLabel lSocle = new JLabel(h1 + "Цоколь: " + h2, SwingConstants.LEFT);
	private JLabel lDrying = new JLabel(h1 + "Сушка: " + h2);
	private JLabel lBottleHolder = new JLabel(h1 + "Бутылочница: " + h2, SwingConstants.LEFT);
	private JLabel lMetall = new JLabel(h1 + "Комплект металлоизделий: " + h2);
	private JLabel lLighting = new JLabel(h1 + "Подстветка: " + h2);
	private JLabel lTop = new JLabel(h1 + "Крыша: " + h2);
	private JLabel lHandle = new JLabel(h1 + "Ручки: " + h2);
	private JLabel lGlass = new JLabel(h1 + "Стекло: " + h2);
	private JLabel lGuide = new JLabel(h1 + "Направляющие: " + h2);

	private Box boxLeft = Box.createVerticalBox();
	private Box boxCenter = Box.createVerticalBox();
	private Box boxRight = Box.createVerticalBox();

	private int ID_Order = -1;

	public PnlOrderKitchenFurniture() {
		this.setLayout(new BorderLayout());
		pTable.setLayout(new BorderLayout());
		pWallPanel.setLayout(new BorderLayout());
		pPlinth.setLayout(new BorderLayout());
		pSocle.setLayout(new BorderLayout());
		pDrying.setLayout(new BorderLayout());
		pLighting.setLayout(new BorderLayout());
		pBottleHolder.setLayout(new BorderLayout());
		pMetall.setLayout(new BorderLayout());
		pTop.setLayout(new BorderLayout());
		pHandle.setLayout(new BorderLayout());
		pGlass.setLayout(new BorderLayout());
		pGuide.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlCenter.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());

		pTable.add(lTable, BorderLayout.NORTH);
		pTable.add(fTable, BorderLayout.CENTER);

		pWallPanel.add(lWallPanel, BorderLayout.NORTH);
		pWallPanel.add(fWallPanel, BorderLayout.CENTER);

		pPlinth.add(lPlinth, BorderLayout.NORTH);
		pPlinth.add(fPlinth, BorderLayout.CENTER);

		pSocle.add(lSocle, BorderLayout.NORTH);
		pSocle.add(fSocle, BorderLayout.CENTER);

		pDrying.add(lDrying, BorderLayout.NORTH);
		pDrying.add(fDrying, BorderLayout.CENTER);

		pBottleHolder.add(lBottleHolder, BorderLayout.NORTH);
		pBottleHolder.add(fBottleHolder, BorderLayout.CENTER);

		pMetall.add(lMetall, BorderLayout.NORTH);
		pMetall.add(fMetall, BorderLayout.CENTER);

		pLighting.add(lLighting, BorderLayout.NORTH);
		pLighting.add(fLighting, BorderLayout.CENTER);

		pTop.add(lTop, BorderLayout.NORTH);
		pTop.add(fTop, BorderLayout.CENTER);

		pGuide.add(lGuide, BorderLayout.NORTH);
		pGuide.add(fGuide, BorderLayout.CENTER);

		pGlass.add(lGlass, BorderLayout.NORTH);
		pGlass.add(fGlass, BorderLayout.CENTER);

		pHandle.add(lHandle, BorderLayout.NORTH);
		pHandle.add(fHandle, BorderLayout.CENTER);

		boxLeft.add(pTable);
		boxLeft.add(Box.createVerticalStrut(10));
		boxLeft.add(pWallPanel);
		boxLeft.add(Box.createVerticalStrut(10));
		boxLeft.add(pPlinth);
		boxLeft.add(Box.createVerticalStrut(10));
		boxLeft.add(pSocle);

		boxCenter.add(pDrying);
		boxCenter.add(Box.createVerticalStrut(10));
		boxCenter.add(pLighting);
		boxCenter.add(Box.createVerticalStrut(10));
		boxCenter.add(pBottleHolder);
		boxCenter.add(Box.createVerticalStrut(10));
		boxCenter.add(pMetall);

		boxRight.add(pTop);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pHandle);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pGlass);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pGuide);

		pnlLeft.add(boxLeft, BorderLayout.CENTER);
		pnlRight.add(boxRight, BorderLayout.CENTER);
		pnlCenter.add(boxCenter, BorderLayout.CENTER);

		pnlTop.add(pnlLeft, BorderLayout.WEST);
		pnlTop.add(pnlCenter, BorderLayout.CENTER);
		pnlTop.add(pnlRight, BorderLayout.EAST);

		pnlLeft.setPreferredSize(new Dimension(280, 0));
		pnlLeft.setMinimumSize(new Dimension(280, 0));
		pnlRight.setPreferredSize(new Dimension(280, 0));
		pnlRight.setMinimumSize(new Dimension(280, 0));

		this.add(pnlTop, BorderLayout.NORTH);
	}

	public void fillFields(Properties p) {
		p.setProperty("ID_Table", "null");
		p.setProperty("ID_Wall_Panel", "null");
		p.setProperty("ID_Plinth", "null");
		p.setProperty("ID_Socle", "null");
		p.setProperty("ID_Drying", "null");
		p.setProperty("ID_Lighting", "null");
		p.setProperty("ID_Bottle_Holder", "null");
		p.setProperty("ID_Metall", "null");
		p.setProperty("ID_Top", "null");
		p.setProperty("ID_Handle", "null");
		p.setProperty("ID_Glass", "null");
		p.setProperty("ID_Guide", "null");

		if (fTable.getID() != -1)
			p.setProperty("ID_Table", "" + fTable.getID());

		if (fWallPanel.getID() != -1)
			p.setProperty("ID_Wall_Panel", "" + fWallPanel.getID());

		if (fPlinth.getID() != -1)
			p.setProperty("ID_Plinth", "" + fPlinth.getID());

		if (fSocle.getID() != -1)
			p.setProperty("ID_Socle", "" + fSocle.getID());

		if (fDrying.getID() != -1)
			p.setProperty("ID_Drying", "" + fDrying.getID());

		if (fLighting.getID() != -1)
			p.setProperty("ID_Lighting", "" + fLighting.getID());

		if (fBottleHolder.getID() != -1)
			p.setProperty("ID_Bottle_Holder", "" + fBottleHolder.getID());

		if (fMetall.getID() != -1)
			p.setProperty("ID_Metall", "" + fMetall.getID());

		if (fTop.getID() != -1)
			p.setProperty("ID_Top", "" + fTop.getID());

		if (fHandle.getID() != -1)
			p.setProperty("ID_Handle", "" + fHandle.getID());

		if (fGlass.getID() != -1)
			p.setProperty("ID_Glass", "" + fGlass.getID());

		if (fGuide.getID() != -1)
			p.setProperty("ID_Guide", "" + fGuide.getID());
	}

	public void load(int aID_Order) throws Exception {
		Object obj;
		TaoDataModel dmOrder = new TaoDataModel();
		ID_Order = aID_Order;
		dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vkitchen_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			obj = dmOrder.getData(0, "ID_Table");
			if (obj instanceof Integer)
				fTable.setValue((Integer)obj, dmOrder.getData(0, "Table").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Wall_Panel");
			if (obj instanceof Integer)
				fWallPanel.setValue((Integer)obj, dmOrder.getData(0, "Wall_Panel").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Plinth");
			if (obj instanceof Integer)
				fPlinth.setValue((Integer)obj, dmOrder.getData(0, "Plinth").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Socle");
			if (obj instanceof Integer)
				fSocle.setValue((Integer)obj, dmOrder.getData(0, "Socle").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Drying");
			if (obj instanceof Integer)
				fDrying.setValue((Integer)obj, dmOrder.getData(0, "Drying").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Lighting");
			if (obj instanceof Integer)
				fLighting.setValue((Integer)obj, dmOrder.getData(0, "Lighting").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Bottle_Holder");
			if (obj instanceof Integer)
				fBottleHolder.setValue((Integer)obj, dmOrder.getData(0, "Bottle_Holder").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Metall");
			if (obj instanceof Integer)
				fMetall.setValue((Integer)obj, dmOrder.getData(0, "Metall").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Top");
			if (obj instanceof Integer)
				fTop.setValue((Integer)obj, dmOrder.getData(0, "Top").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Handle");
			if (obj instanceof Integer)
				fHandle.setValue((Integer)obj, dmOrder.getData(0, "Handle").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Glass");
			if (obj instanceof Integer)
				fGlass.setValue((Integer)obj, dmOrder.getData(0, "Glass").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Guide");
			if (obj instanceof Integer)
				fGuide.setValue((Integer)obj, dmOrder.getData(0, "Guide").toString());
		} catch(Exception e) {};
	}
}
