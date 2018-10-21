/*
 * 创建日期 2007-11-26
 *
 */
package matech.utils.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * 获取系统相关路径
 * @author Administrator
 *
 */
public class Path {    
	private final static Log log = LogFactory.getLog(Path.class);
	/**
	 * 
	 * 从请求中获取资源的相对路径
	 * 
	 */
    public static String getPath(String url,HttpServletRequest request){
        return url.replaceAll("http://" + request.getServerName() + ":" + request.getServerPort(), "");
    }
    
    public static String getRootPath(HttpServletRequest request){
        String classPath = ClassPath.getClassPath();
        return  classPath.substring(0,classPath.indexOf("WEB-INF"));
    }
	/**
	 * 得到工程目录下WEB-INF/classes的路径
	 * 
	 * @return
	 */
	public static String getClassRoot() {
		String path=ClassPath.getClassPath();
		return path;
	}

	/**
	 * 得到工程目录下WEB-INF的路径
	 * 
	 * @return
	 */
	public static String getWebInfoPath() {
		String path = getClassRoot();
		if (path != null&&path.indexOf("classes")>0){
			return path.substring(0, path.indexOf("classes"));
		}else{
			log.error("无法得到工程目录下WEB-INF的路径");
		}
		return null;
	}

	/**
	 * 
	 * 得到工程目录下WEB-INF的上一级目录，即war包的路径
	 * 
	 * @return
	 */
	public static String getWarPath() {
		String path = getClassRoot();
		if (path != null&&path.indexOf("WEB-INF")>0){
			return path.substring(0, path.indexOf("WEB-INF"));
		}else{
			log.error("无法得到war包的路径");
		}	
		return null;
	}

	/**
	 * 得到当前class的路径
	 * 
	 * @return
	 */
	public static String getCurClsPath(Class cls) {
		URL url = null;
		try {
			if (cls != null){
				url = cls.getResource(cls.getSimpleName()+".class");
			}
			else{
				url = Path.class.getResource("Path.class");
			}
		} catch (Exception ex) {
			log.error("执行方法getCurClsPath(Class cls)出错："+ex.getMessage());
			ex.printStackTrace();
		}
		
		String path=url.getPath();
		if(path!=null){
			path=path.substring(0,path.lastIndexOf("/"));
			if(!path.endsWith("/")){
				path=path+"/";
			}			
		}else{
			log.error("无法得到当前class文件路径！");
		}
		return path;
	}

	/**
	 * 得到当前war的路径
	 * 
	 * @return
	 */
	public static String getWarPath(ServletConfig config) {
		String path = config.getServletContext().getRealPath("/");
		return path;
	}    
}
