package com.lns.tinydbms.engine.test;

import com.lns.tinydbms.parser.SQLiteLexer;
import com.lns.tinydbms.parser.SQLiteParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

public class Test3 {
    @Test
    public void testParse(){
        try {
            CharStream input = new ANTLRFileStream("tmp/Hello.sql");
            SQLiteLexer lexer = new SQLiteLexer(input);
            CommonTokenStream commonStream = new CommonTokenStream(lexer);
            SQLiteParser parseTree = new SQLiteParser(commonStream);
            
            //  parseTree.parse();
            //SQLiteParser. ctx = parseTree.classDeclaration();

            //System.out.println(ctx.className().getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
