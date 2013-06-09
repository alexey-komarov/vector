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

public class PnlMebel extends JPanel {
	private TaoTable tbl = new TaoTable();
	private String tblName = TaoGlobal.database.order.getTempTableName();
	private String SQL_temp_table = 
		"CREATE TEMPORARY TABLE `" + tblName + "` ( " +
			"`ID_Order_Mebel` int(11) NOT NULL AUTO_INCREMENT, " +
				"`ID_Order` int(11) DEFAULT NULL, " +
				"`ID_Mebel` int(11) DEFAULT NULL, " +
				"`Quantity` int(11) DEFAULT NULL, " +
				"`Square` float(9,3) DEFAULT NULL, " +
				"`Price` float(9,3) DEFAULT NULL, " +
				"`ID_Employee` int(11) DEFAULT NULL, " +
				"PRIMARY KEY (`ID_Order_Mebel`), " +
				"UNIQUE KEY `ID_Order_Mebel` (`ID_Order_Mebel`) " +
			") ENGINE=InnoDB DEFAULT CHARSET=cp1251; ";

	private String SQL_drop = "DROP TABLE `" + tblName + "`";

	private String SQL_load = "insert into `" + tblName + "`" +
		"select ID_Order_Mebel, ID_Order, ID_Mebel, Quantity, Square, Price, ID_Employee " +
		"from order_mebel"; 

	private String SQL_Select_Base = "SELECT m.`Art`, m.`Name`, " +
		"concat(e.Family, \" \", e.Name, \" тел.:\", e.Phone) as Manager, " +
		"om.*, om.`Price`*om.`Quantity` as psum " +
		"from `" + tblName + "` as om " +
		"inner join mebel as m on m.`ID_Mebel` = om.`ID_Mebel`" + 
		"left join employers as e on e.`ID_Employee` = om.`ID_Employee`";

	private String SQL_Select = "";

	private boolean alt = false;

	private AcAdd acAdd = new AcAdd();
	private AcEdit acEdit = new AcEdit();
	private AcDelete acDelete = new AcDelete();

	private JButton btnAdd = new JButton(acAdd);
	private JButton btnEdit = new JButton(acEdit);
	private JButton btnDelete = new JButton(acDelete);

	private JToolBar toolBar = new JToolBar();

