package com.lns.tinydbms.engine;

import java.io.Serializable;
import java.util.List;

public class TableDef implements Serializable {
    public List<FieldDef> getFieldDefs() {
        return fieldDefs;
    }

    public void setFieldDefs(List<FieldDef> fieldDefs) {
        this.fieldDefs = fieldDefs;
    }

    List<FieldDef> fieldDefs;
}
