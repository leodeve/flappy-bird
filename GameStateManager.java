package com.android.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameStateManager { //creating a GameStateManager fix everything inside of a state

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){ states.push(state); }

    public void pop(){
        states.pop().dispose(); //during a break, push the playing state and pop the break state
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state); //we want to push and pop instantly
    }

    public void update(float dt){ //delta time between the 2 renders
        states.peek().update(dt); //how do we look at the top object of the stack - that is the peek method
    }

    public void render(SpriteBatch sb){ //SpriteBatch = container for everything and render is everything to our screen
        states.peek().render(sb);
    }
}
