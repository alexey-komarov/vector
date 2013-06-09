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
import tao.database.TaoStoredProc;

public class FrmTaoRoles extends FrmTaoChild {
	private FrmTaoRoles frm;
	private int ID_Directory = 4;
	private TaoDirectoryPanel pnlDir;

	public FrmTaoRoles() throws Exception {
		super("”правление рол€ми пользователей", Nuvola.apps.s_roles);
		frm = this;
		pnlDir = new TaoDirectoryPanel(ID_Directory, null, true);
		this.add(pnlDir);
		pnlDir.initHotKeys(null);
		pack();
	}
}


