/*
	Vector
	2009, Alexey Komarov <alexey@komarov.org.ru>
*/

package tao.order;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

import tao.global.TaoGlobal;
import tao.visual.*;
import tao.icons.*;
import tao.dialogs.*;
import tao.database.TaoDataModel;

public class PnlImages extends JPanel {
	private TaoTable tbl = new TaoTable();
	private String tblName = TaoGlobal.database.order.getTempTableName();
	private String SQL_temp_table = 
		"CREATE TEMPORARY TABLE `" + tblName +"` (" +
		"`ID_Image` int(11) NOT NULL AUTO_INCREMENT," +
		"`oID_Image` int(11) DEFAULT NULL," +
		"`ID_Order` int(11) DEFAULT NULL," +
		"`Name` varchar(512) DEFAULT NULL," +
		"`Image` mediumblob DEFAULT NULL, " +
		" PRIMARY KEY (`ID_Image`), " +
		" UNIQUE KEY `ID_Image` (`ID_Image`), " +
		" KEY `ID_Order` (`ID_Order`) " +
		") ENGINE=InnoDB DEFAULT CHARSET=cp1251;";

	private String SQL_drop = "DROP TABLE `" + tblName + "`";
	private String SQL_Select_Base = "SELECT ID_Image, ID_Order, Name FROM " + tblName;
	private String SQL_Select = "";

	private String SQL_load =  "insert into `" + tblName + "` " +
		"select null as ID_Image, ID_Image as oID_Image, ID_Order, Name, Image " +
		"from images";

	private AcAdd acAdd = new AcAdd();
	private AcDelete acDelete = new AcDelete();
	private AcOpen acOpen = new AcOpen();

	private JButton btnAdd = new JButton(acAdd);
	private JButton btnDelete = new JButton(acDelete);
	private JButton btnOpen = new JButton(acOpen);

	private JToolBar toolBar = new JToolBar();
	private int max_file_size = 16000000;

