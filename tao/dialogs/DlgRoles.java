/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JButton;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Properties;
import tao.visual.TaoDirectoryPanel;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;

public class DlgRoles extends TaoDialog {
	public boolean result = false;
	private DlgRoles dlg;
	private Box bxRecord = Box.createVerticalBox();

	public DlgRoles(int aID_User) throws Exception {
		super(TaoGlobal.frmMain, "Роли пользователя");
		this.setModal(true);
		dlg = this;

		AcClose acClose = new AcClose();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		TaoDirectoryPanel rolePanel = new TaoDirectoryPanel(-1, "ID_User = " + aID_User, false);

		Properties pu = new Properties();
		pu.setProperty("ID_User", "" + aID_User);

		rolePanel.addFields(pu);
		rolePanel.setKeyNumber(1);
		rolePanel.addKey(0);
		rolePanel.addKey(1);

		this.add(rolePanel, BorderLayout.CENTER);
		rolePanel.initHotKeys(null);

		JButton btnClose = new JButton(acClose);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnClose);

		this.add(bxButtons, BorderLayout.SOUTH);

		this.pack();
		this.setPreferredSize(new Dimension(512, this.getSize().height));
		this.setResizable(false);
		getRootPane().setDefaultButton(btnClose);
		this.pack();

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);

		if (rolePanel.tblDirectory.getRowCount() > 0)
			rolePanel.tblDirectory.sorter.toggleSortOrder(1);
	}

	class AcClose extends AbstractAction {
		AcClose() {
			putValue(Action.NAME, "Закрыть");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}
 
		public void actionPerformed(ActionEvent event) {
			listener.windowClosing(null);
			dlg.setVisible(false);
		}
	}
}
