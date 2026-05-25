package it.unina.uninagym.model;

import java.util.Date;

public class Istruttore extends Persona{
    private String specializzazione;
    private String certificazioni;
    private double tariffaOraria;

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
