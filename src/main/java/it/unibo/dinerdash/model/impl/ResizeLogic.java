package it.unibo.dinerdash.model.impl;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.utility.Pair;

public class ResizeLogic {
    int altezzaCl;
    int larghezzaCl;
	double percentualeAltezza;
    double percentualeLargezza;
    double AlSchermo=1002;
    double LargSchermo=1888;
    private static final int SPACE_BETWEEN_LINE_PEOPLE = 25;
    private boolean flag=true;
    public ResizeLogic() {

    }

    public void updateCustomDest(LinkedList<Table> tables){     //aggiorno la destinazione nel caso il cliente stesse andando al tavolo , andrebbe aggiornata anche x e y
        tables.forEach(t->{
            //if(!t.getCustom().equals(Optional.empty())){
               // t.getCustom().setDestination(Optional.of(t.getPosition()));  
           // }
        });
    }

    public void updateCustomLinePos(LinkedList<Customer> customers,Pair<Integer,Integer> firstPosition){ 
        LinkedList<Customer> novaLista=customers.stream().filter(c->c.getState().equals(CustomerState.LINE)).collect(Collectors.toCollection(LinkedList::new));
        novaLista.stream().forEachOrdered(cl->{
            if(flag) {
                cl.setPosition(firstPosition);
                this.flag=false;
            }
            else {
                int pos=novaLista.indexOf(cl);
                novaLista.get(pos).setPosition(new Pair<Integer,Integer>(firstPosition.getX(),firstPosition.getY()-(pos*SPACE_BETWEEN_LINE_PEOPLE) ));
            }
        });
    }

    public Pair<Integer,Integer> updateFirstPos(Pair<Integer,Integer> firstPosition,int altezzaNuova,int larghezzaNuova){     //aggiorno la prima posizione in fila
       double percentualeVarY=((AlSchermo-altezzaNuova)/AlSchermo)*100;              //percentuale cambiamento da valore a ad b
       double percentualeVarX=((LargSchermo-larghezzaNuova)/LargSchermo)*100;       
       altezzaCl=(int)((firstPosition.getY()/100)*percentualeVarY);                         //valore  da sottrarre all altezza
       larghezzaCl=(int)((firstPosition.getX()/100)*percentualeVarX);    //valore percentuale da sottrarre all largezza
       return new Pair<>(firstPosition.getX()-larghezzaCl, firstPosition.getY()-altezzaCl);
    }
}
