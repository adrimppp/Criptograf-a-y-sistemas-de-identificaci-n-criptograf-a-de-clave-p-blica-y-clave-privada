/**
 * Clase principal que inicia tanto el servidor como el cliente en diferentes hilos.
 * Esta configuración permite que ambos se ejecuten de manera concurrente para facilitar la comunicación.
 */
public class Principal {

    /**
     * Método principal que lanza el servidor y el cliente.
     *
     * El servidor se ejecuta en un hilo independiente, permitiendo que pueda gestionar múltiples clientes.
     * El cliente también se ejecuta en su propio hilo para interactuar con el servidor.
     */
    public static void main(String[] args) {
        // Inicia el servidor en un hilo
        new Thread(new Servidor()).start();

        // Inicia el cliente en otro hilo
        new Thread(new Cliente()).start();
    }
}