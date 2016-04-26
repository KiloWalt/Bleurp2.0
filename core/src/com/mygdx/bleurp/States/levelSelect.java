package com.mygdx.bleurp.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.bleurp.Bleurp;

/**
 * Created by Albert on 2016-03-29.
 */
public class levelSelect extends State{

    //Create  font;
    BitmapFont font;
    Skin skin;
    Skin skin2;

    private OrthographicCamera b2dCam;

    //Texture
    Pixmap pixmap;
    Stage stage;
    FitViewport vp;

    TextButton b;

    Array<TextButton> buttons;

    Preferences prefs;

    public levelSelect(GSM gsm) {
        super(gsm);
        buttons = new Array<TextButton>(20);
        prefs = Gdx.app.getPreferences("My Preferences");
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Bleurp.WIDTH, Bleurp.HEIGHT);
        vp = new FitViewport(Bleurp.WIDTH,Bleurp.HEIGHT,b2dCam);
        stage = new Stage(vp,sb);

        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        createBasicSkin();
        createUncheckSkin();

        makeButton(prefs.getBoolean("level1"), "Level 1", 0, 400);
        makeButton(prefs.getBoolean("level2"), "Level 2", 130, 400);
        makeButton(prefs.getBoolean("level3"), "Level 3", 260, 400);
        makeButton(prefs.getBoolean("level4"), "Level 4", 390, 400);
        makeButton(prefs.getBoolean("level5"), "Level 5", 520, 400);
        makeButton(prefs.getBoolean("level6"), "Level 6", 650, 400);

        makeButton(prefs.getBoolean("level7"), "Level 7", 0, 300);
        makeButton(prefs.getBoolean("level8"), "Level 8", 130, 300);
        makeButton(prefs.getBoolean("level9"), "Level 9", 260, 300);
        makeButton(prefs.getBoolean("level10"), "Level 10", 390, 300);
        makeButton(prefs.getBoolean("level11"), "Level 11", 520, 300);
        makeButton(prefs.getBoolean("level12"), "Level 12", 650, 300);

        makeButton(prefs.getBoolean("level13"), "Level 13", 0, 200);
        makeButton(prefs.getBoolean("level14"), "Level 14", 130, 200);
        makeButton(prefs.getBoolean("level15"), "Level 15", 260, 200);
        makeButton(prefs.getBoolean("level16"), "Level 16", 390, 200);
        makeButton(prefs.getBoolean("level17"), "Level 17", 520, 200);
        makeButton(prefs.getBoolean("level18"), "Level 18", 650, 200);


        for(int i = 0; i < buttons.size; i++){
            stage.addActor(buttons.get(i));
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        updateButton("level1", 0);
        updateButton("level2", 1);
        updateButton("level3", 2);
        updateButton("level4", 3);
        updateButton("level5", 4);
        updateButton("level6", 5);
        updateButton("level7", 6);
        updateButton("level8", 7);
        updateButton("level9", 8);
        updateButton("level10", 9);
        updateButton("level11", 10);
        updateButton("level12", 11);
        updateButton("level13", 12);
        updateButton("level14", 13);
        updateButton("level15", 14);
        updateButton("level16", 15);
        updateButton("level17", 16);
        updateButton("level18", 17);
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            gsm.set(new MenuState(gsm));
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 255f, 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(b2dCam.combined);
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
        pixmap = new Pixmap(120,70, Pixmap.Format.RGB888);
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

    public void createUncheckSkin(){
        font = new BitmapFont();
        skin2 = new Skin();
        skin2.add("default",font);

        //Texture
        pixmap = new Pixmap(120,70, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin2.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin2.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.down = skin2.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin2.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin2.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin2.getFont("default");
        skin2.add("default", textButtonStyle);

    }

    public void makeButton(Boolean bool,String name, int x, int y){
        if(bool){
            b = new TextButton(name,skin);
            b.setPosition(x, y);
            buttons.add(b);
        }else{
            b = new TextButton(name,skin2);
            b.setPosition( x, y);
            buttons.add(b);
        }
    }

    public void updateButton(String bool,int number){
        if(prefs.getBoolean(bool)){
            prefs.flush();
            if(buttons.get(number).isPressed()){
                prefs.putString("currentLevel", bool);
                prefs.flush();
                gsm.set(new Play(gsm));
            }
        }
    }
}
