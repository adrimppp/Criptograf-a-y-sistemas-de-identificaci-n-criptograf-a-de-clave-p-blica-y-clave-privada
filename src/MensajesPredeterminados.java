public enum MensajesPredeterminados {
    BIENVENIDA("BIENVENIDO AL SERVIDOR DONDE OBTENDRÁS UNA FRASE SOBRE TU FUTURO"),
    TIPO_CONSEJO("¿SOBRE QUÉ QUIERES OBTENER CONSEJO? 1. AMOR 2. DINERO"),
    CODIGO_INVALIDO("INTRODUCE UN CODIGO VALIDO"),
    DESPEDIDA("ADIOS, VUELVA PRONTO");

    private final String mensaje; // Campo para almacenar el mensaje

    // Constructor del enum
    MensajesPredeterminados(String mensaje) {
        this.mensaje = mensaje;
    }

    // Método para obtener el mensaje
    public String getMensaje() {
        return mensaje;
    }
}
