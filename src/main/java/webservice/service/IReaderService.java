package webservice.service;
import websvr.bean.Reader;
import java.util.List;

public interface IReaderService {
    
    public Reader getReader(String name, String password);
    public List<Reader> getReaders();
}
