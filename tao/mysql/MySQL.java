/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.mysql;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import tao.global.TaoGlobal;
import java.util.Properties;

public class MySQL {
	public static SQLException sql_exception = null;
	public static Exception exception = null;

	public static Connection connect(String host, String user, String password, String database) {
		Properties properties=new Properties();
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			String url = "jdbc:mysql://" + host + "/" + database + "?user=" + user +
				"&password=" + password + "&autoReconnect=true&max_allowed_packet=16M";

			properties.setProperty("characterEncoding", "CP1251");

			return DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			sql_exception = e;
			return null;
		} catch (Exception e) {
			exception = e;
			return null;
		}
	}
}
