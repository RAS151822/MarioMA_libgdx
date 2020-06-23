package Personajes;

import Pantallas.PantallaInicial;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    

    @Override
    public void create() {
        int puntos = 0;
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new PantallaInicial(this));
    }

    @Override
    public void render () {
    super.render();
    }

    @Override
    public void dispose () {
    batch.dispose();
        font.dispose();
    }
}
