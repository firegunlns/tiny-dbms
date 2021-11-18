package com.lns.tinydbms.engine;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TableFile {
    private String filename;
    private RandomAccessFile rf = null;

    final static byte[] TAG = "tabf".getBytes(StandardCharsets.US_ASCII);

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

    public boolean close(){
        if (rf != null) {
            try {
                rf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

    public long appendRec(byte[] data){
        try {
            long file_len = rf.length();
            rf.seek(file_len);

            int data_len = data.length;
            rf.writeInt(data_len);
            rf.write(data);
            return file_len;
        }catch (Exception e){

        }

        return -1;
    }

    public byte[] readData(long pos){
        try{
            rf.seek(pos);
            int rec_size = rf.readInt();
            byte[] rec_data = new byte[rec_size];
            rf.read(rec_data);
            return rec_data;
        }catch (Exception e){

        }
        return null;
    }

    public boolean deleteRec(int pos){
        return true;
    }

    public boolean updateRec(int pos, byte[] data){
        return true;
    }
}
