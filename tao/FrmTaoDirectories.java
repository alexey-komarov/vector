/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

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

public class FrmTaoDirectories extends FrmTaoChild {
	private TaoTable tblDirectories = new TaoTable();
	private ResultSet rsDirectories;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private FrmTaoChild frm;
	private Hashtable<Integer, TaoDirectoryPanel> dirs = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoDirectoriesListener listener = new FrmTaoDirectoriesListener();

	public FrmTaoDirectories(String caption) throws Exception {
		super(caption, Nuvola.apps.s_bookcase);
		frm = this;
		this.getContentPane().add(spMain);

		spMain.setLeftComponent(tblDirectories.getPanel());
		spMain.setRightComponent(new JPanel());

		tblDirectories.getPanel().setPreferredSize(new Dimension(200,200));
		tblDirectories.setScheme("Directories");
		tblDirectories.refresh("SELECT * FROM directories WHERE Is_System = 0");
		tblDirectories.getSelectionModel().addListSelectionListener(new ListSelectionListener() { 
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		});

		pack();

		tblDirectories.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblDirectories.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblDirectories.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblDirectories.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblDirectories.setDefaultRenderer(Float.class, new TaoRowRenderer());

		this.addInternalFrameListener(listener);

		onDirChanged(null);
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblDirectories.getSelected("ID_Directory") != null) {
			int ID_Directory = 0;

			Object obj = tblDirectories.getSelected("ID_Directory");

		try {
			ID_Directory = new Integer(obj.toString());
		} catch (Exception ex) {
			return;
		}

			try {
				if (dirs.get(ID_Directory) == null)
					dirs.put(ID_Directory, new TaoDirectoryPanel(ID_Directory, null, true));
				spMain.setRightComponent(dirs.get(ID_Directory));
				dirs.get(ID_Directory).initHotKeys(null);
				dirs.get(ID_Directory).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника", ex);
			}
		}
	}

	class FrmTaoDirectoriesListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
			Enumeration en = dirs.keys();
			int key;
			while (en.hasMoreElements()) {
				key = (Integer)en.nextElement();
				dirs.get(key).tblDirectory.saveScheme();
			}
		}
	}
}

