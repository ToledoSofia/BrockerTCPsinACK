package criptografia;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Hash
{
    public static String Hashear(String mensaje) throws NoSuchAlgorithmException {
        String hasheado;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(mensaje.getBytes(StandardCharsets.UTF_8));
        hasheado = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return hasheado;
    }
    public static PublicKey StringAPublica(String llave) throws NoSuchAlgorithmException, InvalidKeySpecException {
   /*     byte[] publicBytes = Base64.getDecoder().decode(llave);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);*/
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(llave.getBytes()));
    }

    public static void main(String[] args) throws Exception
    {
        try{
            String password = "cacona";

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();

            // imprimir resumen de mensaje SHA-256
            System.out.println(sha256);
        }catch(Exception e){
            System.err.println(e);
        }

    }
}

