/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.util.Properties;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import tao.dialogs.DlgDirectory;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.database.TaoDataModel;

import tao.visual.TaoTable;
import tao.visual.TaoTableBar;
import tao.visual.TaoDirectoryPanel;
import tao.visual.TaoRowRenderer;
import tao.visual.TaoCheckBoxRenderer;
import tao.database.TaoStoredProc;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;
import java.util.Enumeration;

public class FrmTaoCustomers extends FrmTaoChild {
	private TaoTable tblCGroups = new TaoTable();
	private ResultSet rsCGroups;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private FrmTaoChild frm;
	private Hashtable<Integer, TaoDirectoryPanel> Customers = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoCustomersListener listener = new FrmTaoCustomersListener();
	private AcMoveToGroup acMoveToGroup = new AcMoveToGroup();

	public FrmTaoCustomers(String caption) throws Exception {
		super(caption, Nuvola.apps.s_customers);
		frm = this;
		this.getContentPane().add(spMain);

		spMain.setLeftComponent(tblCGroups.getPanel());

		spMain.setRightComponent(new JPanel());

		tblCGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblCGroups.setScheme("Customer_Groups");
		tblCGroups.refresh("SELECT * FROM customer_groups WHERE Anul = 0");

		tblCGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblCGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblCGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblCGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblCGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblCGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());

		this.addInternalFrameListener(listener);

		onDirChanged(null);
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblCGroups.getSelected("ID_Customer_Group") != null) {
			int ID_Customer_Group = 0;
			Object obj = tblCGroups.getSelected("ID_Customer_Group");

			try {
				ID_Customer_Group = new Integer(obj.toString());
			} catch (Exception ex) {
				return; 
			}

			try {
				if (Customers.get(ID_Customer_Group) == null) {
					Customers.put(ID_Customer_Group, new TaoDirectoryPanel(5, "ID_Customer_Group = " + ID_Customer_Group, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Customer_Group", "" + ID_Customer_Group);
					Customers.get(ID_Customer_Group).addFields(pu);
					Customers.get(ID_Customer_Group).setKeyNumber(1);
					Customers.get(ID_Customer_Group).addKey(0);
					Customers.get(ID_Customer_Group).addKey(1);
					Customers.get(ID_Customer_Group).addToBar(new AcMoveToGroup());
				}
				spMain.setRightComponent(Customers.get(ID_Customer_Group));

				Customers.get(ID_Customer_Group).initHotKeys(null);
				Customers.get(ID_Customer_Group).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника закзачиков", ex);
			}
		}
	}

	class FrmTaoCustomersListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
		}
	}

	class AcMoveToGroup extends AbstractAction {
		AcMoveToGroup() {
			putValue(Action.NAME, "Переместить в группу");
			putValue(Action.SHORT_DESCRIPTION, "Переместить в другую группу");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_movetogroup);
		}

		public void actionPerformed(ActionEvent event) {

			if (tblCGroups.getSelected("ID_Customer_Group") != null) {
				int ID_Customer_Group = 0;
				Object obj = tblCGroups.getSelected("ID_Customer_Group");

				try {
					ID_Customer_Group = new Integer(obj.toString());
				} catch (Exception ex) {
					return; 
				}

				if (!Customers.get(ID_Customer_Group).tblDirectory.isSelected()) {
					Dlg.error("Не выбран элемент справочника!");
					return;
				}

				try {
					DlgDirectory dlg = new DlgDirectory(106);
					dlg.setVisible(true);
					if (dlg.result) {
						try {
							int ID = Integer.parseInt(dlg.getSelect(0));
							Properties p = new Properties();
							p.setProperty("ID_Customer_Group", "" + ID);
							String expression = "ID_Customer = " + Integer.parseInt(Customers.get(ID_Customer_Group).tblDirectory.getSelected("ID_Customer"));
							try {
								TaoGlobal.database.query.update("Customers", p, expression);
							} catch(Exception e) {
								Dlg.error("Ошибка перемещения элемента справочника в группу", e);
							}

							Customers.get(ID_Customer_Group).tblDirectory.refresh();

							if (Customers.get(ID) != null)
								Customers.get(ID).tblDirectory.refresh();
						} catch (Exception e) {
							Dlg.error("Ошибка получения кода выбранной записи", e);
						}
					}
					dlg.dispose();
				} catch (Exception e) {
					Dlg.error("Ошибка открытия справочника \"Группы заказчиков\"", e);
				}
			}
		}
	}
}
