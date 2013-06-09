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

public class DlgMebelSelect extends TaoDialog {
	public boolean result = false;
	private TaoTable tblMebelGroups = new TaoTable();
	private ResultSet rsProfessions;
	private DlgMebelSelect dlg;
	private Box bxRecord = Box.createVerticalBox();
	private TaoDataModel dmRec;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Hashtable<Integer, TaoDirectoryPanel> Mebels = new Hashtable<Integer, TaoDirectoryPanel>();
	private AcSelect acSelect = new AcSelect();

	public DlgMebelSelect() throws Exception {
		super(TaoGlobal.frmMain,"Выбор из справочника \"Мебель\"");	
		this.setModal(true);
		dlg = this;

		spMain.setLeftComponent(tblMebelGroups.getPanel());

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
		tblMebelGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblMebelGroups.setScheme("Mebel_Groups");
		tblMebelGroups.refresh("SELECT * FROM Mebel_groups WHERE Anul = 0");

		tblMebelGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblMebelGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblMebelGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblMebelGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblMebelGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblMebelGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());

		onDirChanged(null);

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getSelect(String aFieldName) {
		int ID_Profession = 0;

		Object obj = tblMebelGroups.getSelected("ID_Mebel_Group");

		try {
			ID_Profession = new Integer(obj.toString());
			return Mebels.get(ID_Profession).tblDirectory.getSelected(aFieldName);
		} catch (Exception ex) 
		{
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public String getSelect(int aNumber) {
		int ID_Profession = 0;

		Object obj = tblMebelGroups.getSelected("ID_Mebel_Group");

		try {
			ID_Profession = new Integer(obj.toString());
			return Mebels.get(ID_Profession).tblDirectory.getSelected(aNumber);
		} catch (Exception ex) 
		{
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblMebelGroups.getSelected("ID_Mebel_Group") != null) {
			int ID_Mebel_Group = 0;

			Object obj = tblMebelGroups.getSelected("ID_Mebel_Group");

			try {
				ID_Mebel_Group = new Integer(obj.toString());
			} catch (Exception ex) {
				return;
			}

			try {
				if (Mebels.get(ID_Mebel_Group) == null) {
					Mebels.put(ID_Mebel_Group, new TaoDirectoryPanel(7, "ID_Mebel_Group = " + ID_Mebel_Group, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Mebel_Group", ""+ID_Mebel_Group);
					Mebels.get(ID_Mebel_Group).addFields(pu);
					Mebels.get(ID_Mebel_Group).setKeyNumber(1);
					Mebels.get(ID_Mebel_Group).addKey(0);
					Mebels.get(ID_Mebel_Group).addKey(1);
				}
				spMain.setRightComponent(Mebels.get(ID_Mebel_Group));
				Mebels.get(ID_Mebel_Group).initHotKeys(acSelect);
				Mebels.get(ID_Mebel_Group).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника мебели", ex);
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
			int ID_Mebel = 0;
			Object obj = tblMebelGroups.getSelected("ID_Mebel_Group");

			try {
				ID_Mebel = new Integer(obj.toString());

				if (Mebels.get(ID_Mebel).tblDirectory.isSelected())
					result = true;

				dlg.setVisible(false);
			} catch (Exception ex) {
				Dlg.error("Ошибка выбора изделия", ex);
			}
		}
	}
}
