package com.stalker.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stalker.game.Scenes.Hud;
import com.stalker.game.Sprites.Player;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.WorldContactListener;

/**
 * basic screen to refer for implementing our differents levels in specialised classes
 */
public abstract class GameScreen implements Screen {
    // load images created with TexturePacker
    protected TextureAtlas atlas;
    protected TextureAtlas atlasEnemi;

    // viewport elements
    protected OrthographicCamera gameCam;
    protected FitViewport gamePort;

    // to load the map
    protected TmxMapLoader mapLoader;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;

    // elements of our game
    protected StalkerGame game;
    protected World world;
    protected Box2DDebugRenderer b2dr;
    protected Player player;

    // controller over the screen
    protected Hud hud;

    // Asset Manager
    protected AssetManager manager;

    /**
     * Constructor of the GameScreen - create the basic screen we'll use in specialized classes
     *
     * @param game StalkerGame
     */
    public GameScreen(StalkerGame game) {
        // creation of atlas container for our player images animations
        atlas = new TextureAtlas("player/player.pack");
        atlasEnemi = new TextureAtlas("enemi/ball.pack");
        this.game = game;
        this.gameCam = new OrthographicCamera();
        // ration of the screen conserved
        this.gamePort = new FitViewport(StalkerGame.V_WIDTH / StalkerGame.PPM, StalkerGame.V_HEIGHT / StalkerGame.PPM, gameCam);
        this.hud = new Hud();
        mapLoader = new TmxMapLoader();
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());
        // to load all sounds and music in the game
        this.manager = new AssetManager();
        manager.load("audio/music/selectLevel.ogg", Music.class);
        manager.load("audio/music/level1.mp3", Music.class);
        manager.load("audio/music/level2.mp3", Music.class);
        manager.load("audio/music/confusedPlayer.mp3", Music.class);
        manager.load("audio/sounds/gameOverSound.ogg", Sound.class);
        manager.load("audio/sounds/successSound.ogg", Sound.class);
        manager.finishLoading();
    }

    /**
     * function to move the player according to the conditions :
     * - if right button pressed : move the player to right on x position
     * - if left button pressed : move the player to left on -x position
     * - if up button pressed : move the player up on y position
     * - if down button pressed : move the player down on -y position
     * - if none pressed : set velocity to 0
     *
     * @param dt     type : float - delta time to update player movement
     * @param player type : Player - our player
     */
    public void handleInput(float dt, Player player) {
        if (player.currentState != Player.State.DEAD) {
            if (hud.isRightPressed() && player.b2body.getLinearVelocity().x <= 2 && !hud.isUpPressed() && !hud.isDownPressed() && !hud.isLeftPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);
            }
            if (hud.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2 && !hud.isUpPressed() && !hud.isDownPressed() && !hud.isRightPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);
            }

            if (hud.isUpPressed() && player.b2body.getLinearVelocity().y <= 2 && !hud.isLeftPressed() && !hud.isRightPressed() && !hud.isDownPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (hud.isDownPressed() && player.b2body.getLinearVelocity().y >= -2 * StalkerGame.DeplacementValue && !hud.isLeftPressed() && !hud.isRightPressed() && !hud.isUpPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (!hud.isUpPressed() && !hud.isDownPressed() && !hud.isLeftPressed() && !hud.isRightPressed()) {
                player.b2body.setLinearVelocity(0, 0);
            }
        }
    }
    /*public void handleInput(float dt, Player player) {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2 && !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), false);
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2 && !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
            player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
            player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.setLinearVelocity(0, 0);
        }
    }*/


    /**
     * Inverted function to move the player according to the conditions :
     * - if the player is dead : may have a kind of animation
     * - if right button pressed : move the player to right on -x position
     * - if left button pressed : move the player to left on x position
     * - if up button pressed : move the player up on -y position
     * - if down button pressed : move the player down on y position
     * - if none pressed : set velocity to 0
     *
     * @param dt     type : float - delta time to update player movement
     * @param player type : Player - our player
     */
    public void handleInputInverted(float dt, Player player) {
        if (player.currentState != Player.State.DEAD) {
            if (hud.isRightPressed() && player.b2body.getLinearVelocity().x >= -2 && !hud.isUpPressed() && !hud.isDownPressed() && !hud.isLeftPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);

            }
            if (hud.isLeftPressed() && player.b2body.getLinearVelocity().x <= 2 && !hud.isUpPressed() && !hud.isDownPressed() && !hud.isRightPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);
            }

            if (hud.isUpPressed() && player.b2body.getLinearVelocity().y >= -2 && !hud.isLeftPressed() && !hud.isRightPressed() && !hud.isDownPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (hud.isDownPressed() && player.b2body.getLinearVelocity().y <= 2 && !hud.isLeftPressed() && !hud.isRightPressed() && !hud.isUpPressed()) {
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (!hud.isUpPressed() && !hud.isDownPressed() && !hud.isLeftPressed() && !hud.isRightPressed()) {
                player.b2body.setLinearVelocity(0, 0);
            }
        }
    }

   /* public void handleInputInverted(float dt, Player player) {
        if (player.currentState != Player.State.DEAD) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x >= -2 && !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), false);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);

            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <= 2 && !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y >=-2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y <=2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.setLinearVelocity(0, 0);
            }
        }
    }*/

    /**
     * Getter for atlas images
     *
     * @return the atlas
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * Getter for atlas images
     *
     * @return the atlas
     */
    public TextureAtlas getAtlasEnemi() {
        return atlasEnemi;
    }

    /**
     * function to resize the viewport
     *
     * @param width  type: int
     * @param height type: int
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * function to dispose the resources
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
