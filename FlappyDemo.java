//from Youtube, Brent Aureli's - Code School
//https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
//The main class of our LibGDX Project
//AndroidManifest > Screen orientation > from landscape to portrait
package com.android.game;

import com.android.game.states.GameStateManager;
import com.android.game.states.MenuState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class FlappyDemo extends ApplicationAdapter {

    //global variables
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Flappy Bird";
    private GameStateManager gsm;
    private SpriteBatch batch; //you need only one SpriteBatch. It's very heavy.

    private Music music;

    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true); //it's gonna continuously loop around
        music.setVolume(0.1f); //1 is 100% volume
        music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));

    }

    @Override
    public void render () { //it's on loop all the time
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime()); //our gsm needs to first update
        gsm.render(batch); //we will give it our batch file
    }

    @Override
    public void dispose() { //right click after "extends Application Adapter", Generate, Override Method, dispose()
        super.dispose();
        music.dispose();
    }
}
