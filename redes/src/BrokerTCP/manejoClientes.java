package BrokerTCP;

import com.sun.corba.se.spi.activation.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class manejoClientes implements Runnable {

    private static ServerSocket servidor;
    private static Socket so;
    private static HashSet<Socket>clientes;
    private static DataOutputStream salida;
    private static DataInputStream entrada;
    private static String mensajeRecibido;
    private static HashMap<String, HashSet<Socket>>topicosClientes;

    public manejoClientes(ServerSocket servidor){
        this.servidor  = servidor;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        topicosClientes = new HashMap<String, HashSet<Socket>>();
        while(true) {
            try {
                entrada = new DataInputStream(so.getInputStream());
                salida = new DataOutputStream(so.getOutputStream());
                mensajeRecibido = entrada.readUTF();
                if (mensajeRecibido.substring(0, 2).contains("s:") || mensajeRecibido.substring(0, 1).contains("S:")) {
                    System.out.println("+ suscripcion " + mensajeRecibido.substring(2));
                    if (topicosClientes.keySet().contains(mensajeRecibido.substring(2))) {
                        HashSet<Socket> nuevo = topicosClientes.get(mensajeRecibido.substring(2));
                        nuevo.add(so);//guardar socket, no c como
                        topicosClientes.replace(mensajeRecibido.substring(2), clientes);
                    } else {
                        HashSet<Socket> nuevo = new HashSet<Socket>();
                        nuevo.add(so);
                        topicosClientes.put(mensajeRecibido.substring(2), nuevo);
                    }
                }else if (mensajeRecibido.substring(0, 2).contains("m:") || mensajeRecibido.substring(0, 1).contains("M:")){

                    salida.writeUTF("Mensaje de: " + mensajeRecibido.substring(2));
                }
                System.out.println(mensajeRecibido);
                System.out.println("Escriba un msnj");
                String msn = scanner.nextLine();
                salida.writeUTF(""+msn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}