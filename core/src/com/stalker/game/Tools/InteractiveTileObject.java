package com.stalker.game.Tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Sprites.Player;
import com.stalker.game.StalkerGame;

/**
 * abstract class to define basic objets in the game
 */
public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected Player player;

    /**
     * Constructor of the class
     *
     * @param world
     * @param map
     * @param bounds
     * @param player
     */
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds, Player player) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        this.player = player;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / StalkerGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / StalkerGame.PPM);
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2 / StalkerGame.PPM, bounds.getHeight() / 2 / StalkerGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onBodyHit();

    /**
     * function to set the category filter - to define the interactions between the element of our game
     *
     * @param filterBit : short
     */
    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * function to get the cell which is interacting with the player
     *
     * @return the cell : TiledMapTileLayer.Cell
     */
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int) (body.getPosition().x * StalkerGame.PPM / 32), (int) (body.getPosition().y * StalkerGame.PPM / 32));
    }
}
