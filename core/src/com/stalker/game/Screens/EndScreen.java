package com.stalker.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stalker.game.Sprites.Ball;
import com.stalker.game.StalkerGame;
import com.stalker.game.Tools.B2WorldCreator;


public class EndScreen extends GameScreen {

    private Stage stage;
    private Ball ball;
    // Sounds and music attributes
    private Sound gameOverSound;
    private Sound successSound;
    private Music level2Music;

    public EndScreen(StalkerGame g) {
        super(g);
        this.ball = new Ball(world, this);
        stage = new Stage(gamePort, game.batch);
        map = mapLoader.load("minijeu.tmx");
        new B2WorldCreator(world, map, 2, player, null);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / StalkerGame.PPM);
        this.gameOverSound = manager.get("audio/sounds/gameOverSound.ogg", Sound.class);
        this.successSound = manager.get("audio/sounds/successSound.ogg", Sound.class);
        this.level2Music = manager.get("audio/music/level2.mp3", Music.class);
        this.level2Music.play();

    }

    /**
     * function to update all elements of the current scene
     *
     * @param dt : float representing the time
     */
    public void update(float dt) {
        world.step(1 / 60f, 6, 2);
        ball.getBody().setLinearVelocity(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX());
        gameCam.position.x = ball.getBody().getPosition().x;
        gameCam.position.y = ball.getBody().getPosition().y;
        gameCam.update();
        ball.update(dt);
        renderer.setView(gameCam);
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
        //b2dr.render(world, gameCam.combined); // uncomment to see debug lines
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        ball.draw(game.batch);
        game.batch.end();
        stage.draw();
        // if the player is DEAD - call GameOverScreen to be disposed
        if (gameOver()) {
            StalkerGame.POS_X = 670;
            StalkerGame.POS_Y = 670;
            this.level2Music.stop();
            this.gameOverSound.play();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        // if path 1 selected - end n1
        if (Ball.end == 1) {
            this.level2Music.stop();
            successSound.play();
            this.game.setScreen(new FinalScreen(game, 1));
            // if path 2 selected - end n2
        } else if (Ball.end == 2) {
            this.level2Music.stop();
            this.successSound.play();
            game.setScreen(new FinalScreen(game, 2));
        }
    }

    /**
     * function that indicate if the ball is dead or not
     *
     * @return boolean true if the ball is dead otherwise return false
     */
    public boolean gameOver() {
        if (ball.isStalkerIsDead()) {
            return true;
        }
        return false;
    }
}
