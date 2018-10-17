package websvr.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import websvr.service.OAService;

public class WebServiceClient {

    public OAResponse testClient(OARequest oaRequest) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "xfire-client.xml");
     OAService oaService = (OAService) ctx.getBean("testWebService");
     OAResponse oaResponse =   oaService.sendFile(oaRequest);
        System.out.println(oaService.sendFile(oaRequest).toString());
        return oaResponse;
        
    }
    
    

}
