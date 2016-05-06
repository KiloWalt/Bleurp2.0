package com.mygdx.bleurp.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.bleurp.Bleurp;

/**
 * Created by Albert on 2016-03-29.
 */
public class pauseState extends State{
    //Create  font;
    BitmapFont font;
    Skin skin;
    //Texture
    Pixmap pixmap;
    Stage stage;
    TextButton newGameButton;
    TextButton levelSelect;
    FitViewport vp;
    Preferences prefs;
    Texture texture;
    private Music music;




    public pauseState(GSM gsm) {
        super(gsm);
        prefs = Gdx.app.getPreferences("My Preferences");
        cam.setToOrtho(false, Bleurp.WIDTH,Bleurp.HEIGHT);
        stage = new Stage();
        vp = new FitViewport(Bleurp.WIDTH,Bleurp.HEIGHT,cam);
        stage = new Stage(vp,sb);
        texture = new Texture("backgroundMenu.png");

        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        createBasicSkin();
        newGameButton = new TextButton("Resume", skin); // Use the initialized skin
        newGameButton.setPosition(318, 200);
        stage.addActor(newGameButton);

        levelSelect = new TextButton("Main Menu",skin);
        levelSelect.setPosition(318, 100);
        stage.addActor(levelSelect);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(newGameButton.isPressed()){
            gsm.pop();
        }
        if(levelSelect.isPressed()){
            gsm.pop();
            gsm.set(new MenuState(gsm));
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 255f, 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        //sb.draw(texture,0,0,800,480);
        sb.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    public void createBasicSkin(){
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default",font);

        //Texture
        pixmap = new Pixmap(150,75, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.PINK);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
}
