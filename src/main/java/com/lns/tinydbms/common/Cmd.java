package com.lns.tinydbms.common;

import com.lns.tinydbms.common.CmdType;

public interface Cmd {
    CmdType getType();
    boolean parse();
    void process();
}
