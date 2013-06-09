/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

import tao.global.TaoGlobal;
import tao.visual.*;
import tao.icons.*;
import tao.dialogs.*;
import tao.database.TaoDataModel;

public class PnlOrderKitchenText extends JPanel {
	private TaoTextArea fNotes = new TaoTextArea("");
	private TaoTextArea fInstallation = new TaoTextArea("");

	private JPanel pNotes = new TaoPanel();
	private JPanel pInstallation = new TaoPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lNotes = new JLabel(h1 + "Примечания: " + h2, SwingConstants.LEFT);
	private JLabel lInstallation = new JLabel(h1 + "Сведения об установке: " + h2, SwingConstants.LEFT);

	private Box boxMain = Box.createHorizontalBox();
	private int ID_Order = -1;

	public PnlOrderKitchenText() {
		this.setLayout(new BorderLayout());
		pNotes.setLayout(new BorderLayout());
		pInstallation.setLayout(new BorderLayout());

		pNotes.add(lNotes, BorderLayout.NORTH);
		pNotes.add(fNotes, BorderLayout.CENTER);

		pInstallation.add(lInstallation, BorderLayout.NORTH);
		pInstallation.add(fInstallation, BorderLayout.CENTER);

		pNotes.setPreferredSize(new Dimension(320, 0));
		pNotes.setMinimumSize(new Dimension(280, 0));
		this.add(pNotes, BorderLayout.WEST);
		this.add(pInstallation, BorderLayout.CENTER);
	}

	public void fillFields(Properties p) {
		p.setProperty("Installation", "\"" + fInstallation.getText().replaceAll("\"", "\\\\\"") + "\"");
		p.setProperty("Notes", "\"" + fNotes.getText().replaceAll("\"", "\\\\\"")  + "\"");
	}

	public void load(int aID_Order) throws Exception {
		TaoDataModel dmOrder = new TaoDataModel();
		ID_Order = aID_Order;
		dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vkitchen_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			fNotes.setText(dmOrder.getData(0, "Notes").toString());
		} catch(Exception e) {};

		try {
			fInstallation.setText(dmOrder.getData(0, "Installation").toString());
		} catch(Exception e) {};
	}
}
