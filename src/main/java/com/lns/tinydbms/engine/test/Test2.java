package com.lns.tinydbms.engine.test;

import org.junit.Test;

import java.io.File;

public class Test2 {
    @Test
    public void testDir(){
        File f = new File("./abc/def");
        System.out.println(f.isDirectory());
        boolean ret = f.mkdirs();
    }
}
