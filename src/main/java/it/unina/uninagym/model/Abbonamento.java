package it.unina.uninagym.model;
import java.util.Date;

public class Abbonamento {
    private int id;
    private TipoAbbonamento tipo;
    private Date dataInizio;
    private Date dataFine;
    private double costoMensile;

    public Abbonamento(int id, TipoAbbonamento tipo, Date dataInizio, Date dataFine, double costoBase) {
        this.id = id;
        this.tipo = tipo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.costoMensile = (tipo == TipoAbbonamento.GOLD) ? costoBase - (costoBase * 0.30) : costoBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public double getCostoMensile() {
        return costoMensile;
    }

    public void setCostoMensile(double costoMensile) {
        this.costoMensile = costoMensile;
    }
}
