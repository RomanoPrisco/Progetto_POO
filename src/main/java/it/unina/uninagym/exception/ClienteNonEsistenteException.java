package it.unina.uninagym.exception;

/**
 * Eccezione sollevata quando il cliente non risulta presente nel sistema.
 */
public class ClienteNonEsistenteException extends RuntimeException {

    /**
     * Crea una nuova eccezione con il messaggio specificato.
     *
     * @param message il messaggio associato all'eccezione
     */
    public ClienteNonEsistenteException(String message) {
        super(message);
    }
}