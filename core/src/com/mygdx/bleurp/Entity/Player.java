package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Albert on 2016-03-28.
 */
public class Player extends Sprite implements GestureDetector.GestureListener{

    private Vector2 velocity = new Vector2();
    private float speed = 120 * 2, speedU = 60 * 2,animationTime = 0;
    private float gravity = 60 *1.8f;
    private GestureDetector gd;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";
    private boolean canJump,canJump2;
    private Animation still, left, right,upLeft,upRight,upIdle;
    public float oldX, oldY;
    public float fallTimer;
    public Sound jump;


    public Player(Animation still,Animation left,Animation right,
                  Animation upLeft,Animation upRight,Animation upIdle,TiledMapTileLayer collisionLayer) {
        super(still.getKeyFrame(0));
        this.still = still;
        this.left = left;
        this.right = right;
        this.upLeft = upLeft;
        this.upRight = upRight;
        this.upIdle = upIdle;
        this.collisionLayer = collisionLayer;
        jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));



        gd = new GestureDetector(this);
    }

    public void update(float dt){
        Gdx.input.setInputProcessor(gd);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(canJump){
                animationTime = 0;
                jump.play();
                velocity.y = speedU / 1.8f;
                canJump = false;
            }
            if(canJump2){
                jump.play();
                animationTime = 0;
                velocity.y = speedU /1.8f;
                fallTimer = 0;
                canJump2 = false;
            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            velocity.x = speed;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            velocity.x =- speed;

        }

        // apply gravity
        velocity.y -= gravity * dt;

        // clamp velocity
        if(velocity.y > speed)
            velocity.y = speedU;
        else if(velocity.y < -speed)
            velocity.y = -speedU;

        // save old position
        oldX = getX();
        oldY = getY();
        boolean collisionX = false, collisionY = false;

        // move on x
        setX(getX() + velocity.x * dt);

        if(velocity.x < 0){
            //Going Left
            collisionX = collidesLeft();
            canJump2 = collisionX = collidesLeft();
        }


        else if(velocity.x > 0){
            // Going right
            collisionX = collidesRight();
            canJump2 = collisionX = collidesRight();
        }

        // react to x collision
        if(collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        // move on y
        setY(getY() + velocity.y * dt * 5f);

        if(velocity.y < 0){
            //falling Down
            fallTimer+=1;
            canJump = collisionY = collidesBottom();
        }

        else if(velocity.y > 0) // going up
            collisionY = collidesTop();

        // react to y collision
        if(collisionY) {
            setY(oldY);
            fallTimer = 0;
            velocity.y = 0;
        }

        // update animation
        animationTime += dt;
        setRegion( velocity.y > 0 & velocity.x > 0 ? upRight.getKeyFrame(0)
                :velocity.x < 0 & velocity.y > 0 ? upLeft.getKeyFrame(0):velocity.x < 0 ? left.getKeyFrame(animationTime) :
                velocity.x > 0 ? right.getKeyFrame(animationTime):velocity.x == 0 & velocity.y > 0 ? upIdle.getKeyFrame(animationTime):
                velocity.x > 0 & velocity.y < 0 ? right.getKeyFrame(animationTime):velocity.x < 0 & velocity.y < 0 ? left.getKeyFrame(animationTime):still.getKeyFrame(animationTime));
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
    public void draw(SpriteBatch sb){
        super.draw(sb);
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (deltaX > 0) {
            if (deltaX > deltaY) {
                if (deltaX > deltaY * -1) {
                    velocity.x = speed;
                }

            }
        }
        else if (deltaX < 0) {
            if (deltaX < deltaY) {
                if (deltaX < deltaY * -1) {
                    velocity.x =- speed;
                }

            }
        }
        if (deltaY > 0) {
            if (deltaY > deltaX) {
                if (deltaY > deltaX * -1) {
                    velocity.x = 0;
                }

            }
        }
        else if (deltaY < 0) {
            if (deltaY < deltaX) {
                if (deltaY < deltaX * -1) {
                    if(canJump){
                        animationTime = 0;
                        jump.play();
                        velocity.y = speedU / 1.8f;
                        canJump = false;
                    }
                    if(canJump2){
                        jump.play();
                        animationTime = 0;
                        velocity.y = speedU /1.8f;
                        fallTimer = 0;
                        canJump2 = false;
                    }

                }

            }
        }


        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
