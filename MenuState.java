//from Youtube, Brent Aureli's - Code School. I added a score and a highscore.
//https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
package com.android.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.android.game.FlappyDemo.HEIGHT;
import static com.android.game.FlappyDemo.WIDTH;

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, WIDTH / 2, HEIGHT / 2);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){ //if we touch the screen with our finger or a mouse
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) { //we need to handle input
        handleInput();//it's always going to be checking our input to see if our user has done anything
    }

    @Override
    public void render(SpriteBatch sb) { //open the box, put everything you want to it and close it (to render things to the screen)
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);//(0,0) = bottom left hand corner
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }

}
