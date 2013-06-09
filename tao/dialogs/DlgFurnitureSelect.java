/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.sql.*;
import tao.visual.*;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.database.TaoDataModel;
import tao.parameters.TaoParameters;

public class DlgFurnitureSelect extends TaoDialog {
	public boolean result = false;
	private TaoTable tblFurnitureGroups = new TaoTable();
	private DlgFurnitureSelect dlg;
	private Box bxRecord = Box.createVerticalBox();
	private TaoDataModel dmRec;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Hashtable<Integer, TaoDirectoryPanel> Furnitures = new Hashtable<Integer, TaoDirectoryPanel>();
	private AcSelect acSelect = new AcSelect();

	public DlgFurnitureSelect() throws Exception {
		super(TaoGlobal.frmMain,"Выбор из справочника \"Фурнитура\"");
		this.setModal(true);
		dlg = this;

		spMain.setLeftComponent(tblFurnitureGroups.getPanel());

		spMain.setRightComponent(new JPanel());

		this.getContentPane().add(spMain);

		AcClose acClose = new AcClose();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		JButton btnClose = new JButton(acClose);
		JButton btnSelect = new JButton(acSelect);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnSelect);
		bxButtons.add(btnClose);

		this.add(bxButtons, BorderLayout.SOUTH);

		this.pack();
		this.setPreferredSize(new Dimension(620, this.getSize().height >> 1));
		getRootPane().setDefaultButton(btnSelect);
		tblFurnitureGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblFurnitureGroups.setScheme("Furniture_Groups");
		tblFurnitureGroups.refresh("SELECT * FROM furniture_groups WHERE Anul = 0");

		if (TaoGlobal.ID_Furniture_Group_Last != -1)
			tblFurnitureGroups.locateRecord(0, TaoGlobal.ID_Furniture_Group_Last + "");

		tblFurnitureGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack(); 

		tblFurnitureGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblFurnitureGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblFurnitureGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblFurnitureGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblFurnitureGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());

		onDirChanged(null);

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getSelect(String aFieldName) {
		int ID_Furniture = 0;

		Object obj = tblFurnitureGroups.getSelected("ID_Furniture_Group");

		try {
			ID_Furniture = new Integer(obj.toString());
			return Furnitures.get(ID_Furniture).tblDirectory.getSelected(aFieldName);
		} catch (Exception ex) 
		{
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public String getSelect(int aNumber) {
		int ID_Furniture = 0;

		Object obj = tblFurnitureGroups.getSelected("ID_Furniture_Group");

		try {
			ID_Furniture = new Integer(obj.toString());
			return Furnitures.get(ID_Furniture).tblDirectory.getSelected(aNumber);
		} catch (Exception ex) 
		{
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblFurnitureGroups.getSelected("ID_Furniture_Group") != null) {
			int ID_Furniture_Group = 0;

			Object obj = tblFurnitureGroups.getSelected("ID_Furniture_Group");

			try {
				ID_Furniture_Group = new Integer(obj.toString());
			} catch (Exception ex) {
				return;
			}

			try {
				if (Furnitures.get(ID_Furniture_Group) == null) {
					Furnitures.put(ID_Furniture_Group, new TaoDirectoryPanel(6, "ID_Furniture_Group = " + ID_Furniture_Group, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Furniture_Group", ""+ID_Furniture_Group);
					Furnitures.get(ID_Furniture_Group).addFields(pu);
					Furnitures.get(ID_Furniture_Group).setKeyNumber(1);
					Furnitures.get(ID_Furniture_Group).addKey(0);
					Furnitures.get(ID_Furniture_Group).addKey(1);
				}
				spMain.setRightComponent(Furnitures.get(ID_Furniture_Group));
				Furnitures.get(ID_Furniture_Group).initHotKeys(acSelect);
				Furnitures.get(ID_Furniture_Group).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника фурнитуры", ex);
			}
		}
	}

	class AcClose extends AbstractAction {
		AcClose() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			listener.windowClosing(null);
			dlg.setVisible(false);
		}
	}

	class AcSelect extends AbstractAction {
		AcSelect() {
			putValue(Action.NAME, "Выбрать");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			listener.windowClosing(null);
			int ID_Furniture = 0;
			Object obj = tblFurnitureGroups.getSelected("ID_Furniture_Group");

			try {
				ID_Furniture = new Integer(obj.toString());
				TaoGlobal.ID_Furniture_Group_Last = ID_Furniture;
				if (Furnitures.get(ID_Furniture).tblDirectory.isSelected())
				result = true;
				dlg.setVisible(false);
			} catch (Exception ex) {
				Dlg.error("Ошибка выбора изделия", ex);
			}
		}
	}
}
