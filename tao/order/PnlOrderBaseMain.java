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

public class PnlOrderBaseMain extends JPanel {
	private TaoDirectoryRecord fMaterial = new TaoDirectoryRecord(101, "Name");
	private TaoDirectoryRecord fColor = new TaoDirectoryRecord(102, "Name");
	private FurnitureRecord fEdge = new FurnitureRecord();
	private FurnitureRecord fGlass = new FurnitureRecord();
	private FurnitureRecord fHandle = new FurnitureRecord();

	private JPanel pMaterial = new JPanel();
	private JPanel pColor = new JPanel();
	private JPanel pEdge = new JPanel();
	private JPanel pHandle = new JPanel();
	private JPanel pGlass = new JPanel();
	private JPanel pnlLeft = new TaoPanel();
	private JPanel pnlRight = new TaoPanel();
	private JPanel pnlTop = new JPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lMaterial = new JLabel(h1 + "Материал: " + h2, SwingConstants.LEFT);
	private JLabel lColor = new JLabel(h1 + "Цвет: " + h2, SwingConstants.LEFT);
	private JLabel lHandle = new JLabel(h1 + "Ручка: " + h2);
	private JLabel lEdge = new JLabel(h1 + "Кромка: " + h2, SwingConstants.LEFT);
	private JLabel lGlass = new JLabel(h1 + "Стекло: " + h2);

	private Box boxLeft = Box.createVerticalBox();
	private Box boxRight = Box.createVerticalBox();

	private int ID_Order = -1;

	public PnlOrderBaseMain() {
		this.setLayout(new BorderLayout());
		pMaterial.setLayout(new BorderLayout());
		pColor.setLayout(new BorderLayout());
		pEdge.setLayout(new BorderLayout());
		pGlass.setLayout(new BorderLayout());
		pHandle.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());

		pMaterial.add(lMaterial, BorderLayout.NORTH);
		pMaterial.add(fMaterial, BorderLayout.CENTER);

		pColor.add(lColor, BorderLayout.NORTH);
		pColor.add(fColor, BorderLayout.CENTER);

		pEdge.add(lEdge, BorderLayout.NORTH);
		pEdge.add(fEdge, BorderLayout.CENTER);

		pGlass.add(lGlass, BorderLayout.NORTH);
		pGlass.add(fGlass, BorderLayout.CENTER);

		pHandle.add(lHandle, BorderLayout.NORTH);
		pHandle.add(fHandle, BorderLayout.CENTER);

		boxLeft.add(pMaterial);
		boxLeft.add(Box.createVerticalStrut(10));
		boxLeft.add(pColor);
		boxLeft.add(Box.createVerticalStrut(50));
		boxLeft.add(new JPanel());

		boxRight.add(pEdge);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pHandle);
		boxRight.add(Box.createVerticalStrut(10));
		boxRight.add(pGlass);

		pnlLeft.add(boxLeft, BorderLayout.CENTER);
		pnlRight.add(boxRight, BorderLayout.CENTER);
		pnlLeft.setPreferredSize(new Dimension(400, 0));
		pnlTop.add(pnlLeft, BorderLayout.WEST);
		pnlTop.add(pnlRight, BorderLayout.CENTER);

		this.add(pnlTop, BorderLayout.NORTH);
	}

	public void fillFields(Properties p) {
		p.setProperty("ID_Material", "null");
		p.setProperty("ID_Color", "null");
		p.setProperty("ID_Edge","null");
		p.setProperty("ID_Handle", "null");
		p.setProperty("ID_Glass", "null");

		if (fMaterial.getID() != -1)
			p.setProperty("ID_Material", "" + fMaterial.getID());

		if (fColor.getID() != -1)
			p.setProperty("ID_Color", "" + fColor.getID());

		if (fEdge.getID() != -1)
			p.setProperty("ID_Edge", "" + fEdge.getID());

		if (fHandle.getID() != -1)
			p.setProperty("ID_Handle", "" + fHandle.getID());

		if (fGlass.getID() != -1)
			p.setProperty("ID_Glass", "" + fGlass.getID());
	}

	public void load(int aID_Order) throws Exception {
		Object obj;
		TaoDataModel dmOrder = new TaoDataModel();
		ID_Order = aID_Order;
		dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vstandard_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			obj = dmOrder.getData(0, "ID_Material");
			if (obj instanceof Integer)
				fMaterial.setValue((Integer)obj, dmOrder.getData(0, "Material").toString());
		} catch(Exception e) {};

		try {
			obj = dmOrder.getData(0, "ID_Color");
			if (obj instanceof Integer)
				fColor.setValue((Integer)obj, dmOrder.getData(0, "Color").toString());
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
	}
}
