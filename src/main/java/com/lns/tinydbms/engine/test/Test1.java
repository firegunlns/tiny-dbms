package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.engine.DBEngine;
import com.lns.tinydbms.engine.FieldDef;
import com.lns.tinydbms.engine.FieldType;
import com.lns.tinydbms.engine.Table;
import org.junit.Test;

import java.util.ArrayList;

public class Test1 {
    @Test
    public void testDBEngine(){
        DBEngine engine = DBEngine.initDB("/Users/alan/work/learn/java/tiny-dbms/tmp/db1");
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
    public void testTable(){
        DBEngine engine = DBEngine.openDB("/Users/alan/work/learn/java/tiny-dbms/tmp/db1");
        Table test = engine.getTable("test");
        //test.insert();
    }
}
