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

    public List<Record> getRows() {
        return rows;
    }

    public void setRows(List<Record> rows) {
        this.rows = rows;
    }

    @JSONField(name="schema")
    TableDef tableDef;
    List<Record> rows;

    public boolean drop(){
        File f = new File(filename);
        f.delete();
        return true;
    }

    public boolean insert(Record rec){
        return false;
    }

    public boolean delete(){
        return false;
    }

    public boolean update(){
        return false;
    }

    public Record query(){
        return null;
    }
}
