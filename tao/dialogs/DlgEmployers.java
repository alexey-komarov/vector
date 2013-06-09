/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.sql.*;
import tao.visual.*;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.database.TaoDataModel;
import tao.parameters.TaoParameters;

public class DlgEmployers extends TaoDialog {
	public boolean result = false;
	private TaoTable tblProfessions = new TaoTable();
	private ResultSet rsProfessions;
	private DlgEmployers dlg;
	private Box bxRecord = Box.createVerticalBox();
	private TaoDataModel dmRec;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Hashtable<Integer, TaoDirectoryPanel> Employers = new Hashtable<Integer, TaoDirectoryPanel>();
	private AcSelect acSelect = new AcSelect();

	public DlgEmployers() throws Exception {
		super(TaoGlobal.frmMain,"Выбор из справочника \"Сотрудники\"");
		this.setModal(true);
		dlg = this;

		spMain.setLeftComponent(tblProfessions.getPanel());

		spMain.setRightComponent(new JPanel());

		this.getContentPane().add(spMain);

		AcClose acClose = new AcClose();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		JButton btnClose = new JButton(acClose);
		JButton btnSelect = new JButton(acSelect);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnSelect);
		bxButtons.add(btnClose);

		this.add(bxButtons, BorderLayout.SOUTH);

		this.pack();
		this.setPreferredSize(new Dimension(620, this.getSize().height >> 1));
		getRootPane().setDefaultButton(btnSelect);
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

		onDirChanged(null);

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getSelect(String aFieldName) {
		int ID_Profession = 0;
		Object obj = tblProfessions.getSelected("ID_Profession");

		try {
			ID_Profession = new Integer(obj.toString());
			return Employers.get(ID_Profession).tblDirectory.getSelected(aFieldName);
		} catch (Exception ex) {
			Dlg.error("Ошибка выбора сотрудника", ex);
			return "";
		}
	}

	public String getSelect(int aNumber) {
		int ID_Profession = 0;

		Object obj = tblProfessions.getSelected("ID_Profession");

		try {
			ID_Profession = new Integer(obj.toString());
			return Employers.get(ID_Profession).tblDirectory.getSelected(aNumber);
		} catch (Exception ex) {
			Dlg.error("Ошибка выбора сотрудника", ex);
			return "";
		}
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
					Employers.put(ID_Profession, new TaoDirectoryPanel(8, "ID_Profession = " + ID_Profession, false));
					Properties pu = new Properties();
					pu.setProperty("ID_Profession", "" + ID_Profession);
					Employers.get(ID_Profession).addFields(pu);
					Employers.get(ID_Profession).setKeyNumber(1);
					Employers.get(ID_Profession).addKey(0);
					Employers.get(ID_Profession).addKey(1);
				}
				spMain.setRightComponent(Employers.get(ID_Profession));

				Employers.get(ID_Profession).initHotKeys(acSelect);
				Employers.get(ID_Profession).tblDirectory.applyScheme(spMain.getSize().width);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки справочника сотрудников", ex);
			}
		}
	}

	class AcClose extends AbstractAction {
		AcClose() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			listener.windowClosing(null);
			dlg.setVisible(false);
		}
	}

	class AcSelect extends AbstractAction {
		AcSelect() {
			putValue(Action.NAME, "Выбрать");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			listener.windowClosing(null);
			int ID_Profession = 0;

			Object obj = tblProfessions.getSelected("ID_Profession");

			try {
				ID_Profession = new Integer(obj.toString());

				if (Employers.get(ID_Profession).tblDirectory.isSelected())
					result = true;

				dlg.setVisible(false);
			} catch (Exception ex) {
				Dlg.error("Ошибка выбора сотрудника", ex);
			}
		}
	}
}