	public PnlImages() {
		this.setLayout(new BorderLayout());
		try {
			TaoGlobal.database.order.execQuery(SQL_temp_table);
		} catch (Exception e) {
			Dlg.error("Ошибка создания временной таблицы " + SQL_temp_table, e);
		}

		tbl.setScheme("Images");
		SQL_Select= SQL_Select_Base;

		try {
			tbl.refresh(SQL_Select);
		} catch (Exception e) {
			Dlg.error("Ошибка чтения списка файлов", e);
		}

		tbl.setDefaultRenderer(Object.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Integer.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Boolean.class, new TaoCheckBoxRenderer());
		tbl.setDefaultRenderer(String.class, new TaoRowRenderer());
		tbl.setDefaultRenderer(Float.class, new TaoRowRenderer());
		this.add(tbl.getPanel(), BorderLayout.CENTER);

		toolBar.add(btnAdd);
		toolBar.add(btnDelete);
		toolBar.add(btnOpen);
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.NORTH);
	}

	public TaoDataModel getTableModel() {
		return (TaoDataModel)tbl.getModel();
	}

	public String getTableName() {
		return tblName;
	}

	public void dispose() {
		try {
			TaoGlobal.database.order.execQuery(SQL_drop);
		} catch (Exception e) {
			System.out.println("drop error");
		}
	}

	public void initHotKeys() {
		InputMap iMap = this.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insert");
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		tbl.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		ActionMap aMap = this.getRootPane().getActionMap();
		ActionMap tMap = tbl.getActionMap();

		aMap.put("insert", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acAdd.actionPerformed(e);
			}
		});

		aMap.put("delete", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acDelete.actionPerformed(e);
			}
		});

		tMap.put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				acOpen.actionPerformed(e);
			}
		});

		tbl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					acOpen.actionPerformed(null);
				}
			}
		});
	}

	public void load(int aID_Order) throws Exception {
		TaoGlobal.database.query.execQuery(SQL_load + " WHERE ID_Order=" + aID_Order);
		tbl.refresh();
	}

	public void save(int aID_Order) throws Exception {
		String SQL_save =  "insert into `Images` " +
			"select null as ID_Image, " + aID_Order + " as ID_Order, Name, Image " +
			"from " + tblName + " WHERE oID_Image IS Null; ";

		String SQL_delete =  "delete from `Images` " +
			"where not exists(select * from " +
			tblName + " WHERE ID_Order =" + aID_Order +
			" AND oID_Image=images.ID_Image) AND " +
			" ID_Order="+aID_Order;

		TaoGlobal.database.query.execQuery(SQL_delete);
		TaoGlobal.database.query.execQuery(SQL_save);
	}

	class AcAdd extends AbstractAction {
		AcAdd() {
			putValue(Action.NAME, "Добавить");
			putValue(Action.SHORT_DESCRIPTION, "Добавить файл в заказ");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_add );
		}

		public void actionPerformed(ActionEvent event) {
			if (TaoGlobal.LastImageDir == null)
				TaoGlobal.LastImageDir = System.getProperty("user.docs");

			JFileChooser fc = new JFileChooser(TaoGlobal.LastImageDir);
			boolean autoCommit = true;

			int rc = fc.showDialog(null, "Выберите файл");
			if (rc == JFileChooser.APPROVE_OPTION) {
				TaoGlobal.LastImageDir = fc.getSelectedFile().getPath();
				String INSERT_PICTURE = "insert into " + tblName + " (name, image) values (?, ?)";
				FileInputStream fis = null;
				PreparedStatement ps = null;

				try {
					File file = new File(fc.getSelectedFile().getAbsolutePath());
					if (file.length() > max_file_size) {
						Dlg.error("Размер файла не может превышать 16 мегабайт!");
						return;
					}

					TaoGlobal.startWaitCursor();
					autoCommit = TaoGlobal.database.connection.getAutoCommit();
					TaoGlobal.database.connection.setAutoCommit(false);
					fis = new FileInputStream(file);
					ps = TaoGlobal.database.connection.prepareStatement(INSERT_PICTURE);
					ps.setString(1, fc.getSelectedFile().getName());
					ps.setBinaryStream(2, fis, (int) file.length());
					ps.executeUpdate();
					TaoGlobal.database.connection.commit();
					int index = tbl.getSelectedRow();
					tbl.refresh();
				} catch(Exception e) {
					Dlg.error("Ошибка сохранения файла в базу данных", e);
				} finally {
					try {
						if (ps != null)
							ps.close();

						if (fis != null)
							fis.close();
					} catch(Exception e) {
						Dlg.error("Ошибка сохранения файла в базу данных", e);
					}

					try {
						TaoGlobal.database.connection.setAutoCommit(autoCommit);
					} catch (Exception e) {
						Dlg.error("Ошибка включения AutoCommit! Перезапустите программу!" ,e);
					}
					TaoGlobal.stopWaitCursor();
				}
			}
		}
	}

	class AcDelete extends AbstractAction {
		AcDelete() {
			putValue(Action.NAME, "Удалить");
			putValue(Action.SHORT_DESCRIPTION, "Удалить позицию");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_delete );
		}

		public void actionPerformed(ActionEvent event) {

			if (!tbl.isSelected()) {
				Dlg.error("Не выбрана позиция для удаления!");
				return;
			}

			try {
				if (Dlg.queryYN(btnDelete.getText() + " файл \""  + tbl.getSelected("Name") + "\"?")) {
					int index = tbl.getSelectedRow();
					String oldKeyValue = tbl.getSelected("ID_Image");
					String expression = "ID_Image = " + oldKeyValue;
					TaoGlobal.database.query.delete(tblName, expression);
					tbl.refresh();

					if (index >= tbl.getRowCount())
						index--;

					if (index >= 0)
						tbl.changeSelection(index, 0, false, false);
				}
			} catch (Exception e) {
				Dlg.error("Ошибка удаления файла из заказа", e);
			} 
		}
	}

	class AcOpen extends AbstractAction {
		AcOpen() {
			putValue(Action.NAME, "Открыть");
			putValue(Action.SHORT_DESCRIPTION, "Открыть");
			putValue(Action.SMALL_ICON,Nuvola.actions.s_file);
		}

		public void actionPerformed(ActionEvent event) {
			if (!tbl.isSelected()) {
				Dlg.error("Не выбран файл!");
				return;
			}

			TaoGlobal.startWaitCursor();

			try {
				String path = TaoGlobal.config.localParams.getProperty("tempdir");
				String fileName = path + tbl.getSelected("Name");
				FileOutputStream fos = null;

				try {
					String sqlStatement = "select Image from " + tblName + " where ID_Image = " + tbl.getSelected("ID_Image");
					ResultSet rs = TaoGlobal.database.connection.createStatement().executeQuery(sqlStatement);

					if (rs.next()) {
						byte[] buf;
						try {
							// Get as a BLOB
							Blob aBlob = rs.getBlob(1);
							buf = aBlob.getBytes(1, (int) aBlob.length());
						} catch(Exception ex) {
							// The driver could not handle this as a BLOB...
							// Fallback to default (and slower) byte[] handling
							buf = rs.getBytes(1);
						}

						try {
							fos = new FileOutputStream(fileName);
							fos.write(buf);
						} catch (Exception e) {
							Dlg.error("Ошибка сохранения файла " + fileName, e);
						} finally  {
							if (fos != null)
								fos.close();
						}
					} else {
						Dlg.error("Файл не найден!");
						return;
					}
				} catch(Exception e) {
					Dlg.error("Ошибка чтения файла из базы!", e);
					return;
				}

				File file = new File(fileName); 

				Desktop dt = Desktop.getDesktop();
				dt.open(file);
			} catch (Exception e) {
				Dlg.error("Ошибка открытия файла", e);
				return;
			} finally {
				TaoGlobal.stopWaitCursor();
			}
		}
	}
}
