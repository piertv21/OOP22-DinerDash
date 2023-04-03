package it.unibo.dinerdash.controller.impl;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import it.unibo.dinerdash.controller.api.Controller;
import it.unibo.dinerdash.model.api.CustomerState;
import it.unibo.dinerdash.model.impl.Customer;
import it.unibo.dinerdash.model.impl.ModelImpl;
import it.unibo.dinerdash.utility.Pair;
import it.unibo.dinerdash.view.api.GameEntityViewable;
import it.unibo.dinerdash.view.api.View;
import it.unibo.dinerdash.view.impl.GameView;
import it.unibo.dinerdash.view.impl.TableViewable;

public class ControllerImpl implements Controller {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "dinerdash" + SEP;

    private ModelImpl model;
    private View view;
    private GameView gamePanel;
    Timer spawnTime = new Timer();                             //timer to make spawn customers
    
    public ControllerImpl() {
        this.model = new ModelImpl();
        System.out.println("creo model");
    }
    
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void start() {
        this.gamePanel = this.view.getGamePanel();
        //TODO
        this.model.start();
        this.startSpawnTimer();  //starts customers spawn
    }

    @Override
    public void restart() {        
        this.model.restart();
        this.gamePanel.init();
    }

    @Override
    public void quit() {
        this.view.quit();
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
    public void addCustomer() {
        this.model.addCustomer();
        this.gamePanel.getViewableCustomersList().add(new GameEntityViewable(null, null));
        this.gamePanel.addCustomerViewable(model.getRandomNumber());  
    }

    @Override
    public void setFrameSize(Dimension dimension) {
        this.model.setRestaurantSize(dimension);
    }

    @Override
    public void resizeEntities() {
        //TODO        QUI CHIAMO I METODI DELLA CLASSE RESIZE
    }

    private void init() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    private void startSpawnTimer() {
        spawnTime.schedule(custumCreation_Trd, 2000, 6000);                                //avvio la creazione programmata  dei clienti
    }

    TimerTask custumCreation_Trd = new TimerTask() {
        @Override
        public void run() { 
          // addCustomer();          // thread che ogni 6 secondi chiama il metodo per creare un cliente  
        }
    };

    public LinkedList<Customer> getSittedCustomList(){             
        return this.model.getCustomers();
    }

    // https://stackoverflow.com/questions/49871233/using-imageicon-to-access-a-picture-cant-access-it-how-to-fix
    private ImageIcon loadIcon(String iconName) throws IOException {
        final URL imgURL = ClassLoader.getSystemResource(ROOT + iconName);
        return new ImageIcon(imgURL);
    }
        
    @Override
    public HashMap<TableViewable, Optional<GameEntityViewable>> getTables() {
     /* 
        //TODO Modifica e migliora
        var tables = this.model.getTables();                                                        TOGLIERE
        HashMap<TableViewable, Optional<GameEntityViewable>> out = new HashMap<>();

        for(var e : tables.entrySet()) {
            try {
                var tableImage = loadIcon("table" + e.getKey().getPeopleSeatedNumber() + ".png").getImage();
                var tableViewablePosition = new Pair<>(e.getKey().getPosition().getX(), e.getKey().getPosition().getY());
                var tableViewable = new TableViewable(tableViewablePosition, tableImage);

                var customerViewableImage = loadIcon("client" + e.getValue().get().getCustomerMultiplicity() + ".png").getImage();
                var customerViewablePosition = new Pair<>(e.getValue().get().getPosition().getX(), e.getValue().get().getPosition().getY());
                var customerViewable = new GameEntityViewable(customerViewablePosition, customerViewableImage);

                out.put(tableViewable, Optional.of(customerViewable));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return out;*/   return null;                
    }

    private void updateListPosition() {         //aggiorno le posizioni dei clienti nella lista della view  SARA DA CHIAMARE OGNI VOLTA PRIMA DI STAMPARE LE IMMAGINI
        int p=0;
        for(var cus: model.getCustomers()) {
                this.gamePanel.getViewableCustomersList().get(p).update(cus);
                if(cus.getState().equals(CustomerState.THINKING)) {
                    //int tableNmb= this.getHashMapkey(model.getTables(), cus).getTableNumber();
                    //this.gamePanel.getTablesList().get(tableNmb-1).setImg(cus.getCustomerMultiplicity());
                }
                p++;
                // BISOGNA CHIAMARE UN METODO NELLA VIEW CHE CAMBIA L'IMMAGINE DEL TAVOLO ,
                // AGGIUNGI METODO CHE AGGIORNA POSIZIONI TAVOLI ,E CONTROLLO SE IL CLIENTE è IN THINKING ,POI SE IL NUMERO DI CLIENTI SEDUTI ED LA MOLTEPLIC DEL CLIENTE
                // ALLEGATO SONO UGUALI,  SE NON LO SONO CAMBIA L'IMMAGINE DEL TAVOLO
        }
    }

}
