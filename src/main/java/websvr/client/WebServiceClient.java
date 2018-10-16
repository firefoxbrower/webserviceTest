package websvr.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import websvr.bean.OARequest;
import websvr.service.OAService;

public class WebServiceClient {

    public void testClient() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "client.xml");
     OAService oaService = (OAService) ctx.getBean("testWebService");
        OARequest oaRequest = new OARequest();
        System.out.println(oaService.sendFile(oaRequest).toString());
    }
    
    

}
