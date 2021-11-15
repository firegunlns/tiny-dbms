package com.lns.tinydbms.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBEngine {
    @JSONField(name="dir")
    String dir;

    @JSONField(name="tables")
    List<Table> tables = new ArrayList<>();

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public boolean initDB(String path){
        File f = new File(path);
        if (f.exists() && f.canRead() && f.canWrite()) {
            dir = path;
            save();
        }
        return false;
    }

    public boolean openDB(String path){
        File f = new File(path);
        if (f.exists() && f.canRead() && f.canWrite()) {
            dir = path;
            return true;
        }
        return false;

    }

    public boolean createTable(String name, List<FieldDef> fieldDefList){
        Table newTable = new Table();
        newTable.setName(name);
        TableDef tableDef = new TableDef();
        tableDef.setFieldDefs(fieldDefList);

        newTable.setTableDef(tableDef);

        this.tables.add(newTable);

        return true;
    }

    public Table getTable(String name){
        for (Table tab: this.tables){
            if (tab.getName().equals(name)) {
                return tab;
            }
        }
        return null;
    }

    public boolean dropTable(String name){
        for (Table tab: this.tables){
            if (tab.getName().equals(name)) {
                tab.drop();
                tables.remove(tab);
            }
        }
        return true;
    }

    public boolean save(){
        try {
            String metaFile = dir + "/meta.json";
            FileOutputStream fos = new FileOutputStream(metaFile);
            String str = JSON.toJSONString(this);
            byte[] data = str.getBytes();
            fos.write(data);
            fos.close();

            return true;
        }catch (Exception e){

        }

        return true;
    }
}
