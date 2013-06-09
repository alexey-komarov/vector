/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import java.util.Hashtable;
import java.util.Enumeration;
import java.sql.ResultSet;

import tao.database.TaoDatabase;
import tao.global.TaoGlobal;

public class TaoParametersCollection extends TaoDatabase {
	public Hashtable<String, Object> parameters = new Hashtable<String, Object>();
	public Hashtable<String, Object> defParameters = new Hashtable<String, Object>();
	public Hashtable<String, String> descr_parameters = new Hashtable<String, String>();

	public TaoParametersCollection() {
		parameters = new Hashtable<String, Object>();
		descr_parameters = new Hashtable<String, String>();
	}

	public void setDefaultParameters() {
		Enumeration e = TaoGlobal.parameters.parameters.keys();
		String key;
		while (e.hasMoreElements()) {
			key = e.nextElement().toString();
			parameters.put(key, TaoGlobal.parameters.parameters.get(key));
		}
	}

	public void loadRoleParameters(int aID_Role) throws Exception {
		ResultSet rsParams = getNewResultSet("SELECT Parameter, BoolValue FROM parameters WHERE ID_Role=" + aID_Role);
		while (rsParams.next()) {
			parameters.put(rsParams.getObject(1).toString(), rsParams.getObject(2)) ;
		}
	}

	public void loadUserParameters(int aID_User) throws Exception {
		ResultSet rsRoles = getNewResultSet("SELECT ID_Role FROM users_roles WHERE ID_User=" + aID_User + " ORDER BY Position");
		while (rsRoles.next()) {
			loadRoleParameters((Integer)(rsRoles.getObject(1)));
		}
	}

	// public void saveParameters(String aUser) throws Exception { }
}
