package BrokerTCP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
class Enviar implements Runnable {
    private Socket sc;
    //private DataOutputStream salida;

    public Enviar(Socket so) {
        sc = so;
    }

    @Override
    public void run() {
        try {
            DataOutputStream salida = new DataOutputStream(sc.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String mensaje = scanner.nextLine();
                salida.writeUTF(mensaje);
                salida.flush();
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

