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
import tao.visual.TaoParamsTree;
import tao.database.TaoStoredProc;
import tao.parameters.TaoParametersCollection;

public class FrmTaoRules extends FrmTaoChild {
	private FrmTaoRules frm;
	private int ID_Directory = 4;
	private TaoDirectoryPanel pnlDir;
	private JSplitPane spMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private Hashtable<Integer, TaoParamsTree> params = new Hashtable<Integer, TaoParamsTree>();

	public FrmTaoRules() throws Exception {
		super("Настройка параметров ролей", Nuvola.apps.s_params);
		frm = this;
		this.getContentPane().add(spMain);

		pnlDir = new TaoDirectoryPanel(ID_Directory, null, true);
		pnlDir.hideBar();

		pnlDir.tblDirectory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				onRoleChanged(e);
			}
		});

		spMain.setLeftComponent(pnlDir);

		pnlDir.initHotKeys(null);
		onRoleChanged(null);
		pack();
	}

	public void onRoleChanged(ListSelectionEvent e) {
		if ((e != null) && (e.getValueIsAdjusting()))
			return;

		if (pnlDir.tblDirectory.getSelected("ID_Role") != null) {
			int ID_Role = 0;

			Object obj = pnlDir.tblDirectory.getSelected("ID_Role");

			try {
				ID_Role = new Integer(obj.toString());
			} catch (Exception ex) {
				return; 
			}

			try {
				if (params.get(ID_Role) == null) {
					params.put(ID_Role, new TaoParamsTree(ID_Role));
				}
				spMain.setRightComponent(params.get(ID_Role).scrTree);
			} catch (Exception ex) {
				Dlg.error("Ошибка загрузки параметров роли", ex);
			}
		}
	}
}
