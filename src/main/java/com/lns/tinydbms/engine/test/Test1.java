package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.engine.DBEngine;
import com.lns.tinydbms.engine.FieldDef;
import com.lns.tinydbms.engine.FieldType;
import com.lns.tinydbms.engine.Table;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        assert(engine != null);
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
        assert(engine != null);
        List<Table> tables = engine.getTables();
        for (Table tab : tables){
            System.out.print("table " + tab.getName() + "( ");
            for (FieldDef fld : tab.getTableDef().getFieldDefs()){
                System.out.print(fld.getName() + " " + fld.getFieldType().name() + ",");
            }
            System.out.println(")");
        }
    }

    @Test
    public void testInsert(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        assert(engine != null);
        Table test = engine.getTable("test");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "lns" );
        map.put("address", "bei jing hui long guan");
        test.insert(map);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("id", 2);
        map1.put("name", "huang wan ying" );
        map1.put("address", "he nan provicene ping ding shan");
        test.insert(map1);

        test.flush();
    }

    @Test
    public void testScan1(){
        testScan("./tmp/db1", "test", 0, 2);
    }

    @Test
    public void testScan2(){
        testScan("./tmp/db1", "commodity", 0, 10000000);
    }

    @Test
    public void testScanTiming(){
        String db = "./tmp/db1";
        int start = 0;
        int num = 300000;
        String table = "commodity";

        DBEngine engine = DBEngine.open(db);
        assert(engine != null);
        Table test = engine.getTable(table);

        System.out.println("select * from test");
        long t0 = System.currentTimeMillis();
        List<HashMap<String, Object>> lst = test.scan(start, num);
        long t1 = System.currentTimeMillis();
        System.out.println("time used is " + (t1 - t0) + " ms.");
        engine.close();
    }

    public void testScan(String db, String table, int start, int num){
        DBEngine engine = DBEngine.open(db);
        assert(engine != null);
        Table test = engine.getTable(table);

        System.out.println("select * from test");
        int count = 0;
        List<HashMap<String, Object>> lst = test.scan(start, num);
        for (HashMap<String, Object> map : lst){
            count ++;
            for (int i = 0; i < test.getTableDef().getFieldDefs().size(); i++) {
                String key = test.getTableDef().getFieldDefs().get(i).getName();
                System.out.print(key + ":" + map.get(key) + ", ");
            }
            System.out.println();
        }

        System.out.println("totoal " + count + " records.");
        engine.close();
    }

    @Test
    public void prepateTable(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        assert(engine != null);

        FieldDef fd1 = new FieldDef();
        fd1.setName("id");
        fd1.setFieldType(FieldType.INT);

        FieldDef fd2 = new FieldDef();
        fd2.setName("guid");
        fd2.setFieldType(FieldType.VARCHAR);

        FieldDef fd3 = new FieldDef();
        fd3.setName("commodity_no");
        fd3.setFieldType(FieldType.VARCHAR);

        FieldDef fd4 = new FieldDef();
        fd4.setName("name");
        fd4.setFieldType(FieldType.VARCHAR);

        FieldDef fd5 = new FieldDef();
        fd5.setName("commodity_pic");
        fd5.setFieldType(FieldType.VARCHAR);

        ArrayList<FieldDef> lst = new ArrayList<>();
        lst.add(fd1);
        lst.add(fd2);
        lst.add(fd3);
        lst.add(fd4);
        lst.add(fd5);

        engine.createTable("commodity", lst);
        engine.close();
    }

    @Test
    public void loadFromMySQL(){
        DBEngine engine = DBEngine.open("./tmp/db1");
        assert(engine != null);
        Table commodity = engine.getTable("commodity");

        // create table
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/openroad?user=root&password=11111111");
            PreparedStatement ps = conn.prepareStatement("select id, guid, commodity_no, name, commodity_pic from commodity");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String guid = rs.getString(2);
                String commodity_no = rs.getString(3);
                String name = rs.getString(4);
                String pic = rs.getString(5);

                HashMap<String, Object> rec = new HashMap<>();
                rec.put("id", id);
                rec.put("guid", guid);
                rec.put("commodity_no", commodity_no);
                rec.put("name", name);
                rec.put("pic", pic);

                commodity.insert(rec);
            }
            commodity.close();
            engine.close();
            rs.close();
            ps.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testAll(){
        testInitDB();
        testCreateTable();
        testListTables();
        testInsert();
        testScan1();
    }
}
