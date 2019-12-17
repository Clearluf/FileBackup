package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

//import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import SqlTool.Dao;
import FileTool.FileManage;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import FileTool.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import javax.swing.JScrollPane;

public class TrueUI {

	private JFrame frame;
	String backupdir = "D:\\Backup";
	private JTextField keywords;
	String cellVal;
	DefaultTableModel tm;
	JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

//		try {
//			// 设置本属性将改变窗口边框样式定义
//			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
//			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		} catch (Exception e) {
//			// TODO exception
//			e.printStackTrace();
//		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrueUI window = new TrueUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrueUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// 初始化filemanager
		final FileManage fm = new FileManage();

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("文件备份记录工具_CopyRight@Joe");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel HomePage = new JPanel();
		tabbedPane.addTab("文件备份", HomePage);
		HomePage.setLayout(new GridLayout(7, 1));
		DragFile df=new DragFile();
		df.p1=HomePage;
		

		JPanel hp1 = new JPanel();
		hp1.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp1);

		final JButton getinfo = new JButton("获取信息");
		final JButton start = new JButton("开始备份");

		JLabel lb1 = new JLabel("文件路径");
		lb1.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf1 = new JTextField(40);
		
		//拖拽文件到界面
		df.jtf=tf1;
		df.b=getinfo;
		df.drag();
		
		tf1.setFont(new Font("楷体", Font.PLAIN, 16));
		tf1.setTransferHandler(new TransferHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean importData(JComponent comp, Transferable t) {
				try {
					Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

					String filepath = o.toString();
					if (filepath.startsWith("[")) {
						filepath = filepath.substring(1);
					}
					if (filepath.endsWith("]")) {
						filepath = filepath.substring(0, filepath.length() - 1);
					}

					tf1.setText(filepath);
					getinfo.setEnabled(true);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			public boolean canImport(JComponent comp, DataFlavor[] flavors) {
				for (int i = 0; i < flavors.length; i++) {
					if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
						return true;
					}
				}
				return false;
			}
		});
		tf1.addFocusListener(new MyFocusListener("输入路径或者把文件拖这儿,不支持文件夹", tf1));
		
//		DragFile df=new DragFile();
//		df.p1=HomePage;
//		df.jtf=tf1;
//		df.drag();
		
		hp1.add(lb1);
		hp1.add(tf1);

		JPanel hp2 = new JPanel();
		hp2.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp2);
		JLabel lb2 = new JLabel("文件名称");
		lb2.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf2 = new JTextField(40);
		tf2.setEnabled(false);
		
		tf2.setFont(new Font("楷体", Font.PLAIN, 16));
		hp2.add(lb2);
		hp2.add(tf2);

		JPanel hp3 = new JPanel();
		hp3.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp3);
		JLabel lb3 = new JLabel("创建时间");
		lb3.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf3 = new JTextField(40);
		
		tf3.setEnabled(false);
		tf3.setFont(new Font("楷体", Font.PLAIN, 16));
		hp3.add(lb3);
		hp3.add(tf3);

		JPanel hp4 = new JPanel();
		hp4.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp4);
		JLabel lb4 = new JLabel("文件大小");
		lb4.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf4 = new JTextField(40);
		
		tf4.setEnabled(false);
		tf4.setFont(new Font("楷体", Font.PLAIN, 16));
		hp4.add(lb4);
		hp4.add(tf4);

		JPanel hp5 = new JPanel();
		hp5.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp5);
		JLabel lb5 = new JLabel("唯一标识");
		lb5.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf5 = new JTextField(40);
		tf5.setEnabled(false);
		tf5.setFont(new Font("楷体", Font.PLAIN, 16));
		hp5.add(lb5);
		hp5.add(tf5);

		JPanel hp6 = new JPanel();
		hp6.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp6);
		JLabel lb6 = new JLabel("详细信息");
		lb6.setFont(new Font("楷体", Font.PLAIN, 16));
		final JTextField tf6 = new JTextField(40);
		tf6.setFont(new Font("楷体", Font.PLAIN, 16));
		hp6.add(lb6);
		hp6.add(tf6);

		JPanel hp7 = new JPanel();
		hp7.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp7);
//		JButton getinfo=new JButton("获取信息");		
//		JButton start=new JButton("开始备份");
		//获取信息按钮
		getinfo.setFont(new Font("楷体", Font.PLAIN, 16));
		getinfo.setEnabled(false);
		start.setFont(new Font("楷体", Font.PLAIN, 16));
		start.setEnabled(false);

		getinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tf2.setText(fm.getFileName(tf1.getText()));
				tf3.setText(fm.getFileLastModifyTimeFormat(tf1.getText()));
				tf4.setText(fm.getfilesize(tf1.getText()) + " Byte");
				tf5.setText(fm.getMD5(tf1.getText()));
				tf6.setText("");
				start.setEnabled(true);
			}

		});

		start.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				try {

					start.setEnabled(false);
					long timestamp = System.currentTimeMillis();
					final String subdir = String.valueOf(timestamp / 1000);
					
					JOptionPane tips = new JOptionPane();
					final CountDownLatch cd = new CountDownLatch(1);
					//System.out.println(cd);
					new Thread(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							try {
								fm.copyFile(tf1.getText(), backupdir + java.io.File.separator + subdir
										+ java.io.File.separator + tf2.getText());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cd.countDown();

						}
					}).start();

					cd.await();
					tips.showMessageDialog(frame, "备份完成");
					//System.out.println(cd);
					// 将记录存储到数据库中
					String id = subdir;// ID
					String name = tf2.getText();
					long size = Long.valueOf(tf4.getText().split(" ")[0]);
					String modifytime = tf3.getText();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String savetime = sdf.format(new Date(timestamp));
					String MD5 = tf5.getText();
					String content = tf6.getText();
					new Dao().insert(id, name, size, modifytime, savetime, MD5, content);

