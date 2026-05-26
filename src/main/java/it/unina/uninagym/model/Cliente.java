package it.unina.uninagym.model;
import java.util.Date;

public class Cliente extends Persona{
    private Date dataIscrizione;
    private Abbonamento abbonamento;
    private String password;

    public Cliente(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita, Date dataIscrizione, Abbonamento abbonamento, String password) {
        super(codiceFiscale, nome, cognome, email, numeroTelefonico, dataNascita);
        this.dataIscrizione = dataIscrizione;
        this.abbonamento = abbonamento;
        this.password = password;
    }

    //Overloading del Costruttore per aggiungere Clienti alla GUI
    public Cliente(String password, String nome, String cognome){
        super(nome, cognome);
        this.password = password;
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

    public boolean isAbbonamentoValido() {
        return abbonamento != null && abbonamento.getDataFine().after(new Date());
    }

    public Abbonamento getAbbonamentoValido(){
        return isAbbonamentoValido() ? abbonamento : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
