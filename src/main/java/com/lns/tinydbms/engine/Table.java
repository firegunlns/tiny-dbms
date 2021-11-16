package com.lns.tinydbms.engine;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;
import java.io.Serializable;
import java.util.List;

// v1.0 表的数据都可以存储在内存
public class Table implements Serializable {
    @JSONField(name="name")
    String name;

    @JSONField(name="filename")
    String filename;

    @JSONField(name="schema")
    TableDef tableDef;

    List<Record> row_cache;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }


    public boolean drop(){
        File f = new File(filename);
        f.delete();
        return true;
    }

    public boolean insert(byte[] data){
        Record rec = new Record();
        rec.setData(data);
        rec.setDirty(true);
        rec.setDeleted(false);

        row_cache.add(rec);

        return false;
    }

    public boolean flush(){
        for (Record rec: row_cache){
            if (rec.isDirty() == true){
                if (rec.isDeleted()){
                    // delete the record

                }
                else if (rec.isNewrec()) {
                    // insert a new

                }
                else {
                    // update
                }
            }
        }
        return true;
    }

    public boolean delete(List<Integer> recIds){
        for (Integer id: recIds) {
            Record rec = new Record();
            rec.setDirty(true);
            rec.setDeleted(true);
            row_cache.add(rec);
        }

        return true;
    }

    public boolean update(){
        return false;
    }

    public Record query(){
        return null;
    }
}
