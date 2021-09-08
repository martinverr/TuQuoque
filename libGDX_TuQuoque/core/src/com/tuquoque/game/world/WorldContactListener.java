package com.tuquoque.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.map.MapType;
import com.tuquoque.game.world.Portal;

public class WorldContactListener implements ContactListener {
    private final GameStarter context;

    public WorldContactListener(GameStarter context){
        this.context = context;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // if Player-<...>
        if(fixA.getUserData() == "player_sensor" || fixB.getUserData() == "player_sensor"){
            Fixture player = fixA.getUserData() == "player_sensor" ? fixA : fixB;
            Fixture other = fixA.getUserData() == "player_sensor" ? fixB : fixA;

            // if Player-Portal
            if(other.getUserData() != null && other.getUserData().getClass().equals(Portal.class)){
                //during contact listener, changing map with loadMap() would cause a crash because box2d World is locked
                MapType destination = ((Portal) other.getUserData()).getDestinationMapType();
                context.getMapManager().loadMapSafe(destination);
                Gdx.app.debug(this.getClass().getSimpleName(), "Player passed portal to " + destination);
            }
        }
        else
            Gdx.app.debug(this.getClass().getSimpleName(),
                    fixA.getUserData().getClass().getSimpleName() + " : "
                            + fixB.getUserData().getClass().getSimpleName());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
