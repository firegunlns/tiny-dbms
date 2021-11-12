package com.lns.tinydbms.engine;

import java.util.List;

// v1.0 表的数据都可以存储在内存
public class Table {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }

    public List<Record> getRows() {
        return rows;
    }

    public void setRows(List<Record> rows) {
        this.rows = rows;
    }

    TableDef tableDef;
    List<Record> rows;
}
