import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.*;
import java.util.Scanner;

/**
 * Clase Cliente que implementa Runnable para ejecutarse en un hilo independiente.
 * Este cliente establece una comunicación con un servidor, intercambia claves públicas,
 * envía mensajes cifrados y procesa las respuestas del servidor.
 */
public class Cliente implements Runnable {

    /**
     * Método principal de la ejecución del cliente.
     * Establece la conexión con el servidor, realiza el intercambio de claves públicas,
     * envía mensajes cifrados y procesa las respuestas.
     */
    @Override
    public void run() {
        try {
            // Scanner para leer datos de entrada del usuario
            Scanner escribir = new Scanner(System.in);

            // Generación del par de claves (pública y privada) del cliente
            KeyPair keyPair = Utilidades.generadorKeyPar();
            PublicKey publicKey = Utilidades.generadorClavePublica(keyPair);
            PrivateKey privateKey = Utilidades.generadorClavePrivada(keyPair);

            // Conectar al servidor en localhost, puerto 80
            Socket socket = new Socket("localhost", 80);

            // Crear los streams de entrada y salida para comunicación con el servidor
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Recibir la clave pública del servidor
            PublicKey llavePublicaServidor = Utilidades.convertirAClavePublica(entrada.readLine());

            // Enviar la clave pública del cliente al servidor
            salida.println(Utilidades.codificarLlave(publicKey));

            // Recibir y descifrar mensajes del servidor, la bienvenida y los consejos que nos puede dar
            System.out.println(Utilidades.descifrarMensaje(entrada.readLine(), privateKey));
            System.out.println(Utilidades.descifrarMensaje(entrada.readLine(), privateKey));

            // Leer un número de consejo que quiere el usuario
            int numeroEntregar = escribir.nextInt();
            salida.println(Utilidades.cifrarMensaje(String.valueOf(numeroEntregar), llavePublicaServidor));

            // Enviar un mensaje personalizado cifrado al servidor
            salida.println(mensajeCliente("He enviado el número " +String.valueOf(numeroEntregar), llavePublicaServidor));

            // Recibir y descifrar más mensajes del servidor, en este caso son el consejo y la despedida
            System.out.println(Utilidades.descifrarMensaje(entrada.readLine(), privateKey));
            System.out.println(Utilidades.descifrarMensaje(entrada.readLine(), privateKey));

            // Cerrar el socket
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Genera un mensaje cifrado con el prefijo "Cliente" utilizando la clave pública del servidor.
     *
     * @param mensaje El mensaje que se desea enviar.
     * @param llavePublicaServidor La clave pública del servidor para cifrar el mensaje.
     * @return Mensaje cifrado con el prefijo "Cliente".
     */
    public static String mensajeCliente(String mensaje, PublicKey llavePublicaServidor){
        return Utilidades.cifrarMensaje(Utilidades.mensaje("Cliente", mensaje), llavePublicaServidor);
    }

}
