package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Albert on 2016-03-31.
 */
public class Trampoline extends Sprite{

    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";

    public Trampoline(Sprite sprite,TiledMapTileLayer collisionLayer){
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    public void update(float dt){


    }
    public void draw(SpriteBatch sb){
        super.draw(sb);
    }

    public com.badlogic.gdx.maps.tiled.TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }
}
