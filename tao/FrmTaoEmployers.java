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

public class FrmTaoEmployers extends FrmTaoChild {
	private TaoTable tblProfessions = new TaoTable();
	private ResultSet rsProfessions;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private FrmTaoChild frm;
	private Hashtable<Integer, TaoDirectoryPanel> Employers = new Hashtable<Integer, TaoDirectoryPanel>();
	private FrmTaoEmployersListener listener = new FrmTaoEmployersListener();

	public FrmTaoEmployers(String caption) throws Exception {
		super(caption, Nuvola.actions.s_employers);
		frm = this;
		this.getContentPane().add(spMain);
		spMain.setLeftComponent(tblProfessions.getPanel());
		spMain.setRightComponent(new JPanel());
		tblProfessions.getPanel().setPreferredSize(new Dimension(200,200));

		tblProfessions.setScheme("Professions");
		tblProfessions.refresh("SELECT * FROM professions WHERE Anul = 0");

		tblProfessions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onDirChanged(e);
			}
		}); 

		pack(); 

		tblProfessions.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tblProfessions.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tblProfessions.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tblProfessions.setDefaultRenderer(String.class, new TaoRowRenderer());
		tblProfessions.setDefaultRenderer(Float.class, new TaoRowRenderer());
		this.addInternalFrameListener(listener);
		onDirChanged(null);
	}

	public void onDirChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (tblProfessions.getSelected("ID_Profession") != null) {
			int ID_Profession = 0;

			Object obj = tblProfessions.getSelected("ID_Profession");

			try {
				ID_Profession = new Integer(obj.toString());
			} catch (Exception ex) {
				return;
			}

			try {
				if (Employers.get(ID_Profession) == null) {
					Employers.put(ID_Profession, new TaoDirectoryPanel(8, "ID_Profession = " + ID_Profession, true));
					Properties pu = new Properties();
					pu.setProperty("ID_Profession", ""+ID_Profession);
					Employers.get(ID_Profession).addFields(pu);
					Employers.get(ID_Profession).setKeyNumber(1);
					Employers.get(ID_Profession).addKey(0);
					Employers.get(ID_Profession).addKey(1);
				}
				spMain.setRightComponent(Employers.get(ID_Profession));

				Employers.get(ID_Profession).initHotKeys(null);
				Employers.get(ID_Profession).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника сотрудников", ex);
			}
		}
	}

	class FrmTaoEmployersListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
			/* Enumeration en = Employerss.keys();
			int key;
			while (en.hasMoreElements()) {
				key = (Integer)en.nextElement();
				Employerss.get(key).tblDirectory.saveScheme();
			} */
		}
	}
}
