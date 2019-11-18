package com.yc.redis.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 查看RESP协议格式
 * @auther wcc
 * @create 2019-09-23 11:13
 */
public class RedisServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6379);
        while (true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buf)) != -1){
                System.out.println(new String(buf, 0, len));
            }
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("+OK\\r\\n".getBytes());
        }

    }

}
