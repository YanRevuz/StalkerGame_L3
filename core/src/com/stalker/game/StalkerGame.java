package com.stalker.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stalker.game.Screens.EndScreen;
import com.stalker.game.Screens.Level1Screen;
import com.stalker.game.Screens.MenuScreen;

public class StalkerGame extends Game {
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 408;
    public static final float PPM = 100; //pixel per meters
    public static final String TITLE = "Stalker";

    // CATEGORIES
    public static final short DEFAULT_BIT = 1;
    public static final short STALKER_BIT = 2;
    public static final short ITEM_BIT = 4;
    public static final short TRAP_SHOW_BIT = 8;
    public static final short TRAP_HIDE_BIT = 16;
    public static final short DESTROYED_BIT = 32;
    public static final short STALKER_FOOT_BIT = 64;

    public static int DeplacementValue = 1;
    public static boolean Level2unLock = false;

    // POSITION PLAYER
    public static int POS_X = 670;
    public static int POS_Y = 670;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
        //setScreen(new EndScreen(this));
        //setScreen(new Level1Screen(this));
        //setScreen(new GameOverScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static boolean isLevel2unLock() {
        return Level2unLock;
    }

    public static void setLevel2unLock(boolean level2unLock) {
        Level2unLock = level2unLock;
    }
}
