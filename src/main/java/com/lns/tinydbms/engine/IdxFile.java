package com.lns.tinydbms.engine;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class IdxFile {

    final static byte[] TAG = "idxf".getBytes(StandardCharsets.US_ASCII);
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

    public boolean close(){
        if (rf != null){
            try {
                rf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    public boolean addRec(long pos){
        try {
            long file_len = rf.length();

            rf.seek(file_len);
            rf.writeLong(pos);

            return true;
        }catch (Exception e){

        }

        return false;
    }

    public long getAt(int pos){
        try {
            long file_len = rf.length();
            long offset = TAG.length + pos * 8;

            if (offset < file_len){
                rf.seek(offset);
                long tabPos = rf.readLong();
                return tabPos;
            }
        }catch (Exception e){

        }

        return -1;
    }
}
