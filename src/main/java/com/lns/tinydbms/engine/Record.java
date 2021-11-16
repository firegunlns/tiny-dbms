package com.lns.tinydbms.engine;

public class Record {
    boolean dirty;
    boolean deleted;
    boolean newrec;

    int rowId;
    byte[] data;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }


    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isNewrec() {
        return newrec;
    }

    public void setNewrec(boolean newrec) {
        this.newrec = newrec;
    }
}
