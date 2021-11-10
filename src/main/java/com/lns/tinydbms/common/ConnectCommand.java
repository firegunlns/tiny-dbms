package com.lns.tinydbms.common;

import java.util.ArrayList;

public class ConnectCommand implements Cmd{
    @Override
    public CmdType getType() {
        return null;
    }

    @Override
    boolean parse(String line){
        String[] args = line.split("\\s");
        if (line.startsWith("connect"))
    }

    @Override
    public void process() {

    }
}
