package websvr.bean;

public class OAResponse {
    
    //响应参数 ID 请求ID
    //receiptTime 送达时间
    // errCode   返回代码
    // errMsg    返回信息
    String ID = null ;
    String receiptTime = null ;
    
    String errCode  = null ;
    String errMsg  = null ;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
