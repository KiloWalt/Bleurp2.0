package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bleurp.Bleurp;
import com.mygdx.bleurp.States.Play;

/**
 * Created by Albert on 2016-03-29.
 */
public class Boulder extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 50 * 2, speedU = 60 * 2;
    private float gravity = 60 *1.8f;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";

    public Boulder(Sprite sprite,TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    public void update(float dt){
        // apply gravity
        velocity.y -= gravity * dt;

        // clamp velocity
        if(velocity.y > speed)
            velocity.y = speedU;
        else if(velocity.y < -speed)
            velocity.y = -speedU;

        // save old position
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // move on x
        setX(getX() + velocity.x * dt);

        if(velocity.x < 0){// going left
            collisionX = collidesLeft();
        }
        else if(velocity.x > 0) // going right
            collisionX = collidesRight();
        // react to x collision
        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on y
        setY(getY() + velocity.y * dt * 5f);

        if(velocity.y < 0){
            collisionY = collidesBottom();
        }
        else if(velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if(collisionY) {
            setY(oldY);
            velocity.y = 0;
        }

        if(velocity.x > 0){
            velocity.x--;
        }else if(velocity.x < 0){
            velocity.x++;
        }
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public Vector2 getVelocity(){return velocity;
    }
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }
    public void draw(SpriteBatch sb){
        super.draw(sb);
    }
    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }



}
