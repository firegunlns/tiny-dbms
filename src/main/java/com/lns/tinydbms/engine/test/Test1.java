package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.engine.DBEngine;
import com.lns.tinydbms.engine.FieldDef;
import com.lns.tinydbms.engine.FieldType;
import com.lns.tinydbms.engine.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class Test1 {
    @Test
    public void testDBEngine(){

        DBEngine engine = DBEngine.initDB("./tmp/db1");
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
        engine.save();
    }

    @Test
    public void testInsert(){
        DBEngine engine = DBEngine.openDB("./tmp/db1");
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

        test.flush();
    }

    @Test
    public void testSelect(){

    }

}
