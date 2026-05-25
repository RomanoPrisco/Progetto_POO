package it.unina.uninagym.model;
import java.util.Date;

public class Accesso {
    private int id;
    private Date dataInizio;
    private TipoAccesso tipo;

    public Accesso(int id, Date dataInizio, TipoAccesso tipo) {
        this.id = id;
        this.dataInizio = dataInizio;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public TipoAccesso getTipo() {
        return tipo;
    }

    public void setTipo(TipoAccesso tipo) {
        this.tipo = tipo;
    }
}
