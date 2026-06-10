package it.unina.uninagym.exception;

/**
 * Eccezione sollevata quando non è stato selezionato alcun corso.
 */
public class NessunCorsoSelezionatoException extends RuntimeException {

    /**
     * Crea una nuova eccezione con il messaggio specificato.
     *
     * @param message il messaggio associato all'eccezione
     */
    public NessunCorsoSelezionatoException(String message) {
        super(message);
    }
}