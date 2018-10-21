package matech.pub;
import matech.utils.db.DbHarmony;
import matech.utils.db.DbType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import java.net.ProxySelector;
import java.sql.*;
public class DBConnect {
        private static Log log = LogFactory.getLog(DBConnect.class);
        /**
         * 获得数据库连接
         *
         * @return
         * @throws Exception
         */
        public Connection getConnect() throws Exception {

            Connection conn = null;

            try {
                ProxySelector proxySelector = ProxySelector.getDefault();
                ProxySelector.setDefault(null);
                if(DbHarmony.cur_system_dbType== DbType.DM){
                    conn = DriverManager.getConnection("proxool.dm");
                }else{
                    conn = DriverManager.getConnection("proxool.oracle");
                }
                ProxySelector.setDefault(proxySelector);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("从连接池获取连接失败,将从本地获取连接！");
                conn = getLocalConnection();
            }
            return conn;
        }

        public Connection testDmdbConnect() {
            Connection con=null;
            try {
                Class.forName("dm.jdbc.driver.DmDriver");
                String url = "jdbc:dm://127.0.0.1";
                String userID = "JSGAT";
                String passwd = "JSGATJSGAT";
                con = DriverManager.getConnection(url, userID, passwd);
            } catch (Exception e) {
                e.printStackTrace();
            } // 加载驱动程序

            return con;
        }

        /**
         * 供本地测试使用
         * @return
         * @throws Exception
         */
        public Connection getLocalConnection() throws Exception {
            Connection conn = null;
            try {

                String webInfoPath = DBConnect.class.getClassLoader().getResource("").getPath();

                webInfoPath = webInfoPath.substring(1, webInfoPath.indexOf("classes"));

                if(webInfoPath.lastIndexOf("/") != webInfoPath.length()) {
                    webInfoPath += "/";
                }

                log.info("本地数据库连接池配置路径：" + webInfoPath);

                JAXPConfigurator.configure(webInfoPath + "proxool.xml", false);

                Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");

                ProxySelector proxySelector = ProxySelector.getDefault();
                ProxySelector.setDefault(null);

                if(DbHarmony.cur_system_dbType==DbType.DM){
                    conn =  DriverManager.getConnection("proxool.dm");
                }else{
                    conn =  DriverManager.getConnection("proxool.oracle");
                }

                ProxySelector.setDefault(proxySelector);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return conn;

        }

        /**
         * sql server
         * */
        public Connection getSqlServerConn(){
            String url = "jdbc:microsoft:sqlserver://192.168.1.2:1433;DatabaseName=jsgat";
            String uid = "sa";
            String pwd = "matech";
            Connection conn = null;
            try{
                Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                if(conn == null || conn.isClosed()){
                    conn = DriverManager.getConnection( url, uid, pwd);
                }
                System.out.println(conn);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return conn;
        }
        

        public static void main(String[] args) {
            Connection conn=new DBConnect().testDmdbConnect();
            PreparedStatement ps=null;
            ResultSet rs=null;
            try {
                ps=conn.prepareStatement("select * from MT_BS_dept");
                rs=ps.executeQuery();
                while(rs.next()){
                    System.out.println(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("连接测试："+conn.toString());
        }

        //获取数据库连接
        public Connection getConnection(String DbType,String host,String client,String dbName,String user,String password) throws SQLException, ClassNotFoundException {
            Connection connection=null;
            String url="";
            if("oracle".equalsIgnoreCase(DbType)){
                Class.forName("oracle.jdbc.driver.OracleDriver");//指定连接类型  
                //url="jdbc:oracle:thin:@//"+host+":"+client+"/"+dbName+"?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
                url="jdbc:oracle:thin:@(description=(address_list= (address=(host="+host+") (protocol=tcp)(port="+client+"))(load_balance=yes)(failover=yes))(connect_data=(service_name= "+dbName+")))";
            }else if("mySql".equalsIgnoreCase(DbType)){
                Class.forName("com.mysql.jdbc.Driver");//指定连接类型  
                url="jdbc:mysql://"+host+":"+client+"/"+dbName;
            }else if("dm".equalsIgnoreCase(DbType)){
                Class.forName("dm.jdbc.driver.DmDriver");//指定连接类型  
                url="jdbc:dm://"+host+":"+client+"/"+dbName;
            }
            connection=DriverManager.getConnection(url, user, password);
            return connection;
        }
    }


