package com.mygdx.bleurp.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.bleurp.Entity.Boulder;
import com.mygdx.bleurp.Entity.Exit;
import com.mygdx.bleurp.Entity.Player;
import com.mygdx.bleurp.Entity.Spike;
import com.mygdx.bleurp.Entity.Trampoline;

/**
 * Created by Albert on 2016-03-28.
 */
public class Play extends State{

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public Player player;
    private Preferences prefs;
    private String currentLevel;
    private ShapeRenderer sr;
    TextureAtlas playerAtlas;
    Sound portal;
    Sprite button1;
    Rectangle playerRect;

    //Particles
    ParticleEffect pe;
    //Boulders
    private Array<Boulder> boulders;

    //Spikes
    private Array<Spike> spikes;

    //Trampolines
    private Array<Trampoline> tramps;

    private Exit exit;
    Vector3 touchPoint;


    public Play(GSM gsm) {
        super(gsm);
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("Particle.party"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pe.start();
        touchPoint = new Vector3();
        button1 = new Sprite(new Texture("backButton.gif"));
        button1.setPosition(100,100);
        portal = Gdx.audio.newSound(Gdx.files.internal("portal.wav"));
        prefs = Gdx.app.getPreferences("My Preferences");
        currentLevel = prefs.getString("currentLevel");
        map = new TmxMapLoader().load(currentLevel + ".tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        sr = new ShapeRenderer();
        sr.setColor(Color.CYAN);
        Gdx.gl.glLineWidth(3);
        renderer.setView(cam);
        playerAtlas = new TextureAtlas("player.pack");
        Animation still, left, right,upLeft,upRight,upIdle;
        still = new Animation(1 / 2f, playerAtlas.findRegions("still"));
        left = new Animation(1 / 10f, playerAtlas.findRegions("left"));
        right = new Animation(1 / 10f, playerAtlas.findRegions("right"));
        upLeft = new Animation(1, playerAtlas.findRegions("upLeft"));
        upRight = new Animation(1, playerAtlas.findRegions("upRight"));
        upIdle = new Animation(1 / 6f, playerAtlas.findRegions("upIdle"));
        still.setPlayMode(Animation.PlayMode.LOOP);
        left.setPlayMode(Animation.PlayMode.LOOP);
        right.setPlayMode(Animation.PlayMode.LOOP);
        upLeft.setPlayMode(Animation.PlayMode.LOOP);
        upRight.setPlayMode(Animation.PlayMode.LOOP);
        upIdle.setPlayMode(Animation.PlayMode.LOOP);
        boulders = new Array<Boulder>();
        spikes = new Array<Spike>();
        tramps = new Array<Trampoline>();

        //Render Objects
        for(MapObject object : map.getLayers().get("objects").getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rec = ((RectangleMapObject) object).getRectangle();
                if(((RectangleMapObject)object).getProperties().containsKey("boulder")){
                    Boulder b = new Boulder(new Sprite(new Texture("boulder.gif"),0,0,60,60),(TiledMapTileLayer) map.getLayers().get("Block"));
                    b.setPosition(rec.x /60 * b.getCollisionLayer().getTileWidth(),rec.y /60 * b.getCollisionLayer().getTileHeight());
                    boulders.add(b);
                }
                if(((RectangleMapObject)object).getProperties().containsKey("spike")){
                    Spike s = new Spike(new Sprite(new Texture("spike.gif")),(TiledMapTileLayer) map.getLayers().get("Block"));
                    s.setPosition(rec.x /60 * s.getCollisionLayer().getTileWidth(),rec.y / 60 * s.getCollisionLayer().getTileHeight());
                    spikes.add(s);
                }
                if(((RectangleMapObject)object).getProperties().containsKey("portal")){
                    exit = new Exit(new Sprite(new Texture("portal.gif")),(TiledMapTileLayer) map.getLayers().get("Block"));
                    exit.setPosition(rec.getX() / 60 * exit.getCollisionLayer().getTileWidth(), rec.getY() / 60 * exit.getCollisionLayer().getTileHeight());
                }
                if(((RectangleMapObject)object).getProperties().containsKey("player")){
                    player = new Player(still, left, right,upLeft,upRight,upIdle,(TiledMapTileLayer) map.getLayers().get("Block"));
                    player.setPosition(rec.x / 60 * player.getCollisionLayer().getTileWidth(), rec.y / 60 * player.getCollisionLayer().getTileHeight());
                }
                if(((RectangleMapObject)object).getProperties().containsKey("tramp")){
                    Trampoline tramp = new Trampoline(new Sprite(new Texture("tramp.gif")),(TiledMapTileLayer)map.getLayers().get("Block"));
                    tramp.setPosition(rec.x / 60 * tramp.getCollisionLayer().getTileWidth(),rec.y / 60 * tramp.getCollisionLayer().getTileHeight());
                    tramps.add(tramp);
                }
                sr.begin(ShapeRenderer.ShapeType.Filled);
                //sr.rect(rec.x,rec.y,rec.width,rec.height);
                sr.end();
            }else if(object instanceof CircleMapObject){
                Circle circle = ((CircleMapObject) object).getCircle();
                sr.begin(ShapeRenderer.ShapeType.Filled);
                //sr.circle(circle.x, circle.y, circle.radius);
                sr.end();

            }else if(object instanceof PolylineMapObject){
                Polyline poly = ((PolylineMapObject) object).getPolyline();
                sr.begin(ShapeRenderer.ShapeType.Line);
                //sr.polyline(poly.getTransformedVertices());
                sr.end();

            }else if(object instanceof EllipseMapObject){
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                sr.begin(ShapeRenderer.ShapeType.Filled);
                //sr.ellipse(ellipse.x, ellipse.y, ellipse.width,ellipse.height);
                sr.end();
            }else if(object instanceof PolygonMapObject){
                Polygon poly = ((PolygonMapObject) object).getPolygon();
                sr.begin(ShapeRenderer.ShapeType.Filled);
                //sr.polygon(poly.getTransformedVertices());
                sr.end();
            }
        }
        playerRect = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        pe.setPosition(player.getX() + 30,player.getY() + 20);
        pe.update(dt);
        button1.setPosition(player.getX() - 370, player.getY() + 225);
        player.update(dt);
        if(player.fallTimer >= 200){
            gsm.set(new Play(gsm));
            dispose();
        }
        //Boulders
        for(int i = 0; i < boulders.size; i++){
            boulders.get(i).update(dt);
        }

        exit.update(dt);
        CheckCollision();
        checkTrampCollision();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (Gdx.input.justTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (button1.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
                gsm.push(new pauseState(gsm));
            }
        }
        Gdx.gl.glClearColor(0, 255f, 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
        cam.update();
        sb.begin();
        pe.draw(sb);
        player.draw(sb);
        exit.draw(sb);
        button1.draw(sb);

        //Spikes
        /*for(int i = 0; i < spikes.size; i++){
            spikes.get(i).draw(sb);
        }*/
        //Boulders
        for (int i = 0; i < boulders.size; i++) {
            boulders.get(i).draw(sb);
        }
        //Trampolines
        for (int i = 0; i < tramps.size; i++) {
            tramps.get(i).draw(sb);
        }
        sb.end();
        renderer.setView(cam);
        renderer.render();
        sb.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);


    }
    public void dispose() {
        player.getTexture().dispose();
        player.jump.dispose();
        pe.dispose();
        map.dispose();
        playerAtlas.dispose();
    }

    private void CheckCollision(){
        playerRect.setPosition(player.getX(),player.getY());
        Rectangle portalRect = new Rectangle(exit.getX(),exit.getY(),exit.getWidth(),exit.getHeight());
        if(playerRect.overlaps(portalRect)){
            for(int i = 1; i < 6; i ++){
                String level = "level" + String.valueOf(i);
                if(String.valueOf(currentLevel).equals(level)){
                    prefs.putBoolean("level" + String.valueOf(i + 1), true);
                    prefs.flush();
                    portal.play();
                    gsm.set(new levelSelect(gsm));
                    dispose();

                }
            }

        }
        for(int i = 0; i < boulders.size; i++ ){
            Boulder b = boulders.get(i);
            Rectangle boulderRect = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
            if(playerRect.overlaps(boulderRect)){
                player.setY(player.oldY);
                if(b.getVelocity().x < player.getVelocity().x){
                    player.setVelocity(new Vector2(70,70 / 1.8f));
                    b.setVelocity(new Vector2(player.getVelocity().x,b.getVelocity().y));

                }
                if(b.getVelocity().x > player.getVelocity().x){
                    player.setVelocity(new Vector2(-60,70 / 1.8f));
                    b.setVelocity(new Vector2(player.getVelocity().x,b.getVelocity().y));
                }else{
                }
            }
        }
        for(int i = 0 ; i < spikes.size; i++){
            Spike s = spikes.get(i);
            Rectangle spikeRect = new Rectangle(s.getX(),s.getY(),s.getWidth(),s.getHeight());
            if(playerRect.overlaps(spikeRect)){
                gsm.set(new Play(gsm));
                dispose();
            }
        }

    }

    private void checkTrampCollision(){
        for(int i = 0; i < tramps.size; i++){
            Trampoline tramp = tramps.get(i);
            playerRect.setPosition(player.getX(),player.getY());
            Rectangle trampRect = new Rectangle(tramp.getX(),tramp.getY(),60,30);
            if(playerRect.overlaps(trampRect)){
                player.setVelocity((new Vector2(player.getVelocity().x,150 / 1.8f)));
                player.jump.play();
                player.fallTimer = 0;
            }


        }
    }




}
