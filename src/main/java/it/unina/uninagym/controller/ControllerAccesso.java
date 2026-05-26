package it.unina.uninagym.controller;
import it.unina.uninagym.exception.CampoNonTrovatoException;
import it.unina.uninagym.exception.ClienteNonEsistenteException;
import it.unina.uninagym.model.Cliente;
import it.unina.uninagym.model.TipoAbbonamento;

import java.util.ArrayList;

public class ControllerAccesso{

    private ArrayList<Cliente> clienti;
    private ArrayList<Cliente> accessi;

    public ControllerAccesso(){
        clienti = new ArrayList<>();
        accessi = new ArrayList<>();
    }

    public void registraCliente(String nome, String cognome, String password, TipoAbbonamento tipoAbbonamento){
        if(nome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Nome\" non può essere vuoto");
        }
        if(cognome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Cognome\" non può essere vuoto");
        }
        if(password.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Password\" non può essere vuoto");
        }
        if(tipoAbbonamento == null){
            throw new CampoNonTrovatoException("Non è stato selezionato nessun abbonamento");
        }

        Cliente nuovoCliente = new Cliente(password, nome, cognome);
        clienti.add(nuovoCliente);
    }
    //boolean per controllare se l'utente che viene aggiunto ha già fatto l'accesso in precedenza
    public boolean aggiungiCliente(String nome, String cognome, String password, TipoAbbonamento tipoAbbonamento){
        //Controllo per vedere se i campi sono vuoti
        if(nome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Nome\" non può essere vuoto");
        }
        if(cognome.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Cognome\" non può essere vuoto");
        }
        if(password.isBlank()){
            throw new CampoNonTrovatoException("Il campo \"Password\" non può essere vuoto");
        }
        if(tipoAbbonamento == null){
            throw new CampoNonTrovatoException("Non è stato selezionato nessun abbonamento");
        }


        //Controlla se il cliente che prova a fare l'accesso esiste esiste, verrà implementato quando collegheremo il database
        /*boolean clienteEsistente = false;

        for(Cliente c : clienti){
            if(c.getNome().equals(nome) &&
               c.getCognome().equals(cognome) &&
               c.getPassword().equals(password)){
                    clienteEsistente = true;
                    break;
            }
        }

        if(!clienteEsistente){
            throw new ClienteNonEsistenteException("Questo cliente non esiste");
        }*/

        for(Cliente c : accessi){
            //Controllo per vedere se l'accesso è uguale a un accesso già fatto dallo stesso cliente, il controllo sul tipoAbbonamento verrà aggiunto quando implementeremo dati persistenti dal database
            if(c.getPassword().equals(password) && c.getNome().equals(nome) && c.getCognome().equals(cognome) /* && c.getAbbonamento().getTipo().equals(tipoAbbonamento)*/){
                return false;
            }
        }



        Cliente nuovoAccesso = new Cliente(password, nome, cognome);
        accessi.add(nuovoAccesso);

        return true;
    }
}
