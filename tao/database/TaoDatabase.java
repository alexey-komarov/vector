/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import tao.global.TaoGlobal;
import java.util.Properties;
import java.sql.Types;
import java.util.Calendar;

public class TaoDatabase {
	public Exception exception;
	public TaoDatabase() { }

	public ResultSet getNewResultSet(String sql) throws Exception {
		Statement stmt = TaoGlobal.database.connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);

		return stmt.executeQuery(sql);
	}

	public void fillProperties(Properties p, String sql) throws Exception {
		ResultSet rs = getNewResultSet(sql);
		while (rs.next()) {
			p.setProperty(rs.getObject("ID_Scheme").toString(), rs.getObject("xml").toString());
		}
	}

	public void execQuery(String aQuery) throws Exception {
		Statement stmt = TaoGlobal.database.connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);

		stmt.execute(aQuery);
	}

	public int keyExecQuery(String aQuery) throws Exception {
		Statement stmt = TaoGlobal.database.connection.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);

		try {
			stmt.execute(aQuery, Statement.RETURN_GENERATED_KEYS);
		} catch (Exception e) { }

		ResultSet rs = stmt.getGeneratedKeys();
		rs.first();
		try {
			return rs.getInt(1);
		} catch (Exception e) {
			return -1;
		}
	}

	public String getTempTableName() {
		Calendar cal = Calendar.getInstance();
		TaoGlobal.ttCounter++;
		return "tt_" + System.currentTimeMillis() + "_" +
			TaoGlobal.database.users.getID_User() +
			"_" + TaoGlobal.ttCounter;
	}
}
