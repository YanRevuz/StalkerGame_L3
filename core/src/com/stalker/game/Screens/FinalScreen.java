package com.stalker.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.stalker.game.StalkerGame;


public class FinalScreen implements Screen{
    private StalkerGame game;
    private Stage stage;
    private Table table;
    private Label endLabel;
    private BitmapFont white;

    public FinalScreen(StalkerGame g, int num) {
        this.game=g;
        this.stage = new Stage(new ScreenViewport());
        this.white = new BitmapFont(Gdx.files.internal("fonts/whiteSmall.fnt"), false);
        //style Label
        Label.LabelStyle textStyle = new Label.LabelStyle(white, Color.WHITE);
        this.table = new Table();
        this.table.center();
        this.table.setFillParent(true);
        if(num==1){
            this.endLabel = new Label("FELICITATIONS ! VOUS AVEZ GAGNE\nVOUS AVEZ CHOISI LE CHEMIN FACILE \nQUI VOUS MENE VERS DE NOUVELLES AVENTURES \nDANS LES TERRIFIANTS BAS FONDS DE LA BASE XLIM \nSEE YOU SOON STALKER...", textStyle);
        }
        if(num==2){
            this.endLabel = new Label("FELICITATIONS ! VOUS AVEZ GAGNE\nVOUS AVEZ CHOISI LE CHEMIN DIFFICILE \nQUI VOUS MENE VERS DE NOUVELLES AVENTURES \nDANS LA DIMENSION EXTRATERRESTRE GOBLIM \nSEE YOU SOON STALKER...", textStyle);
        }
        table.add(endLabel).maxSize(800,800).center();
        //table.debug();
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
