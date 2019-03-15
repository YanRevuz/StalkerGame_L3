package com.stalker.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Entities.B2dSteeringEntity;
import com.stalker.game.Screens.GameScreen;
import com.stalker.game.StalkerGame;


public class Enemis extends Sprite {
    private World world;
    private Body body;
    private B2dSteeringEntity enemi;
    private TextureRegion region;

    public Enemis(World world, GameScreen screen) {
        super(screen.getAtlasEnemi().findRegion("ball"));
        this.world = world;
        defineEnemi();
        this.enemi = new B2dSteeringEntity(this.body, 10);
        this.region = new TextureRegion(getTexture(), 0, 0, 43, 36);
        setBounds(0, 0, 43 / StalkerGame.PPM, 36 / StalkerGame.PPM);
        setRegion(this.region);

    }

    public void defineEnemi() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(1100 / StalkerGame.PPM, 500 / StalkerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(15 / StalkerGame.PPM);
        fdef.shape = circle;
        this.body.createFixture(fdef);
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public B2dSteeringEntity getEnemi() {
        return this.enemi;
    }

    public void setEnemi(B2dSteeringEntity enemi) {
        this.enemi = enemi;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * function to update the element for the enemi animation and position
     *
     * @param dt : float representing the time
     */
    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 4);
        this.enemi.update(dt);
    }
}
