package it.unina.uninagym.model;
import java.util.Date;

public class Cliente extends Persona{
    private Date dataIscrizione;
    private Abbonamento abbonamento;

    public Cliente(String codiceFiscale, String nome, String cognome, String email, String numeroTelefonico, Date dataNascita, Date dataIscrizione, Abbonamento abbonamento) {
        super(codiceFiscale, nome, cognome, email, numeroTelefonico, dataNascita);
        this.dataIscrizione = dataIscrizione;
        this.abbonamento = abbonamento;
    }

    //Overloading del Costruttore per aggiungere Clienti alla GUI
    public Cliente(String codiceFiscale, String nome, String cognome){
        super(codiceFiscale, nome, cognome);
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
}
