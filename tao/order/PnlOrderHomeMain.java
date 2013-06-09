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

public class PnlOrderHomeMain extends JPanel {
	private TaoDirectoryRecord fMaterial = new TaoDirectoryRecord(101, "Name");
	private TaoDirectoryRecord fColorBase = new TaoDirectoryRecord(102, "Name");
	private TaoDirectoryRecord fColorBox = new TaoDirectoryRecord(102, "Name");
	private TaoDirectoryRecord fColorFacade = new TaoDirectoryRecord(102, "Name");
	private FurnitureRecord fEdge = new FurnitureRecord();
	private FurnitureRecord fGlass = new FurnitureRecord();
	private FurnitureRecord fHandle = new FurnitureRecord();
	private FurnitureRecord fGuide = new FurnitureRecord();

	private JPanel pMaterial = new JPanel();
	private JPanel pColor = new TaoPanel();
	private JPanel pColorBase = new JPanel();
	private JPanel pColorBox = new JPanel();
	private JPanel pColorFacade = new JPanel();
	private JPanel pEdge = new JPanel();
	private JPanel pHandle = new JPanel();
	private JPanel pGuide = new JPanel();
	private JPanel pGlass = new JPanel();
	private JPanel pnlLeft = new TaoPanel();
	private JPanel pnlRight = new TaoPanel();
	private JPanel pnlTop = new JPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lMaterial = new JLabel(h1 + "Материал: " + h2, SwingConstants.LEFT);
	private JLabel lColorBase = new JLabel(h1 + "Цвет: " + h2, SwingConstants.LEFT);
	private JLabel lColorBox = new JLabel(h1 + "   а) короба: " + h2, SwingConstants.LEFT);
	private JLabel lColorFacade = new JLabel(h1 + "   б) фасады: " + h2, SwingConstants.LEFT);
	private JLabel lHandle = new JLabel(h1 + "Ручка: " + h2);
	private JLabel lEdge = new JLabel(h1 + "Кромка: " + h2, SwingConstants.LEFT);
	private JLabel lGlass = new JLabel(h1 + "Стекло: " + h2);
	private JLabel lGuide = new JLabel(h1 + "Направляющие: " + h2);

	private Box boxLeft = Box.createVerticalBox();
	private Box boxRight = Box.createVerticalBox();

	private int ID_Order = -1;

	public PnlOrderHomeMain() {
		this.setLayout(new BorderLayout());
		pMaterial.setLayout(new BorderLayout());
		pColor.setLayout(new BorderLayout());
		pColorFacade.setLayout(new BorderLayout());
		pColorBox.setLayout(new BorderLayout());
		pColorBase.setLayout(new BorderLayout());
		pEdge.setLayout(new BorderLayout());
		pGlass.setLayout(new BorderLayout());
		pGuide.setLayout(new BorderLayout());
		pHandle.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());

		pMaterial.add(lMaterial, BorderLayout.NORTH);
		pMaterial.add(fMaterial, BorderLayout.CENTER);

		pColorBox.add(lColorBox, BorderLayout.NORTH);
		pColorBox.add(fColorBox, BorderLayout.CENTER);

		pColorFacade.add(lColorFacade, BorderLayout.NORTH);
		pColorFacade.add(fColorFacade, BorderLayout.CENTER);

		pColorBase.add(lColorBase, BorderLayout.NORTH);
		pColorBase.add(fColorBase, BorderLayout.CENTER);

		pEdge.add(lEdge, BorderLayout.NORTH);
		pEdge.add(fEdge, BorderLayout.CENTER);

		pGuide.add(lGuide, BorderLayout.NORTH);
		pGuide.add(fGuide, BorderLayout.CENTER);

		pGlass.add(lGlass, BorderLayout.NORTH);
		pGlass.add(fGlass, BorderLayout.CENTER);

		pHandle.add(lHandle, BorderLayout.NORTH);
		pHandle.add(fHandle, BorderLayout.CENTER);

		pColor.add(pColorBase, BorderLayout.NORTH);
		pColor.add(pColorBox, BorderLayout.CENTER);
		pColor.add(pColorFacade, BorderLayout.SOUTH);

		boxLeft.add(pMaterial);
		boxLeft.add(Box.createVerticalStrut(10));
		boxLeft.add(pColor);

		boxRight.add(pEdge);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pHandle);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pGlass);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pGuide);

		pnlLeft.add(boxLeft, BorderLayout.CENTER);
		pnlRight.add(boxRight, BorderLayout.CENTER);
		pnlLeft.setPreferredSize(new Dimension(400, 0));
		pnlTop.add(pnlLeft, BorderLayout.WEST);
		pnlTop.add(pnlRight, BorderLayout.CENTER);

		this.add(pnlTop, BorderLayout.NORTH);
	}

	public void fillFields(Properties p) {
		p.setProperty("ID_Material", "null");
		p.setProperty("ID_Color_Base", "null");
		p.setProperty("ID_Color_Box", "null");
		p.setProperty("ID_Color_Facade", "null");
		p.setProperty("ID_Edge", "null");
		p.setProperty("ID_Handle", "null");
		p.setProperty("ID_Glass", "null");
		p.setProperty("ID_Guide", "null");

		if (fMaterial.getID() != -1)
			p.setProperty("ID_Material", "" + fMaterial.getID());

		if (fColorBase.getID() != -1)
			p.setProperty("ID_Color_Base", "" + fColorBase.getID());

		if (fColorBox.getID() != -1)
			p.setProperty("ID_Color_Box", "" + fColorBox.getID());

		if (fColorFacade.getID() != -1)
			p.setProperty("ID_Color_Facade", "" + fColorFacade.getID());

		if (fEdge.getID() != -1)
			p.setProperty("ID_Edge", "" + fEdge.getID());

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
		dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vhome_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			obj = dmOrder.getData(0, "ID_Material");
			if (obj instanceof Integer)
				fMaterial.setValue((Integer)obj, dmOrder.getData(0, "Material").toString());
		} catch(Exception e) {};

		try {
		obj = dmOrder.getData(0, "ID_Color_Base");
		if (obj instanceof Integer)
			fColorBase.setValue((Integer)obj, dmOrder.getData(0, "Color_Base").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Color_Box");
			if (obj instanceof Integer)
				fColorBox.setValue((Integer)obj, dmOrder.getData(0, "Color_Box").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Color_Facade");
			if (obj instanceof Integer)
				fColorFacade.setValue((Integer)obj, dmOrder.getData(0, "Color_Facade").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Edge");
			if (obj instanceof Integer)
				fEdge.setValue((Integer)obj, dmOrder.getData(0, "Edge").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Glass");
			if (obj instanceof Integer)
				fGlass.setValue((Integer)obj, dmOrder.getData(0, "Glass").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Handle");
			if (obj instanceof Integer)
				fHandle.setValue((Integer)obj, dmOrder.getData(0, "Handle").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Guide");
			if (obj instanceof Integer)
				fGuide.setValue((Integer)obj, dmOrder.getData(0, "Guide").toString());
		} catch(Exception e) {};
	}
}
