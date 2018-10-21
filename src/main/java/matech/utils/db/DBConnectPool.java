package matech.utils.db;

import java.sql.Connection;

import javax.sql.DataSource;

import com.matech.framework.pub.db.DBConnect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 数据库链接（供webservice使用）
 *
 * 
 */
public class DBConnectPool {
	private final static Log log = LogFactory.getLog(DBConnectPool.class);
	/*数据源*/
	private static DataSource dataSource=null;
	
	public DBConnectPool(DataSource dataSource){
		DBConnectPool.dataSource=dataSource;
	}
	
	/**
	 *	获取数据库连接 
	 * 
	 */
	public static Connection getConnection() {
		Connection conn = null;
		System.out.println("执行getConnection()");
		try{
			conn=new DBConnect().getConnect();//dataSource.getConnection();
		}catch(Exception e){
			log.error("执行方法getConnection()出错,无法从数据源中获取数据库连接！\n"+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
}
