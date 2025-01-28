import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Clase GestorClientes que implementa Runnable para manejar la comunicación
 * con un cliente conectado al servidor. Proporciona funcionalidades como el
 * intercambio de claves públicas y el envío de consejos basados en la solicitud del cliente.
 */
public class GestorClientes implements Runnable {

    private Socket socketConexion;
    private static BufferedReader entrada;
    private static PrintWriter salida;

    // Listas de consejos amorosos y financieros
    static ArrayList<String> listaConsejosAmorosos = new ArrayList<>(Arrays.asList(
            "Comunicación es clave en cualquier relación.",
            "Escucha más de lo que hablas.",
            "Nunca pierdas tu independencia.",
            "Aprende a perdonar y olvidar.",
            "El respeto mutuo es fundamental.",
            "Haz sentir especial a tu pareja todos los días.",
            "No tengas miedo de ser vulnerable."
    ));

    static ArrayList<String> listaConsejosFinancieros = new ArrayList<>(Arrays.asList(
            "Ahorra al menos el 20% de tus ingresos cada mes.",
            "Evita las deudas innecesarias, especialmente las de consumo.",
            "Haz un presupuesto mensual y cúmplelo.",
            "Diversifica tus inversiones para reducir riesgos.",
            "Tienes que tener un fondo de emergencia, mínimo de 3 a 6 meses de tus gastos.",
            "Revisa tus hábitos de consumo y busca maneras de reducir gastos.",
            "Invierte en educación financiera para tomar mejores decisiones.",
            "No gastes más de lo que ganas.",
            "Busca siempre negociar mejores precios o condiciones de pago.",
            "Aprovecha los intereses compuestos invirtiendo a largo plazo."
    ));

    /**
     * Constructor de la clase GestorClientes.
     * Inicializa la conexión con el cliente mediante los streams de entrada y salida.
     *
     * @param socketConexion Socket asociado a la conexión con el cliente.
     * @throws IOException Si ocurre un error al obtener los streams de entrada/salida.
     */
    public GestorClientes(Socket socketConexion) throws IOException {
        this.socketConexion = socketConexion;
        entrada = new BufferedReader(new InputStreamReader(socketConexion.getInputStream()));
        salida = new PrintWriter(socketConexion.getOutputStream(), true);
    }

    /**
     * Método principal del hilo que gestiona al cliente.
     * Realiza el intercambio de claves públicas, envía mensajes predeterminados
     * y responde a las solicitudes de consejos según el tipo seleccionado por el cliente.
     */
    @Override
    public void run() {
        try {
            // Generar claves pública y privada para el servidor
            KeyPair keyPair = Utilidades.generadorKeyPar();
            PublicKey publicKey = Utilidades.generadorClavePublica(keyPair);
            PrivateKey privateKey = Utilidades.generadorClavePrivada(keyPair);

            // Enviar la clave pública del servidor al cliente
            salida.println(Utilidades.codificarLlave(publicKey));

            // Recibir la clave pública del cliente
            PublicKey llavePublicaCliente = Utilidades.convertirAClavePublica(entrada.readLine());

            // Enviar mensaje de bienvenida cifrado al cliente
            salida.println(mensajeServidor(MensajesPredeterminados.BIENVENIDA.getMensaje(), llavePublicaCliente));

            // Gestionar el envío de consejos
            consejosEnviar(llavePublicaCliente, privateKey);

            // Enviar mensaje de despedida cifrado al cliente
            salida.println(mensajeServidor(MensajesPredeterminados.DESPEDIDA.getMensaje(), llavePublicaCliente));

            // Cerrar la conexión con el cliente
            socketConexion.close();

            // Esperar antes de finalizar el hilo
            Thread.sleep(1000);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Servidor --> Gestion con cliente terminada");
    }

    /**
     * Genera un mensaje cifrado con el prefijo "Servidor".
     *
     * @param mensaje El mensaje a enviar.
     * @param llavePublicaCliente La clave pública del cliente para cifrar el mensaje.
     * @return Mensaje cifrado.
     */
    public static String mensajeServidor(String mensaje, PublicKey llavePublicaCliente) {
        return Utilidades.cifrarMensaje(Utilidades.mensaje("Servidor", mensaje), llavePublicaCliente);
    }

    /**
     * Envía un consejo al cliente basado en el tipo seleccionado (amoroso o financiero).
     *
     * @param llavePublicaCliente La clave pública del cliente para cifrar los mensajes.
     * @param llavePrivadaServidor La clave privada del servidor para descifrar mensajes del cliente.
     * @throws Exception Si ocurre un error durante el proceso.
     */
    public static void consejosEnviar(PublicKey llavePublicaCliente, PrivateKey llavePrivadaServidor) throws Exception {
        // Enviar mensaje para solicitar tipo de consejo
        salida.println(mensajeServidor(MensajesPredeterminados.TIPO_CONSEJO.getMensaje(), llavePublicaCliente));

        // Leer la respuesta del cliente
        String consejo = "";
        int numRespuesta = Integer.parseInt(Utilidades.descifrarMensaje(entrada.readLine(), llavePrivadaServidor));

        // Leer un mensaje adicional del cliente
        System.out.println(Utilidades.descifrarMensaje(entrada.readLine(), llavePrivadaServidor));

        // Seleccionar el tipo de consejo según la respuesta
        switch (numRespuesta) {
            case 1:
                consejo = consejoAmoroso();
                break;
            case 2:
                consejo = consejoFinanciero();
                break;
            default:
                salida.println(mensajeServidor(MensajesPredeterminados.CODIGO_INVALIDO.getMensaje(), llavePublicaCliente));
        }

        // Enviar el consejo cifrado al cliente
        salida.println(mensajeServidor(consejo, llavePublicaCliente));
    }

    /**
     * Obtiene un consejo amoroso aleatorio de la lista.
     *
     * @return Consejo amoroso aleatorio.
     */
    public static String consejoAmoroso(){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaConsejosAmorosos.size());
        return listaConsejosAmorosos.get(indiceAleatorio);
    }

    /**
     * Obtiene un consejo financiero aleatorio de la lista.
     *
     * @return Consejo financiero aleatorio.
     */
    public static String consejoFinanciero(){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaConsejosFinancieros.size());
        return listaConsejosFinancieros.get(indiceAleatorio);
    }
}
