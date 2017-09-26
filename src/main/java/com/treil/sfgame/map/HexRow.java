package com.treil.sfgame.map;

import java.util.ArrayList;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexRow extends ArrayList<HexCell> {
    public HexRow(int rowNum, int columns) {
        for (int c = 0; c < columns; c++) {
            add(new HexCell(rowNum, c));
        }
    }
}
