/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.util.*;
import java.text.SimpleDateFormat;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import javax.swing.event.*;

import tao.FrmTaoChild;
import tao.global.TaoGlobal;
import tao.icons.Nuvola;
import tao.dialogs.Dlg;
import tao.database.TaoDataModel;

import tao.visual.*;
import tao.database.TaoStoredProc;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;
import java.util.Enumeration;
import tao.parameters.TaoParameters;
import tao.nottao.PopupMenuLayout;

public class FrmOrder extends FrmTaoChild {
	private JPanel pnlBottom = new JPanel();
	private JPanel pnlMain = new JPanel();
	private TaoPanel pnlDateFields = new TaoPanel();
	private TaoPanel pnlMainFields = new TaoPanel();
	private TaoPanel pnlAltFields = new TaoPanel();
	private JPanel pnlTop = new JPanel();

	private Box col1 = Box.createVerticalBox();
	private Box col2 = Box.createVerticalBox();
	private Box col3 = Box.createVerticalBox();
	private Box bxDateFields = Box.createHorizontalBox();
	private Box bxMainFields = Box.createHorizontalBox();
	private Box bxAltFields = Box.createHorizontalBox();
	private Box bxClientFields1 = Box.createHorizontalBox();

	private TaoIntegerField fNumber = new TaoIntegerField();
	private JLabel fDate_Order_Create = new JLabel(TaoGlobal.currDate());
	private JLabel fDate_Order_Finish = new JLabel("Не указана");
	private TaoDatePicker fDate_Order_Begin = new TaoDatePicker();
	private TaoDatePicker fDate_Order_End = new TaoDatePicker();
	private TaoDatePicker fDate_Shiping_Wanting = new TaoDatePicker();
	private TaoDatePicker fDate_Shiping_Max = new TaoDatePicker();
	private TaoDBComboBox fStatus = new TaoDBComboBox();
	private CustomerRecord fCustomer = new CustomerRecord();
	private EmployeeRecord fManager = new EmployeeRecord();
	private Address2 fAddress2 = new Address2();
	private Phone fPhone = new Phone();
	private Lift fLift = new Lift();
	private Floor fFloor = new Floor();
	private Contact fContact = new Contact();
	private TaoIntegerField fQuantity = new TaoIntegerField();

	private TaoFloatField fSum = new TaoFloatField();
	private TaoFloatField fPrepay = new TaoFloatField();

	private JPanel pNumber = new JPanel();
	private JPanel pStatus = new JPanel();
	private JPanel pCustomer = new JPanel();
	private JPanel pDate_Order_Create = new JPanel();
	private JPanel pDate_Order_Finish = new JPanel();
	private JPanel pDate_Order_Begin = new JPanel();
	private JPanel pDate_Order_End = new JPanel();
	private JPanel pDate_Shiping_Wanting = new JPanel();
	private JPanel pDate_Shiping_Max = new JPanel();
	private JPanel pManager = new JPanel();
	private JPanel pAddress2 = new JPanel();
	private JPanel pSum = new JPanel();
	private JPanel pPrepay = new JPanel();
	private JPanel pPhone = new JPanel();
	private JPanel pContact = new JPanel();
	private JPanel pQuantity = new JPanel();
	private JPanel pFloor = new JPanel();
	private JPanel pLift = new JPanel();
	private JPanel pnlSplit = new JPanel();

	private String h1 = "<html><font color=#507060><i>";
	private String h2 = "</i></font></html>";
	private JLabel lNumber = new JLabel(h1 + "Номер: " + h2, SwingConstants.LEFT);
	private JLabel lCustomer = new JLabel(h1 + "Заказчик: " + h2, SwingConstants.LEFT);
	private JLabel lStatus = new JLabel(h1 + "Статус: " + h2, SwingConstants.LEFT);
	private JLabel lDate_Order_Create = new JLabel(h1 + "Дата создания заказа: " + h2);
	private JLabel lDate_Order_Finish = new JLabel(h1 + "Дата закрытия заказа: " + h2);
	private JLabel lDate_Order_Begin = new JLabel(h1 + "Дата дачи в работу: " + h2);
	private JLabel lDate_Order_End = new JLabel(h1 + "Дата готовности заказа: " + h2);
	private JLabel lDate_Shiping_Wanting = new JLabel(h1 + "Дата доставки желаемая: " + h2);
	private JLabel lDate_Shiping_Max = new JLabel(h1 + "Дата доставки обязательная: " + h2);
	private JLabel lManager = new JLabel(h1 + "Ответственный: " + h2);
	private JLabel lAddress2 = new JLabel(h1 + "Адрес доставки: " + h2);
	private JLabel lSum = new JLabel(h1 + "Cумма заказа: " + h2, SwingConstants.LEFT);
	private JLabel lPrepay = new JLabel(h1 + "Предоплата: " + h2, SwingConstants.LEFT);
	private JLabel lPhone = new JLabel(h1 + "Телефон: " + h2, SwingConstants.LEFT);
	private JLabel lFloor = new JLabel(h1 + "Этаж: " + h2, SwingConstants.LEFT);
	private JLabel lLift = new JLabel(h1 + "Лифт: " + h2, SwingConstants.LEFT);
	private JLabel lContact = new JLabel(h1 + "Контактное лицо заказчика: " + h2, SwingConstants.LEFT);
	private JLabel lQuantity = new JLabel(h1 + "Кол-во: " + h2, SwingConstants.LEFT);

	private JTabbedPane tpOrder = new JTabbedPane();
	private int typeOrder = 1;

	private FrmOrderListener listener = new FrmOrderListener();

