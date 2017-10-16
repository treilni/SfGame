package com.treil.render.scene;

import com.jme3.app.SimpleApplication;
import com.treil.render.geom.HasExtent;
import com.treil.sfgame.map.HexMap;
import com.treil.sfgame.player.Player;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Nicolas
 * @since 24/01/2017.
 */
public interface Scene extends HasExtent {
    void init(@Nonnull SimpleApplication application, @Nonnull HexMap map, @Nonnull List<Player> players);

    void update(float tpf, @Nonnull SimpleApplication application);

}
