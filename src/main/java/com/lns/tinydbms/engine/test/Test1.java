package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.engine.DBEngine;
import com.lns.tinydbms.engine.FieldDef;
import com.lns.tinydbms.engine.FieldType;
import com.lns.tinydbms.engine.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test1 {
    @Test
    public void testInitDB(){
        boolean ret = DBEngine.initDB("./tmp/db1", true);
        assert(ret);
    }

    @Test
    public void testCreateTable(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        FieldDef fd1 = new FieldDef();
        fd1.setName("id");
        fd1.setFieldType(FieldType.INT);

        FieldDef fd2 = new FieldDef();
        fd2.setName("name");
        fd2.setFieldType(FieldType.VARCHAR);

        FieldDef fd3 = new FieldDef();
        fd3.setName("address");
        fd3.setFieldType(FieldType.VARCHAR);

        ArrayList<FieldDef> lst = new ArrayList<>();
        lst.add(fd1);
        lst.add(fd2);
        lst.add(fd3);

        engine.createTable("test", lst);
        engine.close();
    }

    @Test
    public void testListTables(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        List<Table> tables = engine.getTables();
        for (Table tab : tables){
            System.out.println("table " + tab.getName() + "( ");
            for (FieldDef fld : tab.getTableDef().getFieldDefs()){
                System.out.println(fld.getName() + " " + fld.getFieldType().name() + ",");
            }
            System.out.println(")");
        }
    }

    @Test
    public void testInsert(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        Table test = engine.getTable("test");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", new Integer(1) );
        map.put("name", "lns" );
        map.put("address", "bei jing hui long guan");
        test.insert(map);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("id", new Integer(2) );
        map1.put("name", "huang wan ying" );
        map1.put("address", "he nan provicene ping ding shan");
        test.insert(map1);

        test.flush();
    }

    @Test
    public void testScan(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        Table test = engine.getTable("test");

        System.out.println("select * from test");
        List<HashMap<String, Object>> lst = test.scan(0, 2);
        for (HashMap<String, Object> map : lst){
            for (int i = 0; i < test.getTableDef().getFieldDefs().size(); i++) {
                String key = test.getTableDef().getFieldDefs().get(i).getName();
                System.out.print(key + ":" + map.get(key) + ", ");
            }
            System.out.println();
        }
        engine.close();
    }

    @Test
    public void loadFromMySQL(){
        // create table

    }

    @Test
    public void testAll(){
        testInitDB();
        testCreateTable();
        testListTables();
        testInsert();
        testScan();
    }
}
