/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database.subsystem;

import tao.database.TaoDatabase;
import java.util.Properties;
import java.util.Enumeration;

public class TaoQuery extends TaoDatabase {
	public int insert(String aTableName, Properties aProp) throws Exception {
		String key;
		String fields = "";
		String values = "";
		for (Enumeration e = aProp.propertyNames(); e.hasMoreElements();) {
			key = e.nextElement().toString();

			if (fields.length() > 0)
				fields = fields + ",";

			fields = fields + key;

			if (values.length() > 0)
				values = values + ",";

			values = values + aProp.getProperty(key);
		}
		String query = "INSERT INTO " + aTableName +"(" + fields +") VALUES(" +values +")";
		return keyExecQuery(query);
	}

	public void update(String aTableName, Properties aProp, String Expression) throws Exception {
		String key;
		String values = "";
		for (Enumeration e = aProp.propertyNames(); e.hasMoreElements();) {
			key = e.nextElement().toString();

			if (values.length() > 0)
				values = values + ",";

			values = values + key + "=" + aProp.getProperty(key);
		}
		String query = "UPDATE " + aTableName + " SET " + values + " WHERE " + Expression;

		execQuery(query);
	}

	public void anul(String aTableName, String Expression) throws Exception {
		String query = "UPDATE " + aTableName + " SET anul = anul XOR 1 WHERE " + Expression;
		execQuery(query);
	}

	public void delete(String aTableName, String Expression) throws Exception {
		String query = "DELETE FROM " + aTableName + " WHERE " + Expression;
		execQuery(query);
	}

	public void setPassword(String aUserName, String aPassword) throws Exception {
		String query ="UPDATE mysql.user SET Password=PASSWORD('" + aPassword + "')" +
		              "WHERE User = '" + aUserName + "'";
		execQuery(query);
		query = "FLUSH PRIVILEGES";
		execQuery(query);
	}
}
