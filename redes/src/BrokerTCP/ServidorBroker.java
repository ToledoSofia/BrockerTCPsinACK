package BrokerTCP;
import javax.swing.text.Segment;
import java.net.*;
import java.io.*;
import java.util.*;
public class ServidorBroker {
    private HashMap<String, HashSet<Socket>>topicosClientes;
    private ServerSocket sc;
    private Socket so;
    private HashSet<Socket>clientes;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private String mensajeRecibido;
    public ServidorBroker(){
        topicosClientes = new HashMap<String, HashSet<Socket>>();
    }
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        try{
            sc= new ServerSocket(5000);//puerto random
            so = sc.accept();
            clientes.add(so);
            System.out.println("Se conecto uno");
            entrada = new DataInputStream(so.getInputStream());
            salida = new DataOutputStream(so.getOutputStream());


            String msn = "";
            while(true) {
                mensajeRecibido = entrada.readUTF();
                if (mensajeRecibido.substring(0, 2).contains("s:") || mensajeRecibido.substring(0, 1).contains("S:")) {
                    System.out.println("+ suscripcion " + mensajeRecibido.substring(2));
                    if (topicosClientes.keySet().contains(mensajeRecibido.substring(2))) {
                        HashSet<Socket> nuevo = topicosClientes.get(mensajeRecibido.substring(2));
                        nuevo.add(so);//guardar socket, no c como
                        topicosClientes.replace(mensajeRecibido.substring(2), clientes);
                       /* for (String s : topicosClientes.keySet()) {
                            System.out.println(s);
                            System.out.println(topicosClientes.get(s).size());
                        }*/
                    } else {
                        HashSet<Socket> nuevo = new HashSet<Socket>();
                        nuevo.add(so);
                        topicosClientes.put(mensajeRecibido.substring(2), nuevo);
                    }
                }else if (mensajeRecibido.substring(0, 2).contains("m:") || mensajeRecibido.substring(0, 1).contains("M:")){

                    salida.writeUTF("Mensaje de: " + mensajeRecibido.substring(2));
                }
           /*     else{
                    HashSet<Socket>nuevo = new HashSet<Socket>();
                    nuevo.add(so);
                    topicosClientes.put(mensajeRecibido.substring(2),nuevo);
                }*/
                System.out.println(mensajeRecibido);
                System.out.println("Escriba un msnj");
                msn = scanner.nextLine();
                salida.writeUTF(""+msn);

            }
            //sc.close();
            //byte[] buffer1 = new byte[1024];

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServidorBroker server = new ServidorBroker();
        server.iniciar();
    }
}
// s: algo (suscripcion) si le llega eso lo agrego a un array o algo de la suscripcion
// m: algo: "hola" (mensaje, se envia el mensaje a todos los que estan en el array)
// si alguie manda s:.... con una suscripcion que no existe la agrega a un hashmap<Topico, ArrayCliente>
// socketTCP
//guardar socket generado por el cliente en el array/hashset
