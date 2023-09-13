package criptografia;

//import javax.xml.bind.DatatypeConverter;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.SchemaOutputResolver;
import java.io.UnsupportedEncodingException;
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
/*
    public static String hashear(String mensaje){
        return mensaje;
    }
*/
    public static String hashear(String mensaje) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hasheado;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(mensaje.getBytes("UTF8"));
        hasheado = DatatypeConverter.printHexBinary(digest).toLowerCase();
        return hasheado;
    }

    public static void hashearSimetrica(SecretKey clave) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(clave.getEncoded());
        SecretKey originalKey = new SecretKeySpec(digest, "AES");
        digest = md.digest(originalKey.getEncoded());
        SecretKey originalKey1 = new SecretKeySpec(digest, "AES");

        System.out.println("jiji");
        System.out.println(clave);
        System.out.println(originalKey);
        System.out.println(originalKey1);

        if (originalKey.equals(originalKey1)){
            System.out.println("LOOOOOOOL");
        }
        String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
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
            //String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();

            // imprimir resumen de mensaje SHA-256
            //System.out.println(sha256);
        }catch(Exception e){
            System.err.println(e);
        }

    }
}

