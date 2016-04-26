package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Albert on 2016-03-30.
 */
public class Spike extends Sprite{

    private TiledMapTileLayer collisionLayer;

    public Spike(Sprite sprite,TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }
    public void update(float dt) {
    }

    public void draw(SpriteBatch sb){
        super.draw(sb);
    }
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }
}
