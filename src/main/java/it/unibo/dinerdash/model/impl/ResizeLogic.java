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

    public void updateCustomDest(LinkedList<Table> tables){     //aggiorno la destinazione nel caso il cliente stesse andando al tavolo
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

}
