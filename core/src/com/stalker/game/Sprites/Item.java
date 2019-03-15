package com.stalker.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.InteractiveTileObject;

public class Item extends InteractiveTileObject {
    /**
     * Constructor of the class
     *
     * @param world  : World
     * @param map    : TiledMap
     * @param bounds : Rectangle
     * @param player : Player
     */
    public Item(World world, TiledMap map, Rectangle bounds, Player player) {
        super(world, map, bounds, player);
        fixture.setUserData(this);
        setCategoryFilter(StalkerGame.ITEM_BIT);
    }

    /**
     * function called when the player collide with the objet
     */
    @Override
    public void onBodyHit() {
        setCategoryFilter(StalkerGame.DESTROYED_BIT);
        getCell().setTile(null);
        StalkerGame.DeplacementValue = -1;
    }
}
