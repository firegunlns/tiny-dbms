package com.lns.tinydbms.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Console {
    int port = 3606;
    int timeout = 1000*30; // in milliseconds
    Socket sk = new Socket();
    BufferedReader reader;
    BufferedWriter writer;
    boolean connected = false;

    public Console(){
    }

    public void run(){
        while(true){
            System.out.print("tiny: > ");
            BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = rd.readLine();
                String[] args = line.split("\\s");
                if (args.length >0){
                    if (args[0].equals("connect")) {
                        String server_addr = args[1];
                        String pattern = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

                        if (server_addr.matches(pattern)){
                            try {
                                sk.connect(new InetSocketAddress(server_addr, port), timeout);
                                System.out.println("tiny: > connected.");
                                reader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                                writer = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
                                connected = true;
                            } catch (IOException e) {
                                System.out.println("tiny: > connect failed");
                            }
                        }
                    }else if (args[0].equals("close")){
                        writer.write("close");
                        writer.newLine();
                        writer.flush();
                        sk.close();
                        System.out.println("bye");
                        break;
                    }else{
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                        do{
                            String response = reader.readLine();
                            System.out.println(response);
                        }while(reader.ready());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("tiny-dbms v0.1");
        Console console = new Console();
        console.run();
    }
}
