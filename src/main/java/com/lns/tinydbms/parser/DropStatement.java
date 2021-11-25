package com.lns.tinydbms.parser;

public class DropStatement extends DDLStatement {
    public boolean isNotExist() {
        return notExist;
    }

    public void setNotExist(boolean notExist) {
        this.notExist = notExist;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    boolean notExist;
    String targetType;  // table, index, database
    String target;
}
