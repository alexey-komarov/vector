/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database.subsystem;

import tao.database.TaoDataModel;
import tao.database.TaoDatabase;

public class TaoUsers extends TaoDatabase {
	private TaoDataModel dmUser = new TaoDataModel();
	private TaoDataModel dmEmployee = new TaoDataModel();

	public TaoUsers(String aLogin) throws Exception {
		dmUser.refresh(getNewResultSet("SELECT * FROM users WHERE Login = '" + aLogin + "'"));
		dmEmployee.refresh(getNewResultSet("SELECT * FROM employers WHERE ID_User = '" + getID_User() + "'"));
	}

	public String getLogin() {
		return dmUser.getData(0, "Login").toString();
	}

	public int getID_User() {
		return ((Integer)dmUser.getData(0, "ID_User")).intValue();
	}

	public int getID_Employee() {
		if (dmEmployee.getRowCount() > 0)
			return ((Integer)dmEmployee.getData(0, "ID_Employee")).intValue();
		else
			return -1;
	}

	public String getEmployee() {
		if (dmEmployee.getRowCount() > 0)
			return dmEmployee.getData(0, "Family").toString() + " " +
				dmEmployee.getData(0, "Name").toString() + " " +
				dmEmployee.getData(0, "Patronymic").toString();
		else
			return "";
	}

	public boolean getAnul() {
		return ((Boolean)dmUser.getData(0, "Anul")).booleanValue();
	}

	public String getShortName() {
		String fam = dmUser.getData(0, "Family").toString().trim();
		String nam = dmUser.getData(0, "Name").toString().trim();
		String pat = dmUser.getData(0, "Patronymic").toString().trim();
		String result = "";

		if (fam.length() > 0) {
			result = fam;

			if (nam.length() > 0)
				result = result + " " + nam.substring(0, 1) + ".";

			if (pat.length() > 0)
				result = result + " " + pat.substring(0, 1) + ".";
		} else {
			if (nam.length() > 0) {
				result = nam;

				if (pat.length() > 0)
					result = result + " " + pat;
			} else {
				result = pat;
			}
		}
		return result;
	}
}
