package matech.utils.log;
/**
 *
 */


import javax.servlet.http.HttpServletRequest;

import matech.pub.listener.UserSession;
import matech.utils.string.StringUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;


/**
 * @author bill
 *
 */
public class Log {
    private Logger log = null;

    protected Log() {}

    public Log(Class clazz) {
        log = Logger.getLogger(clazz);
    }
    /**
     * 记录系统异常日志,通常放于catch块中记录
     *
     * @param e
     */
    public void exception(Object logInfo, Exception e) {
        log.error(logInfo + " " + e.getMessage());
    }

    /**
     * 记录系统运行日志,如系统启动，停止等信息
     *
     */
    public void sysInfo(Object logInfo) {
        log.fatal(logInfo);
    }

    /**
     * 记录模块操作日志，如模块的修改和删除
     *

     */
    public void log(Object logInfo) {
        log.warn(logInfo);
    }
    /**
     *
     * 系统调试日志
     *
     */
    public void debug(Object logInfo) {
        log.debug(logInfo);
    }

    /**
     *
     * 操作日志,写到日志文件
     *
     */
    public void info(Object logInfo) {
        log.info(logInfo);
    }
    public void info(Object logInfo,String func,HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        String userName = userSession.getUserName();

        log.info("["+userName+"]["+func+"]"+logInfo);

    }
    public void info(Object logInfo,String func,String userName) {
        log.info("["+userName+"]["+func+"]"+logInfo);
    }
    /**
     *
     * 警告日志,写到数据库表中
     *
     */
    public void warn(Object logInfo) {
        log.warn(logInfo);
    }
    public void warn(Object logInfo,String func,HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        String userName = userSession.getUserName();

        log.warn("["+userName+"]["+func+"]"+logInfo);
    }
    public void warn(Object logInfo,String func,String userName) {
        log.warn("["+userName+"]["+func+"]"+logInfo);
    }
    /**
     *
     * 错误日志
     *
     */
    public void error(Object logInfo,Throwable e) {
        log.error(logInfo,e);
    }

    public void error(Object logInfo,Throwable e,String func,HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        String userName = userSession.getUserName();

        log.error("["+userName+"]["+func+"]"+logInfo);

    }

    public void error(Object logInfo,Throwable e,String func,String userName) {
        log.error("["+userName+"]["+func+"]"+logInfo);
    }

    public void warn(Object logInfo,String optFunc,String optSql,String optData) {
        MDC.put("OPT_FUN", StringUtil.showNull(optFunc));
        MDC.put("OPT_SQL",StringUtil.showNull(optSql));
        MDC.put("OPT_DATA",StringUtil.showNull(optData));

        log.warn(logInfo);
    }
}
