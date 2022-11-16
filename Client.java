package client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try( Socket socket = new Socket("192.168.1.211", 8000) ) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            writer.println("Hello World - åäö@£$€");

            //InputStreamReader reader = new InputStreamReader(input);
            //int character = reader.read();  // reads a single character
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while (true) {
                String line = reader.readLine();    // reads a line of text
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
