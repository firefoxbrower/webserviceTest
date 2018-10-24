package websvr.bean;

import java.util.UUID;

public class OARequest {

    private static final long serialVersionUID = -5809782578272943999L;
    //请求ID
   // String id = UUID.randomUUID().toString().replaceAll("-", "");
    String id = null ;
    //项目ID
    String prjId  = null;
    // 项目名字
    String prjName = null ;
    // 文件类型ID
    String fileTypeID = null ;
    // 文件类型
    String fileType = null ;
    // 提交人
    String submitterID = null ;
    // 提交人姓名
    String  submitterName = null ;
    //文件ID
    String fileID = null ;
    //  文件名
    String fileName = null ;

    // 文件对象
    String fileObject = null ;

    // 文件MD5
    String fileMD5 = null ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getFileTypeID() {
        return fileTypeID;
    }

    public void setFileTypeID(String fileTypeID) {
        this.fileTypeID = fileTypeID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSubmitterID() {
        return submitterID;
    }

    public void setSubmitterID(String submitterID) {
        this.submitterID = submitterID;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileObject() {
        return fileObject;
    }

    public void setFileObject(String fileObject) {
        this.fileObject = fileObject;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }
    
}
