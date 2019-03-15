package com.stalker.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stalker.game.Screens.GameScreen;
import com.stalker.game.StalkerGame;


public class Ball extends Sprite {
    private World world;
    private Body body;
    private TextureRegion region;
    public static boolean stalkerIsDead;
    public static int end;

    public Ball(World world, GameScreen screen) {
        super(screen.getAtlasEnemi().findRegion("ball"));
        this.world = world;
        defineBall();
        this.region = new TextureRegion(getTexture(), 0, 0, 43, 36);
        setBounds(0, 0, 43 / StalkerGame.PPM, 36 / StalkerGame.PPM);
        setRegion(this.region);
        this.stalkerIsDead = false;
    }

    public void defineBall() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(693 / StalkerGame.PPM, 50 / StalkerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(15 / StalkerGame.PPM);
        fdef.shape = circle;
        this.body.createFixture(fdef).setUserData("playerBody");
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
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
    }

    public boolean isStalkerIsDead() {
        return stalkerIsDead;
    }

    public void setStalkerIsDead(boolean stalkerIsDead) {
        this.stalkerIsDead = stalkerIsDead;
    }

}
