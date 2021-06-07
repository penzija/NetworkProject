import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

    private static void connectionHandling(Socket clientRequest) {
        try {
//            System.out.println(this.getClass().getName());
            InetAddress ipAddress = clientRequest.getInetAddress();
            System.out.println("IP ADDRESS: " + ipAddress);
            System.out.println();

//            InputStream inputFromClient = clientRequest.getInputStream();
//            System.out.println(inputFromClient);
//            System.out.println();

            BufferedReader inputFromClient2 = new BufferedReader(new InputStreamReader(clientRequest.getInputStream()));
            readRequest(inputFromClient2);
//
            OutputStream outputToClient = clientRequest.getOutputStream();
            sendResponse(outputToClient);

            clientRequest.close();
//            inputFromClient.close();
            inputFromClient2.close();
            outputToClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readRequest(BufferedReader inputFromClient2) throws IOException {
//        inputFromClient2.lines().forEach(System.out::println); //problem här

        while (true) {
            String lineReceived = inputFromClient2.readLine();
            if (lineReceived == null || lineReceived.isEmpty()) {
                break;
            }
            System.out.println(lineReceived);
        }
    }

    private static void sendResponse(OutputStream outputToClient) throws IOException {
//        outputToClient.print("HTTP/1.1 404 Not found\r\nContent-length: 0\r\n\r\n");

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Luka", 22, true));
        persons.add(new Person("Luka", 22, true));
        persons.add(new Person("Luka", 22, true));
        Gson gson = new Gson();

        String json = gson.toJson(persons);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        String header = "HTTP/1.1 200 OK\r\nContent-type: application/json\r\nContent-length: " + bytes.length + "\r\n\r\n";

        outputToClient.write(header.getBytes());
        outputToClient.write(bytes);
        outputToClient.flush();
    }
}