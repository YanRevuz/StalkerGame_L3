package com.stalker.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.stalker.game.StalkerGame;


/**
 * Menu of the game
 */
public class MenuScreen implements Screen {
    // all attributes for UI elements
    private Skin skin;
    private Stage stage;
    private TextureAtlas atlas;
    private StalkerGame game;
    private Table table;
    private BitmapFont white;
    private TextButton playButton;
    private TextButton quitButton;
    private Texture bg;
    private Label heading;

    /**
     * Constructor of the class - initialisation of all elements and the listeners for the buttons
     *
     * @param g : StalkerGame
     */
    public MenuScreen(StalkerGame g) {
        this.game = g;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // resources for buttons
        this.atlas = new TextureAtlas("ui/buttons/buttons.pack");
        this.skin = new Skin(atlas);
        this.table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // fonts||  object.getUserData() instanceof Lava
        this.white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);

        // background texture
        bg = new Texture("bg.png");

        // style buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.normal");
        textButtonStyle.down = skin.getDrawable("button.pressed");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;

        //buttons
        this.playButton = new TextButton("Jouer", textButtonStyle);
        this.quitButton = new TextButton("Quitter", textButtonStyle);
        playButton.pad(30);
        quitButton.pad(30);

        //style Label
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        this.heading = new Label(StalkerGame.TITLE, headingStyle);

        // add all elements to the table
        table.add(heading).center();
        table.getCell(heading).spaceBottom(15);
        table.row();
        table.add(playButton).width(300);
        table.getCell(playButton).spaceBottom(10);
        table.row();
        table.add(quitButton).width(300);

        stage.addActor(table);
        // playButton listener - call a SelectLevelScreen(selection of level)
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SelectLevelScreen(game));
            }
        });
        // quitButton listener
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    /**
     * function to render the menu
     *
     * @param dt : float representing the time
     */
    @Override
    public void render(float dt) {
        // clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        // update eveything in the stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * function to dispose the resources
     */
    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
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
        stage.getCamera().update();
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