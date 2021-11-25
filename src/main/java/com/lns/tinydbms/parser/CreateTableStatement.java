package com.lns.tinydbms.parser;

import com.lns.tinydbms.engine.TableDef;

public class CreateTableStatement extends DDLStatement {
    TableDef tableDef;

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }
}
