package BrokerTCP;
import criptografia.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.Arrays;

public class Cliente1 {
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

            /*byte[] test1 = (byte[]) inputStream.readObject();
            String testDesencriptado = Arrays.toString(Asimetrica.desencriptar(test1,privateKey,"RSA"));
            System.out.println("MENSAJITO: " + testDesencriptado);*/


            Thread hiloRecibir = new Thread(new Recibir(sc, publicKey, privateKey, publicaDestino));
            Thread hiloEnviar = new Thread(new Enviar(sc, publicKey, privateKey, publicaDestino));

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
        /*criptografia.Pepe p = new criptografia.Pepe();
        p.iniciarCliente();*/
         Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}




/*import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Cliente1 {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public void iniciarCliente() {
        try {

            //sc = new Socket(HOST, PUERTO);
            Socket sc = new Socket(HOST, PUERTO);

            Rigoberto rigo = new Rigoberto();

            Thread hiloRecibir = new Thread(new Recibir(sc, rigo, publicKey, privateKey, publicaDestino));
            Thread hiloEnviar = new Thread(new Enviar(sc, rigo, publicKey, privateKey, publicaDestino));

            hiloRecibir.start();
            hiloEnviar.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}


/*
   Runnable hilo3 = new Enviar(sc);
            Thread hilo2 = new Thread(hilo3);
            hilo2.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensajeEnviado = scanner.nextLine();
                if (mensajeEnviado.equalsIgnoreCase("x")) {
                    sc.close();
                    break;
                }
                salida = new DataOutputStream(sc.getOutputStream());
                salida.writeUTF(mensajeEnviado);
            }
        } catch (Exception e) {
            // Handle exceptions properly
        }

package BrokerTCP;
import java.net.*;
import java.io.*;
import java.util.*;
public class Cliente1 {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    private Socket sc;

    private DataOutputStream salida;
    private DataInputStream entrada;
    String mensajeRecibido;
    public void iniciarCliente(){
        try{
            sc = new Socket(HOST, PUERTO);
            entrada = new DataInputStream(sc.getInputStream());
            mensajeRecibido = "";

            while(true){
                Runnable hilo3 = new RecibirServidor(sc);
                Thread hilo2 = new Thread(hilo3);
                hilo2.start();
                mensajeRecibido = entrada.readUTF();
                System.out.println(mensajeRecibido);
            }
           // sc.close();
        }catch(Exception e){
            //throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}
*/
