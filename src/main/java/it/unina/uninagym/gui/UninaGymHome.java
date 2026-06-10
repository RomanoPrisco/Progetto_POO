package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerIscrizione;
import it.unina.uninagym.exception.IscrizioneGiaPresenteException;
import it.unina.uninagym.exception.NessunCorsoSelezionatoException;
import it.unina.uninagym.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Gestisce la schermata principale del cliente dopo l'accesso.
 *
 * Consente di visualizzare i dati personali, i corsi disponibili,
 * lo storico degli accessi e di effettuare nuove iscrizioni ai corsi.
 */
public class UninaGymHome {

    /** Pannello principale della schermata home. */
    private JPanel mainHomePanel;

    /** Pannello a schede della schermata home. */
    private JTabbedPane tabbedPanelHome;

    /** Pannello contenente le informazioni di profilo. */
    private JPanel panelProfilo;

    /** Pannello contenente la sezione dei corsi. */
    private JPanel panelCorsi;

    /** Pannello contenente la sezione degli accessi. */
    private JPanel panelAccessi;

    /** Pannello iniziale della schermata home. */
    private JPanel panelHome;

    /** Campo per il nome del cliente nella home. */
    private JTextField campoNomeHome;

    /** Campo per il cognome del cliente nella home. */
    private JTextField campoCognomeHome;

    /** Campo per il codice fiscale del cliente nella home. */
    private JTextField campoCodFiscaleHome;

    /** Campo per la data di scadenza dell'abbonamento nella home. */
    private JTextField campoScadAbbonamentoHome;

    /** Area di testo contenente i corsi seguiti dal cliente. */
    private JTextArea campoCorsiSeguiti;

    /** Pulsante per uscire dalla schermata home. */
    private JButton esciButton;

    /** Campo per il tipo di abbonamento nella home. */
    private JTextField campoTipoAbbonamentoHome;

    /** Campo per la data di iscrizione del cliente. */
    private JTextField campoDataIscrizione;

    /** Campo per il tipo di abbonamento nel profilo. */
    private JTextField campoTipoAbbonamentoProfilo;

    /** Campo per la scadenza dell'abbonamento nel profilo. */
    private JTextField campoScadAbbonamentoProfilo;

    /** Campo per il prezzo mensile dell'abbonamento. */
    private JTextField campoPrezzoMensile;

    /** Campo per il nome del cliente nel profilo. */
    private JTextField campoNomeProfilo;

    /** Campo per il cognome del cliente nel profilo. */
    private JTextField campoCognomeProfilo;

    /** Campo per il codice fiscale del cliente nel profilo. */
    private JTextField campoCodFiscaleProfilo;

    /** Campo per la data di nascita del cliente. */
    private JTextField campoDataNascita;

    /** Campo per l'email del cliente. */
    private JTextField campoEmail;

    /** Campo per il numero di telefono del cliente. */
    private JTextField campoNumTelefono;

    /** Casella di selezione del corso Yoga. */
    private JCheckBox yogaCheckBox;

    /** Casella di selezione del corso Pilates. */
    private JCheckBox pilatesCheckBox;

    /** Casella di selezione del corso Zumba. */
    private JCheckBox zumbaCheckBox;

    /** Casella di selezione del corso Crossfit. */
    private JCheckBox crossfitCheckBox;

    /** Casella di selezione del corso Pesi. */
    private JCheckBox pesiCheckBox;

    /** Casella di selezione del corso Aerobica. */
    private JCheckBox aerobicaCheckBox;

    /** Pulsante per confermare l'iscrizione ai corsi selezionati. */
    private JButton iscrivitiButton;

    /** Tabella contenente i corsi disponibili. */
    private JTable tableCorsi;

    /** Tabella contenente lo storico degli accessi. */
    private JTable tableAccessi;

    /** Campo password del cliente. */
    private JPasswordField campoPassword;

    /** Pulsante per mostrare o nascondere la password. */
    private JButton occhioButton;

    /** Finestra della schermata home. */
    public JFrame frame;

    /** Controller utilizzato per la gestione delle iscrizioni ai corsi. */
    private ControllerIscrizione controllerIscrizione = new ControllerIscrizione();

    /** Cliente attualmente associato alla schermata home. */
    private Cliente cliente;

    /** Array delle caselle di selezione dei corsi. */
    private JCheckBox[] cb = new JCheckBox[6];

    /** Array dei corsi disponibili. */
    private Corso[] corsi = new Corso[6];

    /** Array degli istruttori associati ai corsi. */
    private Istruttore[] istruttori = new Istruttore[6];

    /** Formato utilizzato per la visualizzazione delle date. */
    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    /** Lista degli accessi del cliente. */
    ArrayList<Accesso> accessi = new ArrayList<>();

