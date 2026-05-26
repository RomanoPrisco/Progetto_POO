package it.unina.uninagym.gui;

import it.unina.uninagym.controller.ControllerAccesso;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.exception.ClienteNonEsistenteException;
import it.unina.uninagym.model.TipoAbbonamento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UninaGymRegistrazione {
    private JPanel mainRegistrazionePanel;
    private JTextField campoNome;
    private JTextField campoCognome;
    private JPasswordField campoPassword;
    private JButton confermaButton;
    private JRadioButton radioGold;
    private JRadioButton radioNormal;
    private JButton esciButton;
    private JButton occhioButton;

    private static JFrame frame;
    private ControllerAccesso controllerAccesso = new ControllerAccesso();
    private ButtonGroup gruppoAbbonamento;


    public UninaGymRegistrazione(ButtonGroup gruppoAbbonamento, ControllerAccesso controllerAccesso) {
        this.gruppoAbbonamento = gruppoAbbonamento;
        this.controllerAccesso = controllerAccesso;
    }

    public UninaGymRegistrazione(JFrame frameChiamante) {

        frame = new JFrame("UninaGymRegistrazione");
        frame.setContentPane(mainRegistrazionePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        gruppoAbbonamento = new ButtonGroup();
        gruppoAbbonamento.add(radioGold);
        gruppoAbbonamento.add(radioNormal);

        ImageIcon iconaOcchioAperto = new ImageIcon(getClass().getResource("/Icone/Occhio-aperto.png"));
        ImageIcon iconaOcchioChiuso = new ImageIcon(getClass().getResource("/Icone/Occhio-chiuso.png"));

        //Mette nella variabile password il testo (nascosto) della password
        char password = campoPassword.getEchoChar();

        occhioButton.setIcon(iconaOcchioChiuso);
        occhioButton.setBorderPainted(false);
        occhioButton.setContentAreaFilled(false);
        occhioButton.setFocusPainted(false);

        occhioButton.addActionListener(new ActionListener(){
            private boolean passwordSiVede = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordSiVede){
                    //Mostra i caratteri della password e cambia icona del bottone
                    campoPassword.setEchoChar(password);
                    occhioButton.setIcon(iconaOcchioChiuso);
                    passwordSiVede = false;
                }
                else{
                    //Nasconde i caratteri della password e cambia icona del bottone
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
                    String nome = campoNome.getText();
                    String cognome = campoCognome.getText();
                    String password = new String(campoPassword.getPassword());
                    TipoAbbonamento tipoAbbonamento = null;

                    if (radioGold.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.GOLD;
                    } else if (radioNormal.isSelected()) {
                        tipoAbbonamento = TipoAbbonamento.NORMAL;
                    }

                    controllerAccesso.registraCliente(nome, cognome, password, tipoAbbonamento);
                    JOptionPane.showMessageDialog(null, "Registrazione avvenuta correttamente");

                    frame.dispose();

                    campoNome.setText("");
                    campoCognome.setText("");
                    campoPassword.setText("");
                    gruppoAbbonamento.clearSelection();

                    frameChiamante.setVisible(true);
                }
                catch(CampoNonTrovatoException ec){
                    JOptionPane.showMessageDialog(null, ec.getMessage());
                }
                catch(ClienteNonEsistenteException en){
                    JOptionPane.showMessageDialog(null, en.getMessage());
                }
            }
        });

        esciButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }
}
