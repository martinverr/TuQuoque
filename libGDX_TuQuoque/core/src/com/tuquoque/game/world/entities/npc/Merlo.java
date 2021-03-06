package com.tuquoque.game.world.entities.npc;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.ui.GameUI;
import com.tuquoque.game.world.entities.Player;
import com.tuquoque.game.world.entities.animation.OnlyIdleAnimation;

public class Merlo extends NPC{
    //1.3, 1.5
    public Merlo(World world, Vector2 coords, AssetManager assetManager) {
        super(world, 0.3f, 0.5f, coords);

        setNPCname("Merlo");
        setConversationConfigPath("NPC/Vecchio/dialogue_default.json");
        setNpcAnimation(new OnlyIdleAnimation(assetManager, "NPC/Vecchio/idle.atlas"));
    }


    @Override
    public void actionTriggered(Player player) {
        //if in dialogue range, load conversation with dog
        if(player.isInConversationRadius(this) && !GameUI.getInstance().getDialogue().isVisible()){
            GameUI.getInstance().getDialogue().loadConversation(this, null);
        }

        B2DBody.applyLinearImpulse(
                -B2DBody.getLinearVelocity().x,
                -B2DBody.getLinearVelocity().y,
                B2DBody.getWorldCenter().x,B2DBody.getWorldCenter().y,
                true);
    }

    @Override
    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getNpcAnimation().getCurrentAnimation().getKeyFrame(elapsedTime, true),
                    B2DBody.getPosition().x - 0.3f,
                    B2DBody.getPosition().y - 0.5f,
                    0.6f, 1f);
        }
        else
            Gdx.app.error(this.getClass().getSimpleName(), "batch not drawing");
    }
}
