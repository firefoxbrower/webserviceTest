package webservice.service;


import websvr.bean.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderService implements IReaderService{
    
    private Reader reader ;

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Reader getReader(String name, String password) {
        
        reader.setName(name);
        reader.setPassword(password);
        return reader;    
    }

     @Override
    public List<Reader> getReaders(){
        List<Reader> readerList = new ArrayList<Reader>();
        readerList.add(new Reader("shun1","123"));
        readerList.add(new Reader("shun2","123"));
        return readerList;
    } 
}