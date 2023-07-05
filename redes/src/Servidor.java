import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        final int PUERTO = 5000;
        byte[] buffer = new byte[1024];
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        try {
            //Creacion del socket

            while (true) {
                DatagramSocket socketUDP = new DatagramSocket(PUERTO);

                byte[] buffer1 = new byte[1024];
                DatagramPacket peticion = new DatagramPacket(buffer1, buffer1.length);

                socketUDP.receive(peticion);

                //Convierto lo recibido y mostrar el mensaje
                String mensaje = new String(peticion.getData());
                System.out.println(mensaje);

                //Obtengo el puerto y la direccion de origen
                //Sino se quiere responder, no es necesario
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
                System.out.print("YO: ");

                mensaje = sc.nextLine();

                buffer = new byte[1024];
                buffer = mensaje.getBytes();

                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);

                //Envio la información
                socketUDP.send(respuesta);
                socketUDP.close();
            }

        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

