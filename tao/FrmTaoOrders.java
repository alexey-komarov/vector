/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.event.*;
import java.awt.event.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;


import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.database.TaoDataModel;

import tao.visual.*;
import tao.database.TaoStoredProc;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;
import java.util.Enumeration;
import tao.parameters.TaoParameters;
import tao.nottao.PopupMenuLayout;
import tao.dialogs.DlgPeriod;

import tao.utils.TaoPeriod;
import tao.order.FrmOrder;

import tao.order.report.*;

public class FrmTaoOrders extends FrmTaoChild {
	private TaoPeriod period = new TaoPeriod();
	private TaoTable tblOrders = new TaoTable();
	private JPanel pnlMain = new JPanel();
	private ResultSet rsFGroups;
	private FrmTaoOrders frm;
	private Hashtable<Integer, TaoDirectoryPanel> Furnitures = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoOrdersListener listener = new FrmTaoOrdersListener();

	private JButton btnAddStandart = new JButton();
	private JButton btnAddForHome = new JButton();
	private JButton btnAddKitchen = new JButton();
	private JButton btnEdit = new JButton();
	private JButton btnDelete = new JButton();
	private JButton btnUpdate = new JButton();
	private JButton btnPeriod = new JButton();
	private JButton btnPrint = new JButton();
	private JButton btnPrintMKZ = new JButton();
	private JToolBar bar = new JToolBar();
	private TaoParameters params;

	private AcAddStandart acAddStandart = new AcAddStandart();
	private AcAddForHome acAddForHome = new AcAddForHome();
	private AcAddKitchen acAddKitchen = new AcAddKitchen();

	private AcPrint acPrint = new AcPrint();
	private AcPrintMKZ acPrintMKZ = new AcPrintMKZ();
	private AcEdit acEdit = new AcEdit();
	private AcPeriod acPeriod;
	private AcDelete acDelete = new AcDelete();
	private AcUpdate acUpdate = new AcUpdate();
	private AcRequery acRequery = new AcRequery();
	private TaoDatePicker dpDateFrom = new TaoDatePicker();
	private TaoDatePicker dpDateTo = new TaoDatePicker();


	public FrmTaoOrders() throws Exception {
		super("Заказы", Nuvola.apps.s_orders);

		period.setDate1(munus3month(period.getDate2()));
		params = new TaoParameters("Заказы");
		acPeriod = new AcPeriod();

		frm = this;
		pnlMain.setLayout(new BorderLayout());
		this.getContentPane().add(pnlMain);

		tblOrders.setScheme("Orders");
		refresh();
		createBar();
		pnlMain.add(tblOrders.getPanel(), BorderLayout.CENTER);
		pnlMain.add(bar, BorderLayout.NORTH);
		tblOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblOrders.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblOrders.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblOrders.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblOrders.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblOrders.setDefaultRenderer(Float.class, new TaoRowRenderer());
		tblOrders.setDefaultRenderer(Date.class, new TaoRowRenderer());

		((TaoDataModel)tblOrders.getModel()).flg_orders = true;

		this.addInternalFrameListener(listener);

		initHotKeys();
	}

	public void refresh() {
		try {
			java.sql.Date sdt1 = new java.sql.Date(period.getDate1().getTime());
			java.sql.Date sdt2 = new java.sql.Date(period.getDate2().getTime());
			tblOrders.refresh("SELECT o.*, c.FullName AS FullCustomerName FROM Orders o "+
			                  "INNER JOIN Customers AS c ON c.ID_Customer =  o.ID_Customer " +
			                  "WHERE Date_Order_Create >= \"" + sdt1 + 
			                  "\" AND Date_Order_Create <=\"" + sdt2 +"\" ORDER BY Number DESC");
		} catch (Exception e) {
			Dlg.error("Ошибка получения данных из таблицы заказов", e);
		}
	}

