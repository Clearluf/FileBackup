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
//			// ���ñ����Խ��ı䴰�ڱ߿���ʽ����
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
		// ��ʼ��filemanager
		final FileManage fm = new FileManage();

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("�ļ����ݼ�¼����_CopyRight@Joe");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel HomePage = new JPanel();
		tabbedPane.addTab("�ļ�����", HomePage);
		HomePage.setLayout(new GridLayout(7, 1));
		DragFile df=new DragFile();
		df.p1=HomePage;
		

		JPanel hp1 = new JPanel();
		hp1.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp1);

		final JButton getinfo = new JButton("��ȡ��Ϣ");
		final JButton start = new JButton("��ʼ����");

		JLabel lb1 = new JLabel("�ļ�·��");
		lb1.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf1 = new JTextField(40);
		
		//��ק�ļ�������
		df.jtf=tf1;
		df.b=getinfo;
		df.drag();
		
		tf1.setFont(new Font("����", Font.PLAIN, 16));
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
		tf1.addFocusListener(new MyFocusListener("����·�����߰��ļ������,��֧���ļ���", tf1));
		
//		DragFile df=new DragFile();
//		df.p1=HomePage;
//		df.jtf=tf1;
//		df.drag();
		
		hp1.add(lb1);
		hp1.add(tf1);

		JPanel hp2 = new JPanel();
		hp2.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp2);
		JLabel lb2 = new JLabel("�ļ�����");
		lb2.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf2 = new JTextField(40);
		tf2.setEnabled(false);
		
		tf2.setFont(new Font("����", Font.PLAIN, 16));
		hp2.add(lb2);
		hp2.add(tf2);

		JPanel hp3 = new JPanel();
		hp3.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp3);
		JLabel lb3 = new JLabel("����ʱ��");
		lb3.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf3 = new JTextField(40);
		
		tf3.setEnabled(false);
		tf3.setFont(new Font("����", Font.PLAIN, 16));
		hp3.add(lb3);
		hp3.add(tf3);

		JPanel hp4 = new JPanel();
		hp4.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp4);
		JLabel lb4 = new JLabel("�ļ���С");
		lb4.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf4 = new JTextField(40);
		
		tf4.setEnabled(false);
		tf4.setFont(new Font("����", Font.PLAIN, 16));
		hp4.add(lb4);
		hp4.add(tf4);

		JPanel hp5 = new JPanel();
		hp5.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp5);
		JLabel lb5 = new JLabel("Ψһ��ʶ");
		lb5.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf5 = new JTextField(40);
		tf5.setEnabled(false);
		tf5.setFont(new Font("����", Font.PLAIN, 16));
		hp5.add(lb5);
		hp5.add(tf5);

		JPanel hp6 = new JPanel();
		hp6.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp6);
		JLabel lb6 = new JLabel("��ϸ��Ϣ");
		lb6.setFont(new Font("����", Font.PLAIN, 16));
		final JTextField tf6 = new JTextField(40);
		tf6.setFont(new Font("����", Font.PLAIN, 16));
		hp6.add(lb6);
		hp6.add(tf6);

		JPanel hp7 = new JPanel();
		hp7.setLayout(new FlowLayout(1, 30, 20));
		HomePage.add(hp7);
//		JButton getinfo=new JButton("��ȡ��Ϣ");		
//		JButton start=new JButton("��ʼ����");
		//��ȡ��Ϣ��ť
		getinfo.setFont(new Font("����", Font.PLAIN, 16));
		getinfo.setEnabled(false);
		start.setFont(new Font("����", Font.PLAIN, 16));
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
					tips.showMessageDialog(frame, "�������");
					//System.out.println(cd);
					// ����¼�洢�����ݿ���
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
//					tips.showMessageDialog(frame, "�������");
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
		tabbedPane.addTab("��¼��ѯ", subpage);
		subpage.setLayout(new BorderLayout(0, 0));

		JPanel cx = new JPanel();

		keywords = new JTextField(40);
		keywords.setFont(new Font("����", Font.PLAIN, 16));
		cx.add(keywords);

		JButton search = new JButton("��ѯ");
		search.setFont(new Font("����", Font.PLAIN, 16));
		cx.add(search);

		subpage.add(cx, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		subpage.add(scrollPane, BorderLayout.CENTER);

		final Vector<String> columnNames = new Vector<String>();
		// ����ģ��
		columnNames.add("ID");
		columnNames.add("�ļ���");
		columnNames.add("����޸�ʱ��");
		columnNames.add("����ʱ��");
		columnNames.add("�ļ���С���ֽڣ�");
		columnNames.add("MD5");
		columnNames.add("��ϸ����");
		final Vector<Vector<String>> data = new Vector<Vector<String>>();
		new Dao().show(data);
		tm = new DefaultTableModel(data, columnNames);

		// ������
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tm);

		table = new JTable(tm) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}

			public String getToolTipText(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				String tiptextString = null;
				if (row > -1 && col > -1) {
					Object value = table.getValueAt(row, col);
					if (null != value && !"".equals(value))
						tiptextString = value.toString();// ������ʾ��Ԫ������

				}
				return tiptextString;
			}
		};
		table.setFont(new Font("����", Font.PLAIN, 16));

		table.setRowSorter(sorter);
		Color c = new Color(200, 200, 200);
		table.setSelectionBackground(c);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		int[] width = { 100, 300, 200, 200, 150, 300, 600 };
		table.setColumnModel(getColumn(table, width));
		//�����˵�
		final JPopupMenu popupMenu = new JPopupMenu();
		JButton del=new JButton("Delete");
		//����
		final JOptionPane tips = new JOptionPane();
		del.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub						
				if(e.getClickCount()==1) {
					Dao.delete(cellVal);
					try {
						Process pro = Runtime.getRuntime().exec("cmd /c" + "cd D:\\Backup\\ && rmdir /s /q " + cellVal);
						tips.showMessageDialog(frame, "�ɹ�ɾ��");
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
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint()); // �����λ��
					// ���ɸѡ��������֮��Ķ�λ����
					row = (sorter.convertRowIndexToModel(row));
					int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint()); // �����λ��

					cellVal = (String) (tm.getValueAt(row, 0)); // ��õ����Ԫ������
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
				// ����triggerEvent���������¼�
				//triggerEvent(event);
				// isPopupTrigger():���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼���
				if (event.isPopupTrigger())
					// ��ʾ�˵�
				popupMenu.show(event.getComponent(), event.getX(), event.getY());
				int row = ((JTable) event.getSource()).rowAtPoint(event.getPoint()); // �����λ��
				//�����ɸѡ��������֮��Ķ�λ����
				row=(sorter.convertRowIndexToModel(row));
				int col = ((JTable) event.getSource()).columnAtPoint(event.getPoint()); // �����λ��
				cellVal = (String) (tm.getValueAt(row, 0)); // ��õ����Ԫ������
				System.out.println("ѡ�е�����Ϊ��"+cellVal);
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				//triggerEvent(event);
				// isPopupTrigger():���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼���
				if (event.isPopupTrigger())
					// ��ʾ�˵�
					popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}

		});
		scrollPane.setViewportView(table);
		
		// ��ѯ��ť
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
