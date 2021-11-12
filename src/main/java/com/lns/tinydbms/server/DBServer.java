package com.lns.tinydbms.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBServer {
    int port = 3606;

    static void printUsage(){
        System.out.println("dbserver usage:");
    }

    public static void main(String[] args){
        if ((args.length >1) && args[1].equals("help")){
            printUsage();
            return;
        }

        new DBServer().start();
    }

    DBServer(){

    }

    void process(Socket s){
        try {
            long clientId = Thread.currentThread().getId();
            System.out.printf("client from %s connected, thread id is %d.%n", s.getRemoteSocketAddress().toString(), clientId);
            while (true) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String line = rd.readLine();
                String[] args = line.split("\\s");
                if (args.length > 0) {
                    if (args[0].equals("close")) {
                        System.out.printf("client %d: closed.%n", clientId);
                        break;
                    }
                    else if (args[0].equals("create") && args[1].equals("table")){
                        // create table
                    }
                    else {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                        writer.write("bad command");
                        writer.newLine();
                        writer.flush();

                        System.out.println("bad command");
                    }
                }
                System.out.printf("client %d: %s%n", clientId, line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void start(){
        try {
            // 创建线程池
            ExecutorService executor = Executors.newCachedThreadPool();

            // 监听端口
            ServerSocket sk = new ServerSocket(port);

            System.out.println("tiny-dbms server started.");
            while(true) {
                Socket s = sk.accept();
                executor.submit(() -> process(s));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
