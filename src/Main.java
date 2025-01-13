import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int PORT = 8030;
    private static final String SONNETS_FILE = "121.txt";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String sonnet = getRandomSonnet();
                    if (sonnet != null) {
                        out.println(sonnet);
                        System.out.println("Сонет отправлен клиенту.");
                    } else {
                        out.println("Сонеты не найдены.");
                        System.out.println("Файл сонетов пуст или отсутствует.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private static String getRandomSonnet() {
        List<String> sonnets = new ArrayList<>();
        StringBuilder currentSonnet = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(SONNETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { // Конец текущего сонета
                    if (currentSonnet.length() > 0) {
                        sonnets.add(currentSonnet.toString().trim());
                        currentSonnet.setLength(0); // Очистка буфера
                    }
                } else {
                    currentSonnet.append(line).append("\n");
                }
            }
            // Добавление последнего сонета, если файл не заканчивается пустой строкой
            if (currentSonnet.length() > 0) {
                sonnets.add(currentSonnet.toString().trim());
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        if (sonnets.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return sonnets.get(random.nextInt(sonnets.size()));
    }
}
