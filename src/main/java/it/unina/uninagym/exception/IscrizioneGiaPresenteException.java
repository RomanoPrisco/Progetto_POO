package it.unina.uninagym.exception;

/**
 * Eccezione sollevata quando il cliente risulta già iscritto a un corso.
 */
public class IscrizioneGiaPresenteException extends RuntimeException {

    /**
     * Crea una nuova eccezione con il messaggio specificato.
     *
     * @param message il messaggio associato all'eccezione
     */
    public IscrizioneGiaPresenteException(String message) {
        super(message);
    }
}