	public PnlMebel(boolean aAlt) {
		alt = aAlt;
		this.setLayout(new BorderLayout());

		try {
			TaoGlobal.database.order.execQuery(SQL_temp_table);
		} catch (Exception e) {
			Dlg.error("Ошибка создания временной таблицы " + SQL_temp_table, e);
		}

		if (aAlt) {
			tbl.setScheme("Order_Mebel_Alt");
			SQL_Select= SQL_Select_Base + " WHERE e.ID_Employee is not null";
		} else {
			tbl.setScheme("Order_Mebel");
			SQL_Select= SQL_Select_Base + " WHERE e.ID_Employee is null";
		}

		try {
			tbl.refresh(SQL_Select);
		} catch (Exception e) {
			Dlg.error("Ошибка чтения списка изделий", e);
		}

		tbl.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tbl.setDefaultRenderer(String.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Float.class, new TaoRowRenderer());
		this.add(tbl.getPanel(), BorderLayout.CENTER);

		toolBar.add(btnAdd);
		toolBar.add(btnEdit);
		toolBar.add(btnDelete);
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.NORTH);
	}

	public String getTableName() {
		return tblName;
	}

	public void load(int aID_Order) throws Exception {
		TaoGlobal.database.query.execQuery(SQL_load + " WHERE ID_Order=" + aID_Order);
		tbl.refresh();
	}

	public TaoDataModel getTableModel() {
		return (TaoDataModel)tbl.getModel();
	}

	public void dispose() {
		try {
			TaoGlobal.database.order.execQuery(SQL_drop);
		} catch (Exception e) {
			System.out.println("drop error");
		}
	}

	public void initHotKeys() {
		InputMap iMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insert");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		tbl.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		ActionMap aMap = this.getRootPane().getActionMap();
		ActionMap tMap = tbl.getActionMap();

		aMap.put("insert", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acAdd.actionPerformed(e);
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

		tbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					acEdit.actionPerformed(null);
				}
			}
		});
	}

	class AcAdd extends AbstractAction {
		AcAdd() {
			putValue(Action.NAME, "Добавить");
			putValue(Action.SHORT_DESCRIPTION, "Добавить изделие в заказ");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_add);
		}
 
		public void actionPerformed(ActionEvent event) {
			DlgMebel dlg = new DlgMebel(alt);
			dlg.setVisible(true);

			if (dlg.result) {
				Properties p = new Properties();
				p.setProperty("ID_Mebel", ""+dlg.getMebel_ID());
				p.setProperty("Quantity", ""+dlg.getQuantity());
				p.setProperty("Square", ""+dlg.getSquare());
				p.setProperty("Price", ""+dlg.getPrice());

				if (alt)
					p.setProperty("ID_Employee", "" + dlg.getEmployee_ID());

				try {
					TaoGlobal.database.query.insert(tblName, p);
					int index = tbl.getSelectedRow();
					tbl.refresh();
					tbl.changeSelection(index, 0, false, false);
				} catch(Exception e) {
					Dlg.error("Ошибка добавления позиции в заказ", e);
				}
			}
		}
	}

	class AcEdit extends AbstractAction {
		AcEdit() {
			putValue(Action.NAME, "Редактировать");
			putValue(Action.SHORT_DESCRIPTION, "Редактировать позицию");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_edit );
		}

		public void actionPerformed(ActionEvent event) {
			if (!tbl.isSelected()) {
				Dlg.error("Не выбрана позиция для редактирования!");
				return;
			}

			DlgMebel dlg = new DlgMebel(alt);

			try {
				dlg.setMebel_ID(Integer.parseInt(tbl.getSelected("ID_Mebel")),  tbl.getSelected("Name"));
			} catch (Exception e) {}

			try {
				dlg.setSquare(Float.parseFloat(tbl.getSelected("Square")));
			} catch (Exception e) {}

			try {
				dlg.setQuantity(Integer.parseInt(tbl.getSelected("Quantity")));
			} catch (Exception e) {}

			try {
				dlg.setPrice(Float.parseFloat(tbl.getSelected("Price")));
			} catch (Exception e) {}

			if (alt) try {
				dlg.setEmployee_ID(Integer.parseInt(tbl.getSelected("ID_Employee")), tbl.getSelected("Manager"));
			} catch (Exception e) {}

			dlg.setVisible(true);

			if (dlg.result) {
				int index = tbl.getSelectedRow();
				Properties p = new Properties();
				p.setProperty("ID_Mebel", ""+dlg.getMebel_ID());
				p.setProperty("Quantity", ""+dlg.getQuantity());
				p.setProperty("Square", ""+dlg.getSquare());
				p.setProperty("Price", ""+dlg.getPrice());

				if (alt)
					p.setProperty("ID_Employee", "" + dlg.getEmployee_ID());

				String expression = "ID_Order_Mebel = " + Integer.parseInt(tbl.getSelected("ID_Order_Mebel"));

				try {
					TaoGlobal.database.query.update(tblName, p, expression);
				} catch(Exception e) {
					Dlg.error("Ошибка редактирования позиции в заказе", e);
				}

				try {
					tbl.refresh();
				} catch(Exception e) {
					Dlg.error("Ошибка обновления таблицы", e);
				}
				tbl.changeSelection(index, 0, false, false);
			}
		}
	}

	class AcDelete extends AbstractAction {
		AcDelete() {
			putValue(Action.NAME, "Удалить");
			putValue(Action.SHORT_DESCRIPTION, "Удалить позицию");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_delete );
		}

		public void actionPerformed(ActionEvent event) {
			if (!tbl.isSelected()) {
				Dlg.error("Не выбрана позиция для удаления!");
				return;
			}

			try {
				if (Dlg.queryYN(btnDelete.getText() + " позицию \"" + tbl.getSelected("Name") + "\"?")) {
					int index = tbl.getSelectedRow();
					String oldKeyValue = tbl.getSelected("ID_Order_Mebel");
					String expression = "ID_Order_Mebel = " + oldKeyValue;
					TaoGlobal.database.query.delete(tblName, expression);
					tbl.refresh();

					if (index >= tbl.getRowCount())
						index--;

					if (index >= 0)
						tbl.changeSelection(index, 0, false, false);
				}
			} catch (Exception e) {
				Dlg.error("Ошибка удаления позиции из заказа", e);
			}
		}
	}
}
