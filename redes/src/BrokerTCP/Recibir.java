package BrokerTCP;

import criptografia.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
class Recibir implements Runnable {
    private Socket sc;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PublicKey publicaCliente;
//    private DataOutputStream salida;

    public Recibir(Socket so, PublicKey pubKey, PrivateKey privKey, PublicKey publicaCliente) {
        sc = so;
        publicKey = pubKey;
        privateKey = privKey;
        this.publicaCliente = publicaCliente;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream entrada = new ObjectInputStream(sc.getInputStream());
            /*ObjectInputStream entradaClave = new ObjectInputStream(sc.getInputStream());
            PublicKey clavePublicaOtro = (PublicKey) entradaClave.readObject();*/

            while (true) {
                //System.out.println("hlhaajif");
                Mensaje mensajeRecibido = (Mensaje) entrada.readObject();
                String hash = new String(Asimetrica.desenciptarFirma(mensajeRecibido.getFirma(),publicaCliente,"RSA"),"UTF8");
                String mensaje = new String(Asimetrica.desencriptar(mensajeRecibido.getEncriptadoPublica(),privateKey,"RSA"),"UTF8");
                if(hash.equals(Hash.hashear(mensaje))){
                    System.out.println("entra");
                    System.out.println("Mensaje recibido: " + mensaje);
                }else{
                    System.out.println("hash no coincide en el cliente");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


/*
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
class Recibir implements Runnable {
    private Socket sc;
//    private DataOutputStream salida;

    public Recibir(Socket so) {
        sc = so;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(sc.getInputStream());
            while (true) {
                String mensajeRecibido = entrada.readUTF();
                System.out.println("Mensaje recibido: " + mensajeRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/