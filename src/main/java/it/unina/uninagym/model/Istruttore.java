package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta un istruttore del sistema UninaGym.
 *
 * Un istruttore è caratterizzato dai dati anagrafici ereditati da {@link Persona},
 * dalla specializzazione, dalle certificazioni e dalla tariffa oraria.
 */
public class Istruttore extends Persona {

    /** Specializzazione dell'istruttore. */
    private String specializzazione;

    /** Certificazioni possedute dall'istruttore. */
    private String certificazioni;

    /** Tariffa oraria dell'istruttore. */
    private double tariffaOraria;

    /**
     * Crea un nuovo istruttore.
     *
     * @param codiceFiscale il codice fiscale dell'istruttore
     * @param nome il nome dell'istruttore
     * @param cognome il cognome dell'istruttore
     * @param email l'email dell'istruttore
     * @param numeroTelefonico il numero di telefono dell'istruttore
     * @param dataNascita la data di nascita dell'istruttore
     * @param specializzazione la specializzazione dell'istruttore
     * @param certificazioni le certificazioni dell'istruttore
     * @param tariffaOraria la tariffa oraria dell'istruttore
     */
    public Istruttore(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita, String specializzazione, String certificazioni, double tariffaOraria) {
        super(codiceFiscale, nome, cognome, email, numeroTelefonico, dataNascita);
        this.specializzazione = specializzazione;
        this.certificazioni = certificazioni;
        this.tariffaOraria = tariffaOraria;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    public String getCertificazioni() {
        return certificazioni;
    }

    public void setCertificazioni(String certificazioni) {
        this.certificazioni = certificazioni;
    }

    public double getTariffaOraria() {
        return tariffaOraria;
    }

    public void setTariffaOraria(double tariffaOraria) {
        this.tariffaOraria = tariffaOraria;
    }
}