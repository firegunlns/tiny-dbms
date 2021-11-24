package com.lns.tinydbms.parser;

import com.lns.tinydbms.engine.FieldDef;
import com.lns.tinydbms.engine.FieldType;
import com.lns.tinydbms.engine.TableDef;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class MySQLVisitor extends SQLiteParserBaseVisitor<SQLStatment>{
    @Override public SQLStatment visitSql_stmt_list(SQLiteParser.Sql_stmt_listContext ctx) {
        List<ParseTree> tree = ctx.children;

        MultiStatement stmt = new MultiStatement();
        for (ParseTree sub : ctx.children){
            SQLStatment st = sub.accept(this);
            if (st instanceof DDLStatment | st instanceof DMLStatement){
                stmt.getStatementlst().add(st);
            }
        }
        return stmt;
    }

    @Override public SQLStatment visitSql_stmt(SQLiteParser.Sql_stmtContext ctx) {

        SQLStatment stmt = visitChildren(ctx);
        return stmt;
    }

    @Override public SQLStatment visitCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        TableDef tableDef = new TableDef();
        tableDef.setName(ctx.table_name().getText());

        List<FieldDef> lst = new ArrayList<>();
        tableDef.setFieldDefs(lst);

        System.out.print("create table " + ctx.table_name().getText() + " (");
        List<SQLiteParser.Column_defContext> cols = ctx.column_def();
        for (SQLiteParser.Column_defContext col : cols){
            FieldDef fieldDef = new FieldDef();
            fieldDef.setName(col.column_name().getText());
            if (col.type_name().name(0).getText().toLowerCase().equals("varchar")) {
                fieldDef.setFieldType(FieldType.VARCHAR);
            }
            else if (col.type_name().name(0).getText().toLowerCase().equals("int")) {
                fieldDef.setFieldType(FieldType.INT);
            }
            else if (col.type_name().name(0).getText().toLowerCase().equals("datetime")) {
                fieldDef.setFieldType(FieldType.DATETIME);
            }

            lst.add(fieldDef);
            System.out.print(col.column_name().getText() + " " + col.type_name().getText() + ",");
        }

        CreateTableStatment stmt = new CreateTableStatment();
        stmt.setTableDef(tableDef);
        return stmt;
    }
}
