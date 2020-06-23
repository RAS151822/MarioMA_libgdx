/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Pantallas.PantallaGAMEOVER;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class World implements Screen {
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    DonMarioMA DonMarioMA;
    
    Sound GAMEOVERsound;
    
    int vidas = 100;
    int puntos = 0;
    
    MyGdxGame game;
    Music MusicaNivel1;
    
    //ROBOTS ENEMIGOS INTELIGENTES
    RobotPerseguidor RobotMalvado;
    
    
    //ALIENS VOLADORES ENEMIGOS COMUNES
    AlienVoladorAzul AlienAzul;
    AlienVoladorVerde AlienVerde;
    AlienVoladorAmarillo AlienAmarillo;
    
    
    //ZOMBIES
    Zombie Zombie1;
    Zombie Zombie2;
    

    public World(MyGdxGame gamenew, int puntosnew) {
        
        this.game=gamenew;
        this.puntos = puntosnew;
    }

    @Override
    public void show() {
        
        //Musica
        GAMEOVERsound = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
        MusicaNivel1 = Gdx.audio.newMusic(Gdx.files.internal("RunningOne.mp3"));
        MusicaNivel1.setLooping(true);
        //MusicaNivel1.play();
        
        
        map = new TmxMapLoader().load("Levelnew.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        
        
        //music_level1 = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));

        //HEROE
        DonMarioMA = new DonMarioMA(game);
        DonMarioMA.layer1 = (TiledMapTileLayer) map.getLayers().get("walls");
        DonMarioMA.layer2 = (TiledMapTileLayer) map.getLayers().get("puntos");
        DonMarioMA.layer3 = (TiledMapTileLayer) map.getLayers().get("dead");
        DonMarioMA.layer4 = (TiledMapTileLayer) map.getLayers().get("finalgame");
        DonMarioMA.layer5 = (TiledMapTileLayer) map.getLayers().get("background2");
        DonMarioMA.layer6 = (TiledMapTileLayer) map.getLayers().get("background1");
        
        DonMarioMA.setPosition(6, 15);
        stage.addActor(DonMarioMA);
        
        //ENEMIGOOOS INTELIGENTES
        //Robot perseguidor
        RobotMalvado = new RobotPerseguidor(DonMarioMA);
        RobotMalvado.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        RobotMalvado.setPosition(100, 2);
        stage.addActor(RobotMalvado);
        
        
        //ENEMIGOS COMUNES ALIENIGENAS!!
        AlienAzul = new AlienVoladorAzul();
        AlienAzul.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        AlienAzul.setPosition(35, 18);
        stage.addActor(AlienAzul);
        
        AlienVerde = new AlienVoladorVerde();
        AlienVerde.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        AlienVerde.setPosition(71, 18);
        stage.addActor(AlienVerde);
        
        AlienAmarillo = new AlienVoladorAmarillo();
        AlienAmarillo.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        AlienAmarillo.setPosition(74, 18);
        stage.addActor(AlienAmarillo);
        
        //ZOMBIES
        Zombie1 = new Zombie(DonMarioMA);
        Zombie1.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        Zombie1.setPosition(62, 2);
        stage.addActor(Zombie1);
        
        Zombie2 = new Zombie(DonMarioMA);
        Zombie2.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        Zombie2.setPosition(104, 2);
        stage.addActor(Zombie2);
        
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.x = DonMarioMA.getX();
        camera.update();

        renderer.setView(camera);
        renderer.render();

        stage.act(delta);
        stage.draw();
        game.batch.begin();
        game.font.draw(game.batch, "Puntos de vida:" + DonMarioMA.getVidas(), 20, 450);
        game.font.draw(game.batch, "Puntos:" + DonMarioMA.getPuntos(), 20, 420);
        game.batch.end();
        
        //PARA TERMINAR EL NIVELY PARAR LA MUSICA ETC
        if(DonMarioMA.getX() > 204){
            MusicaNivel1.stop();
        
        }
        
        //CAIDA LIBRE CUANDO TOCA EL AGUA
        //System.out.println("La Y del personaje es: " + DonMarioMA.getY());
        
        if (DonMarioMA.getY()<-90) {
            game.setScreen(new PantallaGAMEOVER(game,puntos));
            MusicaNivel1.stop();
            GAMEOVERsound.play();
        }
        
        if (DonMarioMA.getX() > 220) {
            game.setScreen(new PantallaGAMEOVER(game,puntos));
            MusicaNivel1.stop();
            GAMEOVERsound.play();
        }
       
        
        if (DonMarioMA.getX() < -20) {
            game.setScreen(new PantallaGAMEOVER(game,puntos));
            MusicaNivel1.stop();
            GAMEOVERsound.play();
        }
          
        //MUERTE POR FALTA DE VIDAS
        if(DonMarioMA.getVidas() <= 0){
            game.setScreen(new PantallaGAMEOVER(game,puntos));
            MusicaNivel1.stop();
            GAMEOVERsound.play();
        }
        
///////////////////////////////////////MUERTE POR ENEMIGOS
        if(DonMarioMA.getBounds().overlaps(RobotMalvado.getBounds())){
             vidas--;
             DonMarioMA.setVidas(vidas);
        }
        
        if(DonMarioMA.getBounds().overlaps(AlienAzul.getBounds())){
             vidas--;
             DonMarioMA.setVidas(vidas);
        }
        
        if(DonMarioMA.getBounds().overlaps(AlienVerde.getBounds())){
             vidas--;
             DonMarioMA.setVidas(vidas);
        }
        
        if(DonMarioMA.getBounds().overlaps(AlienAmarillo.getBounds())){
             vidas--;
             DonMarioMA.setVidas(vidas);
        }
        
        if(DonMarioMA.getBounds().overlaps(Zombie1.getBounds())){
             puntos--;
             vidas--;
             DonMarioMA.setVidas(vidas);
             DonMarioMA.setPuntos(puntos);
        }
        
        if(DonMarioMA.getBounds().overlaps(Zombie2.getBounds())){
             puntos--;
             vidas--;
             DonMarioMA.setVidas(vidas);
             DonMarioMA.setPuntos(puntos);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    @Override
    public void resume() {
    }
}
