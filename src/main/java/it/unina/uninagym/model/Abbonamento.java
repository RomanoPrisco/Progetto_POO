package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta un abbonamento del sistema UninaGym.
 *
 * Un abbonamento è caratterizzato da un identificativo, una tipologia,
 * una data di inizio, una data di fine e un costo mensile.
 */
public class Abbonamento {

    /** Identificativo dell'abbonamento. */
    private int id;

    /** Tipologia dell'abbonamento. */
    private TipoAbbonamento tipo;

    /** Data di inizio dell'abbonamento. */
    private Date dataInizio;

    /** Data di fine dell'abbonamento. */
    private Date dataFine;

    /** Costo mensile dell'abbonamento. */
    private double costoMensile;

    /**
     * Crea un nuovo abbonamento.
     *
     * @param id l'identificativo dell'abbonamento
     * @param tipo la tipologia dell'abbonamento
     * @param dataInizio la data di inizio dell'abbonamento
     * @param dataFine la data di fine dell'abbonamento
     * @param costoMensile il costo mensile dell'abbonamento
     */
    public Abbonamento(int id, TipoAbbonamento tipo, Date dataInizio, Date dataFine, double costoMensile) {
        this.id = id;
        this.tipo = tipo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.costoMensile = costoMensile;
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