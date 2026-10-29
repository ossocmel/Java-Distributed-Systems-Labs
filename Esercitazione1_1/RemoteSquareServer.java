package Esercitazione1_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteSquareServer {
    // RemoteSquareServer port
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println("Usage: RemoteSquareServer <port>");
                System.exit(1);
            }
            ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));

            while (true) {
                try (Socket ns = ss.accept()) {

                    var netIn = new BufferedReader(new InputStreamReader(ns.getInputStream(), "UTF-8"));
                    var netOut = new BufferedWriter(new OutputStreamWriter(ns.getOutputStream(), "UTF-8"));

                    String line;
                    while ((line = netIn.readLine()) != null) {
                        System.out.println("Ricevuto: " + line);
                        try {
                            long req_n = Long.parseLong(line);
                            long ris = req_n * req_n;
                            System.out.println("Risultato: " + ris);

                            netOut.write(Long.toString(ris) + "\n");
                            System.out.println("Inviato: " + ris);
                            netOut.flush();
                        } catch (Exception e) {
                            netOut.write("(!) Inserisci un numero");
                            netOut.flush();
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace(System.err);
                    System.exit(2);
                }
            }
        } catch (Exception ss) {
            System.err.println(ss.getMessage());
            System.exit(3);
        }
    }
}
