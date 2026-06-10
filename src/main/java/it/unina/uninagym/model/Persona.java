package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta una persona del sistema UninaGym.
 *
 * Questa classe astratta contiene i dati anagrafici comuni
 * a clienti e istruttori.
 */
public abstract class Persona {

    /** Codice fiscale della persona. */
    protected String codiceFiscale;

    /** Nome della persona. */
    protected String nome;

    /** Cognome della persona. */
    protected String cognome;

    /** Email della persona. */
    protected String email;

    /** Numero di telefono della persona. */
    protected String numeroTelefonico;

    /** Data di nascita della persona. */
    protected Date dataNascita;

    /**
     * Crea una nuova persona con tutti i dati anagrafici.
     *
     * @param codiceFiscale il codice fiscale della persona
     * @param nome il nome della persona
     * @param cognome il cognome della persona
     * @param email l'email della persona
     * @param numeroTelefonico il numero di telefono della persona
     * @param dataNascita la data di nascita della persona
     */
    public Persona(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.dataNascita = dataNascita;
    }

    /**
     * Crea una nuova persona con dati ridotti.
     *
     * @param nome il nome della persona
     * @param cognome il cognome della persona
     */
    public Persona(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
}