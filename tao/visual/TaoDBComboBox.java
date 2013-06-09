/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.JComboBox;
import java.sql.ResultSet;
import java.util.Vector;

public class TaoDBComboBox extends JComboBox {
	private Vector<String> values = new Vector<String>();

	public void setSource(ResultSet rs) {
		try {
			rs.beforeFirst();
			while (rs.next()) {
				values.addElement(rs.getObject(1).toString());
				this.addItem(rs.getObject(2).toString());
			}
		} catch(Exception e) {}
	}

	public String getValue() {
		return values.elementAt(this.getSelectedIndex());
	}

	public void setValue(Object aValue) {
		this.setSelectedIndex(values.indexOf(aValue.toString()));
	}
}
