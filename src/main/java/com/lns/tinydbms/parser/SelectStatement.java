package com.lns.tinydbms.parser;

import java.util.List;

public class SelectStatement extends DMLStatement {
    List<Expression> result_cols;
    List<TableLike> tableOrViewsList;
    Expression where;

}
