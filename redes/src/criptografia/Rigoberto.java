package criptografia;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Scanner;

public class Rigoberto {
    private static ServerSocket sc;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private HashMap<String, HashMap<Socket,PublicKey>>TopicosClientes;
    private static HashMap<Socket, PublicKey>Clientes;

    public static ServerSocket getSc() {
        return sc;
    }

    public static void setSc(ServerSocket sc) {
        Rigoberto.sc = sc;
    }

    public HashMap<String, HashMap<Socket, PublicKey>> getTopicosClientes() {
        return TopicosClientes;
    }

    public void setTopicosClientes(HashMap<String, HashMap<Socket, PublicKey>> topicosClientes) {
        TopicosClientes = topicosClientes;
    }

    public HashMap<Socket, PublicKey> getClientes() {
        return Clientes;
    }

    public void setClientes(HashMap<Socket, PublicKey> clientes) {
        Clientes = clientes;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Rigoberto() {

    }
    public static void main(String[] args) throws IOException {
        Rigoberto sb = new Rigoberto();
        sc = new ServerSocket(5000);
        String mensajeRecibido;
        try {
            KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
            key.initialize(1024);
            KeyPair keyPair = key.generateKeyPair();
            PublicKey publicKey2 = keyPair.getPublic();
            PrivateKey privateKey2 = keyPair.getPrivate();

            privateKey = privateKey2;
            publicKey = publicKey2;

            while (true) {
                Socket so;
                so = sc.accept();
                System.out.println("Se conecto uno");

                //guardar clave publica
                ObjectInputStream inputStream = new ObjectInputStream(so.getInputStream());
                PublicKey publicaCliente = (PublicKey) inputStream.readObject();
                System.out.println("se recibio clave del cliente");

                //mandar clave publica
                ObjectOutputStream outputStream = new ObjectOutputStream(so.getOutputStream());
                outputStream.writeObject(publicKey);
                outputStream.flush();
                System.out.println("se mando clave del server");


                Runnable hiloEnviar = new EnviarEncriptado(so, sb,publicKey,privateKey, publicaCliente);
                Thread hilo2 = new Thread(hiloEnviar);
                hilo2.start();

                Runnable hiloRecibir = new RecibirEncriptado(so, sb,publicKey,privateKey, publicaCliente);
                Thread hilo3 = new Thread(hiloRecibir);
                hilo3.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }
}


