package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerAccesso;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.exception.ClienteNonEsistenteException;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.TipoAbbonamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gestisce la schermata principale di accesso del sistema UninaGym.
 *
 * Consente al cliente di effettuare il login, accedere alla registrazione
 * oppure uscire dall'applicazione.
 */
public class UninaGymMain {

    /** Pannello principale della schermata di accesso. */
    private JPanel mainPanelAccesso;

    /** Pulsante per effettuare l'accesso. */
    private JButton accediButton;

    /** Pulsante per uscire dall'applicazione. */
    private JButton esciButton;

    /** Campo di testo per il nome del cliente. */
    private JTextField campoNome;

    /** Campo di testo per il cognome del cliente. */
    private JTextField campoCognome;

    /** Campo di testo per il codice fiscale del cliente. */
    private JTextField campoCodFiscale;

    /** Pulsante di selezione per l'abbonamento NORMAL. */
    private JRadioButton radioNormal;

    /** Pulsante di selezione per l'abbonamento GOLD. */
    private JRadioButton radioGold;

    /** Campo password del cliente. */
    private JPasswordField campoPassword;

    /** Pulsante per aprire la schermata di registrazione. */
    private JButton registrazioneButton;

    /** Pulsante per mostrare o nascondere la password. */
    private JButton occhioButton;

    /** Finestra principale dell'applicazione. */
    private static JFrame frame;

    /** Controller che gestisce le operazioni di accesso. */
    private ControllerAccesso controllerAccesso;

    /** Gruppo dei pulsanti relativi alla scelta dell'abbonamento. */
    private ButtonGroup gruppoAbbonamento;

    /**
     * Crea la schermata principale di accesso e inizializza i componenti grafici.
     *
     * @param frameChiamante la finestra chiamante
     * @param controllerAccesso il controller utilizzato per la gestione dell'accesso
     */
    public UninaGymMain(JFrame frameChiamante, ControllerAccesso controllerAccesso) {
        this.controllerAccesso = controllerAccesso;

        gruppoAbbonamento = new ButtonGroup();
        gruppoAbbonamento.add(radioGold);
        gruppoAbbonamento.add(radioNormal);

        ImageIcon iconaOcchioAperto = new ImageIcon(getClass().getResource("/Icone/Occhio-aperto.png"));
        ImageIcon iconaOcchioChiuso = new ImageIcon(getClass().getResource("/Icone/Occhio-chiuso.png"));

        char echoChar = campoPassword.getEchoChar();

        occhioButton.setIcon(iconaOcchioChiuso);
        occhioButton.setBorderPainted(false);
        occhioButton.setContentAreaFilled(false);
        occhioButton.setFocusPainted(false);

        occhioButton.addActionListener(new ActionListener() {
            private boolean passwordSiVede = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordSiVede) {
                    campoPassword.setEchoChar(echoChar);
                    occhioButton.setIcon(iconaOcchioChiuso);
                    passwordSiVede = false;
                } else {
                    campoPassword.setEchoChar((char) 0);
                    occhioButton.setIcon(iconaOcchioAperto);
                    passwordSiVede = true;
                }
            }
        });

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = campoNome.getText();
                    String cognome = campoCognome.getText();
                    String password = new String(campoPassword.getPassword());

                    TipoAbbonamento tipoAbbonamento = null;

                    if (radioGold.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.GOLD;
                    } else if (radioNormal.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.NORMAL;
                    }

                    boolean isPrimoAccesso = controllerAccesso.verificaPrimoAccesso(
                            nome,
                            cognome,
                            password,
                            tipoAbbonamento
                    );

                    Cliente cliente = controllerAccesso.getClienteLogin(
                            nome,
                            cognome,
                            password,
                            tipoAbbonamento
                    );

                    if (isPrimoAccesso) {
                        JOptionPane.showMessageDialog(null,
                                "Benvenuto " + cliente.getNome() + " " + cliente.getCognome());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Bentornato " + cliente.getNome() + " " + cliente.getCognome());
                    }

                    UninaGymHome uninaGymHome = new UninaGymHome(frame);
                    uninaGymHome.setCliente(cliente);
                    uninaGymHome.setCorsiDaLista(controllerAccesso.leggiCorsi());
                    uninaGymHome.caricaCampiCliente();
                    uninaGymHome.caricaCorsiSeguiti();
                    uninaGymHome.caricaTabellaCorsi(controllerAccesso.leggiCorsiEIstruttore());
                    uninaGymHome.caricaTabellaAccessi(controllerAccesso.leggiAccessiCliente(cliente.getCodiceFiscale()));

                    frame.setVisible(false);

                    campoNome.setText("");
                    campoCognome.setText("");
                    campoPassword.setText("");
                    gruppoAbbonamento.clearSelection();

                }
                catch (CampoNonTrovatoException ect) {
                    JOptionPane.showMessageDialog(null, ect.getMessage());
                }
                catch (ClienteNonEsistenteException ecn){
                    JOptionPane.showMessageDialog(null, ecn.getMessage());
                }
                catch (RuntimeException er) {
                    JOptionPane.showMessageDialog(null, er.getMessage());
                }
            }
        });

        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Grazie per aver usato il nostro servizio");
                frame.dispose();
            }
        });

        registrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UninaGymRegistrazione registrazione = new UninaGymRegistrazione(frame, controllerAccesso);
                frame.setVisible(false);
            }
        });
    }

    /**
     * Avvia l'applicazione e mostra la schermata principale di accesso.
     *
     * @param args argomenti della riga di comando
     */
    public static void main(String[] args) {
        ControllerAccesso controllerAccesso = new ControllerAccesso();

        frame = new JFrame("UninaGymMain");
        frame.setContentPane(new UninaGymMain(frame, controllerAccesso).mainPanelAccesso);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}