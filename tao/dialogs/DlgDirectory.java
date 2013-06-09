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
import tao.database.TaoDataModel;
import tao.parameters.TaoParameters;

public class DlgDirectory extends TaoDialog {
	public boolean result = false;
	private DlgDirectory dlg;
	private Box bxRecord = Box.createVerticalBox();
	private TaoDataModel dmRec;

	private TaoDirectoryPanel dirPanel = null;

	public DlgDirectory(int aID_Directory) throws Exception {
		super(TaoGlobal.frmMain,"");
		dmRec = TaoGlobal.database.directories.getDirectoryRecord(aID_Directory);
		this.setModal(true);
		dlg = this;
		String aDictionaryName = dmRec.getData(0, "Name").toString();
		this.setTitle("Выбор из справочника \"" + aDictionaryName + "\"");

		AcClose acClose = new AcClose();
		AcSelect acSelect = new AcSelect();

		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		dirPanel = new TaoDirectoryPanel(aID_Directory, "Anul=0", false);

		this.add(dirPanel, BorderLayout.CENTER);
		dirPanel.initHotKeys(acSelect);

		JButton btnClose = new JButton(acClose);
		JButton btnSelect = new JButton(acSelect);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnSelect);
		bxButtons.add(btnClose);

		this.add(bxButtons, BorderLayout.SOUTH);

		this.pack();
		this.setPreferredSize(new Dimension(512, this.getSize().height));
		getRootPane().setDefaultButton(btnClose);
		this.pack();

		Dimension ownSize = TaoGlobal.frmMain.getSize();

		int x = (ownSize.width - this.getSize().width) >> 1;
		int y = (ownSize.height - this.getSize().height) >> 1;
		x = x + TaoGlobal.frmMain.getLocation().x;
		y = y + TaoGlobal.frmMain.getLocation().y;
		this.setLocation(x, y);
	}

	public String getSelect(String aFieldName) {
		return dirPanel.tblDirectory.getSelected(aFieldName);
	}

	public String getSelect(int aNumber) {
		return dirPanel.tblDirectory.getSelected(aNumber);
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
			if (dirPanel.tblDirectory.isSelected()) {
				result = true;
			}
			dlg.setVisible(false);
		}
	}
}
