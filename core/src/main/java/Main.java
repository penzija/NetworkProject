import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(5051)) {
            while (true) {
                Socket theClient = socket.accept();
                InetAddress ipAddress = theClient.getInetAddress();
                System.out.println("IP ADDRESS: " + ipAddress);
                System.out.println();

//                InputStream inputFromClient = theClient.getInputStream();
//                System.out.println(inputFromClient);
                System.out.println();

                BufferedReader inputFromClientBetter = new BufferedReader(new InputStreamReader(theClient.getInputStream()));
                while (true) {
                    String lineReceived = inputFromClientBetter.readLine();
                    if (lineReceived == null || lineReceived.isEmpty()) {
                        break;
                    }
                    System.out.println(lineReceived);
                }
                inputFromClientBetter.lines().forEach(System.out::println); //problem här

                PrintWriter outputToClient = new PrintWriter(theClient.getOutputStream());
                outputToClient.print("HTTP/1.1 404 Not found\r\nContent-length: 0\r\n\r\n");
                outputToClient.flush();

                theClient.close();
//                inputFromClient.close();
                outputToClient.close();
                inputFromClientBetter.close();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