//					JOptionPane tips = new JOptionPane();
//					tips.showMessageDialog(frame, "备份完成");
//					frame.add(tips);

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		hp7.add(getinfo);
		hp7.add(start);

		JPanel subpage = new JPanel();
		tabbedPane.addTab("记录查询", subpage);
		subpage.setLayout(new BorderLayout(0, 0));

		JPanel cx = new JPanel();

		keywords = new JTextField(40);
		keywords.setFont(new Font("楷体", Font.PLAIN, 16));
		cx.add(keywords);

		JButton search = new JButton("查询");
		search.setFont(new Font("楷体", Font.PLAIN, 16));
		cx.add(search);

		subpage.add(cx, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		subpage.add(scrollPane, BorderLayout.CENTER);

		final Vector<String> columnNames = new Vector<String>();
		// 数据模型
		columnNames.add("ID");
		columnNames.add("文件名");
		columnNames.add("最后修改时间");
		columnNames.add("保存时间");
		columnNames.add("文件大小（字节）");
		columnNames.add("MD5");
		columnNames.add("详细内容");
		final Vector<Vector<String>> data = new Vector<Vector<String>>();
		new Dao().show(data);
		tm = new DefaultTableModel(data, columnNames);

		// 排序器
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tm);

		table = new JTable(tm) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}

			public String getToolTipText(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				String tiptextString = null;
				if (row > -1 && col > -1) {
					Object value = table.getValueAt(row, col);
					if (null != value && !"".equals(value))
						tiptextString = value.toString();// 悬浮显示单元格内容

				}
				return tiptextString;
			}
		};
		table.setFont(new Font("楷体", Font.PLAIN, 16));

		table.setRowSorter(sorter);
		Color c = new Color(200, 200, 200);
		table.setSelectionBackground(c);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		int[] width = { 100, 300, 200, 200, 150, 300, 600 };
		table.setColumnModel(getColumn(table, width));
		//弹出菜单
		final JPopupMenu popupMenu = new JPopupMenu();
		JButton del=new JButton("Delete");
		//弹窗
		final JOptionPane tips = new JOptionPane();
		del.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub						
				if(e.getClickCount()==1) {
					Dao.delete(cellVal);
					try {
						Process pro = Runtime.getRuntime().exec("cmd /c" + "cd D:\\Backup\\ && rmdir /s /q " + cellVal);
						tips.showMessageDialog(frame, "成功删除");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		popupMenu.add(del);
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // 获得行位置
					// 解决筛选或者排序之后的定位问题
					row = (sorter.convertRowIndexToModel(row));
					int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); // 获得列位置

					cellVal = (String) (tm.getValueAt(row, 0)); // 获得点击单元格数据
					if (cellVal.matches("[0-9]{10}")) {
						try {
							Process pro = Runtime.getRuntime().exec("cmd /c" + "start D:\\Backup\\" + cellVal);
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					;
				}
			}
			
			@Override
			public void mousePressed(MouseEvent event) {
				// 调用triggerEvent方法处理事件
				//triggerEvent(event);
				// isPopupTrigger():返回此鼠标事件是否为该平台的弹出菜单触发事件。
				if (event.isPopupTrigger())
					// 显示菜单
				popupMenu.show(event.getComponent(), event.getX(), event.getY());
				int row = ((JTable) event.getSource()).rowAtPoint(event.getPoint()); // 获得行位置
				//解决了筛选或者排序之后的定位问题
				row=(sorter.convertRowIndexToModel(row));
				int col = ((JTable) event.getSource()).columnAtPoint(event.getPoint()); // 获得列位置
				cellVal = (String) (tm.getValueAt(row, 0)); // 获得点击单元格数据
				System.out.println("选中的数据为："+cellVal);
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				//triggerEvent(event);
				// isPopupTrigger():返回此鼠标事件是否为该平台的弹出菜单触发事件。
				if (event.isPopupTrigger())
					// 显示菜单
					popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}

		});
		scrollPane.setViewportView(table);
		
		// 查询按钮
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				tm.setRowCount(0);
				new Dao().show(data);
				tm = new DefaultTableModel(data, columnNames);
				String key = keywords.getText();
				sorter.setRowFilter(RowFilter.regexFilter(key));

			}
		});
	}

	public static TableColumnModel getColumn(JTable table, int[] width) {
		TableColumnModel columns = table.getColumnModel();
		for (int i = 0; i < width.length; i++) {
			TableColumn column = columns.getColumn(i);
			column.setPreferredWidth(width[i]);
		}
		return columns;
	}
}
