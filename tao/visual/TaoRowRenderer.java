/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import tao.database.TaoDataModel;

public class TaoRowRenderer implements TableCellRenderer {
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	private Calendar ds = Calendar.getInstance();
	private Calendar cc = Calendar.getInstance();

	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column)
	{
		row = table.convertRowIndexToModel(row);
		boolean isOrders = false;

		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);

		TaoDataModel dm = (TaoDataModel)table.getModel();

		if (dm.getRowCount() == 0) {
		  return renderer;
		}

		isOrders = dm.flg_orders;

		Object anul = dm.getData(row, "Anul");

		if ( (anul instanceof Boolean) && (((Boolean)anul).booleanValue()) )
			renderer.setForeground(Color.RED);
		else
			renderer.setForeground(Color.BLACK);

		Color color = Color.BLACK;

		if (column % 2 == 0)
			color = new Color(234,255,244);

		if (column % 2 == 1)
			color = new Color(244, 255, 234);

		if (dm.isColumnFiltered(column)) {
			renderer.setBackground(color);
		} else
			renderer.setBackground(Color.WHITE);

		if (isSelected)
			renderer.setBackground(new Color(0xA8, 0xC8, 0xE8));

		color = renderer.getBackground();
		if (row%2 == 1)
			renderer.setBackground(new Color(color.getRed() - 8, color.getGreen() - 8, color.getBlue() - 8));

		if (isOrders) {
			Object oStatus = dm.getData(row, "ID_Order_Status");
			if ((oStatus != null) && (oStatus instanceof Integer)) {
				int status = (Integer)oStatus;
				if (status == 4) {
					renderer.setForeground(new Color(0, 120, 0));
				} else {
					Object dg = dm.getData(row, "Date_Order_End");
					if ((dg != null) && (dg instanceof Date)) {
						ds.setTime((Date)dg);
						cc = Calendar.getInstance();

						ds.set(Calendar.HOUR_OF_DAY, 0);
						ds.set(Calendar.MINUTE, 0);
						ds.set(Calendar.SECOND, 0);
						ds.set(Calendar.MILLISECOND, 0);

						cc.set(Calendar.HOUR_OF_DAY, 0);
						cc.set(Calendar.MINUTE, 0);
						cc.set(Calendar.SECOND, 0);
						cc.set(Calendar.MILLISECOND, 0);

						if ((ds.compareTo(cc) == 0)) {
							//Сегодня{
							renderer.setForeground(new Color(196, 0, 0));
						} else {
							if (ds.before(cc)) //просрочен
								renderer.setForeground(new Color(156, 0, 100));
							else { //осталось три дня?
								ds.add(Calendar.DATE, -3);
								if ( (ds.before(cc) || ds.compareTo(cc) == 0))
									renderer.setForeground(new Color(156, 70, 0));
							}
						}
					}
				}
			}
		}
	return renderer;
	}
}
