import java.net.*;
import java.io.*;
import java.util.Random;

public class NumberGuessServer {
    public static void main(String[] args) throws IOException {
        int portNumber = 5050;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Servidor iniciado en el puerto " + portNumber);

        try {
            Socket player1Socket = serverSocket.accept();
            System.out.println("Jugador 1 se ha conectado");
            Socket player2Socket = serverSocket.accept();
            System.out.println("Jugador 2 se ha conectado");

            Random rand = new Random();
            int numberToGuess = rand.nextInt(100) + 1;
            System.out.println("El número a adivinar es " + numberToGuess);

            DataOutputStream player1Out = new DataOutputStream(player1Socket.getOutputStream());
            player1Out.writeUTF("¡Bienvenido! Adivina un número entre 1 y 100.");
            DataOutputStream player2Out = new DataOutputStream(player2Socket.getOutputStream());
            player2Out.writeUTF("¡Bienvenido! Adivina un número entre 1 y 100.");

            boolean gameOver = false;
            while (!gameOver) {
                DataInputStream player1In = new DataInputStream(player1Socket.getInputStream());
                int player1Guess = player1In.readInt();
                System.out.println("Jugador 1 adivinó " + player1Guess);
                if (player1Guess == numberToGuess) {
                    player1Out.writeUTF("¡Felicidades, ganaste!");
                    player2Out.writeUTF("¡Lo siento, perdiste!");
                    gameOver = true;
                    break;
                } else {
                    player1Out.writeUTF("No, intenta de nuevo.");
                }

                DataInputStream player2In = new DataInputStream(player2Socket.getInputStream());
                int player2Guess = player2In.readInt();
                System.out.println("Jugador 2 adivinó " + player2Guess);
                if (player2Guess == numberToGuess) {
                    player2Out.writeUTF("¡Felicidades, ganaste!");
                    player1Out.writeUTF("¡Lo siento, perdiste!");
                    gameOver = true;
                    break;
                } else {
                    player2Out.writeUTF("No, intenta de nuevo.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error en la comunicación con los jugadores: " + e.getMessage());
        } finally {
            serverSocket.close();
        }
    }
}
