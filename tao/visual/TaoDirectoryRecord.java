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
import tao.dialogs.Dlg;
import tao.dialogs.DlgDirectory;
public class TaoDirectoryRecord extends JPanel {
	private int ID_Directory;
	private String fieldName;
	private JButton btnSelect = new JButton();
	private JTextField fDirectory = new JTextField();
	private AcSelect acSelect = new AcSelect();
	private TaoDataModel dmRec;
	private String directoryName;
	private int ID = -1;

	public AbstractAction acAfterSelect = null;

	public TaoDirectoryRecord(int aID_Directory, String aFieldName) {
		ID_Directory = aID_Directory;
		fieldName = aFieldName;
		fDirectory.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(btnSelect, BorderLayout.EAST);
		this.add(fDirectory, BorderLayout.CENTER);
		this.add(new TaoClearButton(new AcClear()), BorderLayout.WEST);

		try {
			dmRec = TaoGlobal.database.directories.getDirectoryRecord(ID_Directory);
		} catch (Exception e) {
			Dlg.error("Ошибка чтения справочника с кодом " + ID_Directory, e);
		}

		directoryName = dmRec.getData(0, "Name").toString();
		btnSelect.setAction(acSelect);
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
				DlgDirectory dlg = new DlgDirectory(ID_Directory);
				dlg.setVisible(true);
				if (dlg.result) {
					try {
						ID = Integer.parseInt(dlg.getSelect(0));
						fDirectory.setText(dlg.getSelect(fieldName));
					} catch (Exception e) {
						Dlg.error("Ошибка получения кода выбранной записи", e);
					}
				}

				dlg.dispose();

				if (acAfterSelect != null)
					acAfterSelect.actionPerformed(event);
			} catch (Exception e) {
					Dlg.error("Ошибка открытия справочника \"" + directoryName + "\"", e);
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
