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
    private static ServidorBroker serv;
    private static Socket so;
    private static DataOutputStream salida;
    private static DataInputStream entrada;
    private static String mensajeRecibido;

    public manejoClientes(ServerSocket servidor, Socket so, ServidorBroker sb){
        this.servidor  = servidor;
        this.so = so;
        serv = sb;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            mensajeRecibido = "";
            try {
                entrada = new DataInputStream(so.getInputStream());
                salida = new DataOutputStream(so.getOutputStream());
                mensajeRecibido = entrada.readUTF();
                if (mensajeRecibido.substring(0, 2).contains("s:") || mensajeRecibido.substring(0, 1).contains("S:")) {
                    System.out.println("+ suscripcion " + mensajeRecibido.substring(2));
                    String mensaje = mensajeRecibido.substring(2);

                    if (serv.getTopicosClientes().keySet().contains(mensaje)) {
                        serv.getTopicosClientes().get(mensaje).add(so);
                    } else {
                        HashMap<String, HashSet<Socket>>copia = serv.getTopicosClientes();
                        HashSet<Socket> nuevo = new HashSet<Socket>();
                        nuevo.add(so);
                        copia.put(mensaje, nuevo);
                        serv.setTopicosClientes(copia);
                    }
                }else if (!mensajeRecibido.equals("") && mensajeRecibido.substring(0, 2).contains("m:") || mensajeRecibido.substring(0, 1).contains("M:")){
                    //System.out.println("entraaaa");
                    String canal = "";
                    boolean fuera = false;
                    char[] chars = mensajeRecibido.substring(2).toCharArray();
                    for(char c : chars){
                        if(!(c == ':') && !fuera){
                            canal = canal+c;
                        }else if(c == ':'){
                            fuera = true;
                        }
                    }

                    for(Socket s : serv.getTopicosClientes().get(canal)) {
                        salida = new DataOutputStream(s.getOutputStream());
                        salida.writeUTF("\nMensaje de: " + mensajeRecibido.substring(2));
                    }
                }
                System.out.println(mensajeRecibido);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}