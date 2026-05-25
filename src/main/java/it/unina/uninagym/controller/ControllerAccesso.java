package it.unina.uninagym.controller;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.TipoAbbonamento;

import java.util.ArrayList;

public class ControllerAccesso{

    private ArrayList<Cliente> clienti;


    public ControllerAccesso(){
        clienti = new ArrayList<>();
    }

    //boolean per controllare se l'utente che viene aggiunto ha già fatto l'accesso in precedenza
    public boolean aggiungiCliente(String nome, String cognome, String codiceFiscale, TipoAbbonamento tipoAbbonamento){
        //Controllo per vedere se i campi sono vuoti
        if (nome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Nome\" non può essere vuoto");
        }
        if (cognome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Cognome\" non può essere vuoto");
        }
        if (codiceFiscale.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Codice fiscale\" non può essere vuoto");
        }
        if(tipoAbbonamento == null){
            throw new CampoNonTrovatoException("Non è stato selezionato nessun abbonamento");
        }

        for(Cliente c : clienti){
            //Controllo per vedere se l'accesso è uguale a un accesso già fatto dallo stesso cliente, il controllo sul tipoAbbonamento verrà aggiunto quando implementeremo dati persistenti dal database
            if(c.getCodiceFiscale().equals(codiceFiscale) && c.getNome().equals(nome) && c.getCognome().equals(cognome) /* && c.getAbbonamento().getTipo().equals(tipoAbbonamento)*/){
                return false;
            }
        }

        Cliente nuovoAccesso = new Cliente(codiceFiscale, nome, cognome);
        clienti.add(nuovoAccesso);

        return true;
    }
}
