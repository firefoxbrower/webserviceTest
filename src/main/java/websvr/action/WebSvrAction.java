package websvr.action;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import websvr.client.DynamicClientTest;
import websvr.client.WebServiceClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebSvrAction extends MultiActionController {

    private static final String init_view = "initPage.jsp";
    
   
    // webservice 首页
    public ModelAndView goInitPage(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("text/html;charset=utf-8");
        ModelAndView view = new ModelAndView(init_view);
        return view;
    }
    
    public  void  websvrClient (HttpServletRequest request, HttpServletResponse response){
        //  response.setContentType("text/json;charset=utf-8");
        DynamicClientTest client =  new DynamicClientTest();
        
      
    }

    public  void  springClient (HttpServletRequest request, HttpServletResponse response){
         response.setContentType("text/json;charset=utf-8");
        WebServiceClient websvrClient  = new WebServiceClient();
        websvrClient.testClient();
         

    }
}
