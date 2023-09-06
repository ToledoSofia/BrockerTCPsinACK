package criptografia;

public class Mensaje {
    private byte[]firma;//mensaje hasheado y encriptado con la clave privada
    private byte[]encriptadoPublica;//mensaje encriptado con la clave publica del destino

    public Mensaje(byte[]firma, byte[]encriptadoPublica){
        this.encriptadoPublica = encriptadoPublica;
        this.firma = firma;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public byte[] getEncriptadoPublica() {
        return encriptadoPublica;
    }

    public void setEncriptadoPublica(byte[] encriptadoPublica) {
        this.encriptadoPublica = encriptadoPublica;
    }
}
