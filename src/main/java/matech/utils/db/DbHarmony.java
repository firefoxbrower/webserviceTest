package matech.utils.db;

import dm.jdbc.driver.DmdbType;
import oracle.jdbc.OracleTypes;
/**
 * 数据库和谐类   
 * @author xiaohua
 *
 */
public class DbHarmony {
	
public static DbType cur_system_dbType=DbType.DM;

/**
 * 存储过程输出游标参数
 * @return
 */
public static int getOutParamCursorType(){
	
	int type;
	switch (cur_system_dbType) {
	case Oracle:
		type=OracleTypes.CURSOR;
		break;
	case DM:
		type=DmdbType.CURSOR;
		break;

	default:
		type=OracleTypes.CURSOR;//系统默认为Oracle
		break;
	}

	return type;
}

/**
 * 存储过程参数Float类型
 * @return
 */
public static int getDBParamFloat(){
     int type;
     switch (cur_system_dbType){
       case Oracle :
    	   type=OracleTypes.FLOAT; 
      break;
       case DM:
    	   type=DmdbType.DATA_FLOAT;
      default:
    		   type=OracleTypes.FLOAT; 
    		   break;
     }
    
     
	return type;
	
}


}
