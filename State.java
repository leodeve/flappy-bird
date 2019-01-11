//from Youtube, Brent Aureli's - Code School. I added a score and a highscore.
//https://www.youtube.com/watch?v=rzBVTPaUUDg&list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJypackage com.android.game.states;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;//each state needs a camera to locate a position in the world
    protected Vector3 mouse;//sort of pointer
    protected GameStateManager gsm;//way to manage our state

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);//delta time between two frames
    public abstract void render(SpriteBatch sb);//it renders everything to the screen in one block
    public abstract void dispose(); //each state should have that
}
