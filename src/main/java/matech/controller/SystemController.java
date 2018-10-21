package matech.controller;


import matech.framework.sys.ReadSysProperty;
import matech.framework.sys.UTILSysProperty;
import matech.utils.db.DbHarmony;
import matech.utils.db.DbType;
import matech.utils.quartz.SchedulerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;


public class SystemController  extends HttpServlet {


    private static final long serialVersionUID = 7026976627457368723L;

    private static Log log = LogFactory.getLog(SystemController.class);

    /**
     * 目前只是用来初始化整个系统的环境变量、数据库联结池等动作
     * @param config
     * @throws ServletException
     */
     @Override
    public void init(ServletConfig config) throws ServletException {
        long startTime = System.currentTimeMillis();
        log.info("系统初始化开始..");
        super.init(config);
        //初始化全局配置
        try {
            UTILSysProperty.SysProperty = new Properties();
            log.info("初始化全局配置...");

            log.info(UTILSysProperty.SysProperty.getProperty("attachFilePath"));
            log.info("从配置文件中加载...");
            ReadSysProperty.readFromXML(UTILSysProperty.SysProperty);
            String dbType=(String) UTILSysProperty.SysProperty.get("SYSTEMDBTYPE");
            if("DM".equalsIgnoreCase(dbType)){
                DbHarmony.cur_system_dbType= DbType.DM;
                log.info("参数决定当前系统数据库连接[达梦]。");
            }else if("Oracle".equalsIgnoreCase(dbType)){
                DbHarmony.cur_system_dbType=DbType.Oracle;
                log.info("参数决定当前系统数据库连接[Oracle]。");
            }else{
                log.error("未设置数据库类型参数[<SYSTEMDBTYPE></SYSTEMDBTYPE>]!!!请在配置文件sysconfig.xml上设置。");
            }
            log.info("从数据库中加载...");
            ReadSysProperty.readFromDatabase(UTILSysProperty.SysProperty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        //初始化调度器
        new SchedulerService();
        
        

        log.info("启动控制器成功,耗时：" + (endTime - startTime));

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
}
