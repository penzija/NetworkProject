public class Utils {

    public static String findInRequestStartOfURL(String inputFromClient) {
        int firstSpace = inputFromClient.indexOf(' ') + 1;
        int secondSpace = inputFromClient.indexOf(' ', firstSpace);
        return inputFromClient.substring(firstSpace, secondSpace);
    }

    public static String findInRequestCompleteURL(String inputFromClient) {
        var result = inputFromClient.split(" ");
        return result[1];
    }

    public static String parseTypeOfRequestHTTP(String inputFromClient) {
        return inputFromClient.substring(0, inputFromClient.indexOf(' '));
    }

    public String message() {
        return "Hello from Utils";
    }
}



