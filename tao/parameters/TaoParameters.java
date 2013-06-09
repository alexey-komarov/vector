/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.parameters;

import tao.global.TaoGlobal;

public class TaoParameters {
	private String prefix = null;
	private TaoParametersCollection parameters;

	public TaoParameters(String aPrefix) {
		prefix = aPrefix + ".";
		parameters = TaoGlobal.parameters;
	}

	public TaoParameters(String aPrefix, TaoParametersCollection aParameters) {
		prefix = aPrefix + ".";
		parameters = aParameters;
	}

	public boolean getBoolean(String aKey) {
		return (Boolean)parameters.parameters.get(prefix + aKey);
	}

	public void setBoolean(String aKey, boolean aValue) {
		parameters.parameters.put(prefix + aKey, aValue);
	}

	public void setBoolean(String aKey, boolean aValue, String aDescription) {
		setBoolean(aKey, aValue);
		TaoGlobal.parameters.descr_parameters.put(prefix + aKey, aDescription);
	}

	public void setDefBoolean(String aKey, boolean aValue) {
		setBoolean(aKey, aValue);
		parameters.defParameters.put(prefix + aKey, aValue);
	}

	public void setDefBoolean(String aKey, boolean aValue, String aDescription) {
		setDefBoolean(aKey, aValue);
		TaoGlobal.parameters.descr_parameters.put(prefix + aKey, aDescription);
	}

	public String getPrefix() {
		return prefix;
	}
}
