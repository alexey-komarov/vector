/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.AbstractAction;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.ActionMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import tao.icons.Nuvola;
import tao.visual.TaoTable;
import tao.dialogs.TaoRecordDialog;
import tao.dialogs.DlgStringFilter;
import tao.dialogs.Dlg;
import tao.global.TaoGlobal;
import tao.database.TaoDataModel;
import tao.database.TaoKey;
import tao.nottao.PopupMenuLayout;
import tao.parameters.TaoParameters;

public class TaoTableBar extends JToolBar{
	public JButton btnAdd = new JButton();
	public JButton btnEdit = new JButton();
	public JButton btnDelete = new JButton();
	public JButton btnUpdate = new JButton();

	private AcAdd acAdd = new AcAdd();
	private AcEdit acEdit = new AcEdit();
	private AcDelete acDelete = new AcDelete();
	private AcUpdate acUpdate = new AcUpdate();
	private AcRequery acRequery = new AcRequery();
	public TaoParameters params;
	private Properties default_fields;
	private AbstractAction acAfterInsert = null;
	private AbstractAction acSelect = null;
	private int keyNumber = 0;
	private TaoTable tbl;
	private Vector<Integer> key = null;
	private TaoTableBar bar;

	public TaoTableBar(TaoTable aTbl, TaoParameters aParams) {
		super();
		params = aParams;
		bar = this;
		tbl = aTbl;
		this.setLayout(new PopupMenuLayout());
		btnAdd.setAction(acAdd);
		btnEdit.setAction(acEdit);
		btnDelete.setAction(acDelete);
		btnUpdate.setAction(acUpdate);

		btnAdd.setMargin(new Insets(0, 0, 1, 1));
		btnEdit.setMargin(new Insets(0, 0, 1, 1));
		btnDelete.setMargin(new Insets(0, 0, 1, 1));
		btnUpdate.setMargin(new Insets(0, 0, 1, 1));

		acAdd.setEnabled(false);
		acEdit.setEnabled(false);
		acDelete.setEnabled(false);

		if (params.getBoolean("CanAdd"))
			this.add(btnAdd);

		if (params.getBoolean("CanEdit"))
			this.add(btnEdit);

		if (params.getBoolean("CanDelete"))
			this.add(btnDelete);

		this.add(btnUpdate);
		this.setFloatable(false);

		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				updateControls();
			}
		});
	}

	public void setKeyNumber(int aKeyNumber) {
		keyNumber = aKeyNumber;
	}

	public void addKey(int aKeyNumber) {
		if (key == null)
			key = new Vector<Integer>();
		key.addElement(aKeyNumber);
	}

	public void updateControls() {
		updateControls(tbl);
	}

	public boolean isSelected() {
		return (tbl.getSelectedRow() != -1) && (tbl.getRowCount() > 0);
	}

	public void updateControls(TaoTable tbl) {
		boolean is_selected = isSelected();
		acAdd.setEnabled(true);
		acEdit.setEnabled(is_selected);
		acDelete.setEnabled(is_selected);
	}

	public void setAfterInsert(AbstractAction a) {
		acAfterInsert = a;
	}

	public void addFields(Properties aFields) {
		default_fields = aFields;
	}

	public void initHotKeys(AbstractAction doubleClick) {
		acSelect = doubleClick; 

		if (acSelect == null) 
			acSelect = acEdit;

		InputMap iMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insert");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "f5");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "f7");
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

		aMap.put("f5", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acUpdate.actionPerformed(e);
			}
		});

		aMap.put("f7", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		tMap.put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acSelect.actionPerformed(e);
			}
		});

		tbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					acSelect.actionPerformed(null);
				}
			}
		});
	}

	private String getWhereExpression(int aRow) {
		String expression = "";

		if (key != null) {
			TaoDataModel dm = (TaoDataModel)tbl.getModel();
			for (int i = 0; i < key.size(); i++) {
				if (expression.length() > 0)
					expression = expression + " AND ";

				expression = expression + tbl.getScheme().getName(key.elementAt(i)) + " = " + TaoGlobal.database.sqlValue(dm.getData(aRow, key.elementAt(i)).toString());
			}
		}
		return expression;
	}

	class AcAdd extends AbstractAction {
		AcAdd() {
			putValue(Action.NAME, "Добавить");
			putValue(Action.SHORT_DESCRIPTION, "Новый элемент справочника (INS)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_add);
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanAdd")))
				return;

			TaoDataModel dm = (TaoDataModel)tbl.getModel();

			Properties p = new Properties();
			String key;
			String rvalue = null;

			try {
				TaoRecordDialog dlg = new TaoRecordDialog("Новый элемент справочника", tbl.getScheme(), true);
				dlg.setVisible(true);

				if (dlg.result) {
					for (Enumeration e = dlg.getFields(); e.hasMoreElements(); ) {
						key = e.nextElement().toString();
						p.setProperty(key, dlg.getSQLField(key));
					}

					if (default_fields != null) {
						for (Enumeration e = default_fields.propertyNames(); e.hasMoreElements(); ) {
							key = e.nextElement().toString();

							if (rvalue == null)
								rvalue = default_fields.getProperty(key);

							p.setProperty(key, default_fields.getProperty(key));
						}
					}

					int newKeyValue = TaoGlobal.database.query.insert(tbl.getTableName(), p);

					if (acAfterInsert != null)
						acAfterInsert.actionPerformed(event);

					acRequery.actionPerformed(event);

					if (newKeyValue >= 0)
						tbl.locateRecord(0, Integer.toString(newKeyValue));
					else if (rvalue != null)
						tbl.locateRecord(0, rvalue);
				}
			} catch (Exception e) {
				Dlg.error("Ошибка добавления элемента справочника", e);
			}
		}
	}

	class AcEdit extends AbstractAction {
		AcEdit() {
			putValue(Action.NAME, "Редактировать");
			putValue(Action.SHORT_DESCRIPTION, "Редактировать элемент справочника (ENTER)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_edit );
		}

		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanEdit")))
				return;

			TaoDataModel dm = (TaoDataModel)tbl.getModel();
			Properties p = new Properties();
			String field;
			try {
				TaoRecordDialog dlg = new TaoRecordDialog("Редактирование элемента справочника", tbl.getScheme(), false);
				dlg.setValues(dm.getRow(tbl.getSelectedRow()));
      
				dlg.setVisible(true);
				if (dlg.result) {
					for (Enumeration e = dlg.getFields(); e.hasMoreElements(); ) {
						field = e.nextElement().toString();
						p.setProperty(field, dlg.getSQLField(field));
					}

					String oldKeyValue = dm.getData(tbl.getSelectedRow(), keyNumber).toString();

					if (key != null) 
						TaoGlobal.database.query.update(tbl.getTableName(), p, bar.getWhereExpression(tbl.getSelectedRow()));
					else {
						String expression = tbl.getScheme().getName(keyNumber) + " = " + oldKeyValue;
						TaoGlobal.database.query.update(tbl.getTableName(), p, expression);
					}

					acRequery.actionPerformed(event);
					tbl.locateRecord(keyNumber, oldKeyValue);
				}
			} catch (Exception e) {
				Dlg.error("Ошибка изменения элемента справочника", e);
			}
		}
	}

	class AcDelete extends AbstractAction {
		AcDelete() {
			putValue(Action.NAME, "Удалить");
			putValue(Action.SHORT_DESCRIPTION, "Удалить элемент справочника (DELETE)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_delete );
		}
 
		public void actionPerformed(ActionEvent event) {
			if (!(params.getBoolean("CanDelete")))
				return;

			try {
				TaoDataModel dm = (TaoDataModel)tbl.getModel();
				String name = dm.getData(tbl.getSelectedRow(), 1).toString();

				if (Dlg.queryYN(btnDelete.getText() + " запись справочника \"" + name + "\"?")) {
					int index = tbl.getSelectedRow();

					if (key != null) 
						TaoGlobal.database.query.delete(tbl.getTableName(), bar.getWhereExpression(index));
					else {
						String oldKeyValue = dm.getData(index, keyNumber).toString();
						String expression = tbl.getScheme().getName(keyNumber) + " = " + oldKeyValue;
						TaoGlobal.database.query.anul(tbl.getTableName(), expression);
					}

					acRequery.actionPerformed(event);

					if (index >= tbl.getRowCount())
						index--;

					if (index >= 0)
						tbl.changeSelection(index, 0, false, false);
				}
			} catch (Exception e) {
				if (btnDelete.getText().equals("Восстановить"))
					Dlg.error("Ошибка восстановления элемента справочника", e);
				else
					Dlg.error("Ошибка удаления элемента справочника", e);
			}
			updateControls();
		}
	}

	class AcUpdate extends AbstractAction {
		AcUpdate() {
			putValue(Action.NAME, "Обновить");
			putValue(Action.SHORT_DESCRIPTION, "Обновить данные справочника (F5)");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_reload);
		}
 
		public void actionPerformed(ActionEvent event) {
			TaoDataModel dm = (TaoDataModel)tbl.getModel();

			String oldKeyValue = null;

			if (isSelected()) {
				oldKeyValue = tbl.getSelected(keyNumber);
			}
			acRequery.actionPerformed(event);
  
			if (oldKeyValue != null)
				tbl.locateRecord(keyNumber, oldKeyValue);
		}
	}

	class AcRequery extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			try {
				tbl.refresh();  
			} catch (Exception e) {
				Dlg.error("Ошибка обновления таблицы", e);
			}

			tbl.revalidate();
			tbl.repaint();
			tbl.scrTable.revalidate();
			tbl.scrTable.repaint();
		}
	}
}
