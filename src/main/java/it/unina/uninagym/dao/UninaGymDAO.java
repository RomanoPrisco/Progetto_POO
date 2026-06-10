package it.unina.uninagym.dao;

import it.unina.uninagym.model.*;

import java.util.ArrayList;

/**
 * Interfaccia per l'accesso ai dati persistenti del sistema UninaGym.
 */
public interface UninaGymDAO {

    /**
     * Inserisce un cliente e il relativo abbonamento nel database.
     *
     * @param cliente il cliente da inserire
     * @param abbonamento l'abbonamento associato al cliente
     */
    public void inserisciClienteEAbbonamento(Cliente cliente, Abbonamento abbonamento);

    /**
     * Inserisce un accesso valido associato a un cliente.
     *
     * @param accesso l'accesso da inserire
     * @param codiceFiscaleCliente il codice fiscale del cliente
     */
    public void inserisciAccesso(Accesso accesso, String codiceFiscaleCliente);

    /**
     * Inserisce un accesso non valido associato a un cliente.
     *
     * @param accesso l'accesso non valido da inserire
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     */
    public void inserisciAccessoNonValido(Accesso accesso, String nome, String cognome);

    /**
     * Restituisce il prossimo identificativo disponibile per una iscrizione.
     *
     * @return il prossimo id per una iscrizione
     */
    public int prossimoIdIscrizione();

    /**
     * Inserisce una nuova iscrizione nel database.
     *
     * @param iscrizione l'iscrizione da inserire
     * @return lo stato dell'iscrizione inserita
     */
    public StatoIscrizione inserisciIscrizione(Iscrizione iscrizione);

    /**
     * Effettua il login di un cliente.
     *
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param password la password del cliente
     * @param tipoAbbonamento il tipo di abbonamento del cliente
     * @return il cliente autenticato, se presente
     */
    public Cliente loginCliente(String nome, String cognome, String password, TipoAbbonamento tipoAbbonamento);

    /**
     * Verifica se si tratta del primo accesso del cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return true se è il primo accesso, false altrimenti
     */
    public boolean verificaPrimoAccesso(String codiceFiscale);

    /**
     * Legge i corsi con i relativi istruttori.
     *
     * @return la lista dei corsi con istruttore, utilizzato per riempire la JTable
     */
    public ArrayList<Object[]> leggiCorsiEIstruttore();

    /**
     * Legge tutti i corsi presenti nel database.
     *
     * @return la lista dei corsi
     */
    public ArrayList<Corso> leggiCorsi();

    /**
     * Legge tutti gli accessi di un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return la lista degli accessi del cliente
     */
    public ArrayList<Accesso> leggiAccessiCliente(String codiceFiscale);

    /**
     * Legge un cliente tramite codice fiscale.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return il cliente corrispondente
     */
    public Cliente leggiCliente(String codiceFiscale);

    /**
     * Legge l'abbonamento associato a un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return l'abbonamento del cliente
     */
    public Abbonamento leggiAbbonamentoCliente(String codiceFiscale);

    /**
     * Verifica se esiste già una iscrizione a un corso per un cliente.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @param idCorso l'identificativo del corso
     * @return true se l'iscrizione esiste, false altrimenti
     */
    public boolean esisteIscrizione(String codiceFiscaleCliente, int idCorso);

    /**
     * Legge i nomi dei corsi a cui il cliente è iscritto senza duplicati.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @return la lista dei nomi dei corsi senza duplicati
     */
    public ArrayList<String> leggiNomiCorsiIscrittiSenzaDuplicati(String codiceFiscaleCliente);

    /**
     * Legge lo stato dell'ultima iscrizione del cliente a un corso.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @param idCorso l'identificativo del corso
     * @return lo stato dell'ultima iscrizione
     */
    public StatoIscrizione leggiStatoUltimaIscrizione(String codiceFiscaleCliente, int idCorso);
}