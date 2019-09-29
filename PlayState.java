package com.android.game.states;

import com.android.game.FlappyDemo;
import com.android.game.sprite.Bird;
import com.android.game.sprite.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;


import static com.android.game.FlappyDemo.HEIGHT;
import static com.android.game.FlappyDemo.WIDTH;


public class PlayState extends State {
    public int TUBE_SPACING = 125; //the space between tubes (not including tube itself)
    private static final int TUBE_COUNT = 4; //how many tube the game actually have
    private static final int GROUND_Y_OFFSET = -43;


    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    private Stage uiStage; //user interface stage
    private Label scoreLabel;
    private int score;
    private int highscore;

    public static BitmapFont fontMedium;
    Preferences prefs = Gdx.app.getPreferences("My Preferences");

    //CharSequence scoreString = "";


    public PlayState(GameStateManager gsm) { //we need to generate our constructor = GamePlayScreen
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, WIDTH / 2, HEIGHT / 2);//yDown = False (we want the basic orthonormal coordinate system) / Put that in our MenuState as well
        fontMedium = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font_0.png"), false);
        uiStage = new Stage(new StretchViewport(FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2));
        scoreLabel = new Label("0", new Label.LabelStyle(fontMedium, Color.WHITE));
        scoreLabel.setPosition(FlappyDemo.WIDTH / 4, (FlappyDemo.HEIGHT / 2)*.9f, Align.center);
        scoreLabel.setFontScale(1f,1f);
        uiStage.addActor(scoreLabel);

        //initialisation: if record not broken, highscore = 0
        //prefs.putInteger("highscore", 0);

        //get Integer from preferences, 0 is the default value
        highscore = prefs.getInteger("highscore", 0);

        bg = new Texture("bg.jpg");
        ground = new Texture("ground.jpg");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) { //we are gonna create 4 new tubes in our array
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

    }



    @Override
    protected void handleInput() { //right click, generate, implement methods
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt); //we gotta update our bird with the changing delta time
        cam.position.x = bird.getPosition().x + 80; //we are gonna offset the camera just a little bit in front of our bird

        for(int i = 0; i <tubes.size; i++){
            Tube tube = tubes.get(i);

            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) { //if the tube is off the side of the screen
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT)); //to reposition the tube
            }

            if(tube.collides(bird.getBounds())){
                if (score>prefs.getInteger("highscore")){
                    prefs.putInteger("highscore", score); }
                //persist preferences
                prefs.flush();
                gsm.set(new PlayState(gsm));}

            if (tube.not_collides(bird.getBounds())){
                score++;
                scoreLabel.setText(String.valueOf(score));
                tube.moveBoundsMidOffScreen(); //We can't just increment the score. It's gonna happen every frame. We are gonna constantly overlapping as we go through the rectangle.
            }

        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){ //if it's lower than the ground level
            if (score>prefs.getInteger("highscore")){
                prefs.putInteger("highscore", score);}
            //persist preferences
            prefs.flush();
            gsm.set(new PlayState(gsm));}
        cam.update(); //to tell LibGDX the cam has been repositioned

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);//setProjectionMatrix tell us where in our game world we are / Put that in our MenuState as well
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2),0); //if we don't "-(cam...)", the left side of the bg will be in the center of the screen
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        //now, we need to draw our ground
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        fontMedium.draw(sb, "HIGHSCORE: ", 150, 100);
        fontMedium.draw(sb, String.valueOf(highscore), 290, 100);
        fontMedium.setColor(Color.WHITE);
        fontMedium.getData().setScale(1);
        fontMedium.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sb.end();

        uiStage.draw();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        //fontMedium.dispose();DO NOT DISPOSE FRONTMEDIUM !!! OTHERWISE, BLACK RECTANGLE
        uiStage.dispose();
        for(Tube tube : tubes) //all our tubes
            tube.dispose();
        System.out.println("Play State Disposed");
    }

    private void updateGround(){ //we will check if the camera is passed where the position of our ground is
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2,0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2,0);

    }
}
