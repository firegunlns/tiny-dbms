package com.lns.tinydbms.parser;

import java.util.ArrayList;
import java.util.List;

public class MultiStatement extends SQLStatment{
    private List<SQLStatment> statementlst = new ArrayList<>();

    public List<SQLStatment> getStatementlst() {
        return statementlst;
    }

    public void setStatementlst(List<SQLStatment> statementlst) {
        this.statementlst = statementlst;
    }
}
