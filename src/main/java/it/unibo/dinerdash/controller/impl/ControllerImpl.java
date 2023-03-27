package it.unibo.dinerdash.controller.impl;

import java.io.IOException;
import java.util.Random;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.impl.Model;
import it.unibo.dinerdash.view.impl.View;

public class ControllerImpl implements Controller {

    private Model model;
    private View view;
    private Random rand=new Random();                   // used to create customers 
    
    public ControllerImpl() {
        this.model = new Model();
    }
    
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void start() {
        //TO-DO
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public void restart() {        
        this.model.restart();
    }

    @Override
    public void quit() {
        this.view.closeWindow();

        // TO-DO: Remove entity images, eg. image1.flush();

        System.exit(0);
    }

    @Override
    public int getCoins() {
        return this.model.getCoins();
    }

    @Override
    public int getRemainingTime() {
        return this.model.getRemainingTime();
    }

    @Override
    public boolean gameOver() {
        return this.model.gameOver();
    }

    @Override
    public void addCustomerInLine() {
        this.model.addCustomerInLine();      
    }
    public void addCustumers(){                                                                //aggiungo un cliente che entra nel ristorante
        /*try {
            if(this.emptyTables!=0){
                
                AssegnoTavolo();
            }else{
                
                AssegnoPostoFila();
            }
         } catch (IOException e) {     
             e.printStackTrace();
         }*/
    }

}
