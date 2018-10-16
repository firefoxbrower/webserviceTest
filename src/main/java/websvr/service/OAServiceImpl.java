package websvr.service;

import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OAServiceImpl implements  OAService {
    

    @Override
    public OAResponse sendFile(OARequest request) {
        request.getId();
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
        String strJson=json.toString();
        System.out.println("strJson:"+strJson);
        return response;
    }
    
    
    
}
