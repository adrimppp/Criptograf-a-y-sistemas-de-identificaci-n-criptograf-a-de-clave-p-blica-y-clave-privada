Aquí tienes una versión mejorada y visualmente más clara del archivo `README.md`:

---

# Criptografía y Sistemas de Identificación: Comunicación Asíncrona con Clave Pública y Privada

Este programa simula la comunicación asíncrona entre un cliente y un servidor, utilizando criptografía de clave pública y privada para garantizar la seguridad de los mensajes. La comunicación se realiza mediante **sockets** y sus correspondientes **streams de entrada y salida**.

## 🛠️ Funcionalidad

El programa simula un sistema seguro de intercambio de mensajes que incluye:

1. **Intercambio de Claves**:
   - El cliente envía su clave pública al servidor y viceversa.
   - Cada parte cifra los mensajes con la clave pública del receptor y los descifra con su clave privada.

2. **Comunicación Segura**:
   - Los mensajes intercambiados están cifrados, protegiendo la comunicación incluso ante posibles ataques MiM (**Man-in-the-Middle**).

3. **Gestión Asíncrona**:
   - El servidor puede gestionar múltiples conexiones simultáneamente mediante hilos.

---

## 🚀 Estructura del Programa

### **1. Servidor**
- **Descripción**: Abre un `ServerSocket` que acepta todas las conexiones. Crea un nuevo hilo por cliente para gestionar las comunicaciones de forma paralela.
- **Gestión**: 
  - Cada hilo se ejecuta en la clase `GestorCliente`.

### **2. GestorCliente**
- **Descripción**: Responsable de gestionar las comunicaciones con un cliente específico.
- **Funciones**:
  - Recibir y enviar mensajes cifrados.
  - Asegurar la correcta comunicación entre cliente y servidor.

### **3. Cliente**
- **Descripción**: Se conecta al servidor para intercambiar mensajes.
- **Funciones**:
  - Solicita mensajes específicos al servidor.
  - Envía mensajes cifrados utilizando la clave pública del servidor.

### **4. Cliente y Servidor**
- Ambos generan sus claves pública y privada al iniciar.
- Comparten sus claves públicas al conectarse.
- Los mensajes son cifrados con la clave pública del receptor y descifrados con la clave privada propia.

---

## 📂 Clases Auxiliares

### **1. Utilidades**
- Contiene funciones comunes utilizadas tanto por el cliente como por el servidor.
- Ayuda a mantener el código limpio y reutilizable.

### **2. MensajesPredeterminados**
- Clase `utility` que define mensajes predefinidos para situaciones específicas.

---

## 🛠️ Tecnologías y Bibliotecas Usadas

- **`java.io`**: Para la entrada y salida de datos mediante sockets.
- **`java.net`**: Para la creación y gestión de sockets.
- **`java.security`**: Para la generación y manejo de claves pública y privada.
- **`javax.crypto`**: Para el cifrado y descifrado de mensajes.
- **`java.util`**: Para utilidades como `ArrayList`, `Random`, `Instant`, entre otros.

---

## ⚙️ Requisitos

- **JDK 8 o superior**: Se utiliza la clase `Base64` que requiere esta versión o posterior.
- **Sin Dependencias Adicionales**: No se necesitan librerías externas.

---

## 🧩 Diagrama de Funcionamiento

```plaintext
+------------------+        +------------------+
|      Cliente     |        |     Servidor     |
+------------------+        +------------------+
| Genera claves    |        | Genera claves    |
| pública/privada  |        | pública/privada  |
|                  | <----> |                  |
| Envía clave pub. |        | Envía clave pub. |
|                  |        |                  |
| Cifra mensaje    |        | Cifra mensaje    |
| Descifra con su  |        | Descifra con su  |
| clave privada    |        | clave privada    |
+------------------+        +------------------+
```

---

## 🛡️ Seguridad

- **Cifrado Asimétrico**: Asegura que solo el destinatario deseado pueda descifrar los mensajes.
- **Protección contra MiM**: Los mensajes cifrados no pueden ser leídos ni alterados por terceros que no posean las claves necesarias.

---

## 📦 Cómo Usar

- Simplemente iniciar la clase Principal, donde se iniciarán dos hilos de dos clases:
- Iniciar el .jar con :
   bash
    java -jar '.\Proyecto Unidad 6.jar'

1. **Servidor**:
   - Ejecuta la clase `Servidor`.
   - Acepta múltiples conexiones y gestiona clientes en hilos.

2. **Cliente**:
   - Ejecuta la clase `Cliente`.
   - Se conecta al servidor e intercambia mensajes seguros.
---  
## 🚨 Importante
El servidor utiliza un bloque while(true) en su ejecución principal. Este enfoque está diseñado para gestionar de manera hipotética un escenario con múltiples peticiones. Sin embargo, en este momento, solo se maneja una conexión en el hilo principal.
Por lo que para salir necesitará hacer un Ctrl+C desde consola



