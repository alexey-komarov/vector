/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import tao.FrmTaoDirectories;
import tao.icons.Nuvola;
import tao.global.TaoGlobal;
import tao.nottao.PopupMenuLayout;
import tao.dialogs.Dlg;
import tao.xml.TaoXMLFrame;
import tao.parameters.TaoParameters;

public class FrmTaoMain extends JFrame {
	private FrmTaoMainListener listener;
	private JMenuBar mbMain = new JMenuBar();
	private JDesktopPane dpMain = new JDesktopPane();
	private JPanel pnlMain = new JPanel();
	private JMenu mSetup = new JMenu("���������");
	private JMenu mDirectories = new JMenu("�����������");
	private JMenu mFile = new JMenu("����");
	private JMenu mDocs = new JMenu("�o�������");
	private AcExit acExit = new AcExit();
	private AcDirectories acDirectories = new AcDirectories();
	private AcUsers acUsers = new AcUsers();
	private AcRoles acRoles = new AcRoles();
	private AcRules acRules = new AcRules();
	private AcCustomers acCustomers = new AcCustomers();
	private AcFurniture acFurniture = new AcFurniture();
	private AcMebel acMebel = new AcMebel();
	private AcEmployers acEmployers = new AcEmployers();
	private AcOrders acOrders = new AcOrders();

	private AcInfo acInfo = new AcInfo();

	private AcSaveInterfacePreset acSaveInterfacePreset = new AcSaveInterfacePreset();
	private JFrame frm;
	private TaoXMLFrame xmlFrame;
	private TaoParameters mainprm = new TaoParameters("������� ����");

	public FrmTaoMain(String caption) {
		super(caption);

		this.setIconImage(Nuvola.apps.ll_furniture.getImage());

		javax.swing.UIManager.put("ComboBox.disabledForeground", Color.DARK_GRAY);
		javax.swing.UIManager.put("Spinner.disabledForeground", Color.DARK_GRAY);

		frm = this;
		listener = new FrmTaoMainListener(this);
		this.addWindowListener(listener);

		TaoGlobal.taskbar.setRollover(false);
		TaoGlobal.taskbar.setFloatable(false);
		TaoGlobal.taskbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		TaoGlobal.taskbar.setLayout(new PopupMenuLayout());

		TaoGlobal.infobar.setRollover(false);
		TaoGlobal.infobar.setFloatable(false);
		TaoGlobal.infobar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		TaoGlobal.infobar.setLayout(new PopupMenuLayout());

		JLabel lbUser = new JLabel(TaoGlobal.database.users.getLogin() + " (" + TaoGlobal.database.users.getShortName() + ")");
		lbUser.setFont(new Font("Verdana", Font.PLAIN, 10));
		lbUser.setForeground(new Color(100, 0, 0));

		TaoGlobal.infobar.add(lbUser);

		TaoGlobal.frmMain = this; 

		pnlMain.setLayout(new BorderLayout());
		pnlMain.add(TaoGlobal.taskbar, BorderLayout.SOUTH);
		pnlMain.add(TaoGlobal.infobar, BorderLayout.NORTH);

		pnlMain.add(dpMain, BorderLayout.CENTER);
		TaoGlobal.rootPane = dpMain;

		setContentPane(pnlMain);

		mSetup.add(acRoles);
		mSetup.add(acUsers);
		mSetup.add(acRules);
		mSetup.setIcon(Nuvola.apps.s_params);
		mFile.setIcon(Nuvola.actions.s_file);
		mDocs.setIcon(Nuvola.actions.s_documents);
		mDirectories.setIcon(Nuvola.apps.s_bookcase);

		mDirectories.add(acDirectories);
		mDirectories.add(new JSeparator());
		mDirectories.add(acCustomers);
		mDirectories.add(new JSeparator());
		mDirectories.add(acFurniture);
		mDirectories.add(acMebel);
		mDirectories.add(new JSeparator());
		mDirectories.add(acEmployers);

		mDocs.add(acOrders);

		mFile.add(acExit);
		mFile.add(acInfo);
		mbMain.add(mFile);
		mbMain.add(mDocs);
		mbMain.add(mDirectories);
		mbMain.add(mSetup);

		this.setJMenuBar(mbMain);

		setScheme();

		acDirectories.setEnabled(mainprm.getBoolean("CanDirectories"));
		acRoles.setEnabled(mainprm.getBoolean("CanRoles"));
		acUsers.setEnabled(mainprm.getBoolean("CanUsers"));
		acRules.setEnabled(mainprm.getBoolean("CanParameters"));
		acCustomers.setEnabled(mainprm.getBoolean("CanCustomers"));
		acFurniture.setEnabled(mainprm.getBoolean("CanFurniture"));
		acMebel.setEnabled(mainprm.getBoolean("CanMebel"));
		acEmployers.setEnabled(mainprm.getBoolean("CanEmployers"));
		acEmployers.setEnabled(mainprm.getBoolean("CanOrders"));
	}

	public void setScheme() {
		xmlFrame = new TaoXMLFrame(TaoGlobal.database.schemes.getScheme(frm));
		xmlFrame.applyScheme(frm);
	}

