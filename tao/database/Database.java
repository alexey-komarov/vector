/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database;

import java.sql.Connection;
import tao.database.subsystem.*;

public class Database {
	public Connection connection = null;
	public TaoDirectories directories;
	public TaoXML_Schemes schemes;
	public TaoVisualSubSystem visual;
	public TaoQuery query;
	public TaoUsers users;
	public Order order;

	public Database() {
		directories = new TaoDirectories();
		schemes = new TaoXML_Schemes();
		visual = new TaoVisualSubSystem();
		query = new TaoQuery();
		order = new Order();
	}

	public void init() throws Exception {
		schemes.reload();
	}

	public void loadUser(String aLogin) throws Exception {
		users = new TaoUsers(aLogin);
	}

	public String sqlValue(Object value) {
		int type;

		if (value == null) {
			return "null";
		}

		if ( (value.getClass().equals(Integer.TYPE))
		     | (value.getClass().equals(Double.TYPE))
		     | (value.getClass().equals(Float.TYPE)) )
		{
			return value.toString();
		} else if (value.getClass().equals(Boolean.TYPE)) {
			return ((Boolean)value).booleanValue() ? "1" : "0";
		} else {
			return "'" + value.toString() + "'";
		}
	}
}
