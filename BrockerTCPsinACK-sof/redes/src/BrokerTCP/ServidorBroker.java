package BrokerTCP;
import criptografia.Asimetrica;

import javax.swing.text.Segment;
import java.net.*;
import java.io.*;
import java.security.*;
import java.util.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ServidorBroker {
    private static ServerSocket sc;
    private HashMap<String, HashMap<Socket,PublicKey>>TopicosClientes;
    private static HashMap<Socket, PublicKey>Clientes;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    public ServidorBroker() {
        TopicosClientes = new HashMap<>();
    }


    public static ServerSocket getSc() {
        return sc;
    }

    public static void setSc(ServerSocket sc) {
        ServidorBroker.sc = sc;
    }

    public HashMap<String, HashMap<Socket, PublicKey>> getTopicosClientes() {
        return TopicosClientes;
    }

    public void setTopicosClientes(HashMap<String, HashMap<Socket, PublicKey>> topicosClientes) {
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
        ServidorBroker.publicKey = publicKey;
    }

    public static void main(String[] args) throws IOException {
        ServidorBroker sb = new ServidorBroker();
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

                Runnable hilo = new manejoClientes(sc, so, sb, publicaCliente, privateKey);
                Thread hilo1 = new Thread(hilo);
                hilo1.start();

         /*       String test = "holaComoEstas";

                byte[]testEncriptadoPublicaCliente = Asimetrica.encriptar(test.getBytes("UTF8"),publicKey,"RSA");
                byte[]laconchadetumadre = Asimetrica.desencriptar(testEncriptadoPublicaCliente,privateKey,"RSA");
                System.out.println(new String(laconchadetumadre,"UTF8"));*/
                /*outputStream.writeObject(testEncriptadoPublicaCliente);
                outputStream.flush();*/

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

/*public class ServidorBroker {
    private HashMap<String, HashSet<Socket>>topicosClientes;
    private static ServerSocket sc;
    private static HashSet<Socket>clientes;

    public ServidorBroker() {
        topicosClientes = new HashMap<String, HashSet<Socket>>();
    }

    public HashMap<String, HashSet<Socket>> getTopicosClientes() {
        return topicosClientes;
    }

    public void setTopicosClientes(HashMap<String, HashSet<Socket>> topicosClientes) {
        this.topicosClientes = topicosClientes;
    }


    public  ServerSocket getSc() {
        return sc;
    }

    public void setSc(ServerSocket sc) {
        this.sc = sc;
    }

    public HashSet<Socket> getClientes() {
        return clientes;
    }

    public void setClientes(HashSet<Socket> clientes) {
        this.clientes = clientes;
    }

    public static void main(String[] args) throws IOException {
        ServidorBroker sb = new ServidorBroker();
            sc= new ServerSocket(5000);
            clientes = new HashSet<Socket>();
            try{
                while(true){
                    Socket so;
                    so = sc.accept();
                    clientes.add(so);
                    System.out.println("Se conecto uno");

                    Runnable hilo = new manejoClientes(sc, so, sb );
                    Thread hilo1 = new Thread(hilo);
                    hilo1.start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    }
*/

// s: algo (suscripcion) si le llega eso lo agrego a un array o algo de la suscripcion
// m: algo: "hola" (mensaje, se envia el mensaje a todos los que estan en el array)
// si alguie manda s:.... con una suscripcion que no existe la agrega a un hashmap<Topico, ArrayCliente>
//guardar socket generado por el cliente en el array/hashset