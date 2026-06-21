import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
    public static void main(String[] args) {
        int port = 8082;
        int poolSize = 10;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            ExecutorService pool = Executors.newFixedThreadPool(poolSize);
            System.out.println("Servidor Thread Pool rodando na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}