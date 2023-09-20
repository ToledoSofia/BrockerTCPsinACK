package simetrica;


import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServidorBroker2 {
    private static ServerSocket sc;
    private HashMap<String, HashMap<Socket,HashMap<PublicKey,SecretKey>>>TopicosClientes;
    private static HashMap<Socket, PublicKey>Clientes;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    public ServidorBroker2() {
        TopicosClientes = new HashMap<>();
    }


    public static ServerSocket getSc() {
        return sc;
    }

    public static void setSc(ServerSocket sc) {
        ServidorBroker2.sc = sc;
    }

    public HashMap<String, HashMap<Socket, HashMap<PublicKey, SecretKey>>> getTopicosClientes() {
        return TopicosClientes;
    }

    public void setTopicosClientes(HashMap<String, HashMap<Socket, HashMap<PublicKey, SecretKey>>> topicosClientes) {
        TopicosClientes = topicosClientes;
    }

    public static HashMap<Socket, PublicKey> getClientes() {
        return Clientes;
    }

    public static void setClientes(HashMap<Socket, PublicKey> clientes) {
        Clientes = clientes;
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(PublicKey publicKey) {
        ServidorBroker2.publicKey = publicKey;
    }

    public static void main(String[] args) throws IOException {
        ServidorBroker2 sb = new ServidorBroker2();
        sc = new ServerSocket(5000);
        Clientes = new HashMap<>();
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
                Clientes.put(so,publicaCliente);

                //mandar clave publica
                ObjectOutputStream outputStream = new ObjectOutputStream(so.getOutputStream());
                outputStream.writeObject(publicKey);
                outputStream.flush();
                System.out.println("se mando clave del server");

                //recibe clave simetrica
                ObjectInputStream entrada = new ObjectInputStream(so.getInputStream());
                byte[] clave = (byte[]) entrada.readObject();
                SecretKey claveSimetrica = (SecretKey) Simetrica.desenvolverClaveRSA(privateKey,clave);
                System.out.println("se recibio clave simetrica");

                Runnable hilo = new manejoClientes(sc, so, sb, publicaCliente, privateKey, claveSimetrica);
                Thread hilo1 = new Thread(hilo);
                hilo1.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

