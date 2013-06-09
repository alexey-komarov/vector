/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.config;

import java.util.*;
import java.io.*;

public class Config {
	public Exception exception = null;
	public Properties localParams = null;
	public String configFileName ;

	public Config(String filename) {
		configFileName = filename;
		localParams = new Properties();
		try {
			localParams.load(new FileInputStream(filename));
		} catch (Exception e) {
			exception = e;
			localParams = null;
		}
	}

	public void Save() {
		exception = null;
		try {
			localParams.store(new FileOutputStream(configFileName), "tao.conf");
		}
		catch (Exception e) {
			exception = e;
		}
	}
}
