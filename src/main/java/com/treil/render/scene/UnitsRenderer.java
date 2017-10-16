package com.treil.render.scene;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.treil.render.scene.tile.HexTile;
import com.treil.render.scene.units.AntSprite;
import com.treil.render.scene.units.UnitSprite;
import com.treil.sfgame.units.Ant;
import com.treil.sfgame.units.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Nicolas
 * @since 26/09/2017.
 */
class UnitsRenderer {
    @Nonnull
    private static final Logger logger = LoggerFactory.getLogger(UnitsRenderer.class);

    @Nonnull
    private final Map<Class<? extends Unit>, Supplier<UnitSprite>> spriteFromClass = new HashMap<>();

    @Nonnull
    private final Map<Unit, UnitSprite> spriteFromUnit = new HashMap<>();

    @Nonnull
    private final MapRenderer mapRenderer;
    @Nonnull
    private final Node rootNode;

    UnitsRenderer(@Nonnull AssetManager assetManager, @Nonnull MapRenderer mapRenderer, @Nonnull Node rootNode) {
        this.mapRenderer = mapRenderer;
        spriteFromClass.put(Ant.class, () -> new AntSprite(assetManager));
        this.rootNode = rootNode;
    }

    public void registerUnits(@Nonnull List<Unit> units) {
        units.forEach(unit -> {
            final Supplier<UnitSprite> spriteSupplier = spriteFromClass.get(unit.getClass());
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
            unitSprite.setLocalTranslation(tile.getWorldTranslation());
        }
    }

    public void onUnitUpdate(Unit unit) {
        updateUnitSpritePosition(unit);
    }
}
