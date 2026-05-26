package it.unina.uninagym.exception;

public class ClienteNonEsistenteException extends RuntimeException {
    public ClienteNonEsistenteException(String message) {
        super(message);
    }
}
