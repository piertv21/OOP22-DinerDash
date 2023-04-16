package it.unibo.dinerdash.view.api;

import java.awt.Image;
import java.util.Optional;

import javax.swing.JLabel;

import it.unibo.dinerdash.model.api.GameEntity;
import it.unibo.dinerdash.utility.impl.Pair;

/*
 * GameEntity viewable with State Label for GUI representation (Customers - Tables)
 */
public class GameEntityViewableWithLabel extends GameEntityViewable {

    private GameView gameView;
	private Optional<JLabel> state;
	private boolean stateVisibility;
	private int multiplicity;

	public GameEntityViewableWithLabel(Pair<Integer, Integer> coordinates, Pair<Integer, Integer> size, Image icon, GameView gameView, int multiplicity) {
		super(coordinates, size, icon);
        this.gameView = gameView;
        this.multiplicity = multiplicity;
        this.state = Optional.empty();
		this.showState(false);
	}

	private void showState(boolean showState) { //private, lo usa la classe
		this.stateVisibility = showState;
	}

    public boolean getStateStatus() {
		return this.stateVisibility;
	}

    public void update(GameEntity gameEntity, int multiplicity) {
        this.update(gameEntity); //metodo madre

        // params aggiuntivi (state + multiplicity)
        if(this.multiplicity != multiplicity) {
            this.gameView.assignNewImage(this, multiplicity);
            this.multiplicity = multiplicity;
        }

        //TODO Aggiungi aggiornamento state

        // Se dopo update c'è uno state viene mostrato altrimenti no
        this.showState(this.state.isPresent() ? true : false);
    }
    
}
