package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.treil.render.scene.tile.HexTile;
import com.treil.render.scene.tile.SelectionMarker;
import com.treil.render.scene.units.AntSprite;
import com.treil.render.scene.units.UnitSprite;
import com.treil.sfgame.units.Ant;
import com.treil.sfgame.units.Unit;
import com.treil.sfgame.units.UnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
class UnitsRenderer {
    @SuppressWarnings("unused")
    @Nonnull
    private static final Logger logger = LoggerFactory.getLogger(UnitsRenderer.class);

    @Nonnull
    private final Map<UnitType, Supplier<UnitSprite>> spriteFromType = new HashMap<>();

    @Nonnull
    private final Map<Unit, UnitSprite> spriteFromUnit = new HashMap<>();

    @Nonnull
    private final MapRenderer mapRenderer;
    @Nonnull
    private final Node rootNode;
    @Nonnull
    private final Spatial selectionNode;

    UnitsRenderer(@Nonnull AssetManager assetManager, @Nonnull MapRenderer mapRenderer, @Nonnull Node rootNode) {
        this.mapRenderer = mapRenderer;
        EnumSet<UnitType> antTypes = EnumSet.of(UnitType.BLACK_ANT, UnitType.RED_ANT, UnitType.YELLOW_ANT);
        antTypes.forEach(unitType -> spriteFromType.put(unitType, () -> new AntSprite(assetManager, unitType)));
        this.rootNode = rootNode;
        final Material selectionMaterial = mapRenderer.getUnshadedMaterial(Colors.selectedUnitColor);
        selectionNode = new SelectionMarker(selectionMaterial);
    }

    void registerUnits(@Nonnull List<Unit> units) {
        units.forEach(unit -> {
            final Supplier<UnitSprite> spriteSupplier = spriteFromType.get(unit.getType());
            if (spriteSupplier != null) {
                final UnitSprite unitSprite = spriteSupplier.get();
                rootNode.attachChild(unitSprite);
                spriteFromUnit.put(unit, unitSprite);
                updateUnitSpritePosition(unit);
            }
        });
    }

    private void updateUnitSpritePosition(Unit unit) {
        UnitSprite unitSprite = spriteFromUnit.get(unit);
        if (unitSprite != null) {
            final HexTile tile = mapRenderer.getTileAt(unit.getPosition().getLocation());
            final Vector3f unitTranslation = tile.getWorldTranslation();
            unitSprite.setLocalTranslation(unitTranslation);
            if (unit.isSelected()) {
                rootNode.attachChild(selectionNode);
                selectionNode.setLocalTranslation(unitTranslation);
            }
        }
    }

    void onUnitUpdate(Unit unit) {
        hideSelection();
        updateUnitSpritePosition(unit);
    }

    private void hideSelection() {
        selectionNode.removeFromParent();
    }
}
