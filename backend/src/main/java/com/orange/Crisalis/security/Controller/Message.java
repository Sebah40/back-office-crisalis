package com.orange.Crisalis.security.Controller;
/**
 *
 * @author Sebastián
 */
public class Message {
    private String mensaje;
    
    public Message() {
    }

    public Message(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
