package criptografia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;
class RecibirEncriptado implements Runnable {
    private Socket sc;
    private Rigoberto rigoberto;
//    private DataOutputStream salida;

    public RecibirEncriptado(Socket so, Rigoberto rigo) {
        sc = so;
        rigoberto = rigo;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(sc.getInputStream());
            ObjectInputStream entradaClave = new ObjectInputStream(sc.getInputStream());

            PublicKey clavePublicaOtro = (PublicKey) entradaClave.readObject();
            while (true) {
                String mensajeRecibido = entrada.readUTF();
                System.out.println("Mensaje recibido: " + mensajeRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

