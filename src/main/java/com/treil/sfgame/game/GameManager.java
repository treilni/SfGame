package com.treil.sfgame.game;

import com.treil.sfgame.gui.GuiCommand;
import com.treil.sfgame.map.HexCell;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicolas
 * @since 17/10/2017.
 */
public class GameManager implements GuiCommand.Listener {
    private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

    private int totalTurns = 1;
    private int currentTurn = 0;
    private int currentPlayer = -1;
    @Nullable
    private Unit selectedUnit = null;

    @Nonnull
    private final List<Player> players = new ArrayList<>();
    @Nonnull
    private final HexMap map;

    public GameManager(@Nonnull HexMap map) {
        this.map = map;
        final Player player1 = getDefaultPlayer(map);
        players.add(player1);
        nextPlayer();
    }

    @NotNull
    private Player getDefaultPlayer(HexMap map) {
        final Player result = new Player();
        final Ant ant = new Ant();
        final HexCell midMap = map.getCellAt(map.getRowCount() / 2, map.getColCount() / 2);
        assert midMap != null;
        ant.setPosition(midMap);
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
        logger.info(String.format("Turn %d/%d, player %d", currentTurn, totalTurns, currentPlayer));
        final List<Unit> units = players.get(currentPlayer).getUnits();
        setSelectedUnit(units.isEmpty() ? null : units.get(0));
        return false;
    }

    private void setSelectedUnit(@Nullable Unit unit) {
        if (selectedUnit != null) {
            selectedUnit.setSelected(false);
        }
        selectedUnit = unit;
        final Map<HexCell, Integer> reachableCells;
        if (selectedUnit != null) {
            selectedUnit.setSelected(true);
            reachableCells = map.getReachableCells(selectedUnit.getPosition(), selectedUnit.getMovementPoints());
        }
        else {
            reachableCells = new HashMap<>();
        }
        map.setHighlightedCells(reachableCells);
    }

    @Nonnull
    public List<Player> getPlayers() {
        return players;
    }

    @Nullable
    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    @Override
    public void processCommand(@Nonnull GuiCommand command) {
        switch (command) {
            case END_TURN:
                if (nextPlayer()) {
                    endGame();
                }
                break;
            default:
                break;
        }
    }

    private void endGame() {

    }
}
