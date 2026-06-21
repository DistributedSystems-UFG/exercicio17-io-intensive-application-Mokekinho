import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor Single-threaded rodando na porta " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientHandler(clientSocket).run();
        }
    }
}