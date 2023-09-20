package simetrica;

import javax.crypto.*;
import java.security.*;

public class Simetrica {
    public static KeyPair generarParDeClavesRSA() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        return keyGen.generateKeyPair();
    }

    public static SecretKey generarClaveSecretaAES() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        return kg.generateKey();
    }

    public static byte[] envolverClaveRSA(PublicKey clavePublica, SecretKey claveSecreta) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.WRAP_MODE, clavePublica);
        return c.wrap(claveSecreta);
    }

    public static Key desenvolverClaveRSA(PrivateKey clavePrivada, byte[] claveEnvuelta) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.UNWRAP_MODE, clavePrivada);
        return c.unwrap(claveEnvuelta, "AES", Cipher.SECRET_KEY);
    }

    public static byte[] cifrarTextoAES(SecretKey claveSecretaAES, byte[] textoPlano) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, claveSecretaAES);
        return c.doFinal(textoPlano);
    }

    public static byte[] descifrarTextoAES(Key claveDesenvuelta, byte[] textoCifrado) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, claveDesenvuelta);
        return c.doFinal(textoCifrado);
    }
    public static void main(String args[]) {
        try {
            KeyPair keyPair = generarParDeClavesRSA();
            PrivateKey clavePrivada = keyPair.getPrivate();
            PublicKey clavePublica = keyPair.getPublic();

            SecretKey claveSecretaAES = generarClaveSecretaAES();

            byte claveEnvuelta[] = envolverClaveRSA(clavePublica, claveSecretaAES);

            Key claveDesenvuelta = desenvolverClaveRSA(clavePrivada, claveEnvuelta);

            byte textoCifrado[] = cifrarTextoAES(claveSecretaAES, "Esto es un Texto Plano".getBytes());
            System.out.println("Cifrado: " + new String(textoCifrado));

            byte desencriptado[] = descifrarTextoAES(claveDesenvuelta, textoCifrado);
            System.out.println("Descifrado: " + new String(desencriptado));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


