package websvr.service;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.util.Base64;
import websvr.bean.OAResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Administrator
 */
public class OAServiceImpl implements  OAService {
    
    private static Log log = LogFactory.getLog(OAServiceImpl.class);
    OAResponse oaResponse = new OAResponse();






    @Override
    public String receiveFile(String id, String auditID, String prjID, String prjName, String fileTypeID, String fileType, String submmitterID, String submmitterName, String fileID, String fileName, String fileObject, String fileMD5) {
        log.info("文件名:"+ fileName);
        log.info("编码内容:"+fileObject);
        log.info("md值:"+ fileMD5);


        byte[] buffer = Base64.decode(fileObject);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(buffer, 0, buffer.length);

        BigInteger bigInt = new BigInteger(1, md.digest());
        String md5val = bigInt.toString(16);
        String errormsg = null ;

        if (!StringUtils.equalsIgnoreCase(fileMD5, md5val)) {// MD5值不一致
            errormsg = "md5_error";
            oaResponse.setErrCode("1");
            oaResponse.setErrMsg("md5 校验失败");
        }else{
            // md5一致
            oaResponse.setErrCode("0");
            oaResponse.setErrMsg("");

        }
        oaResponse.setID(id);
        Date date= new Date(System.currentTimeMillis());
        String pattern="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
        String datestr=sdf.format(date);// format  为格式化方法
        oaResponse.setReceiptTime(datestr);
        JSONObject jsonObject = JSONObject.fromObject(oaResponse);
        System.out.println(jsonObject.toString());
        return jsonObject.toString() ;
    }
}


//    @Override
//    public String test(String fileName, String base64, String md5) {
//        log.info("文件名:"+ fileName);
//        log.info("编码内容:"+base64);
//        try {
//            System.out.println(new String( Base64.decode(base64)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        byte[] buffer2 = Base64.decode(base64);
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        md.update(buffer2, 0, buffer2.length);
//        
//        BigInteger bigInt = new BigInteger(1, md.digest());
//        String md5val = bigInt.toString(16);
//        String errormsg = null ;
//       
//        if (!StringUtils.equalsIgnoreCase(md5, md5val)) {// MD5值不一致
//            errormsg = "md5_error";
//            oaResponse.setErrCode("1");
//            oaResponse.setErrMsg("md5 校验失败");
//        }else{
//            // md5一致
//            oaResponse.setErrCode("0");
//            oaResponse.setErrMsg("");
//        }
//        log.info("文件的md:"+md5);
//        log.info("errormsg:"+errormsg);
//        oaResponse.setID("88899");
//        Date date= new Date(System.currentTimeMillis());
//        String pattern="yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
//        String datestr=sdf.format(date);// format  为格式化方法
//        oaResponse.setReceiptTime(datestr);
//        JSONObject jsonObject = JSONObject.fromObject(oaResponse);
//        System.out.println(jsonObject.toString());
//        return jsonObject.toString();
//    }
//    @Override
//    public OAResponse sendFile(OARequest request) {
//       
//        // 请求的文件信息
//      OAResponse response = new OAResponse() ;
//      response.setID(request.getId());
//      Date date= new Date(System.currentTimeMillis());
//      String pattern="yyyy-MM-dd HH:mm:ss";
//      SimpleDateFormat sdf= new SimpleDateFormat(pattern);
//      String datestr=sdf.format(date);// format  为格式化方法
//      response.setReceiptTime(datestr);
//      response.setErrMsg("0");
//        //1、使用JSONObject
//        JSONObject json = JSONObject.fromObject(response);
//        //2、使用JSONArray
//        //JSONArray array=JSONArray.fromObject(stu);
//        //  String strArray=array.toString();
//        //System.out.println("strArray:"+strArray);
//
//        JSONObject jsonObject = new JSONObject();
//        //文件是一个数组,其实就是嵌套json
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("fileId", request.getFileID());
//        jsonObject1.put("fileName",  request.getFileName());
//        jsonObject1.put("fileObject",request.getFileObject());
//        jsonObject1.put("md5",request.getFileMD5());
//        //从此处可以看出其实list和json也是互相转换的
//        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
//        jsonObjects.add(jsonObject1);
//        jsonObject.put("ID",request.getId());
//        jsonObject.put("receiptTime",response.getReceiptTime());
//        jsonObject.put("errCode",response.getErrCode());
//        jsonObject.put("errMsg",response.getErrMsg());
//        jsonObject.put("files",jsonObjects);
//        System.out.println(jsonObject);
//        return response;
//        
//    }
//    
  //  @Override
//    public  OAResponse receiveFile(ReceiveRequest request){
//      OAResponse response = new OAResponse() ;
//      request.getAuditID();
//      OARequest oaRequest = request.getOaRequest();
//      request.getID();
//      response.setID(oaRequest.getId());
//      response.setErrMsg("0");
//      Date date= new Date(System.currentTimeMillis());
//      String pattern="yyyy-MM-dd HH:mm:ss";
//      SimpleDateFormat sdf= new SimpleDateFormat(pattern);
//      String datestr=sdf.format(date);// format  为格式化方法
//      response.setReceiptTime(datestr);
//      response.setErrCode("no");
//      JSONObject json = JSONObject.fromObject(response);
//      System.out.println(json);
//      return response;
//    }

 