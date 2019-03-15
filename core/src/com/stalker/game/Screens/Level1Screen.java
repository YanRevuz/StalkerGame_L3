package com.stalker.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stalker.game.Sprites.Enemis;
import com.stalker.game.Sprites.Player;
import com.stalker.game.Sprites.Trap;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.B2WorldCreator;

import java.util.ArrayList;

/**
 * specialised class of GameScreen.class
 * it's the level 1 class
 */
public class Level1Screen extends GameScreen {
    private Stage stage;
    private ArrayList<Trap> tabTrap;
    private Enemis enemi;
    int indice = 0;
    float timeTrap = 0;

    // sounds and music for the level1
    private Music music;
    private Music music2;
    private Sound gameOverSound;

    /**
     * Constructor of the class, initialisation of all elements we need for the level 1
     *
     * @param game : StalkerGame
     */
    public Level1Screen(StalkerGame game) {
        super(game);
        stage = new Stage(gamePort, ((StalkerGame) game).batch);
        player = new Player(world, this);
        enemi = new Enemis(world, this);

        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / StalkerGame.PPM);
        this.tabTrap = new ArrayList<Trap>();
        new B2WorldCreator(world, map, 1, player, tabTrap);
        this.music = manager.get("audio/music/level1.mp3", Music.class);
        this.music2 = manager.get("audio/music/confusedPlayer.mp3", Music.class);
        this.gameOverSound = manager.get("audio/sounds/gameOverSound.ogg", Sound.class);
        music.setLooping(true);
        music2.setLooping(true);
        music.play();
        // to configure the arrival of AI
        Arrive<Vector2> arrive = new Arrive<Vector2>(enemi.getEnemi(), player.getPlayerEntity())
                .setArrivalTolerance(2f)
                .setDecelerationRadius(10);
        enemi.getEnemi().setBehavior(arrive);
    }

    /**
     * function to update the positions of the traps
     * use a loop to show/hide and activate/desactivate the traps depending of delta time
     *
     * @param dt : float representing the time
     */
    public void updateTrap(float dt) {
        if (indice >= this.tabTrap.size()) {
            indice = 0;
        }
        timeTrap += dt;
        // activate and desactivate the traps according to timeTrap
        if (timeTrap >= 1) {
            if (indice > 0) {
                this.tabTrap.get(indice - 1).cacher();
                this.tabTrap.get(indice).montrer();
            } else {
                this.tabTrap.get(this.tabTrap.size() - 1).cacher();
                this.tabTrap.get(indice).montrer();
            }
            indice++;
            timeTrap = 0;
        }
    }

    /**
     * function to update all elements of the current scene
     *
     * @param dt : float representing the time
     */
    public void update(float dt) {
        // if the player doesn't have the objet - call normal function of deplacement
        if (StalkerGame.DeplacementValue == 1) {
            handleInput(dt, this.player);
            // otherwise - call inverted function of deplacement
        } else {
            handleInputInverted(dt, this.player);
            music.stop();
            music2.play();
        }
        // update player
        player.update(dt);
        // update enemi
        enemi.update(dt);
        // update traps
        updateTrap(dt);
        world.step(1 / 60f, 6, 2);
        // update camera
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
        // when player is dead fix the camera
        if (player.currentState == Player.State.DEAD) {
            gameCam.position.x = player.b2body.getPosition().x;
        }
    }


    /**
     * function to render the scene
     * update and draw all the element needed for the level 1
     *
     * @param dt : float representing the time
     */
    @Override
    public void render(float dt) {
        update(dt);
        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //b2dr.render(world, gameCam.combined); // uncomment to see dedug lines
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        enemi.draw(game.batch);
        game.batch.end();
        stage.draw();
        hud.stage.draw();
        // if the player is DEAD - call GameOverScreen to be disposed
        if (gameOver()) {
            StalkerGame.POS_X = 670;
            StalkerGame.POS_Y = 670;
            music.stop();
            music2.stop();
            gameOverSound.play();
            game.setScreen(new GameOverScreen(game));
            StalkerGame.DeplacementValue = 1;
            dispose();
        }
        // back to selection map level
        if (player.getStateDoor() == 1) {
            StalkerGame.POS_X = 1650;
            StalkerGame.POS_Y = 750;
            music.stop();
            music2.stop();
            game.setScreen(new SelectLevelScreen(game));
            dispose();
        }
    }

    /**
     * function to detect if the player is dead
     *
     * @return a boolean true if his state is DEAD otherwise return false
     */
    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD) {
            return true;
        }
        return false;
    }

}
