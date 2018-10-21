package matech.framework.sys;

import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *  * <p>Title: 系统全局变量 </p>
 * <p>Description:
 * 本程序用来维护系统的所有全局变量
 * 以及存放系统从XML文件中读取的系统配置参数；
 * 此外还有国际化对象;
 * </p>
 * <p>Copyright: Matech Copyright (c) 2007</p>
 * <p>Company: 广州铭太科技信息有限公司</p>
 * @author winnerQ
 * @version 3.0
 */
public class UTILSysProperty {
  public UTILSysProperty() {
  }

  /**
   * SystemProperty:系统XML文件中读取的内容；
   */
  public static Properties SysProperty = null;
  
  public static Map<String,List<String>> SysMapProperty=new HashMap<String, List<String>>();

  /**
   * 全局的WEB环境变量
   */
  public static  ApplicationContext context=null;

  /**
   * 系统路径
   */
  public static  String Path =null;

  /**
   * 超级系统管理员的USERPK!!!!
   */
  public static final String SUPERADMINISTRATOR = "admin";

  /**
   * 登陆、错误页面与URL、URI
   * 此处页面不得随意修改
   */
  public static final String LOGINONPAGE = "login.do";
  public static final String ERRORPAGE = "";
  public static final String INFOPAGE = "";

  /**
   * 打印调试语名
   */
  public static final boolean DEBUGPRINTLN = true;

}
