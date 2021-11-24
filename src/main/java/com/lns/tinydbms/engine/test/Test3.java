package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.engine.SQLEngine;
import com.lns.tinydbms.parser.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Test3 {

    @Test
    public void testParse(){
        try {
            CharStream input = CharStreams.fromString("create table test(id int, age int, address varchar(32));");
            SQLiteLexer lexer=new SQLiteLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SQLiteParser parser = new SQLiteParser(tokens);
            SQLiteParser.Sql_stmt_listContext tree = parser.sql_stmt_list();

            MySQLVisitor tv = new MySQLVisitor();
            SQLStatment stmt = tv.visit(tree);
            if (stmt instanceof MultiStatement){
                for (SQLStatment st : ((MultiStatement)stmt).getStatementlst()){
                    if (st instanceof CreateTableStatment){

                    }
                }
                System.out.println("hello");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
