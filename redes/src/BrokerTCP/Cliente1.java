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
        Scanner scanner1 = new Scanner(System.in);
        try{
            sc = new Socket(HOST, PUERTO);
            salida = new DataOutputStream(sc.getOutputStream());
            entrada = new DataInputStream(sc.getInputStream());
            String msn = "";
            while(!msn.equals("x")){
                System.out.println("Escriba un msn para enviar");
                msn = scanner1.nextLine();
                salida.writeUTF(msn);//enviamos mensaje
                mensajeRecibido = entrada.readUTF();//Leemos respuesta
                System.out.println(mensajeRecibido);
            }

            sc.close();
        }catch(Exception e){

        }
    }

    public static void main(String[] args){
        Cliente1 c1 = new Cliente1();
        c1.iniciarCliente();
    }
}
