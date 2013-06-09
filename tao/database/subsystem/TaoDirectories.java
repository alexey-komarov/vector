/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database.subsystem;

import tao.global.*;
import java.sql.ResultSet;
import tao.database.TaoDatabase;
import tao.database.TaoDataModel;

public class TaoDirectories extends TaoDatabase {
	public TaoDirectories() {
		super();
	}

	public ResultSet getNewDirectories() throws Exception {
		return getNewResultSet("SELECT * FROM directories WHERE Is_System = 0");
	}

	public ResultSet getNewDirectory(String Table_name) throws Exception {
		return getNewResultSet("SELECT * FROM " + Table_name + " WHERE Anul = 0");
	}

	public ResultSet getNewDirectoryFull(String Table_name) throws Exception {
		return getNewResultSet("SELECT * FROM " + Table_name );
	}

	public TaoDataModel getDirectoryRecord(int ID_Directory) throws Exception {
		TaoDataModel result = new TaoDataModel();
		result.refresh(getNewResultSet("SELECT * FROM directories WHERE ID_Directory = "+ ID_Directory));
		return result;
	}

	public TaoDataModel getDirectory(int ID_Directory) throws Exception {
		TaoDataModel result = new TaoDataModel();
		result.refresh(getNewResultSet("SELECT * FROM directories WHERE ID_Directory = "+ ID_Directory));
		result.refresh(getNewResultSet("SELECT * FROM " + result.getData(0, "Table_Name")));
		return result;
	}
}
