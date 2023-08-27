package BrokerTCP;

//import com.sun.
// //corba.se.spi.activation.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/*

public class manejoClientes implements Runnable {

    private static ServerSocket servidor;
    private static ServidorBroker serv;
    private static Socket so;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private String mensajeRecibido;

    public manejoClientes(ServerSocket servidor, Socket so, ServidorBroker sb){
        this.servidor  = servidor;
        this.so = so;
        serv = sb;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        String mensaje2 ;
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
                        HashSet<Socket>aux = serv.getTopicosClientes().get(mensaje);
                        aux.add(so);
                        serv.getTopicosClientes().put(mensaje, aux);
                    } else {
                        HashSet<Socket>aux = new HashSet<Socket>();
                        aux.add(so);
                        serv.getTopicosClientes().put(mensaje, aux);
                    }
                }else if (!mensajeRecibido.equals("") && mensajeRecibido.substring(0, 2).contains("m:") || mensajeRecibido.substring(0, 1).contains("M:")){
                    //
                    String[] partes = mensajeRecibido.split(":");
                    String canal = partes[1];
                    String mensajeSolo = partes[2];

                    for(Socket s : serv.getTopicosClientes().get(canal)) {
                        salida = new DataOutputStream(s.getOutputStream());
                        salida.writeUTF("\nCANAL: " + canal + " = " + mensajeSolo);
                    }
                }
                System.out.println(mensajeRecibido);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}*/

class manejoClientes implements Runnable {
    private ServerSocket servidor;
    private ServidorBroker serv;
    private Socket so;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private String mensajeRecibido;

    public manejoClientes(ServerSocket servidor, Socket so, ServidorBroker sb) {
        this.servidor = servidor;
        this.so = so;
        serv = sb;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String mensaje2;
        while (true) {
            mensajeRecibido = "";
            try {
                entrada = new DataInputStream(so.getInputStream());
                salida = new DataOutputStream(so.getOutputStream());
                mensajeRecibido = entrada.readUTF();
                if (mensajeRecibido.startsWith("s:") || mensajeRecibido.startsWith("S:")) {
                    System.out.println("+ suscripcion " + mensajeRecibido.substring(2));
                    String mensaje = mensajeRecibido.substring(2);

                    serv.getTopicosClientes()
                        .computeIfAbsent(mensaje, k -> new HashSet<>())
                        .add(so);
                } else if (mensajeRecibido.startsWith("m:") || mensajeRecibido.startsWith("M:")) {
                    String[] partes = mensajeRecibido.split(":");
                    String canal = partes[1];
                    String mensajeSolo = partes[2];

                    HashSet<Socket> socketsCanal = serv.getTopicosClientes().get(canal);
                    if (socketsCanal != null) {
                        for (Socket s : socketsCanal) {
                            salida = new DataOutputStream(s.getOutputStream());
                            salida.writeUTF("\nCANAL: " + canal + " = " + mensajeSolo);
                        }
                    }
                }
                System.out.println(mensajeRecibido);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }
}


