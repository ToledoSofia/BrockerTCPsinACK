package criptografia;

import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Scanner;

public class Pepe {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public void iniciarCliente() {
        try {
            KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
            key.initialize(1024);
            KeyPair keyPair = key.generateKeyPair();
            PublicKey publicKey2 = keyPair.getPublic();
            PrivateKey privateKey2 = keyPair.getPrivate();

            privateKey = privateKey2;
            publicKey = publicKey2;
            //sc = new Socket(HOST, PUERTO);
            Socket sc = new Socket(HOST, PUERTO);
            //mandar clave
            ObjectOutputStream outputStream = new ObjectOutputStream(sc.getOutputStream());
            outputStream.writeObject(publicKey);
            outputStream.flush();
            System.out.println("se mando clave cliente");
            //recibir clave
            ObjectInputStream inputStream = new ObjectInputStream(sc.getInputStream());
            PublicKey publicaDestino = (PublicKey) inputStream.readObject();
            System.out.println("se recibio clave del server");


            Rigoberto rigo = new Rigoberto();

            Thread hiloRecibir = new Thread(new RecibirEncriptado(sc, rigo, publicKey, privateKey, publicaDestino));
            Thread hiloEnviar = new Thread(new EnviarEncriptado(sc, rigo, publicKey, privateKey, publicaDestino));

            hiloRecibir.start();
            hiloEnviar.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Pepe p = new Pepe();
        p.iniciarCliente();
 /*       Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();*/
    }
}

