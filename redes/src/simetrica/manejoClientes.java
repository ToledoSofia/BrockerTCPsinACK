package simetrica;
import BrokerTCP.ServidorBroker;
import criptografia.Asimetrica;
import criptografia.Hash;
import criptografia.Mensaje;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

class manejoClientes implements Runnable {
    private ServerSocket servidor;
    private ServidorBroker2 serv;
    private Socket so;
    private String mensajeRecibido;
    private PublicKey publicaCliente;
    private SecretKey claveSimetrica;
    private PrivateKey privadaServidor;

    public manejoClientes(ServerSocket servidor, Socket so, ServidorBroker2 sb, PublicKey publicaCliente, PrivateKey privada, SecretKey claveSimetrica) {
        this.servidor = servidor;
        this.so = so;
        serv = sb;
        this.publicaCliente = publicaCliente;
        privadaServidor = privada;
        this.claveSimetrica = claveSimetrica;
    }

    @Override
    public void run() {
/*
        String mensaje2;
*/
        while (true) {
            mensajeRecibido = "";
            try {
                ObjectInputStream entrada = new ObjectInputStream(so.getInputStream());
                ObjectOutputStream salida;

                Mensaje mensaje1 = (Mensaje) entrada.readObject();
                String hasheado = new String(Asimetrica.desenciptarFirma(mensaje1.getFirma(),publicaCliente,"RSA"),"UTF8");
                mensajeRecibido = new String(Simetrica.descifrarTextoAES(claveSimetrica,mensaje1.getEncriptadoPublica()),"UTF8");
                //tiene que enviar encriptandolo con lo del destino y acomodar cuando se agregan canales a los topicosClientes
                if(hasheado.equals(Hash.hashear(mensajeRecibido))){
                    if (mensajeRecibido.startsWith("s:") || mensajeRecibido.startsWith("S:")) {// suscripcion
                        System.out.println("+ suscripcion " + mensajeRecibido.substring(2));
                        String mensaje = mensajeRecibido.substring(2);

                        if(!serv.getTopicosClientes().keySet().contains(mensaje)){//si no existe el canal lo crea y guarda el cliente en la lista junto con la clave publica
                            HashMap<PublicKey,SecretKey> aux2 = new HashMap<>();
                            aux2.put(publicaCliente,claveSimetrica);

                            HashMap<Socket, HashMap<PublicKey,SecretKey>> c = new HashMap<>();
                            c.put(so,aux2);

                            HashMap<String,HashMap<Socket,HashMap<PublicKey,SecretKey>>>aux = serv.getTopicosClientes();
                            aux.put(mensaje,c);
                            serv.setTopicosClientes(aux);
                        }else{
                            HashMap<PublicKey,SecretKey>aux2 = new HashMap<>();
                            aux2.put(publicaCliente,claveSimetrica);

                            HashMap<Socket, HashMap<PublicKey,SecretKey>> c = serv.getTopicosClientes().get(mensaje);
                            c.put(so,aux2);

                            HashMap<String,HashMap<Socket,HashMap<PublicKey,SecretKey>>>aux = serv.getTopicosClientes();
                            aux.put(mensaje,c);
                            serv.setTopicosClientes(aux);
                        }
                    /*serv.getTopicosClientes()
                        .computeIfAbsent(mensaje, k -> new HashMap<>())
                        .add(so);*/
                    } else if (mensajeRecibido.startsWith("m:") || mensajeRecibido.startsWith("M:")) {//mensaje
                        String[] partes = mensajeRecibido.split(":");
                        String canal = partes[1];
                        String mensajeSolo = partes[2];
                        mensajeSolo ="CANAL: " + canal + ":" + mensajeSolo;

                        HashMap<Socket, HashMap<PublicKey,SecretKey>> socketsCanal = serv.getTopicosClientes().get(canal);
                        if (socketsCanal != null) {
                            for (Socket s : socketsCanal.keySet()) {//manda el mnsj a todos los suscriptos al canal
                                Object[] keys = socketsCanal.get(s).keySet().toArray();
                                SecretKey clave = socketsCanal.get(s).get(keys[0]);
                                Mensaje mensajeEncriptado = new Mensaje(
                                        Asimetrica.firmar(Hash.hashear(mensajeSolo).getBytes("UTF8"),privadaServidor,"RSA"),
                                        Simetrica.cifrarTextoAES(clave,mensajeSolo.getBytes("UTF8"))
                                );
                                salida = new ObjectOutputStream(s.getOutputStream());
                                salida.writeObject(mensajeEncriptado);
                                salida.flush();
                            }
                        }
                    }
                }else{
                    System.out.println("no coincide el hash");
                }
                System.out.println(mensajeRecibido);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
