package criptografia;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public class Simetrica {
    public static byte[] encriptar(byte[] inputBytes, SecretKey clave){
        try {
            Cipher cipher = Cipher.getInstance("RSA");//  Inicializamos el cipher en modo de encriptado y pasamos la llave pública
            cipher.init(Cipher.ENCRYPT_MODE, clave);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }

    public static byte[] desencriptar(byte[] inputBytes, SecretKey clave){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            //  Inicializamos el cipher en modo de desencriptado y pasamos la llave privada
            cipher.init(Cipher.DECRYPT_MODE, clave);

            return cipher.doFinal(inputBytes);

        }  catch (Exception ex) {
            System.out.print(ex);
        }
        return null;
    }


}
