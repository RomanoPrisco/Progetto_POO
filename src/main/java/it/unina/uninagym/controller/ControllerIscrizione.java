package it.unina.uninagym.controller;

import it.unina.uninagym.exception.IscrizioneGiaPresenteException;
import it.unina.uninagym.model.*;

import java.util.ArrayList;
import java.util.Date;

public class ControllerIscrizione{

    private ArrayList<Iscrizione> iscrizioni;

    public ControllerIscrizione(){
        iscrizioni = new ArrayList<>();
    }

    public boolean iscriviCliente(Cliente cliente, Corso corso, int id){
        if (cliente == null || corso == null){
            return false;
        }
        for (Iscrizione i : iscrizioni){
            if (i.getCliente().getCodiceFiscale().equals(cliente.getCodiceFiscale()) && i.getCorso().getId() == (corso.getId())){
                throw new IscrizioneGiaPresenteException("Sei già iscritto a questo corso");
            }
        }

        Iscrizione nuovaIscrizione = new Iscrizione(id, new Date(), StatoIscrizione.ISCRIZIONE_CREATA, cliente, corso);

        iscrizioni.add(nuovaIscrizione);
        return true;
    }
}