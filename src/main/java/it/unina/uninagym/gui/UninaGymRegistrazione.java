package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerAccesso;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.model.TipoAbbonamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Gestisce la finestra di registrazione di un nuovo cliente.
 *
 * Consente l'inserimento dei dati anagrafici, della password
 * e della tipologia di abbonamento scelta.
 */
public class UninaGymRegistrazione {

    /** Pannello principale della finestra di registrazione. */
    private JPanel mainRegistrazionePanel;

    /** Campo di testo per il nome del cliente. */
    private JTextField campoNome;

    /** Campo di testo per il cognome del cliente. */
    private JTextField campoCognome;

    /** Campo di testo per il codice fiscale del cliente. */
    private JTextField campoCodiceFiscale;

    /** Campo di testo per l'email del cliente. */
    private JTextField campoEmail;

    /** Campo password del cliente. */
    private JPasswordField campoPassword;

    /** Pulsante per confermare la registrazione. */
    private JButton confermaButton;

    /** Pulsante di selezione per l'abbonamento GOLD. */
    private JRadioButton radioGold;

    /** Pulsante di selezione per l'abbonamento NORMAL. */
    private JRadioButton radioNormal;

    /** Pulsante per uscire dalla finestra di registrazione. */
    private JButton esciButton;

    /** Pulsante per mostrare o nascondere la password. */
    private JButton occhioButton;

    /** Campo di testo per il numero di telefono del cliente. */
    private JTextField campoNumTelefono;

    /** Campo per la selezione della data di nascita. */
    private JSpinner campoDataNascita;

    /** Finestra della schermata di registrazione. */
    private static JFrame frame;

    /** Controller che gestisce la registrazione del cliente. */
    private ControllerAccesso controllerAccesso;

    /** Gruppo dei pulsanti relativi alla scelta dell'abbonamento. */
    private ButtonGroup gruppoAbbonamento;

    /**
     * Crea la finestra di registrazione e inizializza i componenti grafici.
     *
     * @param frameChiamante la finestra chiamante da rendere nuovamente visibile in uscita
     * @param controllerAccesso il controller utilizzato per registrare il cliente
     */
    public UninaGymRegistrazione(JFrame frameChiamante, ControllerAccesso controllerAccesso) {
        this.controllerAccesso = controllerAccesso;

        frame = new JFrame("UninaGymRegistrazione");
        frame.setContentPane(mainRegistrazionePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        gruppoAbbonamento = new ButtonGroup();
        gruppoAbbonamento.add(radioGold);
        gruppoAbbonamento.add(radioNormal);

        SpinnerDateModel modelData = new SpinnerDateModel(
                new Date(),
                null,
                null,
                java.util.Calendar.DAY_OF_MONTH
        );

        campoDataNascita.setModel(modelData);
        campoDataNascita.setEditor(new JSpinner.DateEditor(campoDataNascita, "dd/MM/yyyy"));

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

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codiceFiscale = campoCodiceFiscale.getText();
                    String nome = campoNome.getText();
                    String cognome = campoCognome.getText();
                    String email = campoEmail.getText();
                    String numeroTelefonico = campoNumTelefono.getText();
                    Date dataNascita = (Date) campoDataNascita.getValue();
                    String password = new String(campoPassword.getPassword());

                    TipoAbbonamento tipoAbbonamento = null;

                    if (radioGold.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.GOLD;
                    } else if (radioNormal.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.NORMAL;
                    }

                    controllerAccesso.registraCliente(
                            codiceFiscale,
                            nome,
                            cognome,
                            email,
                            numeroTelefonico,
                            dataNascita,
                            password,
                            tipoAbbonamento
                    );

                    JOptionPane.showMessageDialog(null, "Registrazione avvenuta correttamente");

                    frame.dispose();
                    frameChiamante.setVisible(true);

                } catch (CampoNonTrovatoException ec) {
                    JOptionPane.showMessageDialog(null, ec.getMessage());
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
    }
}