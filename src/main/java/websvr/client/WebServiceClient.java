package websvr.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import websvr.bean.ReceiveRequest;
import websvr.service.OAService;

public class WebServiceClient {

    private static ApplicationContext applicationContext ;
    private static WebServiceClient client ;
    public static WebServiceClient getWebServiceClient(){
        if(WebServiceClient.client==null){
            WebServiceClient.client = new WebServiceClient();
        }
        return WebServiceClient.client;
    }
    private WebServiceClient(){

    };

    private static  ApplicationContext getApplicationContext(String xmlName){
        if(WebServiceClient.applicationContext==null){
                WebServiceClient.applicationContext = new ClassPathXmlApplicationContext(xmlName);
        }
        return WebServiceClient.applicationContext;
    };

    
//    实例方法
    public String  testClient(String id ,String auditID ,String prjID ,String prjName,String fileTypeID,String fileType ,
                                 String submmitterID,String submmitterName,String fileID,String fileName,
                                 String fileObject ,String fileMD5 ){
        
        ApplicationContext ctx =  WebServiceClient.getApplicationContext("xfire-client.xml");
        OAService oaService =  oaService = ctx.getBean("testWebService",OAService.class);
        String  oaResponse =  oaService.receiveFile(id ,auditID,prjID ,prjName,fileTypeID, fileType ,
                submmitterID,submmitterName,fileID,fileName,fileObject , fileMD5 );
        return oaResponse;
    }

    
       
    

}
