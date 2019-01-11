//from Youtube, Brent Aureli's - Code School. I added a score and a highscore.
//https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
package com.android.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird { //we need a position, a texture (what it needs to be drawn to the screen, and a velocity (in which direction is it going
    private static final int GRAVITY = -15; //if you don't fly, you fall
    public int MOVEMENT = 100; //we add a horizontal movement
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds; //we need also a rectangle for the bird
    private Animation birdAnimation;
    private Texture texture; //instead of creating the texture inside the constructor, we create that outside as a private variable
    private Sound flap; //private Sound if .ogg

    public Bird(int x, int y){ //our constructor
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);//we will start at not moving
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    //let's create a new method called "update" which will send the delta time to our bird class to reset its position in our game world
    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt); //we need to scale the velocity by its changing time (it's gonna multiply everything by the delta time)
        position.add(MOVEMENT * dt, velocity.y, 0); //dt = a move related to our change in delta time for the frame
        if (position.y < 0)
            position.y = 0; //to not go below the bottom of the screen

        velocity.scl(1/dt);//we turn that back to be able to add it again
        bounds.setPosition(position.x, position.y); //to update the position of the bird
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() { return birdAnimation.getFrame(); } //instead of "public Texture" and "return bird" (for one bird)

    public void jump(){
        velocity.y = 250;
        flap.play(1f); //or just flap.play()
    }

    public Rectangle getBounds(){ return bounds; }


    public void dispose(){
        texture.dispose(); //instead of "bird.dispose()
        flap.dispose();
    }
}
