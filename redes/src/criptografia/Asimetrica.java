package criptografia;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

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



    public static String publicaAString(PublicKey clave){
        byte[] bytesClave = clave.getEncoded();
        return bytesClave.toString();
    }
    public static PublicKey stringAPublica(String clave) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //PublicKey clavePublica = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(claveEnBytes));
        byte[] claveEnBytes = clave.getBytes();
        byte[] publicBytes = Base64.getDecoder().decode(claveEnBytes);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
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

        byte[] msgEncryptedBytes = encriptar(msg.getBytes(), publicKey, "RSA");
        System.out.println("Texto encriptado -> " + new String(msgEncryptedBytes));

        byte[] msgDecryptedBytes = desencriptar(msgEncryptedBytes, privateKey, key.getAlgorithm());
        System.out.println("Texto desencriptado -> " + new String(msgDecryptedBytes));
    }
}
