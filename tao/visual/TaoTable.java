/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import java.sql.ResultSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JToolBar;
import javax.swing.RowSorter;
import javax.swing.RowFilter;
import javax.swing.JPopupMenu;
import javax.swing.JCheckBoxMenuItem;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import tao.database.TaoDataModel;
import tao.database.TaoDatabase;

import tao.dialogs.Dlg;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.xml.TaoXMLTable;
import tao.dialogs.DlgStringFilter;
import tao.dialogs.DlgIntegerFilter;
import tao.dialogs.DlgDateFilter;
import tao.dialogs.DlgFloatFilter;
import tao.dialogs.DlgBooleanFilter;
import tao.dialogs.DlgDirectoryFilter;
import tao.dialogs.Dlg;

public class TaoTable extends JTable {
	public JScrollPane scrTable;
	private JPanel pnl = new JPanel();
	private String query;
	private TaoTable tbl;
	private TaoXMLTable scheme;
	private TaoDataModel dm = new TaoDataModel();
	private JButton btnSave = new JButton();
	private AcSave acSave = new AcSave();
	private String ID_Scheme = null;
	private JPopupMenu pmFilter = new JPopupMenu();
	public TableRowSorter<TaoDataModel> sorter = null;
	private String tableName = "";
	private ActionListener pmFilterListener = new PopupActionListener();

