package com.lns.tinydbms.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.lns.tinydbms.parser.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DBEngine 是DBMS的存取访问引擎类
 * 一个Engine 包括多个表，这些表的Schema存储在tables成员变量中，在Engine保存/关闭的时候会flush到磁盘
 * Engine将一个数据库的数据都存储在目录中，schema存储在json文件meta.json中，各个表的数据文件存储在2个文件中
 * 一个是表索引文件，一个是表数据文件
 * @author alan liu
 * @version 0.1
 */
public class DBEngine {

    enum EngineStatus {
        open, close, init
    };

    public EngineStatus getStatus() {
        return status;
    }

    public void setStatus(EngineStatus status) {
        this.status = status;
    }

    private EngineStatus status = EngineStatus.init;

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

    /**
     *  DBEngine的初始化，自动创建目录，如果目录不为空，初始化失败。
     * @param autoCreate 是否自动创建目录
     * @param path 数据库的目录
     *
     */
    public static boolean initDB(String path, boolean autoCreate){
        File f = new File(path);

        // 如果自动创建，并且目标目录不存在，尝试创建目录
        if (!f.exists() && autoCreate){
            boolean ret = f.mkdirs();
            if (!ret)
                return false;
        }

        // 目标目录必须存在而且可以读写
        if (!f.isDirectory() || !f.canRead() || !f.canWrite())
            return false;

        // 目标目录必须为空
        if (f.list().length >0)
            return false;

        // 创建初始化的schema文件
        DBEngine engine = new DBEngine();
        engine.setDir(path);
        engine.save();
        engine.close();

        return true;
    }

    public boolean close(){
        if (getStatus() == EngineStatus.open || getStatus()==EngineStatus.init){
            save();
            setStatus(EngineStatus.close);
            return true;
        }

        return false;
    }

    public static DBEngine open(String path){
        File f = new File(path);
        if (f.exists() && f.canRead() && f.canWrite()) {

            String metaFile = path + "/meta.json";
            FileInputStream fis = null;
            byte[] buf = new byte[1024*1024];
            try {
                fis = new FileInputStream(metaFile);
                int n = fis.read(buf);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String str = new String(buf);
            DBEngine engine =JSON.parseObject(str, DBEngine.class);
            return engine;
        }
        return null;

    }

    public boolean execSQL(String sql){
        try {
            CharStream input = CharStreams.fromString(sql);
            SQLiteLexer lexer=new SQLiteLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SQLiteParser parser = new SQLiteParser(tokens);
            SQLiteParser.ParseContext tree = parser.parse();

            SQLiteParserBaseListener listener = new SQLiteParserBaseListener();
            MySQLVisitor tv = new MySQLVisitor();
            SQLStatment statment = tv.visit(tree);
            if (statment instanceof CreateTableStatment){
                CreateTableStatment createTableStatment = (CreateTableStatment) statment;
                createTable(createTableStatment.getTableDef().getName(), createTableStatment.getTableDef().fieldDefs);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createTable(String name, List<FieldDef> fieldDefList){
        Table newTable = new Table();
        newTable.setName(name);
        TableDef tableDef = new TableDef();
        tableDef.setFieldDefs(fieldDefList);

        newTable.setTableDef(tableDef);
        File fname = new File(getDir(), newTable.getName());
        newTable.setFilename(fname.getAbsolutePath());
        newTable.open();

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
        if (getStatus()==EngineStatus.close)
            return false;

        try {
            String metaFile = dir + "/meta.json";
            FileOutputStream fos = new FileOutputStream(metaFile);
            String str = JSON.toJSONString(this);
            byte[] data = str.getBytes();
            fos.write(data);
            fos.close();

            for (Table tab : getTables()){
                tab.close();
            }
            return true;
        }catch (Exception e){

        }

        return true;
    }
}
