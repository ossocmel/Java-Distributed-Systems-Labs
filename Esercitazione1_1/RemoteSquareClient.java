package Esercitazione1_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RemoteSquareClient {
    // RemoteSquareClient hostname port
    static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.err.println("Usage: RemoteSquareClient hostname port");
                System.exit(1);
            }

            var userIn = new BufferedReader(new InputStreamReader(System.in));

            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            var netIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            var netOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            while (true) {
                System.out.println("Numero intero: ");
                String number = userIn.readLine();
                System.out.println("User input: " + number);
                if (number == null || number.equals("fine")) {
                    break;
                }

                if (!isNumber(number)) {
                    System.out.println("(!) Inserire un numero intero");
                } else {
                    netOut.write(number + "\n");
                    netOut.flush();
                    System.out.println("Inviato: " + number);

                    String ris = netIn.readLine();
                    System.out.println(ris);

                }
            }
            socket.close();

        } catch (Exception e) {
            System.err.println("Il server non è raggiungibile o non è attivo");
            System.err.println(e.getMessage());
        }
    }
}
