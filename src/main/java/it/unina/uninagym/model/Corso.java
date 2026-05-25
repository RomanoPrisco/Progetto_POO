package it.unina.uninagym.model;
import java.sql.Time;
import java.util.Date;

public class Corso {
    private int id;
    private String nome;
    private int capienzaMassima;
    private Time oraInizio;
    private Date giorno;

    public Corso(int id, String nome, int capienzaMassima, Time oraInizio, Date giorno) {
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

    public Date getGiorno() {
        return giorno;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }
}
