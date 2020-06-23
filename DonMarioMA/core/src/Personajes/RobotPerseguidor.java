/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author Wunderton
 */
public class RobotPerseguidor extends Image {
    TextureRegion stand, jump;
    Animation walk;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;
    float VELOCIDAD_ENEMIGO = 2f;
    DonMarioMA DonMarioMA;

    public RobotPerseguidor(DonMarioMA DonMarioMa) {
        final float width = 190;
        final float height = 260;
        this.setSize(1, height / width);

        Texture koalaTexture = new Texture("Robotnew.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);

        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[2][6], grid[2][7], grid[2][8]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        this.DonMarioMA = DonMarioMa;
    }

    @Override
    public void act(float delta) {
         xVelocity = 0;
         
        if (DonMarioMA.getX() - this.getX() < 20) {

            xVelocity = MAX_VELOCITY;

            if (DonMarioMA.getY()-this.getY()<1) {
                if (DonMarioMA.getX()-this.getX()<1) {

                    xVelocity=-xVelocity;

                }
            } else {
                xVelocity=0;
            } 
        }
        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }   
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (yVelocity != 0) {
            frame = jump;
            
        } else if (xVelocity != 0) {
            frame = (TextureRegion) walk.getKeyFrame(time);
        } else {
            frame = stand;
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layer.setCell(x, y, null);
                    }
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(),this.getWidth(), this.getHeight());
    }
}