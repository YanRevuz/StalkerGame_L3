package com.stalker.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Sprites.Door;
import com.stalker.game.Sprites.Ground;
import com.stalker.game.Sprites.Hole;
import com.stalker.game.Sprites.Item;
import com.stalker.game.Sprites.Lava;
import com.stalker.game.Sprites.Player;
import com.stalker.game.Sprites.Shop;
import com.stalker.game.Sprites.Trap;
import com.stalker.game.StalkerGame;

import java.util.ArrayList;

/**
 * class to define all objects of the game
 */
public class B2WorldCreator {
    private Player player;
    private int i = 1;

    public B2WorldCreator(World world, TiledMap map, int level, Player p, ArrayList<Trap> tabTrap) {
        this.player = p;
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        if (level == 1) {
            //FOR border
            for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / StalkerGame.PPM, (rect.getY() + rect.getHeight() / 2) / StalkerGame.PPM);
                body = world.createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / StalkerGame.PPM, rect.getHeight() / 2 / StalkerGame.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //FOR other
            for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / StalkerGame.PPM, (rect.getY() + rect.getHeight() / 2) / StalkerGame.PPM);
                body = world.createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / StalkerGame.PPM, rect.getHeight() / 2 / StalkerGame.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //FOR item
            for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Item(world, map, rect, player);
            }

            //FOR trap
            for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                Trap t = new Trap(world, map, rect, player);
                t.cacher();
                tabTrap.add(t);
            }

            //FOR doors
            for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Door(world, map, rect, player, 1);
            }
        } else if (level == 0) { // for selection level map
            //FOR building
            for (MapObject object : map.getLayers().get(4).getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / StalkerGame.PPM, (rect.getY() + rect.getHeight() / 2) / StalkerGame.PPM);
                    body = world.createBody(bdef);
                    shape.setAsBox(rect.getWidth() / 2 / StalkerGame.PPM, rect.getHeight() / 2 / StalkerGame.PPM);
                    fdef.shape = shape;
                    body.createFixture(fdef);

                } else if (object instanceof PolygonMapObject) {
                    PolygonMapObject poly = (PolygonMapObject) object;
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set(0, 0); //in the middle
                    float[] vertices = poly.getPolygon().getTransformedVertices();
                    Vector2[] worldVertices = new Vector2[vertices.length / 2];
                    for (int i = 0; i < vertices.length / 2; ++i) {
                        worldVertices[i] = new Vector2();
                        worldVertices[i].x = vertices[i * 2] / StalkerGame.PPM;
                        worldVertices[i].y = vertices[i * 2 + 1] / StalkerGame.PPM;
                    }
                    shape.set(worldVertices);
                    fdef.shape = shape; //change the shape of the fixture
                    body = world.createBody(bdef);
                    body.createFixture(fdef);

                }
            }

            //FOR object
            for (MapObject object : map.getLayers().get(5).getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / StalkerGame.PPM, (rect.getY() + rect.getHeight() / 2) / StalkerGame.PPM);
                    body = world.createBody(bdef);
                    shape.setAsBox(rect.getWidth() / 2 / StalkerGame.PPM, rect.getHeight() / 2 / StalkerGame.PPM);
                    fdef.shape = shape;
                    body.createFixture(fdef);

                } else if (object instanceof PolygonMapObject) {
                    PolygonMapObject poly = (PolygonMapObject) object;
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set(0, 0); //in the middle
                    float[] vertices = poly.getPolygon().getTransformedVertices();
                    Vector2[] worldVertices = new Vector2[vertices.length / 2];
                    for (int i = 0; i < vertices.length / 2; ++i) {
                        worldVertices[i] = new Vector2();
                        worldVertices[i].x = vertices[i * 2] / StalkerGame.PPM;
                        worldVertices[i].y = vertices[i * 2 + 1] / StalkerGame.PPM;
                    }
                    shape.set(worldVertices);
                    fdef.shape = shape; //change the shape of the fixture
                    body = world.createBody(bdef);
                    body.createFixture(fdef);
                }
            }

            //FOR border
            for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / StalkerGame.PPM, (rect.getY() + rect.getHeight() / 2) / StalkerGame.PPM);
                body = world.createBody(bdef);
                shape.setAsBox(rect.getWidth() / 2 / StalkerGame.PPM, rect.getHeight() / 2 / StalkerGame.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //FOR doors
            for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Door(world, map, rect, player, i);
                i++;
            }

            //FOR shop
            for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Shop(world, map, rect, player);
            }
        } else if (level == 2) {
            //FOR lava
            for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Lava(world, map, rect, player);
            }

            //FOR hole
            for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Hole(world, map, rect, player, i);
                i++;
            }

            //FOR ground
            for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                new Ground(world, map, rect, player);
            }
        }
    }
}