	class AcDirectories extends AbstractAction {
		AcDirectories() {
			putValue(Action.NAME, "�����������");
			putValue(Action.SHORT_DESCRIPTION, "���������� �������������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_bookcase);
		}
 
		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoDirectories = new FrmTaoDirectories("���������� �������������");
				dpMain.add(frmTaoDirectories);
				frmTaoDirectories.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ ��������� ������ �� ������������", e);
			}
		}
	}

	class AcOrders extends AbstractAction {
		AcOrders() {
			putValue(Action.NAME, "������");
			putValue(Action.SHORT_DESCRIPTION, "������ � ��������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_orders);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoOrders = new FrmTaoOrders();
				dpMain.add(frmTaoOrders);
				frmTaoOrders.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� �������", e);
			}
		}
	}

	class AcUsers extends AbstractAction {
		AcUsers() {
			putValue(Action.NAME, "������������");
			putValue(Action.SHORT_DESCRIPTION, "���������� ��������������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_users);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoUsers = new FrmTaoUsers();
				dpMain.add(frmTaoUsers);
				frmTaoUsers.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� �������������", e);
			}
		}
	}

	class AcRoles extends AbstractAction {
		AcRoles() {
			putValue(Action.NAME, "���� �������������");
			putValue(Action.SHORT_DESCRIPTION, "���������� ������ ��������������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_roles);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoRoles = new FrmTaoRoles();
				dpMain.add(frmTaoRoles);
				frmTaoRoles.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� ����� �������������", e);
			}
		}
	}

	class AcCustomers extends AbstractAction {
		AcCustomers() {
			putValue(Action.NAME, "���������");
			putValue(Action.SHORT_DESCRIPTION, "���������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_customers);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoCustomers = new FrmTaoCustomers("���������");
				dpMain.add(frmTaoCustomers);
				frmTaoCustomers.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� ����������", e);
			}
		}
	}

	class AcFurniture extends AbstractAction {
		AcFurniture() {
			putValue(Action.NAME, "���������");
			putValue(Action.SHORT_DESCRIPTION, "���������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_furniture);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoFurniture = new FrmTaoFurniture("���������");
				dpMain.add(frmTaoFurniture);
				frmTaoFurniture.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� ���������", e);
			}
		}
	}

	class AcMebel extends AbstractAction {
		AcMebel() {
			putValue(Action.NAME, "������");
			putValue(Action.SHORT_DESCRIPTION, "������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_mebel);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoMebel= new FrmTaoMebel("������");
				dpMain.add(frmTaoMebel);
				frmTaoMebel.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� ������", e);
			}
		}
	}

	class AcInfo extends AbstractAction {
		AcInfo() {
			putValue(Action.NAME, "� ���������");
			putValue(Action.SHORT_DESCRIPTION, "���������� � ���������");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_messagebox_info);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				String a ="<hr><p><font size=4><center>��������� �������� ��������� <i>(� �������� ���������� �� Java)</i> ���������� ��� <b>��� \"������-������\"</b></center></font></p><hr>";
				FrmTaoChild frmTaoPrint = new FrmTaoPrint(a);
				TaoGlobal.rootPane.add(frmTaoPrint);
				frmTaoPrint.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ����", e);
			}
		}
	}

	class AcEmployers extends AbstractAction {
		AcEmployers() {
			putValue(Action.NAME, "����������");
			putValue(Action.SHORT_DESCRIPTION, "����������");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_employers);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoEmployers= new FrmTaoEmployers("����������");
				dpMain.add(frmTaoEmployers);
				frmTaoEmployers.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ������� �����������", e);
			}
		}
	}

	class AcRules extends AbstractAction {
		AcRules() {
			putValue(Action.NAME, "���������");
			putValue(Action.SHORT_DESCRIPTION, "��������� ����������");
			putValue(Action.SMALL_ICON, Nuvola.apps.s_params);
		}

		public void actionPerformed(ActionEvent event) {
			try {
				FrmTaoChild frmTaoRules = new FrmTaoRules();
				dpMain.add(frmTaoRules);
				frmTaoRules.setVisible(true);
			} catch(Exception e) {
				Dlg.error("������ �������� ���� ��������� ����������", e);
			}
		}
	}

	class AcExit extends AbstractAction {
		AcExit() {
			putValue(Action.NAME, "�����");
			putValue(Action.SHORT_DESCRIPTION, "����� �� ���������");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_exit);
			putValue(Action.LARGE_ICON_KEY, Nuvola.actions.l_exit);
		}

		public void actionPerformed(ActionEvent event) {
			acSaveInterfacePreset.actionPerformed(null);
			TaoGlobal.cleartempdir();
			System.exit(0);
		}
	}

	class AcSaveInterfacePreset extends AbstractAction {
		AcSaveInterfacePreset() {
			putValue(Action.NAME, "��������� ��������� ����");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_save);
			putValue(Action.LARGE_ICON_KEY, Nuvola.actions.l_save);
		}

		public void actionPerformed(ActionEvent event) {
			TaoGlobal.database.schemes.saveAndSetScheme(frm);
			processTaoChildSave(frm);
		}

		private void processTaoChildSave(Container cnt) {
			FrmTaoChild childFrm;
			for (int k = 0; k < cnt.getComponentCount(); k++) {
				if (cnt.getComponent(k) instanceof FrmTaoChild) {
					childFrm = (FrmTaoChild)cnt.getComponent(k);
					childFrm.saveFrame();
				} else if (cnt.getComponent(k) instanceof Container)
					processTaoChildSave((Container)cnt.getComponent(k));
			}
		}
	}

	class FrmTaoMainListener extends WindowAdapter {
		private JFrame frmParent;

		public FrmTaoMainListener(JFrame frm) {
			frmParent = frm;
		}

		public void windowClosing(WindowEvent e) {
			acSaveInterfacePreset.actionPerformed(null);
			TaoGlobal.cleartempdir();
			System.exit(0);
		}
	}
}
