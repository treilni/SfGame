package com.treil.sfgame.game;

import com.treil.sfgame.controls.Action;
import com.treil.sfgame.gui.GuiCommand;
import com.treil.sfgame.map.HexCell;
import com.treil.sfgame.map.HexDirection;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.map.MapLocation;
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
    @Nonnull
    private Player currentPlayer;
    @Nullable
    private Unit selectedUnit = null;

    @Nonnull
    private final List<Player> players = new ArrayList<>();
    @Nonnull
    private final HexMap map;
    @Nonnull
    private final GameEventListener gameEventListener;
    @Nonnull
    private final MapCellLocator mapCellLocator;

    public GameManager(@Nonnull HexMap map,
                       @Nonnull GameEventListener gameEventListener,
                       @Nonnull MapCellLocator mapCellLocator) {
        this.map = map;
        this.gameEventListener = gameEventListener;
        this.mapCellLocator = mapCellLocator;
        final Player player1 = getDefaultPlayer(map);
        players.add(player1);
        currentPlayer = player1;
        logger.info(String.format("Turn %d/%d, player %s", currentTurn, totalTurns, currentPlayer.getName()));
    }

    @NotNull
    private Player getDefaultPlayer(HexMap map) {
        final Player result = new Player(false, "Black ants");
        createAntAtPosition(map, result, map.getRowCount() / 2, map.getColCount() / 2);
        createAntAtPosition(map, result, map.getRowCount() / 2, map.getColCount() / 2 + 3);
        return result;
    }

    private void createAntAtPosition(HexMap map, Player player, int row, int column) {
        final HexCell cell = map.getCellAt(row, column);
        if (cell != null) {
            final Ant ant = new Ant();
            moveUnitToPosition(ant, cell, 0);
            player.addUnit(ant);
        } else {
            logger.error("Null destination cell for ant creation : " + new MapLocation(row, column));
        }
    }

    /**
     * @return true if end of game
     */
    private boolean nextPlayer() {
        int i = players.indexOf(currentPlayer);
        if (i >= 0) {
            i = (i + 1) % players.size();
            if (i == 0) {
                if (currentTurn >= totalTurns) {
                    return true;
                }
                currentTurn++;
            }
        } else {
            i = 0;
        }
        currentPlayer = players.get(i);
        logger.info(String.format("Turn %d/%d, player %s", currentTurn, totalTurns, currentPlayer.getName()));
        final List<Unit> units = currentPlayer.getUnits();
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
        } else {
            reachableCells = new HashMap<>();
        }
        map.setHighlightedCells(reachableCells);
        gameEventListener.onUnitUpdate(unit);
    }

    @Nonnull
    public List<Player> getPlayers() {
        return players;
    }

    @Nullable
    private Unit getSelectedUnit() {
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

    public void processAction(@Nonnull Action action) {
        final Unit unit = getSelectedUnit();
        HexDirection direction = null;
        switch (action) {
            case LEFT:
                direction = HexDirection.WEST;
                break;
            case RIGHT:
                direction = HexDirection.EAST;
                break;
            case UP:
                direction = HexDirection.NORTH_EAST;
                break;
            case DOWN:
                direction = HexDirection.SOUTH_EAST;
                break;
            case RIGHT_CLICK:
                processRightClick();
                return;
            case LEFT_CLICK:
                processLeftClick();
                return;
            case CYCLE:
                processCycleThroughUnits();
                return;
            case FORWARD:
            case BACKWARD:
            case NONE:
                break;
        }
        if (direction != null && unit != null) {
            final HexCell newPosition = map.getSibling(unit.getPosition(), direction);
            if (newPosition != null) {
                logger.debug("New position : " + newPosition.getLocation());
                moveUnitToPosition(unit, newPosition, newPosition.getTerrain().getMovementCost());
            }
        }
    }

    private void processCycleThroughUnits() {
        final List<Unit> units = currentPlayer.getUnits();
        int i = units.indexOf(selectedUnit);
        if (i < 0) {
            i = 0;
        } else {
            i = (i + 1) % units.size();
        }
        final Unit unit = units.get(i);
        if (unit != selectedUnit) {
            setSelectedUnit(unit);
        }
    }

    private void processRightClick() {
        final MapLocation clickedLocation = mapCellLocator.getLocationUnderCursor();
        final HexCell clickedCell = clickedLocation != null ? map.getCellAt(clickedLocation) : null;
        if (clickedCell == null) {
            return;
        }
        // Moving the selected unit
        final Unit selectedUnit = getSelectedUnit();
        if (selectedUnit != null) {
            final Map<HexCell, Integer> reachableCells = map.getReachableCells(selectedUnit.getPosition(), selectedUnit.getMovementPoints());
            final Integer cost = reachableCells.get(clickedCell);
            if (cost != null) {
                // cell is reachable
                boolean movementDone = moveUnitToPosition(selectedUnit, clickedCell, cost);
                logger.info("Movement to " + clickedCell.getLocation() + " : " + movementDone);
            }
        }
    }

    private void processLeftClick() {
        final MapLocation clickedLocation = mapCellLocator.getLocationUnderCursor();
        final HexCell clickedCell = clickedLocation != null ? map.getCellAt(clickedLocation) : null;
        if (clickedCell == null) {
            return;
        }
        Unit unit = getUnitAtCell(clickedCell);

        // Moving the selected unit
        final Unit selectedUnit = getSelectedUnit();
        if (unit != null && unit != selectedUnit) {
            setSelectedUnit(unit);
        }
    }

    @Nullable
    private Unit getUnitAtCell(@Nonnull HexCell clickedCell) {
        final List<Unit> units = currentPlayer.getUnits();
        for (Unit unit : units) {
            if (unit.getPosition() == clickedCell) {
                return unit;
            }
        }
        return null;
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean moveUnitToPosition(@Nonnull Unit unit, @Nonnull HexCell newPosition, int movementCost) {
        final int usablePoints = unit.getMovementPoints();
        if (usablePoints < movementCost) {
            return false;
        }
        unit.setMovementPoints(usablePoints - movementCost);
        unit.setPosition(newPosition);
        if (unit.isSelected()) {
            setSelectedUnit(unit);
        }
        gameEventListener.onUnitUpdate(unit);
        return true;
    }

}
