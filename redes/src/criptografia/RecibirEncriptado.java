package criptografia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;
class RecibirEncriptado implements Runnable {
    private Socket sc;
    private Rigoberto rigoberto;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private PublicKey publicaCliente;
//    private DataOutputStream salida;

    public RecibirEncriptado(Socket so, Rigoberto rigo, PublicKey pubKey, PrivateKey privKey, PublicKey publicaCliente) {
        sc = so;
        rigoberto = rigo;
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
                String hash = Asimetrica.desenciptarFirma(mensajeRecibido.getFirma(),publicaCliente,"RSA").toString();
                String mensaje = Asimetrica.desencriptar(mensajeRecibido.getEncriptadoPublica(),privateKey,"RSA").toString();
               if(hash.equals(Hash.hashear(mensaje))){
                   System.out.println("entra");
                   System.out.println("Mensaje recibido: " + mensaje);
               }else{
                   System.out.println("else");
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

