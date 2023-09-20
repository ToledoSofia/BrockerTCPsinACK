package simetrica;

import criptografia.*;

import javax.crypto.SecretKey;
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
    private SecretKey claveSimetrica;

    public Recibir(Socket so, PublicKey pubKey, PrivateKey privKey, PublicKey publicaCliente, SecretKey claveSimetrica) {
        sc = so;
        publicKey = pubKey;
        privateKey = privKey;
        this.publicaCliente = publicaCliente;
        this.claveSimetrica = claveSimetrica;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream entrada = new ObjectInputStream(sc.getInputStream());
                Mensaje mensaje1 = (Mensaje) entrada.readObject();
                String hash = new String(Asimetrica.desenciptarFirma(mensaje1.getFirma(),publicaCliente,"RSA"),"UTF8");
                String mensaje = new String(Simetrica.descifrarTextoAES(claveSimetrica,mensaje1.getEncriptadoPublica()),"UTF8");
                if(hash.equals(Hash.hashear(mensaje))){
                    System.out.println("Mensaje recibido = " + mensaje);
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