	public void initHotKeys() {
		InputMap iMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insert");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		tblOrders.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		ActionMap aMap = this.getRootPane().getActionMap();
		ActionMap tMap = tblOrders.getActionMap();


		aMap.put("insert", new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
				acAddStandart.actionPerformed(e);
			}
		});

		aMap.put("delete", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acDelete.actionPerformed(e);
			}
		});

		tMap.put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acEdit.actionPerformed(e);
			}
		});

		tblOrders.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					acEdit.actionPerformed(null);
				}
			}
		}); 
	}

	public Date munus3month(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		month = month - 3;
		if (month <= 0) {
			year--;
			month = 12 + month;
		}
		cal.set(year, month, day, 0, 0, 0);
		return cal.getTime();
	}

	private void createBar() {
		Box bxDateFilter = Box.createHorizontalBox();
		bar.setLayout(new PopupMenuLayout());

		if (params.getBoolean("CanAdd")) {
			btnAddStandart.setAction(acAddStandart);
			btnAddForHome.setAction(acAddForHome);
			btnAddKitchen.setAction(acAddKitchen);
		}

		if (params.getBoolean("CanEdit")) 
			btnEdit.setAction(acEdit);

		if (params.getBoolean("CanDelete")) 
			btnDelete.setAction(acDelete);

		btnUpdate.setAction(acUpdate);
		btnPeriod.setAction(acPeriod);

		if (params.getBoolean("CanPrint")) {
			btnPrint.setAction(acPrint);
			btnPrintMKZ.setAction(acPrintMKZ);
		}

		btnAddStandart.setMargin(new Insets(0, 0, 1, 1));
		btnAddForHome.setMargin(new Insets(0, 0, 1, 1));
		btnAddKitchen.setMargin(new Insets(0, 0, 1, 1));
		btnEdit.setMargin(new Insets(0, 0, 1, 1));
		btnDelete.setMargin(new Insets(0, 0, 1, 1));
		btnPrint.setMargin(new Insets(0, 0, 1, 1));
		btnPrintMKZ.setMargin(new Insets(0, 0, 1, 1));
		btnUpdate.setMargin(new Insets(0, 0, 1, 1));
		btnPeriod.setMargin(new Insets(0, 0, 1, 1));

		bar.add(btnPrint);
		bar.add(btnPrintMKZ);
		bar.add(btnPeriod);

		if (params.getBoolean("CanAdd")) {
			bar.add(btnAddStandart);
			bar.add(btnAddForHome);
			bar.add(btnAddKitchen);
		}

		if (params.getBoolean("CanEdit"))
			bar.add(btnEdit);

		if (params.getBoolean("CanDelete"))
			bar.add(btnDelete);

		bar.add(btnUpdate);
		bar.add(bxDateFilter);
		bar.setFloatable(false);
	}

	public void onDirChanged(ListSelectionEvent e) {
	}

	class FrmTaoOrdersListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
		}
	}

	class AcAddStandart extends AbstractAction {
		AcAddStandart() {
			putValue(Action.NAME, "Офис");
			putValue(Action.SHORT_DESCRIPTION, "Новый заказ \"Офис\"");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_sorder);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanAdd")))
				return;
			try {
				FrmTaoChild frmOrder = new FrmOrder("Новый заказ [Офис]", 1, acUpdate);
				TaoGlobal.rootPane.add(frmOrder);
				frmOrder.setVisible(true);
			} catch(Exception e) {
				Dlg.error("Ошибка открытия формы заказа", e);
			}
		}
	}

	class AcAddForHome extends AbstractAction {
		AcAddForHome() {
			putValue(Action.NAME, "Для дома");
			putValue(Action.SHORT_DESCRIPTION, "Новый заказ \"Для дома\" ");
			putValue(Action.SMALL_ICON,Nuvola.apps.s_home);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanAdd")))
				return;

			try {
				FrmTaoChild frmOrder = new FrmOrder("Новый заказ [Для дома]", 2, acUpdate);
				TaoGlobal.rootPane.add(frmOrder);
				frmOrder.setVisible(true);
			} catch(Exception e) {
				Dlg.error("Ошибка открытия формы заказа", e);
			}
		}
	}

	class AcAddKitchen extends AbstractAction {
		AcAddKitchen() {
			putValue(Action.NAME, "Кухня");
			putValue(Action.SHORT_DESCRIPTION, "Новый заказ \"Кухня\" (INS)");
			putValue(Action.SMALL_ICON,Nuvola.apps.s_kitchen);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanAdd")))
				return;

			try {
				FrmTaoChild frmOrder = new FrmOrder("Новый заказ [Кухня]", 3, acUpdate);
				TaoGlobal.rootPane.add(frmOrder);
				frmOrder.setVisible(true);
			} catch(Exception e) {
				Dlg.error("Ошибка открытия формы заказа", e);
			}
		}
	}

	class AcPrint extends AbstractAction {
		AcPrint() {
			putValue(Action.NAME, "Печать");
			putValue(Action.SHORT_DESCRIPTION, "Распечатать заказа");
			putValue(Action.SMALL_ICON,Nuvola.devices.s_printer);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanPrint")))
				return;

			if (!tblOrders.isSelected()) {
				Dlg.error("Не выбран заказ для печати!");
				return;
			}

			try {
				int ID_Order= Integer.parseInt(tblOrders.getSelected("ID_Order"));
				int ID_Order_Type= Integer.parseInt(tblOrders.getSelected("ID_Order_Type"));
				if (ID_Order_Type == 1)
					new StandardReport(ID_Order);

					if (ID_Order_Type == 2)
						new HomeReport(ID_Order);

					if (ID_Order_Type == 3)
						new KitchenReport(ID_Order);
			} catch(Exception e) {
				Dlg.error("Ошибка открытия заказа", e);
			}
		}
	}

	class AcPrintMKZ extends AbstractAction {
		AcPrintMKZ() {
			putValue(Action.NAME, "МКЗ");
			putValue(Action.SHORT_DESCRIPTION, "Распечатать МКЗ");
			putValue(Action.SMALL_ICON,Nuvola.devices.s_printer);
		}
 
		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanPrint")))
				return;

			if (!tblOrders.isSelected()) {
				Dlg.error("Не выбран заказ для печати!");
				return;
			}

			try {
				int ID_Order= Integer.parseInt(tblOrders.getSelected("ID_Order"));
				int ID_Order_Type= Integer.parseInt(tblOrders.getSelected("ID_Order_Type"));
				new MKZReport(ID_Order_Type, ID_Order);
			} catch(Exception e) {
				Dlg.error("Ошибка открытия заказа", e);
			}
		}
	}

	class AcPeriod extends AbstractAction {
		AcPeriod() {
			putValue(Action.NAME, period.getHTMLPeriod());
			putValue(Action.SHORT_DESCRIPTION, "Выбор периода");
			putValue(Action.SMALL_ICON,Nuvola.apps.s_calendar);
		}
 
		public void actionPerformed(ActionEvent event) {
			DlgPeriod dlg = new DlgPeriod(period);
			dlg.setVisible(true);
			if (dlg.result != null) {
				period = dlg.result;
				btnPeriod.setText(period.getHTMLPeriod());
				frm.refresh();
			}
			dlg.dispose();
		}
	}

	class AcEdit extends AbstractAction {
		AcEdit() {
			putValue(Action.NAME, "Редактировать");
			putValue(Action.SHORT_DESCRIPTION, "Редактировать элемент справочника (ENTER)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_edit);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanEdit")))
				return;

			if ( (tblOrders.getSelected("ID_Status").equals("4")) &&
				(!(params.getBoolean("CanChangeClosed"))) )
					return;

			if (!tblOrders.isSelected()) {
				Dlg.error("Не выбран заказ для редактирования!");
				return;
			}

			try {
				if (tblOrders.getSelected("ID_Order_Type").equals("1")) {
					FrmOrder frmOrder = new FrmOrder("Редактирование заказа", 1, acUpdate);
					frmOrder.load(Integer.parseInt(tblOrders.getSelected("ID_Order")));
					TaoGlobal.rootPane.add(frmOrder);
					frmOrder.setVisible(true);
					return;
				}
				if (tblOrders.getSelected("ID_Order_Type").equals("2")) {
					FrmOrder frmOrder = new FrmOrder("Редактирование заказа", 2, acUpdate);
					frmOrder.load(Integer.parseInt(tblOrders.getSelected("ID_Order")));
					TaoGlobal.rootPane.add(frmOrder);
					frmOrder.setVisible(true);
					return;
				}
				if (tblOrders.getSelected("ID_Order_Type").equals("3")) {
					FrmOrder frmOrder = new FrmOrder("Редактирование заказа", 3, acUpdate);
					frmOrder.load(Integer.parseInt(tblOrders.getSelected("ID_Order")));
					TaoGlobal.rootPane.add(frmOrder);
					frmOrder.setVisible(true);
					return;
				}
			} catch(Exception e) {
				Dlg.error("Ошибка открытия формы заказа", e);
			}
		}
	}

	class AcDelete extends AbstractAction {
		AcDelete() {
			putValue(Action.NAME, "Удалить");
			putValue(Action.SHORT_DESCRIPTION, "Удалить элемент справочника (DELETE)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_delete);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanDelete")))
				return;

			if ( (tblOrders.getSelected("ID_Status").equals("4")) &&
				(!(params.getBoolean("CanChangeClosed"))) )
					return;

			if (!tblOrders.isSelected()) {
				Dlg.error("Не выбран заказ для удаления!");
				return;
			}

			try {
				if (Dlg.queryYN("Удалить заказ \"" +tblOrders.getSelected("Number") + "\"?")) {
					int index = tblOrders.getSelectedRow(); 
					String oldKeyValue = tblOrders.getSelected("ID_Order");
					String expression = "ID_Order= " + oldKeyValue;
					TaoGlobal.database.query.delete("orders", expression);
					tblOrders.refresh();
					if (index >= tblOrders.getRowCount())
						index--;
					if (index >= 0)
						tblOrders.changeSelection(index, 0, false, false);
				}
			} catch (Exception e) {
				Dlg.error("Ошибка удаления заказа", e);
			} 
		}
	}

	public class AcUpdate extends AbstractAction {
		AcUpdate() {
			putValue(Action.NAME, "Обновить");
			putValue(Action.SHORT_DESCRIPTION, "Обновить таблицу заказов");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_reload);
		}
 
		public void actionPerformed(ActionEvent event) {
			TaoDataModel dm = (TaoDataModel)tblOrders.getModel();
			String oldKeyValue = null;

			if (tblOrders.isSelected())
				oldKeyValue = tblOrders.getSelected(0);

			acRequery.actionPerformed(event);

			if (oldKeyValue != null)
				tblOrders.locateRecord(0, oldKeyValue);
		}
	}

	class AcRequery extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			try {
				tblOrders.refresh();
			} catch (Exception e) {
				Dlg.error("Ошибка обновления таблицы", e);
			}
			tblOrders.revalidate();
			tblOrders.repaint();
			tblOrders.scrTable.revalidate();
			tblOrders.scrTable.repaint();
		}
	}
}
