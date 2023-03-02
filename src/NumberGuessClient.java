import java.net.*;
import java.io.*;
import java.util.Scanner;

public class NumberGuessClient {
    public static void main(String[] args) throws IOException {
        String hostname = "192.168.1.3";
        int portNumber = 5050;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String playerName = scanner.nextLine();

        try (
                Socket socket = new Socket(hostname, portNumber);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Conectado al servidor. Esperando instrucciones...");
            String message = in.readUTF();
            System.out.println(message);
            boolean gameOver = false;
            while (!gameOver) {
                message = in.readUTF();
                System.out.println(message);

                System.out.print("Intente adivinar un número: ");
                int guess = scanner.nextInt();
                out.writeInt(guess);

                message = in.readUTF();
                System.out.println(message);

                if (message.startsWith("¡Felicidades")) {
                    gameOver = true;
                }
            }

            socket.close();
            System.out.println("Juego terminado. Gracias por jugar.");
        } catch (IOException e) {
            System.out.println("Error en la comunicación con el servidor: " + e.getMessage());
        }
    }
}

