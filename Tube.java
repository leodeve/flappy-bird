//from Youtube, Brent Aureli's - Code School. I added a score and a highscore.
//https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
package com.android.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;


public class Tube {
    public static final int TUBE_WIDTH = 52; //it depends on the width of our tube in pixels
    private static final int FLUCTUATION = 130; //it can move up and down randomly between 0 and 130
    private static final int TUBE_GAP = 75; //the difference between the openings in the tube will be 75 (it was 100 at first)
    private static final int LOWEST_OPENING = 120; //the top of the bottom tube can't be below 120 pixels
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Rectangle boundsTop, boundsBot;
    private Rectangle boundsMid; //to draw an invisible rectangle around the outside of our tube texture
    private Random rand;

    public Tube(float x){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x + 200,rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING); //x + 150 to wait a bit before seeing the first tube
        posBotTube = new Vector2(x + 200, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        boundsMid = new Rectangle(posTopTube.x + topTube.getWidth() + 20, posTopTube.y - TUBE_GAP, 1, TUBE_GAP);}

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() { return posTopTube; }

    public Vector2 getPosBotTube() { return posBotTube; }

    public void reposition(float x){ //to put the tube we just passed forward
        posTopTube.set(x ,rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x , posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);//we just need to reset the position of the rectangle, not the size
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
        boundsMid.setPosition(posTopTube.x + topTube.getWidth(), posTopTube.y - TUBE_GAP);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot); //does the bird overlap one of the rectangles?
    }

    public boolean not_collides(Rectangle player){
        return player.overlaps(boundsMid);
    }

    public void moveBoundsMidOffScreen(){
        boundsMid.setY(boundsMid.getY() + 10000);
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }

}