	private PnlMebel pnlMebel = new PnlMebel(false);
	private PnlMebel pnlMebelAlt = new PnlMebel(true);
	private PnlFurniture pnlFurniture = new PnlFurniture();
	private PnlOrderBaseMain pnlOrderData = new PnlOrderBaseMain();
	private PnlOrderBaseText pnlOrderText = new PnlOrderBaseText();
	private PnlOrderHomeMain pnlOrderHomeData = new PnlOrderHomeMain();
	private PnlOrderKitchenMaterial pnlOrderKitchenMaterial = new PnlOrderKitchenMaterial();
	private PnlOrderKitchenFurniture pnlOrderKitchenFurniture = new PnlOrderKitchenFurniture();
	private PnlSanitary pnlSanitary = new PnlSanitary();
	private PnlOrderKitchenText pnlOrderKitchenText = new PnlOrderKitchenText();
	private PnlImages pnlImages = new PnlImages();

	private AbstractAction closeAction;
	private int ID_Order = -1;
	public FrmOrder(String aTitle, int aTypeOrder, AbstractAction aCloseAction) {
		super(aTitle, Nuvola.apps.s_orders);
		typeOrder = aTypeOrder;

		fLift.setLift(false);

		switch(typeOrder) {
			case 2:
				this.setFrameIcon(Nuvola.apps.s_home);
				break;
			case 3:
				this.setFrameIcon(Nuvola.apps.s_kitchen);
		}

		fCustomer.acAfterSelect = new AcAfterCustomerSelect();
		closeAction = aCloseAction;
		Box bxButtons = Box.createHorizontalBox();
		bxButtons.setBorder(new EmptyBorder(4, 4, 4, 4));

		AcOk acOk = new AcOk();
		AcCancel acCancel = new AcCancel();

		JButton btnOk = new JButton(acOk);
		JButton btnCancel = new JButton(acCancel);

		bxButtons.add(Box.createHorizontalGlue());
		bxButtons.add(btnOk);
		bxButtons.add(Box.createHorizontalStrut(10));
		bxButtons.add(btnCancel);

		int ID_Employee = TaoGlobal.database.users.getID_Employee();
		if (ID_Employee != -1)
			fManager.setValue(ID_Employee, TaoGlobal.database.users.getEmployee());

		tpOrder.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane)evt.getSource();
				int sel = pane.getSelectedIndex();
				switch(sel) {
					case 1:
						if (typeOrder == 1)
							pnlMebel.initHotKeys();
						if (typeOrder == 2) 
							pnlFurniture.initHotKeys();
						break;
					case 2:
						if (typeOrder == 1)
							pnlFurniture.initHotKeys();
						if (typeOrder == 2)
							pnlFurniture.initHotKeys();
						break;
					case 3:
						if (typeOrder == 1)
								pnlMebelAlt.initHotKeys();
						if (typeOrder == 3)
								pnlSanitary.initHotKeys();
					case 4:
						if (typeOrder == 2)
								pnlImages.initHotKeys();
						break;
					case 5:
						if ((typeOrder == 1) || (typeOrder == 3))
								pnlImages.initHotKeys();
						break;
					default:
						clearHotKeys();
					break;
				}
			}
		});

		pNumber.setLayout(new BorderLayout());
		pDate_Order_Create.setLayout(new BorderLayout());
		pDate_Order_Begin.setLayout(new BorderLayout());
		pDate_Order_End.setLayout(new BorderLayout());
		pDate_Shiping_Wanting.setLayout(new BorderLayout());
		pDate_Shiping_Max.setLayout(new BorderLayout());
		pCustomer.setLayout(new BorderLayout());
		pStatus.setLayout(new BorderLayout());
		pDate_Order_Finish.setLayout(new BorderLayout());
		pManager.setLayout(new BorderLayout());
		pAddress2.setLayout(new BorderLayout());
		pSum.setLayout(new BorderLayout());
		pPrepay.setLayout(new BorderLayout());
		pPhone.setLayout(new BorderLayout());
		pLift.setLayout(new BorderLayout());
		pContact.setLayout(new BorderLayout());
		pFloor.setLayout(new BorderLayout());
		pQuantity.setLayout(new BorderLayout());

		pnlBottom.setLayout(new BorderLayout());
		pnlDateFields.setLayout(new BorderLayout());
		pnlMainFields.setLayout(new BorderLayout());
		pnlAltFields.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlMain.setLayout(new BorderLayout());

		this.getContentPane().add(pnlMain);

		fStatus.setEditable(false);
		try {
			fStatus.setSource(TaoGlobal.database.query.getNewResultSet("SELECT * FROM order_statuses"));
		} catch (Exception e) {
			Dlg.error("Ошибка чтения справочника статусов заказа", e);
		}

		pNumber.add(lNumber, BorderLayout.NORTH);
		pNumber.add(fNumber, BorderLayout.CENTER);

		pStatus.add(lStatus, BorderLayout.NORTH);
		pStatus.add(fStatus, BorderLayout.CENTER);

		pQuantity.add(lQuantity, BorderLayout.NORTH);
		pQuantity.add(fQuantity, BorderLayout.CENTER);

		pCustomer.add(lCustomer, BorderLayout.NORTH);
		pCustomer.add(fCustomer, BorderLayout.CENTER);

		pDate_Order_Finish.add(lDate_Order_Finish, BorderLayout.NORTH);
		pDate_Order_Finish.add(fDate_Order_Finish, BorderLayout.CENTER);

		pDate_Order_Create.add(lDate_Order_Create, BorderLayout.NORTH);
		pDate_Order_Create.add(fDate_Order_Create, BorderLayout.CENTER);

		pDate_Order_Begin.add(lDate_Order_Begin, BorderLayout.NORTH);
		pDate_Order_Begin.add(fDate_Order_Begin.getPanel(), BorderLayout.CENTER);

		pDate_Order_End.add(lDate_Order_End, BorderLayout.NORTH);
		pDate_Order_End.add(fDate_Order_End.getPanel(), BorderLayout.CENTER);

		pDate_Shiping_Wanting.add(lDate_Shiping_Wanting, BorderLayout.NORTH);
		pDate_Shiping_Wanting.add(fDate_Shiping_Wanting.getPanel(), BorderLayout.CENTER);

		pSum.add(lSum, BorderLayout.NORTH);
		pSum.add(fSum, BorderLayout.CENTER);

		pPrepay.add(lPrepay, BorderLayout.NORTH);
		pPrepay.add(fPrepay, BorderLayout.CENTER);

		pDate_Shiping_Max.add(lDate_Shiping_Max, BorderLayout.NORTH);
		pDate_Shiping_Max.add(fDate_Shiping_Max.getPanel(), BorderLayout.CENTER);

		pManager.add(lManager, BorderLayout.NORTH);
		pManager.add(fManager, BorderLayout.CENTER);

		pAddress2.add(lAddress2, BorderLayout.NORTH);
		pAddress2.add(fAddress2, BorderLayout.CENTER);

		pFloor.add(lFloor, BorderLayout.NORTH);
		pFloor.add(fFloor, BorderLayout.CENTER);

		pContact.add(lContact, BorderLayout.NORTH);
		pContact.add(fContact, BorderLayout.CENTER);

		pLift.add(lLift, BorderLayout.NORTH);
		pLift.add(fLift, BorderLayout.CENTER);

		pPhone.add(lPhone, BorderLayout.NORTH);
		pPhone.add(fPhone, BorderLayout.CENTER);

		pNumber.setMaximumSize(new Dimension(60, 40));
		pStatus.setMaximumSize(new Dimension(180, 40));
		pStatus.setPreferredSize(new Dimension(180, 40));

		pnlSplit.setMaximumSize(new Dimension(0, 12));
		pnlSplit.setPreferredSize(new Dimension(0, 12));

		pSum.setPreferredSize(new Dimension(100, 40));
		pPrepay.setPreferredSize(new Dimension(100, 40));
		pSum.setMaximumSize(new Dimension(100, 40));
		pPrepay.setMaximumSize(new Dimension(100, 40));

		pManager.setMaximumSize(new Dimension(150, 40));
		pManager.setPreferredSize(new Dimension(290, 40));

		pFloor.setMaximumSize(new Dimension(100, 40));
		pFloor.setPreferredSize(new Dimension(100, 40));
		pLift.setMaximumSize(new Dimension(160, 40));
		pLift.setPreferredSize(new Dimension(160, 40));

		pPhone.setMaximumSize(new Dimension(200, 40));
		pPhone.setPreferredSize(new Dimension(200, 40));

		pQuantity.setMaximumSize(new Dimension(60, 40));
		pQuantity.setPreferredSize(new Dimension(60, 40));

		col1.add(pDate_Order_Create);
		col1.add(Box.createVerticalStrut(10));
		col1.add(pDate_Order_Finish);

		col2.add(pDate_Order_Begin);
		col2.add(Box.createVerticalStrut(10));
		col2.add(pDate_Order_End);

		col3.add(pDate_Shiping_Wanting);
		col3.add(Box.createVerticalStrut(10));
		col3.add(pDate_Shiping_Max);

		bxDateFields.add(col1);
		bxDateFields.add(Box.createHorizontalStrut(10));
		bxDateFields.add(col2);
		bxDateFields.add(Box.createHorizontalStrut(10));
		bxDateFields.add(col3);

		bxMainFields.add(pStatus);
		bxMainFields.add(Box.createHorizontalStrut(10));
		bxMainFields.add(pNumber);
		bxMainFields.add(Box.createHorizontalStrut(10));
		bxMainFields.add(pCustomer);

		bxAltFields.add(pManager);
		bxAltFields.add(Box.createHorizontalStrut(10));
		bxAltFields.add(pAddress2);
		bxAltFields.add(Box.createHorizontalStrut(10));
		bxAltFields.add(pSum);
		bxAltFields.add(Box.createHorizontalStrut(10));
		bxAltFields.add(pPrepay);

		bxClientFields1.add(pFloor);
		bxClientFields1.add(Box.createHorizontalStrut(10));
		bxClientFields1.add(pLift);
		bxClientFields1.add(Box.createHorizontalStrut(10));
		bxClientFields1.add(pPhone);
		bxClientFields1.add(Box.createHorizontalStrut(10));
		bxClientFields1.add(pContact);

		if (aTypeOrder > 2) {
			bxAltFields.add(Box.createHorizontalStrut(10));
			bxAltFields.add(pQuantity);
		}

		pnlMainFields.add(bxMainFields, BorderLayout.CENTER);
		pnlDateFields.add(bxDateFields, BorderLayout.CENTER);

		pnlAltFields.add(bxAltFields, BorderLayout.NORTH);
		pnlAltFields.add(pnlSplit, BorderLayout.CENTER);
		pnlAltFields.add(bxClientFields1, BorderLayout.SOUTH);

		pnlTop.add(pnlMainFields, BorderLayout.NORTH);
		pnlTop.add(pnlDateFields, BorderLayout.CENTER);
		pnlTop.add(pnlAltFields, BorderLayout.SOUTH);

		pnlMain.add(pnlTop, BorderLayout.NORTH);
		pnlMain.add(pnlBottom, BorderLayout.CENTER);
		pnlMain.add(bxButtons, BorderLayout.SOUTH);

		try {
			fNumber.setValue(TaoGlobal.database.order.getNextNumber());
		} catch (Exception e) {
			Dlg.error("Ошибка получения номера заказа", e);
		}

		if (typeOrder == 1) {
			tpOrder.addTab("Данные заказа", Nuvola.actions.s_sorder, pnlOrderData, "Данные заказа");
			tpOrder.addTab("Изделия", Nuvola.apps.s_mebel, pnlMebel, "Список изделий");
			tpOrder.addTab("Дополнительная Фурнитура", Nuvola.apps.s_furniture, pnlFurniture, "Список фурнитуры");
			tpOrder.addTab("Дополнительные изделия", Nuvola.apps.s_alt, pnlMebelAlt, "Список дополнительных изделий");
			tpOrder.addTab("Дополнительные сведения", Nuvola.apps.s_info, pnlOrderText, "Дополнительные сведения");
			tpOrder.addTab("Файлы", Nuvola.actions.s_attach, pnlImages, "Файлы");
		}

		if (typeOrder == 2) {
			tpOrder.addTab("Данные заказа", Nuvola.actions.s_sorder, pnlOrderHomeData, "Данные заказа");
			tpOrder.addTab("Изделия", Nuvola.apps.s_mebel, pnlMebel, "Список изделий");
			tpOrder.addTab("Дополнительная Фурнитура", Nuvola.apps.s_furniture, pnlFurniture, "Список фурнитуры");
			tpOrder.addTab("Дополнительные сведения", Nuvola.apps.s_info, pnlOrderText, "Дополнительные сведения");
			tpOrder.addTab("Файлы", Nuvola.actions.s_attach, pnlImages, "Файлы");
		}

		if (typeOrder == 3) {
			tpOrder.addTab("Модель/Материал/Цвет", Nuvola.actions.s_color, pnlOrderKitchenMaterial, "Цвет/материал");
			tpOrder.addTab("Фурнитура", Nuvola.apps.s_furniture, pnlOrderKitchenFurniture, "Основной список фурнитуры");
			tpOrder.addTab("Дополнительная Фурнитура", Nuvola.apps.s_furniture, pnlFurniture, "Список фурнитуры");
			tpOrder.addTab("Сантехника", Nuvola.fs.s_sanitary, pnlSanitary, "Сантехника");
			tpOrder.addTab("Дополнительные сведения", Nuvola.apps.s_info, pnlOrderKitchenText, "Дополнительные сведения");
			tpOrder.addTab("Файлы", Nuvola.actions.s_attach, pnlImages, "Файлы");
		}

		pnlBottom.add(tpOrder, BorderLayout.CENTER);
		this.addInternalFrameListener(listener);

		pack();
	}

	public void load(int aID_Order) throws Exception {
		Object obj;
		String fd;
		TaoDataModel order1 = new TaoDataModel();
		order1.refresh(TaoGlobal.database.query.getNewResultSet("SELECT * FROM vorders WHERE ID_Order = '" + aID_Order + "'"));
		ID_Order = aID_Order;
		fNumber.setValue(order1.getData(0, "Number"));

		fDate_Order_Create.setText(TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Create")));

		try {
			fd = TaoGlobal.stringSQLDate((java.sql.Date)order1.getData(0, "Date_Order_Finish"));
			fDate_Order_Finish.setText(fd);
		} catch(Exception e) {};

		try {
			obj = order1.getData(0, "ID_Customer");
			if (obj instanceof Integer)
				fCustomer.setValue((Integer)obj, order1.getData(0, "Customer").toString());
		} catch (Exception e) {};

		if (typeOrder > 1)
		try {
			obj = order1.getData(0, "Quantity");
			if (obj instanceof Integer)
				fQuantity.setValue((Integer)obj);
		} catch (Exception e) {};

		try {
			fDate_Order_Begin.off();
			fDate_Order_Begin.setDate((java.sql.Date)order1.getData(0, "Date_Order_Begin"));
			fDate_Order_Begin.on();
		} catch(Exception e) {};

		try {
			fDate_Order_End.off();
			fDate_Order_End.setDate((java.sql.Date)order1.getData(0, "Date_Order_End"));
			fDate_Order_End.on();
		} catch(Exception e) {};

		fStatus.setValue(order1.getData(0, "ID_Order_Status"));

		try {
			fDate_Shiping_Wanting.off();
			fDate_Shiping_Wanting.setDate((java.sql.Date)order1.getData(0, "Date_Shiping_Wanting"));
			fDate_Shiping_Wanting.on();
		} catch(Exception e) {};

		try {
			fDate_Shiping_Max.off();
			fDate_Shiping_Max.setDate((java.sql.Date)order1.getData(0, "Date_Shiping_Max"));
			fDate_Shiping_Max.on();
		} catch(Exception e) {};

		try {
			obj = order1.getData(0, "ID_Employee");
			if (obj instanceof Integer)
			fManager.setValue((Integer)obj, order1.getData(0, "mFamily") + " " +
			                   order1.getData(0, "mName") + " " +
			                   order1.getData(0, "mPatronymic"));
			else
				fManager.setValue(-1, "");
		} catch (Exception e) {}

		fAddress2.setAddress(order1.getData(0, "Address").toString());
		typeOrder = (Integer)order1.getData(0, "ID_Order_Type");

		try {
			fPhone.setPhone(order1.getData(0, "Phone").toString());
		} catch(Exception e) {};

		try {
			fContact.setContact(order1.getData(0, "Contact").toString());
		} catch(Exception e) {};

		try {
			fLift.setLift((Boolean)order1.getData(0, "Lift"));
		} catch(Exception e) {};

		try {
			fFloor.setFloor((Integer)order1.getData(0, "Floor"));
		} catch(Exception e) {};

		try {
			fSum.setValue((Float)order1.getData(0, "Sum"));
		} catch(Exception e) {};

		try {
			fPrepay.setValue((Float)order1.getData(0, "Prepay"));
		} catch(Exception e) {};

		if (typeOrder == 1) {
			try {
				pnlOrderData.load(ID_Order);
				pnlOrderText.load(ID_Order, 1);
				pnlFurniture.load(ID_Order);
				pnlMebel.load(ID_Order); 
				pnlMebelAlt.load(ID_Order); 
				pnlImages.load(ID_Order); 
			} catch (Exception e) {
				Dlg.error("Ошибка загрузки данных заказа", e);
			}
		}

		if (typeOrder == 2) {
			try {
				pnlOrderHomeData.load(ID_Order);
				pnlOrderText.load(ID_Order, 2);
				pnlMebel.load(ID_Order);
				pnlFurniture.load(ID_Order);
				pnlImages.load(ID_Order); 
			} catch (Exception e) {
				Dlg.error("Ошибка загрузки данных заказа", e);
			}
		}

		if (typeOrder == 3) {
			try {
				pnlOrderKitchenMaterial.load(ID_Order);
				pnlOrderKitchenFurniture.load(ID_Order);
				pnlFurniture.load(ID_Order);
				pnlSanitary.load(ID_Order);
				pnlOrderKitchenText.load(ID_Order);
				pnlImages.load(ID_Order); 
			} catch (Exception e) {
				Dlg.error("Ошибка загрузки данных заказа", e);
			}
		}
	}

	public void clearHotKeys() {
		InputMap iMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insert");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");

		ActionMap aMap = this.getRootPane().getActionMap();

		aMap.put("insert", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {}
		});

		aMap.put("delete", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {}
		});
	}

	private class Address2 extends JPanel {
		private JButton btnSelect = new JButton();
		private JTextField fAddress2 = new JTextField();
		private AcSet acSet = new AcSet();

		Address2() {
			this.setLayout(new BorderLayout());
			this.add(btnSelect, BorderLayout.EAST);
			this.add(fAddress2, BorderLayout.CENTER);
			btnSelect.setAction(acSet);
		}

		public String getAddress() {
			return fAddress2.getText();
		}

		public void setAddress(String aAddress) {
			fAddress2.setText(aAddress);
		}

		class AcSet extends AbstractAction {
			AcSet() {
				putValue(Action.NAME,"");
				putValue(Action.SMALL_ICON, Nuvola.actions.s_back);
				putValue(Action.SHORT_DESCRIPTION, "Адрес клиента");
			}

			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Address FROM customers WHERE ID_Customer = " + fCustomer.getID());
					rs.first();

					if (rs.getObject(1) != null)
						fAddress2.setText(rs.getObject(1).toString());
				} catch (Exception e) { }
			}
		}
	}

	private class Phone extends JPanel {
		private JButton btnSelect = new JButton();
		private JTextField fPhone = new JTextField();
		private AcSet acSet = new AcSet();

		Phone() {
			this.setLayout(new BorderLayout());
			this.add(btnSelect, BorderLayout.EAST);
			this.add(fPhone, BorderLayout.CENTER);
			btnSelect.setAction(acSet);
		}

		public String getPhone() {
			return fPhone.getText();
		}

		public void setPhone(String aPhone) {
			fPhone.setText(aPhone);
		}

		class AcSet extends AbstractAction {
			AcSet() {
				putValue(Action.NAME,"");
				putValue(Action.SMALL_ICON, Nuvola.actions.s_back);
				putValue(Action.SHORT_DESCRIPTION, "Телефон клиента");
			}

			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Phone FROM customers WHERE ID_Customer = " + fCustomer.getID());
					rs.first();
					fPhone.setText(rs.getObject(1).toString());
				} catch (Exception e) { }
			}
		}
	}

	private class Contact extends JPanel {
		private JButton btnSelect = new JButton();
		private JTextField fContact = new JTextField();
		private AcSet acSet = new AcSet();

		Contact() {
			this.setLayout(new BorderLayout());
			this.add(btnSelect, BorderLayout.EAST);
			this.add(fContact, BorderLayout.CENTER);
			btnSelect.setAction(acSet);
		}

		public String getContact() {
			return fContact.getText();
		}

		public void setContact(String aAddress)
		{
			fContact.setText(aAddress);
		}

		class AcSet extends AbstractAction {
			AcSet() {
				putValue(Action.NAME,"");
				putValue(Action.SMALL_ICON, Nuvola.actions.s_back);
				putValue(Action.SHORT_DESCRIPTION, "Контактное лицо заказчика");
			}

			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Contact FROM customers WHERE ID_Customer = " + fCustomer.getID());
					rs.first();
					if (rs.getObject(1) != null)
						fContact.setText(rs.getObject(1).toString());
					} catch (Exception e) {
				}
			}
		}
	}

	private class Floor extends JPanel {
		private JButton btnSelect = new JButton();
		private TaoIntegerField fFloor = new TaoIntegerField();
		private AcSet acSet = new AcSet();

		Floor() {
			this.setLayout(new BorderLayout());
			this.add(btnSelect, BorderLayout.EAST);
			this.add(fFloor, BorderLayout.CENTER);
			btnSelect.setAction(acSet);
		}

		public Integer getFloor() {
			return (Integer)fFloor.getValue();
		}

		public void setFloor(Integer aFloor) {
			fFloor.setValue(aFloor);
		}

		class AcSet extends AbstractAction {
			AcSet() {
				putValue(Action.NAME,"");
				putValue(Action.SMALL_ICON, Nuvola.actions.s_back);
				putValue(Action.SHORT_DESCRIPTION, "Этажность клиента");
			}

			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Floor FROM customers WHERE ID_Customer = " + fCustomer.getID());
					rs.first();
					fFloor.setValue(rs.getObject(1));
				} catch (Exception e) {}
			}
		}
	}

	private class Lift extends JPanel {
		private JButton btnSelect = new JButton();
		private TaoBooleanField fLift = new TaoBooleanField();
		private AcSet acSet = new AcSet();

		Lift() {
			this.setLayout(new BorderLayout());
			this.add(btnSelect, BorderLayout.EAST);
			this.add(fLift, BorderLayout.CENTER);
			btnSelect.setAction(acSet);
		}

		public boolean getLift() {
			return fLift.getValue();
		}

		public void setLift(Boolean aLift) {
			fLift.setValue(aLift);
		}

		class AcSet extends AbstractAction {
			AcSet() {
				putValue(Action.NAME, "");
				putValue(Action.SMALL_ICON, Nuvola.actions.s_back);
				putValue(Action.SHORT_DESCRIPTION, "Наличие лифта у клиента");
			}
 
			public void actionPerformed(ActionEvent event) {
				try {
					ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Lift FROM customers WHERE ID_Customer = " + fCustomer.getID());
					rs.first();
					fLift.setValue((Boolean)rs.getObject(1));
				} catch (Exception e) {}
			}
		}
	}

	class FrmOrderListener extends InternalFrameAdapter {
		public void internalFrameClosed(InternalFrameEvent e) {
			pnlMebel.dispose();
			pnlMebelAlt.dispose();
			pnlFurniture.dispose();
		}
	}

	class AcOk extends AbstractAction {
		AcOk() {
			putValue(Action.NAME, "Ок");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_ok);
		}

		public void actionPerformed(ActionEvent event) {
			if (fCustomer.getID() == -1) {
				Dlg.error("Не выбран клиент!");
				return;
			}

			TaoGlobal.startWaitCursor();

			try {
				if (ID_Order == -1)
					insert();
				else
					update();

				if (closeAction != null)
					closeAction.actionPerformed(null);

				dispose();
			} catch (Exception e) {
				Dlg.error("Ошибка сохранения заказа", e);
			} finally {
				TaoGlobal.stopWaitCursor();
			}
		}

		public void setFields(Properties p) {
			p.setProperty("Number", fNumber.getValue().toString());
			p.setProperty("Date_Order_Create", TaoGlobal.currSQLDate());
			p.setProperty("Date_Order_Modify", TaoGlobal.currSQLDate());
			p.setProperty("Date_Order_Begin", fDate_Order_Begin.getSQL());
			p.setProperty("Date_Order_End", fDate_Order_End.getSQL());
			p.setProperty("Date_Shiping_Wanting", fDate_Shiping_Wanting.getSQL());
			p.setProperty("Date_Shiping_Max", fDate_Shiping_Max.getSQL());
			p.setProperty("ID_Customer", fCustomer.getID() + "");

			if (typeOrder > 1)
				p.setProperty("Quantity", fQuantity.getValue() + "");

			p.setProperty("ID_Order_Status", fStatus.getValue());
			p.setProperty("Address", "\"" + fAddress2.getAddress().replaceAll("\"", "\\\\\"") + "\"");
			p.setProperty("ID_User", "\"" + TaoGlobal.database.users.getID_User() + "\"");

			if (fManager.getID() == -1)
				p.setProperty("ID_Employee", "null");
			else
				p.setProperty("ID_Employee", + fManager.getID() + "");

			p.setProperty("`Sum`", fSum.getValue().toString());
			p.setProperty("`Prepay`", fPrepay.getValue().toString());
			p.setProperty("`Phone`", "\"" + fPhone.getPhone().replaceAll("\"", "\\\\\"") + "\"");

			if (fLift.getLift())
				p.setProperty("`Lift`", "1");
			else
				p.setProperty("`Lift`", "0");

			p.setProperty("`Contact`", "\"" + fContact.getContact().replaceAll("\"", "\\\\\"") + "\"");
			p.setProperty("`Floor`", fFloor.getFloor()+"");
		}

		public void insert() throws Exception {
			String query;
			Properties p = new Properties();
			Integer status = Integer.parseInt(fStatus.getValue());
			p.setProperty("ID_Order_Type", typeOrder+"");
			setFields(p);

			if (status == 4)
				p.setProperty("Date_Order_Finish", TaoGlobal.currSQLDate());

			ID_Order = TaoGlobal.database.query.insert("orders", p);

			if (ID_Order == -1) {
				Dlg.error("Ошибка сохранения заказа", "Не удалось получить значение ключа");
				return;
			}

			if (typeOrder == 1) {
				TaoDataModel dm = pnlMebel.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p = makeFields(dm, i);
					p.setProperty("ID_Order", ID_Order + "");
					TaoGlobal.database.query.insert("order_mebel", p);
				}

				dm = pnlMebelAlt.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p = makeFields(dm, i);
					p.setProperty("ID_Order", ID_Order+"");
					TaoGlobal.database.query.insert("order_mebel", p);
				}

				dm = pnlFurniture.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p.clear();
					p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
					p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));
					p.setProperty("ID_Order", ID_Order+"");
					TaoGlobal.database.query.insert("order_furniture", p);
				}

				p.clear();
				p.setProperty("ID_Order", ID_Order+"");
				pnlOrderText.fillFields(p);
				pnlOrderData.fillFields(p);
				TaoGlobal.database.query.insert("standard_orders", p);
			}

			if (typeOrder == 2) {
				TaoDataModel dm = pnlMebel.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p = makeFields(dm, i);
					p.setProperty("ID_Order", ID_Order+"");
					TaoGlobal.database.query.insert("order_mebel", p);
				}

				dm = pnlFurniture.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p.clear();
					p.setProperty("ID_Furniture", "" + dm.getData(i, "ID_Furniture"));
					p.setProperty("Quantity", "" + dm.getData(i, "Quantity"));
					p.setProperty("ID_Order", ID_Order + "");
					TaoGlobal.database.query.insert("order_furniture", p);
				}

				p.clear();
				p.setProperty("ID_Order", ID_Order+"");
				pnlOrderText.fillFields(p);
				pnlOrderHomeData.fillFields(p);
				TaoGlobal.database.query.insert("home_orders", p);
			}

			if (typeOrder == 3) {
				TaoDataModel dm = pnlFurniture.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p.clear();
					p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
					p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));
					p.setProperty("ID_Order", ID_Order+"");
					TaoGlobal.database.query.insert("order_furniture", p);
				}

				p.clear();
				dm = pnlSanitary.getTableModel();

				for (int i = 0; i < dm.getRowCount(); i++) {
					p.clear();
					p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
					p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));
					p.setProperty("ID_Order", ID_Order+"");
					TaoGlobal.database.query.insert("order_sanitary", p);
				}

				p.clear();
				p.setProperty("ID_Order", ID_Order+"");

				pnlOrderKitchenMaterial.fillFields(p);
				pnlOrderKitchenFurniture.fillFields(p);
				pnlOrderKitchenText.fillFields(p);
				TaoGlobal.database.query.insert("kitchen_orders", p);
			}

			try {
				pnlImages.save(ID_Order);
			} catch (Exception e) {
				Dlg.error("Ошибка сохранения файлов в базу!", e);
			}
		}

		public void update() throws Exception {
			String query;
			Properties p = new Properties();
			Integer status = Integer.parseInt(fStatus.getValue());
			setFields(p);

			if (status == 4)
				p.setProperty("Date_Order_Finish", TaoGlobal.currSQLDate());

			String expression = "ID_Order="+ID_Order;
			TaoGlobal.database.query.update("orders", p, expression);

			if (ID_Order == -1) {
				Dlg.error("Ошибка сохранения заказа", "Не удалось получить значение ключа");
				return;
			}

			if (typeOrder == 1) {
			String rest = "";
			int rest_id;
			TaoDataModel dm = pnlMebel.getTableModel();

			for (int i = 0; i < dm.getRowCount(); i++) {
				p = makeFields(dm, i);
				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Mebel");
					expression = "ID_Order_Mebel=" + dm.getData(i, "ID_Order_Mebel").toString();
					TaoGlobal.database.query.update("order_mebel", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_mebel", p);
				}

				if (rest.length() > 0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Employee IS null AND " +
					"ID_Order=" + ID_Order + " AND " +
					"ID_Order_Mebel NOT IN (" + rest +")";

				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_mebel", expression);
				rest = "";
			}

			dm = pnlMebelAlt.getTableModel();
			for (int i = 0; i < dm.getRowCount(); i++) {
				p = makeFields(dm, i);
				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Mebel");
					expression = "ID_Order_Mebel="+dm.getData(i, "ID_Order_Mebel").toString();
					TaoGlobal.database.query.update("order_mebel", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_mebel", p);
				}

				if (rest.length() > 0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Employee IS NOT null AND " +
					"ID_Order=" + ID_Order + " AND " +
					"ID_Order_Mebel NOT IN (" + rest +")";

				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_mebel", expression);
				rest = "";
				}

				dm = pnlFurniture.getTableModel();
				for (int i = 0; i < dm.getRowCount(); i++) {
					p.clear();
					p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
					p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));

					if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
						rest_id = (Integer)dm.getData(i, "ID_Order_Furniture");
						expression = "ID_Order_Furniture=" + dm.getData(i, "ID_Order_Furniture").toString();
						TaoGlobal.database.query.update("order_furniture", p, expression);
					} else {
						p.setProperty("ID_Order", ID_Order+"");
						rest_id = TaoGlobal.database.query.insert("order_furniture", p);
					}

					if (rest.length()>0)
						rest = rest + ",";

					rest = rest + rest_id;
				}

				if (rest.length() > 0) {
					expression = "ID_Order=" + ID_Order + " AND " +
						"ID_Order_Furniture NOT IN (" + rest +")";

					//Удаляем лишние!
					TaoGlobal.database.query.delete("order_furniture", expression);
				}

				expression = "ID_Order=" + ID_Order;
				p.clear();
				pnlOrderText.fillFields(p);
				pnlOrderData.fillFields(p);
				TaoGlobal.database.query.update("standard_orders", p, expression);
			}

		if (typeOrder == 2) {
				String rest = "";
				int rest_id;
				TaoDataModel dm = pnlMebel.getTableModel();

			for (int i = 0; i < dm.getRowCount(); i++) {
				p = makeFields(dm, i);

				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Mebel");
					expression = "ID_Order_Mebel=" + dm.getData(i, "ID_Order_Mebel").toString();
					TaoGlobal.database.query.update("order_mebel", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_mebel", p);
				}

				if (rest.length()>0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Employee IS null AND " +
					"ID_Order=" + ID_Order + " AND " +
					"ID_Order_Mebel NOT IN (" + rest + ")";

				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_mebel", expression);
				rest = "";
			}

			rest = "";
			dm = pnlMebel.getTableModel();

			dm = pnlFurniture.getTableModel();
			for (int i = 0; i < dm.getRowCount(); i++) {
				p.clear();
				p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
				p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));

				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Furniture");
					expression = "ID_Order_Furniture=" + dm.getData(i, "ID_Order_Furniture").toString();
					TaoGlobal.database.query.update("order_furniture", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_furniture", p);
				}

				if (rest.length()>0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Order="+ID_Order + " AND " +
					"ID_Order_Furniture NOT IN (" + rest + ")";

				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_furniture", expression);
			}

			expression = "ID_Order="+ID_Order;
			p.clear();
			pnlOrderText.fillFields(p);
			pnlOrderHomeData.fillFields(p);
			TaoGlobal.database.query.update("home_orders", p, expression);
		}

		if (typeOrder == 3) {
			String rest = "";
			int rest_id;
			TaoDataModel dm = pnlMebel.getTableModel();

			rest = "";

			dm = pnlFurniture.getTableModel();
			for (int i = 0; i < dm.getRowCount(); i++) {
				p.clear();
				p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
				p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));

				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Furniture");
					expression = "ID_Order_Furniture=" + dm.getData(i, "ID_Order_Furniture").toString();
					TaoGlobal.database.query.update("order_furniture", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_furniture", p);
				}

				if (rest.length()>0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Order=" + ID_Order + " AND " +
					"ID_Order_Furniture NOT IN (" + rest + ")";

				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_furniture", expression);
				rest = "";
			}

			dm = pnlSanitary.getTableModel();
			for (int i = 0; i < dm.getRowCount(); i++) {
				p.clear();
				p.setProperty("ID_Furniture", ""+dm.getData(i, "ID_Furniture"));
				p.setProperty("Quantity", ""+dm.getData(i, "Quantity"));

				if (dm.getData(i, "ID_Order").toString().equals(ID_Order + "")) {
					rest_id = (Integer)dm.getData(i, "ID_Order_Furniture");
					expression = "ID_Order_Furniture=" + dm.getData(i, "ID_Order_Furniture").toString();
					TaoGlobal.database.query.update("order_furniture", p, expression);
				} else {
					p.setProperty("ID_Order", ID_Order+"");
					rest_id = TaoGlobal.database.query.insert("order_sanitary", p);
				}

				if (rest.length()>0)
					rest = rest + ",";

				rest = rest + rest_id;
			}

			if (rest.length() > 0) {
				expression = "ID_Order=" + ID_Order + " AND " +
					"ID_Order_Furniture NOT IN (" + rest + ")";
	
				//Удаляем лишние!
				TaoGlobal.database.query.delete("order_sanitary", expression);
			}

			expression = "ID_Order="+ID_Order;
			p.clear();
			pnlOrderKitchenMaterial.fillFields(p);
			pnlOrderKitchenFurniture.fillFields(p);
			pnlOrderKitchenText.fillFields(p);
			TaoGlobal.database.query.update("kitchen_orders", p, expression);
		}

		try {
			pnlImages.save(ID_Order);
		} catch (Exception e) {
			Dlg.error("Ошибка сохранения файлов в базу!", e);
		}
	}

	private Properties makeFields(TaoDataModel aDm, int rn) {
		Properties result = new Properties();
		String ID_Employee = ""+aDm.getData(rn, "ID_Employee");

		if (ID_Employee.length() == 0) 
			ID_Employee = "null";

			result.setProperty("ID_Mebel", "" + aDm.getData(rn, "ID_Mebel"));
			result.setProperty("Quantity", "" + aDm.getData(rn, "Quantity"));
			result.setProperty("Square", "" + aDm.getData(rn, "Square"));
			result.setProperty("Price", "" + aDm.getData(rn, "Price"));
			result.setProperty("ID_Employee", ID_Employee);
			return result;
		}
	}

	class AcCancel extends AbstractAction {
		AcCancel() {
			putValue(Action.NAME, "Отмена");
			putValue(Action.SMALL_ICON, Nuvola.actions.s_cancel);
		}

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	class AcAfterCustomerSelect extends AbstractAction {
		public void actionPerformed(ActionEvent event) {
		try {
			ResultSet rs = TaoGlobal.database.order.getNewResultSet("SELECT Address, Contact, Floor, Lift, Phone FROM customers WHERE ID_Customer = " + fCustomer.getID());
			rs.first();

			if (rs.getObject(1) != null)
				fAddress2.setAddress(rs.getObject(1).toString());
			else
				fAddress2.setAddress("");

			if (rs.getObject(2) != null)
				fContact.setContact(rs.getObject(2).toString());
			else
				fContact.setContact("");

			try {
				if (rs.getObject(3) != null)
					fFloor.setFloor((Integer)rs.getObject(3));
			} catch (Exception e) {}

			try {
				if (rs.getObject(4) != null)
					fLift.setLift((Boolean)rs.getObject(3));
			} catch (Exception e) {}

			if (rs.getObject(5) != null)
				fPhone.setPhone(rs.getObject(5).toString());
			else
				fPhone.setPhone("");
			} catch (Exception e) {}
		}
	}
}
