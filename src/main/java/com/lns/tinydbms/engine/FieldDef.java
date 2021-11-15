package com.lns.tinydbms.engine;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class FieldDef implements Serializable {
    @JSONField(name="name")
    String name;

    @JSONField(name="field_type")
    FieldType fieldType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
