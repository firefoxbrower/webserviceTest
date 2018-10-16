package websvr.service;

import websvr.bean.OARequest;
import websvr.bean.OAResponse;

public interface OAService {
    
   OAResponse sendFile(OARequest request);
}
