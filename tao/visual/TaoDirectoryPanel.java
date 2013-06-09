/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JCheckBox;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import java.util.Properties;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.database.TaoDataModel;

import tao.visual.TaoTable;
import tao.visual.TaoTableBar;
import tao.visual.TaoRowRenderer;
import tao.parameters.TaoParameters;
import tao.visual.TaoTableHeaderRenderer;

public class TaoDirectoryPanel extends JPanel {
	public TaoTable tblDirectory = new TaoTable();
	private JCheckBox cbShowDeleted = new JCheckBox();
	private ResultSet rsDirectory;

	private TaoTableBar barDirectory;
	private AcShowDeleted acShowDeleted = new AcShowDeleted();
	private TaoParameters dictprm;
	private TaoDirectoryPanel pnl;
	private TaoDataModel dmRec;
	private int ID_Directory;
	private String where;
	private boolean anul;
	private boolean barHidden = false;

	public TaoDirectoryPanel(int aID_Directory, String aWhere, boolean aAnul) throws Exception {
		anul = aAnul;
		ID_Directory = aID_Directory;

		where = aWhere;

		dmRec = TaoGlobal.database.directories.getDirectoryRecord(ID_Directory);
		String aDictionaryName = dmRec.getData(0, "Name").toString();

		dictprm = new TaoParameters("Справочники." + aDictionaryName);

		pnl = this;
		pnl.setLayout(new BorderLayout());

		cbShowDeleted.setAction(acShowDeleted);

		barDirectory = new TaoTableBar(tblDirectory, dictprm);

		if ((dictprm.getBoolean("CanShowDeleted")) && (anul))
			barDirectory.add(cbShowDeleted);

		cbShowDeleted.setMargin(new Insets(0, 0, 1, 1));

		tblDirectory.getPanel().setPreferredSize(new Dimension(250,200));
		tblDirectory.addTop(barDirectory);

		pnl.add(tblDirectory.getPanel());

		tblDirectory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirItemChanged(e);
			}
		});

		tblDirectory.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblDirectory.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblDirectory.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblDirectory.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblDirectory.setDefaultRenderer(Float.class, new TaoRowRenderer());
		acShowDeleted.actionPerformed(null);
	}

	public void initHotKeys(AbstractAction acSelect) {
		if (!barHidden)
			barDirectory.initHotKeys(acSelect);
	}

	public void addFields(Properties p) {
		barDirectory.addFields(p);
	}

	public void hideBar() {
		tblDirectory.getPanel().remove(barDirectory);
		barHidden = true;
	}

	public void addToBar(AbstractAction aAction) {
		JButton btn = new JButton();
		btn.setMargin(new Insets(0, 0, 1, 1));
		btn.setAction(aAction);
		barDirectory.add(btn);
	}

	public void hideEdit() {
		barDirectory.remove(barDirectory.btnEdit);
		barDirectory.repaint();
	}

	public void setKeyNumber(int aKeyNumber) {
		barDirectory.setKeyNumber(aKeyNumber);
	}

	public void addKey(int aKeyNumber) {
		barDirectory.addKey(aKeyNumber);
	}

	public Object getSelectedField(String aField) {
		TaoDataModel dm = (TaoDataModel)tblDirectory.getModel();
		return dm.getData(tblDirectory.getSelectedRow(), aField);
	}

	public void setAfterInsert(AbstractAction a) {
		barDirectory.setAfterInsert(a);
	}

	public void onDirItemChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		boolean anul = false;

		try {
			TaoDataModel dm = (TaoDataModel)tblDirectory.getModel();
			anul = ((Boolean)dm.getData(tblDirectory.getSelectedRow(), "Anul")).booleanValue();
		} catch (Exception ex) { }

		if (anul) {
			barDirectory.btnDelete.setText("Восстановить");
			barDirectory.btnDelete.setIcon(Nuvola.actions.s_undo);
			barDirectory.btnDelete.setToolTipText("Восстановить элемент справочника (DELETE)");
		} else {
			barDirectory.btnDelete.setText("Удалить");
			barDirectory.btnDelete.setIcon(Nuvola.actions.s_delete);
			barDirectory.btnDelete.setToolTipText("Удалить элемент справочника (DELETE)");
		}
		barDirectory.repaint();
	}

	class AcShowDeleted extends AbstractAction {
		AcShowDeleted() {
			putValue(Action.NAME, "Показывать удалённые");
		}

		public void actionPerformed(ActionEvent event) {
			try {
				String oldKeyValue = null;
				if ((tblDirectory.getModel() instanceof TaoDataModel)) {
					TaoDataModel dm = (TaoDataModel)tblDirectory.getModel();
					if (dm.getRowCount() > 0) { 
						//Ищем неудаленную запись, на тот случай, если курсор 
						//стоит на удаленной записи, а мы скрываем удаленные
						int srow = tblDirectory.getSelectedRow();
						while ( (srow>0) && (((Boolean)dm.getData(srow, "Anul")).booleanValue()) )
							srow--;

						oldKeyValue = dm.getData(srow, 0).toString();
					}
				}

				String fTable_Name = dmRec.getData(0, "Table_Name").toString();
				String fID_Scheme = dmRec.getData(0, "ID_Scheme").toString(); 

				tblDirectory.setScheme(fID_Scheme);

				if ((cbShowDeleted.isSelected()) | (!anul)) {
					String query = "SELECT * FROM " + fTable_Name;
					if (where != null)
						query = query + " WHERE " + where;

					tblDirectory.refresh(query);
					if (oldKeyValue != null)
						tblDirectory.locateRecord(0, oldKeyValue);
				} else {
					String query = "SELECT * FROM " + fTable_Name + " WHERE Anul = 0";
					if (where != null)
						query = query + " AND " + where;
						tblDirectory.refresh(query);
						if (oldKeyValue != null) {
							tblDirectory.locateRecord(0, oldKeyValue);
						}
					}

					tblDirectory.setTableName(fTable_Name);
					barDirectory.updateControls(tblDirectory);

					if (pnl.isVisible())
						tblDirectory.applyScheme();

					tblDirectory.revalidate();
					tblDirectory.repaint();
					tblDirectory.scrTable.revalidate();
					tblDirectory.scrTable.repaint();
				}
			catch (Exception ex) {
				Dlg.error("Ошибка чтения справочника!", ex);
			}
		}
	}
}
