/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import tao.database.TaoDataModel;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.dialogs.*;
public class MebelRecord extends JPanel {
	private JButton btnSelect = new JButton();
	private JTextField fDirectory = new JTextField();
	private AcSelect acSelect = new AcSelect();
	private int ID = -1;
	private AbstractAction afterSelect = null;

	public float defPrice = 0;
	public float defSquare = 0;

	public MebelRecord() {
		fDirectory.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(btnSelect, BorderLayout.EAST);
		this.add(fDirectory, BorderLayout.CENTER);
		this.add(new TaoClearButton(new AcClear()), BorderLayout.WEST);
		btnSelect.setAction(acSelect);
	}

	public void setAfterSelect(AbstractAction aAction) {
		afterSelect = aAction;
	}

	public int getID() {
		return ID;
	}

	public void setValue(int aID, String aName) {
		ID = aID;
		fDirectory.setText(aName);
	}

	class AcSelect extends AbstractAction {
		AcSelect() {
			putValue(Action.NAME,"");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_find);
			putValue(Action.SHORT_DESCRIPTION, "Выбрать из справочника");
		}

		public void actionPerformed(ActionEvent event) {
			try {
				DlgMebelSelect dlg = new DlgMebelSelect();
				dlg.setVisible(true);
				if (dlg.result) {
					try {
						ID = Integer.parseInt(dlg.getSelect(0));
						fDirectory.setText(dlg.getSelect("Name"));
						try {
							defPrice = Float.parseFloat(dlg.getSelect("Price"));
						} catch (Exception e) {}

						try {
							defSquare = Float.parseFloat(dlg.getSelect("Square"));
						} catch (Exception e) {}
					} catch (Exception e) {
						Dlg.error("Ошибка получения кода выбранной записи", e);
					}
				}
				dlg.dispose();
				if (afterSelect != null)
					afterSelect.actionPerformed(event);
			} catch (Exception e) {
				Dlg.error("Ошибка открытия справочника \"Мебель\"", e);
			}
		}
	}

	class AcClear extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
			ID = -1;
			fDirectory.setText("");
		}
	}
}
