/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.util.Properties;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.dialogs.DlgDirectory;
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

public class FrmTaoMebel extends FrmTaoChild {
	private TaoTable tblMGroups = new TaoTable();
	private ResultSet rsMGroups;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private FrmTaoChild frm;
	private Hashtable<Integer, TaoDirectoryPanel> Mebels = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoMebelListener listener = new FrmTaoMebelListener();
	private AcMoveToGroup acMoveToGroup = new AcMoveToGroup();

	public FrmTaoMebel(String caption) throws Exception {
		super(caption, Nuvola.apps.s_mebel);
		frm = this;
		this.getContentPane().add(spMain);

		spMain.setLeftComponent(tblMGroups.getPanel());
		spMain.setRightComponent(new JPanel());

		tblMGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblMGroups.setScheme("Mebel_Groups");
		tblMGroups.refresh("SELECT * FROM Mebel_groups WHERE Anul = 0");
		tblMGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblMGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblMGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblMGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblMGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblMGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());
		tblMGroups.setDefaultRenderer(Date.class, new TaoRowRenderer());

		this.addInternalFrameListener(listener);

		onDirChanged(null);
	}

	class AcMoveToGroup extends AbstractAction {
		AcMoveToGroup() {
			putValue(Action.NAME, "Переместить в группу");
			putValue(Action.SHORT_DESCRIPTION, "Переместить в другую группу");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_movetogroup);
		}

		public void actionPerformed(ActionEvent event) {
			if (tblMGroups.getSelected("ID_Mebel_Group") != null) {
				int ID_Mebel_Group = 0;
				Object obj = tblMGroups.getSelected("ID_Mebel_Group");
				try {
					ID_Mebel_Group = new Integer(obj.toString());
				} catch (Exception ex) {
					return; 
				}

				if (!Mebels.get(ID_Mebel_Group).tblDirectory.isSelected()) {
					Dlg.error("Не выбран элемент справочника!");
					return;
				}

				try {
					DlgDirectory dlg = new DlgDirectory(104);
					dlg.setVisible(true);
					if (dlg.result) {
						try {
							int ID = Integer.parseInt(dlg.getSelect(0));
							Properties p = new Properties();
							p.setProperty("ID_Mebel_Group", "" + ID);
							String expression = "ID_Mebel = " + Integer.parseInt(Mebels.get(ID_Mebel_Group).tblDirectory.getSelected("ID_Mebel"));
							try {
								TaoGlobal.database.query.update("Mebel", p, expression);
							} catch(Exception e) {
								Dlg.error("Ошибка перемещения элемента справочника в группу", e);
							}

							Mebels.get(ID_Mebel_Group).tblDirectory.refresh();

							if (Mebels.get(ID) != null)
								Mebels.get(ID).tblDirectory.refresh();
						} catch (Exception e) {
							Dlg.error("Ошибка получения кода выбранной записи", e);
						}
					}
					dlg.dispose();
				} catch (Exception e) {
					Dlg.error("Ошибка открытия справочника \"Группы мебели\"", e);
				}
			}
		}
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblMGroups.getSelected("ID_Mebel_Group") != null) {
			int ID_Mebel_Group = 0;

			Object obj = tblMGroups.getSelected("ID_Mebel_Group");

			try {
				ID_Mebel_Group = new Integer(obj.toString());
			} catch (Exception ex) {
				return;
			}

			try {
				if (Mebels.get(ID_Mebel_Group) == null) {
					Mebels.put(ID_Mebel_Group, new TaoDirectoryPanel(7, "ID_Mebel_Group = " + ID_Mebel_Group, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Mebel_Group",  "" + ID_Mebel_Group);
					Mebels.get(ID_Mebel_Group).addFields(pu);
					Mebels.get(ID_Mebel_Group).setKeyNumber(1);
					Mebels.get(ID_Mebel_Group).addKey(0);
					Mebels.get(ID_Mebel_Group).addKey(1);
					Mebels.get(ID_Mebel_Group).addToBar(new AcMoveToGroup());
				}
				spMain.setRightComponent(Mebels.get(ID_Mebel_Group));
				Mebels.get(ID_Mebel_Group).initHotKeys(null);
				Mebels.get(ID_Mebel_Group).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника мебели", ex);
			}
		}
	}

	class FrmTaoMebelListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
			/* Enumeration en = Mebels.keys();
			int key;
			while (en.hasMoreElements()) {
				key = (Integer)en.nextElement();
				Mebels.get(key).tblDirectory.saveScheme();
			} */
		}
	}
}
