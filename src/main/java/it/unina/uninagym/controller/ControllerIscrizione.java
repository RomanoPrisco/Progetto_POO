package it.unina.uninagym.controller;

import it.unina.uninagym.dao.UninaGymDAO;
import it.unina.uninagym.exception.IscrizioneGiaPresenteException;
import it.unina.uninagym.implementazionePostgresDAO.UninaGymImplementazionePostgresDAO;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.Corso;
import it.unina.uninagym.model.Iscrizione;
import it.unina.uninagym.model.StatoIscrizione;

import java.util.Date;
import java.util.ArrayList;

/**
 * Gestisce le operazioni relative all'iscrizione dei clienti ai corsi.
 *
 * Questa classe coordina la verifica di iscrizioni già presenti
 * e l'inserimento di nuove iscrizioni tramite il livello DAO.
 */
public class ControllerIscrizione {

    /** Oggetto DAO utilizzato per l'accesso ai dati persistenti. */
    private UninaGymDAO dao;

    /**
     * Crea un controller per la gestione delle iscrizioni.
     */
    public ControllerIscrizione() {
        dao = new UninaGymImplementazionePostgresDAO();
    }

    /**
     * Iscrive un cliente a un corso.
     *
     * Se il cliente risulta già iscritto al corso, viene sollevata
     * una eccezione.
     *
     * @param cliente il cliente da iscrivere
     * @param corso il corso scelto
     * @return lo stato dell'iscrizione inserita
     * @throws IscrizioneGiaPresenteException se il cliente è già iscritto al corso
     */
    public StatoIscrizione iscriviCliente(Cliente cliente, Corso corso) {
        if (cliente == null || corso == null) {
            return null;
        }

        boolean giaPresente = dao.esisteIscrizione(cliente.getCodiceFiscale(), corso.getId());

        if (giaPresente) {
            throw new IscrizioneGiaPresenteException("Sei già iscritto a questo corso");
        }

        Iscrizione nuovaIscrizione = new Iscrizione(
                0,
                new Date(),
                StatoIscrizione.ISCRIZIONE_CREATA,
                cliente,
                corso
        );

        return dao.inserisciIscrizione(nuovaIscrizione);
    }

    /**
     * Legge i nomi dei corsi a cui un cliente è iscritto.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @return la lista dei nomi dei corsi iscritti
     */
    public ArrayList<String> leggiNomiCorsiIscritti(String codiceFiscaleCliente) {
        return dao.leggiNomiCorsiIscrittiSenzaDuplicati(codiceFiscaleCliente);
    }
}