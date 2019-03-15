package com.stalker.game.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.stalker.game.Sprites.Door;
import com.stalker.game.Sprites.Ground;
import com.stalker.game.Sprites.Hole;
import com.stalker.game.Sprites.Item;
import com.stalker.game.Sprites.Lava;
import com.stalker.game.Sprites.Shop;
import com.stalker.game.Sprites.Trap;

public class WorldContactListener implements ContactListener {
    /**
     * function to define the actions to do depending on objects collided with and part of the body of the player (trap/foot)
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "playerBody" || fixB.getUserData() == "playerBody") {
            Fixture playerBody = fixA.getUserData() == "playerBody" ? fixA : fixB;
            Fixture object = playerBody == fixA ? fixB : fixA;
            if (object.getUserData() != null && (object.getUserData() instanceof Door || object.getUserData() instanceof Item) || object.getUserData() instanceof Shop || object.getUserData() instanceof Lava || object.getUserData() instanceof Hole || object.getUserData() instanceof Ground) {
                ((InteractiveTileObject) object.getUserData()).onBodyHit();
            }
        }

        if (fixA.getUserData() == "foot" || fixB.getUserData() == "foot") {
            Fixture playerBody = fixA.getUserData() == "foot" ? fixA : fixB;
            Fixture object = playerBody == fixA ? fixB : fixA;
            if (object.getUserData() != null && object.getUserData() instanceof Trap) {
                ((InteractiveTileObject) object.getUserData()).onBodyHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
