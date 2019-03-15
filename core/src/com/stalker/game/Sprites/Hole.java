package com.stalker.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Tools.InteractiveTileObject;


public class Hole extends InteractiveTileObject {
    private int num;

    public Hole(World world, TiledMap map, Rectangle bounds, Player player, int num) {
        super(world, map, bounds, player);
        this.num = num;
        fixture.setUserData(this);
    }

    @Override
    public void onBodyHit() {
        Ball.end = this.num;
    }
}
