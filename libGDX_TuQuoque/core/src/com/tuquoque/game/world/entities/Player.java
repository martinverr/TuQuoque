package com.tuquoque.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tuquoque.game.ui.GameUI;
import com.tuquoque.game.world.entities.animation.PlayerAnimation;

import java.io.StringWriter;
import java.io.Writer;


public class Player extends Entity {

    private final World world;
    private final float B2D_BODY_DEF_WIDTH = 0.4f;
    private final float B2D_BODY_DEF_HEIGHT = 0.6f;
    private final GameUI gameUI;

    private float maxHealth;
    private float maxMana;
    private float maxExp;

    private float health;
    private float mana;
    private float exp;
    private float level;

    /**
    * Constructor of Player
     *
     * Create the Box2D Body of the player and init the animations of its texture
     *
     * @param world Box2D World where the player will be defined
     * @param coords coordinates of where the player will be spawned
    * */
    public Player(World world, Vector2 coords, GameUI gameUI, AssetManager assetManager){
        //offset to legs = - heightEntireBox + heightLegs / 2
        super(world, coords, 0.4f, 0.15f, -0.6f +0.15f/2);
        this.world = world;
        this.gameUI = gameUI;

        health = 100;
        maxHealth = 100;
        mana = 50;
        maxMana = 100;
        exp = 0;
        maxExp = 100;
        level = 1;
        updateGameUI();

        playerBodyDef();
        setNpcAnimation(new PlayerAnimation(this, assetManager));
    }

    void playerBodyDef(){
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(B2D_BODY_DEF_WIDTH, B2D_BODY_DEF_HEIGHT);
        fixtureDef.shape = sensorShape;
        fixtureDef.isSensor = true;
        B2DBody.createFixture(fixtureDef).setUserData("player_sensor");

        fixtureDef.isSensor = false;
        sensorShape.dispose();
    }

    private void updateGameUI(){
        gameUI.setBars(health/maxHealth, exp/maxExp, mana/maxMana);
    }

    public void fillHealth(){
        health = maxHealth;
    }

    public void fillMana(){
        mana = maxMana;
    }

    public void levelUp(){
        level += 1;
        maxHealth += 50;
        maxMana += 10;
        maxExp *= 1.5f;
        fillHealth();
        fillMana();
        updateGameUI();
    }

    public void setHealth(float health) {
        if(health >= 0 && health <= maxHealth)
            this.health = health;
        else
            throw new IllegalArgumentException("Tried to set health to \"" + health + "\", but not in range: 0-" + maxHealth);
    }

    public void setMana(float mana) {
        if(mana >= 0 && mana <= maxMana)
            this.mana = mana;
        else
            throw new IllegalArgumentException("Tried to set mana to \"" + mana + "\", but not in range: 0-" + maxMana);
    }

    public void addExp(float exp) {
        if(exp < 0)
            throw new IllegalArgumentException("exp must be a positive value");
        this.exp += exp;

        //recursive call addExp if exp exceed
        if(exp > maxExp){
            float expInExceed = exp-maxExp;
            levelUp();
            addExp(expInExceed);
        }
    }

    public float getLevel() {
        return level;
    }

    public float getHealth() {
        return health;
    }

    public float getMana() {
        return mana;
    }

    public float getExp() {
        return exp;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public float getMaxExp() {
        return maxExp;
    }

    public void saveStats(){
        Json json = new Json(JsonWriter.OutputType.json);
        StringWriter jsonText = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(jsonText);
        json.setWriter(jsonWriter);


        json.writeObjectStart();

        {
            json.writeObjectStart("stats");
            json.writeValue("health", health);
            json.writeValue("maxHealth", maxHealth);
            json.writeValue("mana", mana);
            json.writeValue("maxMana", maxMana);
            json.writeValue("exp", exp);
            json.writeValue("maxExp", maxExp);
            json.writeValue("level", level);
            json.writeObjectEnd();
        }
        json.writeObjectEnd();

        FileHandle file = Gdx.files.local("data/stats.json");
        file.writeString(json.prettyPrint(jsonText.toString()), false);
    }

    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getNpcAnimation().getCurrentAnimation().getKeyFrame(elapsedTime, true),
                    B2DBody.getPosition().x - 0.65f,
                    B2DBody.getPosition().y - 0.7f,
                    1.3f, 1.6f);
        }
        else
            Gdx.app.error(this.getClass().getSimpleName(), "batch not drawing");
    }

    @Override
    public void teleportTo(Vector2 coordinates){
        world.destroyBody(B2DBody);
        //offset to legs = - heightEntireBox + heightLegs / 2
        entityDef(coordinates, B2D_BODY_DEF_WIDTH, 0.15f, -B2D_BODY_DEF_HEIGHT +0.15f/2);
        playerBodyDef();
    }
}
