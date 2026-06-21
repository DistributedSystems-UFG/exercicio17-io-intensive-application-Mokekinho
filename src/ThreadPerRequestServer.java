import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerRequestServer {
    public static void main(String[] args) throws IOException {
        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor Thread-per-request rodando na porta " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}