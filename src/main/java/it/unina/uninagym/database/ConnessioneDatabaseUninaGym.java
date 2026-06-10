package it.unina.uninagym.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database del sistema UninaGym.
 *
 * Questa classe implementa il pattern Singleton per garantire
 * un unico punto di accesso alla connessione PostgreSQL.
 */
public class ConnessioneDatabaseUninaGym {

    /** Istanza unica della classe di connessione. */
    private static ConnessioneDatabaseUninaGym instance;

    /** Connessione al database PostgreSQL. */
    private Connection connection = null;

    /** Nome utente del database. */
    private String nome = "postgres";

    /** Password del database. */
    private String password = "Us1ng_SQL_4-D474Base";

    /** URL di connessione al database. */
    private String url = "jdbc:postgresql://localhost:5432/uninagym";

    /** Nome del driver PostgreSQL. */
    private String driver = "org.postgresql.Driver";

    /**
     * Crea una nuova connessione al database.
     *
     * @throws SQLException se si verifica un errore durante la connessione
     */
    private ConnessioneDatabaseUninaGym() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Restituisce l'istanza unica della classe di connessione.
     *
     * Se necessario, crea una nuova connessione al database.
     *
     * @return l'istanza della connessione al database
     * @throws SQLException se si verifica un errore durante la connessione
     */
    public static ConnessioneDatabaseUninaGym getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabaseUninaGym();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDatabaseUninaGym();
        }
        return instance;
    }

    /**
     * Restituisce la connessione al database.
     *
     * @return la connessione al database
     */
    public Connection getConnection() {
        return connection;
    }
}