	public TaoTable() {
		super();

		tbl = this;
		tbl.getTableHeader().setReorderingAllowed(false);
		scrTable = new JScrollPane(this);
		this.setFillsViewportHeight(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pnl.setLayout(new BorderLayout());
		pnl.add(scrTable, BorderLayout.CENTER);

		btnSave.setAction(acSave);
		btnSave.setPreferredSize(new Dimension(20, 20));
		Box bxButtons = Box.createHorizontalBox();

		MouseListener popupListener = new PopupListener();
		this.getTableHeader().addMouseListener(popupListener);
	}

	public JPanel getPanel() {
		return pnl;
	}

	public boolean isSelected() {
		return (this.getSelectedRow() != -1) && (dm.getRowCount() > 0);
	}

	public void setPreferredColumnWidths(int aColumn, int aPercent, int width) {
		TableColumnModel columnNodel = getColumnModel();
		TableColumn tableColumn = columnModel.getColumn(aColumn);

		while (tableColumn==null) {
			tableColumn = new TableColumn(this.dataModel.getColumnCount());
			this.addColumn(tableColumn);
			tableColumn = getColumnModel().getColumn(aColumn);
		}

		if (tableColumn != null) {
			tableColumn.setPreferredWidth((int) (width * ((float)aPercent / 100)));
		}
	}

	public void setTableName(String aTableName) {
		tableName = aTableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void addTop(Component cmp) {
		pnl.add(cmp, BorderLayout.NORTH);
	}

	public TaoXMLTable getScheme() {
		return scheme;
	}

	public void setScheme(String aID_Scheme) {
		try {
			dm.setScheme(aID_Scheme);
			ID_Scheme = aID_Scheme;
		} catch (Exception e)
			{System.out.println(e);}

		scheme = dm.getScheme();
		tbl.getTableHeader().repaint();
	}

	public void refresh() throws Exception {
		refresh(TaoGlobal.database.query.getNewResultSet(query));
	}

	public void refresh(String aQuery) throws Exception {
		query = aQuery;
		refresh(TaoGlobal.database.query.getNewResultSet(query));
	}

	public void refresh(ResultSet ARS) {
		try {
			dm.refresh(ARS);
			tbl.setModel(dm);

			if (sorter==null) {
				if (this.getRowCount() > 0) {
					sorter = new TableRowSorter<TaoDataModel>(dm);
					sorter.setRowFilter(dm.filter);
					tbl.setRowSorter(sorter);
				}
			} else {
				if (dm.getRowCount() > 0)
					sorter.allRowsChanged();
				this.repaint();
				this.getTableHeader().resizeAndRepaint();
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		for (int i = pmFilter.getComponentCount() - 1; i >= 0; i--)
			pmFilter.remove(i);

		for (int i = 0; i < this.getColumnCount(); i++) {
			int column = dm.getRealNumber(i);
			JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(this.getColumnModel().getColumn(i).getHeaderValue().toString());
			cbmi.addActionListener(pmFilterListener);
			if (dm.isColumnFiltered(i)) {
				cbmi.setState(true);
			}
			pmFilter.add(cbmi);
		}

		this.changeSelection(0, 0, false, false);
	}

	public String getSelected(String aFieldName) {
		try {
			return dm.getData(this.convertRowIndexToModel(this.getSelectedRow()), dm.getColumnIndexByName(aFieldName)).toString();
		} catch (Exception e) {
			return null;
		}
	}

	public String getSelected(int aIndex) {
		try {
			return dm.getData(this.convertRowIndexToModel(this.getSelectedRow()), aIndex).toString();
		} catch (Exception e) {
			return null;
		}
	}

	public void locateRecord(int aColumnIndex, String  aValue) {
		String keyValue;
		for (int i = 0; i < tbl.getRowCount(); i++) {
			keyValue = dm.getData(this.convertRowIndexToModel(i), aColumnIndex).toString();
			if (keyValue.equals(aValue))
				tbl.changeSelection(i, 0, false, false);
		}
	}

	public void applyScheme() {
		int ii = 0;
		if (scheme != null)
		for (int i = 0; i < scheme.getFieldCount(); i++) {
			if (scheme.getVisible(i)) {
				if (ii < this.getColumnCount()){
					setPreferredColumnWidths(ii, scheme.getWidth(i), getSize().width);
				}
				ii++;
			}
		}
	}

	public void applyScheme(int width) {
		int ii = 0;
		if (scheme != null)
		for (int i = 0; i < scheme.getFieldCount(); i++) {
			if (scheme.getVisible(i)) {
				if (ii < this.getColumnCount()) {
					setPreferredColumnWidths(ii, scheme.getWidth(i), width);
				}
				ii++;
			}
		}
	}

	public void saveScheme() {
		acSave.actionPerformed(null);
	}

	class AcSave extends AbstractAction {
		AcSave() {
			putValue(Action.NAME, "");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_save);
			putValue(Action.SHORT_DESCRIPTION, "Сохранить настройки таблицы");
		}

		public void actionPerformed(ActionEvent event) {
			if (ID_Scheme != null) {
				TableColumnModel columnNodel = getColumnModel();
				int width = getSize().width;
				int newWidth = 0;

				for (int i = 0; i < dm.getColumnCount(); i++) {
					TableColumn tableColumn = columnModel.getColumn(i);
					newWidth = Math.round((float)tableColumn.getWidth() / width * 100);
					scheme.setWidth(dm.getFieldName(i), newWidth);
				}

				TaoGlobal.database.schemes.saveAndSetScheme(ID_Scheme, scheme.getXML());
			}
		}
	}

	class PopupActionListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			Object source = actionEvent.getSource();
			if ((source != null) && (source instanceof Component)) {
				int column = dm.getRealNumber(pmFilter.getComponentIndex((Component)source));
				int tcolumn = pmFilter.getComponentIndex((Component)source);
				Class cclass = dm.getColumnClass(column);

				if (dm.getScheme().getType(column).equals("Integer")) {
					DlgIntegerFilter dlg = new DlgIntegerFilter(dm.getIntegerFilter(tcolumn), tbl.getScheme().getCaption(column));
					dlg.setVisible(true);
					if (dlg.result != null) {
						dm.setFilter(tcolumn, dlg.result);
						try {
							tbl.refresh();
						} catch(Exception e) {
							Dlg.error("Ошибка применения фильтра к колонке \"" + tbl.getScheme().getCaption(column) + "\"", e);
						}
					}
					dlg.dispose();
				}

				if (dm.getScheme().getType(column).equals("Date")) {
					DlgDateFilter dlg = new DlgDateFilter(dm.getDateFilter(tcolumn), tbl.getScheme().getCaption(column));
					dlg.setVisible(true);

					if (dlg.result != null) {
						dm.setFilter(tcolumn, dlg.result);
						try {
							tbl.refresh();
						} catch(Exception e) {
							Dlg.error("Ошибка применения фильтра к колонке \"" + tbl.getScheme().getCaption(column) + "\"", e);
						}
					}

					dlg.dispose();
				}

				if (dm.getScheme().getType(column).equals("Boolean")) {
					DlgBooleanFilter dlg = new DlgBooleanFilter(dm.getBooleanFilter(tcolumn), tbl.getScheme().getCaption(column));
					dlg.setVisible(true);
					if (dlg.result != null) {
						dm.setFilter(tcolumn, dlg.result);
						try {
							tbl.refresh();
						} catch(Exception e) {
							Dlg.error("Ошибка применения фильтра к колонке \"" + tbl.getScheme().getCaption(column) + "\"", e);
						}
					}
					dlg.dispose();
				}

				if ( (dm.getScheme().getType(column).equals("String")) ||
					 (dm.getScheme().getType(column).equals("Text")) ||
					 (dm.getScheme().getType(column).equals("Directory")) )
				{
					DlgStringFilter dlg = new DlgStringFilter(dm.getStringFilter(tcolumn), tbl.getScheme().getCaption(column));
					dlg.setVisible(true);
					if (dlg.result != null) {
						dm.setFilter(tcolumn, dlg.result);
						try {
							tbl.refresh();
						} catch(Exception e) {
							Dlg.error("Ошибка применения фильтра к колонке \"" + tbl.getScheme().getCaption(column) + "\"", e);
						}
					}
					dlg.dispose();
				}

				if (dm.getScheme().getType(column).equals("Float")) {
					DlgFloatFilter dlg = new DlgFloatFilter(dm.getFloatFilter(tcolumn), tbl.getScheme().getCaption(column));
					dlg.setVisible(true);

					if (dlg.result != null) {
						dm.setFilter(tcolumn, dlg.result);

						try {
							tbl.refresh();
						} catch(Exception e) {
							Dlg.error("Ошибка применения фильтра к колонке \"" + tbl.getScheme().getCaption(column) + "\"", e);
						}
					}
					dlg.dispose();
				}
			}
		}
	}

	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		private void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (pmFilter.getComponentCount() > 0)
					pmFilter.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
