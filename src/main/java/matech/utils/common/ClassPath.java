package matech.utils.common;

import java.io.UnsupportedEncodingException;

/**
 * 
 * 系统Class路径信息类
 * 
 */
public class ClassPath {
	
	public static String getClassAbsolutePath(String className)throws Exception  {

		if (className.startsWith("/")) {
			className =className.substring(1);
		}
		className = className.replace('.', '/');
		
		className =ClassPath.getClassPath()+ className + ".class";

		return className;

	}
	
	/**
	 * 取得.../WEB-INF/classes路径
	 * @return
	 * since 2007-7-3 上午10:23:45
	 */
	public static String getClassPath(){
		String cp="";
		if(ClassPath.class.getClassLoader().getResource("").getPath()!=null&&ClassPath.class.getClassLoader().getResource("").getPath().indexOf("/WEB-INF/classes")>-1){
			cp=ClassPath.class.getClassLoader().getResource("").getPath();
		}
		else if(ClassPath.class.getClassLoader().getResource("/").getPath()!=null&&ClassPath.class.getClassLoader().getResource("/").getPath().indexOf("/WEB-INF/classes")>-1){
			cp=ClassPath.class.getClassLoader().getResource("/").getPath();
		}
		else if(ClassPath.class.getResource("/").getPath()!=null&&ClassPath.class.getResource("/").getPath().indexOf("/WEB-INF/classes")>-1){
			cp=ClassPath.class.getResource("/").getPath();
		}
		try {
			cp =java.net.URLDecoder.decode(cp,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cp;
	}

}
