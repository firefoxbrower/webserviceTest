package websvr.service;

import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OAServiceImpl implements  OAService {
    

    @Override
    public OAResponse sendFile(OARequest request) {
       
        // 请求的文件信息
      
        
      OAResponse response = new OAResponse() ;
      response.setID(request.getId());
      Date date= new Date(System.currentTimeMillis());
      String pattern="yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf= new SimpleDateFormat(pattern);
      String datestr=sdf.format(date);// format  为格式化方法
      response.setReceiptTime(datestr);
      response.setErrMsg("0");
        //1、使用JSONObject
        JSONObject json = JSONObject.fromObject(response);
        //2、使用JSONArray
        //JSONArray array=JSONArray.fromObject(stu);
        //  String strArray=array.toString();
        //System.out.println("strArray:"+strArray);

        JSONObject jsonObject = new JSONObject();
        //文件是一个数组,其实就是嵌套json
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("fileId", request.getFileID());
        jsonObject1.put("fileName",  request.getFileName());
        jsonObject1.put("fileObject",request.getFileObject());
        //从此处可以看出其实list和json也是互相转换的
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        jsonObjects.add(jsonObject1);
        jsonObject.put("ID",request.getId());
        jsonObject.put("receiptTime",response.getReceiptTime());
        jsonObject.put("errCode",response.getErrCode());
        jsonObject.put("errMsg",response.getErrMsg());
        jsonObject.put("files",jsonObjects);
        System.out.println(jsonObject);
        return response;
        
    }
    
    
    
}
