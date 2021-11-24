package com.lns.tinydbms.parser;

import com.lns.tinydbms.engine.TableDef;

public class CreateTableStatment extends DDLStatment {
    TableDef tableDef;

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }
}
