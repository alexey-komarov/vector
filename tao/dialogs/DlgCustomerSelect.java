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

public class DlgCustomerSelect extends TaoDialog {
	public boolean result = false;
	private TaoTable tblCustomerGroups = new TaoTable();
	private DlgCustomerSelect dlg;
	private Box bxRecord = Box.createVerticalBox();
	private TaoDataModel dmRec;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Hashtable<Integer, TaoDirectoryPanel> Customers = new Hashtable<Integer, TaoDirectoryPanel>();
	private AcSelect acSelect = new AcSelect();

	public DlgCustomerSelect() throws Exception {
		super(TaoGlobal.frmMain,"Выбор из справочника \"Заказчики\"");
		this.setModal(true);
		dlg = this;

		spMain.setLeftComponent(tblCustomerGroups.getPanel());
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
		tblCustomerGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblCustomerGroups.setScheme("Customer_Groups");
		tblCustomerGroups.refresh("SELECT * FROM Customer_groups WHERE Anul = 0");

		if (TaoGlobal.ID_Customer_Group_Last != -1)
			tblCustomerGroups.locateRecord(0, TaoGlobal.ID_Customer_Group_Last + "");

		tblCustomerGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblCustomerGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblCustomerGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblCustomerGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblCustomerGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblCustomerGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());

		onDirChanged(null);

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getSelect(String aFieldName) {
		int ID_Customer = 0;

		Object obj = tblCustomerGroups.getSelected("ID_Customer_Group");

		try {
			ID_Customer = new Integer(obj.toString());
			return Customers.get(ID_Customer).tblDirectory.getSelected(aFieldName);
		} catch (Exception ex) {
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public String getSelect(int aNumber) {
		int ID_Customer = 0;
		Object obj = tblCustomerGroups.getSelected("ID_Customer_Group");

		try {
			ID_Customer = new Integer(obj.toString());
			return Customers.get(ID_Customer).tblDirectory.getSelected(aNumber);
		} catch (Exception ex) {
			Dlg.error("Ошибка выбора изделия", ex);
			return "";
		}
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblCustomerGroups.getSelected("ID_Customer_Group") != null) {
			int ID_Customer_Group = 0;

			Object obj = tblCustomerGroups.getSelected("ID_Customer_Group");

			try {
				ID_Customer_Group = new Integer(obj.toString());
			} catch (Exception ex) {
				return;
			}

			try {
				if (Customers.get(ID_Customer_Group) == null) {
					Customers.put(ID_Customer_Group, new TaoDirectoryPanel(5, "ID_Customer_Group = " + ID_Customer_Group, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Customer_Group", ""+ID_Customer_Group);
					Customers.get(ID_Customer_Group).addFields(pu);
					Customers.get(ID_Customer_Group).setKeyNumber(1);
					Customers.get(ID_Customer_Group).addKey(0);
					Customers.get(ID_Customer_Group).addKey(1);
				}
				spMain.setRightComponent(Customers.get(ID_Customer_Group));
				Customers.get(ID_Customer_Group).initHotKeys(acSelect);
				Customers.get(ID_Customer_Group).tblDirectory.applyScheme(spMain.getSize().width);
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
			int ID_Customer = 0;

			Object obj = tblCustomerGroups.getSelected("ID_Customer_Group");

			try {
				ID_Customer = new Integer(obj.toString());
				TaoGlobal.ID_Customer_Group_Last = ID_Customer;

				if (Customers.get(ID_Customer).tblDirectory.isSelected())
					result = true;

				dlg.setVisible(false);
			} catch (Exception ex) {
				Dlg.error("Ошибка выбора изделия", ex);
			}
		}
	}
}
