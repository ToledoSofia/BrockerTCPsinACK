package criptografia;

import javax.crypto.Cipher;
import java.security.*;

public class Asimetrica {
    public static byte[] encriptar(byte[] inputBytes, PublicKey publicKey, String algorithm){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);//  Inicializamos el cipher en modo de encriptado y pasamos la llave pública
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }
    public static byte[] firmar(byte[] inputBytes, PrivateKey privateKey, String algorithm){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);//  Inicializamos el cipher en modo de encriptado y pasamos la llave pública
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }
    public static byte[] desenciptarFirma(byte[] inputBytes, PublicKey publicKey, String algorithm){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);//  Inicializamos el cipher en modo de desencriptado y pasamos la llave privada
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }

    public static byte[] desencriptar(byte[] inputBytes, PrivateKey privateKey, String algorithm){
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            //  Inicializamos el cipher en modo de desencriptado y pasamos la llave privada
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }
    public static KeyPair generarClaves() throws NoSuchAlgorithmException {
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        //  Especificamos el tamaño de la llave en bits
        key.initialize(1024);
        KeyPair keyPair = key.generateKeyPair();
        return keyPair;
    }



    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        //  Especificamos el tamaño de la llave en bits
        key.initialize(1024);
        KeyPair keyPair = key.generateKeyPair();
        //  Obtenemos ambas llaves por separado
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String msg = "cacona";

        System.out.println("Texto sin encriptar -> " + msg);

        byte[] msgEncryptedBytes = encriptar(msg.getBytes(), publicKey, key.getAlgorithm());
        System.out.println("Texto encriptado -> " + new String(msgEncryptedBytes));

        byte[] msgDecryptedBytes = desencriptar(msgEncryptedBytes, privateKey, key.getAlgorithm());
        System.out.println("Texto desencriptado -> " + new String(msgDecryptedBytes));
    }
}
