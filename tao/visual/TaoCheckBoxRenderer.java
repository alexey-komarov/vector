/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.border.*;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;

import tao.database.TaoDataModel;

public class TaoCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	Border noFocusBorder;
	Border focusBorder;

	public TaoCheckBoxRenderer() {
		super();
		setOpaque(true);
		setBorderPainted(true);
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column)
	{

		row = table.convertRowIndexToModel(row);

		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}

		if (hasFocus) {
			if (focusBorder == null) {
				focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(focusBorder);
		} else {
			if (noFocusBorder == null) {
				noFocusBorder = new EmptyBorder(1, 1, 1, 1);
			}
			setBorder(noFocusBorder);
		}

		setSelected(Boolean.TRUE.equals(value));

		TaoDataModel dm = (TaoDataModel)table.getModel();

		Object anul = dm.getData(row, "Anul");

		if ( (anul instanceof Boolean) && (((Boolean)anul).booleanValue()) )
			this.setForeground(Color.RED);
		else
			this.setForeground(Color.BLACK);

		Color color = Color.BLACK;

		if (column % 2 == 0)
			color = new Color(234, 255, 244);

		if (column % 2 == 1)
			color = new Color(244, 255, 234);

		if (dm.isColumnFiltered(column))
			this.setBackground(color);
		else
			this.setBackground(Color.WHITE);

		if (isSelected)
			this.setBackground(new Color(0xA8, 0xC8, 0xE8));

		color = this.getBackground();

		if (row % 2 == 1)
			this.setBackground(new Color(color.getRed() - 8, color.getGreen() - 8, color.getBlue() - 8));

		return this;
	}
}
