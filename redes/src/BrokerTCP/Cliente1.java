package BrokerTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente1 {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    public void iniciarCliente() {
        try {
            //sc = new Socket(HOST, PUERTO);
            Socket sc = new Socket(HOST, PUERTO);

            Thread hiloRecibir = new Thread(new Recibir(sc));
            Thread hiloEnviar = new Thread(new Enviar(sc));

            hiloRecibir.start();
            hiloEnviar.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}


/*
   Runnable hilo3 = new Enviar(sc);
            Thread hilo2 = new Thread(hilo3);
            hilo2.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensajeEnviado = scanner.nextLine();
                if (mensajeEnviado.equalsIgnoreCase("x")) {
                    sc.close();
                    break;
                }
                salida = new DataOutputStream(sc.getOutputStream());
                salida.writeUTF(mensajeEnviado);
            }
        } catch (Exception e) {
            // Handle exceptions properly
        }

package BrokerTCP;
import java.net.*;
import java.io.*;
import java.util.*;
public class Cliente1 {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    private Socket sc;

    private DataOutputStream salida;
    private DataInputStream entrada;
    String mensajeRecibido;
    public void iniciarCliente(){
        try{
            sc = new Socket(HOST, PUERTO);
            entrada = new DataInputStream(sc.getInputStream());
            mensajeRecibido = "";

            while(true){
                Runnable hilo3 = new RecibirServidor(sc);
                Thread hilo2 = new Thread(hilo3);
                hilo2.start();
                mensajeRecibido = entrada.readUTF();
                System.out.println(mensajeRecibido);
            }
           // sc.close();
        }catch(Exception e){
            //throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}
*/
