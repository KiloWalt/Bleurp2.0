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
public class MenuState extends State{
    //Create  font;
    BitmapFont font;
    Skin skin;
    //Texture
    Pixmap pixmap;
    Stage stage;
    TextButton newGameButton;
    TextButton levelSelect;
    OrthographicCamera b2dCam;
    FitViewport vp;
    Preferences prefs;
    Texture texture;
    private Music music;




    public MenuState(GSM gsm) {
        super(gsm);
        prefs = Gdx.app.getPreferences("My Preferences");
        stage = new Stage();

        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Bleurp.WIDTH, Bleurp.HEIGHT);
        vp = new FitViewport(Bleurp.WIDTH,Bleurp.HEIGHT,b2dCam);
        stage = new Stage(vp,sb);
        texture = new Texture("backgroundMenu.png");

        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        createBasicSkin();
        newGameButton = new TextButton("Play", skin); // Use the initialized skin
        newGameButton.setPosition(318, 200);
        stage.addActor(newGameButton);

        levelSelect = new TextButton("Level Select",skin);
        levelSelect.setPosition(318, 100);
        stage.addActor(levelSelect);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(newGameButton.isPressed()){
            gsm.set(new Play(gsm));
            dispose();
        }
        if(levelSelect.isPressed()){
            gsm.set(new levelSelect(gsm));
            dispose();
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(b2dCam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(texture,0,0,800,480);
        sb.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        sb.dispose();
        texture.dispose();
        skin.dispose();
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
