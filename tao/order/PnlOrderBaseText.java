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

public class PnlOrderBaseText extends JPanel {
	private TaoTextArea fMebelInfo = new TaoTextArea("");
	private TaoTextArea fPackInfo = new TaoTextArea("");
	private TaoTextArea fClientInfo = new TaoTextArea("");

	private JPanel pMebelInfo = new TaoPanel();
	private JPanel pPackInfo = new TaoPanel();
	private JPanel pClientInfo = new TaoPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";

	private JLabel lMebelInfo = new JLabel(h1 + "Доп. сведения о изделиях: " + h2, SwingConstants.LEFT);
	private JLabel lPackInfo = new JLabel(h1 + "Доп. сведения о сборке, упаковке: " + h2, SwingConstants.LEFT);
	private JLabel lClientInfo = new JLabel(h1 + "Доп. сведения о установке на месте: " + h2, SwingConstants.LEFT);

	private Box boxMain = Box.createHorizontalBox();
	private int ID_Order = -1;

	public PnlOrderBaseText() {
		this.setLayout(new BorderLayout());
		pMebelInfo.setLayout(new BorderLayout());
		pPackInfo.setLayout(new BorderLayout());
		pClientInfo.setLayout(new BorderLayout());

		pMebelInfo.add(lMebelInfo, BorderLayout.NORTH);
		pMebelInfo.add(fMebelInfo, BorderLayout.CENTER);

		pPackInfo.add(lPackInfo, BorderLayout.NORTH);
		pPackInfo.add(fPackInfo, BorderLayout.CENTER);

		pClientInfo.add(lClientInfo, BorderLayout.NORTH);
		pClientInfo.add(fClientInfo, BorderLayout.CENTER);

		pMebelInfo.setPreferredSize(new Dimension(280, 0));
		pPackInfo.setPreferredSize(new Dimension(280, 0));
		pPackInfo.setMinimumSize(new Dimension(280, 0));
		pClientInfo.setPreferredSize(new Dimension(280, 0));
		this.add(pMebelInfo, BorderLayout.WEST);
		this.add(pPackInfo, BorderLayout.CENTER);
		this.add(pClientInfo, BorderLayout.EAST);
	}

	public void fillFields(Properties p) {
		p.setProperty("Mebel_Info", "\"" + fMebelInfo.getText().replaceAll("\"", "\\\\\"") + "\"");
		p.setProperty("Pack_Info", "\"" + fPackInfo.getText().replaceAll("\"", "\\\\\"")  + "\"");
		p.setProperty("Client_Info", "\"" +  fClientInfo.getText().replaceAll("\"", "\\\\\"")  + "\"");
	}

	public void load(int aID_Order, int aTypeOrder) throws Exception {
		TaoDataModel dmOrder = new TaoDataModel();
		ID_Order = aID_Order;

		if (aTypeOrder == 1)
			dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vstandard_orders WHERE ID_Order = '" + aID_Order + "'"));

		if (aTypeOrder == 2)
			dmOrder.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vhome_orders WHERE ID_Order = '" + aID_Order + "'"));

		try {
			fMebelInfo.setText(dmOrder.getData(0, "Mebel_Info").toString());
		} catch(Exception e) {};

		try {
			fPackInfo.setText(dmOrder.getData(0, "Pack_Info").toString());
		} catch(Exception e) {};

		try {
			fClientInfo.setText(dmOrder.getData(0, "Client_Info").toString());
		} catch(Exception e) {};
	}
}
