package com.mygdx.bleurp.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Albert on 2016-04-07.
 */
public class Bird  {
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;



    public Bird(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("flappyPigAnimation.png");
        birdAnimation = new Animation(new TextureRegion(texture),3,0.5f);
        bounds = new Rectangle(x,y,texture.getWidth() / 3,texture.getHeight());
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0){
            velocity.add(0,GRAVITY,0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if(position.y < 0){
            position.y = 0;
        }

        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 250;
    }
    public void dispose(){
        texture.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
