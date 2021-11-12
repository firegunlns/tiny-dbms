package com.lns.tinydbms.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DBEngine {
    String dir;
    List<Table> tables;

    public boolean initDB(String path){
        File f = new File(path);
        if (f.exists() && f.canRead() && f.canWrite()) {
            dir = path;
            try {
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeChars("tiny-db");
                oos.write(tables.size());
                for (Table tab: tables) {
                    oos.writeObject(tab.getTableDef());
                }
                oos.close();
                fos.close();

                return true;
            }catch (Exception e){

            }
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

    public boolean save(){
        return true;
    }
}
