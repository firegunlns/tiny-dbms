package com.lns.tinydbms.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DBServer {
    int port = 3606;
    int minThreadNum = 4;
    int maxThreadNum = 100;
    int keepAliveTime = 60;

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
            System.out.println(String.format("receive client %s connected.", s.getRemoteSocketAddress().toString()));
            while (true) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String line = rd.readLine();
                if (line.equals("bye"))
                    break;
                System.out.println(String.format("%s", line));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean start(){
        try {
            // 创建线程池
            ExecutorService executor = new ThreadPoolExecutor(minThreadNum, maxThreadNum, keepAliveTime,
                    TimeUnit.MINUTES, null );
            executor.awaitTermination(1, TimeUnit.DAYS);

            // 监听端口
            ServerSocket sk = new ServerSocket(port);

            //
            System.out.println("tiny-dbms server started.");
            while(true) {
                Socket s = sk.accept();
                executor.submit(() -> process(s));
            }
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }catch ( InterruptedException e){
            e.printStackTrace();
        }

        return true;
    }

    boolean stop(){
        return true;
    }
}
