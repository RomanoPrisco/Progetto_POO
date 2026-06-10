package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta un accesso registrato nel sistema UninaGym.
 *
 * Un accesso è caratterizzato da un identificativo, una data e una tipologia.
 */
public class Accesso {

    /** Identificativo dell'accesso. */
    private int id;

    /** Data dell'accesso. */
    private Date dataInizio;

    /** Tipologia dell'accesso. */
    private TipoAccesso tipo;

    /**
     * Crea un nuovo accesso.
     *
     * @param id l'identificativo dell'accesso
     * @param dataInizio la data dell'accesso
     * @param tipo la tipologia dell'accesso
     */
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