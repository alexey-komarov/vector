/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database;

import tao.global.TaoGlobal;
import tao.dialogs.Dlg;

import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Calendar;
import java.awt.Point;
import tao.xml.TaoXMLTable;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.RowFilter;
import tao.utils.TaoStringFilter;
import tao.utils.TaoIntegerFilter;
import tao.utils.TaoFloatFilter;
import tao.utils.TaoBooleanFilter;
import tao.utils.TaoDirectoryFilter;
import tao.utils.TaoDateFilter;

public class TaoDataModel extends AbstractTableModel {
	public String[] columnNames = {};
	private Vector<Vector> rows = new Vector<Vector>();
	private Hashtable<Integer, ResultSet> rsDir = new Hashtable<Integer, ResultSet>();

	private ResultSetMetaData metaData = null;
	private int columnCount = 0;  
	private int rowCount = 0;  
	private TaoXMLTable xmlTable = null;
	private boolean[] columnVisible;
	private Hashtable<Integer, Object> filters = new Hashtable<Integer, Object>();
	public ResultSet rs = null;

	public TaoRowFilter filter = new TaoRowFilter();
	public boolean flg_orders = false; //для раскраски

	public int getRealNumber(int column) {
		int result = -1;
		int i;
		for (i = 0; i < columnCount; i++) {
			if (columnVisible[i])
				result++;

			if (result == column)
				break;
		}

		if (result != column)
			i = -1;

		return i; 
	}

	public boolean isColumnVisible(String aName) {
		if (xmlTable == null){
			return true;
		} else {
			return xmlTable.getVisible(aName);
		}
	}

	public String getColumnName(int column) {
		int realColumn = getRealNumber(column);
		if (realColumn == -1)
			return "";
		else if (xmlTable == null) {
			return columnNames[getRealNumber(column)];
		} else {
			return xmlTable.getCaption(columnNames[getRealNumber(column)]);
		}
	}

	public String getFieldName(int column) {
		int realColumn = getRealNumber(column);
		if (realColumn == -1)
			return "";
		else
			return columnNames[getRealNumber(column)];
	}

	public Object getValueAt(int aRow, int aColumn) {
		int realColumn = getRealNumber(aColumn);
		try {
			Vector row = (Vector)rows.elementAt(aRow);
			if (realColumn == -1)
				return null;
			else {
				if (xmlTable.getType(realColumn).equals("Directory")) {
					//lazy
					if (rsDir.get(realColumn) == null) {
						try {
							rsDir.put(realColumn, TaoGlobal.database.directories.getNewDirectoryFull(xmlTable.getDirectory(realColumn)));
						} catch(Exception e) {}
					}
					ResultSet rsd = rsDir.get(realColumn);

					Object value = row.elementAt(realColumn);
					String strValue = "";

					if (value != null)
						strValue = value.toString();

					String newValue = null;
					if (rsd != null) {
						rsd.beforeFirst();
						while (rsd.next()) {
							if (strValue.equals(rsd.getObject(1).toString())) {
								newValue = rsd.getObject(2).toString();
								break;
							}
						}

						if (newValue != null)
							return newValue;
						else 
							return value;
					} else
						return value;
					} else
				return row.elementAt(realColumn);
			}
		} catch (Exception e) {
			System.out.println("TaoDataModel.getValueAt " + e);
			return null;
		}
	}

	public int getColumnCount() {
		int result = 0;
		for (int i = 0; i < columnCount; i++)
			if (columnVisible[i]) result++;
		return result;
	}

	public int getRowCount() {
		return rows.size();
	}

	public void refresh(ResultSet aResultSet) throws Exception {
		rs = aResultSet;
		rows.clear();
		metaData = rs.getMetaData();
		columnCount = metaData.getColumnCount();
		columnNames = new String[columnCount];
		columnVisible = new boolean[columnCount];

		for(int column = 0; column < columnCount; column++) {
			columnNames[column] = metaData.getColumnLabel(column + 1);
			columnVisible[column] = isColumnVisible(columnNames[column]);
		}

		while (rs.next()) {
			Vector<Object> newRow = new Vector<Object>();
			for (int i = 0; i < columnCount; i++) {
				newRow.addElement(rs.getObject(i + 1));
			}
			rows.addElement(newRow);
		}
	}

	public void setXML(String xml) {
		xmlTable = new TaoXMLTable(xml);
	}

	public void setScheme(String aID_Scheme) throws Exception {
		if (TaoGlobal.database.schemes.getScheme(aID_Scheme) == null) {
			throw new Exception("Ошибка. В базе данных отсутствует схема " + aID_Scheme+".");
		} else {
			xmlTable = new TaoXMLTable(TaoGlobal.database.schemes.getScheme(aID_Scheme));
		}
	}

	public TaoXMLTable getScheme() {
		return xmlTable;
	} 

	public int getColumnIndexByName(String columnName) {
		int result;
		for (result = 0; result < columnCount; result++) {
			if (columnNames[result].equals(columnName))
				break;
		}

		if (result == columnCount)
			result = -1;

		return result;
	}

	public Object getData(int aRow, int aColumn) {
		Object result = new String("");
		if (aColumn >= 0) {
			Vector row = (Vector)rows.elementAt(aRow);

			if (row.elementAt(aColumn) != null)
				result = row.elementAt(aColumn);
		}
		return result;
	}

	public Vector getRow(int aRow) {
		return (Vector)rows.elementAt(aRow);
	}