    /**
     * Crea la schermata home e inizializza i componenti grafici.
     *
     * @param frameChiamante la finestra chiamante da rendere nuovamente visibile in uscita
     */
    public UninaGymHome(JFrame frameChiamante) {

        frame = new JFrame("UninaGymHome");
        frame.setContentPane(mainHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cb[0] = yogaCheckBox;
        cb[1] = pilatesCheckBox;
        cb[2] = zumbaCheckBox;
        cb[3] = crossfitCheckBox;
        cb[4] = pesiCheckBox;
        cb[5] = aerobicaCheckBox;

        ImageIcon iconaOcchioAperto = new ImageIcon(getClass().getResource("/Icone/Occhio-aperto.png"));
        ImageIcon iconaOcchioChiuso = new ImageIcon(getClass().getResource("/Icone/Occhio-chiuso.png"));

        char password = campoPassword.getEchoChar();

        occhioButton.setIcon(iconaOcchioChiuso);
        occhioButton.setBorderPainted(false);
        occhioButton.setContentAreaFilled(false);
        occhioButton.setFocusPainted(false);

        occhioButton.addActionListener(new ActionListener() {
            private boolean passwordSiVede = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordSiVede) {
                    campoPassword.setEchoChar(password);
                    occhioButton.setIcon(iconaOcchioChiuso);
                    passwordSiVede = false;
                } else {
                    campoPassword.setEchoChar((char) 0);
                    occhioButton.setIcon(iconaOcchioAperto);
                    passwordSiVede = true;
                }
            }
        });

        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });

        iscrivitiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean almenoUnoSelezionato = false;
                    boolean almenoUnaConfermata = false;
                    boolean almenoUnaListaAttesa = false;

                    StringBuilder messaggiListaAttesa = new StringBuilder();
                    StringBuilder messaggiGiaPresente = new StringBuilder();
                    StringBuilder messaggiErrore = new StringBuilder();

                    for (int i = 0; i < cb.length; i++) {
                        if (cb[i].isSelected()) {
                            almenoUnoSelezionato = true;

                            if (cliente == null || corsi[i] == null) {
                                messaggiErrore.append(cb[i].getText())
                                        .append(" dati corso non disponibili\n");
                                continue;
                            }

                            try {
                                StatoIscrizione stato = controllerIscrizione.iscriviCliente(cliente, corsi[i]);

                                if (stato == StatoIscrizione.ISCRIZIONE_CREATA) {
                                    almenoUnaConfermata = true;
                                } else if (stato == StatoIscrizione.LISTA_ATTESA) {
                                    almenoUnaListaAttesa = true;
                                    messaggiListaAttesa.append(cb[i].getText())
                                            .append(" inserito in lista d'attesa\n");
                                }

                            } catch (IscrizioneGiaPresenteException ei) {
                                messaggiGiaPresente.append(cb[i].getText())
                                        .append(" ")
                                        .append(ei.getMessage())
                                        .append("\n");

                            } catch (RuntimeException ex) {
                                messaggiErrore.append(cb[i].getText())
                                        .append(" ")
                                        .append(ex.getMessage())
                                        .append("\n");
                            }
                        }
                    }

                    if (!almenoUnoSelezionato) {
                        throw new NessunCorsoSelezionatoException("Seleziona almeno un corso");
                    }

                    caricaCorsiSeguiti();

                    for (int i = 0; i < cb.length; i++) {
                        cb[i].setSelected(false);
                    }

                    StringBuilder messaggioFinale = new StringBuilder();

                    if (almenoUnaConfermata) {
                        messaggioFinale.append("Iscrizione confermata per alcuni corsi.\n");
                    }

                    if (almenoUnaListaAttesa) {
                        messaggioFinale.append("Alcuni corsi sono stati messi in lista d'attesa.\n");
                        messaggioFinale.append(messaggiListaAttesa);
                    }

                    if (messaggiGiaPresente.length() > 0) {
                        messaggioFinale.append("\n").append(messaggiGiaPresente);
                    }

                    if (messaggiErrore.length() > 0) {
                        messaggioFinale.append("\nErrori:\n").append(messaggiErrore);
                    }

                    if (messaggioFinale.length() == 0) {
                        JOptionPane.showMessageDialog(null, "Operazione completata");
                    } else {
                        JOptionPane.showMessageDialog(null, messaggioFinale.toString());
                    }

                } catch (NessunCorsoSelezionatoException enc) {
                    JOptionPane.showMessageDialog(null, enc.getMessage());
                }
            }
        });
    }

    /**
     * Imposta il cliente associato alla schermata home.
     *
     * @param cliente il cliente da associare
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Imposta l'array dei corsi disponibili.
     *
     * @param corsi l'array dei corsi
     */
    public void setCorsi(Corso[] corsi) {
        this.corsi = corsi;
    }

    /**
     * Carica i corsi disponibili a partire da una lista.
     *
     * @param listaCorsi la lista dei corsi da caricare
     */
    public void setCorsiDaLista(ArrayList<Corso> listaCorsi) {
        if (listaCorsi == null) {
            return;
        }

        for (int i = 0; i < listaCorsi.size() && i < corsi.length; i++) {
            corsi[i] = listaCorsi.get(i);
        }
    }

    /**
     * Imposta l'array degli istruttori.
     *
     * @param istruttori l'array degli istruttori
     */
    public void setIstruttori(Istruttore[] istruttori) {
        this.istruttori = istruttori;
    }

    /**
     * Carica nei campi grafici i dati del cliente e dell'abbonamento.
     */
    public void caricaCampiCliente() {
        if (cliente == null) {
            return;
        }

        campoNomeHome.setText(cliente.getNome());
        campoCognomeHome.setText(cliente.getCognome());
        campoCodFiscaleHome.setText(cliente.getCodiceFiscale());
        campoDataIscrizione.setText(formatoData.format(cliente.getDataIscrizione()));
        campoPassword.setText(cliente.getPassword());

        if (cliente.getAbbonamento() != null) {
            campoTipoAbbonamentoHome.setText(cliente.getAbbonamento().getTipo().toString());
            campoScadAbbonamentoHome.setText(formatoData.format(cliente.getAbbonamento().getDataFine()));
        } else {
            campoTipoAbbonamentoHome.setText("");
            campoScadAbbonamentoHome.setText("");
        }

        campoNomeProfilo.setText(cliente.getNome());
        campoCognomeProfilo.setText(cliente.getCognome());
        campoCodFiscaleProfilo.setText(cliente.getCodiceFiscale());
        campoDataNascita.setText(formatoData.format(cliente.getDataNascita()));
        campoEmail.setText(cliente.getEmail());
        campoNumTelefono.setText(cliente.getNumeroTelefonico());

        if (cliente.getAbbonamento() != null) {
            campoTipoAbbonamentoProfilo.setText(cliente.getAbbonamento().getTipo().toString());
            campoScadAbbonamentoProfilo.setText(formatoData.format(cliente.getAbbonamento().getDataFine()));
            campoPrezzoMensile.setText(String.valueOf(cliente.getAbbonamento().getCostoMensile()));
        } else {
            campoTipoAbbonamentoProfilo.setText("");
            campoScadAbbonamentoProfilo.setText("");
            campoPrezzoMensile.setText("");
        }
    }

    /**
     * Carica la tabella dei corsi disponibili.
     *
     * @param righeCorsi le righe da inserire nella tabella dei corsi
     */
    public void caricaTabellaCorsi(ArrayList<Object[]> righeCorsi) {
        String[] colonneCorsi = {
                "NOME CORSO",
                "ID CORSO",
                "ISTRUTTORE",
                "SPECIALIZZAZIONE",
                "CAPIENZA MASSIMA",
                "ORA D'INIZIO",
                "GIORNO"
        };

        DefaultTableModel tabellaCorsi = new DefaultTableModel(colonneCorsi, 0) {
            @Override
            public boolean isCellEditable(int righe, int colonne) {
                return false;
            }
        };

        if (righeCorsi != null) {
            for (Object[] riga : righeCorsi) {
                tabellaCorsi.addRow(riga);
            }
        }

        tableCorsi.setModel(tabellaCorsi);

        tableCorsi.setRowHeight(40);
        tableCorsi.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tableCorsi.getColumnModel().getColumn(0).setPreferredWidth(250);
        tableCorsi.getColumnModel().getColumn(1).setPreferredWidth(50);
        tableCorsi.getColumnModel().getColumn(2).setPreferredWidth(350);
        tableCorsi.getColumnModel().getColumn(3).setPreferredWidth(200);
        tableCorsi.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableCorsi.getColumnModel().getColumn(5).setPreferredWidth(200);
        tableCorsi.getColumnModel().getColumn(6).setPreferredWidth(300);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tableCorsi.getColumnCount(); i++) {
            tableCorsi.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Carica la tabella degli accessi del cliente.
     *
     * @param accessi la lista degli accessi da visualizzare
     */
    public void caricaTabellaAccessi(ArrayList<Accesso> accessi) {
        this.accessi = accessi;

        String[] colonneAccessi = {"ID", "DATA ACCESSO", "STATO ACCESSO"};

        DefaultTableModel tabellaAccessi = new DefaultTableModel(colonneAccessi, 0) {
            @Override
            public boolean isCellEditable(int righe, int colonne) {
                return false;
            }
        };

        if (accessi != null) {
            for (Accesso accesso : accessi) {
                tabellaAccessi.addRow(new Object[]{
                        accesso.getId(),
                        formatoData.format(accesso.getDataInizio()),
                        accesso.getTipo()
                });
            }
        }

        tableAccessi.setModel(tabellaAccessi);
    }

    /**
     * Carica l'elenco dei corsi seguiti dal cliente.
     */
    public void caricaCorsiSeguiti() {
        if (cliente == null) {
            campoCorsiSeguiti.setText("");
            return;
        }

        ArrayList<String> nomiCorsi = controllerIscrizione.leggiNomiCorsiIscritti(cliente.getCodiceFiscale());

        if (nomiCorsi == null || nomiCorsi.isEmpty()) {
            campoCorsiSeguiti.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String nomeCorso : nomiCorsi) {
            builder.append(nomeCorso).append("\n");
        }

        campoCorsiSeguiti.setText(builder.toString());
    }
}