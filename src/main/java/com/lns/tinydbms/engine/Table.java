package com.lns.tinydbms.engine;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// v1.0 表的数据都可以存储在内存
public class Table implements Serializable {
    @JSONField(name="name")
    String name;

    @JSONField(name="filename")
    String filename;

    @JSONField(name="schema")
    TableDef tableDef;

    List<Record> row_cache = new ArrayList<>();

    TableFile tableFile;
    IdxFile idxFile;

    boolean isOpen = false;

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

    public boolean open(){
        if (tableFile == null) {
            tableFile = new TableFile(getFilename() + ".dat");
            tableFile.open();

            idxFile = new IdxFile(getFilename() + ".idx");
            idxFile.open();
        }

        return true;
    }

    public boolean close(){
        if (tableFile != null) {
            flush();
            tableFile.close();
            idxFile.close();

            return true;
        }

        return false;
    }

    public boolean insert(HashMap<String, Object> data){
        open();

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            for (int i = 0; i < tableDef.fieldDefs.size(); i++){
                String name = tableDef.fieldDefs.get(i).getName();
                Object val = data.get(name);
                oos.writeObject(val);
            }

            oos.close();
            bos.close();

            insert(bos.toByteArray());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean insert(byte[] data){
        open();

        Record rec = new Record();
        rec.setData(data);
        rec.setDirty(true);
        rec.setDeleted(false);
        rec.setNewrec(true);

        row_cache.add(rec);

        return false;
    }

    public boolean flush(){
        open();

        for (Record rec: row_cache){
            if (rec.isDirty() == true){
                if (rec.isDeleted()){
                    // delete the record

                }
                else if (rec.isNewrec()) {
                    // insert a new
                    long pos = tableFile.appendRec(rec.data);
                    idxFile.addRec(pos);
                }
                else {
                    // update
                }

                rec.setDirty(false);
            }
        }
        return true;
    }

    public boolean delete(List<Integer> recIds){
        open();

        for (Integer id: recIds) {
            Record rec = new Record();
            rec.setDirty(true);
            rec.setDeleted(true);
            row_cache.add(rec);
        }

        return true;
    }

    public boolean update(){
        open();
        return false;
    }

    public List<HashMap<String, Object>> scan(int start, int num){
        open();

        List<HashMap<String, Object>> lst = new ArrayList<>();

        for (int n = 0 ; n < num; n ++) {
            long data_off = idxFile.getAt(start + n);
            if (data_off >0) {
                byte[] record = tableFile.readData(data_off);
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(record);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    HashMap<String, Object> map = new HashMap<>();

                    for (int i = 0; i < tableDef.fieldDefs.size(); i++) {
                        FieldDef def = tableDef.fieldDefs.get(i);
                        Object obj = ois.readObject();
                        map.put(def.getName(), obj);
                    }

                    lst.add(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return lst;
    }
}
