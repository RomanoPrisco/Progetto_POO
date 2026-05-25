package it.unina.uninagym.model;
import java.util.Date;

public abstract class Persona {
    protected String codiceFiscale;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String numeroTelefonico;
    protected Date dataNascita;

    public Persona(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.dataNascita = dataNascita;
    }

    //Overloading del Costruttore per aggiungere Clienti alla GUI
    public Persona(String codiceFiscale, String nome, String cognome){
        this.codiceFiscale = codiceFiscale;
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
