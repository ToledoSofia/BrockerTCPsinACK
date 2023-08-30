package criptografia;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;
class EnviarEncriptado implements Runnable {
    private Socket sc;
    private Rigoberto rigoberto;
    //private DataOutputStream salida;

    public EnviarEncriptado(Socket so, Rigoberto rigo) {
        sc = so;
        rigoberto = rigo;
    }

    @Override
    public void run() {
        try {



            /*while (true) {
                String mensaje = scanner.nextLine();
                mensaje = Arrays.toString(Asimetrica.firmar(Hash.Hashear(mensaje).getBytes(),privateKey,key.getAlgorithm()));
                salida.writeUTF(mensaje);
                salida.flush();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


