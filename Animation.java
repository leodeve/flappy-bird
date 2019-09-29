package com.android.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; //how long we're telling a frame has to stay in view before we switch to the next one
    private float currentFrameTime; //the time animation is been in the current frame
    private int frameCount;//the number of frames in our animation
    private int frame; //the current frame we are actually in

    //We need to build constructor
    public Animation(TextureRegion region, int frameCount, float cycleTime){ //the region is gonna be all of the frame combined into one image / cycle : how long it's gonna take to cycle through the entire animation
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount; //the width of just a single frame
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount; //the total time it takes to get through our image divided by our frameCount (each frame gets an equal portion)
        frame = 0; //that's where we start
    }

    //We need to update our animation
    public void update(float dt){ //the change in time between render cycles
        currentFrameTime += dt; //how long the current frame is been in view
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount)
            frame = 0; //go back to the start
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }




}
