/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JFileChooser;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JSeparator;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Properties;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.StringWriter;

import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.visual.TaoTextView;

public class DlgInfo extends TaoDialog {
	private JPanel 
		pnlMain = new JPanel(new BorderLayout()),
		pnlLeft = new JPanel(new BorderLayout());

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lbIco = new JLabel();

	private TaoTextView
		taLongMsg,
		taShortMsg;

	private JButton btnOk = new JButton();

	private AcOk acOk;

	private Box 
		bxButtons = Box.createHorizontalBox(),
		bxSave = Box.createHorizontalBox(),
		bxRight = Box.createVerticalBox(),
		bxMain = Box.createHorizontalBox();

	public DlgInfo(JFrame frm, String shortMsg) {
		super(frm,"Сообщение");
		taShortMsg = new TaoTextView(shortMsg);

		acOk = new AcOk();
		btnOk.setAction(acOk);
		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);

		lbIco.setIcon(Nuvola.actions.ll_messagebox_info);
		lbIco.setVerticalAlignment(JLabel.TOP);

		pnlLeft.add(lbIco, BorderLayout.CENTER);
		pnlLeft.setBorder(new EmptyBorder(0, 0, 0, 8));

		bxRight.add(taShortMsg, BorderLayout.CENTER);
		bxRight.add(Box.createVerticalStrut(10));
		bxRight.add(new JSeparator());
		bxRight.add(Box.createVerticalStrut(10));
		bxRight.add(bxButtons);
		bxRight.add(Box.createVerticalStrut(10));

		pnlMain.setBorder(new EmptyBorder(8, 8, 8, 8));
		pnlMain.add(pnlLeft, BorderLayout.WEST);
		pnlMain.add(bxRight, BorderLayout.CENTER);

		this.setContentPane(pnlMain);
		this.setResizable(false);
		this.setModal(true);

		getRootPane().setDefaultButton(btnOk);

		pack();
		taShortMsg.setColumns(1);
		pack();
		getRootPane().setDefaultButton(btnOk);

		int x = (dim.width - this.getWidth()) >> 1;
		int y = (dim.height - this.getHeight()) >> 1;
		this.setLocation(x, y);
	}

class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
}
