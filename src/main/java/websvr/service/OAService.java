package websvr.service;

import websvr.bean.OARequest;
import websvr.bean.OAResponse;
import websvr.bean.ReceiveRequest;

public interface OAService {
    
   OAResponse sendFile(OARequest request);
   OAResponse receiveFile(ReceiveRequest request);
 
}
