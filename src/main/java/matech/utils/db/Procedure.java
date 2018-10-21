package matech.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import matech.utils.log.Log;
import net.sf.json.JSONArray;
import oracle.jdbc.OracleTypes;


/**
 * 
 * 执行存储过程类
 * 
 */
public class Procedure {
	private static Log log = new Log(Procedure.class) ;
	
	/**
	 * 
	 * 执行带参数返回记录集存储过程
	 * 
	 * @param Connection
	 * @param String 存储过程名称
	 * @param String 存储过程参数：param1=value1,param2=value2
	 * 
	 * 要求存储过程的第2个参数为返回记录集的游标
	 * 
	 * @return ResultSet
	 */
	public ResultSet execute(Connection conn,String proName,String params){
		CallableStatement  stmt=null;
		ResultSet rs=null;
		
		try {
			stmt=(CallableStatement )conn.prepareCall("{call "+proName+"(?,?)}");
			stmt.setString(1, params);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
			
			rs=(ResultSet)stmt.getObject(2);
			
		} catch (Exception e) {
			log.error("执行存储过程"+"{call "+proName+"(?,?)}"+"出错："+e.getMessage(),e);
			throw new RuntimeException(e);
		} 
		return rs;		
	}

	/**
	 * 
	 * 执行带参数存储过程
	 * 
	 * @param Connection
	 * @param String   存储过程名称
	 * @param Object[] 存储过程参数
	 * 
	 * @return ResultSet
	 * 
	 */
	public void execute(Connection conn,String proName,Object[] params){
		CallableStatement  stmt=null;
		String sql="";
		try {
			sql="{call "+proName+"(";	
			if(params!=null && params.length>0 ){
				sql=sql+"?";
				for(int i=1;i<params.length;i++){
					sql=sql+",?";
				}
				sql=sql+")}";
			}else{
				sql=sql+")}";
			}

			stmt=(CallableStatement )conn.prepareCall(sql);
			if(params!=null && params.length>0 ){
				for(int i=0;i<params.length;i++){
					stmt.setObject(i+1, params[i]);
				}
			}
			
			stmt.execute();
			
			log.warn("执行存储过程", "存储过程", sql, JSONArray.fromObject(params).toString());
			
		} catch (Exception e) {
			log.error("执行存储过程："+proName+"出错："+e.getMessage(),e);
			throw new RuntimeException(e);
		}finally{
			DbUtil.close(stmt);
		}	 	
	}	
	/**
	 * 
	 * 执行无参数存储过程
	 * 
	 * @param Connection
	 * @param String 存储过程名称
	 * @param String
	 * 
	 * @return ResultSet
	 */
	public void execute(Connection conn,String proName){
		CallableStatement  stmt=null;
		String sql="";
		try {
			sql="{call "+proName+"()}";

			stmt=(CallableStatement )conn.prepareCall(sql);

			stmt.execute();
			
			log.warn("执行存储过程", "存储过程", sql,null);
			
		} catch (Exception e) {
			log.error("执行存储过程："+proName+"出错："+e.getMessage(),e);
			throw new RuntimeException(e);
		}finally{
			DbUtil.close(stmt);
		}	
	}
}
