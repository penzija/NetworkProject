import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {

        try {
            Socket clientSocket = new Socket("localhost", 5051);
            OutputStream output = clientSocket.getOutputStream();
            PrintWriter sendOutput = new PrintWriter(output);

            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String lineReceived = inputFromServer.readLine();
                if (lineReceived == null || lineReceived.isEmpty()) {
                    break;
                }
            }
            sendOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendDatabaseMessage() {

        Gson gson = new Gson();

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Luka", 22, true));
        persons.add(new Person("Luka", 22, true));
        persons.add(new Person("Luka", 22, true));

        String jsonContentInText = gson.toJson(persons);
        byte[] jsonContentInBinary = jsonContentInText.getBytes();

        String header = "HTTP/1.1 200 OK\r\nContent-type: application/json\r\nContent-length: " + jsonContentInBinary.length + "\r\n\r\n";

       // OutputStream outputToClient = new OutputStream(() -> write(jsonContentInBinary);

//        outputToClient.write(header.getBytes());
//        outputToClient.write(jsonContentInBinary);
//        outputToClient.flush();
//    }
//            Socket clientSocket = new Socket("178.174.162.51", 5050);
    }
}
