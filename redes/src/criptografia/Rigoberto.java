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
                FileInputStream fis = new FileInputStream("t.tmp");
                ObjectInputStream entrada = new ObjectInputStream(fis);
                PublicKey publicaCliente = (PublicKey) entrada.readObject();
                System.out.println("hola");

                //mandar clave publica
                FileOutputStream fos = new FileOutputStream("t.tmp");
                ObjectOutputStream salida = new ObjectOutputStream(fos);
                salida.writeObject(publicKey);

                Clientes.put(so,publicaCliente);

                /*Scanner scanner = new Scanner(System.in);
                Runnable hiloEnviar = new EnviarEncriptado(so, sb, publicKey, privateKey, publicaCliente);
                Thread hilo1 = new Thread(hiloEnviar);
                hilo1.start();*/

                Runnable hiloRecibir = new RecibirEncriptado(so, sb,publicKey,privateKey, publicaCliente);
                Thread hilo2 = new Thread(hiloRecibir);
                hilo2.start();

              /*  PublicKey llavePepe = null;

                ObjectInputStream entradaLlave = new ObjectInputStream(so.getInputStream());
                PublicKey llavePublicaPepe = ((PublicKey) entradaLlave.readObject() );



                DataInputStream entrada = new DataInputStream(so.getInputStream());
                DataOutputStream salida = new DataOutputStream(so.getOutputStream());
                //String llaveP = entrada.readUTF();

                KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
                key.initialize(1024);
                KeyPair keyPair = key.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();



                String test = "hola";
                byte [] testEncriptado = criptografia.Asimetrica.encriptar(test.getBytes(), llavePepe, key.getAlgorithm());
                System.out.println(criptografia.Asimetrica.desencriptar(testEncriptado, privateKey, key.getAlgorithm()));
*/
            /*    KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
                key.initialize(1024);
                KeyPair keyPair = key.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();*/


               /* salida.writeUTF("pk" + String.valueOf(publicKey));
                mensajeRecibido = entrada.readUTF();*/


               /* String mensajeHasheado = criptografia.Hash.Hashear(mensajeRecibido);//hashear mensaje
                byte[] firma = criptografia.Asimetrica.firmar(mensajeHasheado.getBytes(), privateKey,key.getAlgorithm());// firmar

                //mensaje encriptado con clave publica de criptografia.Rigoberto
                byte [] mnsjEncriptadoConPublica = criptografia.Asimetrica.encriptar(mensajeRecibido.getBytes(), llavePepe, key.getAlgorithm());

*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }
}


