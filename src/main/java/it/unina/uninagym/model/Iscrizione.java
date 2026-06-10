package it.unina.uninagym.model;

import java.util.Date;

/**
 * Rappresenta l'iscrizione di un cliente a un corso.
 *
 * Una iscrizione è caratterizzata da un identificativo, una data,
 * uno stato, un cliente e un corso associato.
 */
public class Iscrizione {

    /** Identificativo dell'iscrizione. */
    private int id;

    /** Data dell'iscrizione. */
    private Date dataIscrizione;

    /** Stato dell'iscrizione. */
    private StatoIscrizione stato;

    /** Cliente associato all'iscrizione. */
    private Cliente cliente;

    /** Corso associato all'iscrizione. */
    private Corso corso;

    /**
     * Crea una nuova iscrizione.
     *
     * @param id l'identificativo dell'iscrizione
     * @param dataIscrizione la data dell'iscrizione
     * @param stato lo stato dell'iscrizione
     * @param cliente il cliente associato
     * @param corso il corso associato
     */
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