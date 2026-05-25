package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerAccesso;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.Corso;
import it.unina.uninagym.model.TipoAbbonamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UninaGymMain {
    private JPanel mainPanelAccesso;
    private JButton accediButton;
    private JButton esciButton;
    private JTextField campoNome;
    private JTextField campoCognome;
    private JTextField campoCodFiscale;
    private JRadioButton radioNORMAL;
    private JRadioButton radioGOLD;

    private static JFrame frame;
    private ControllerAccesso controllerAccesso = new ControllerAccesso();
    //ButtonGroup per gestire il tipo di abbonamento con i radioButton
    private ButtonGroup gruppoAbbonamento;

    public UninaGymMain(ButtonGroup gruppoAbbonamento, ControllerAccesso controllerAccesso){
        this.gruppoAbbonamento = gruppoAbbonamento;
        this.controllerAccesso = controllerAccesso;
    }

    public UninaGymMain(){
        gruppoAbbonamento = new ButtonGroup();
        gruppoAbbonamento.add(radioGOLD);
        gruppoAbbonamento.add(radioNORMAL);

        accediButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                try {
                    String nome = campoNome.getText();
                    String cognome = campoCognome.getText();
                    String codiceFiscale = campoCodFiscale.getText();
                    TipoAbbonamento tipoAbbonamento = null;
                    //Controlla quale tipo di Abbonamento viene selezionato
                    if (radioGOLD.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.GOLD;
                    } else if (radioNORMAL.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.NORMAL;
                    }
                    //Aggiungi il cliente alla lista del Controller
                    boolean isUtenteNuovo = controllerAccesso.aggiungiCliente(nome, cognome, codiceFiscale, tipoAbbonamento);

                    if(isUtenteNuovo){
                        JOptionPane.showMessageDialog(null, "Benvenuto " + nome + " " + cognome);
                    }
                    //Per ora dà errore perchè, non avendo il database da cui prende i dati, l'Abbonamento è null
                    else{
                        JOptionPane.showMessageDialog(null, "Bentornato " + nome + " " + cognome);
                    }

                    Cliente cliente = null;
                    Corso[] corsi = new Corso[6];

                    UninaGymHome uninaGymHome = new UninaGymHome(frame);
                    frame.setVisible(false);
                    campoNome.setText("");
                    campoCognome.setText("");
                    campoCodFiscale.setText("");
                    gruppoAbbonamento.clearSelection();

                    uninaGymHome.setCliente(cliente);
                    uninaGymHome.setCorsi(corsi);
                }
                catch (CampoNonTrovatoException ec){
                    JOptionPane.showMessageDialog(null, ec.getMessage());
                }
            }
        });

        esciButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Grazie per aver usato il nostro servizio");
                frame.dispose();
            }
        });
    }

    public static void main(String[] args){
        frame = new JFrame("UninaGymMain");
        frame.setContentPane(new UninaGymMain().mainPanelAccesso);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
