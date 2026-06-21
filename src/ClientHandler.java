import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String filePath = "src/data.txt";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String request = in.readLine();

            if (request != null) {
                File file = new File(filePath);
                if (file.exists()) {
                    String content = new String(Files.readAllBytes(Paths.get(filePath)));
                    out.println("200 OK - Dados: " + content);
                } else {
                    out.println("404 Not Found - Crie um arquivo 'data.txt' no servidor.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}