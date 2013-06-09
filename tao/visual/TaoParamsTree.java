/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.visual;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import tao.global.TaoGlobal;
import java.util.Enumeration;
import java.util.Arrays;
import java.util.Hashtable;

import tao.parameters.TaoParametersCollection;
import tao.dialogs.Dlg;

import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicTreeUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.tree.TreePath;
import tao.database.TaoStoredProc;

public class TaoParamsTree extends JTree{
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Параметры");
	private TaoParametersCollection parameters = new TaoParametersCollection();
	private TaoParamsTree tree;
	private Hashtable<DefaultMutableTreeNode, String> nodeParams = new Hashtable<DefaultMutableTreeNode, String>();
	private int ID_Role = -1;

	public JScrollPane scrTree;

	public TaoParamsTree(int aID_Role) throws Exception {
		ID_Role = aID_Role;
		parameters.loadRoleParameters(ID_Role);
		scrTree = new JScrollPane(this);
		tree = this;
		this.setModel(new DefaultTreeModel(rootNode));
		DefaultMutableTreeNode node = null;
		DefaultMutableTreeNode nnode = null;
		String key = null;
		String[] skey = null;
		String value = "";
		boolean boolValue = false;

		Enumeration e = TaoGlobal.parameters.descr_parameters.keys();

		while (e.hasMoreElements()) {
			key = e.nextElement().toString();
			skey = key.split("\\.");
			for (int k = 0; k < skey.length - 1; k++) {
				if (k == 0)
					node = rootNode;

				nnode = findNode(node, skey[k]);

				if (nnode == null) {
					nnode = new DefaultMutableTreeNode(skey[k]);
					node.add(nnode);
				}
				node = nnode;
			}

			if (parameters.parameters.get(key) != null) {
				boolValue = ((Boolean)parameters.parameters.get(key)).booleanValue();

				if (boolValue)
					value = ": <font color=#009000>Да</font>";
				else
					value = ": <font color=#900000>Нет</font>";
			} else {
				boolValue = ((Boolean)TaoGlobal.parameters.defParameters.get(key)).booleanValue();
				value = ": <font color=#000090>" + (boolValue ? "Да" : "Нет") + " [по умолчанию]</font>";
			}

			nnode = new DefaultMutableTreeNode("<html>" + TaoGlobal.parameters.descr_parameters.get(key) + value + "</html>");
			nodeParams.put(nnode, key);
			node.add(nnode);
		}

		this.expandRow(0);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if ((tree.getSelectionRows() != null) && (tree.getSelectionRows().length == 1)) {
						DefaultMutableTreeNode slnode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

						if ((slnode != null) && (slnode.isLeaf())) {
							int value = 0; //default
							String key = nodeParams.get(slnode);

							if (parameters.parameters.get(key) != null) {
								if (((Boolean)parameters.parameters.get(key)).booleanValue())
									value = 1;
								else
									value = 2;
							}

							int result = Dlg.param_BoolValue(key, value);
							String newValue = null;

							if (result >= 0) {
								try {
									TaoStoredProc sp = new TaoStoredProc("setBooleanParam", 3);
									sp.params.addElement(tree.ID_Role);
									sp.params.addElement(key);
									sp.params.addElement(result);
									sp.execute();

									if (result == 1) {
										newValue = ": <font color=#009000>Да</font>";
										parameters.parameters.put(key, true);
									}

									if (result == 2) {
										newValue = ": <font color=#900000>Нет</font>";
										parameters.parameters.put(key, false);
									}

									if (result == 0) {
										boolean boolValue = ((Boolean)TaoGlobal.parameters.defParameters.get(key)).booleanValue();
										newValue = ": <font color=#000090>" + (boolValue ? "Да" : "Нет") + " [по умолчанию]</font>";
										parameters.parameters.remove(key);
									}

									slnode.setUserObject("<html>" + TaoGlobal.parameters.descr_parameters.get(key)  + newValue + "</html>");
								} catch (Exception ex) {
									Dlg.error("Ошибка сохранения параметра", ex);
								}
							}
						}
					}
				}
			}
		});
	}

	public DefaultMutableTreeNode findNode(DefaultMutableTreeNode node, String text) {
		Enumeration ne = node.children();
		DefaultMutableTreeNode result = null;

		while (ne.hasMoreElements()) {
			result = (DefaultMutableTreeNode)ne.nextElement();

			if (result.getUserObject().toString().equals(text))
				break;
			else
				result = null;
		}
		return result;
	}
}
