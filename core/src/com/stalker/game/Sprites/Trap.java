package com.stalker.game.Sprites;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.InteractiveTileObject;

public class Trap extends InteractiveTileObject {
    private TiledMapTile tile;

    /**
     * Constructor of the class
     *
     * @param world  : World
     * @param map    : TiledMap
     * @param bounds : Rectangle
     * @param player : Player
     */
    public Trap(World world, TiledMap map, Rectangle bounds, Player player) {
        super(world, map, bounds, player);
        fixture.setUserData(this);
        setCategoryFilter(StalkerGame.TRAP_HIDE_BIT);
        this.tile = getCell().getTile();
    }

    /**
     * function called when the player collide with the trap
     */
    @Override
    public void onBodyHit() {
        player.hit();
    }

    /**
     * function to get the cell (trap)
     *
     * @return the cell concerned by the trap
     */
    @Override
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(4);
        return layer.getCell((int) (body.getPosition().x * StalkerGame.PPM / 32), (int) (body.getPosition().y * StalkerGame.PPM / 32));
    }

    /**
     * function to change the aspect of the tile - hide the trap (desactivation)
     */
    public void cacher() {
        getCell().setTile(null);
        setCategoryFilter(StalkerGame.TRAP_HIDE_BIT);
    }

    /**
     * function to change the aspect of the tile - show the trap (activation)
     */
    public void montrer() {
        getCell().setTile(this.tile);
        setCategoryFilter(StalkerGame.TRAP_SHOW_BIT);
    }


}
