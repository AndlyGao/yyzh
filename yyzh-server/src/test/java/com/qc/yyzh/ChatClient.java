package com.qc.yyzh;

/**
 * @author qc
 * @date 2019/5/1
 */


import java.io.*;
import java.net.Socket;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        String host = "129.211.12.216";
        int port = 9999;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);
        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String device= "55556,1";
        outputStream.write(device.getBytes("utf-8"));
        System.out.println(inputStream.read());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
//            System.out.println("4");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                System.out.println("5");
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sb.toString());

    }
}