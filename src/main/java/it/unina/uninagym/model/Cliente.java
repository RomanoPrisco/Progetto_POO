package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta un cliente del sistema UninaGym.
 *
 * Un cliente è caratterizzato dai dati anagrafici ereditati da {@link Persona},
 * dalla data di iscrizione, dall'abbonamento associato e dalla password.
 */
public class Cliente extends Persona {

    /** Data di iscrizione del cliente al sistema. */
    private Date dataIscrizione;

    /** Abbonamento associato al cliente. */
    private Abbonamento abbonamento;

    /** Password del cliente. */
    private String password;

    /**
     * Crea un nuovo cliente con tutti i dati anagrafici e di iscrizione.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param email l'email del cliente
     * @param numeroTelefonico il numero di telefono del cliente
     * @param dataNascita la data di nascita del cliente
     * @param dataIscrizione la data di iscrizione del cliente
     * @param abbonamento l'abbonamento associato al cliente
     * @param password la password del cliente
     */
    public Cliente(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita, Date dataIscrizione, Abbonamento abbonamento, String password) {
        super(codiceFiscale, nome, cognome, email, numeroTelefonico, dataNascita);
        this.dataIscrizione = dataIscrizione;
        this.abbonamento = abbonamento;
        this.password = password;
    }

    /**
     * Crea un nuovo cliente con dati ridotti per la GUI.
     *
     * @param password la password del cliente
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     */
    public Cliente(String password, String nome, String cognome){
        super(nome, cognome);
        this.password = password;
    }

    /**
     * Verifica se l'abbonamento del cliente è ancora valido.
     *
     * @return true se l'abbonamento è valido, false altrimenti
     */
    public boolean isAbbonamentoValido() {
        return abbonamento != null && abbonamento.getDataFine().after(new Date());
    }

    /**
     * Restituisce l'abbonamento del cliente solo se ancora valido.
     *
     * @return l'abbonamento valido del cliente, null altrimenti
     */
    public Abbonamento getAbbonamentoValido(){
        return isAbbonamentoValido() ? abbonamento : null;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public Abbonamento getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}