/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.InputMap;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import tao.visual.TaoTable;

public class TaoDialog extends JDialog {
	private TaoDialog dlg;
	protected TaoDlgListener listener;

	public TaoDialog(JFrame frm, String caption ) {
		super(frm, caption);
		dlg = this;
		listener = new TaoDlgListener();
		this.addWindowListener(listener);

		JRootPane rootPane = this.getRootPane();
		InputMap iMap = rootPane.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);

		ActionMap aMap = rootPane.getActionMap();

		aMap.put("escape", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				listener.windowClosed(null);
				dispose();
			}
		});
	}

	class TaoDlgListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			processTaoTableSave(dlg);
		}

		private void processTaoTableSave(Container cnt) {
			TaoTable tbl;
			for (int k = 0; k < cnt.getComponentCount(); k++) {
				if (cnt.getComponent(k) instanceof TaoTable) {
					tbl = (TaoTable)cnt.getComponent(k);
					tbl.saveScheme();
			} else if (cnt.getComponent(k) instanceof Container)
				processTaoTableSave((Container)cnt.getComponent(k));
			}
		}

		private void processTaoTableLoad(Container cnt) {
			TaoTable tbl;
			for (int k = 0; k < cnt.getComponentCount(); k++) {
				if (cnt.getComponent(k) instanceof TaoTable) {
					tbl = (TaoTable)cnt.getComponent(k);
					tbl.applyScheme();
				} else if (cnt.getComponent(k) instanceof Container)
					processTaoTableLoad((Container)cnt.getComponent(k));
			}
		}

		public void windowOpened(WindowEvent e) {
			processTaoTableLoad(dlg);
		}
	}
}
