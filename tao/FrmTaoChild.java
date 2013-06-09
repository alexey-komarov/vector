/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import javax.swing.AbstractAction;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.Box;

import tao.global.TaoGlobal;
import tao.visual.TaoTable;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.xml.TaoXMLFrame;

public class FrmTaoChild extends JInternalFrame {
	private FrmTaoChildListener listener;
	private JButton btnTask = new JButton();
	Box statusBox = Box.createHorizontalBox();
	private JButton btnSaveFrm = new JButton();
	private JInternalFrame frm = null;
	private TaoXMLFrame xmlFrame;
	private AcSaveFrame acSaveFrame;

	public FrmTaoChild(String caption, Icon icon) {
		super (caption);
		frm = this;

		this.frameIcon = icon;
		listener = new FrmTaoChildListener(this);
		AcRegister acRegister = new AcRegister(this, caption);
		acSaveFrame = new AcSaveFrame();

		btnTask.setMargin(new Insets(0, 0, 1, 1));
		btnTask.setAction(acRegister);
		TaoGlobal.taskbar.add(btnTask);

		statusBox.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));

		btnSaveFrm.setPreferredSize(new Dimension(20, 20));
		btnSaveFrm.setAction(acSaveFrame);
		statusBox.add(Box.createHorizontalGlue());

		this.addInternalFrameListener(listener);
		this.resizable = true;
		this.maximizable = true;
		this.setIconifiable(true);
		this.closable = true;
	}

	public void setScheme() {
		xmlFrame = new TaoXMLFrame(TaoGlobal.database.schemes.getScheme(frm));
		xmlFrame.applyScheme(frm);
	}

	public void saveFrame() {
		acSaveFrame.actionPerformed(null);
	}

	//Регистрируем окно на таскбаре
	class AcRegister extends AbstractAction {
		private FrmTaoChild frmParent;

		AcRegister(FrmTaoChild frm, String caption) {
			putValue(Action.NAME, caption);
			putValue(Action.SMALL_ICON, frameIcon);
			frmParent = frm;
		}

		public void actionPerformed(ActionEvent event) {
			try {
				frmParent.setIcon(false);
				frmParent.setVisible(true);
				frmParent.setSelected(true);
			} catch(Exception ex) { }
			frmParent.moveToFront();
		}
	}

	//Сохранение настроек окна
	class AcSaveFrame extends AbstractAction {

		AcSaveFrame() {
			putValue(Action.NAME,"");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_save);
			putValue(Action.SHORT_DESCRIPTION, "Сохранить настройки окна");
		}

		public void actionPerformed(ActionEvent event) {
			TaoGlobal.database.schemes.saveAndSetScheme(frm);
			processTaoTableSave(frm);
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
	}

	class FrmTaoChildListener extends InternalFrameAdapter {
		private JInternalFrame frmParent;

		public FrmTaoChildListener(JInternalFrame frm) {
			frmParent = frm;
		}

		public void internalFrameClosed(InternalFrameEvent e) {
			//Удалить кнопку с таскбара
			TaoGlobal.taskbar.remove(btnTask);
			TaoGlobal.updateTaskBar(btnTask);
			acSaveFrame.actionPerformed(null);
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

		public void internalFrameOpened(InternalFrameEvent e) {
			setScheme();
			processTaoTableLoad(frmParent);
		}

		public void internalFrameIconified(InternalFrameEvent e) {
			frmParent.setVisible(false);
		}

		public void internalFrameActivated(InternalFrameEvent e) {
			TaoGlobal.updateTaskBar(btnTask);
		}
	}
}
