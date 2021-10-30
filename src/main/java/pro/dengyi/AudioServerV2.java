package pro.dengyi;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dengyi (email:dengyi@dengyi.pro)
 * @date 2021-10-06
 */
public class AudioServerV2 {


    public static void main(String[] args) throws IOException, LineUnavailableException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("启动服务器....");
        File file = new File("C:\\Users\\BLab\\Desktop\\au.raw");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        if (!file.exists()) {
            file.createNewFile();
        }
        //阻塞等待客户端连接
        Socket socket = serverSocket.accept();
        //播放
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);
        SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, 1024);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        socket.getInetAddress();
        System.out.println("客户端:" + InetAddress.getLocalHost() + "已连接到服务器");
        // 装饰流BufferedReader封装输入流（接收客户端的流）
        BufferedInputStream bis = new BufferedInputStream(
                socket.getInputStream());
        byte[] buffer = new byte[2];

        while (bis.read(buffer) != -1) {
//            fileOutputStream.write(buffer);
//            fileOutputStream.flush();
            sourceDataLine.write(buffer, 0, 2);
            //播放
            //executorService.execute(new PlayThread(sourceDataLine,buffer));


        }


    }

}
