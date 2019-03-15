package com.stalker.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.stalker.game.StalkerGame;


public class GameOverScreen implements Screen {
    private Stage stage;
    private StalkerGame game;
    private Table table;
    private float time = 0;

    public GameOverScreen(StalkerGame g) {
        this.game = g;
        this.stage = new Stage(new ScreenViewport());
        //style Label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        this.table = new Table();
        table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("GAME OVER", font);
        table.add(gameOverLabel).expandX();
        stage.addActor(table);
    }

    /**
     * function to dispose the resources
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * function to render gameOver screen
     *
     * @param dt : float representing the time
     */
    @Override
    public void render(float dt) {
        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        this.time += dt;
        // redirect to menu after a moment
        if (this.time > 1) {
            game.setScreen(new MenuScreen(game));
        }


    }

    /**
     * function to resize the viewport
     *
     * @param width  type: int
     * @param height type: int
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setTransform(true);
        table.setSize(width, height);
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
}
