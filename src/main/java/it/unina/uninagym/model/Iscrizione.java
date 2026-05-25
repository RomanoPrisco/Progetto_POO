package it.unina.uninagym.model;
import java.util.Date;

public class Iscrizione {
    private int id;
    private Date dataIscrizione;
    private StatoIscrizione stato;
    private Cliente cliente;
    private Corso corso;


    public Iscrizione(int id, Date dataIscrizione, StatoIscrizione stato, Cliente cliente, Corso corso) {
        this.id = id;
        this.dataIscrizione = dataIscrizione;
        this.stato = stato;
        this.cliente = cliente;
        this.corso = corso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public StatoIscrizione getStato() {
        return stato;
    }

    public void setStato(StatoIscrizione stato) {
        this.stato = stato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }
}
