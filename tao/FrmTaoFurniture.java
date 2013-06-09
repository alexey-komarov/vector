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

public class FrmTaoFurniture extends FrmTaoChild {
	private TaoTable tblFGroups = new TaoTable();
	private ResultSet rsFGroups;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private FrmTaoChild frm;
	private Hashtable<Integer, TaoDirectoryPanel> Furnitures = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoFurnitureListener listener = new FrmTaoFurnitureListener();
	private AcMoveToGroup acMoveToGroup = new AcMoveToGroup();

	public FrmTaoFurniture(String caption) throws Exception {
		super(caption, Nuvola.apps.s_furniture);
		frm = this;
		this.getContentPane().add(spMain);

		spMain.setLeftComponent(tblFGroups.getPanel());
		spMain.setRightComponent(new JPanel());

		tblFGroups.getPanel().setPreferredSize(new Dimension(200,200));

		tblFGroups.setScheme("Furniture_Groups");
		tblFGroups.refresh("SELECT * FROM furniture_groups WHERE Anul = 0");

		tblFGroups.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		}); 

		pack(); 

		tblFGroups.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblFGroups.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblFGroups.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblFGroups.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblFGroups.setDefaultRenderer(Float.class, new TaoRowRenderer());

		this.addInternalFrameListener(listener);

		onDirChanged(null);
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblFGroups.getSelected("ID_Furniture_Group") != null) {
			int ID_Furniture_Group = 0;

			Object obj = tblFGroups.getSelected("ID_Furniture_Group");

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
					Furnitures.get(ID_Furniture_Group).addToBar(new AcMoveToGroup());
				}
				spMain.setRightComponent(Furnitures.get(ID_Furniture_Group));

				Furnitures.get(ID_Furniture_Group).initHotKeys(null);
				Furnitures.get(ID_Furniture_Group).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника фурнитуры", ex);
			}
		}
	}

	class FrmTaoFurnitureListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
			/* Enumeration en = Furnitures.keys();
			int key;
			while (en.hasMoreElements()) {
				key = (Integer)en.nextElement();
				Furnitures.get(key).tblDirectory.saveScheme();
			} */
		}
	}

	class AcMoveToGroup extends AbstractAction {
		AcMoveToGroup() {
			putValue(Action.NAME, "Переместить в группу");
			putValue(Action.SHORT_DESCRIPTION, "Переместить в другую группу");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_movetogroup);
		}
 
		public void actionPerformed(ActionEvent event) {
			if (tblFGroups.getSelected("ID_Furniture_Group") != null) {
				int ID_Furniture_Group = 0;
				Object obj = tblFGroups.getSelected("ID_Furniture_Group");

				try {
					ID_Furniture_Group = new Integer(obj.toString());
				} catch (Exception ex) {
					return;
				}

				if (!Furnitures.get(ID_Furniture_Group).tblDirectory.isSelected()) {
					Dlg.error("Не выбран элемент справочника!");
					return;
				}

				try {
					DlgDirectory dlg = new DlgDirectory(103);
					dlg.setVisible(true);
					if (dlg.result) {
						try {
							int ID = Integer.parseInt(dlg.getSelect(0));
							Properties p = new Properties();
							p.setProperty("ID_Furniture_Group", "" + ID);
							String expression = "ID_Furniture = " + Integer.parseInt(Furnitures.get(ID_Furniture_Group).tblDirectory.getSelected("ID_Furniture"));
							try {
								TaoGlobal.database.query.update("Furniture", p, expression);
							} catch(Exception e) {
								Dlg.error("Ошибка перемещения элемента справочника в группу", e);
							}

							Furnitures.get(ID_Furniture_Group).tblDirectory.refresh();

							if (Furnitures.get(ID) != null)
								Furnitures.get(ID).tblDirectory.refresh();
						} catch (Exception e) {
							Dlg.error("Ошибка получения кода выбранной записи", e);
						}
					}
					dlg.dispose();
				} catch (Exception e) {
					Dlg.error("Ошибка открытия справочника \"Группы фурнитуры\"", e);
				}
			}
		}
	}
}
