package com.lns.tinydbms.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class TableFile {
    private String filename;
    private RandomAccessFile rf = null;

    int TAG = 0x2367;

    public TableFile(String fname){
        this.filename = fname;
    }

    public boolean createNew(){
        File f = new File(filename);
        try {
            f.createNewFile();
            rf = new RandomAccessFile(filename, "rw");
            rf.write(TAG);
        }catch (Exception e){

        }
        return false;
    }
    public boolean open(){
        try {
            File f = new File(filename);
            if (!f.exists()){
                createNew();
            }
            else
                rf = new RandomAccessFile(filename, "rw");
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public long appendRec(byte[] data){
        try {
            long file_len = rf.length();
            rf.seek(file_len);

            int data_len = data.length;
            rf.write(data_len << 8 );
            rf.write(data_len >> 8 );
            rf.write(data);
            return file_len;
        }catch (Exception e){

        }

        return -1;
    }

    public boolean deleteRec(int pos){
        return true;
    }

    public boolean updateRec(int pos, byte[] data){
        return true;
    }
}
