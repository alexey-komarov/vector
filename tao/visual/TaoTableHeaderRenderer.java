/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import tao.database.TaoDataModel;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class TaoTableHeaderRenderer extends DefaultTableCellRenderer {
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus,
		int row, int column) 
	{
		TableCellRenderer renderer = table.getCellRenderer(row, column);
			return (Component)renderer;
	}
}
