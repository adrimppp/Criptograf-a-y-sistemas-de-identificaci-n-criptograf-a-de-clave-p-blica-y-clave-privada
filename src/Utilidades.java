import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

/**
 * Clase Utilidades que contiene métodos auxiliares para la generación,
 * manipulación y uso de claves RSA, así como el cifrado y descifrado de mensajes.
 */
public class Utilidades {

    /**
     * Genera un par de claves (pública y privada) utilizando el algoritmo RSA.
     *
     * @return Un objeto KeyPair que contiene la clave pública y privada generadas.
     * @throws NoSuchAlgorithmException Si el algoritmo RSA no está disponible.
     */
    public static KeyPair generadorKeyPar() throws NoSuchAlgorithmException{
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048);
        return keygen.genKeyPair();
    }

    /**
     * Obtiene la clave pública de un KeyPair.
     *
     * @param keyPair Par de claves del que se extraerá la clave pública.
     * @return La clave pública del KeyPair proporcionado.
     */
    public static PublicKey generadorClavePublica(KeyPair keyPair){
        return keyPair.getPublic();
    }

    /**
     * Obtiene la clave privada de un KeyPair.
     *
     * @param keyPair Par de claves del que se extraerá la clave privada.
     * @return La clave privada del KeyPair proporcionado.
     */
    public static PrivateKey generadorClavePrivada(KeyPair keyPair){
        return keyPair.getPrivate();
    }

    /**
     * Genera un mensaje con formato que incluye la marca de tiempo, el usuario y el contenido del mensaje.
     *
     * @param usuario Nombre del usuario que genera el mensaje.
     * @param mensaje Contenido del mensaje.
     * @return Cadena formateada con marca de tiempo, usuario y contenido del mensaje.
     */
    public static String mensaje(String usuario, String mensaje){
        return Instant.now() + "||" + usuario + "||" + mensaje;
    }

    /**
     * Convierte una clave pública codificada en Base64 a un objeto PublicKey.
     *
     * @param clavePublicaBase64 Cadena en Base64 que representa la clave pública.
     * @return Un objeto PublicKey correspondiente a la clave proporcionada.
     */
    public static PublicKey convertirAClavePublica(String clavePublicaBase64){
        try {
            byte[] decodedKey = Base64.getDecoder().decode(clavePublicaBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Codifica una clave pública en una cadena Base64 para facilitar su transmisión.
     *
     * @param llavePublica La clave pública a codificar.
     * @return Cadena Base64 que representa la clave pública.
     */
    public static String codificarLlave(PublicKey llavePublica){
        return Base64.getEncoder().encodeToString(llavePublica.getEncoded());
    }

    /**
     * Decodifica una clave pública a partir de una cadena Base64.
     *
     * @param encodedKey Cadena Base64 que representa la clave pública.
     * @return Objeto PublicKey correspondiente a la clave decodificada.
     * @throws Exception Si ocurre algún error durante el proceso de decodificación.
     */
    public static PublicKey decodificarLlave(String encodedKey) throws Exception{
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * Cifra un mensaje utilizando una clave pública y el algoritmo RSA.
     *
     * @param mensaje El mensaje a cifrar.
     * @param llavePublica La clave pública utilizada para cifrar el mensaje.
     * @return Cadena Base64 que representa el mensaje cifrado.
     */
    public static String cifrarMensaje(String mensaje, PublicKey llavePublica){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, llavePublica);

            byte[] mensajeBytes = mensaje.getBytes();
            byte[] mensajeCifrado = cipher.doFinal(mensajeBytes);

            return Base64.getEncoder().encodeToString(mensajeCifrado);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Descifra un mensaje cifrado utilizando una clave privada y el algoritmo RSA.
     *
     * @param mensajeCifrado Cadena Base64 que representa el mensaje cifrado.
     * @param llavePrivada La clave privada utilizada para descifrar el mensaje.
     * @return El mensaje descifrado en forma de cadena.
     * @throws Exception Si ocurre algún error durante el proceso de descifrado.
     */
    public static String descifrarMensaje(String mensajeCifrado, PrivateKey llavePrivada) throws Exception{
        byte[] encryptedMessage = Base64.getDecoder().decode(mensajeCifrado);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, llavePrivada);

        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }
}
