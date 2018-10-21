package matech.framework.sys;

import matech.utils.common.Path;
import matech.utils.db.DBConnectPool;
import matech.utils.db.DbUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ReadSysProperty {
	
	private static Log log = LogFactory.getLog(ReadSysProperty.class);

	/**
	 * 读取XML文件
	 * 
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	public static final Properties readFromXML(Properties prop)
			throws Exception {
		/**
		 * 读取XML文件中的所有关键字-值，存放到Properties中。
		 * 
		 * @return 返回一个名值对的列表。
		 */
		SAXBuilder sb = new SAXBuilder();

		// 从文件构造一个Document，因为XML文件中已经指定了编码，所以这里不必了
		String path = Path.getClassRoot()+"sysconfig.xml";
			
		Document doc = sb.build(new FileInputStream(path));

		// 加入一条处理指令
		Element root = doc.getRootElement();
		
		// 得到根元素
		List sysconfig = root.getChildren();
		
		String configName = "";
		String configValue = "";
		
		// 得到根元素所有子元素的集合
		for (Iterator iter1 = sysconfig.iterator(); iter1.hasNext();) {
			Element configNode = (Element) iter1.next();
			
			configName = configNode.getName();
			String nodeListStr="";
			List<String> nodeListText=new ArrayList<String>();
			if(configName.toUpperCase().endsWith("LIST")){
				List nodeList = configNode.getChildren();
				for(Iterator iter11 = nodeList.iterator(); iter11.hasNext();){
					Element nodeListTR = (Element) iter11.next();
					nodeListText.add(nodeListTR.getValue());
					nodeListStr=nodeListStr+","+nodeListTR.getValue();
				}
				UTILSysProperty.SysMapProperty.put(configName,nodeListText);
			}else{
				nodeListStr=configNode.getText();
			}
			configValue = nodeListStr;
			
			log.info("系统配置(从XML文件加载):" + configName + "=" + configValue);
			prop.setProperty(configName, configValue);
		}
		return prop;
	}
	
	/**
	 * 读取数据库配置表
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	public static final Properties readFromDatabase(Properties prop)
			throws Exception {
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnectPool.getConnection();
			
			String sql = " select cfg_Name, cfg_Value from mt_sys_config ";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			String configName = "";
			String configValue = "";
			while (rs.next()) {
				configName = rs.getString(1);
				configValue = rs.getString(2);
				
				log.info("系统配置(从数据库加载):" + configName + "=" + configValue);
				prop.setProperty(configName, configValue);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(ps);
			DbUtil.close(conn);
		}

		return prop;
	}

	public static final Properties read() {
		return new Properties();
	}
}
