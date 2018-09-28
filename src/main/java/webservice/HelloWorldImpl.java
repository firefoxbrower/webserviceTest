package webservice;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import org.codehaus.xfire.util.Base64;

public class HelloWorldImpl implements HelloWorld {

    private RandomAccessFile raf=null;

    /**
     * @filename:要下载的文件名（全名）;
     * @startpost:由于WebService不能一次性传输大文件，所以使用startpost定位参数来实现断点续传；
     * @return:把文件内容Byte[]转换成Base64编码返回；
     */
    @Override
    public String download(String filename, long startpost) throws Exception {
        //System.out.println("要下载的文件名是："+filename);
        int BUFFER_LENGTH = 1024 * 20;//一次性读入大小
        int SLEEP_TIME=250;//循环读次数
        int time=0;
        long filesize=0;
        String ret=null;
        String str=null;
        File file=new File("e://",filename);
        if (file.exists()) {
            filesize=file.length();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);

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
        }

        return ret;
    }
    
    
}
