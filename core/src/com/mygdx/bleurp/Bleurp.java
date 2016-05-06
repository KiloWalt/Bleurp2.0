package com.mygdx.bleurp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.bleurp.States.GSM;
import com.mygdx.bleurp.States.IntroState;
import com.mygdx.bleurp.States.MenuState;

public class Bleurp extends Game{
    public final static int WIDTH = 800;
    public final static int HEIGHT = 480;
    public final float STEP = 1 / 60f;
    private float accum;
    private SpriteBatch sb;
    private GSM gsm;
    private Preferences prefs;
    private boolean ran;
    Music music;



	@Override
	public void create () {

        prefs = Gdx.app.getPreferences("My Preferences");
        ran = prefs.getBoolean("ran");
        if (prefs.getBoolean("is_first_run", true)) {
            ran = false;
            prefs.putBoolean("is_first_run", false).flush();
        }

        prefs.putBoolean("update",true);
        prefs.flush();

        music = Gdx.audio.newMusic(Gdx.files.internal("playMusic.mp3"));
        music.setLooping(true);
        music.play();
        sb = new SpriteBatch();
        gsm = new GSM();
        //ran = false;
        if(ran == false) {
            prefs.putBoolean("intro",true);
            prefs.flush();
            prefs.putBoolean("level1", true);
            prefs.flush();
            for(int i = 2; i < 21; i++){
                prefs.putBoolean("level" + i,false);
                prefs.flush();
            }
            prefs.putString("currentLevel", "level1");
            prefs.flush();
            prefs.putBoolean("ran", true);
        }

        if(prefs.getBoolean("intro") == true){
            gsm.push(new IntroState(gsm));
            prefs.putBoolean("intro",false);
            prefs.flush();

        }
        else{
            gsm.push(new MenuState(gsm));
        }





	}

    @Override
    public void render(){
        accum += Gdx.graphics.getDeltaTime();
        while(accum >= STEP){
            accum -=STEP;
            gsm.update(STEP);
            gsm.render(sb);
        }
    }

    @Override
    public void dispose(){
        gsm.dispose();

    }
    public void update(float delta){

    }

}
