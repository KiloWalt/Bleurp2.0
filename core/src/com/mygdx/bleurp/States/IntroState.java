package com.mygdx.bleurp.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.bleurp.Bleurp;

/**
 * Created by KiloWalt on 5/4/2016.
 */
public class IntroState extends State {

    private Texture texture;
    private float locationX = -960;
    public IntroState(GSM gsm) {
        super(gsm);
        cam.setToOrtho(false, Bleurp.WIDTH,Bleurp.HEIGHT);
        texture = new Texture("introImage.png");


    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        locationX +=20 *dt;

        if(locationX >= -200){
            gsm.set(new MenuState(gsm));
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(texture,0,locationX,texture.getWidth(),texture.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {
        texture.dispose();
        sb.dispose();

    }
}
