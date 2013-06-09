/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database.subsystem;

import tao.database.TaoDatabase;
import java.util.Properties;
import java.util.Enumeration;

import java.sql.ResultSet;

public class Order extends TaoDatabase {
	public Integer getNextNumber() throws Exception {
		ResultSet rs = getNewResultSet("SELECT Number + 1 FROM orders ORDER BY ID_Order DESC LIMIT 0,1");
		rs.first();
		try {
			return ((Long)rs.getObject(1)).intValue();
		} catch (Exception e) {
			return 1;
		}
	}
}
