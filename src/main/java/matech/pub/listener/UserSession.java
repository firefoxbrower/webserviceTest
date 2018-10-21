package matech.pub.listener;


import javax.servlet.http.HttpSession;

public class UserSession extends matech.framework.pub.listener.UserSession {
	//private String userId;
	//private String userName;
	private String userIp;
	private String userLoginId;
	private String userDepartmentId;
	private String userDepartmentName ;
	private String userPopedom;				//用户权限
	private String unitId;   				//单位
	private volatile HttpSession userSession;
	private String sessionId;				
	private String sysFlag;					//是否使用IE
	private String ctlUnitId;				//权限控制单位


	public String getCtlUnitId() {
		return ctlUnitId;
	}

	public void setCtlUnitId(String ctlUnitId) {
		this.ctlUnitId = ctlUnitId;
	}
    
	@Override
	public String getUserDepartmentName() {
		return userDepartmentName;
	}

	@Override
	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}

	public String getUserPopedom() {
		return userPopedom;
	}

	public void setUserPopedom(String userPopedom) {
		this.userPopedom = userPopedom;
	}

//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
    @Override
	public String getUserIp() {
		return userIp;
	}
	@Override
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	@Override
	public String getUserLoginId() {
		return userLoginId;
	}

	@Override
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
     @Override
	public String getUserDepartmentId() {
		return userDepartmentId;
	}
	
	public String getSysFlag(){
		return sysFlag;
	}

	@Override
	public void setUserDepartmentId(String userDepartmentId) {
		this.userDepartmentId = userDepartmentId;
	}

	@Override
	public String getUnitId() {
		return unitId;
	}

	@Override
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public HttpSession getUserSession() {
		return userSession;
	}

	public void setUserSession(HttpSession userSession) {
		this.userSession = userSession;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSysFlag(String sysflag){
		this.sysFlag = sysflag;
	}
}
