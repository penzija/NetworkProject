import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
//            Socket clientSocket = new Socket("178.174.162.51", 5050);
            Socket clientSocket = new Socket("localhost", 5051);
            OutputStream output = clientSocket.getOutputStream();
            PrintWriter sendOutput = new PrintWriter(output);
            sendOutput.println("Hello from client Luka\r\n\r\n");
            sendOutput.flush();

            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String lineReceived = inputFromServer.readLine();
                if (lineReceived == null || lineReceived.isEmpty()) {
                    break;
                }
                System.out.println(lineReceived);
            }
            sendOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
