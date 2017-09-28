package com.treil.sfgame.map;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
public class HexRow extends ArrayList<HexCell> {
    public HexRow(int rowNum, int columns, @Nonnull MapGenerator mapGenerator) {
        for (int c = 0; c < columns; c++) {
            add(new HexCell(rowNum, c, mapGenerator));
        }
    }
}
