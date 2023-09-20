package simetrica;

import criptografia.*;

import javax.crypto.SecretKey;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.Scanner;
class Enviar implements Runnable {
    private Socket sc;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PublicKey publicaDestino;
    private SecretKey claveSimetrica;
    //private DataOutputStream salida;

    public Enviar(Socket so, PublicKey pubKey, PrivateKey privKey, PublicKey publicaDestino, SecretKey claveSimetrica) {
        sc = so;
        publicKey = pubKey;
        privateKey = privKey;
        this.publicaDestino = publicaDestino;
        this.claveSimetrica = claveSimetrica;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                ObjectOutputStream salida = new ObjectOutputStream(sc.getOutputStream());
                String mensaje = scanner.nextLine();
                Mensaje mnsj = new Mensaje(
                        Asimetrica.firmar(Hash.hashear(mensaje).getBytes("UTF8"),privateKey,"RSA"),
                        Simetrica.cifrarTextoAES(claveSimetrica,mensaje.getBytes("UTF8"))
                );
                salida.writeObject(mnsj);
                salida.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}