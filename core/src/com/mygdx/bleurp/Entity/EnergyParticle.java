package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Albert on 2016-04-02.
 */
public class EnergyParticle extends Sprite {
    float x;
    float y;
    private int count;
    private boolean remove;

    public EnergyParticle(Sprite sprite,float x,float y) {
        super(sprite);
        this.x = x;
        this.y = y;
        setPosition(x,y);
    }

    public void update(float dt){
        count++;
        if(count == 60) remove = true;


    }
    public boolean shouldRemove() { return remove; }
    public void updateRemove(boolean remove) {
        this.remove = remove;
        count = 0;
    }


    public void draw(SpriteBatch sb){
        super.draw(sb);
    }
}
