package BrokerTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
class Recibir implements Runnable {
    private Socket sc;
//    private DataOutputStream salida;

    public Recibir(Socket so) {
        sc = so;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(sc.getInputStream());
            while (true) {
                String mensajeRecibido = entrada.readUTF();
                System.out.println("Mensaje recibido: " + mensajeRecibido);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*public class RecibirServidor implements Runnable {
    private String HOST = "localhost";
    private int PUERTO = 5000;
    private Socket sc;
    private DataOutputStream salida;

    public RecibirServidor(Socket so){
        sc = so;
    }

    @Override
    public void run(){
        Scanner scanner1 = new Scanner(System.in);
        try{
            salida = new DataOutputStream(sc.getOutputStream());
            String msn = "";
            while(!(msn.equals("x"))){
                System.out.print("mnsj: ");
                msn = scanner1.nextLine();
                salida.writeUTF(msn);
            }
            sc.close();
        }catch(Exception e){

        }
    }
}






*/

