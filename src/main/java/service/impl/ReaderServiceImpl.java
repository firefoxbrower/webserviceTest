package service.impl;

import bean.Reader;
import service.IReaderService;

import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements IReaderService {
    @Override
    public Reader getReader(String name, String password) {
        return  new Reader(name,password);
     
    }

    @Override
    public List<Reader> getReaders() {
        List<Reader> readerList = new ArrayList<Reader>();
        readerList.add(new Reader("shun1","123"));
        readerList.add(new Reader("shun2","123"));
        return readerList;
    }
}
