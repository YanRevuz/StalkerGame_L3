package com.stalker.game.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stalker.game.StalkerGame;

public class Hud implements Disposable {
    public Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Viewport viewport;
    private Label label;
    private BitmapFont white;

    // the table for the movements of player
    private Table table;

    // the buttons to move the player
    private Button buttonUp;
    private Button buttonRight;
    private Button buttonDown;
    private Button buttonLeft;

    // the table for the shop
    private Table table1;

    // the buttons for the shop table
    private Button buttonSell;
    private Button buttonKeep;

    // the booleans pressed buttons - to move the player
    public boolean upPressed = false;
    public boolean leftPressed = false;
    public boolean downPressed = false;
    public boolean rightPressed = false;

    /**
     * Constructor of the overlay controller
     * initialisation of directionnal buttons and theirs listeners
     */
    public Hud() {
        viewport = new FitViewport(StalkerGame.V_WIDTH, StalkerGame.V_HEIGHT, new OrthographicCamera());
        // empty box - create a table into our stage
        stage = new Stage(viewport);
        this.atlas = new TextureAtlas("ui/buttons/buttons.pack");
        this.skin = new Skin(atlas);
        this.table = new Table(skin);
        Gdx.input.setInputProcessor(stage);

        // directional buttons
        buttonUp = new Button(skin.getDrawable("buttonUp"));
        buttonDown = new Button(skin.getDrawable("buttonDown"));
        buttonRight = new Button(skin.getDrawable("buttonRight"));
        buttonLeft = new Button(skin.getDrawable("buttonLeft"));

        // set a boolean to true if up button is pressed, false otherwise
        buttonUp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });
        // set a boolean to true if down button is pressed, false otherwise
        buttonDown.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        // set a boolean to true if right button is pressed, false otherwise
        buttonRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        // set a boolean to true if left button is pressed, false otherwise
        buttonLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        // size of stage
        table.setFillParent(true);
        table.add(buttonUp).width(50).height(50).padLeft(50);
        table.row();
        table.add(buttonLeft).width(50).height(50).left();
        table.getCell(buttonLeft);
        table.add(buttonRight).width(50).height(50);
        table.row();
        table.add(buttonDown).width(50).height(50).bottom().padLeft(50);
        table.bottom().left();
        //table.debug(); // to see the debug lines on table
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
     * function to know the state of the down button
     *
     * @return downPressed : a boolean - true if pressed otherwise return false
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * function to know the state of the left button
     *
     * @return leftPressed : a boolean - true if pressed otherwise return false
     */
    public boolean isLeftPressed() {

        return leftPressed;
    }

    /**
     * function to know the state of the right button
     *
     * @return rightPressed : a boolean - true if pressed otherwise return false
     */
    public boolean isRightPressed() {

        return rightPressed;
    }

    /**
     * function to know the state of the up button
     *
     * @return upPressed : a boolean - true if pressed otherwise return false
     */
    public boolean isUpPressed() {

        return upPressed;
    }

    /**
     * function to display the table on screen in order to sell the artefact
     */
    public void shopSell() {
        // resources for buttons
        this.table1 = new Table(skin);
        table1.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // fonts
        this.white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
        // style buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.normal");
        textButtonStyle.down = skin.getDrawable("button.pressed");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;
        //label
        Label.LabelStyle labelStyle = new Label.LabelStyle(this.white, Color.WHITE);
        this.label = new Label("Vendre l'objet ?", labelStyle);
        //buttons
        this.buttonSell = new TextButton("Oui", textButtonStyle);
        this.buttonKeep = new TextButton("Non", textButtonStyle);
        Gdx.input.setInputProcessor(stage);

        buttonSell.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                StalkerGame.DeplacementValue = 1;
                table1.remove();
                StalkerGame.Level2unLock = true;
                return true;
            }

        });

        buttonKeep.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                table1.remove();
                return true;
            }

        });

        table1.add(label).colspan(2).padLeft(150);
        table1.row();
        table1.add(buttonSell).width(150);
        table1.getCell(buttonSell).padLeft(150);
        table1.add(buttonKeep).width(150);
        table1.getCell(buttonKeep);
        //table1.setPosition(600/ StalkerGame.PPM, 600 / StalkerGame.PPM);
        //table1.debug(); // debug lines for table1
        table1.setFillParent(true);
        stage.addActor(table1);
    }

    public Table getTable() {
        return table;
    }

    public Table getTable1() {
        return table1;
    }
}
