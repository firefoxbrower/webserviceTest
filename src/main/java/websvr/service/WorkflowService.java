package websvr.service;

import weaver.workflow.webservices.WorkflowRequestLog;

public interface WorkflowService {

   public String doCreateWorkflowRequest(String flowquest, int userid);
   public boolean deleteRequest(int in0, int in1) ;
   public String submitWorkflowRequest(String in0, int in1, int in2, String in3, String in4) ;
   public int getToDoWorkflowRequestCount(int in0, String[] in1) ;
   public String getCreateWorkflowRequestInfo(int in0, int in1) ;
   public String[] getAllWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public String[] getMyWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public int getProcessedWorkflowRequestCount(int in0, String[] in1) ;
   public String getWorkflowRequest(int in0, int in1, int in2) ;
   public String getLeaveDays(String in0, String in1, String in2, String in3, String in4) ;
   public String getWorkflowRequest4Split(int in0, int in1, int in2, int in3) ;
   public String[] getHendledWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public int getCreateWorkflowCount(int in0, int in1, String[] in2) ;
   public String[] getToDoWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public String[] getWorkflowNewFlag(String[] in0, String in1) ;
   public String[] getCCWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public String[] getCreateWorkflowList(int in0, int in1, int in2, int in3, int in4, String[] in5) ;
   public int getCCWorkflowRequestCount(int in0, String[] in1) ;
   public int getAllWorkflowRequestCount(int in0, String[] in1) ;
   public WorkflowRequestLog[] getWorkflowRequestLogs(String in0, String in1, int in2, int in3, int in4) ;
   public int getMyWorkflowRequestCount(int in0, String[] in1) ;
   public String[] getProcessedWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4) ;
   public int getCreateWorkflowTypeCount(int in0, String[] in1) ;
   public String forwardWorkflowRequest(int in0, String in1, String in2, int in3, String in4) ;
   public void writeWorkflowReadFlag(String in0, String in1) ;
   public int getHendledWorkflowRequestCount(int in0, String[] in1) ;
   public String[] getCreateWorkflowTypeList(int in0, int in1, int in2, int in3, String[] in4) ;
}


