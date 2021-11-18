package com.lns.tinydbms.engine;

import java.io.File;
import java.io.RandomAccessFile;

public class IdxFile {

    final static int TAG = 0x2367;
    private String fname;
    private RandomAccessFile rf = null;

    public IdxFile(String fname){
        this.fname = fname;
    }

    public boolean createNew(){
        File f = new File(fname);
        try {
            f.createNewFile();
            rf = new RandomAccessFile(fname, "rw");
            rf.write(TAG);
        }catch (Exception e){

        }
        return false;
    }

    public boolean open(){
        try {
            File f = new File(fname);
            if (!f.exists()){
                createNew();
            }
            else
                rf = new RandomAccessFile(fname, "rw");
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean addRec(long pos){
        try {
            long file_len = rf.length();

            rf.seek(file_len);
            rf.write((byte)(pos));
            rf.write((byte)(pos >> 8));
            rf.write((byte)(pos >> 16));
            rf.write((byte)(pos >> 24));

            return true;
        }catch (Exception e){

        }

        return false;
    }
}
