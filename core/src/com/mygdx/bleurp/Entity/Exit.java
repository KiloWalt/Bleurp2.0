package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Albert on 2016-03-30.
 */
public class Exit extends Sprite {
    private Vector2 velocity = new Vector2();
    private float gravity = 60 * 1.8f;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";

    public Exit(Sprite sprite, TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }
    public void update(float dt) {
        //Gravity
        velocity.y -= gravity * dt;


        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        if (velocity.y < 0) {
            collisionY = collidesBottom();
        }
        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
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
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }
    public void draw(SpriteBatch sb){
        super.draw(sb);
    }
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

}
