/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.database.subsystem;

import java.awt.Component;

import tao.database.TaoDatabase;
import tao.database.TaoStoredProc;
import tao.global.TaoGlobal;

public class TaoVisualSubSystem extends TaoDatabase {
	public void schemeStore(String aID_Scheme, String aXML) throws Exception {
		TaoStoredProc sp = new TaoStoredProc("setScheme", 3);
		sp.params.addElement(aID_Scheme);
		sp.params.addElement(aXML);
		sp.params.addElement(TaoGlobal.database.users.getID_User());
		sp.execute();
	}
}
