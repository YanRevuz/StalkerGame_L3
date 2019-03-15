package com.stalker.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.InteractiveTileObject;

public class Door extends InteractiveTileObject {
    private int num;

    /**
     * Constructor of the class
     *
     * @param world  : World
     * @param map    : TiledMap
     * @param bounds : Rectangle
     * @param player : Player
     */
    public Door(World world, TiledMap map, Rectangle bounds, Player player, int num) {
        super(world, map, bounds, player);
        this.num = num;
        fixture.setUserData(this);
    }


    /**
     * function called when the player collide with the objet
     */
    @Override
    public void onBodyHit() {
        if (this.num == 1) {
            super.player.setStateDoor(1);
        }
        if (this.num == 2) {

            if(StalkerGame.Level2unLock) {
                super.player.setStateDoor(2);
            }
        }
    }


}
