import com.google.gson.Gson;
import database.*;
import database.Person;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket socket = new ServerSocket(5051)) {
            while (true) {
                Socket connectedClient = socket.accept();

                System.out.println("THREAD --> " + Thread.currentThread().getName());
                InetAddress ipAddress = connectedClient.getInetAddress();
                System.out.println("CONNECTION FROM IP ADDRESS --> " + ipAddress);

                executorService.submit(() -> connectionHandling(connectedClient));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static void connectionHandling(Socket connectedClient) {
        try {
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
            OutputStream outputToClient = connectedClient.getOutputStream();

            String clientMessage = inputFromClient.readLine();

            System.out.println("CLIENT MESSAGE --> ' " + clientMessage + " '\n");

            if ((clientMessage.split(" ")[1]).equals("/cat.png")) {
                System.out.print("CAT PICTURE REQUESTED --> ");
                sendImageResponse(outputToClient);
            }
            if ((clientMessage.split(" ")[1]).equals("/index.html")) {
                System.out.print("HTML PICTURE REQUESTED --> ");
                sendHtmlResponse(outputToClient);
            }
            sendJsonResponse(outputToClient);

            connectedClient.close();
            inputFromClient.close();
            outputToClient.close();
//            inputFromClient.lines().forEach(System.out::println); //problem här
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendJsonResponse(OutputStream outputToClient) throws IOException {
        Gson gson = new Gson();
        Database database = new Database();
        List<Person> persons = database.showAll();

        persons.add(new Person("Luka"));

        String jsonContentInText = gson.toJson(persons);
        byte[] jsonContentInBinary = jsonContentInText.getBytes();

        String header = "HTTP/1.1 200 OK\r\nContent-type: application/json\r\nContent-length: " + jsonContentInBinary.length + "\r\n\r\n";

        outputToClient.write(header.getBytes());
        outputToClient.write(jsonContentInBinary);
        outputToClient.flush();
    }

    private static void sendImageResponse(OutputStream outputToClient) throws IOException {
        String header = "";
        byte[] data = new byte[0];
        File catFile = Path.of("core", "target", "web", "cat.png").toFile();
        Gson gson = new Gson();

        String contentType = Files.probeContentType(catFile.toPath());
        System.out.println(contentType);

        if (!(catFile.exists() && !catFile.isDirectory())) {
            header = "HTTP/1.1 404 Not found\r\nContent-length: 0\r\n\r\n";
            System.out.println("FILE NOT FOUND");
        } else {

            try (FileInputStream fileInputStream = new FileInputStream(catFile)) {
                data = new byte[(int) catFile.length()];
                int read = fileInputStream.read(data);
                header = "HTTP/1.1 200 OK\r\nContent-type: " + contentType + "\r\nContent-length: " + data.length + "\r\n\r\n";
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputToClient.write(header.getBytes());
        outputToClient.write(data);
        outputToClient.flush();
    }
    private static void sendHtmlResponse(OutputStream outputToClient) throws IOException {
        String header = "";
        byte[] data = new byte[0];
        File htmlFile = Path.of("core", "target", "web", "index.html").toFile();
        Gson gson = new Gson();

        String contentType = Files.probeContentType(htmlFile.toPath());
        System.out.println(contentType);

        if (!(htmlFile.exists() && !htmlFile.isDirectory())) {
            header = "HTTP/1.1 404 Not found\r\nContent-length: 0\r\n\r\n";
            System.out.println("FILE NOT FOUND");
        } else {

            try (FileInputStream fileInputStream = new FileInputStream(htmlFile)) {
                data = new byte[(int) htmlFile.length()];
                int read = fileInputStream.read(data);
                header = "HTTP/1.1 200 OK\r\nContent-type: " + contentType + "\r\nContent-length: " + data.length + "\r\n\r\n";
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputToClient.write(header.getBytes());
        outputToClient.write(data);
        outputToClient.flush();
    }

}