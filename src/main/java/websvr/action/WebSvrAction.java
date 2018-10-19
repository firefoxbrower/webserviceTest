package websvr.action;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.xfire.util.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import websvr.client.DynamicClientTest;
import websvr.client.WebServiceClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

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
        OARequest oaRequest = new OARequest();
        oaRequest.setFileName("dsdssd");
        oaRequest.setFileObject("sdds");
        WebServiceClient websvrClient  = WebServiceClient.getWebServiceClient();
        websvrClient.testClient(oaRequest);
         

    }

    public String download(InputStream fis,long filesize, long startpost) throws Exception {
        //System.out.println("要下载的文件名是："+filename);
        int BUFFER_LENGTH = 1024 * 20;//一次性读入大小
        int SLEEP_TIME=250;//循环读次数
        int time=0;
        String ret=null;
        String str=null;
        fis.skip(startpost);//先定位
        try {
            StringBuffer sb = new StringBuffer();
            System.out.println("定位："+startpost);
            fis.skip(startpost);//先定位
            byte[] buffer = new byte[BUFFER_LENGTH];
            int count;
            while (time<SLEEP_TIME && (count = fis.read(buffer)) != -1 ) {
                sb.append(Base64.encode(buffer,0,count));
                time++;
            }
            ret = sb.toString();
            System.out.println("输出："+ret.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("出错啦！", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("出错啦！", e);
        } catch (Exception e) {
            throw new Exception("出错啦！", e);
        } finally {
            fis.close();
        }
       

        return ret;
    }

    @RequestMapping(value="upload", method=RequestMethod.POST)
    public String upload(HttpServletRequest request ,HttpServletResponse response) throws Exception {
        response.setContentType("text/json;charset=utf-8");
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    System.out.println(file.getOriginalFilename());
                    //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
                    InputStream is=file.getInputStream();
                    long fileSize = file.getSize();
                    // 断点
                    long  startpost = 0;
                   String download = this.download(is, fileSize,startpost);
                    System.out.println(download);
                    // String path="E:/springUpload"+file.getOriginalFilename();
                    
                    
                    //获取输出流
                    // OutputStream os=new FileOutputStream("E:/springUpload"+new Date().getTime()+file.getOriginalFilename());
                    //上传
//                   file.transferTo(new File(path));
                    //获取输出流
//                   os.flush();
//                   os.close();
//                    is.close();

                    // 传递给webservice
                    WebServiceClient websvrClient  = WebServiceClient.getWebServiceClient();
                    OARequest oaRequest = new OARequest();
                    oaRequest.setFileID(new Integer(new Random().nextInt(4)).toString());
                    oaRequest.setFileName(file.getOriginalFilename());
                    oaRequest.setFileObject(download);
                    OAResponse oaResponse = websvrClient.testClient(oaRequest);
                    
                }

            }

        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        
        return "success";
    }

    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{
    

        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/101.jpg";

        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));

        String filename = "下载文件.jpg";

        filename = URLEncoder.encode(filename,"UTF-8");

        response.addHeader("Content-Disposition", "attachment;filename=" + filename);

        response.setContentType("multipart/form-data");

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}