	public Object getData(int aRow, String columnName) {
		int rci = getColumnIndexByName(columnName);

		if (rci < 0)
			return null;
		else
			return getData(aRow,getColumnIndexByName(columnName));
	}
  
	public Class getColumnClass(int aIndex) {
		if (getRowCount() > 0) {
			if (getValueAt(0, aIndex) == null)
				return ("".getClass());
			else
				return getValueAt(0, aIndex).getClass();
		} else
			return ("".getClass());
	}

	public void setFilter(int aColumn, Object aObject) {
		if (aObject != null)
			filters.put(aColumn, aObject);
	}

	public boolean isColumnFiltered(int aColumn) {
		boolean result = false;
		Object obj = filters.get(aColumn);

		if (obj instanceof TaoStringFilter)
			result = (((TaoStringFilter)obj).oper1 > 0);

		if (obj instanceof TaoIntegerFilter) 
			result = (((TaoIntegerFilter)obj).oper1 > 0);

		if (obj instanceof TaoDateFilter) 
			result = (((TaoDateFilter)obj).oper1 > 0);

		if (obj instanceof TaoFloatFilter) 
			result = (((TaoFloatFilter)obj).oper1 > 0);

		if (obj instanceof TaoBooleanFilter) 
			result = (((TaoBooleanFilter)obj).oper1 > 0);

		if (obj instanceof TaoDirectoryFilter) 
			result = (((TaoDirectoryFilter)obj).oper1 > 0);

		return result;
	}

	public class TaoRowFilter extends RowFilter<Object, Object> {
		public boolean include(Entry entry) {
			boolean result = true;
			Object obj = null;

		TaoStringFilter sf;
		TaoIntegerFilter ifi;
		TaoDateFilter dfi;
		TaoFloatFilter ff;
		TaoBooleanFilter bf;
		TaoDirectoryFilter df;
		String svalue;
		Integer ivalue;
		float fvalue;
		boolean bvalue;
		int dvalue;
		Calendar davalue = Calendar.getInstance();

		for (int i = 0; i < getColumnCount(); i++) {
			obj = filters.get(i);
			sf = null;

			if (!(isColumnFiltered(i)))
				continue;

			if (obj instanceof TaoStringFilter) {
				sf = (TaoStringFilter)obj;
				svalue = entry.getStringValue(i);
				result = sf.test(svalue);
			}

			if (obj instanceof TaoIntegerFilter) {
				ifi = (TaoIntegerFilter)obj;
				try {
					ivalue = (Integer)entry.getValue(i);
				} catch (Exception e) { ivalue = 0; }
				result = ifi.test(ivalue);
			}

			if (obj instanceof TaoDateFilter) {
				dfi = (TaoDateFilter)obj;
				try {
					davalue.setTime((Date)entry.getValue(i));
				} catch (Exception e) {
					davalue = null;
				}
				result = dfi.test(davalue);
			}

			if (obj instanceof TaoFloatFilter) {
				ff = (TaoFloatFilter)obj;
				try {
					fvalue = (Float)entry.getValue(i);
				} catch (Exception e) {
					fvalue = 0;
				}
				result = ff.test(fvalue);
			}

			if (obj instanceof TaoBooleanFilter) {
				bf = (TaoBooleanFilter)obj;
				try {
					bvalue = (Boolean)entry.getValue(i);
				} catch (Exception e) {
					bvalue = false;
				}
				result = bf.test(bvalue);
			}

			if (obj instanceof TaoDirectoryFilter) {
				df = (TaoDirectoryFilter)obj;
				try {
					dvalue = 0;
				} catch (Exception e) {
					dvalue = 0;
				}
				result = df.test(dvalue);
			}

			if (!result)
				break;
		}
		return result;
		}
	}

	public TaoStringFilter getStringFilter(int aColumn) {
		Object obj = null; 
		TaoStringFilter sf = null;
		obj = filters.get(aColumn);
		if (obj instanceof TaoStringFilter)
			sf = (TaoStringFilter)obj;

		return sf;
	}

	public TaoIntegerFilter getIntegerFilter(int aColumn) {
		Object obj = null; 
		TaoIntegerFilter ifi = null;
		obj = filters.get(aColumn);
		if (obj instanceof TaoIntegerFilter)
			ifi = (TaoIntegerFilter)obj;

		return ifi;
	}

	public TaoDateFilter getDateFilter(int aColumn) {
		Object obj = null; 
		TaoDateFilter dfi = null;
		obj = filters.get(aColumn);
		if (obj instanceof TaoDateFilter) 
			dfi = (TaoDateFilter)obj;

		return dfi;
	}

	public TaoFloatFilter getFloatFilter(int aColumn) {
		Object obj = null; 
		TaoFloatFilter ff = null;
		obj = filters.get(aColumn);
		if (obj instanceof TaoFloatFilter) 
			ff = (TaoFloatFilter)obj;

		return ff;
	}

	public TaoBooleanFilter getBooleanFilter(int aColumn) {
		Object obj = null; 
		TaoBooleanFilter bf = null;
		obj = filters.get(aColumn);

		if (obj instanceof TaoBooleanFilter)
			bf = (TaoBooleanFilter)obj;

		return bf;
	}

	public TaoDirectoryFilter getDirectoryFilter(int aColumn) {
		Object obj = null; 
		TaoDirectoryFilter df = null;
		obj = filters.get(aColumn);
		if (obj instanceof TaoDirectoryFilter)
			df = (TaoDirectoryFilter)obj;

		return df;
	}

	public String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
