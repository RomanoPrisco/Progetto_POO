package it.unina.uninagym.model;

import java.sql.Time;

/**
 * Rappresenta un corso disponibile nel sistema UninaGym.
 *
 * Un corso è caratterizzato da un identificativo, un nome,
 * una capienza massima, un orario di inizio e un giorno della settimana.
 */
public class Corso {

    /** Identificativo del corso. */
    private int id;

    /** Nome del corso. */
    private String nome;

    /** Numero massimo di partecipanti al corso. */
    private int capienzaMassima;

    /** Orario di inizio del corso. */
    private Time oraInizio;

    /** Giorno della settimana in cui si svolge il corso. */
    private GiornoSettimana giorno;

    /**
     * Crea un nuovo corso.
     *
     * @param id l'identificativo del corso
     * @param nome il nome del corso
     * @param capienzaMassima il numero massimo di partecipanti
     * @param oraInizio l'orario di inizio del corso
     * @param giorno il giorno della settimana del corso
     */
    public Corso(int id, String nome, int capienzaMassima, Time oraInizio, GiornoSettimana giorno) {
        this.id = id;
        this.nome = nome;
        this.capienzaMassima = capienzaMassima;
        this.oraInizio = oraInizio;
        this.giorno = giorno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapienzaMassima() {
        return capienzaMassima;
    }

    public void setCapienzaMassima(int capienzaMassima) {
        this.capienzaMassima = capienzaMassima;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public GiornoSettimana getGiorno() {
        return giorno;
    }

    public void setGiorno(GiornoSettimana giorno) {
        this.giorno = giorno;
    }
}