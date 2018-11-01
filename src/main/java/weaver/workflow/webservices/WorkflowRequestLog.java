package weaver.workflow.webservices;

import java.io.Serializable;

/**
 * 工作流流转签字意见
 */
public class WorkflowRequestLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5675535868385045786L;
	
	/**
	 * ID
	 */
	private String id;

	/**
	 * 节点
	 */
	private String nodeId;
	private String nodeName;
	
	/**
	 * 流转签字意见
	 */
	private String remark;
	
    /**
     * 流转签字图形化签章意见图片的fileid
     */
    private String remarkSign;
	
	/**
	 * 操作者ID
	 */
	private String operatorId;
	
	/**
	 * 操作者名称
	 */
	private String operatorName;
	
	/**
	 * 操作者姓名签章的图片的path
	 */
	private String operatorSign;
	
	/**
	 * 操作日期(yyyy-HH-dd)
	 */
	private String operateDate;
	
	/**
	 * 操作时间(hh:mm:ss)
	 */
	private String operateTime;
	
	/**
	 * 操作类型名称
	 */
	private String operateType;
	
	/**
	 * 接收者名称
	 */
	private String receivedPersons;
	
	/**
     * 相关附件
     */
    private String annexDocHtmls;

    /**
     * 操作者部门
     */
    private String operatorDept;

    /**
     * 相关文档
     */
    private String signDocHtmls;

    /**
     * 相关流程
     */
    private String signWorkFlowHtmls;
    
    /**
     * 代理授权人
     */
    private String agentor;
    
    /**
     * 代理授权人部门
     */
    private String agentorDept;
    

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getReceivedPersons() {
		return receivedPersons;
	}

	public void setReceivedPersons(String receivedPersons) {
		this.receivedPersons = receivedPersons;
	}

	public String getOperatorDept() {
		return operatorDept;
	}

	public void setOperatorDept(String operatorDept) {
		this.operatorDept = operatorDept;
	}

















	public String getAnnexDocHtmls() {
		return annexDocHtmls;
	}

	public void setAnnexDocHtmls(String annexDocHtmls) {
		this.annexDocHtmls = annexDocHtmls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getRemarkSign() {
		return remarkSign;
	}

	public void setRemarkSign(String remarkSign) {
		this.remarkSign = remarkSign;
	}

	public String getOperatorSign() {
		return operatorSign;
	}

	public void setOperatorSign(String operatorSign) {
		this.operatorSign = operatorSign;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getSignDocHtmls() {
		return signDocHtmls;
	}

	public void setSignDocHtmls(String signDocHtmls) {
		this.signDocHtmls = signDocHtmls;
	}

	public String getSignWorkFlowHtmls() {
		return signWorkFlowHtmls;
	}

	public void setSignWorkFlowHtmls(String signWorkFlowHtmls) {
		this.signWorkFlowHtmls = signWorkFlowHtmls;
	}

	public String getAgentor() {
		return agentor;
	}

	public void setAgentor(String agentor) {
		this.agentor = agentor;
	}

	public String getAgentorDept() {
		return agentorDept;
	}

	public void setAgentorDept(String agentorDept) {
		this.agentorDept = agentorDept;
	}

}

