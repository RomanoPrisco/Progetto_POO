package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerIscrizione;
import it.unina.uninagym.exception.IscrizioneGiaPresenteException;
import it.unina.uninagym.exception.NessunCorsoSelezionatoException;
import it.unina.uninagym.model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UninaGymHome {
    private JPanel mainHomePanel;
    private JTabbedPane tabbedPanelHome;
    private JPanel panelProfilo;
    private JPanel panelCorsi;
    private JPanel panelAccessi;
    private JPanel panelHome;
    private JTextField campoNomeHome;
    private JTextField campoCognomeHome;
    private JTextField campoCodFiscaleHome;
    private JTextField campoScadAbbonamentoHome;
    private JTextArea campoCorsiSeguiti;
    private JButton esciButton;
    private JTextField campoTipoAbbonamentoHome;
    private JTextField campoDataIscrizione;
    private JTextField campoTipoAbbonamentoProfilo;
    private JTextField campoScadAbbonamentoProfilo;
    private JTextField campoPrezzoMensile;
    private JTextField campoNomeProfilo;
    private JTextField campoCognomeProfilo;
    private JTextField campoCodFiscaleProfilo;
    private JTextField campoDataNascita;
    private JTextField campoEmail;
    private JTextField campoNumTelefono;
    private JCheckBox yogaCheckBox;
    private JCheckBox pilatesCheckBox;
    private JCheckBox zumbaCheckBox;
    private JCheckBox crossfitCheckBox;
    private JCheckBox pesiCheckBox;
    private JCheckBox salapesiCheckBox;
    private JButton iscrivitiButton;
    private JTable tableCorsi;
    private JTable tableAccessi;

    public JFrame frame;
    private ControllerIscrizione controllerIscrizione = new ControllerIscrizione();
    private Cliente cliente;
    //Usiamo array per verificare se le checkbox sono state selezionate oppure se un cliente è già iscritto a un corso
    private JCheckBox[] cb = new JCheckBox[6];
    private Corso[] corsi = new Corso[6];
    private Istruttore[] istruttori = new Istruttore[6];
    //Formattazione della data in giorno/mese/anno
    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    //Formattazione della data in giorno della settimana
    SimpleDateFormat formatoGiorno = new SimpleDateFormat("EEEE");

    //ArrayList per controllare e aggiungere gli accessi nella sezione ACCESSI
    ArrayList<Accesso> accessi = new ArrayList<>();

    public UninaGymHome(JFrame frameChiamante){

        frame = new JFrame("UninaGymHome");
        frame.setContentPane(mainHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        cb[0] = yogaCheckBox;
        cb[1] = pilatesCheckBox;
        cb[2] = zumbaCheckBox;
        cb[3] = crossfitCheckBox;
        cb[4] = pesiCheckBox;
        cb[5] = salapesiCheckBox;

        /*Tutti i campi verranno aggiunti una volta fatto il collegamento con il database

        //Inserimento dei campi nei TextField della Home
        campoNomeHome.setText(cliente.getNome());
        campoCognomeHome.setText(cliente.getCognome());
        campoCodFiscaleHome.setText(cliente.getCodiceFiscale());
        campoDataIscrizione.setText(formatoData.format(cliente.getDataIscrizione()));
        //toString usato per l'Enumeration, perchè è già rappresentato come testo
        campoTipoAbbonamentoHome.setText(cliente.getAbbonamento().getTipo().toString());
        campoScadAbbonamentoHome.setText(formatoData.format(cliente.getAbbonamento().getDataFine()));

        //Inserimento dei campi nei TextField della Home
        //Generalità
        campoNomeProfilo.setText(cliente.getNome());
        campoCognomeProfilo.setText(cliente.getCognome());
        campoCodFiscaleProfilo.setText(cliente.getCodiceFiscale());
        campoDataNascita.setText(formatoData.format(cliente.getDataNascita()));
        campoEmail.setText(cliente.getEmail());
        campoNumTelefono.setText(cliente.getNumeroTelefonico());

        //Dati Iscrizione
        campoDataIscrizione.setText(formatoData.format(cliente.getDataIscrizione()));
        campoTipoAbbonamentoProfilo.setText(cliente.getAbbonamento().getTipo().toString());
        campoScadAbbonamentoProfilo.setText(formatoData.format(cliente.getAbbonamento().getDataFine()));
        //String.valueOf serve a convertire il valore double in stringa
        campoPrezzoMensile.setText(String.valueOf(cliente.getAbbonamento().getCostoMensile()));

        //Inizializzazione della JTableCorsi
        //Inserimento colonne
        String colonneCorsi[] = {"NOME CORSO", "ID CORSO", "ISTRUTTORE", "SPECIALIZZAZIONE", "CAPIENZA MASSIMA", "ORA D'INIZIO", "GIORNO"};
        DefaultTableModel tabellaCorsi = new DefaultTableModel(colonneCorsi, 0) {
            @Override
            //Rende la tabella non editabile, è un metodo del DefaultModelTable
            public boolean isCellEditable(int righe, int colonne) {
                return false;
                }
            };

        //Inserimento righe
        for(int i = 0; i < 6; i++){
            Corso corso = corsi[i];
            Istruttore istruttore = istruttori[i];
            tabellaCorsi.addRow(new Object[]{corso.getNome(),
            corso.getId(),
            istruttore.getNome() + " " + istruttore.getCognome(),
            istruttore.getSpecializzazione(),
            corso.getCapienzaMassima(),
            corso.getOraInizio(),
            formatoGiorno.format(corso.getGiorno())
            });
        }

        //Inserimento della JTableAccessi
        String colonneAccessi[] = {"ID", "DATA ACCESSO","STATO ACCESSO"};
        DefaultTableModel tabellaAccesso = new DefaultTableModel(colonneAccessi, 0){
            @Override
            //Rende la tabella non editabile, è un metodo del DefaultModelTable
            public boolean isCellEditable(int righe, int colonne) {
                return false;
            }
        };

        for(int i = 0; i < accessi.size(); i++){
            Accesso accesso = accessi.get(i);
            tabellaAccesso.addRow(new Object[]{
                accesso.getId(),
                accesso.getDataInizio(),
                accesso.getTipo()
            });
        }
        */

        esciButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });


        iscrivitiButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    boolean almenoUnoSelezionato = false;
                    //Per inserire i corsi nella JTextArea nella sezione Home
                    StringBuilder corsiDaAggiungere = new StringBuilder();
                    for (int i = 0; i < cb.length; i++){
                        if (cb[i].isSelected()){
                            almenoUnoSelezionato = true;
                            //i+1 perchè l'Id dei corsi parte da 1 e non da 0
                            controllerIscrizione.iscriviCliente(cliente, corsi[i], i + 1);
                            //Aggiunge il testo da far vedere nella JTextArea
                            corsiDaAggiungere.append(cb[i].getText()).append("\n");
                        }
                    }

                    if (!almenoUnoSelezionato){
                        throw new NessunCorsoSelezionatoException("Seleziona almeno un corso");
                    }

                    //Scrive i corsi selezionati con le CheckBox nel JTextArea
                    campoCorsiSeguiti.setText(corsiDaAggiungere.toString());

                    JOptionPane.showMessageDialog(null, "Iscrizione completata");

                    for(int i = 0; i < cb.length; i++){
                        cb[i].setSelected(false);
                    }
                }
                catch (NessunCorsoSelezionatoException en){
                    JOptionPane.showMessageDialog(null, en.getMessage());
                }
                catch (IscrizioneGiaPresenteException ei){
                    JOptionPane.showMessageDialog(null, ei.getMessage());
                }
            }
        });
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setCorsi(Corso[] corsi){
        this.corsi = corsi;
    }
}