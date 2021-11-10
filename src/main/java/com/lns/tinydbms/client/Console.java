package com.lns.tinydbms.client;

import com.lns.tinydbms.common.Cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Console {
    int timeout = 120; // ms

    public Console(){
    }

    public Cmd parseArgs(String line){
        return null;
    }

    public void run(){
        Client cli = new Client();

        while(true){
            //Socket sk = new Socket();
            //sk.connect(new InetSocketAddress(), timeout);
            System.out.print("tiny: >");
            BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = rd.readLine();
                Cmd cmd = parseArgs(line);
                if (cmd != null)
                    cmd.process();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    }

    public static void main(String[] args){
        System.out.println("tiny-dbms v0.1");
        Console console = new Console();
        Console.run();

}
