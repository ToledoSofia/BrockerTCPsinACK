package BrokerTCP;

import criptografia.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.Scanner;
class Enviar implements Runnable {
    private Socket sc;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PublicKey publicaDestino;
    //private DataOutputStream salida;

    public Enviar(Socket so, PublicKey pubKey, PrivateKey privKey, PublicKey publicaDestino) {
        sc = so;
        publicKey = pubKey;
        privateKey = privKey;
        this.publicaDestino = publicaDestino;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            ObjectOutputStream salida = new ObjectOutputStream(sc.getOutputStream());
            while (true) {
                String mensaje = scanner.nextLine();
                Mensaje mnsj = new Mensaje(Asimetrica.firmar(Hash.hashear(mensaje).getBytes(),privateKey,"RSA"),Asimetrica.encriptar(mensaje.getBytes(),publicaDestino,"RSA"));

                salida.writeObject(mnsj);
                salida.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




/*
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
class Enviar implements Runnable {
    private Socket sc;
    //private DataOutputStream salida;

    public Enviar(Socket so) {
        sc = so;
    }

    @Override
    public void run() {
        try {
            DataOutputStream salida = new DataOutputStream(sc.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String mensaje = scanner.nextLine();
                salida.writeUTF(mensaje);
                salida.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
