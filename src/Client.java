import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        int port = 8082;

        int totalRequests = 2000;
        int concurrentClients = 200;

        ExecutorService executor = Executors.newFixedThreadPool(concurrentClients);
        CountDownLatch latch = new CountDownLatch(totalRequests);

        System.out.println("Iniciando teste na porta " + port + "...");
        System.out.println("Disparando " + totalRequests + " requisições...");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < totalRequests; i++) {
            executor.submit(() -> {
                try (
                        Socket socket = new Socket("localhost", port);
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    out.println("GET_FILE");

                    in.readLine();
                } catch (IOException e) {
                    System.err.println("Erro na conexão: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        long endTime = System.currentTimeMillis();
        executor.shutdown();


        long totalTimeMs = endTime - startTime;
        double totalTimeSec = totalTimeMs / 1000.0;
        double throughput = totalRequests / totalTimeSec;

        System.out.println("RESULTADOS DO TESTE");
        System.out.println("Tempo total: " + totalTimeMs + " ms (" + totalTimeSec + " segundos)");
        System.out.printf("Vazão (Throughput): %.2f requisições/segundo\n", throughput);

    }
}