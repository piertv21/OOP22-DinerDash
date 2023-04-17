package it.unibo.dinerdash.model.api;

import java.util.List;
import java.util.Optional;

import it.unibo.dinerdash.model.api.States.GameState;
import it.unibo.dinerdash.model.api.States.TableState;
import it.unibo.dinerdash.model.impl.CountertopImpl;
import it.unibo.dinerdash.model.impl.CustomerImpl;
import it.unibo.dinerdash.model.impl.DishImpl;
import it.unibo.dinerdash.model.api.GameEntities.Table;
import it.unibo.dinerdash.model.api.GameEntities.Waitress;
import it.unibo.dinerdash.utility.impl.Pair;

public interface Model {

    int getWidth();

    int getHeight();

    void start();

    void pause();

    void stop();

    void sendOrder(int tableNumber);

    boolean gameOver();

    void restart();

    void quit();

    void addCustomer();

    void customerLeft();

    CountertopImpl getCounterTop();

    void update(long elapsedUpdateTime);

    GameState getGameState();

    void setGameState(GameState gameState);

    void decrementRemainingTime();

    int getCoins();

    int getRemainingTime();

    List<CustomerImpl> getCustomers();

    void setWaitressTableDestination(Pair<Integer, Integer> destination);

    boolean checkFreeTables(CustomerImpl cus);

    void linePositionAssignament(CustomerImpl cus);

    void tableAssignament(CustomerImpl cus);

    void setTableCustomers(int customersMolteplicity, int numberTable);

    void removeAngryCustomers();

    boolean thereAreAvaibleTables();

    Table getTablefromPositon(Pair<Integer, Integer> pos);

    void setTableState(TableState state, int numberTable);

    List<Table> getTable();

    Waitress getWaitress();

    void setWaiterssInfo(int indexL, String s, Pair<Integer, Integer> pos);

    void setCoins(int val);

    void setNeedUpdate(boolean b);

    boolean getNeedUpdate();

    boolean thereAreDishesToPrepare();

    Optional<DishImpl> getDishToPrepare();

    void completeDishPreparation(DishImpl dish);

    void earnMoneyFromTable();

}
