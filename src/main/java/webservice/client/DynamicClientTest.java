package webservice.client;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.HttpTransport;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import org.codehaus.xfire.util.Base64;


public class DynamicClientTest {

    public Object[] getWebService(String surl,String saction,Object[] objarr) throws MalformedURLException,Exception {
        Client client = new Client(new URL(surl));
        //client.setProperty("mtom-enabled", "true");
        client.setProperty(HttpTransport.CHUNKING_ENABLED, "true");
        Object[] results = client.invoke(saction, objarr);
        return results;
    }

    public static void main(String[] args) throws MalformedURLException,Exception {
        String surl="http://localhost:8080/webserviceXfire/service/HelloWorld?wsdl";

        Long start=System.currentTimeMillis();
        File file=new File("e://","test.rar");
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
        System.out.println("文件大小："+file.length());
        raf.seek(file.length());//先定位
        boolean isend=false;
        while (!isend){

            DynamicClientTest web = new DynamicClientTest();
            Object[] results3=web.getWebService(surl, "download", new Object[]{"GMS.rar",file.length()});
            String data=(String)results3[0];
            System.out.println("返回大小："+data.length());
            byte[] bytes = Base64.decode(data);
            raf.write(bytes);
            raf.skipBytes(data.length());//顺序写
            if(data.length()<=0){isend=true;}
        }
        raf.close();

        Long end=System.currentTimeMillis();
        System.out.println("用时："+(end-start));
    }
}
