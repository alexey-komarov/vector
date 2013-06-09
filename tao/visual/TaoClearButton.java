/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import tao.icons.Nuvola;

public class TaoClearButton extends JButton {
	private AbstractAction action = null;

	TaoClearButton(AbstractAction aClick) {
		this.setAction(new AcClick());
		action = aClick;
		this.setMargin(new Insets(0, 0, 1, 1));
	}

	class AcClick extends AbstractAction {
		AcClick() {
			putValue(Action.NAME,"");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_clear);
			putValue(Action.SHORT_DESCRIPTION, "Очистить");
		}

		public void actionPerformed(ActionEvent event) {
			if (action != null)
				action.actionPerformed(event);
		}
	}
}
