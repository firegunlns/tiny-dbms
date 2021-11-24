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
            SQLiteParser.ParseContext tree = parser.parse();

            //SQLiteParserBaseListener listener = new SQLiteParserBaseListener();
            //SQLiteParserBaseVisitor<Void> tv = new SQLiteParserBaseVisitor<>();
            //tv.visit(tree);
            MySQLVisitor tv = new MySQLVisitor();
            SQLStatment stmt = tv.visit(tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
