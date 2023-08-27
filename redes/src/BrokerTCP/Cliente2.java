package BrokerTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    public void iniciarCliente() {
        try {
            //sc = new Socket(HOST, PUERTO);
            Socket sc = new Socket(HOST, PUERTO);

            Thread hiloRecibir = new Thread(new Recibir(sc));
            Thread hiloEnviar = new Thread(new Enviar(sc));

            hiloRecibir.start();
            hiloEnviar.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}