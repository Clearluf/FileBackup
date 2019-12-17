package SqlTool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import org.apache.log4j.Logger;


public class Dao {
	static String Driver="org.sqlite.JDBC";
	private static Logger logger = Logger.getLogger(Dao.class);
	
	//建表
	public static void createTable() {
		String sql="CREATE TABLE FileData (id VARCHAR (32), "
				+ "name VARCHAR (32), "
				+ "size VARCHAR (64), "
				+ "modifytime DATETIME,"
				+ "savetime   DATETIME,"
				+ "md5  VARCHAR (32),"
				+ "content VARCHAR (128));";
		try {
			logger.debug("开始加载驱动！");
			Class.forName(Driver);
			logger.debug("加载驱动！完成");
			logger.debug("连接数据库");
			Connection con=DriverManager.getConnection("jdbc:sqlite:db/FileBackUP.db");
			logger.debug("连接数据成功");
			Statement stat=con.createStatement();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.execute();
			ps.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param modifytime
	 * @param savetime
	 * @param MD5
	 * @param content
	 */
	public static void insert(String id,String name,long size,String modifytime,String savetime,String MD5,String content) {
		try {
			logger.debug("开始加载数据库驱动");
			Class.forName(Driver);
			logger.debug("加载驱动完成");
			logger.debug("连接数据库");
			Connection con = null;
			try {
				con=DriverManager.getConnection("jdbc:sqlite:db/FileBackUP.db");
				logger.debug("连接数据成功");
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("连接数据失败，"+e.toString());
				
			}	
			Statement stat=con.createStatement();
			String insertsql="insert into FileData values(?,?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(insertsql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setLong(3, size);
			ps.setString(4, modifytime);
			ps.setString(5, savetime);
			ps.setString(6, MD5);
			ps.setString(7, content);
			
			logger.debug("do sql:insert into FileData values"
					+ "("+id+","+name+","+size+","+modifytime+","+savetime+","+MD5+","+content+")");
			ps.execute();
			ps.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public  void show(Vector<Vector<String>> data) {
		try {
			logger.debug("开始加载数据库驱动");
			Class.forName(Driver);
			logger.debug("加载驱动完成");
			logger.debug("连接数据库");
			Connection con = null;
			try {
				con=DriverManager.getConnection("jdbc:sqlite:db/FileBackUP.db");
				logger.debug("连接数据成功");
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("连接数据失败，"+e.toString());
				
			}			
			
			Statement stat=con.createStatement();
			String selectall="select * from FileData";
			PreparedStatement ps=con.prepareStatement(selectall);
			ResultSet rs=ps.executeQuery(); 
			logger.debug("查询表中所有数据");
			
			while(rs.next()) {
				Vector<String> v=new Vector<String>();
				v.add(rs.getString(1));
				v.add(rs.getString(2));				
				v.add(rs.getString(4));
				v.add(rs.getString(5));
				v.add(rs.getString(3));
				v.add(rs.getString(6));
				v.add(rs.getString(7));
				data.add(v);
				logger.debug("data add "+v);
				
			}
			ps.close();
			stat.close();
			con.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void delete(String id) {
		try {
			logger.debug("开始加载数据库驱动");
			Class.forName(Driver);
			logger.debug("加载驱动完成");
			logger.debug("连接数据库");
			Connection con = null;
			try {
				con=DriverManager.getConnection("jdbc:sqlite:db/FileBackUP.db");
				logger.debug("连接数据成功");
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("连接数据失败，"+e.toString());
				
			}	
			Statement stat=con.createStatement();
			String delete="delete from FileData where id=?";
			
			PreparedStatement ps=con.prepareStatement(delete);
			ps.setString(1, id);
			ps.execute();
			logger.debug("do sql:"+ps.toString());
			ps.close();
			stat.close();
			con.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
