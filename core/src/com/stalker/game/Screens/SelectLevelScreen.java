package com.stalker.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.stalker.game.Sprites.Player;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.B2WorldCreator;

/**
 * specialised class of GameScreen.class
 * it's the level selection class
 */
public class SelectLevelScreen extends GameScreen {
    private Music music;
    private Label label;

    /**
     * Constructor of the class - initialisation of all elements we need for the selection of level
     *
     * @param game : StalkerGame
     */
    public SelectLevelScreen(StalkerGame game) {
        super(game);
        map = mapLoader.load("selectLevel.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / StalkerGame.PPM);
        player = new Player(world, this);
        new B2WorldCreator(world, map, 0, player, null);
        music = manager.get("audio/music/selectLevel.ogg", Music.class);
        music.setLooping(true);
        music.play();
    }

    /**
     * function to update all elements
     *
     * @param dt : float representing the time
     */
    public void update(float dt) {
        if (StalkerGame.DeplacementValue == 1) {
            handleInput(dt, this.player);
            // otherwise - call inverted function of deplacement
        } else {
            handleInputInverted(dt, this.player);
        }
        player.update(dt);
        world.step(1 / 60f, 6, 2);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    /**
     * function to update all elements of the current scene
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
        //b2dr.render(world, gameCam.combined); //uncomment to see debug lines
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        // check if the player is in front of the level 1 door - transition to Level 1
        if (player.getStateDoor() == 1) {
            StalkerGame.POS_X = 802;
            StalkerGame.POS_Y = 50;
            game.setScreen(new Level1Screen(game));
            music.stop();
            dispose();
        }
        if (player.getStateDoor() == 2 && StalkerGame.Level2unLock) {
            game.setScreen(new EndScreen(game));
            music.stop();
            dispose();
        }
        if (player.getStateShop()) {
            if (StalkerGame.DeplacementValue == -1) {
                hud.shopSell();
            }
            // not in front of shop door
            player.setStateShop(false);
        }

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

  /*  @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }*/
}
