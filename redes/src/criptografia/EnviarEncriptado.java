package criptografia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;
class EnviarEncriptado implements Runnable {
    private Socket sc;
    private Rigoberto rigoberto;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PublicKey publicaDestino;
    //private DataOutputStream salida;

    public EnviarEncriptado(Socket so, Rigoberto rigo, PublicKey pubKey, PrivateKey privKey, PublicKey publicaDestino) {
        sc = so;
        rigoberto = rigo;
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
                Mensaje mnsj = new Mensaje(Asimetrica.firmar(mensaje.getBytes(),privateKey,"RSA"),Asimetrica.encriptar(mensaje.getBytes(),publicaDestino,"RSA"));

                salida.writeObject(mnsj);
                salida.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


