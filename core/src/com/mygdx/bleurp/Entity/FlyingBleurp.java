package com.mygdx.bleurp.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by KiloWalt on 5/6/2016.
 */
public class FlyingBleurp extends Sprite implements GestureDetector.GestureListener{

    private Vector2 velocity = new Vector2();
    private float speed = 200 * 2, speedU = 60 * 2,animationTime = 0;
    private float gravity = 80 *1.8f;
    private GestureDetector gd;
    private TiledMapTileLayer collisionLayer;
    private String blockedKey = "blocked";
    private Animation still, left, right,upLeft,upRight,upIdle;
    public float oldX, oldY;
    public Sound jump;


    public FlyingBleurp(Animation still,Animation left,Animation right,
                        Animation upLeft,Animation upRight,Animation upIdle,TiledMapTileLayer collisionLayer){
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

        // apply gravity
        velocity.y -= gravity * dt;

        velocity.x = speed;

        boolean collisionX = collidesRight();
        boolean collisionY = collidesRight();

        if(collisionX || collisionY) {
            setX(oldX);
            velocity.x = 0;
        }

        // save old position
        oldX = getX();
        oldY = getY();


        // move on x
        setX(getX() + velocity.x * dt);

        // move on y
        setY(getY() + velocity.y * dt * 5f);

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
        animationTime = 0;
        jump.play();
        velocity.y = speedU / 1.8f;
        return true;
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
        return false;
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
