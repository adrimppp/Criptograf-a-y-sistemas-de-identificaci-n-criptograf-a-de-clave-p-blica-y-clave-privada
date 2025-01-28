import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

/**
 * Clase Servidor que implementa Runnable para ejecutarse en un hilo independiente.
 * Este servidor escucha conexiones en el puerto 80 y delega la gestión de cada cliente a un gestor dedicado.
 */
public class Servidor implements Runnable {

    /**
     * Método principal de ejecución del servidor.
     * El servidor crea un ServerSocket para escuchar conexiones entrantes y,
     * por cada cliente conectado, lanza un nuevo hilo que maneja la comunicación con dicho cliente.
     */
    @Override
    public void run() {
        try {
            // Crear un ServerSocket para escuchar en el puerto 80
            ServerSocket serverSocket = new ServerSocket(80);

            // Bucle infinito para aceptar conexiones de clientes
            while (true) {
                // Aceptar una conexión entrante
                Socket socket = serverSocket.accept();

                // Crear un nuevo hilo para manejar la conexión con el cliente
                new Thread(new GestorClientes(socket)).start();
            }
        } catch (IOException e) {
            // Capturar y lanzar cualquier excepción de entrada/salida
            throw new RuntimeException(e);
        }
    }
}
