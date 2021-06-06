import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket socket = new ServerSocket(5051)) {
            while (true) {
                Socket theClient = socket.accept();
                System.out.println(Thread.currentThread().getName());
//                Thread thread = new Thread(() -> connectionHandling(theClient));
//                thread.start();
                executorService.submit(() -> connectionHandling(theClient));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static void connectionHandling(Socket theClient) {
        try {
//            System.out.println(this.getClass().getName());
            InetAddress ipAddress = theClient.getInetAddress();
            System.out.println("IP ADDRESS: " + ipAddress);
            System.out.println();

//            InputStream inputFromClient = theClient.getInputStream();
//            System.out.println(inputFromClient);
//            System.out.println();

            BufferedReader inputFromClientBetter = new BufferedReader(new InputStreamReader(theClient.getInputStream()));
//                inputFromClientBetter.lines().forEach(System.out::println); //problem här
            while (true) {
                String lineReceived = inputFromClientBetter.readLine();
                if (lineReceived == null || lineReceived.isEmpty()) {
                    break;
                }
                System.out.println(lineReceived);
            }
            PrintWriter outputToClient = new PrintWriter(theClient.getOutputStream());
            outputToClient.print("HTTP/1.1 404 Not found\r\nContent-length: 0\r\n\r\n");
            outputToClient.flush();

            theClient.close();
//                inputFromClient.close();
            outputToClient.close();
            inputFromClientBetter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}