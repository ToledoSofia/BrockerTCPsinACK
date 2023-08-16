package BrokerTCP;
import javax.swing.text.Segment;
import java.net.*;
import java.io.*;
import java.util.*;
public class ServidorBroker {
    private HashMap<String, HashSet<Socket>>topicosClientes;
    private static ServerSocket sc;
    private static HashSet<Socket>clientes;

    public ServidorBroker() {
        topicosClientes = new HashMap<String, HashSet<Socket>>();
    }

    public HashMap<String, HashSet<Socket>> getTopicosClientes() {
        return topicosClientes;
    }

    public void setTopicosClientes(HashMap<String, HashSet<Socket>> topicosClientes) {
        this.topicosClientes = topicosClientes;
    }


    public  ServerSocket getSc() {
        return sc;
    }

    public void setSc(ServerSocket sc) {
        this.sc = sc;
    }

    public HashSet<Socket> getClientes() {
        return clientes;
    }

    public void setClientes(HashSet<Socket> clientes) {
        this.clientes = clientes;
    }

    public static void main(String[] args) throws IOException {
        ServidorBroker sb = new ServidorBroker();
            sc= new ServerSocket(5000);
            clientes = new HashSet<Socket>();
            try{
                while(true){
                    Socket so;
                    so = sc.accept();
                    clientes.add(so);
                    System.out.println("Se conecto uno");

                    Runnable hilo = new manejoClientes(sc, so, sb );
                    Thread hilo1 = new Thread(hilo);
                    hilo1.start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    }


// s: algo (suscripcion) si le llega eso lo agrego a un array o algo de la suscripcion
// m: algo: "hola" (mensaje, se envia el mensaje a todos los que estan en el array)
// si alguie manda s:.... con una suscripcion que no existe la agrega a un hashmap<Topico, ArrayCliente>
//guardar socket generado por el cliente en el array/hashset