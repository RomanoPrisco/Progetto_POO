package it.unina.uninagym.implementazionePostgresDAO;

import it.unina.uninagym.dao.UninaGymDAO;
import it.unina.uninagym.database.ConnessioneDatabaseUninaGym;
import it.unina.uninagym.model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione PostgreSQL dell'interfaccia {@link UninaGymDAO}.
 *
 * Gestisce le operazioni di lettura e scrittura dei dati persistenti
 * del sistema UninaGym tramite connessione al database.
 */
public class UninaGymImplementazionePostgresDAO implements UninaGymDAO {

    /**
     * Connessione al database PostgreSQL.
     */
    private Connection connection;

    /**
     * Crea un oggetto DAO e inizializza la connessione al database.
     */
    public UninaGymImplementazionePostgresDAO() {
        try {
            connection = ConnessioneDatabaseUninaGym.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserisce nel database un nuovo cliente e il relativo abbonamento.
     *
     * @param cliente il cliente da inserire
     * @param abbonamento l'abbonamento associato al cliente
     */
    @Override
    public void inserisciClienteEAbbonamento(Cliente cliente, Abbonamento abbonamento) {
        String sqlCliente = "INSERT INTO cliente " +
                "(codicefiscale, nome, cognome, email, numerotelefonico, datanascita, dataiscrizione, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlAbbonamento = "INSERT INTO abbonamento " +
                "(tipo, datainizio, datafine, costomensile, cliente_cf) " +
                "VALUES (CAST(? AS tipoabbonamento), ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement psCliente = connection.prepareStatement(sqlCliente);
                 PreparedStatement psAbbonamento = connection.prepareStatement(sqlAbbonamento)) {

                psCliente.setString(1, cliente.getCodiceFiscale());
                psCliente.setString(2, cliente.getNome());
                psCliente.setString(3, cliente.getCognome());
                psCliente.setString(4, cliente.getEmail());
                psCliente.setString(5, cliente.getNumeroTelefonico());
                psCliente.setDate(6, new java.sql.Date(cliente.getDataNascita().getTime()));
                psCliente.setDate(7, new java.sql.Date(cliente.getDataIscrizione().getTime()));
                psCliente.setString(8, cliente.getPassword());
                psCliente.executeUpdate();

                psAbbonamento.setString(1, abbonamento.getTipo().name());
                psAbbonamento.setDate(2, new java.sql.Date(abbonamento.getDataInizio().getTime()));
                psAbbonamento.setDate(3, new java.sql.Date(abbonamento.getDataFine().getTime()));
                psAbbonamento.setDouble(4, abbonamento.getCostoMensile());
                psAbbonamento.setString(5, cliente.getCodiceFiscale());
                psAbbonamento.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Effettua il login di un cliente verificando i dati inseriti.
     *
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param password la password del cliente
     * @param tipoAbbonamento il tipo di abbonamento del cliente
     * @return il cliente autenticato, se presente; null altrimenti
     */
    @Override
    public Cliente loginCliente(String nome, String cognome, String password, TipoAbbonamento tipoAbbonamento) {
        String sql = "SELECT c.codicefiscale, c.nome, c.cognome, c.email, c.numerotelefonico, " +
                "c.datanascita, c.dataiscrizione, c.password, " +
                "a.id AS abbonamento_id, a.tipo, a.datainizio, a.datafine, a.costomensile " +
                "FROM cliente c " +
                "JOIN abbonamento a ON c.codicefiscale = a.cliente_cf " +
                "WHERE c.nome = ? AND c.cognome = ? AND c.password = ? " +
                "AND a.tipo = CAST(? AS tipoabbonamento)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, password);
            ps.setString(4, tipoAbbonamento.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Abbonamento abbonamento = new Abbonamento(
                            rs.getInt("abbonamento_id"),
                            TipoAbbonamento.valueOf(rs.getString("tipo")),
                            rs.getDate("datainizio"),
                            rs.getDate("datafine"),
                            rs.getDouble("costomensile")
                    );

                    return new Cliente(
                            rs.getString("codicefiscale"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("email"),
                            rs.getString("numerotelefonico"),
                            rs.getDate("datanascita"),
                            rs.getDate("dataiscrizione"),
                            abbonamento,
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Verifica se il cliente sta effettuando il primo accesso valido.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return true se si tratta del primo accesso valido, false altrimenti
     */
    @Override
    public boolean verificaPrimoAccesso(String codiceFiscale) {
        String sql = "SELECT COUNT(*) AS totale FROM accesso WHERE cliente_cf = ? AND tipo = CAST(? AS tipoaccesso)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);
            ps.setString(2, TipoAccesso.VALIDO.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numeroAccessiValidi = rs.getInt("totale");
                    return numeroAccessiValidi == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Inserisce un accesso valido nel database.
     *
     * @param accesso l'accesso da inserire
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @throws RuntimeException se l'accesso non è consentito
     */
    @Override
    public void inserisciAccesso(Accesso accesso, String codiceFiscaleCliente) {
        String sql = "INSERT INTO accesso (datainizio, tipo, cliente_cf) VALUES (?, CAST(? AS tipoaccesso), ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(accesso.getDataInizio().getTime()));
            ps.setString(2, accesso.getTipo().name());
            ps.setString(3, codiceFiscaleCliente);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Accesso non consentito: abbonamento scaduto");
        }
    }

    /**
     * Inserisce un accesso non valido nel database.
     *
     * @param accesso l'accesso non valido da inserire
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     */
    @Override
    public void inserisciAccessoNonValido(Accesso accesso, String nome, String cognome) {
        String sql = "INSERT INTO accesso (datainizio, tipo, cliente_cf) VALUES (?, CAST(? AS tipoaccesso), NULL)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(accesso.getDataInizio().getTime()));
            ps.setString(2, accesso.getTipo().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Legge i corsi con i relativi istruttori.
     *
     * @return la lista dei corsi con istruttore
     */
    @Override
    public ArrayList<Object[]> leggiCorsiEIstruttore() {
        ArrayList<Object[]> risultati = new ArrayList<>();

        String sql = "SELECT c.nome AS nome_corso, c.id AS id_corso, " +
                "i.nome AS nome_istruttore, i.cognome AS cognome_istruttore, " +
                "i.specializzazione, c.capienzamax, c.orainizio, c.giorno " +
                "FROM corso c " +
                "JOIN istruttore i ON i.corso_id = c.id " +
                "ORDER BY c.id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] riga = new Object[] {
                        rs.getString("nome_corso"),
                        rs.getInt("id_corso"),
                        rs.getString("nome_istruttore") + " " + rs.getString("cognome_istruttore"),
                        rs.getString("specializzazione"),
                        rs.getInt("capienzamax"),
                        rs.getTime("orainizio"),
                        rs.getString("giorno")
                };

                risultati.add(riga);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    /**
     * Legge tutti i corsi presenti nel database.
     *
     * @return la lista dei corsi
     */
    @Override
    public ArrayList<Corso> leggiCorsi() {
        ArrayList<Corso> corsi = new ArrayList<>();

        String sql = "SELECT id, nome, capienzamax, orainizio, giorno FROM corso ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Corso corso = new Corso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("capienzamax"),
                        rs.getTime("orainizio"),
                        GiornoSettimana.valueOf(rs.getString("giorno"))
                );

                corsi.add(corso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return corsi;
    }

    /**
     * Legge tutti gli accessi di un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return la lista degli accessi del cliente
     */
    @Override
    public ArrayList<Accesso> leggiAccessiCliente(String codiceFiscale) {
        ArrayList<Accesso> accessi = new ArrayList<>();

        String sql = "SELECT id, datainizio, tipo " +
                "FROM accesso " +
                "WHERE cliente_cf = ? " +
                "ORDER BY datainizio DESC, id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Accesso accesso = new Accesso(
                            rs.getInt("id"),
                            rs.getDate("datainizio"),
                            TipoAccesso.valueOf(rs.getString("tipo"))
                    );

                    accessi.add(accesso);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accessi;
    }

    /**
     * Legge un cliente tramite codice fiscale.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return il cliente corrispondente, se presente; null altrimenti
     */
    @Override
    public Cliente leggiCliente(String codiceFiscale) {
        String sql = "SELECT * FROM cliente WHERE codicefiscale = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getString("codicefiscale"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("email"),
                            rs.getString("numerotelefonico"),
                            rs.getDate("datanascita"),
                            rs.getDate("dataiscrizione"),
                            null,
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Legge l'abbonamento associato a un cliente.
     *
     * @param codiceFiscale il codice fiscale del cliente
     * @return l'abbonamento del cliente, se presente; null altrimenti
     */
    @Override
    public Abbonamento leggiAbbonamentoCliente(String codiceFiscale) {
        String sql = "SELECT * FROM abbonamento WHERE cliente_cf = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Abbonamento(
                            rs.getInt("id"),
                            TipoAbbonamento.valueOf(rs.getString("tipo")),
                            rs.getDate("datainizio"),
                            rs.getDate("datafine"),
                            rs.getDouble("costomensile")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Restituisce il prossimo identificativo disponibile per una iscrizione.
     *
     * @return il prossimo id disponibile
     */
    @Override
    public int prossimoIdIscrizione() {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS prossimo_id FROM iscrizione";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("prossimo_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * Inserisce una nuova iscrizione nel database.
     *
     * @param iscrizione l'iscrizione da inserire
     * @return lo stato dell'iscrizione inserita
     * @throws RuntimeException se si verifica un errore durante l'inserimento
     */
    @Override
    public StatoIscrizione inserisciIscrizione(Iscrizione iscrizione) {
        String sql = "INSERT INTO iscrizione (dataiscrizione, stato, cliente_cf, corso_id) " +
                "VALUES (?, CAST(? AS statoiscrizione), ?, ?) " +
                "RETURNING stato";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(iscrizione.getDataIscrizione().getTime()));
            ps.setString(2, iscrizione.getStato().name());
            ps.setString(3, iscrizione.getCliente().getCodiceFiscale());
            ps.setInt(4, iscrizione.getCorso().getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return StatoIscrizione.valueOf(rs.getString("stato"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'iscrizione al corso");
        }

        return null;
    }

    /**
     * Legge i nomi dei corsi a cui un cliente è iscritto senza duplicati.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @return la lista dei nomi dei corsi senza duplicati
     */
    @Override
    public ArrayList<String> leggiNomiCorsiIscrittiSenzaDuplicati(String codiceFiscaleCliente) {
        ArrayList<String> nomiCorsi = new ArrayList<>();

        String sql = "SELECT DISTINCT c.nome " +
                "FROM iscrizione i " +
                "JOIN corso c ON i.corso_id = c.id " +
                "WHERE i.cliente_cf = ? " +
                "AND i.stato = CAST(? AS statoiscrizione) " +
                "ORDER BY c.nome";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscaleCliente);
            ps.setString(2, StatoIscrizione.ISCRIZIONE_CREATA.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nomiCorsi.add(rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomiCorsi;
    }

    /**
     * Verifica se esiste già una iscrizione a un corso per un cliente.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @param idCorso l'identificativo del corso
     * @return true se l'iscrizione esiste, false altrimenti
     */
    @Override
    public boolean esisteIscrizione(String codiceFiscaleCliente, int idCorso) {
        String sql = "SELECT COUNT(*) AS totale " +
                "FROM iscrizione " +
                "WHERE cliente_cf = ? AND corso_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscaleCliente);
            ps.setInt(2, idCorso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("totale") > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Legge lo stato dell'ultima iscrizione di un cliente a un corso.
     *
     * @param codiceFiscaleCliente il codice fiscale del cliente
     * @param idCorso l'identificativo del corso
     * @return lo stato dell'ultima iscrizione, se presente; null altrimenti
     */
    @Override
    public StatoIscrizione leggiStatoUltimaIscrizione(String codiceFiscaleCliente, int idCorso) {
        String sql = "SELECT stato FROM iscrizione WHERE cliente_cf = ? AND corso_id = ? ORDER BY id DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codiceFiscaleCliente);
            ps.setInt(2, idCorso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return StatoIscrizione.valueOf(rs.getString("stato"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}