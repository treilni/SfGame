package com.treil.sfgame.game;

import com.treil.sfgame.Application;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.player.Player;
import com.treil.sfgame.units.Ant;
import com.treil.sfgame.units.Unit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas
 * @since 17/10/2017.
 */
public class GameManager {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private int totalTurns = 1;
    private int currentTurn = 0;
    private int currentPlayer = -1;
    @Nullable
    private Unit selectedUnit = null;

    @Nonnull
    private final List<Player> players = new ArrayList<>();


    public GameManager(HexMap map) {
        final Player player1 = getDefaultPlayer(map);
        players.add(player1);
        setSelectedUnit(player1.getUnits().get(0));
    }

    @NotNull
    private Player getDefaultPlayer(HexMap map) {
        final Player result = new Player();
        final Ant ant = new Ant();
        ant.setPosition(map.getCellAt(5, 10));
        result.addUnit(ant);
        return result;
    }

    /**
     * @return true if end of game
     */
    private boolean nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
        if (currentPlayer == 0) {
            if (currentTurn >= totalTurns) {
                return true;
            }
            currentTurn++;
        }
        final List<Unit> units = players.get(currentPlayer).getUnits();
        setSelectedUnit(units.isEmpty() ? null : units.get(0));
        return false;
    }

    private void setSelectedUnit(@Nullable Unit unit) {
        if (selectedUnit != null) {
            selectedUnit.setSelected(false);
        }
        selectedUnit = unit;
        if (selectedUnit != null) {
            selectedUnit.setSelected(true);
        }
    }

    @Nonnull
    public List<Player> getPlayers() {
        return players;
    }

    @Nullable
    public Unit getSelectedUnit() {
        return selectedUnit;
    }
}
