package it.unina.uninagym.controller;

import it.unina.uninagym.dao.UninaGymDAO;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.exception.ClienteNonEsistenteException;
import it.unina.uninagym.implementazionePostgresDAO.UninaGymImplementazionePostgresDAO;
import it.unina.uninagym.model.Abbonamento;
import it.unina.uninagym.model.Accesso;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.Corso;
import it.unina.uninagym.model.TipoAbbonamento;
import it.unina.uninagym.model.TipoAccesso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Gestisce le operazioni di accesso e registrazione dei clienti.
 *
 * Questa classe coordina la validazione dei dati inseriti
 * e le operazioni di lettura e scrittura sul database
 * tramite il livello DAO.
 */
public class ControllerAccesso {

    /** Oggetto DAO utilizzato per l'accesso ai dati persistenti. */
    private UninaGymDAO dao;

    /**
     * Crea un controller per la gestione dell'accesso.
     */
    public ControllerAccesso() {
        dao = new UninaGymImplementazionePostgresDAO();
    }

    /**
     * Registra un nuovo cliente con il relativo abbonamento.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param email l'email del cliente
     * @param numeroTelefonico il numero di telefono del cliente
     * @param dataNascita la data di nascita del cliente
     * @param password la password del cliente
     * @param tipoAbbonamento il tipo di abbonamento scelto
     * @throws CampoNonTrovatoException se uno o più campi obbligatori sono vuoti
     */
    public void registraCliente(String codiceFiscale,
                                String nome,
                                String cognome,
                                String email,
                                String numeroTelefonico,
                                Date dataNascita,
                                String password,
                                TipoAbbonamento tipoAbbonamento) {

        if (codiceFiscale == null || codiceFiscale.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Codice fiscale\" non può essere vuoto");
        }
        if (nome == null || nome.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Nome\" non può essere vuoto");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Cognome\" non può essere vuoto");
        }
        if (email == null || email.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Email\" non può essere vuoto");
        }
        if (numeroTelefonico == null || numeroTelefonico.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Numero telefonico\" non può essere vuoto");
        }
        if (dataNascita == null) {
            throw new CampoNonTrovatoException("Il campo \"Data di nascita\" non può essere vuoto");
        }
        if (password == null || password.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Password\" non può essere vuoto");
        }
        if (tipoAbbonamento == null) {
            throw new CampoNonTrovatoException("Non è stato selezionato nessun abbonamento");
        }

        Date oggi = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oggi);
        calendar.add(Calendar.YEAR, 1);
        Date dataFine = calendar.getTime();

        Abbonamento nuovoAbbonamento = new Abbonamento(
                0,
                tipoAbbonamento,
                oggi,
                dataFine,
                50.0
        );

        Cliente nuovoCliente = new Cliente(
                codiceFiscale,
                nome,
                cognome,
                email,
                numeroTelefonico,
                dataNascita,
                oggi,
                nuovoAbbonamento,
                password
        );

        dao.inserisciClienteEAbbonamento(nuovoCliente, nuovoAbbonamento);
    }

    /**
     * Verifica se il cliente sta effettuando il primo accesso valido.
     *
     * In caso di credenziali errate, registra un accesso non valido
     * e solleva una eccezione.
     *
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param password la password del cliente
     * @param tipoAbbonamento il tipo di abbonamento del cliente
     * @return true se il cliente è al primo accesso valido, false altrimenti
     * @throws CampoNonTrovatoException se uno o più campi obbligatori sono vuoti
     * @throws ClienteNonEsistenteException se le credenziali non sono valide
     */
    public boolean verificaPrimoAccesso(String nome,
                                        String cognome,
                                        String password,
                                        TipoAbbonamento tipoAbbonamento) {

        if (nome == null || nome.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Nome\" non può essere vuoto");
        }
        if (cognome == null || cognome.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Cognome\" non può essere vuoto");
        }
        if (password == null || password.isBlank()) {
            throw new CampoNonTrovatoException("Il campo \"Password\" non può essere vuoto");
        }
        if (tipoAbbonamento == null) {
            throw new CampoNonTrovatoException("Seleziona un tipo di abbonamento");
        }

        Cliente cliente = dao.loginCliente(nome, cognome, password, tipoAbbonamento);

        if (cliente == null) {
            Accesso accessoNonValido = new Accesso(
                    0,
                    new Date(),
                    TipoAccesso.NON_VALIDO
            );

            dao.inserisciAccessoNonValido(accessoNonValido, nome, cognome);
            throw new ClienteNonEsistenteException("Credenziali non valide");
        }

        boolean primoAccesso = dao.verificaPrimoAccesso(cliente.getCodiceFiscale());

        Accesso accessoValido = new Accesso(
                0,
                new Date(),
                TipoAccesso.VALIDO
        );

        dao.inserisciAccesso(accessoValido, cliente.getCodiceFiscale());

        return primoAccesso;
    }

    /**
     * Restituisce il cliente autenticato tramite le credenziali inserite.
     *
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param password la password del cliente
     * @param tipoAbbonamento il tipo di abbonamento del cliente
     * @return il cliente autenticato
     * @throws ClienteNonEsistenteException se le credenziali non sono valide
     */
    public Cliente getClienteLogin(String nome,
                                   String cognome,
                                   String password,
                                   TipoAbbonamento tipoAbbonamento) {

        Cliente cliente = dao.loginCliente(nome, cognome, password, tipoAbbonamento);

        if (cliente == null) {
            throw new ClienteNonEsistenteException("Credenziali non valide");
        }

        return cliente;
    }

    /**
     * Legge i corsi con i relativi istruttori.
     *
     * @return la lista dei corsi con istruttore
     */
    public ArrayList<Object[]> leggiCorsiEIstruttore() {
        return dao.leggiCorsiEIstruttore();
    }

    /**
     * Legge tutti gli accessi di un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return la lista degli accessi del cliente
     */
    public ArrayList<Accesso> leggiAccessiCliente(String codiceFiscale) {
        return dao.leggiAccessiCliente(codiceFiscale);
    }

    /**
     * Legge un cliente tramite codice fiscale.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return il cliente corrispondente
     */
    public Cliente leggiCliente(String codiceFiscale) {
        return dao.leggiCliente(codiceFiscale);
    }

    /**
     * Legge l'abbonamento associato a un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return l'abbonamento del cliente
     */
    public Abbonamento leggiAbbonamentoCliente(String codiceFiscale) {
        return dao.leggiAbbonamentoCliente(codiceFiscale);
    }

    /**
     * Legge tutti i corsi presenti nel database.
     *
     * @return la lista dei corsi
     */
    public ArrayList<Corso> leggiCorsi() {
        return dao.leggiCorsi();
    }
}