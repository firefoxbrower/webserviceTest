package websvr.bean;

public class ReceiveRequest {
    
  public   String  ID ;
  public   String auditID ;
  public  OARequest  oaRequest;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuditID() {
        return auditID;
    }

    public void setAuditID(String auditID) {
        this.auditID = auditID;
    }

    public OARequest getOaRequest() {
        return oaRequest;
    }

    public void setOaRequest(OARequest oaRequest) {
        this.oaRequest = oaRequest;
    }
}
