/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.dialogs;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import tao.icons.Nuvola;

import tao.visual.TaoTextView;

public class DlgQueryYN extends TaoDialog {
	private JPanel 
		pnlMain = new JPanel(new BorderLayout()),
		pnlLeft = new JPanel(new BorderLayout());

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel lbIco = new JLabel();
	private TaoTextView tvQuestion;

	private JButton
		btnYes = new JButton(),
		btnNo = new JButton();

	private AcYes acYes;
	private AcNo acNo;

	private Box 
		bxButtons = Box.createHorizontalBox(),
		bxRight = Box.createVerticalBox(),
		bxMain = Box.createHorizontalBox();

	public boolean result = false;

	public DlgQueryYN(JFrame frm, String question) {
		super(frm,"Внимание, вопрос!");
		tvQuestion = new TaoTextView(question);

		acYes = new AcYes();
		acNo = new AcNo();

		btnYes.setAction(acYes);
		btnNo.setAction(acNo);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnYes);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnNo);

		lbIco.setIcon(Nuvola.actions.ll_question);
		lbIco.setVerticalAlignment(JLabel.TOP);

		pnlLeft.add(lbIco, BorderLayout.CENTER);
		pnlLeft.setBorder(new EmptyBorder(0, 0, 0, 8));

		bxRight.add(tvQuestion, BorderLayout.CENTER);
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

		getRootPane().setDefaultButton(btnNo);

		pack();
		tvQuestion.setColumns(1);
		pack();

		int x = (dim.width - this.getWidth()) >> 1;
		int y = (dim.height - this.getHeight()) >> 1;
		this.setLocation(x, y);
	}

	class AcYes extends AbstractAction {
		AcYes() {
			putValue(Action.NAME, "Да");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
		  result = true;
		  setVisible(false);
		}
	}

	class AcNo extends AbstractAction {
		AcNo() {
			putValue(Action.NAME, "Нет");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			setVisible(false);
		}
	}
}
