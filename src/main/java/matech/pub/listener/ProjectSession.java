package matech.pub.listener;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 项目操作信息
 * 
 * @author Administrator
 *
 */
public class ProjectSession {
	private String prjId;					//项目id
	private String prjName;					//项目名称
	private String prjCode;					//项目编号
	private String auditTypeId; 			//审计类型
	private String unitId;					//被审单位
	private String auditUnitId; 			//主审单位
	private String orgUserNae;  			//审计组长
	private String prjDeputyorgUser;		//审计副组长
	private String chiefUserName;			//主审
	private String prjStatus;    			//是否归档
	private String prjYear;      			//项目年度
	private String startYear;    			//开始年份
	private String startMonth;   			//开始月份
	private String endYear;		 			//结束年份
	private String endMonth;	 			//结束月份
	private String createBy;	 			//立项人
	private String createDate;   			//立项日期
	private Map<String,String> prjDetail=new HashMap<String, String>(); //项目明细信息
	
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	public String getPrjCode() {
		return prjCode;
	}
	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}
	public String getAuditTypeId() {
		return auditTypeId;
	}
	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getAuditUnitId() {
		return auditUnitId;
	}
	public void setAuditUnitId(String auditUnitId) {
		this.auditUnitId = auditUnitId;
	}
	public String getOrgUserNae() {
		return orgUserNae;
	}
	public void setOrgUserNae(String orgUserNae) {
		this.orgUserNae = orgUserNae;
	}
	public String getPrjDeputyorgUser() {
		return prjDeputyorgUser;
	}
	public void setPrjDeputyorgUser(String prjDeputyorgUser) {
		this.prjDeputyorgUser = prjDeputyorgUser;
	}
	public String getChiefUserName() {
		return chiefUserName;
	}
	public void setChiefUserName(String chiefUserName) {
		this.chiefUserName = chiefUserName;
	}
	public String getPrjStatus() {
		return prjStatus;
	}
	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}
	public String getPrjYear() {
		return prjYear;
	}
	public void setPrjYear(String prjYear) {
		this.prjYear = prjYear;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Map<String, String> getPrjDetail() {
		return prjDetail;
	}
	public void setPrjDetail(Map<String, String> prjDetail) {
		this.prjDetail = prjDetail;
	}	
	
}
