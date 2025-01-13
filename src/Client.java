import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8030;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Подключен к серверу");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder sonnet = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sonnet.append(line).append("\n");
            }

            System.out.println("Получен от сервера:\n");
            System.out.println(sonnet.toString());
        } catch (IOException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());
        }
    }
}
