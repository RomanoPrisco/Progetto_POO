package it.unina.uninagym.exception;

/**
 * Eccezione sollevata quando un campo obbligatorio non è stato compilato.
 */
public class CampoNonTrovatoException extends RuntimeException {

    /**
     * Crea una nuova eccezione con il messaggio specificato.
     *
     * @param message il messaggio associato all'eccezione
     */
    public CampoNonTrovatoException(String message) {
        super(message);
    }
}