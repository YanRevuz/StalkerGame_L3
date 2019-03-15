package com.stalker.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Tools.InteractiveTileObject;


public class Lava extends InteractiveTileObject {

    public Lava(World world, TiledMap map, Rectangle bounds, Player player) {
        super(world, map, bounds, player);
        fixture.setUserData(this);
    }

    @Override
    public void onBodyHit() {
        Ball.stalkerIsDead = true;
    }
}
