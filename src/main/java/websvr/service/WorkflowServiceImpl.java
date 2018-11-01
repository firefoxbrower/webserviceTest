package websvr.service;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.util.Base64;
import weaver.workflow.webservices.WorkflowRequestLog;
import websvr.bean.OAResponse;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkflowServiceImpl implements WorkflowService {
    private static Log log = LogFactory.getLog(WorkflowServiceImpl.class);
    OAResponse oaResponse = new OAResponse();
    @Override
    public String doCreateWorkflowRequest(String flowquest, int userid) {
        
        log.info("response 参数1:"+flowquest);
        log.info("response 参数2:"+ userid);
        
//
//        if (!StringUtils.equalsIgnoreCase(fileMD5, md5val)) {// MD5值不一致
//            errormsg = "md5_error";
//            oaResponse.setErrCode("1");
//            oaResponse.setErrMsg("md5 校验失败");
//        }else{
//            // md5一致
//            oaResponse.setErrCode("0");
//            oaResponse.setErrMsg("");
//
//        }
//        oaResponse.setID(id);
        
        Date date= new Date(System.currentTimeMillis());
        String pattern="yyyy-MM-dd HH:mm:ss";
//        byte[] buffer = Base64.decode(fileObject);
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(buffer, 0, buffer.length);

////        BigInteger bigInt = new BigInteger(1, md.digest());
//        String md5val = bigInt.toString(16);
        String errormsg = null ;
        oaResponse.setErrCode("-1");
        oaResponse.setErrMsg("创建流程失败");

        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        String datestr=sdf.format(date);// format  为格式化方法
        oaResponse.setReceiptTime(datestr);
        JSONObject jsonObject = JSONObject.fromObject(oaResponse);
        System.out.println(jsonObject.toString());
        return jsonObject.toString() ;
      
    }

    @Override
    public boolean deleteRequest(int in0, int in1)  {
        return false;
    }

    @Override
    public String submitWorkflowRequest(String in0, int in1, int in2, String in3, String in4)  {
        return null;
    }

    @Override
    public int getToDoWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public String getCreateWorkflowRequestInfo(int in0, int in1)  {
        return null;
    }

    @Override
    public String[] getAllWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public String[] getMyWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public int getProcessedWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public String getWorkflowRequest(int in0, int in1, int in2)  {
        return null;
    }

    @Override
    public String getLeaveDays(String in0, String in1, String in2, String in3, String in4)  {
        return null;
    }

    @Override
    public String getWorkflowRequest4Split(int in0, int in1, int in2, int in3)  {
        return null;
    }

    @Override
    public String[] getHendledWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public int getCreateWorkflowCount(int in0, int in1, String[] in2)  {
        return 0;
    }

    @Override
    public String[] getToDoWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public String[] getWorkflowNewFlag(String[] in0, String in1)  {
        return new String[0];
    }

    @Override
    public String[] getCCWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public String[] getCreateWorkflowList(int in0, int in1, int in2, int in3, int in4, String[] in5)  {
        return new String[0];
    }

    @Override
    public int getCCWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public int getAllWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public WorkflowRequestLog[] getWorkflowRequestLogs(String in0, String in1, int in2, int in3, int in4)  {
        return new WorkflowRequestLog[0];
    }

    @Override
    public int getMyWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public String[] getProcessedWorkflowRequestList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }

    @Override
    public int getCreateWorkflowTypeCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public String forwardWorkflowRequest(int in0, String in1, String in2, int in3, String in4)  {
        return null;
    }

    @Override
    public void writeWorkflowReadFlag(String in0, String in1)  {

    }

    @Override
    public int getHendledWorkflowRequestCount(int in0, String[] in1)  {
        return 0;
    }

    @Override
    public String[] getCreateWorkflowTypeList(int in0, int in1, int in2, int in3, String[] in4)  {
        return new String[0];
    }
}
