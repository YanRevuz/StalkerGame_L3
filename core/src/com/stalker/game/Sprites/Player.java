package com.stalker.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.stalker.game.Entities.B2dSteeringEntity;
import com.stalker.game.Screens.GameScreen;
import com.stalker.game.StalkerGame;


public class Player extends Sprite {
    // the different states of the player
    public enum State {
        RUNNING_RIGHT, RUNNING_LEFT, RUNNING_UP, RUNNING_DOWN, STANDING, DEAD
    }

    ;
    public State currentState;
    public State previousState;

    // the different TextureRegion of the player movements
    private TextureRegion playerStandUp;
    private TextureRegion playerStandDown;
    private TextureRegion playerStandRight;
    private TextureRegion playerStandLeft;
    private TextureRegion playerStandState;
    private TextureRegion stalkerDead;

    // the different animations of the player
    private Animation<TextureRegion> playerRunLeft;
    private Animation<TextureRegion> playerRunRight;
    private Animation<TextureRegion> playerRunUp;
    private Animation<TextureRegion> playerRunDown;

    private World world;
    public Body b2body;
    private GameScreen screen;
    private float stateTimer;
    private int isFrontDoor;
    private boolean isFrontShop;
    private boolean stalkerIsDead;

    // to add ai behaviour with player
    private B2dSteeringEntity playerEntity;

    /**
     * @param world  : World
     * @param screen : Gamescreen
     */
    public Player(World world, GameScreen screen) {
        super(screen.getAtlas().findRegion("player"));
        this.screen = screen;
        this.world = world;
        isFrontDoor = 0;
        isFrontShop = false;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        // get the TextureRegion from images
        playerStandDown = new TextureRegion(getTexture(), 0, 0, 32, 48);
        playerStandLeft = new TextureRegion(getTexture(), 0, 48, 32, 48);
        playerStandRight = new TextureRegion(getTexture(), 0, 96, 32, 48);
        playerStandUp = new TextureRegion(getTexture(), 0, 144, 32, 48);

        // define the coordinate to get the images corresponding to the movements of player
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 48));
        }
        playerRunDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 48, 32, 48));
        }
        playerRunLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 96, 32, 48));
        }
        playerRunRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 144, 32, 48));
        }
        playerRunUp = new Animation<TextureRegion>(0.1f, frames);
        definePlayer();
        this.playerEntity = new B2dSteeringEntity(this.b2body, 10);
        playerStandState = playerStandDown;
        setBounds(0, 0, 32 / StalkerGame.PPM, 48 / StalkerGame.PPM);
        setRegion(playerStandState);
    }

    /**
     * function to set the state of the door
     *
     * @param state : boolean
     */
    public void setStateDoor(int state) {
        isFrontDoor = state;
    }

    /**
     * function to get the state of the door
     *
     * @return isFrontDoor : a boolean that indicate if the player is touching (true) the door or not (false)
     */
    public int getStateDoor() {
        return isFrontDoor;
    }

    /**
     * function to set the state of the shop
     *
     * @param state : boolean
     */
    public void setStateShop(boolean state) {
        isFrontShop = state;
    }

    /**
     * function to get the state of the shop
     *
     * @return isFrontShop : a boolean that indicate if the player is touching (true) the shop or not (false)
     */
    public boolean getStateShop() {
        return isFrontShop;
    }

    /**
     * function to update the element for the player animation and position
     *
     * @param dt : float representing the time
     */
    public void update(float dt) {
        //Gdx.app.log("player", "" + this.b2body.getPosition());
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 4);
        setRegion(getFrame(dt));
    }

    /**
     * function to get the corresponding frames according to the state of the player
     *
     * @param dt : float - it's the time
     * @return region : the TextureRegion we want to draw for the player
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = playerRunDown.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_DOWN:
                region = playerRunDown.getKeyFrame(stateTimer, true);
                playerStandState = playerStandDown;
                break;
            case RUNNING_LEFT:
                region = playerRunLeft.getKeyFrame(stateTimer, true);
                playerStandState = playerStandLeft;
                break;
            case RUNNING_RIGHT:
                region = playerRunRight.getKeyFrame(stateTimer, true);
                playerStandState = playerStandRight;
                break;
            case RUNNING_UP:
                region = playerRunUp.getKeyFrame(stateTimer, true);
                playerStandState = playerStandUp;
                break;
            default:
                region = playerStandState;
                break;

        }

        /*if the current state is the same as the previous state increase the state timer.
        otherwise the state has changed and we need to reset timer.*/
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    /**
     * function to get the state of the player
     *
     * @return state : State of the player
     */
    public State getState() {
        if (stalkerIsDead) {
            return State.DEAD;
        } else if (b2body.getLinearVelocity().y > 0) {
            return State.RUNNING_UP;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.RUNNING_DOWN;
        } else if (b2body.getLinearVelocity().x < 0) {
            return State.RUNNING_LEFT;
        } else if (b2body.getLinearVelocity().x > 0) {
            return State.RUNNING_RIGHT;
        } else {
            return State.STANDING;
        }
    }

    /**
     * function to create the player and set his position depending on instance of screens
     */
    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        //802 18 beginning position // 120 920 near trap position //650 790 near object position
        bdef.position.set(StalkerGame.POS_X / StalkerGame.PPM, StalkerGame.POS_Y / StalkerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(12 / StalkerGame.PPM, 10 / StalkerGame.PPM);
        fdef.filter.categoryBits = StalkerGame.STALKER_BIT;
        fdef.filter.maskBits = StalkerGame.DEFAULT_BIT | StalkerGame.ITEM_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("playerBody");

        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-2 / StalkerGame.PPM, -5 / StalkerGame.PPM), new Vector2(2 / StalkerGame.PPM, -5 / StalkerGame.PPM));
        fdef.filter.categoryBits = StalkerGame.STALKER_FOOT_BIT;
        fdef.filter.maskBits = StalkerGame.TRAP_SHOW_BIT;
        fdef.shape = foot;
        b2body.createFixture(fdef).setUserData("foot");

    }

    /**
     * function to determinate if the player is DEAD or not
     *
     * @return stalkerIsDead : boolean - true if the player is dead otherwise return false
     */
    public boolean isDead() {
        return stalkerIsDead;
    }

    /**
     * function to get the stateTimer
     *
     * @return stateTimer : float
     */
    public float getStateTimer() {
        return stateTimer;
    }

    /**
     * function to set the player to DEAD if he is hit
     */
    public void hit() {
        stalkerIsDead = true;
    }

    public B2dSteeringEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(B2dSteeringEntity playerEntity) {
        this.playerEntity = playerEntity;
    }
}
