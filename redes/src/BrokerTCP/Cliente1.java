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
