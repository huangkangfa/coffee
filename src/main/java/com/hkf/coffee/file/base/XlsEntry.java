package com.hkf.coffee.file.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class XlsEntry {
    private List<String[]> data;

    public XlsEntry(){
        data=new ArrayList<String[]>();
    }

    public List<String[]> getData() {
        return data;
    }
}
