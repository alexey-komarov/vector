/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.sql.CallableStatement;
import tao.global.TaoGlobal;

public class TaoStoredProc {
	public Vector<Object> params = new Vector<Object>();

	private String name = null;

	public TaoStoredProc(String aName, int aParamCount) {
		name = aName;
	}

	public void execute() throws Exception {
		String cmd = null;
		for (Iterator i = params.iterator(); i.hasNext(); ) {
			if (cmd == null)
				cmd = "{call " + name + "(";
			else 
				cmd = cmd + ",";

			cmd = cmd + TaoGlobal.database.sqlValue(i.next());
		}

		if (cmd == null)        
			cmd = "{call " + name + "(";

		cmd = cmd + ")}";
		CallableStatement cStmt = TaoGlobal.database.connection.prepareCall(cmd);
		cStmt.execute();
	}
}
