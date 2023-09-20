package simetrica;

import criptografia.*;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Arrays;

public class Cliente2 {
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

            //mandar clave simetrica
            SecretKey claveSimetrica = Simetrica.generarClaveSecretaAES();
            byte[] llaveEncriptada = Simetrica.envolverClaveRSA(publicaDestino, claveSimetrica);
            ObjectOutputStream salida = new ObjectOutputStream(sc.getOutputStream());
            salida.writeObject(llaveEncriptada);
            System.out.println("Se mando la clave simetrica");


            Thread hiloRecibir = new Thread(new Recibir(sc, publicKey, privateKey, publicaDestino, claveSimetrica));
            Thread hiloEnviar = new Thread(new Enviar(sc, publicKey, privateKey, publicaDestino,claveSimetrica));

            hiloRecibir.start();
            hiloEnviar.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Cliente2 c1 = new Cliente2();
        c1.iniciarCliente();
    }
}
